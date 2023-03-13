package com.manujell.rgb.dto;

import java.util.Arrays;

public class StripInfoDTO {
    private final int length;
    private final int x;
    private final int y;
    private final Color[] colors;

    public StripInfoDTO(java.awt.Color[] colors) {
        this.colors = Arrays.stream(colors).map(Color::new).toArray(Color[]::new);
        this.length = this.colors.length;
        this.x = this.length;
        this.y = 1;
    }

    public int getLength() {
        return length;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Color[] getColors() {
        return colors;
    }

    private static class Color {
        private final int r;
        private final int g;
        private final int b;

        public Color(java.awt.Color color) {
            this.r = color.getRed();
            this.g = color.getGreen();
            this.b = color.getBlue();
        }

        public int getR() {
            return r;
        }

        public int getG() {
            return g;
        }

        public int getB() {
            return b;
        }
    }
}
