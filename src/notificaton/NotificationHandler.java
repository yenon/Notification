/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notificaton;

import java.awt.Color;
import javax.swing.JFrame;

/**
 *
 * @author Basti
 */
public class NotificationHandler {

    private static NotificationManager nm;

    public static void initialize() {
        nm = new NotificationManager();
        nm.start();
    }

    public static void addNotification(String title, String text, float time) {
        nm.addNotification(title, text, time, new Color(255, 102, 51), false);
    }

    public static void addErrorNotification(String title, String text, float time) {
        nm.addNotification(title, text, time, new Color(255, 102, 51), true);
    }

    public static void addNotificationFrame(JFrame frame) {
        nm.addFrame(frame);
    }

    public static void addErrorNotification(Exception ex, float time) {
        String exception = null;
        int i = 0;
        StackTraceElement[] ste = ex.getStackTrace();
        while (i < ste.length) {
            if(ste[i].toString()!=null&&!ste[i].isNativeMethod()){
            if (exception == null) {
                exception = ste[i].toString();
            } else {
                exception = exception + "\n" + ste[i].toString();
            }
                System.out.println(i+":"+ste[i].toString());
            }
            
            i++;
        }
        nm.addNotification("",exception, time, Color.RED, true);
    }
}
