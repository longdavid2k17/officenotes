package Forms;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

/**
 * @author Dawid Kańtoch
 * klasa okna dialogowego Znajdź i zastąp
 */
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

    /**
     * Konstruktor argumentowy pobierający wskaźnik na TextPane
     * @param pointer
     */
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

        mainPanel.registerKeyboardAction(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    /**
     * ustawienie wartości dla pola searchedString
     */
    void setSearchedString()
    {
        if(textField2.getText().length()>0)
        {
            searchedString = textField2.getText();
            System.out.println("Poszukiwana fraza: "+searchedString);
        }
        else
            JOptionPane.showMessageDialog(this,"Błąd wprowadzania danych","Błąd",JOptionPane.ERROR_MESSAGE);
    }

    /**
     * metoda ustawiająca wartość dla pola replacedString
     */
    void setReplacedString()
    {
        if(textField1.getText().length()>0)
        {
            replacedString = textField1.getText();
            System.out.println("Zastąpiona przez : "+replacedString);
        }
        else
            JOptionPane.showMessageDialog(this,"Błąd wprowadzania danych","Błąd",JOptionPane.ERROR_MESSAGE);
    }

    /**
     * metoda ustawiająca wskaźnik na textPane
     * @param val
     */
    private void setTextPane(JTextPane val)
    {
        textPane = val;
    }

    /**
     * reakcja dla anulowania działania okna
     */
    private void onCancel()
    {
        dispose();
    }

    /**
     * reakcja dla potwierdzenia działania okna
     */
    private void onOK() throws BadLocationException
    {
        String text = textPane.getText();
        Scanner input = new Scanner(text).useDelimiter(" ");
        ArrayList<String> lista = new ArrayList<String>();
        while(input.hasNext())
        {
            lista.add(input.next());
        }
        input.close();

        System.out.println(lista);

        StringBuilder sb = new StringBuilder();
        for(int i=0;i<lista.size();i++)
        {
            String searchTempVar = lista.get(i);

            if(searchTempVar.contains(searchedString))
            {
                counter++;
                System.out.println("Indeks słowa : " + i);
                lista.set(i, replacedString);
            }
            sb.append(lista.get(i).toString()+" ");
        }
        textPane.setText(sb.toString());

        JOptionPane.showMessageDialog(this,"Wyraz pojawił się "+counter+" raz/y i tyle samo razy go zamieniono.","Wyniki wyszukiwania",JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }
}
