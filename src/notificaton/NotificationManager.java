/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notificaton;

import java.awt.Color;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author Basti
 */
public class NotificationManager extends Thread {

    private Deque<JFrame> frames = new ArrayDeque();
    private int width, height, currentHeight;

    public NotificationManager() {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        width = gd.getDisplayMode().getWidth();
        height = gd.getDisplayMode().getHeight();
        currentHeight = height - 32;
    }

    public void addNotification(String title, String text, float time, Color c, boolean alert) {
        if (alert) {
            Toolkit.getDefaultToolkit().beep();
        }
        NotificationFrame nf = new NotificationFrame(title, Color.black, text, Color.black, time, c);
        nf.open();
        nf.setLocation(width - nf.getWidth(), currentHeight);
        currentHeight = currentHeight - nf.getHeight();
        frames.add(nf);
        synchronized (this) {
            this.notify();
        }
    }

    public void addFrame(JFrame frame) {
        frame.setVisible(false);
        frame.setUndecorated(true);
        frame.setVisible(true);
        frames.add(frame);
    }

    public void refresh() {
        synchronized (this) {
            this.notify();
        }
    }

    @Override
    public void run() {
        while (true) {

            synchronized (this) {
                try {
                    this.wait(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(NotificationManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            Iterator<JFrame> it = frames.descendingIterator();
            currentHeight = height - 32;
            while (it.hasNext()) {
                JFrame current = it.next();
                if (current != null) {
                    if (current.isVisible()) {
                        currentHeight = currentHeight - current.getHeight();
                        current.setLocation(width - current.getWidth() - 32, currentHeight);
                        currentHeight = currentHeight - 16;
                    } else {
                        current.dispose();
                        it.remove();
                    }
                } else {
                    it.remove();
                }
            }
        }
    }
}
