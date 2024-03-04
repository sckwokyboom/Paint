package ru.nsu.sckwo.model.tools;

import java.awt.*;
import java.awt.image.BufferedImage;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class StarTool {
    private int outerRadius = 50;

    private int innerRadius = 25;

    private int angle = 0;
    private int angleCount = 5;
    public StarTool(int outerRadius, int innerRadius, int angle, int angleCount) {
        this.outerRadius = outerRadius;
        this.innerRadius = innerRadius;
        this.angle = angle;
        this.angleCount = angleCount;
    }

    public StarTool() {

    }

    public void draw(BufferedImage image, Point center, Color color) {
        final Graphics2D g2d = (Graphics2D) image.getGraphics();

        int[] xCoords = new int[angleCount * 2];
        int[] yCoords = new int[angleCount * 2];
        for (int i = 0; i < angleCount * 2; i++) {
            if ((i % 2) == 0) {
                xCoords[i] = (int) (center.x + outerRadius * cos(-Math.PI / 2 + angle / 180.0 * Math.PI + (i * 2 * Math.PI) / (2 * angleCount)));
                yCoords[i] = (int) (center.y + outerRadius * sin(-Math.PI / 2 + angle / 180.0 * Math.PI + (i * 2 * Math.PI) / (2 * angleCount)));
            } else {
                xCoords[i] = (int) (center.x + innerRadius * cos(-Math.PI / 2 + angle / 180.0 * Math.PI + (i * 2 * Math.PI) / (2 * angleCount)));
                yCoords[i] = (int) (center.y + innerRadius * sin(-Math.PI / 2 + angle / 180.0 * Math.PI + (i * 2 * Math.PI) / (2 * angleCount)));
            }
        }
        g2d.setColor(color);
        g2d.drawPolygon(xCoords, yCoords, angleCount * 2);
    }

    public int getOuterRadius() {
        return outerRadius;
    }

    public int getInnerRadius() {
        return innerRadius;
    }

    public int getAngle() {
        return angle;
    }

    public int getAngleCount() {
        return angleCount;
    }
}
