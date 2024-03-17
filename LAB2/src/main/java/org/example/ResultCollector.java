package org.example;
import java.util.ArrayList;
import java.util.List;
public class ResultCollector {
    private List<Result> results = new ArrayList<>();

    // Metoda dodająca nowy wynik do listy wyników
    public synchronized void addResult(Result result) {
        results.add(result);
        notify(); // Powiadamiamy wątek czekający na wyniki
    }

    // Metoda zwracająca kopię listy wyników
    public synchronized List<Result> getResults() {
        while (results.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return new ArrayList<>();
            }
        }
        List<Result> resultsCopy = new ArrayList<>(results);
        results.clear();
        return resultsCopy;
    }
}
