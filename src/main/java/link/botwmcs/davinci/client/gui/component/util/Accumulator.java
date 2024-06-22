package link.botwmcs.davinci.client.gui.component.util;

public class Accumulator {
    private int value;
    private boolean incrementMode;

    public Accumulator(int value, boolean incrementMode) {
        this.value = value;
        this.incrementMode = incrementMode;
    }

    public Accumulator() {
        this(0, true);
    }

    public void incrementTick() {
        if (incrementMode) {
            value++;
        } else {
            value--;
        }
    }

    public void toggleMode() {
        incrementMode = !incrementMode;
    }

    public int getValue() {
        return value;
    }
}
