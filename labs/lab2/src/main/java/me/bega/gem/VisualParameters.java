package me.bega.gem;

import java.util.Objects;

public class VisualParameters {
    private String color;
    private int opacity;
    private int edging;
    public VisualParameters(){
        this.color = "";
        this.opacity = 0;
        this.edging = 0;
    }
    public VisualParameters(String color, int opacity, int edging) {
        this.color = color;
        this.opacity = opacity;
        this.edging = edging;
    }

    public String getColor() {
        return color;
    }
    public int getOpacity() {
        return opacity;
    }
    public int getEdging() {
        return edging;
    }

    public void setColor(String color) {
        this.color = color;
    }
    public void setOpacity(int opacity) {
        this.opacity = opacity;
    }
    public void setEdging(int edging) {
        this.edging = edging;
    }

    @Override
    public String toString() {
        return "{" +
                "color='" + color + '\'' +
                ", opacity=" + opacity +
                ", edging=" + edging +
                '}';
    }
}
