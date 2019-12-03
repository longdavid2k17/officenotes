import Charts.ChartsEditor;
import Interfaces.Resources;

import javax.swing.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Vector;

public class TextEditor extends JFrame implements ActionListener, Resources
{
    File actualFile = null;

    private JFrame frame;
    private  JFileChooser fileOpener;
    private  JPanel panelUp, mainPanel;
    private  JButton boldButton, cursiveButton, underlineButton, fontColorButton, alignLeftButton, alignCenterButton, allignRightButton, justifyButton;
    private  JButton newButton, openButton, saveButton, printButton, insertImageButton;
    private JScrollPane scrollPanel;
    private JTextPane textPane;
    private JComboBox fontSizes, fontFamilyCmbBox;
    private String fileName;
    private boolean isBold = false;
    JMenu fileMenu, editMenu, insertMenu, helpMenu;
    JMenuBar menuBar;
    protected UndoManager undoManager;
    static ArrayList<String> imageAdressArray;
    static ArrayList<String> imageNameArray;
    private int imageCount=0;

    TextEditor()
    {
        createUI();
    }
    TextEditor(File file)
    {
        createUI();
        actualFile = file;
        try
        {
            FileReader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);
            textPane.read(bufferedReader,null);
            bufferedReader.close();
            textPane.requestFocus();
        }
        catch (Exception error)
        {
            JOptionPane.showMessageDialog(null,error);
        }
    }

    private void createUI()
    {
        EditorActions actions = new EditorActions();

        frame = new JFrame();
        frame.setTitle("OfficeNotes");
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Resources.screenSize.width,Resources.screenSize.height-30);
        frame.validate();
        frame.setBackground(Color.lightGray);
        frame.setLocationRelativeTo(null);
        frame.setIconImage(Resources.mainIcon.getImage());

        textPane = new JTextPane();
        textPane.setBounds(300,0,500,800);
        scrollPanel = new JScrollPane(textPane);
        scrollPanel.setPreferredSize(new Dimension(1300,800));

        SimpleAttributeSet attr = new SimpleAttributeSet();
        StyleConstants.setFontSize(attr,Resources.DEFAULT_FONT_SIZE);
        textPane.setCharacterAttributes(attr, false);

        frame.getContentPane().add(scrollPanel,BorderLayout.CENTER);

        undoManager = new UndoManager();

        textPane.getDocument().addUndoableEditListener(new UndoableEditListener() {
            @Override
            public void undoableEditHappened(UndoableEditEvent e) {
                undoManager.addEdit(e.getEdit());
            }
        });

        menuBar = new JMenuBar();
        menuBar.setBackground(new Color(40,122,252));

        fileMenu = new JMenu("Plik");
        fileMenu.setMnemonic(KeyEvent.VK_F);

        JMenuItem newItem = new JMenuItem("Nowy");
        KeyStroke quickNew = KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK);
        newItem.setAccelerator(quickNew);
        newItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ev)
            {
                TextEditor newEditor = new TextEditor();
                newEditor.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                newEditor.frame.setTitle("OfficeNotes - Nowy");
                try
                {
                    new NotificationActions().showNotification("Utworzono nowy plik");
                }

                catch (AWTException e)
                {
                    e.printStackTrace();
                }
            }
        });
        JMenuItem openItem = new JMenuItem("Otwórz");
        KeyStroke quickOpen = KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK);
        openItem.setAccelerator(quickOpen);
        openItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ev)
            {
                fileOpener = new JFileChooser();
                FileNameExtensionFilter fileExtensionFilter = new FileNameExtensionFilter("Pliki tekstowe", "txt","doc","docs","odt","wpd");
                fileOpener.addChoosableFileFilter(fileExtensionFilter);
                fileOpener.setDialogTitle("Wybierz plik do otwarcia");
                int returnValue = fileOpener.showOpenDialog(null);
                if(returnValue == JFileChooser.APPROVE_OPTION)
                {
                    //fileName=fileOpener.getSelectedFile().getAbsolutePath();
                    new TextEditor(fileOpener.getSelectedFile()).setTitle(fileOpener.getSelectedFile().getName());
                }
            }
        });
        JMenuItem lastFilesItem = new JMenuItem("Ostatnio otwierane");
        lastFilesItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ev)
            {

            }
        });
        JMenuItem saveItem = new JMenuItem("Zapisz");
        saveItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ev)
            {
                if(actualFile!=null)
                {
                    ////here to quick save
                }
                else return;
            }
        });
        ///KeyStroke quickSave = KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK);

        JMenuItem saveAsItem = new JMenuItem("Zapisz jako");
        saveAsItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ev)
            {
                actions.save(frame,textPane);
            }
        });
        KeyStroke normalSave = KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK);
        saveAsItem.setAccelerator(normalSave);

        JMenuItem exportAsPDF = new JMenuItem("Zapisz jako PDF");
        exportAsPDF.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ev)
            {
                if(textPane.getText().length()>0)
                {
                    actions.saveAsPDF(frame, textPane);
                }
                else
                {
                    JOptionPane.showMessageDialog(frame,"Dokument jest pusty i nie może być zapisany!","Błąd zapisu",JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        JMenuItem printItem = new JMenuItem("Drukuj...");
        KeyStroke quickPrint = KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_DOWN_MASK);
        printItem.setAccelerator(quickPrint);
        printItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ev)
            {
                try
                {
                    textPane.print();
                }
                catch (PrinterException e)
                {
                    e.printStackTrace();
                }
            }
        });
        JMenuItem exitItem = new JMenuItem("Zamknij");
        exitItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ev)
            {
                System.exit(0);
            }
        });

        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(lastFilesItem);
        fileMenu.addSeparator();
        fileMenu.add(saveItem);
        fileMenu.add(saveAsItem);
        fileMenu.add(exportAsPDF);
        fileMenu.addSeparator();
        fileMenu.add(printItem);
        fileMenu.add(exitItem);

        editMenu = new JMenu("Edycja");

        JMenuItem undoItem = new JMenuItem("Cofnij");
        KeyStroke quickUndo = KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK);
        undoItem.setAccelerator(quickUndo);
        undoItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ev)
            {
                try
                {
                    undoManager.undo();
                }
                catch (CannotRedoException cre)
                {
                    cre.printStackTrace();
                    //undoItem.setEnabled(false);
                }
            }
        });
        JMenuItem redoItem = new JMenuItem("Ponów");
        KeyStroke quickRedo = KeyStroke.getKeyStroke(KeyEvent.VK_Y, KeyEvent.CTRL_DOWN_MASK);
        redoItem.setAccelerator(quickRedo);
        redoItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ev)
            {
                try
                {
                    undoManager.redo();
                }
                catch (CannotRedoException cre)
                {
                    cre.printStackTrace();
                    //redoItem.setEnabled(false);
                }
            }
        });

        JMenuItem cutItem = new JMenuItem("Wytnij");
        cutItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ev)
            {
                textPane.cut();
            }
        });
        JMenuItem copyItem = new JMenuItem("Kopiuj");
        copyItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ev)
            {
                textPane.copy();
            }
        });
        JMenuItem pasteItem = new JMenuItem("Wklej");
        pasteItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ev)
            {
                textPane.paste();
            }
        });
        JMenuItem markAllItem = new JMenuItem("Zaznacz wszystko");
        markAllItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ev)
            {
                textPane.selectAll();
            }
        });
        JMenuItem findItem = new JMenuItem("Znajdź");
        findItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ev)
            {

                Find simpleFindAction = new Find();
                simpleFindAction.show();
                int tempTextLenght = textPane.getText().length();
                String textCopied = textPane.getText();
                String searchedFrase = simpleFindAction.getString();

                /*
                for(int i=0;i<tempTextLenght;i++)
                {
                    if(textCopied[i]==searchedFrase[0])
                }
                */
                //JOptionPane.showMessageDialog(frame,"Poszukiwana fraza: "+simpleFindAction.getString(),"Fraza",JOptionPane.INFORMATION_MESSAGE);
            }
        });
        JMenuItem findAndReplaceItem = new JMenuItem("Znajdź i zastąp");
        findAndReplaceItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ev)
            {
                FindAndReplace findAndReplaceAction = new FindAndReplace();
                findAndReplaceAction.show();
            }
        });

        editMenu.add(undoItem);
        editMenu.add(redoItem);
        editMenu.addSeparator();
        editMenu.add(cutItem);
        editMenu.add(copyItem);
        editMenu.add(pasteItem);
        editMenu.addSeparator();
        editMenu.add(markAllItem);
        editMenu.add(findItem);
        editMenu.add(findAndReplaceItem);

        insertMenu = new JMenu("Wstaw");

        JMenuItem imageItem = new JMenuItem("Obraz");
        imageItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                actions.drawImage(frame,textPane);
                imageCount++;
                //System.out.println(imageCount);
            }
        });
        JMenuItem graphItem = new JMenuItem("Wykres");
        graphItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                new ChartsEditor();
            }
        });
        JMenuItem specialCharItem = new JMenuItem("Znak specjalny");
        specialCharItem.addActionListener(this);

        JMenuItem tableItem = new JMenuItem("Tabelka");
        tableItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                new Table(textPane);
            }
        });

        insertMenu.add(imageItem);
        insertMenu.add(graphItem);
        insertMenu.add(tableItem);
        insertMenu.add(specialCharItem);

        helpMenu = new JMenu("Pomoc");

        JMenuItem howToUseItem = new JMenuItem("Obsługa programu");
        howToUseItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JOptionPane.showMessageDialog(frame,Resources.howToUseText, "Jak używać programu OfficeNotes",JOptionPane.INFORMATION_MESSAGE);
            }
        });
        JMenuItem aboutAuthorItem = new JMenuItem("O autorze");
        aboutAuthorItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JOptionPane.showMessageDialog(frame,Resources.authorText,"Autor programu OfficeNotes",JOptionPane.INFORMATION_MESSAGE);
            }
        });

        helpMenu.add(howToUseItem);
        helpMenu.add(aboutAuthorItem);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(insertMenu);
        menuBar.add(helpMenu);

        frame.setJMenuBar(menuBar);

        Vector<String> editorFonts = getEditorFonts();
        editorFonts.add(0,"Czcionka");
        fontFamilyCmbBox = new JComboBox<String>(editorFonts);
        fontFamilyCmbBox.setEditable(false);
        fontFamilyCmbBox.addItemListener(new FontFamilyItemListener());

        fontSizes = new JComboBox<String>(Resources.FONT_SIZES);
        fontSizes.setSelectedIndex(3);
        fontSizes.setEditable(false);
        fontSizes.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                SimpleAttributeSet attr = new SimpleAttributeSet();
                StyleConstants.setFontSize(attr,fontSizes.getSelectedIndex()+7);
                textPane.setCharacterAttributes(attr,false);
                textPane.requestFocusInWindow();
            }
        });

        panelUp = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelUp.setBackground(new Color(110,160,240));

        newButton = new JButton(Resources.imageNew);
        newButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                TextEditor newEditor = new TextEditor();
                newEditor.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                newEditor.frame.setTitle("OfficeNotes - Nowy");

                try
                {
                    new NotificationActions().showNotification("Utworzono nowy plik");
                }

                catch (AWTException e)
                {
                    e.printStackTrace();
                }
            }
        });

        openButton = new JButton(Resources.imageOpen);
        openButton.addActionListener(this);

        saveButton = new JButton(Resources.imageSave);
        saveButton.addActionListener(this);

        printButton = new JButton(Resources.imagePrint);
        printButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    textPane.print();
                }
                catch (PrinterException e1)
                {
                    e1.printStackTrace();
                }
            }
        });

        insertImageButton = new JButton(Resources.imageInsertImage);
        insertImageButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                actions.drawImage(frame,textPane);
            }
        });

        panelUp.add(newButton);
        panelUp.add(openButton);
        panelUp.add(saveButton);
        panelUp.add(printButton);
        panelUp.add(new JSeparator(SwingConstants.VERTICAL));
        panelUp.add(insertImageButton);

        boldButton = new JButton(Resources.boldImage);
        boldButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                if(isBold==false)
                {
                    StyleConstants.setBold(attr,true);
                    textPane.setParagraphAttributes(attr,true);
                    isBold=true;
                }
                if(isBold==true)
                {
                    StyleConstants.setBold(attr,false);
                    textPane.setParagraphAttributes(attr,true);
                    isBold=false;
                }
            }
        });

        cursiveButton = new JButton(Resources.italicImage);
        cursiveButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                StyleConstants.setItalic(attr,true);
                textPane.setParagraphAttributes(attr,true);
            }
        });

        underlineButton = new JButton(Resources.underlineImage);
        underlineButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {

            }
        });

        fontColorButton = new JButton();
        fontColorButton.setText("Kolor");
        fontColorButton.addActionListener(this);

        alignLeftButton = new JButton(Resources.alignLeftImage);
        alignLeftButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                StyleConstants.setAlignment(attr , StyleConstants.ALIGN_LEFT);
                textPane.setParagraphAttributes(attr,true);
            }
        });

        alignCenterButton = new JButton(Resources.alignCenterImage);
        alignCenterButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                StyleConstants.setAlignment(attr , StyleConstants.ALIGN_CENTER);
                textPane.setParagraphAttributes(attr,true);
            }
        });

        justifyButton = new JButton(Resources.jutifyImage);
        justifyButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                StyleConstants.setAlignment(attr , StyleConstants.ALIGN_JUSTIFIED);
                textPane.setParagraphAttributes(attr,true);
            }
        });

        allignRightButton = new JButton(Resources.alignRightImage);
        allignRightButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                StyleConstants.setAlignment(attr , StyleConstants.ALIGN_RIGHT);
                textPane.setParagraphAttributes(attr,true);
            }
        });

        panelUp.add(new JSeparator(SwingConstants.CENTER));
        panelUp.add(fontFamilyCmbBox);
        panelUp.add(new JSeparator(SwingConstants.VERTICAL));
        panelUp.add(fontSizes);
        panelUp.add(new JSeparator(SwingConstants.VERTICAL));
        panelUp.add(boldButton);
        panelUp.add(cursiveButton);
        panelUp.add(underlineButton);
        panelUp.add(new JSeparator(SwingConstants.VERTICAL));
        panelUp.add(fontColorButton);
        panelUp.add(new JSeparator(SwingConstants.VERTICAL));
        panelUp.add(alignLeftButton);
        panelUp.add(alignCenterButton);
        panelUp.add(justifyButton);
        panelUp.add(allignRightButton);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        mainPanel.add(panelUp);
        mainPanel.add(panelUp);

        frame.add(mainPanel,BorderLayout.NORTH);
        frame.add(scrollPanel,BorderLayout.CENTER);

        frame.pack();
        frame.setVisible(true);
    }

    private Vector<String> getEditorFonts()
    {
        String [] availableFonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        Vector<String> returnList = new Vector<>();

        for (String font : availableFonts)
        {
                returnList.add(font);
        }
        return returnList;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object source = e.getSource();

        if(source==openButton)
        {
            fileOpener = new JFileChooser();
            FileNameExtensionFilter fileExtensionFilter = new FileNameExtensionFilter("Pliki tekstowe", "txt","doc","docs","odt","wpd");
            fileOpener.addChoosableFileFilter(fileExtensionFilter);
            fileOpener.setDialogTitle("Wybierz plik do otwarcia");
            int returnValue = fileOpener.showOpenDialog(null);
            if(returnValue == JFileChooser.APPROVE_OPTION)
            {
                fileName=fileOpener.getSelectedFile().getAbsolutePath();
                try
                {
                    FileReader reader = new FileReader(fileName);
                    BufferedReader bufferedReader = new BufferedReader(reader);
                    textPane.read(bufferedReader,null);
                    bufferedReader.close();
                    textPane.requestFocus();
                }
                catch (Exception error)
                {
                   JOptionPane.showMessageDialog(null,error);
                }
            }
        }
        if(source==fontColorButton)
        {
            Color initialcolor = JColorChooser.showDialog(this,"Wybierz kolor",Color.BLACK);
            if(initialcolor == null)
            {
                textPane.requestFocusInWindow();
            }
            SimpleAttributeSet attr = new SimpleAttributeSet();
            StyleConstants.setForeground(attr,initialcolor);
            textPane.setCharacterAttributes(attr,false);
            textPane.requestFocusInWindow();
            fontColorButton.setBackground(initialcolor);
            fontColorButton.setForeground(Color.BLACK);
        }
    }
    private class FontFamilyItemListener implements ItemListener
    {
        @Override
        public void itemStateChanged(ItemEvent e)
        {
            if ((e.getStateChange() != ItemEvent.SELECTED) || (fontFamilyCmbBox.getSelectedIndex() == 0))
            {
                return;
            }
            String fontFamily = (String) e.getItem();
            fontFamilyCmbBox.setAction(new StyledEditorKit.FontFamilyAction(fontFamily, fontFamily));
            fontFamilyCmbBox.setSelectedIndex(fontFamilyCmbBox.getSelectedIndex());
            textPane.requestFocusInWindow();
        }
    }
}