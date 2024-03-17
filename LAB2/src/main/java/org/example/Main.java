package org.example;

import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        ResultCollector resultCollector = new ResultCollector();

        int threadsNumber = Integer.parseInt(args[0]);

        // Tworzenie wątków sprawdzających liczby pierwsze
        Thread[] threads = new Thread[threadsNumber];
        for (int i = 0; i < threadsNumber; i++) {
            threads[i] = new Thread(new PrimeChecker(taskManager, resultCollector));
            threads[i].start();
        }
        // Tworzenie wątku wyświetlającego wyniki

        Thread resultPrint = new Thread(new ResultPrint(resultCollector));
        resultPrint.start();

        // wczytywanie liczb z konsoli
        var scanner = new Scanner(System.in);
        int counter = 1;
        while (true) {
            String input = scanner.nextLine();
            if (Objects.equals(input, "exit")) {
                for (Thread thread : threads) {
                    thread.interrupt();
                }
                while (true) {
                    for (Thread thread : threads) {
                        if (thread.isAlive()) {
                            try {
                                thread.join(); // waithing for threads to finish
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                    if (resultPrint.isAlive()) {
                        resultPrint.interrupt();
                        try {
                            resultPrint.join();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                }
                break;
            }
            int number = Integer.parseInt(input);
            taskManager.addTask(number);
            System.out.println("Task " + counter + " Liczba " + number + " dodana do listy zadań.");
            counter++;
        }

    }
}