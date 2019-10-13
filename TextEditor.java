import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
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
    private JComboBox fontSizes, fontFamily;
    JMenu fileMenu, editMenu, insertMenu, helpMenu;
    JMenuBar menuBar;
    Color settedColor;

    private static final List<String> FONT_LIST = Arrays.asList(new String [] {"Arial", "Calibri", "Cambria", "Courier New", "Comic Sans MS", "Dialog", "Georgia", "Helevetica", "Lucida Sans", "Monospaced", "Tahoma", "Times New Roman", "Verdana"});
    private static final String[] FONT_SIZES = {"Rozmiar","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","22","23","24","25","26","27","28","29","30"};
    private static final int DEFAULT_FONT_SIZE = 14;

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
        frame.setTitle("Office Notes");
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(screenSize.width,screenSize.height-30);
        frame.validate();
        frame.setBackground(Color.lightGray);
        frame.setLocationRelativeTo(null);

        textPane = new JTextPane();
        scrollPanel = new JScrollPane(textPane);

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

            }
        });
        JMenuItem printItem = new JMenuItem("Drukuj...");
        printItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ev)
            {

            }
        });
        JMenuItem exitItem = new JMenuItem("Zamknij");
        exitItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ev)
            {

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

        JMenuItem dealWithItem = new JMenuItem("Obsługa programu");
        dealWithItem.addActionListener(this);
        JMenuItem aboutAuthorItem = new JMenuItem("O autorze");
        aboutAuthorItem.addActionListener(this);

        helpMenu.add(dealWithItem);
        helpMenu.add(aboutAuthorItem);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(insertMenu);
        menuBar.add(helpMenu);

        frame.setJMenuBar(menuBar);

        Vector<String> editorFonts = getEditorFonts();
        editorFonts.add(0,"Czcionka");
        fontFamily = new JComboBox<String>(editorFonts);
        fontFamily.setEditable(false);
        //fontFamily.addItemListener(new FontFamilyItemListiner());

        fontSizes = new JComboBox<String>(FONT_SIZES);
        fontSizes.setEditable(false);

        panelUp = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelUp.setBackground(new Color(110,160,240));

        newButton = new JButton(imageNew);
        newButton.addActionListener(this);

        openButton = new JButton(imageOpen);
        openButton.addActionListener(this);

        saveButton = new JButton(imageSave);
        saveButton.addActionListener(this);

        printButton = new JButton(imagePrint);
        printButton.addActionListener(this);

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
        alignLeftButton.addActionListener(this);

        alignCenterButton = new JButton(alignCenterImage);
        alignCenterButton.addActionListener(this);

        justifyButton = new JButton(jutifyImage);
        justifyButton.addActionListener(this);

        allignRightButton = new JButton(alignRightImage);
        allignRightButton.addActionListener(this);

        panelDown = new JPanel();
        panelDown.setBackground(new Color(110,160,240));
        panelDown.add(fontFamily);
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
            //fileOpener.setDialogType();
        }
        if(source==fontColorButton)
        {
            Color initialcolor = Color.BLACK;
            JColorChooser colorChooser = new JColorChooser();
            colorChooser.showDialog(this,"Wybierz kolor",initialcolor);
            settedColor=colorChooser.getColor();
            textPane.setCaretColor(settedColor);
        }
    }
}
