import Interfaces.Resources;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;


public class SplashScreen extends JFrame implements Resources
{
    SplashScreen()
    {
        setSize(500,675);
        setResizable(false);
        setUndecorated(true);
        getContentPane().setBackground(Color.GRAY);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);

        ImageIcon logo = new ImageIcon(SplashScreen.class.getResource("/images/app_icon.png"));
        setIconImage(logo.getImage());
        JLabel logoLabel = new JLabel(logo);
        logoLabel.setBounds(100,50,300,300);
        getContentPane().add(logoLabel);

        JLabel label = new JLabel("OfficeNotes");
        label.setFont(new Font("MyriadPro",Font.BOLD,35));
        label.setBounds(150,375,300,50);
        getContentPane().add(label, CENTER_ALIGNMENT);

        Icon imgIcon = new ImageIcon(SplashScreen.class.getResource("/images/load.gif"));
        JLabel gifLabel = new JLabel(imgIcon);
        gifLabel.setBounds(150,450,200,200);
        getContentPane().add(gifLabel);

        revalidate();
        repaint();
        new java.util.Timer().schedule
                (
                        new java.util.TimerTask()
                        {
                            @Override
                            public void run()
                            {
                                dispose();
                                new TextEditor();
                            }
                        },
                        3500
                );
    }
}
