package Forms;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FindAndReplace extends JDialog
{
    private JTextField textField1;
    private JTextField textField2;
    private JButton OKButton;
    private JButton anulujButton;
    private JPanel mainPanel;
    private String searchedString, replacedString;
    private JTextPane textPane;
    int counter;

    public FindAndReplace(JTextPane pointer)
    {
        setContentPane(mainPanel);
        setModal(true);
        setSize(300,200);
        setTitle("Znajdź frazę i zastąp");
        setLocationRelativeTo(null);
        getRootPane().setDefaultButton(OKButton);
        setTextPane(pointer);
        OKButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                setSearchedString();
                setReplacedString();
                try
                {
                    onOK();
                }
                catch (BadLocationException e1)
                {
                    e1.printStackTrace();
                }

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
    void setSearchedString()
    {
        if(textField1.getText().length()>0)
        {
            searchedString = textField1.getText();
        }
        else
            JOptionPane.showMessageDialog(this,"Błąd wprowadzania danych","Błąd",JOptionPane.ERROR_MESSAGE);
    }

    void setReplacedString()
    {
        if(textField2.getText().length()>0)
        {
            replacedString = textField2.getText();
        }
        else
            JOptionPane.showMessageDialog(this,"Błąd wprowadzania danych","Błąd",JOptionPane.ERROR_MESSAGE);
    }

    private void setTextPane(JTextPane val)
    {
        textPane = val;
    }

    private void onCancel()
    {
        dispose();
    }

    private void onOK() throws BadLocationException {
        String text = textPane.getText();
        System.out.println(text);

        Scanner input = new Scanner(text).useDelimiter(" ");
        ArrayList<String> lista = new ArrayList<String>();
        while(input.hasNext())
        {
            lista.add(input.next());
        }
        input.close();

        for(String a: lista)
        {
            if(a.contains(searchedString))
            {
                counter++;
                a.replace(searchedString,replacedString);
            }
            a.replace(searchedString,replacedString);
        }
        JOptionPane.showMessageDialog(this,"Wyraz pojawił się "+counter+" raz/y","Wyniki wyszukiwania",JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }
}
