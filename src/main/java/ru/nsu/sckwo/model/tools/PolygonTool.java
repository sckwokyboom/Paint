package ru.nsu.sckwo.model.tools;

import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.image.BufferedImage;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class PolygonTool {

    private int radius = 10;

    private int angle = 0;

    private int angleCount = 5;
    public PolygonTool(int radius, int angle, int angleCount) {
        this.radius = radius;
        this.angle = angle;
        this.angleCount = angleCount;
    }

    public PolygonTool() {
    }

    public void draw(@NotNull BufferedImage image, @NotNull Point center, @NotNull Color color) {
        final Graphics2D g2d = (Graphics2D) image.getGraphics();
        g2d.setColor(color);
        g2d.drawPolygon(getXCoords(center), getYCoords(center), angleCount);
    }

    private int[] getXCoords(@NotNull Point center) {
        int[] xCoords = new int[angleCount];
        for (int i = 0; i < angleCount; i++) {
            xCoords[i] = (int) (center.x + radius * cos(Math.toRadians(-90.0 - angle + (360.0 / angleCount) * i)));
        }
        return xCoords;
    }

    private int[] getYCoords(@NotNull Point center) {
        int[] yCoords = new int[angleCount];
        for (int i = 0; i < angleCount; i++) {
            yCoords[i] = (int) (center.y + radius * sin(Math.toRadians(-90.0 - angle + (360.0 / angleCount) * i)));
        }
        return yCoords;
    }

    public int getRadius() {
        return radius;
    }

    public int getAngle() {
        return angle;
    }

    public int getAngleCount() {
        return angleCount;
    }
}
