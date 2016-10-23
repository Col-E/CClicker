package me.coley.clicker.value;

public class NumericValue extends Value<Integer> {
    private final int min, max;

    public NumericValue(int value) {
        this(value, -1, 9999999);
    }

    public NumericValue(int value, int min, int max) {
        super(value);
        this.min = min;
        this.max = max;
    }

    @Override
    public void update(Integer value) {
        if (value > max) {
            value = max;
        } else if (value < min) {
            value = min;
        }
        super.update(value);
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }
}
