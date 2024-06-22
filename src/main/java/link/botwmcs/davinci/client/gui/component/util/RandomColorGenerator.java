package link.botwmcs.davinci.client.gui.component.util;

import java.util.Random;

public class RandomColorGenerator {
    public static int generateRandomColor() {
        Random random = new Random();
        // Generate random values for red, green, and blue components
        int red = random.nextInt(256); // 0-255
        int green = random.nextInt(256); // 0-255
        int blue = random.nextInt(256); // 0-255

        // Combine the components into a hex color string
        return (red << 16) | (green << 8) | blue;
    }
}
