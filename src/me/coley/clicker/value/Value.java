package me.coley.clicker.value;

public class Value<T> {
    private T value, min, max;

    public Value(T value) {
        this.value = value;
    }

    public void update(T value) {
        this.value = value;
    }

    public T getCurrent() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
