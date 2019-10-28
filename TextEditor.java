import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfDocument;
import com.lowagie.text.pdf.PdfWriter;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.*;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;



public class TextEditor extends JFrame implements ActionListener
{
    File actualFile = null;

    private JFrame frame;
    private  JFileChooser fileOpener;
    private  JFileChooser saveDialog;
    private  JPanel panelUp, panelDown, mainPanel;
    private  JToolBar toolBar;
    private  JButton boldButton, cursiveButton, underlineButton, fontColorButton, alignLeftButton, alignCenterButton, allignRightButton, justifyButton;
    private  JButton newButton, openButton, saveButton, printButton, insertImageButton;
    private JScrollPane scrollPanel;
    private JTextPane textPane;
    private JComboBox fontSizes, fontFamilyCmbBox;
    private String fileName;
    private int styleIndex=0;
    JMenu fileMenu, editMenu, insertMenu, helpMenu;
    JMenuBar menuBar;
    Color settedColor;
    PrinterJob printerJob;

    private static final List<String> FONT_LIST = Arrays.asList(new String [] {"Arial", "Calibri", "Cambria", "Courier New", "Comic Sans MS", "Dialog", "Georgia", "Helevetica", "Lucida Sans", "Monospaced", "Tahoma", "Times New Roman", "Verdana"});
    private static final String[] FONT_SIZES = {"7","8","9","10","11","12","13","14","15","16","17","18","19","20","22","23","24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42","43","44","45","46","47","48","49","50","51","52","53","54","55","56","57","58","59","60"};
    private static final int DEFAULT_FONT_SIZE = 10;

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    ImageIcon imageNew = new ImageIcon("src/images/icons8-new-document-50.png");
    ImageIcon imageOpen = new ImageIcon("src/images/icons8-open-document-50.png");
    ImageIcon imageSave = new ImageIcon("src/images/icons8-save-50.png");
    ImageIcon imagePrint = new ImageIcon("src/images/icons8-print-50.png");
    ImageIcon imageInsertImage = new ImageIcon("src/images/icons8-full-image-50.png");
    ImageIcon boldImage = new ImageIcon("src/images/icons8-bold-50.png");
    ImageIcon italicImage = new ImageIcon("src/images/icons8-italic-50.png");
    ImageIcon underlineImage = new ImageIcon("src/images/icons8-underline-50.png");
    ImageIcon alignLeftImage = new ImageIcon("src/images/icons8-align-left-50.png");
    ImageIcon alignCenterImage = new ImageIcon("src/images/icons8-align-center-50.png");
    ImageIcon jutifyImage = new ImageIcon("src/images/icons8-align-justify-50.png");
    ImageIcon alignRightImage = new ImageIcon("src/images/icons8-align-right-50.png");

    TextEditor()
    {
        frame = new JFrame();
        frame.setTitle("OfficeNotes");
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(screenSize.width,screenSize.height-30);
        frame.validate();
        frame.setBackground(Color.lightGray);
        frame.setLocationRelativeTo(null);

        textPane = new JTextPane();
        //textPane.setMinimumSize(new Dimension(600,500));
        textPane.setBounds(300,0,500,800);
        scrollPanel = new JScrollPane(textPane);
        scrollPanel.setPreferredSize(new Dimension(1300,800));

        SimpleAttributeSet attr = new SimpleAttributeSet();
        StyleConstants.setFontSize(attr,DEFAULT_FONT_SIZE);
        textPane.setCharacterAttributes(attr, false);

        frame.getContentPane().add(scrollPanel,BorderLayout.CENTER);

        menuBar = new JMenuBar();
        menuBar.setBackground(new Color(40,122,252));

        fileMenu = new JMenu("Plik");
        fileMenu.setMnemonic(KeyEvent.VK_F);

        JMenuItem newItem = new JMenuItem("Nowy");
        newItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ev)
            {

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
                if (saveDialog.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION)
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

            }
        });
        JMenuItem redoItem = new JMenuItem("Ponów");
        redoItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ev)
            {

            }
        });

        JMenuItem cutItem = new JMenuItem("Wytnij");
        cutItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ev)
            {

            }
        });
        JMenuItem copyItem = new JMenuItem("Kopiuj");
        copyItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ev)
            {

            }
        });
        JMenuItem pasteItem = new JMenuItem("Wklej");
        pasteItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ev)
            {

            }
        });
        JMenuItem markAllItem = new JMenuItem("Zaznacz wszystko");
        markAllItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ev)
            {

            }
        });
        JMenuItem findItem = new JMenuItem("Znajdź");
        findItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ev)
            {

            }
        });
        JMenuItem findAndReplaceItem = new JMenuItem("Znajdź i zastąp");
        findAndReplaceItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ev)
            {

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
        imageItem.addActionListener(this);
        JMenuItem multimediaItem = new JMenuItem("Multimedia");
        multimediaItem.addActionListener(this);
        JMenuItem graphItem = new JMenuItem("Wykres");
        graphItem.addActionListener(this);
        JMenuItem specialCharItem = new JMenuItem("Znak specjalny");
        specialCharItem.addActionListener(this);

        insertMenu.add(imageItem);
        insertMenu.add(multimediaItem);
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
                JOptionPane.showMessageDialog(frame,"Autorem programu jest Dawid Kańtoch. \nOfficeNotes jest darmowym programem, dostępnym dla wszystkich bez opłat.\nPobieranie opłat za użytkowanie wzbronione.\nWszelkie prawa zastrzeżone 2019","Autor programu OfficeNotes",JOptionPane.INFORMATION_MESSAGE);
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

        fontSizes = new JComboBox<String>(FONT_SIZES);
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

        newButton = new JButton(imageNew);
        newButton.addActionListener(this);

        openButton = new JButton(imageOpen);
        openButton.addActionListener(this);

        saveButton = new JButton(imageSave);
        saveButton.addActionListener(this);

        printButton = new JButton(imagePrint);
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

        insertImageButton = new JButton(imageInsertImage);
        insertImageButton.addActionListener(this);

        panelUp.add(newButton);
        panelUp.add(openButton);
        panelUp.add(saveButton);
        panelUp.add(printButton);
        panelUp.add(new JSeparator(SwingConstants.VERTICAL));
        panelUp.add(insertImageButton);

        boldButton = new JButton(boldImage);
        boldButton.addActionListener(this);

        cursiveButton = new JButton(italicImage);
        cursiveButton.addActionListener(this);

        underlineButton = new JButton(underlineImage);
        underlineButton.addActionListener(this);

        fontColorButton = new JButton();
        fontColorButton.setText("Kolor");
        fontColorButton.addActionListener(this);

        alignLeftButton = new JButton(alignLeftImage);
        alignLeftButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                StyleConstants.setAlignment(attr , StyleConstants.ALIGN_LEFT);
                textPane.setParagraphAttributes(attr,true);
            }
        });

        alignCenterButton = new JButton(alignCenterImage);
        alignCenterButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                StyleConstants.setAlignment(attr , StyleConstants.ALIGN_CENTER);
                textPane.setParagraphAttributes(attr,true);
            }
        });

        justifyButton = new JButton(jutifyImage);
        justifyButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                StyleConstants.setAlignment(attr , StyleConstants.ALIGN_JUSTIFIED);
                textPane.setParagraphAttributes(attr,true);
            }
        });

        allignRightButton = new JButton(alignRightImage);
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

    private Vector<String> getEditorFonts() {

        String [] availableFonts =
                GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        Vector<String> returnList = new Vector<>();

        for (String font : availableFonts) {

            if (FONT_LIST.contains(font)) {

                returnList.add(font);
            }
        }

        return returnList;
    }


    public static void main(String args[])
    {
        new TextEditor();
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


