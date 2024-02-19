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
    private ToolType curToolType = ToolType.PEN;

    @NotNull
    private Color currentColor = Color.BLACK;

    @NotNull
    private final LineTool lineTool = new LineTool();
    @NotNull
    private final FillTool fillTool = new FillTool();
    @NotNull
    private final StarTool starTool = new StarTool();
    @NotNull
    private final PolygonTool polygonTool = new PolygonTool();

    @NotNull
    private final Point startPoint = new Point(-1, -1);

    // TODO: maybe not?
    @NotNull
    private Point prevPoint = new Point(-1, -1);

    // TODO: implement + add size and other changes history
    @NotNull
    private final ChangeHistory changeHistory = new ChangeHistory();

    DrawField() {
        initializeImage();
        addMouseListener(this);
        addMouseMotionListener(this);
        //TODO: добавить что-то в историю
    }

    private void initializeImage() {
        image = new BufferedImage(minWidth, minHeight, BufferedImage.TYPE_INT_RGB);
        g2d = image.createGraphics();
        setWhite();
    }

    private void setWhite() {
        assert g2d != null;
        // TODO: ??? setColor
        g2d.setColor(Color.WHITE);
        g2d.setBackground(Color.WHITE);
        // TODO: очистить экран
        g2d.fillRect(0, 0, minWidth, minHeight);
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO: возможно, добавить логгер
        System.out.println("click action : id=" + e.getID() + ", x=" + e.getX() + ", y=" + e.getY());
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
            default -> {
                return;
            }
        }
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO: сохранить в историю
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
            //TODO: cap roud??? stroke??? join round???
            g2d.setStroke(new BasicStroke(thickness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2d.drawLine(prevPoint.x, prevPoint.y, e.getX(), e.getY());
            prevPoint = e.getPoint();
            repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
