import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import javax.swing.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.*;
import java.io.*;
import java.util.Vector;



public class TextEditor extends JFrame implements ActionListener, Resources
{
    File actualFile = null;

    private JFrame frame;
    private  JFileChooser fileOpener;
    private  JFileChooser saveDialog;
    private  JFileChooser imageDialog;
    private  JPanel panelUp, panelDown, mainPanel;
    private  JToolBar toolBar;
    private  JButton boldButton, cursiveButton, underlineButton, fontColorButton, alignLeftButton, alignCenterButton, allignRightButton, justifyButton;
    private  JButton newButton, openButton, saveButton, printButton, insertImageButton;
    private JScrollPane scrollPanel;
    private JTextPane textPane;
    private JComboBox fontSizes, fontFamilyCmbBox;
    private String fileName;
    private int styleIndex=0;
    private boolean isBold = false;
    JMenu fileMenu, editMenu, insertMenu, helpMenu;
    JMenuBar menuBar;
    protected UndoManager undoManager;

    TextEditor()
    {
        createUI();
    }

    void createUI()
    {
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

            }
        });
        JMenuItem saveAsItem = new JMenuItem("Zapisz jako");
        saveAsItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ev)
            {
                saveDialog = new JFileChooser();
                FileNameExtensionFilter textExtenensions = new FileNameExtensionFilter("Pliki tekstowe", "txt");
                FileNameExtensionFilter docxExtension = new FileNameExtensionFilter("Plik MS Word", "docx");
                saveDialog.addChoosableFileFilter(textExtenensions);
                saveDialog.addChoosableFileFilter(docxExtension);
                if (saveDialog.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION)
                {
                    if (saveDialog.getFileFilter() == docxExtension)
                    {
                        XWPFDocument document = new XWPFDocument();
                        try
                        {
                            FileOutputStream out = new FileOutputStream(new File(saveDialog.getSelectedFile().getPath()));

                            XWPFParagraph paragraph = document.createParagraph();
                            XWPFRun run = paragraph.createRun();
                            run.setText(textPane.getText());
                            document.write(out);
                            out.close();
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                        /////tu zapis do worda
                    }
                    else
                    {
                        File file = saveDialog.getSelectedFile();
                        try
                        {
                            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                            writer.write(textPane.getText());
                            writer.close();
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        JMenuItem saveCopyItem = new JMenuItem("Zapisz kopię");
        saveCopyItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ev)
            {

            }
        });
        JMenuItem exportAsPDF = new JMenuItem("Zapisz jako PDF");
        exportAsPDF.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ev)
            {
                saveDialog = new JFileChooser();
                FileNameExtensionFilter pdfExtension = new FileNameExtensionFilter("Pliki PDF", "pdf");
                saveDialog.addChoosableFileFilter(pdfExtension);
                if (saveDialog.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION)
                {
                    File file = saveDialog.getSelectedFile();

                    Document document = new Document();
                    try
                    {
                        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file.getAbsolutePath()));
                        document.open();
                        document.addCreationDate();

                        document.addAuthor(System.getProperty("user.name"));
                        document.add(new Paragraph(textPane.getText()));
                        document.close();
                        writer.close();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }

                    catch (DocumentException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        });
        JMenuItem printItem = new JMenuItem("Drukuj...");
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
        fileMenu.add(saveCopyItem);
        fileMenu.add(exportAsPDF);
        fileMenu.addSeparator();
        fileMenu.add(printItem);
        fileMenu.add(exitItem);

        editMenu = new JMenu("Edycja");



        JMenuItem undoItem = new JMenuItem("Cofnij");
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
                }
            }
        });
        JMenuItem redoItem = new JMenuItem("Ponów");
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
                imageDialog = new JFileChooser();
                FileNameExtensionFilter imageExtension = new FileNameExtensionFilter("Obraz", "jpg","png","raw");
                imageDialog.addChoosableFileFilter(imageExtension);
                if (imageDialog.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION)
                {
                    textPane.insertIcon(new ImageIcon(imageDialog.getSelectedFile().getPath()));
                    //textPane.insertComponent(new JLabel(new ImageIcon(imageDialog.getSelectedFile().getPath())));
                }
            }
        });
        JMenuItem graphItem = new JMenuItem("Wykres");
        graphItem.addActionListener(this);
        JMenuItem specialCharItem = new JMenuItem("Znak specjalny");
        specialCharItem.addActionListener(this);

        insertMenu.add(imageItem);
        insertMenu.add(graphItem);
        insertMenu.add(specialCharItem);

        helpMenu = new JMenu("Pomoc");

        JMenuItem howToUseItem = new JMenuItem("Obsługa programu");
        howToUseItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JOptionPane.showMessageDialog(frame,"Na szczycie okna znajduje się menu obsługi programu - możesz tam utworzyć nowy plik, otworzyć inny plik, zapisać, wydrukować etc.\nIkony najważniejszych funkcji znajdują się pod menu.\nWybierz następnie swoją ulubioną czcionkę, kolor i wpadnij w wir kreatywnej pracy pisarza!", "Jak używać programu OfficeNotes",JOptionPane.INFORMATION_MESSAGE);
            }
        });
        JMenuItem aboutAuthorItem = new JMenuItem("O autorze");
        aboutAuthorItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JOptionPane.showMessageDialog(frame,"Autorem programu jest Dawid Kańtoch. \nOfficeNotes jest darmowym programem, dostępnym dla wszystkich bez opłat.\nPobieranie opłat za użytkowanie wzbronione.\nCopyrights © All rights reserved 2019 ®","Autor programu OfficeNotes",JOptionPane.INFORMATION_MESSAGE);
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
                imageDialog = new JFileChooser();
                imageDialog.setDialogTitle("Wstaw obraz");
                FileNameExtensionFilter imageExtension = new FileNameExtensionFilter("Obraz", "jpg","png","raw");
                imageDialog.addChoosableFileFilter(imageExtension);
                if (imageDialog.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION)
                {
                    textPane.insertIcon(new ImageIcon(imageDialog.getSelectedFile().getPath()));
                }
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

        panelDown = new JPanel();
        panelDown.setBackground(new Color(110,160,240));
        panelDown.add(fontFamilyCmbBox);
        panelDown.add(new JSeparator(SwingConstants.VERTICAL));
        panelDown.add(fontSizes);
        panelDown.add(new JSeparator(SwingConstants.VERTICAL));
        panelDown.add(boldButton);
        panelDown.add(cursiveButton);
        panelDown.add(underlineButton);
        panelDown.add(new JSeparator(SwingConstants.VERTICAL));
        panelDown.add(fontColorButton);
        panelDown.add(new JSeparator(SwingConstants.VERTICAL));
        panelDown.add(alignLeftButton);
        panelDown.add(alignCenterButton);
        panelDown.add(justifyButton);
        panelDown.add(allignRightButton);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        mainPanel.add(panelUp);
        mainPanel.add(panelDown);

        frame.add(mainPanel,BorderLayout.NORTH);
        frame.add(scrollPanel,BorderLayout.CENTER);

        frame.pack();
        frame.setVisible(true);

    }

    private Vector<String> getEditorFonts()
    {
        String [] availableFonts =
                GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        Vector<String> returnList = new Vector<>();

        for (String font : availableFonts)
        {
            if (Resources.FONT_LIST.contains(font))
            {
                returnList.add(font);
            }
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
            if ((e.getStateChange() != ItemEvent.SELECTED) ||
                    (fontFamilyCmbBox.getSelectedIndex() == 0))
            {
                return;
            }

            String fontFamily = (String) e.getItem();
            fontFamilyCmbBox.setAction(new StyledEditorKit.FontFamilyAction(fontFamily, fontFamily));
            fontFamilyCmbBox.setSelectedIndex(0); // initialize to (default) select
            textPane.requestFocusInWindow();
        }
    }
}