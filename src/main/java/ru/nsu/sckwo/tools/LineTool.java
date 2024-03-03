package ru.nsu.sckwo.tools;

import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.image.BufferedImage;

import static java.lang.Math.abs;

public class LineTool {
    public void drawLine(@NotNull BufferedImage image, int thickness, @NotNull Color currentColor, @NotNull Point start, @NotNull Point end) {
        final Graphics2D g2d = (Graphics2D) image.getGraphics();
        final int colorRGB = currentColor.getRGB();

        if (thickness > 1) {
            g2d.setColor(currentColor);
            g2d.setStroke(new BasicStroke(thickness));
            g2d.drawLine(start.x, start.y, end.x, end.y);
            return;
        }

        if (thickness == 1) {
            image.setRGB(start.x, start.y, colorRGB);
            final int dx = abs(end.x - start.x);
            final int dy = abs(end.y - start.y);
            int err = -dx;
            final double diffX = end.x - start.x;
            final double diffY = end.y - start.y;
            int x = start.x;
            int y = start.y;

            if (dx >= dy) {
                if (diffX >= 0 && diffY >= 0) {
                    for (int i = 0; i < dx; i++) {
                        x++;
                        err += 2 * dy;
                        if (err > 0) {
                            y++;
                            err -= 2 * dx;
                        }
                        image.setRGB(x, y, colorRGB);
                    }
                    return;
                }

                if (diffX >= 0 && diffY <= 0) {
                    for (int i = 0; i < dx; i++) {
                        x++;
                        err += 2 * dy;
                        if (err > 0) {
                            y--;
                            err -= 2 * dx;
                        }
                        image.setRGB(x, y, colorRGB);
                    }
                    return;
                }

                if (diffX <= 0 && diffY >= 0) {
                    for (int i = 0; i < dx; i++) {
                        x--;
                        err += 2 * dy;
                        if (err > 0) {
                            y++;
                            err -= 2 * dx;
                        }
                        image.setRGB(x, y, colorRGB);
                    }
                    return;
                }

                if (diffX <= 0 && diffY <= 0) {
                    for (int i = 0; i < dx; i++) {
                        x--;
                        err += 2 * dy;
                        if (err > 0) {
                            y--;
                            err -= 2 * dx;
                        }
                        image.setRGB(x, y, colorRGB);
                    }
                    return;
                }
            }

            if (dx < dy) {
                if (diffX >= 0 && diffY >= 0) {
                    for (int i = 0; i < dy; i++) {
                        y++;
                        err += 2 * dx;
                        if (err > 0) {
                            x++;
                            err -= 2 * dy;
                        }
                        image.setRGB(x, y, colorRGB);
                    }
                    return;

                }

                if (diffX >= 0 && diffY <= 0) {
                    for (int i = 0; i < dy; i++) {
                        y--;
                        err += 2 * dx;
                        if (err > 0) {
                            x++;
                            err -= 2 * dy;
                        }
                        image.setRGB(x, y, colorRGB);
                    }
                    return;
                }

                if (diffX <= 0 && diffY >= 0) {
                    for (int i = 0; i < dy; i++) {
                        y++;
                        err += 2 * dx;
                        if (err > 0) {
                            x--;
                            err -= 2 * dy;
                        }
                        image.setRGB(x, y, colorRGB);
                    }
                    return;
                }

                if (diffX <= 0 && diffY <= 0) {
                    for (int i = 0; i < dy; i++) {
                        y--;
                        err += 2 * dx;
                        if (err > 0) {
                            x--;
                            err -= 2 * dy;
                        }
                        image.setRGB(x, y, colorRGB);
                    }
                    return;
                }
            }
        }

    }
}
