import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.util.Random;

public class TheSecondPart extends JFrame implements  ActionListener {
    JButton selectImage;
    File FileNameForInitialState ;
    JLabel showImageInInitialState;
    String pathForInitialImage;




    TheSecondPart(){
    // set windows
    this.setName("Second window");
    // set const size
    setResizable(false);
    this.setTitle("Search Image");
    this.setSize(2200,1200);
    this.setVisible(true);
    this.setLayout(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    selectImage = new JButton("SelectImage");
    selectImage.setBounds(50, 50 , 200 , 30);
    this.add(selectImage);
    showImageInInitialState =new JLabel();
    showImageInInitialState.setBounds(20 ,120 ,300,300);
    this.add(showImageInInitialState);

 selectImage.addActionListener(

                e -> {
                    JFileChooser fileChooser =new JFileChooser();
                    FileNameExtensionFilter fileNameExtensionFilter =
                            new FileNameExtensionFilter("Extension Supported" ,"jpg","png","jpeg","gif");
                    fileChooser.setFileFilter(fileNameExtensionFilter);
                    int selected =fileChooser.showOpenDialog(this);
                    File file = fileChooser.getSelectedFile();
                    FileNameForInitialState =file;
                    String getSelectedImage =file.getAbsolutePath();
                    ImageIcon imageIcon = new ImageIcon(getSelectedImage); // load the image to a imageIcon
                    Image image = imageIcon.getImage(); // transform it
                    Image newimg = ((Image) image).getScaledInstance(300, 300,  Image.SCALE_SMOOTH); // scale it the smooth way
                    imageIcon = new ImageIcon(newimg);
                    showImageInInitialState.setIcon(imageIcon);
                    pathForInitialImage =getSelectedImage;
                    String filename =file.getAbsolutePath();
                    System.out.println(filename);
                    String newPath="D:\\save\\PART2\\";
                    System.out.println(newPath.toString());
//                    imageIcon = rgb2gray(imread('image1.png'));
                }
 );
}
// Button of  select image Performed
    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
