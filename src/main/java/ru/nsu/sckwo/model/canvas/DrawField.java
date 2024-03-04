package ru.nsu.sckwo.model.canvas;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.nsu.sckwo.model.dialogues.OptionsValues;
import ru.nsu.sckwo.model.tools.*;

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

    @Nullable
    private BufferedImage image = null;
    @Nullable
    private Graphics2D g2d = null;

    @NotNull
    private Color currentColor = Color.BLACK;
    @NotNull
    private ToolType curToolType = ToolType.PEN;
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
    @NotNull
    private Point prevPoint = new Point(-1, -1);
    @NotNull
    private final ChangeHistory changeHistory = new ChangeHistory();

    public DrawField() {
        initializeImage();
        addMouseListener(this);
        addMouseMotionListener(this);
        changeHistory.saveImage(image.getData());
    }

    @NotNull
    public BufferedImage getImage() {
        assert (image != null);
        return image;
    }

    public void setCurrentColor(@NotNull Color currentColor) {
        this.currentColor = currentColor;
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

            case ToolType.ERASER -> {
                prevPoint = e.getPoint();
                assert g2d != null;
                g2d.setColor(Color.WHITE);
                g2d.fillOval(e.getX() - thickness / 2, e.getY() - thickness / 2, thickness, thickness);
            }

            case ToolType.FILL -> {
                assert (image != null);
                fillTool.fill(image, e.getPoint(), currentColor);
            }

            case ToolType.LINE -> {
                assert (image != null);
                if (startPoint == null) {
                    startPoint = e.getPoint();
                } else {
                    lineTool.drawLine(image, thickness, currentColor, startPoint, e.getPoint());
                    startPoint = null;
                }

            }

            case ToolType.POLYGON -> {
                assert (image != null);
                polygonTool.draw(image, e.getPoint(), currentColor);
            }

            case ToolType.STAR -> {
                assert (image != null);
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
        if (curToolType != ToolType.LINE || startPoint == null) {
            changeHistory.saveImage(image.getData());
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        prevPoint = e.getPoint();
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
            g2d.setStroke(new BasicStroke(thickness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2d.drawLine(prevPoint.x, prevPoint.y, e.getX(), e.getY());
            prevPoint = e.getPoint();
            repaint();
        }

        if (curToolType == ToolType.ERASER) {
            assert g2d != null;
            g2d.setColor(Color.WHITE);
            g2d.fillOval(e.getX() - thickness / 2, e.getY() - thickness / 2, thickness, thickness);
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
        changeHistory.saveImage(image.getData());
    }

    public void resizeImage(int newWidth, int newHeight) {
        minWidth = newWidth;
        minHeight = newHeight;
        setPreferredSize(new Dimension(newWidth, newHeight));
        setSize(new Dimension(newWidth, newHeight));
        final BufferedImage newImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        this.g2d = newImage.createGraphics();
        clearAll();
        assert (image != null);
        newImage.setData(image.getData());
        image = newImage;

        repaint();
    }

    public void resetAllToolsStates() {
        startPoint = null;
    }

    public void undo() {
        final Raster lastSave = changeHistory.getLastSave();
        if (lastSave != null) {
            clearAll();
            assert (image != null);
            image.setData(lastSave);
            repaint();
        }
    }

    public void setFiguresParameters(int angle, int numOfVertices, int outerRadius, int innerRadius) {
        polygonTool = new PolygonTool(outerRadius, angle, numOfVertices);
        starTool = new StarTool(outerRadius, innerRadius, angle, numOfVertices);
    }

    @NotNull
    public OptionsValues getCurrentOptionsValues() {
        return new OptionsValues(
                thickness,
                polygonTool.getAngleCount(),
                polygonTool.getAngle(),
                starTool.getOuterRadius(),
                starTool.getInnerRadius()
        );
    }
}
