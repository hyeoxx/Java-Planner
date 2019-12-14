/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contents;

import java.awt.*;
import java.awt.TrayIcon.MessageType;

/**
 *
 * @author hyeoxx
 */
public class Notification {
    
    public Notification() {
        
    }
    
    public void displayTray(String caption, String text) {
        //Obtain only one instance of the SystemTray object
        if (SystemTray.isSupported()) {
            try {
                SystemTray tray = SystemTray.getSystemTray();

        //If the icon is a file
                Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
        //Alternative (if the icon is on the classpath):
        //Image image = Toolkit.getDefaultToolkit().createImage(getClass().getResource("icon.png"));

                TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
        //Let the system resize the image if needed
                trayIcon.setImageAutoSize(true);
        //Set tooltip text for the tray icon
                trayIcon.setToolTip("System tray icon demo");
                tray.add(trayIcon);

                trayIcon.displayMessage(caption, text, MessageType.INFO);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("System tray not supported!");
        }
    }
}
