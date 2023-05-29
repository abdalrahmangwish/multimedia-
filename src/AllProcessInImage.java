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
import java.awt.Image;
import java.awt.image.PixelGrabber;

public class AllProcessInImage  extends JFrame implements  ActionListener{
    JButton selectionImage;
    JButton toGreen;
    JButton toGrey;
    JLabel showImageInInitialState;
    JLabel showImageInFirstState;
    JLabel showImageINSecondState;
    JLabel showImageINThirdState;

    File FileNameForInitialState ;
    String pathForInitialImage;
    BufferedImage bufferedImage = null;
    ImageIcon imageInitial;
    JButton blur;
    JButton toRed;
    JButton toBlue;
    JButton negative;
    JButton detectEdges;
    BufferedImage bufferedImageForBlur;
    JLabel newsLabel;
    JButton brighten;

    BufferedImage bufferedImageForScale;

    AllProcessInImage(){
        // set windows
        this.setName("First window");
        // set const size
        setResizable(false);
        this.setTitle("Selection Image");
        this.setSize(2200,1200);
        this.setVisible(true);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Button1
        selectionImage =new JButton("selectionImage");
        selectionImage.setBounds(20,50,170,30);
        this.add(selectionImage);
        // action For Button 1
        selectionImage.addActionListener(this);
        //Button 2
        toGreen =new JButton("toGreen");
        toGreen.setBounds(400,50,100,30);

        toRed = new JButton("toRed");
        toRed.setBounds(500,50 , 100 , 30 );
        this.add(toRed);

        toBlue = new JButton("toBlue");
        toBlue.setBounds(600,50 , 100 , 30 );
        this.add(toBlue);
        negative = new JButton("negative");
        negative.setBounds(700 , 50 , 100 ,30);
        this.add(negative);
        //Label 1
        showImageInInitialState =new JLabel();
        showImageInInitialState.setBounds(20 ,120 ,300,300);
        this.add(showImageInInitialState);
        //Label2
        showImageInFirstState =new JLabel();
        showImageInFirstState.setBounds(400,120,300,300);
        this.add(showImageInFirstState);
        //Button 3
        toGrey =new JButton("toGrey");
        toGrey.setBounds(800,50,100,30);
        this.add(toGrey);
        //Button4
        blur =new JButton("blur");
        blur.setBounds(900,50,100,30);
        this.add(blur);
        brighten =new JButton("brighten");
        brighten.setBounds(1000,50,100,30);
        this.add(brighten);
        //add button for detectEdges...
        detectEdges =new JButton("detectEdges");
        detectEdges.setBounds(1100,50,100,30);
        this.add(detectEdges);
        //Label 3
        showImageINSecondState =new JLabel();
        showImageINSecondState.setBounds(830 ,120 ,320 ,320);
        this.add(showImageINSecondState);
        //Label 4
        showImageINThirdState =new JLabel();
        showImageINThirdState.setBounds(1500 ,120 ,400 ,400);
        this.add(showImageINThirdState);
        this.newsLabel =new JLabel();
        newsLabel.setBounds(50,1000,200,200 );
        blur.addActionListener(
                e -> {
                    bufferedImageForScale = blur(bufferedImageForScale);
                    System.out.println(bufferedImageForScale);
                    showImageINSecondState.setIcon(new ImageIcon(bufferedImageForScale));
                    System.out.println(bufferedImageForScale);
                    showImageINSecondState.setIcon(new ImageIcon(bufferedImageForScale));
                    //TODO............................................................................<<<<<<<<<<<<<<<<<<
                    // this is Logarithm can show color Hist
                    int[][][] ch = new int[4][4][4];
                    for(int x = 0; x < bufferedImage.getWidth(); x++)
                        for(int y = 0; y < bufferedImage.getHeight(); y++) {
                            int color = bufferedImage.getRGB(x, y);
                            int alpha = (color & 0xff000000) >> 24;
                            int red = (color & 0x00ff0000) >> 16;
                            int green = (color & 0x0000ff00) >> 8;
                            int blue = color & 0x000000ff;
                            ch[red / 64][green / 64][blue / 64]++;
                        }
                    for(int i = 0; i < ch.length; i++)
                        for(int j = 0; j < ch[i].length; j++)
                            for(int p = 0; p < ch[i][j].length; p++)
                                System.out.println("t[" + i + "][" + j + "][" + p + "] = " + ch[i][j][p]);// kb
                    //TODO................................................................................<<<<<<<<<<<<<<<<<<<<<<,

                }
        );
        detectEdges.addActionListener(
                e -> {
                    bufferedImageForScale = detectEdges(bufferedImageForScale);
                    System.out.println(bufferedImageForScale);
                    showImageINSecondState.setIcon(new ImageIcon(bufferedImageForScale));
                    System.out.println(bufferedImageForScale);
                    showImageINSecondState.setIcon(new ImageIcon(bufferedImageForScale));
                    //TODO............................................................................<<<<<<<<<<<<<<<<<<
                    // this is Logarithm can show color Hist
                    int[][][] ch = new int[4][4][4];
                    for(int x = 0; x < bufferedImage.getWidth(); x++)
                        for(int y = 0; y < bufferedImage.getHeight(); y++) {
                            int color = bufferedImage.getRGB(x, y);
                            int alpha = (color & 0xff000000) >> 24;
                            int red = (color & 0x00ff0000) >> 16;
                            int green = (color & 0x0000ff00) >> 8;
                            int blue = color & 0x000000ff;
                            ch[red / 64][green / 64][blue / 64]++;
                        }
                    for(int i = 0; i < ch.length; i++)
                        for(int j = 0; j < ch[i].length; j++)
                            for(int p = 0; p < ch[i][j].length; p++)
                                System.out.println("t[" + i + "][" + j + "][" + p + "] = " + ch[i][j][p]);// kb
                    //TODO................................................................................<<<<<<<<<<<<<<<<<<<<<<,

                }
        );

        toGrey.addActionListener(
                e -> {
                    try {
                        bufferedImageForScale = toGrayScale2(bufferedImageForScale);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    showImageINSecondState.setIcon(new ImageIcon(bufferedImageForScale));


                }
        );
        toBlue.addActionListener(
                e -> {


                    ImageIcon imageIcon = new ImageIcon(pathForInitialImage); // load the image to a imageIcon
                    Image image = imageIcon.getImage(); // transform it
                    Image newImage = ((Image) image).getScaledInstance(200, 200,  Image.SCALE_SMOOTH );
                    imageIcon = new ImageIcon(newImage);
                    imageInitial =imageIcon;

                    try {
                        bufferedImageForScale =ImageIO.read(FileNameForInitialState);
                        bufferedImageForBlur =ImageIO.read(FileNameForInitialState);
                        bufferedImage =ImageIO.read(FileNameForInitialState);
                        System.out.println("Convert to red");
//                            BufferedImage grayScaleImage =new BufferedImage(h,w,BufferedImage.TYPE_INT_ARGB);
                        var height = bufferedImage.getHeight();
                        var width = bufferedImage.getWidth();
                        for (int x =0 ; x<width ; x++){
                            for (int y =0 ; y<height ; y++ ){
                                int p = bufferedImage.getRGB(x,y);
                                int a= (p>>24)&0xff;
                                int b= (p>>0)&0xff;
                                p=(a<<24) | (0<<16) |(0<<8)|b;
                                bufferedImage.setRGB(x,y,p);
                            }
                        }
                        //TODO -> print color Hit in the copy image with green color
                        int[][][] ch = new int[4][4][4];
                        for(int xx = 0; xx < bufferedImage.getWidth(); xx++)
                            for(int yy = 0; yy < bufferedImage.getHeight(); yy++) {
                                int color = bufferedImage.getRGB(xx, yy);
                                int alpha = (color & 0xff000000) >> 24;
                                int red = (color & 0x00ff0000) >> 16;
                                int green = (color & 0x0000ff00) >> 8;
                                int blue = color & 0x000000ff;
                                ch[red / 64][green / 64][blue / 64]++;
                            }
                        for(int i = 0; i < ch.length; i++)
                            for(int j = 0; j < ch[i].length; j++)
                                for(int v = 0; v < ch[i][j].length; v++)
                                    System.out.println("t[" + i + "][" + j + "][" + v + "] = " + ch[i][j][v]);
                        //TODO --> end color Hist..
                        //Storage image after convert
                        ImageIO.write(bufferedImage, "gif", new File("D:\\save\\toBlue\\grayScaleImage.gif"));
                        ImageIO.write(bufferedImage,"png",new File("D:\\save\\toBlue\\grayScaleImage.png"));
                        ImageIcon imageIcon1 = new ImageIcon("D:\\save\\toBlue\\grayScaleImage.gif"); // load the image to a imageIcon
                        Image image2 = imageIcon1.getImage(); // transform it
                        Image newImage1 = ((Image) image2).getScaledInstance(300, 300,  Image.SCALE_AREA_AVERAGING);
                        // scale it the smooth way
                        imageIcon1 = new ImageIcon(newImage1);
                        showImageInFirstState.setIcon(imageIcon1);

                    } catch (IOException ex) {

                        throw new RuntimeException(ex);
                    }



                }
        );
        negative.addActionListener(
                e -> {


                    ImageIcon imageIcon = new ImageIcon(pathForInitialImage); // load the image to a imageIcon
                    Image image = imageIcon.getImage(); // transform it
                    Image newImage = ((Image) image).getScaledInstance(200, 200,  Image.SCALE_SMOOTH );
                    imageIcon = new ImageIcon(newImage);
                    imageInitial =imageIcon;

                    try {
                        bufferedImageForScale =ImageIO.read(FileNameForInitialState);
                        bufferedImageForBlur =ImageIO.read(FileNameForInitialState);
                        bufferedImage =ImageIO.read(FileNameForInitialState);
                        System.out.println("Convert to red");
//                            BufferedImage grayScaleImage =new BufferedImage(h,w,BufferedImage.TYPE_INT_ARGB);
                        var height = bufferedImage.getHeight();
                        var width = bufferedImage.getWidth();
                        for (int x =0 ; x<width ; x++){
                            for (int y =0 ; y<height ; y++ ){
                                int p = bufferedImage.getRGB(x,y);
                                int a= (p>>24)&0xff;
                                int r =(p>>16)&0xff;
                                int g =(p>>8)&0xff;
                                int b= (p>>0)&0xff;
                                r =255-r;
                                g = 255-g;
                                b= 255-b;
                                p=(a<<24) | (r<<16) |(g<<8)| b;
                                bufferedImage.setRGB(x,y,p);
                            }
                        }
                        //TODO -> print color Hit in the copy image with green color
                        int[][][] ch = new int[4][4][4];
                        for(int xx = 0; xx < bufferedImage.getWidth(); xx++)
                            for(int yy = 0; yy < bufferedImage.getHeight(); yy++) {
                                int color = bufferedImage.getRGB(xx, yy);
                                int alpha = (color & 0xff000000) >> 24;
                                int red = (color & 0x00ff0000) >> 16;
                                int green = (color & 0x0000ff00) >> 8;
                                int blue = color & 0x000000ff;
                                ch[red / 64][green / 64][blue / 64]++;
                            }
                        for(int i = 0; i < ch.length; i++)
                            for(int j = 0; j < ch[i].length; j++)
                                for(int v = 0; v < ch[i][j].length; v++)
                                    System.out.println("t[" + i + "][" + j + "][" + v + "] = " + ch[i][j][v]);
                        //TODO --> end color Hist..
                        //Storage image after convert
                        ImageIO.write(bufferedImage, "gif", new File("D:\\save\\grey\\grayScaleImage.gif"));
                        ImageIO.write(bufferedImage,"png",new File("D:\\save\\grey\\grayScaleImage.png"));
                        ImageIcon imageIcon1 = new ImageIcon("D:\\save\\grey\\grayScaleImage.gif"); // load the image to a imageIcon
                        Image image2 = imageIcon1.getImage(); // transform it
                        Image newImage1 = ((Image) image2).getScaledInstance(300, 300,  Image.SCALE_AREA_AVERAGING);
                        // scale it the smooth way
                        imageIcon1 = new ImageIcon(newImage1);
                        showImageInFirstState.setIcon(imageIcon1);

                    } catch (IOException ex) {

                        throw new RuntimeException(ex);
                    }



                }
        );
        toRed.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {


                        ImageIcon imageIcon = new ImageIcon(pathForInitialImage); // load the image to a imageIcon
                        Image image = imageIcon.getImage(); // transform it
                        Image newImage = ((Image) image).getScaledInstance(200, 200,  java.awt.Image.SCALE_SMOOTH );
                        imageIcon = new ImageIcon(newImage);
                        imageInitial =imageIcon;

                        try {
                            bufferedImageForScale =ImageIO.read(FileNameForInitialState);
                            bufferedImageForBlur =ImageIO.read(FileNameForInitialState);
                            bufferedImage =ImageIO.read(FileNameForInitialState);
                            System.out.println("Convert to red");
//                            BufferedImage grayScaleImage =new BufferedImage(h,w,BufferedImage.TYPE_INT_ARGB);
                            var height = bufferedImage.getHeight();
                            var width = bufferedImage.getWidth();
                            for (int x =0 ; x<width ; x++){
                                for (int y =0 ; y<height ; y++ ){
                                    int p = bufferedImage.getRGB(x,y);
                                    int a= (p>>24)&0xff;
                                    int r= (p>>16)&0xff;
                                    p= (a << 24) | (r << 16) | (0);
                                    bufferedImage.setRGB(x,y,p);
                                }
                            }
                            //TODO -> print color H in the copy image with green color
                            int[][][] ch = new int[4][4][4];
                            for(int xx = 0; xx < bufferedImage.getWidth(); xx++)
                                for(int yy = 0; yy < bufferedImage.getHeight(); yy++) {
                                    int color = bufferedImage.getRGB(xx, yy);
                                    int alpha = (color & 0xff000000) >> 24;
                                    int red = (color & 0x00ff0000) >> 16;
                                    int green = (color & 0x0000ff00) >> 8;
                                    int blue = color & 0x000000ff;
                                    ch[red / 64][green / 64][blue / 64]++;
                                }
                            for(int i = 0; i < ch.length; i++)
                                for(int j = 0; j < ch[i].length; j++)
                                    for(int v = 0; v < ch[i][j].length; v++)
                                        System.out.println("t[" + i + "][" + j + "][" + v + "] = " + ch[i][j][v]);
                            //TODO --> end color Hist..
                            //Storage image after convert
                            ImageIO.write(bufferedImage, "gif", new File("D:\\save\\toRed\\grayScaleImage.gif"));
                            ImageIO.write(bufferedImage,"png",new File("D:\\save\\toRed\\grayScaleImage.png"));
                            ImageIcon imageIcon1 = new ImageIcon("D:\\save\\toRed\\grayScaleImage.gif"); // load the image to a imageIcon
                            Image image2 = imageIcon1.getImage(); // transform it
                            Image newImage1 = ((Image) image2).getScaledInstance(300, 300,  Image.SCALE_AREA_AVERAGING);
                            // scale it the smooth way
                            imageIcon1 = new ImageIcon(newImage1);
                            showImageInFirstState.setIcon(imageIcon1);

                        } catch (IOException ex) {

                            throw new RuntimeException(ex);
                        }



                    }}
        );
        brighten.addActionListener(
                e -> {


                    ImageIcon imageIcon = new ImageIcon(pathForInitialImage); // load the image to a imageIcon
                    Image image = imageIcon.getImage(); // transform it
                    Image newImage = ((Image) image).getScaledInstance(200, 200,  Image.SCALE_SMOOTH );
                    imageIcon = new ImageIcon(newImage);
                    imageInitial =imageIcon;

                    try {
                        bufferedImageForScale =ImageIO.read(FileNameForInitialState);
                        bufferedImageForBlur =ImageIO.read(FileNameForInitialState);
                        bufferedImage =ImageIO.read(FileNameForInitialState);
                        System.out.println("brighten_image");
//                            BufferedImage grayScaleImage =new BufferedImage(h,w,BufferedImage.TYPE_INT_ARGB);
                        bufferedImageForScale = brighten(bufferedImageForScale , 25);
                        System.out.println(bufferedImageForScale);
                        showImageINSecondState.setIcon(new ImageIcon(bufferedImageForScale));
                        System.out.println(bufferedImageForScale);
                        showImageINSecondState.setIcon(new ImageIcon(bufferedImageForScale));

                        //TODO -> print color Hit in the copy image with green color
                        int[][][] ch = new int[4][4][4];
                        for(int xx = 0; xx < bufferedImageForScale.getWidth(); xx++)
                            for(int yy = 0; yy < bufferedImageForScale.getHeight(); yy++) {
                                int color = bufferedImageForScale.getRGB(xx, yy);
                                int alpha = (color & 0xff000000) >> 24;
                                int red = (color & 0x00ff0000) >> 16;
                                int green = (color & 0x0000ff00) >> 8;
                                int blue = color & 0x000000ff;
                                ch[red / 64][green / 64][blue / 64]++;
                            }
                        for(int i = 0; i < ch.length; i++)
                            for(int j = 0; j < ch[i].length; j++)
                                for(int v = 0; v < ch[i][j].length; v++)
                                    System.out.println("t[" + i + "][" + j + "][" + v + "] = " + ch[i][j][v]);
                        //TODO --> end color Hist..
                        //Storage image after convert
                        ImageIO.write(bufferedImageForScale, "gif", new File("D:\\save\\grey\\grayScaleImage.gif"));
                        ImageIO.write(bufferedImageForScale,"png",new File("D:\\save\\grey\\grayScaleImage.png"));
                        ImageIcon imageIcon1 = new ImageIcon("D:\\save\\grey\\grayScaleImage.gif"); // load the image to a imageIcon
                        Image image2 = imageIcon1.getImage(); // transform it
                        Image newImage1 = ((Image) image2).getScaledInstance(300, 300,  Image.SCALE_AREA_AVERAGING);
                        // scale it the smooth way
                        imageIcon1 = new ImageIcon(newImage1);
                        showImageInFirstState.setIcon(imageIcon1);

                    } catch (IOException ex) {

                        throw new RuntimeException(ex);
                    }



                }
        );


        toGreen.addActionListener(
                e -> {


                    ImageIcon imageIcon = new ImageIcon(pathForInitialImage); // load the image to a imageIcon
                    Image image = imageIcon.getImage(); // transform it
                    Image newImage = image.getScaledInstance(200, 200,  Image.SCALE_SMOOTH );
                    imageIcon = new ImageIcon(newImage);
                    imageInitial =imageIcon;

                    try {
                        bufferedImageForScale =ImageIO.read(FileNameForInitialState);
                        bufferedImageForBlur =ImageIO.read(FileNameForInitialState);
                        bufferedImage =ImageIO.read(FileNameForInitialState);


//                            BufferedImage grayScaleImage =new BufferedImage(h,w,BufferedImage.TYPE_INT_ARGB);
                        var height = bufferedImage.getHeight();
                        var width = bufferedImage.getWidth();
                        for (int x =0 ; x<width ; x++){
                            for (int y =0 ; y<height ; y++ ){
                                int p = bufferedImage.getRGB(x,y);
                                int a= (p>>24)&0xff;
                                int g= (p>>8)&0xff;
                                p= (a << 24) | (0) | (g << 8);
                                bufferedImage.setRGB(x,y,p);
                                System.out.println( g);
                            }
                        }
                        //TODO -> print color Hit in the copy image with green color
                        int[][][] ch = new int[4][4][4];
                        for(int xx = 0; xx < bufferedImage.getWidth(); xx++)
                            for(int yy = 0; yy < bufferedImage.getHeight(); yy++) {
                                int color = bufferedImage.getRGB(xx, yy);
                                int alpha = (color & 0xff000000) >> 24;
                                int red = (color & 0x00ff0000) >> 16;
                                int green = (color & 0x0000ff00) >> 8;
                                int blue = color & 0x000000ff;
                                ch[red / 64][green / 64][blue / 64]++;

                            }
//                        getSpecificColor(image , xx,yy);

                        for(int i = 0; i < ch.length; i++)
                            for(int j = 0; j < ch[i].length; j++)
                                for(int v = 0; v < ch[i][j].length; v++)
                                    System.out.println("t[" + i + "][" + j + "][" + v + "] = " + ch[i][j][v]);


                        //TODO --> end color Hist..
                        //Storage image after convert
                        ImageIO.write(bufferedImage, "gif", new File("D:\\save\\grey\\grayScaleImage.gif"));
                        ImageIO.write(bufferedImage,"png",new File("D:\\save\\grey\\grayScaleImage.png"));
                        ImageIcon imageIcon1 = new ImageIcon("D:\\save\\grey\\grayScaleImage.gif"); // load the image to a imageIcon
                        Image image2 = imageIcon1.getImage(); // transform it
                        Image newImage1 = image2.getScaledInstance(300, 300,  Image.SCALE_AREA_AVERAGING);
                        // scale it the smooth way
                        imageIcon1 = new ImageIcon(newImage1);
                        showImageInFirstState.setIcon(imageIcon1);

                    } catch (IOException ex) {

                        throw new RuntimeException(ex);
                    }

                }
        );
        this.add(toGreen);

    }

    public static BufferedImage detectEdges (BufferedImage img) {
        int h = img.getHeight(), w = img.getWidth(), threshold=30, p = 0;
        BufferedImage edgeImg = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_GRAY);
        int[][] vert = new int[w][h];
        int[][] horiz = new int[w][h];
        int[][] edgeWeight = new int[w][h];
        for (int y=1; y<h-1; y++) {
            for (int x=1; x<w-1; x++) {
                vert[x][y] = (int)(img.getRGB(x+1, y-1)& 0xFF + 2*(img.getRGB(x+1, y)& 0xFF) + img.getRGB(x+1, y+1)& 0xFF
                        - img.getRGB(x-1, y-1)& 0xFF - 2*(img.getRGB(x-1, y)& 0xFF) - img.getRGB(x-1, y+1)& 0xFF);
                horiz[x][y] = (int)(img.getRGB(x-1, y+1)& 0xFF + 2*(img.getRGB(x, y+1)& 0xFF) + img.getRGB(x+1, y+1)& 0xFF
                        - img.getRGB(x-1, y-1)& 0xFF - 2*(img.getRGB(x, y-1)& 0xFF) - img.getRGB(x+1, y-1)& 0xFF);
                edgeWeight[x][y] = (int)(Math.sqrt(vert[x][y] * vert[x][y] + horiz[x][y] * horiz[x][y]));
                if (edgeWeight[x][y] > threshold)
                    p = (255<<24) | (255<<16) | (255<<8) | 255;
                else {
                    p = (255 << 24) | (0);
                }
                edgeImg.setRGB(x,y,p);
            }
        }
        return edgeImg;
    }

    // make the photo blur <<<<-------------
    public static BufferedImage blur (BufferedImage img) {
        BufferedImage blurImg = new BufferedImage(
                img.getWidth()-2, img.getHeight()-2, BufferedImage.TYPE_BYTE_GRAY);

        int pix = 0;
        for (int y=0; y<blurImg.getHeight(); y++) {
            for (int x=0; x<blurImg.getWidth(); x++) {
                pix = (int)(4*(img.getRGB(x+1, y+1)& 0xFF)
                        + 2*(img.getRGB(x+1, y)& 0xFF)
                        + 2*(img.getRGB(x+1, y+2)& 0xFF)
                        + 2*(img.getRGB(x, y+1)& 0xFF)
                        + 2*(img.getRGB(x+2, y+1)& 0xFF)
                        + (img.getRGB(x, y)& 0xFF)
                        + (img.getRGB(x, y+2)& 0xFF)
                        + (img.getRGB(x+2, y)& 0xFF)
                        + (img.getRGB(x+2, y+2)& 0xFF))/16;
                int p = (255<<24) | (pix<<16) | (pix<<8) | pix;
                Color color = new Color(img.getRGB(x, y));
                blurImg.setRGB(x,y,p);

            }
        }

        return blurImg;


    }
    // make the photo gray
    public static BufferedImage toGrayScale2 (BufferedImage img) throws IOException {
        System.out.println("  Converting to GrayScale2.");
        System.out.println(img.getWidth()+""+ img.getTileHeight());
        BufferedImage grayImage = new BufferedImage(
                img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        int [][]pix =new int [img.getWidth()][img.getHeight()];
        int rgb=0, r=0, g=0, b=0;
        int []rgpPix=new int [rgb];
        for (int y=0; y<img.getHeight(); y++) {
            for (int x=0; x<img.getWidth(); x++) {
                rgb = (int)(img.getRGB(x, y));
                r = ((rgb >> 16) & 0xFF);
                g = ((rgb >> 8) & 0xFF);
                b = (rgb & 0xFF);
                rgb = (int)((r+g+b)/3);
                rgb = (255<<24) | (rgb<<16) | (rgb<<8) | rgb;

                grayImage.setRGB(x,y,rgb);
//                System.out.println("       "+ r + "           "+ g + "           " +b+"       " );
            }
        }



        ImageIO.write(grayImage, "gif", new File("D:\\save\\majd\\"+ getImageName()+"e.gif"));

        return grayImage;
    }
    // on press in the button
    @Override
    public void actionPerformed(ActionEvent e) {

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
        String newPath="D:\\save\\saveImage\\";
        System.out.println(newPath);



              }

    // rename images was selected
    public static String getImageName(){
        int upperbound = 100000;

        Random rand = new Random();

        int int_random = rand.nextInt(upperbound);

        return "imageName"+int_random;
    }


    public static BufferedImage brighten (BufferedImage img, int percentage) {
        int r=0, g=0, b=0, rgb=0, p=0;
        int amount = (int)(percentage * 255 / 100); // rgb scale is 0-255, so 255 is 100%
        BufferedImage newImage = new BufferedImage(
                img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int y=0; y<img.getHeight(); y+=1) {
            for (int x=0; x<img.getWidth(); x+=1) {
                rgb = img.getRGB(x, y);
                r = ((rgb >> 16) & 0xFF) + amount;
                g = ((rgb >> 8) & 0xFF) + amount;
                b = (rgb & 0xFF) + amount;
                if (r>255) r=255;
                if (g>255) g=255;
                if (b>255) b=255;
                p = (255<<24) | (r<<16) | (g<<8) | b;
                System.out.println("       "+ r + "           "+ g + "           " +b+"       " );

                newImage.setRGB(x,y,p);
            }
        }
        return newImage;
    }
//    public static void getSpecificColor(Image image, int x, int y) {
//        if (image instanceof BufferedImage) {
//            new Color(((BufferedImage) image).getRGB(x, y));
//            return;
//        }
//        int width = image.getWidth(null);
//        int height = image.getHeight(null);
//        int[] pixels = new int[width * height];
//        PixelGrabber grabber = new PixelGrabber(image, 0, 0, width, height, pixels, 0, width);
//        try {
//            grabber.grabPixels();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        int c = pixels[x * width + y];
//        int  red = (c & 0x00ff0000) >> 16;
//        int  green = (c & 0x0000ff00) >> 8;
//        int  blue = c & 0x000000ff;
//        System.out.println( "" +red + "              " + "          " + green +"            "+ blue );
//
//        new Color(red, green, blue);
//    }

}
