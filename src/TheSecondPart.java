import services.ImageColorSimilarityForInputImage;
import services.SearchCropImageForMostColor;
import services.SearchResizeImageForMostColor;

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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;


public class TheSecondPart extends JFrame implements  ActionListener {
    JButton selectImage;
    JButton searchImageForGreenColorImage;
    JButton searchImageForSameDate;
    JButton searchImageForSameDimensions;
    JButton searchImageForInputColor;
    JButton searchImageForCropInputImage;
    JButton searchImageForResizeInputImage;
    JLabel introLabel;

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

    selectImage = new JButton("Select Image");
    selectImage.setBounds(50, 50 , 200 , 100);
    this.add(selectImage);
    showImageInInitialState =new JLabel();
    showImageInInitialState.setBounds(50 ,200 ,500,500);
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
                    Image newimg = ((Image) image).getScaledInstance(500, 500,  Image.SCALE_SMOOTH); // scale it the smooth way
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
        introLabel = new JLabel( "Hey let's do  a lot of process ");
        introLabel.setBounds(700 , 50 , 300 , 40 );
        this.add(introLabel);
        //TODO
        searchImageForGreenColorImage = new JButton("searchImageForGreenColorImage");
        searchImageForGreenColorImage.setBounds(700 , 100, 300 , 30);
        this.add(searchImageForGreenColorImage);
         //TODO
        searchImageForSameDate = new JButton("searchImageForSameDate");
        searchImageForSameDate.setBounds(700 , 300, 300 , 30);
        this.add(searchImageForSameDate);
        //TODO
        searchImageForSameDimensions = new JButton("searchImageForSameDimensions");
        searchImageForSameDimensions.setBounds(700 , 400, 300 , 30);
        this.add(searchImageForSameDimensions);
        //TODO
        searchImageForInputColor = new JButton("searchImageForInputColor");
        searchImageForInputColor.setBounds(700 , 500, 300 , 30);
        this.add(searchImageForInputColor);
        //TODO
        searchImageForCropInputImage = new JButton("searchImageForCropInputImage");
        searchImageForCropInputImage.setBounds(700 , 200, 300 , 30);
        this.add(searchImageForCropInputImage);
        //TODO
        searchImageForResizeInputImage = new JButton("searchImageForResizeInputImage");
        searchImageForResizeInputImage.setBounds(700 , 600, 300 , 30);
        this.add(searchImageForResizeInputImage);
        searchImageForResizeInputImage.addActionListener(
                e -> {
                SwingUtilities.invokeLater(new Runnable() {public void run() {new SearchResizeImageForMostColor();}});
                });
        //TODO search a lot of path directory
        searchImageForGreenColorImage.addActionListener(
                e -> {
            SwingUtilities.invokeLater(() -> new ImageDisplayForGreenImage());
        });
        searchImageForSameDate.addActionListener(
                e -> {
            String targetImagePath = pathForInitialImage;
            String[] searchDirectoryPaths = {"C:\\Users\\AbdAlrahman\\Desktop\\saves"};
            String outputDirectoryPath = "C:\\Users\\AbdAlrahman\\Desktop\\saves\\sssssssssss";

            ImageSearchAndDisplay imageSearchAndDisplay = new ImageSearchAndDisplay();
            imageSearchAndDisplay.searchAndDisplayImages(targetImagePath, searchDirectoryPaths, outputDirectoryPath);
        });
        searchImageForSameDimensions.addActionListener(
                e -> {
            ImageDisplayForSameDimensions imageDisplay = new ImageDisplayForSameDimensions();

        });
        searchImageForInputColor.addActionListener(
                e -> {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new ImageColorSimilarityForInputImage();
                }
            });
        });
        searchImageForCropInputImage.addActionListener(
                 e->{
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    new SearchCropImageForMostColor();
                }
            });
        });
    }


//TODO ******************************************************************************************************************************
    //TODO This Methods For Search Green image
private static double getGreenRatio(BufferedImage image) {
    int width = image.getWidth();
    int height = image.getHeight();
    int greenCount = 0;
    int totalPixels = width * height;

    for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
            Color color = new Color(image.getRGB(x, y));
            int greenValue = color.getGreen();

            // قيمة عتبة اللون الأخضر يمكن تعديلها هنا
            int greenThreshold = 100;

            if (greenValue >= greenThreshold) {
                greenCount++;
            }
        }
    }

    return (double) greenCount / totalPixels;
}
//TODO**************************************************************************************************************************
    //TODO This Methods For Search Input Image
private static boolean checkColorSimilarity(BufferedImage image, Color targetColor) {
    int width = image.getWidth();
    int height = image.getHeight();

    int targetRed = targetColor.getRed();
    int targetGreen = targetColor.getGreen();
    int targetBlue = targetColor.getBlue();

    // الحد الأدنى للفروق في الألوان
    int colorThreshold = 50;

    for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
            Color pixelColor = new Color(image.getRGB(x, y));

            int red = pixelColor.getRed();
            int green = pixelColor.getGreen();
            int blue = pixelColor.getBlue();

            int redDiff = Math.abs(red - targetRed);
            int greenDiff = Math.abs(green - targetGreen);
            int blueDiff = Math.abs(blue - targetBlue);

            if (redDiff <= colorThreshold && greenDiff <= colorThreshold && blueDiff <= colorThreshold) {
                return true; // تطابق اللون
            }
        }
    }

    return false; // لم يتم العثور على تطابق في الألوان
}
//TODO**********************************************************************************************************************************
    //TODO This Methods For Most Image Similar
private static double compareImages(BufferedImage image1, BufferedImage image2) {
    int width1 = image1.getWidth();
    int height1 = image1.getHeight();
    int width2 = image2.getWidth();
    int height2 = image2.getHeight();

    if (width1 != width2 || height1 != height2) {
        throw new IllegalArgumentException("Images must have the same dimensions");
    }

    double difference = 0.0;

    for (int y = 0; y < height1; y++) {
        for (int x = 0; x < width1; x++) {
            int rgb1 = image1.getRGB(x, y);
            int rgb2 = image2.getRGB(x, y);

            int red1 = (rgb1 >> 16) & 0xFF;
            int green1 = (rgb1 >> 8) & 0xFF;
            int blue1 = rgb1 & 0xFF;

            int red2 = (rgb2 >> 16) & 0xFF;
            int green2 = (rgb2 >> 8) & 0xFF;
            int blue2 = rgb2 & 0xFF;

            double redDiff = Math.abs(red1 - red2);
            double greenDiff = Math.abs(green1 - green2);
            double blueDiff = Math.abs(blue1 - blue2);

            difference += redDiff + greenDiff + blueDiff;
        }
    }

    double totalPixels = width1 * height1;
    double averageDifference = difference / totalPixels;

    return averageDifference;
}

//TODO**********************************************************************************************************************************
    //TODO This Methods  For Search Red image
    private static double getRedRatio(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int redCount = 0;
        int totalPixels = width * height;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = new Color(image.getRGB(x, y));
                int redValue = color.getRed();

                // يمكن تعديل القيمة هنا بناءً على مدى اللون الأحمر المطلوب
                int redThreshold = 100;

                if (redValue >= redThreshold) {
                    redCount++;
                }
            }
        }

        return (double) redCount / totalPixels;
    }
//TODO**********************************************************************************************************************************
    //TODO This Methods  For Search Input Crop image
    public static class ImageDisplayForGreenImage extends JFrame { public ImageDisplayForGreenImage() {
            setTitle("Similar Images");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(600, 400);
            setLocationRelativeTo(null);

            JPanel panel = new JPanel();
            panel.setLayout(new FlowLayout());

            List<String> directoryPaths = new ArrayList<>();
            directoryPaths.add("C:\\Users\\AbdAlrahman\\Desktop\\saves");
            directoryPaths.add("C:\\Users\\AbdAlrahman\\Desktop\\another_folder");

            String targetImagePath = "C:\\Users\\AbdAlrahman\\Desktop\\saves\\12.jpg";
            String outputDirectoryPath = "C:\\Users\\AbdAlrahman\\Desktop\\saves\\greenImage";

            List<File> similarImages = new ArrayList<>();

            for (String directoryPathForGreenImage : directoryPaths) {
                File directory = new File(directoryPathForGreenImage);
                File[] imageFiles = directory.listFiles();

                if (imageFiles != null) {
                    for (File imageFile : imageFiles) {
                        if (imageFile.isFile()) {
                            BufferedImage image;
                            try {
                                image = ImageIO.read(imageFile);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                                continue;
                            }

                            if (image == null) {
                                continue; // تجاهل الصورة إذا كانت غير صالحة
                            }

                            double greenRatio = getGreenRatio(image);

                            // قيمة عتبة اللون الأخضر يمكن تعديلها هنا
                            double greenThreshold = 1;

                            if (greenRatio >= greenThreshold) {
                                similarImages.add(imageFile);

                                int scaledWidth = 200; // العرض المطلوب للصورة المصغرة
                                int scaledHeight = 150; // الارتفاع المطلوب للصورة المصغرة
                                Image scaledImage = image.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);

                                ImageIcon icon = new ImageIcon(scaledImage);
                                JLabel label = new JLabel(icon);
                                panel.add(label);
                            }
                        }
                    }
                }
            }

            JScrollPane scrollPane = new JScrollPane(panel);
            add(scrollPane);

            setVisible(true);
        }}

    public static class ImageSearchAndDisplay extends JFrame {
        private JPanel panel;

        public ImageSearchAndDisplay() {
            setTitle("Image Search and Display");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(600, 400);
            setLocationRelativeTo(null);

            panel = new JPanel();
            panel.setLayout(new FlowLayout());
            JScrollPane scrollPane = new JScrollPane(panel);
            add(scrollPane);

            setVisible(true);
        }

        public void searchAndDisplayImages(String targetImagePath, String[] searchDirectoryPaths, String outputDirectoryPath) {
            File targetImageFile = new File(targetImagePath);
            if (!targetImageFile.exists()) {
                System.out.println("Target image does not exist.");
                return;
            }

            BufferedImage targetImage;
            try {
                targetImage = ImageIO.read(targetImageFile);
            } catch (IOException ex) {
                ex.printStackTrace();
                return;
            }

            File outputDirectory = new File(outputDirectoryPath);
            if (!outputDirectory.exists()) {
                outputDirectory.mkdirs();
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String targetDateStr = dateFormat.format(targetImageFile.lastModified());

            for (String searchDirectoryPath : searchDirectoryPaths) {
                File searchDirectory = new File(searchDirectoryPath);
                if (!searchDirectory.exists()) {
                    System.out.println("Search directory does not exist: " + searchDirectoryPath);
                    continue;
                }

                File[] imageFiles = searchDirectory.listFiles();
                for (File imageFile : imageFiles) {
                    if (imageFile.isFile()) {
                        try {
                            BasicFileAttributes attrs = Files.readAttributes(imageFile.toPath(), BasicFileAttributes.class);
                            Date creationDate = new Date(attrs.creationTime().toMillis());
                            String inputDateStr = dateFormat.format(creationDate);

                            if (inputDateStr.equals(targetDateStr) && !imageFile.equals(targetImageFile)) {
                                String outputFilePath = outputDirectoryPath + "/" + imageFile.getName();
                                Path outputPath = Path.of(outputFilePath);
                                Files.copy(imageFile.toPath(), outputPath, StandardCopyOption.REPLACE_EXISTING);
                                System.out.println("Image stored: " + outputFilePath);

                                BufferedImage image = ImageIO.read(imageFile);
                                if (image != null) {
                                    ImageIcon icon = new ImageIcon(image.getScaledInstance(150, 150, Image.SCALE_SMOOTH));
                                    JLabel label = new JLabel(icon);
                                    panel.add(label);
                                    revalidate();
                                    repaint();
                                }
                            }
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public class ImageDisplayForSameDimensions extends JFrame {
            private JPanel panel;

            public ImageDisplayForSameDimensions() {
                setTitle("Similar Images with Same Dimensions");
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setSize(600, 400);
                setLocationRelativeTo(null);

                panel = new JPanel();
                panel.setLayout(new FlowLayout());

                String targetImagePath = pathForInitialImage;
                String searchDirectoryPath = "C:\\Users\\AbdAlrahman\\Desktop\\saves";
                String outputDirectoryPath = "C:\\Users\\AbdAlrahman\\Desktop\\saves\\same_dimensions";

                File targetImageFile = new File(targetImagePath);
                if (!targetImageFile.exists()) {
                    System.out.println("Target image does not exist.");
                    return;
                }

                BufferedImage targetImage;
                try {
                    targetImage = ImageIO.read(targetImageFile);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    return;
                }

                int targetWidth = targetImage.getWidth();
                int targetHeight = targetImage.getHeight();

                File searchDirectory = new File(searchDirectoryPath);
                if (!searchDirectory.exists()) {
                    System.out.println("Search directory does not exist.");
                    return;
                }

                File outputDirectory = new File(outputDirectoryPath);
                if (!outputDirectory.exists()) {
                    outputDirectory.mkdirs();
                }

                File[] imageFiles = searchDirectory.listFiles();
                for (File imageFile : imageFiles) {
                    if (imageFile.isFile()) {
                        BufferedImage image;
                        try {
                            image = ImageIO.read(imageFile);
                            if (image == null) {
                                continue; // تجاهل الصورة إذا كانت غير صالحة
                            }
                        } catch (IOException ex) {
                            ex.printStackTrace();
                            continue;
                        }

                        int imageWidth = image.getWidth();
                        int imageHeight = image.getHeight();

                        if (imageWidth == targetWidth && imageHeight == targetHeight) {
                            // تخزين الصورة المطابقة في المجلد المحدد
                            String outputFilePath = outputDirectoryPath + "\\" + imageFile.getName();
                            File outputFile = new File(outputFilePath);
                            try {
                                ImageIO.write(image, "jpg", outputFile);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }

                            System.out.println("Matching image found: " + imageFile.getAbsolutePath());

                            // عرض الصورة المطابقة على الـ JPanel
                            int scaledWidth = 200; // العرض المطلوب للصورة المصغرة
                            int scaledHeight = 150; // الارتفاع المطلوب للصورة المصغرة
                            Image scaledImage = image.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);

                            ImageIcon icon = new ImageIcon(scaledImage);
                            JLabel label = new JLabel(icon);
                            panel.add(label);
                        }
                    }
                }

                JScrollPane scrollPane = new JScrollPane(panel);
                add(scrollPane);

                setVisible(true);
            }
        }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}

