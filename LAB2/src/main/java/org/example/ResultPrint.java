package org.example;

public class ResultPrint implements Runnable{
    private ResultCollector resultCollector;

    public ResultPrint(ResultCollector resultCollector) {
        this.resultCollector = resultCollector;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1);
                for (Result result : resultCollector.getResults()) {
                    System.out.println("Task " + result.getId() + " is " + (result.getResult() ? "prime" : "not prime"));
                }
            } catch (InterruptedException e) {
//                for (Result result : resultCollector.getResults()) {
//                    System.out.println("Task " + result.getId() + " is " + (result.getResult() ? "prime" : "not prime"));
//                }
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
