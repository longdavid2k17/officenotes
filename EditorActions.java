import Interfaces.Resources;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyledDocument;
import javax.swing.text.StyledEditorKit;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class EditorActions implements Resources
{
    JFileChooser imageDialog;
    JFileChooser saveDialog;
    JFileChooser saveAsPDFDialog;

    void drawImage(JFrame framePointer, JTextPane textPanePointer)
    {
        imageDialog = new JFileChooser();
        imageDialog.setDialogTitle("Wstaw obraz");
        FileNameExtensionFilter imageExtension = new FileNameExtensionFilter("Obraz", "jpg","png","raw");
        imageDialog.addChoosableFileFilter(imageExtension);
        if (imageDialog.showOpenDialog(framePointer) == JFileChooser.APPROVE_OPTION)
        {
            TextEditor.imageAdressArray = new ArrayList<String>();
            TextEditor.imageAdressArray.add(imageDialog.getSelectedFile().getPath());

            TextEditor.imageNameArray=new ArrayList<String>();
            TextEditor.imageNameArray.add(imageDialog.getSelectedFile().getName());

            int imageWidth, imageHeight;
            int resizedWidth, resizedHeight;

            ImageIcon beforeScale = new ImageIcon(imageDialog.getSelectedFile().getPath());
            Image image = beforeScale.getImage();

            imageWidth = beforeScale.getIconWidth();
            imageHeight = beforeScale.getIconHeight();
            if(imageWidth>5000)
            {
                resizedWidth = imageWidth / 8;
                resizedHeight = imageHeight / 8;
            }
            else if(imageWidth>4000)
            {
                resizedWidth = imageWidth / 4;
                resizedHeight = imageHeight / 4;
            }
            else
            {
                resizedWidth = imageWidth / 2;
                resizedHeight = imageHeight / 2;
            }
            System.out.println("Szer: "+imageWidth+" px, wys: "+imageHeight+" px");
            System.out.println("Po przemianie szer: "+resizedWidth+" px, wys: "+resizedHeight+" px");

            Image newImage = image.getScaledInstance(resizedWidth,resizedHeight,Image.SCALE_SMOOTH);
            ImageIcon afterScale = new ImageIcon(newImage);

            textPanePointer.insertIcon(afterScale);
        }
    }
    void save(JFrame framePointer, JTextPane textPanePointer)
    {

        saveDialog = new JFileChooser();
        saveDialog.setDialogTitle("Zapisz jako");
        FileNameExtensionFilter textExtenensions = new FileNameExtensionFilter("Pliki tekstowe", "txt");
        FileNameExtensionFilter docxExtension = new FileNameExtensionFilter("Plik MS Word", "docx");
        saveDialog.addChoosableFileFilter(textExtenensions);
        saveDialog.addChoosableFileFilter(docxExtension);
        if (saveDialog.showSaveDialog(framePointer) == JFileChooser.APPROVE_OPTION)
        {
            if (saveDialog.getFileFilter() == docxExtension)
            {
                try
                {
                    XWPFDocument document = new XWPFDocument();
                    saveFileURL(saveDialog.getSelectedFile());
                    FileOutputStream out = new FileOutputStream(new File(saveDialog.getSelectedFile().getPath()));

                    XWPFParagraph paragraph = document.createParagraph();
                    XWPFRun run = paragraph.createRun();
                    run.setText(textPanePointer.getText());
                            /*
                            if(imageAdressArray.size()>0)
                            {
                                System.out.println(imageAdressArray.get(0));
                            }
                            if(imageCount>0)
                            {
                                for(int i = 0; i< imageAdressArray.size(); i++)
                                {
                                    InputStream pic = new FileInputStream(imageAdressArray.get(i));
                                    run.addPicture(pic, XWPFDocument.PICTURE_TYPE_JPEG, imageNameArray.get(i),100,100);
                                }
                            }
                            */
                    document.write(out);
                    out.close();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                /////tu zapis do worda
            }
            else
            {
                //StyledDocument doc = (DefaultStyledDocument) textPanePointer.getDocument();
                //StyledEditorKit kit = (StyledEditorKit) textPanePointer.getEditorKit();
                File file = saveDialog.getSelectedFile();
                try
                {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                    //BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
                    writer.write(textPanePointer.getText());
                    //kit.write(out, doc, 0, doc.getLength());
                    writer.close();
                }
                catch (IOException  e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
    void saveAsPDF(JFrame framePointer, JTextPane textPanePointer)
    {
        saveAsPDFDialog = new JFileChooser();
        FileNameExtensionFilter pdfExtension = new FileNameExtensionFilter("Pliki PDF", "pdf");
        saveAsPDFDialog.addChoosableFileFilter(pdfExtension);
        if (saveAsPDFDialog.showSaveDialog(framePointer) == JFileChooser.APPROVE_OPTION)
        {
            File file = saveAsPDFDialog.getSelectedFile();

            Document document = new Document();
            try
            {
                saveFileURL(file);
                PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file.getAbsolutePath()));
                document.open();
                document.addCreationDate();

                document.addAuthor(System.getProperty("user.name"));
                document.add(new Paragraph(textPanePointer.getText()));
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
    void resizePane(JPanel leftPanelPointer, JPanel rightPanelPointer, JScrollPane scrollPanePointer)
    {
        System.out.println(screenSize.getWidth()+" "+screenSize.getHeight());
        if(screenSize.getWidth()==1366.0 && screenSize.getHeight()==768.0)
        {
            leftPanelPointer.setPreferredSize(new Dimension(278, 800));
            leftPanelPointer.setMinimumSize(new Dimension(270, 800));
            leftPanelPointer.setMaximumSize(new Dimension(280, 800));

            rightPanelPointer.setPreferredSize(new Dimension(278, 800));
            rightPanelPointer.setMinimumSize(new Dimension(270, 800));
            rightPanelPointer.setMaximumSize(new Dimension(280, 800));

            scrollPanePointer.setPreferredSize(new Dimension(810,800));
            scrollPanePointer.setMinimumSize(new Dimension(805, 800));
            scrollPanePointer.setMaximumSize(new Dimension(815, 800));
        }
        else if(screenSize.getWidth()==1600.0 && screenSize.getHeight()==900.0)
        {
            leftPanelPointer.setPreferredSize(new Dimension(320, 800));
            leftPanelPointer.setMinimumSize(new Dimension(310, 800));
            leftPanelPointer.setMaximumSize(new Dimension(330, 800));

            rightPanelPointer.setPreferredSize(new Dimension(320, 800));
            rightPanelPointer.setMinimumSize(new Dimension(310, 800));
            rightPanelPointer.setMaximumSize(new Dimension(330, 800));

            scrollPanePointer.setPreferredSize(new Dimension(960,800));
            scrollPanePointer.setMinimumSize(new Dimension(950, 800));
            scrollPanePointer.setMaximumSize(new Dimension(970, 800));
        }
        else if(screenSize.getWidth()==1920.0 && screenSize.getHeight()==1080.0)
        {
            leftPanelPointer.setPreferredSize(new Dimension(384, 800));
            leftPanelPointer.setMinimumSize(new Dimension(380, 800));
            leftPanelPointer.setMaximumSize(new Dimension(390, 800));

            rightPanelPointer.setPreferredSize(new Dimension(384, 800));
            rightPanelPointer.setMinimumSize(new Dimension(380, 800));
            rightPanelPointer.setMaximumSize(new Dimension(390, 800));

            scrollPanePointer.setPreferredSize(new Dimension(1152,800));
            scrollPanePointer.setMinimumSize(new Dimension(1142, 800));
            scrollPanePointer.setMaximumSize(new Dimension(1162, 800));
        }
    }
    void saveFileURL(File file)
    {
        try (FileOutputStream fileOutputStream = new FileOutputStream(new File("files_urls.txt")))
        {
          BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
          writer.write(file.getAbsolutePath());
          writer.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String loadURL() throws IOException
    {
        BufferedReader reader = new BufferedReader(new FileReader("files_url.txt"));
        String readedString = reader.readLine();
        return readedString;
    }
}
