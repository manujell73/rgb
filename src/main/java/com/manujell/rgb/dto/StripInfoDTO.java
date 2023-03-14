package com.manujell.rgb.dto;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class StripInfoDTO {
    private final int length;
    private final int x;
    private final int y;
    private final List<ColorDTO> colors;
    private final boolean isContinuous;

    public StripInfoDTO(List<Color> colors, boolean isContinuous) {
        this.colors = colors.stream().map(ColorDTO::new).collect(Collectors.toList());
        this.isContinuous = isContinuous;
        this.length = this.colors.size();
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

    public List<ColorDTO> getColors() {
        return colors;
    }

    public boolean isContinuous() {
        return isContinuous;
    }

    private static class ColorDTO {
        private final int r;
        private final int g;
        private final int b;

        public ColorDTO(Color color) {
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
