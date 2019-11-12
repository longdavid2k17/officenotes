import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public interface Resources
{
     static final List<String> FONT_LIST = Arrays.asList(new String [] {"Arial", "Calibri", "Cambria", "Courier New", "Comic Sans MS", "Dialog", "Georgia", "Helevetica", "Lucida Sans", "Monospaced", "Tahoma", "Times New Roman", "Verdana"});
     static final String[] FONT_SIZES = {"7","8","9","10","11","12","13","14","15","16","17","18","19","20","22","23","24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42","43","44","45","46","47","48","49","50","51","52","53","54","55","56","57","58","59","60"};
     static final int DEFAULT_FONT_SIZE = 10;

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

    ImageIcon mainIcon = new ImageIcon("src/images/app_icon.png");
}
