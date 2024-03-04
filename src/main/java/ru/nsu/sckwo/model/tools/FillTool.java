package ru.nsu.sckwo.model.tools;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayDeque;
import java.util.Deque;

public class FillTool {
    @NotNull
    private final Deque<@NotNull Span> spanStack = new ArrayDeque<>();
    @Nullable
    private BufferedImage image = null;
    @Nullable
    private Graphics2D g2d = null;
    private int newColor = 0;
    private int oldColor = 0;
    private int maxUpX = 0;

    private int maxDownX = 0;

    private void addNewSpan(@NotNull Point seed) {
        final Point newSpanStart = findSpanStart(seed);
        final Point newSpanEnd = findSpanEnd(seed);
        spanStack.push(new Span(newSpanStart, newSpanEnd));
    }

    @NotNull
    private Point findSpanStart(@NotNull Point seed) {
        final Point newSpanStart = new Point(seed);
        if (image == null) {
            throw new IllegalStateException("Null image.");
        }
        while (newSpanStart.x >= 0 && image.getRGB(newSpanStart.x, newSpanStart.y) == oldColor) {
            newSpanStart.x--;
        }
        newSpanStart.x++;
        return newSpanStart;
    }

    @NotNull
    private Point findSpanEnd(@NotNull Point seed) {
        final Point newSpanEnd = new Point(seed);
        if (image == null) {
            throw new IllegalStateException("Null image.");
        }
        while (newSpanEnd.x < image.getWidth() && image.getRGB(newSpanEnd.x, newSpanEnd.y) == oldColor) {
            newSpanEnd.x++;
        }
        newSpanEnd.x--;
        return newSpanEnd;
    }

    private void findNewSpan(@NotNull Point point) {
        if (image == null) {
            throw new IllegalStateException("Null image");
        }
        if ((point.y - 1) >= 0 && (point.y + 1) < image.getHeight()) {
            if (point.x > maxUpX && image.getRGB(point.x, point.y + 1) == oldColor) {
                addNewSpan(new Point(point.x, point.y + 1));
                maxUpX = spanStack.peek().spanEnd().x;
            }
            if (point.x > maxDownX && image.getRGB(point.x, point.y - 1) == oldColor) {
                addNewSpan(new Point(point.x, point.y - 1));
                maxDownX = spanStack.peek().spanEnd().x;
            }
        }
    }

    private void startFilling() {
        final Span curSpan = spanStack.pop();
        if (g2d == null) {
            throw new IllegalStateException("Null image");
        }
        g2d.setColor(new Color(newColor));
        g2d.drawLine(curSpan.spanStart().x, curSpan.spanStart().y, curSpan.spanEnd().x, curSpan.spanEnd().y);

        for (int x = curSpan.spanStart().x; x < curSpan.spanEnd().x; x++) {
            findNewSpan(new Point(x, curSpan.spanStart().y));
        }

        maxUpX = 0;
        maxDownX = 0;
    }

    public void fill(@NotNull BufferedImage image, @NotNull Point seed, @NotNull Color fillColor) {
        this.image = image;
        this.g2d = (Graphics2D) image.getGraphics();
        this.newColor = fillColor.getRGB();
        this.oldColor = image.getRGB(seed.x, seed.y);

        if (oldColor != newColor) {
            addNewSpan(seed);
            while (!spanStack.isEmpty()) {
                startFilling();
            }
        }
    }

}

record Span(
        @NotNull Point spanStart,
        @NotNull Point spanEnd
) {
}
