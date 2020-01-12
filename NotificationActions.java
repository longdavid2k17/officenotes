import java.awt.*;

/**
 * klasa obsługująca powiadomienia w systemie
 */
public class NotificationActions
{
    /**
     * metoda wyświetlająca powiadomienie w systemie
     * @param text
     *          przyjmujący tekst do wyświetlenia
     * @throws AWTException
     */
    void showNotification(String text) throws AWTException
    {
        SystemTray tray = SystemTray.getSystemTray();

        Image image = Toolkit.getDefaultToolkit().createImage(getClass().getResource("images/app_icon.png"));

        TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
        trayIcon.setImageAutoSize(true);
        trayIcon.setToolTip("OfficeNotes Assistent");
        tray.add(trayIcon);

        trayIcon.displayMessage("OfficeNotes", text, TrayIcon.MessageType.INFO);
    }
}
