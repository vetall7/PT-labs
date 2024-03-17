package org.example;
import java.util.LinkedList;
import java.util.Queue;
class TaskManager {
    private LinkedList<Integer> tasks = new LinkedList<>();
    public synchronized void addTask(int task) {
        tasks.add(task);
        notify(); // Powiadamiamy wątek czekający na zadania
    }
    public synchronized int getNextTask() throws InterruptedException {
        try {
            wait();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return -1;
        }
        return tasks.removeFirst();
    }
}