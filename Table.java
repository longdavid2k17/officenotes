import Interfaces.Resources;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Table extends JFrame
{
    int columnCount;
    int rowCount;
    JFrame tableCreator;
    JLabel columnLabel, rowLabel;
    JTextField columnField, rowField;
    JButton okButton, cancelButton;

    public Table(JTextPane pointer)
    {
        tableCreator = new JFrame();
        tableCreator.setTitle("Wstaw tabelę");
        tableCreator.setSize(600, 600);
        tableCreator.setVisible(true);
        tableCreator.setLayout(null);
        tableCreator.setLocationRelativeTo(null);
        tableCreator.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        tableCreator.setIconImage(Resources.imageTable.getImage());

        columnLabel = new JLabel("Ilość kolumn");
        columnLabel.setBounds(10,10,100,50);
        columnLabel.setVisible(true);
        tableCreator.add(columnLabel);

        columnField = new JTextField();
        columnField.setBounds(120,10,100,50);
        columnField.setVisible(true);
        tableCreator.add(columnField);

        rowLabel = new JLabel("Ilość wierszy");
        rowLabel.setBounds(10,60,100,50);
        rowLabel.setVisible(true);
        tableCreator.add(rowLabel);

        rowField = new JTextField();
        rowField.setBounds(120,60,100,50);
        rowField.setVisible(true);
        tableCreator.add(rowField);

        okButton = new JButton("Wstaw");
        okButton.setBounds(350,500,75,50);
        okButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                setColumnCount();
                setRowCount();
                pointer.insertComponent(new JTable(getRowCount(),getColumnCount()));
                tableCreator.dispose();
            }
        });
        tableCreator.add(okButton);

        cancelButton = new JButton("Anuluj");
        cancelButton.setBounds(450,500,75,50);
        cancelButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                tableCreator.dispose();
            }
        });
        tableCreator.add(cancelButton);
    }
    int getColumnCount()
    {
        return columnCount;
    }

    int getRowCount()
    {
        return rowCount;
    }
    void setColumnCount()
    {
                columnCount = Integer.valueOf(columnField.getText());
    }
    void setRowCount()
    {
                rowCount = Integer.valueOf(rowField.getText());
    }
}
