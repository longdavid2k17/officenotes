package Forms;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

public class Find extends JDialog
{
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textField1;
    private JTextPane textPane;
    private int counter=0;

    private String searchedString;

    public Find(JTextPane pointer)
    {
        setContentPane(contentPane);
        setModal(true);
        setSize(300,200);
        setTitle("Znajdź frazę");
        setLocationRelativeTo(null);
        getRootPane().setDefaultButton(buttonOK);

        setTextPane(pointer);

        buttonOK.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                searchedString = textField1.getText();
                try {
                    onOK();
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }
            }
        });

        buttonCancel.addActionListener(new ActionListener()
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

        contentPane.registerKeyboardAction(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void setTextPane(JTextPane val)
    {
        textPane = val;
    }

    private void onOK() throws BadLocationException {
        String text = textPane.getText();
        //System.out.println(text);

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
            }
        }
        Highlighter highlighter = textPane.getHighlighter();
        Highlighter.HighlightPainter painter =
                new DefaultHighlighter.DefaultHighlightPainter(Color.red);
        int p0 = text.indexOf(searchedString);
        int p1 = p0 + searchedString.length();

        JOptionPane.showMessageDialog(this,"Wyraz pojawił się "+counter+" raz/y","Wyniki wyszukiwania",JOptionPane.INFORMATION_MESSAGE);
        dispose();
        highlighter.addHighlight(p0, p1, painter );
    }

    private void onCancel()
    {
        dispose();
    }
    String getString()
    {
        return searchedString;
    }
}
