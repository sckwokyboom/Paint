package ru.nsu.sckwo;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.nsu.sckwo.tools.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;

public class DrawField extends JPanel implements MouseListener, MouseMotionListener {
    private static final int DEFAULT_MIN_WIDTH = 640;
    private static final int DEFAULT_MIN_HEIGHT = 480;
    private static final int DEFAULT_THICKNESS = 5;

    private int minWidth = DEFAULT_MIN_WIDTH;
    private int minHeight = DEFAULT_MIN_HEIGHT;

    private int thickness = DEFAULT_THICKNESS;

    @NotNull
    public BufferedImage getImage() {
        assert (image != null);
        return image;
    }

    @Nullable
    private BufferedImage image = null;

    @Nullable
    private Graphics2D g2d = null;
    @NotNull
    private ToolType curToolType = ToolType.PEN;

    public void setCurrentColor(@NotNull Color currentColor) {
        this.currentColor = currentColor;
    }

    @NotNull
    private Color currentColor = Color.BLACK;

    @NotNull
    private final LineTool lineTool = new LineTool();

    @NotNull
    private final FillTool fillTool = new FillTool();
    @NotNull
    private StarTool starTool = new StarTool();
    @NotNull
    private PolygonTool polygonTool = new PolygonTool();
    @Nullable
    private Point startPoint = null;

    // TODO: maybe not?

    @NotNull
    private Point prevPoint = new Point(-1, -1);

    @NotNull
    private final ChangeHistory changeHistory = new ChangeHistory();

    DrawField() {
        initializeImage();
        addMouseListener(this);
        addMouseMotionListener(this);
        changeHistory.saveImage(image.getData());
    }

    private void initializeImage() {
        image = new BufferedImage(minWidth, minHeight, BufferedImage.TYPE_INT_RGB);
        g2d = image.createGraphics();
        clearAll();
    }

    public void clearAll() {
        assert g2d != null;
        g2d.setColor(Color.WHITE);
        g2d.setBackground(Color.WHITE);
        g2d.fillRect(0, 0, minWidth, minHeight);
        repaint();
    }

    public void selectTool(@NotNull ToolType selectedToolType) {
        curToolType = selectedToolType;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO: возможно, добавить логгер
        System.out.println("click actionListener : id=" + e.getID() + ", x=" + e.getX() + ", y=" + e.getY());
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getX() > minWidth || e.getY() > minHeight) return;

        switch (curToolType) {
            case ToolType.PEN -> {
                prevPoint = e.getPoint();
                assert g2d != null;
                g2d.setColor(currentColor);
                g2d.fillOval(e.getX() - thickness / 2, e.getY() - thickness / 2, thickness, thickness);
            }
            case ToolType.FILL -> {
                assert (image != null);
                // TODO: exception???
                fillTool.fill(image, e.getPoint(), currentColor);
            }
            case ToolType.LINE -> {
                assert (image != null);
                // TODO: exception???
                if (startPoint == null) {
                    startPoint = e.getPoint();
                } else {
                    lineTool.drawLine(image, thickness, currentColor, startPoint, e.getPoint());
                    startPoint = null;
                }

            }

            case ToolType.POLYGON -> {
                assert (image != null);
                // TODO: exception???
                polygonTool.draw(image, e.getPoint(), currentColor);
            }

            case ToolType.STAR -> {
                assert (image != null);
                // TODO: exception???
                starTool.draw(image, e.getPoint(), currentColor);
            }
            default -> {
                return;
            }
        }
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        assert (image != null);
        //TODO: exception?
        if (curToolType != ToolType.LINE || startPoint == null) {
            changeHistory.saveImage(image.getData());
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.getX() > minWidth || e.getY() > minHeight) return;

        if (curToolType == ToolType.PEN) {
            assert g2d != null;
            g2d.setColor(currentColor);
            g2d.fillOval(e.getX() - thickness / 2, e.getY() - thickness / 2, thickness, thickness);
            //TODO: cap round??? stroke??? join round???
            g2d.setStroke(new BasicStroke(thickness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2d.drawLine(prevPoint.x, prevPoint.y, e.getX(), e.getY());
            prevPoint = e.getPoint();
            repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    public void setThickness(int thickness) {
        this.thickness = thickness;
    }

    public void setImage(@NotNull BufferedImage newImage) {
        image = newImage;
    }

    public void resizeImage(int newWidth, int newHeight) {
        minWidth = newWidth;
        minHeight = newHeight;
        setPreferredSize(new Dimension(newWidth, newHeight));

        //TODO: read about buffered image and raster
        final BufferedImage newImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        this.g2d = newImage.createGraphics();
        clearAll();
        // TODO: think about it
        assert (image != null);
        newImage.setData(image.getData());
        image = newImage;

        repaint();
    }

    public void resetAllToolsStates() {
        //TODO: maybe rename???
        startPoint = null;
    }

    public void undo() {
        final Raster lastSave = changeHistory.getLastSave();
        if (lastSave != null) {
            clearAll();
            assert (image != null);
            //TODO: exception?
            image.setData(lastSave);
            repaint();
        }
    }

    public void setFiguresParameters(int angle, int numOfVertices, int outerRadius, int innerRadius) {
        polygonTool = new PolygonTool(outerRadius, angle, numOfVertices);
        starTool = new StarTool(outerRadius, innerRadius, angle, numOfVertices);
    }
}
