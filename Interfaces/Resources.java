package Interfaces;

import javax.swing.*;
import java.awt.*;

public interface Resources
{
       String[] FONT_SIZES = {"7","8","9","10","11","12","13","14","15","16","17","18","19","20","22","23","24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42","43","44","45","46","47","48","49","50","51","52","53","54","55","56","57","58","59","60"};
       int DEFAULT_FONT_SIZE = 10;

       String authorText = "Autorem programu jest Dawid Kańtoch. \n" +
               "OfficeNotes jest darmowym programem, dostępnym dla wszystkich bez opłat.\n" +
               "Pobieranie opłat za użytkowanie wzbronione.\nCopyrights © All rights reserved 2019 ®";

       String howToUseText = "Na szczycie okna znajduje się menu obsługi programu - możesz tam utworzyć nowy plik, otworzyć inny plik, zapisać, wydrukować etc.\n" +
        "Ikony najważniejszych funkcji znajdują się pod menu.\n" +
        "Wybierz następnie swoją ulubioną czcionkę, kolor i wpadnij w wir kreatywnej pracy pisarza!";

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    ImageIcon imageTable = new ImageIcon(Resources.class.getResource("/images/icons8-table-50.png"));
    ImageIcon imageOpen = new ImageIcon(Resources.class.getResource("/images/icons8-open-document-50.png"));
    ImageIcon imageSave = new ImageIcon(Resources.class.getResource("/images/icons8-save-50.png"));
    ImageIcon imagePrint = new ImageIcon(Resources.class.getResource("/images/icons8-print-50.png"));
    ImageIcon imageInsertImage = new ImageIcon(Resources.class.getResource("/images/icons8-full-image-50.png"));
    ImageIcon boldImage = new ImageIcon(Resources.class.getResource("/images/icons8-bold-50.png"));
    ImageIcon italicImage = new ImageIcon(Resources.class.getResource("/images/icons8-italic-50.png"));
    ImageIcon underlineImage = new ImageIcon(Resources.class.getResource("/images/icons8-underline-50.png"));
    ImageIcon alignLeftImage = new ImageIcon(Resources.class.getResource("/images/icons8-align-left-50.png"));
    ImageIcon alignCenterImage = new ImageIcon(Resources.class.getResource("/images/icons8-align-center-50.png"));
    ImageIcon jutifyImage = new ImageIcon(Resources.class.getResource("/images/icons8-align-justify-50.png"));
    ImageIcon alignRightImage = new ImageIcon(Resources.class.getResource("/images/icons8-align-right-50.png"));

    ImageIcon mainIcon = new ImageIcon(Resources.class.getResource("/images/app_icon.png"));
}
