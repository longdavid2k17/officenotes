import javax.swing.*;
import java.awt.event.*;

public class FindAndReplace extends JDialog
{
    private JTextField textField1;
    private JTextField textField2;
    private JButton OKButton;
    private JButton anulujButton;
    private JPanel mainPanel;

    public FindAndReplace()
    {
        setContentPane(mainPanel);
        setModal(true);
        setSize(300,200);
        setTitle("Znajdź frazę");
        setLocationRelativeTo(null);
        getRootPane().setDefaultButton(OKButton);

        OKButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                onOK();
            }
        });

        anulujButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                onCancel();
            }
        });

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        mainPanel.registerKeyboardAction(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }
    private void onCancel()
    {
        dispose();
    }

    private void onOK()
    {
        // add your code here
        dispose();
    }
}
