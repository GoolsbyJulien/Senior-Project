package com.jra.api.input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Keyboard extends KeyAdapter {

    public static boolean W = false;
    public static boolean A = false;

    public static boolean S = false;
    public static boolean D = false;
    public static boolean F3 = false;
    public static boolean UP = false;
    public static boolean DOWN = false;
    public static boolean E = false;
    public static boolean ENTER = false;
    public static boolean F = false;

    public static boolean RIGHT = false;

    public static boolean LEFT = false;


    @Override
    public void keyPressed(KeyEvent keyEvent) {


        if (keyEvent.getKeyCode() == keyEvent.VK_W) {
            W = true;
        } else if (keyEvent.getKeyCode() == keyEvent.VK_ENTER) {
            ENTER = true;
        } else if (keyEvent.getKeyCode() == keyEvent.VK_F) {
            F = true;
        } else if (keyEvent.getKeyCode() == keyEvent.VK_S) {
            S = true;
        } else if (keyEvent.getKeyCode() == keyEvent.VK_UP) {
            UP = true;
        } else if (keyEvent.getKeyCode() == keyEvent.VK_DOWN) {
            DOWN = true;

        } else if (keyEvent.getKeyCode() == keyEvent.VK_E) {
            E = true;

        } else if (keyEvent.getKeyCode() == keyEvent.VK_D) {
            D = true;

        } else if (keyEvent.getKeyCode() == keyEvent.VK_A) {
            A = true;

        } else if (keyEvent.getKeyCode() == KeyEvent.VK_F3)
            F3 = true;
        else if (keyEvent.getKeyCode() == KeyEvent.VK_RIGHT)
            RIGHT = true;
        else if (keyEvent.getKeyCode() == KeyEvent.VK_LEFT)
            LEFT = true;

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == keyEvent.VK_W) {
            W = false;
        } else if (keyEvent.getKeyCode() == keyEvent.VK_S) {
            S = false;
        } else if (keyEvent.getKeyCode() == keyEvent.VK_F) {
            F = false;
        } else if (keyEvent.getKeyCode() == keyEvent.VK_UP) {
            UP = false;
        } else if (keyEvent.getKeyCode() == keyEvent.VK_DOWN) {
            DOWN = false;

        } else if (keyEvent.getKeyCode() == keyEvent.VK_LEFT) {
            LEFT = false;
        } else if (keyEvent.getKeyCode() == keyEvent.VK_RIGHT) {
            RIGHT = false;

        } else if (keyEvent.getKeyCode() == keyEvent.VK_E) {
            E = false;

        } else if (keyEvent.getKeyCode() == keyEvent.VK_ENTER) {
            ENTER = false;

        } else if (keyEvent.getKeyCode() == keyEvent.VK_D) {
            D = false;

        } else if (keyEvent.getKeyCode() == keyEvent.VK_A) {
            A = false;

        } else if (keyEvent.getKeyCode() == keyEvent.VK_F3) {
            F3 = false;

        }

    }


}
