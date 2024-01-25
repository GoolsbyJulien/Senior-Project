package com.jra.api.input;

import com.jra.api.util.Vector;

import java.awt.event.*;

public class Mouse implements MouseWheelListener, MouseListener, MouseMotionListener {

    public static boolean LEFT_CLICK = false;
    public static boolean RIGHT_CLICK = false;

    public static Vector lastClick = new Vector();
    public static Vector mousePos = new Vector();
    public static double wheel = 1;

    @Override
    public void mouseClicked(MouseEvent e) {

        lastClick.x = e.getX();
        lastClick.y = e.getY();
        if (e.getButton() == 1)
            RIGHT_CLICK = true;
        else
            LEFT_CLICK = true;
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.getPreciseWheelRotation() < 0) {
            wheel -= 0.1;
        } else {
            wheel += 0.1;
        }
        if (wheel < 0.01) {
            wheel = 0.01;
        }
    }


    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == 0)
            RIGHT_CLICK = false;
        else
            LEFT_CLICK = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mousePos.x = e.getX();
        mousePos.y = e.getY();
    }
}
