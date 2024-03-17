package org.example;

public class Result {
    private Boolean result;
    private int id;

    public Result(Boolean result, int id) {
        this.result = result;
        this.id = id;
    }

    public Boolean getResult() {
        return result;
    }

    public int getId() {
        return id;
    }
}
