package org.example;

class PrimeChecker implements Runnable {
    private TaskManager taskManager;
    private ResultCollector resultCollector;
    private static int task_id;

    public PrimeChecker(TaskManager taskManager, ResultCollector resultCollector) {
        this.taskManager = taskManager;
        this.resultCollector =  resultCollector;
        this.task_id = 0;
    }

    @Override
    public void run() {
        while (true) {
            try {
                int task = taskManager.getNextTask();
                Thread.sleep(3000);
                this.task_id += 1;
                resultCollector.addResult(new Result(isPrime(task), task_id));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
    private boolean isPrime(int n) {
        if (n <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }
}