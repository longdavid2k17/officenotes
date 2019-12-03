package Charts;

import javax.swing.*;
import java.awt.geom.RoundRectangle2D;
import Interfaces.Resources;

public class ChartsEditor extends JFrame implements Resources
{
    JFrame chartsFrame;

    public ChartsEditor()
    {
        createForm();
    }

    void createForm()
    {
        chartsFrame = new JFrame();
        chartsFrame.setTitle("Wstaw wykres");
        //chartsFrame.setUndecorated(true);
        //chartsFrame.setShape(new RoundRectangle2D.Double(100, 100, 400, 400, 60, 60));
        chartsFrame.setSize(600, 600);
        chartsFrame.setVisible(true);
        chartsFrame.setLocationRelativeTo(null);
        chartsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        chartsFrame.setIconImage(Resources.areaChart.getImage());
    }
}
