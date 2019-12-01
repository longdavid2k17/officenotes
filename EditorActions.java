import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.util.ArrayList;

public class EditorActions
{
    JFileChooser imageDialog;

    void drawImage(JFrame framePointer, JTextPane textPanePointer)
    {
        imageDialog = new JFileChooser();
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
}
