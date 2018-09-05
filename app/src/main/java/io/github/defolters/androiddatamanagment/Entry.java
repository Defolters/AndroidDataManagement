package io.github.defolters.androiddatamanagment;

public class Entry {
    private String first;
    private String second;

    public Entry(String first, String second) {
        this.first = first;
        this.second = second;
    }

    public String getFirst() {
        return first;
    }

    public String getSecond() {
        return second;
    }

    @Override
    public String toString() {
        return first + " " + second;
    }
}
