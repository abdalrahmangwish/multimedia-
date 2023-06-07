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
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;


public class TheSecondPart extends JFrame implements  ActionListener {
    JButton selectImage;
    JButton searchImageForRedColorImage;
    JButton searchImageForGreenColorImage;
    JButton searchForMostImage;
    JButton searchImageForSameDate;
    JButton searchImageForSameDimensions;
    JButton searchImageForInputColor;
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
        searchImageForRedColorImage = new JButton("searchImageForRedColorImage");
        searchImageForRedColorImage.setBounds(700 , 200, 300 , 30);
        this.add(searchImageForRedColorImage);
        //TODO
        searchForMostImage = new JButton("searchForMostImage");
        searchForMostImage.setBounds(700 , 300, 300 , 30);
        this.add(searchForMostImage);
        //TODO
        searchImageForSameDate = new JButton("searchImageForSameDate");
        searchImageForSameDate.setBounds(700 , 400, 300 , 30);
        this.add(searchImageForSameDate);
        //TODO
        searchImageForSameDimensions = new JButton("searchImageForSameDimensions");
        searchImageForSameDimensions.setBounds(700 , 500, 300 , 30);
        this.add(searchImageForSameDimensions);
        //TODO
        searchImageForInputColor = new JButton("searchImageForInputColor");
        searchImageForInputColor.setBounds(700 , 600, 300 , 30);
        this.add(searchImageForInputColor);
searchImageForGreenColorImage.addActionListener(
        e -> {
            String targetImagePathForGreenImage = "C:\\Users\\AbdAlrahman\\Desktop\\saves\\12.jpg";
            String directoryPathForGreenImage = "C:\\Users\\AbdAlrahman\\Desktop\\saves";
            String outputDirectoryPathForGreenImage = "C:\\Users\\AbdAlrahman\\Desktop\\saves\\greenImage";

            File directory = new File(directoryPathForGreenImage);
            File[] imageFiles = directory.listFiles();

            BufferedImage targetImage;
            try {
                targetImage = ImageIO.read(new File(targetImagePathForGreenImage));
            } catch (IOException ex) {
                ex.printStackTrace();
                return;
            }

            List<File> similarImages = new ArrayList<>();

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
                    double greenThreshold = 0.4;

                    if (greenRatio >= greenThreshold) {
                        similarImages.add(imageFile);
                    }
                }
            }

            File outputDirectory = new File(outputDirectoryPathForGreenImage);
            if (!outputDirectory.exists()) {
                outputDirectory.mkdirs();
            }

            if (!similarImages.isEmpty()) {
                System.out.println("Similar images:");
                for (File imageFile : similarImages) {
                    String outputImagePath = outputDirectoryPathForGreenImage + File.separator + imageFile.getName();
                    File outputFile = new File(outputImagePath);
                    try {
                        ImageIO.write(ImageIO.read(imageFile), "jpg", outputFile);
                        System.out.println(outputImagePath);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            } else {
                System.out.println("No similar images found.");
            }
        });
searchImageForSameDate.addActionListener(
        e -> {
            String targetImagePathForSameDate = pathForInitialImage ;
            String searchDirectoryPathForSameDate  = "C:\\Users\\AbdAlrahman\\Desktop\\saves" ;
            String outputDirectoryPathForSameDate = "C:\\Users\\AbdAlrahman\\Desktop\\saves\\same_date";

            File targetImageFile = new File(targetImagePathForSameDate);
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

            File searchDirectory = new File(searchDirectoryPathForSameDate);
            if (!searchDirectory.exists()) {
                System.out.println("Search directory does not exist.");
                return;
            }

            File outputDirectory = new File(outputDirectoryPathForSameDate);
            if (!outputDirectory.exists()) {
                outputDirectory.mkdirs();
            }

            File[] imageFiles = searchDirectory.listFiles();
            for (File imageFile : imageFiles) {
                if (imageFile.isFile()) {
                    try {
                        BasicFileAttributes attrs = Files.readAttributes(imageFile.toPath(), BasicFileAttributes.class);
                        Date creationDate = new Date(attrs.creationTime().toMillis());

                        // تحويل التاريخ إلى سلسلة نصية باستخدام تنسيق محدد
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        String targetDateStr = dateFormat.format(creationDate);
                        String inputDateStr = dateFormat.format(targetImageFile.lastModified());

                        if (inputDateStr.equals(targetDateStr)) {
                            // تخزين الصورة المطابقة في المجلد المحدد
                            String outputFilePath = outputDirectoryPathForSameDate + "/" + imageFile.getName();
                            Path outputPath = Paths.get(outputFilePath);
                            Files.copy(imageFile.toPath(), outputPath, StandardCopyOption.REPLACE_EXISTING);
                            System.out.println("Image stored: " + outputFilePath);
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
searchImageForSameDimensions.addActionListener(
        e -> {
            String targetImagePathForSameDimensions = pathForInitialImage ;
            String searchDirectoryPathForSameDimensions  = "C:\\Users\\AbdAlrahman\\Desktop\\saves" ;
            String outputDirectoryPathForSameDimensions = "C:\\Users\\AbdAlrahman\\Desktop\\saves\\same_dimensions"; // مسار المجلد الذي ستتم فيه تخزين الصور المطابقة

            File targetImageFile = new File(targetImagePathForSameDimensions);
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

            File searchDirectory = new File(searchDirectoryPathForSameDimensions);
            if (!searchDirectory.exists()) {
                System.out.println("Search directory does not exist.");
                return;
            }

            File outputDirectory = new File(outputDirectoryPathForSameDimensions);
            if (!outputDirectory.exists()) {
                outputDirectory.mkdirs();
            }

            File[] imageFiles = searchDirectory.listFiles();
            for (File imageFile : imageFiles) {
                if (imageFile.isFile()) {
                    BufferedImage image;
                    try {
                        image = ImageIO.read(imageFile);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        continue;
                    }

                    int imageWidth = image.getWidth();
                    int imageHeight = image.getHeight();

                    if (imageWidth == targetWidth && imageHeight == targetHeight) {
                        // تخزين الصورة المطابقة في المجلد المحدد
                        String outputFilePath = outputDirectoryPathForSameDimensions + "/" + imageFile.getName();
                        File outputFile = new File(outputFilePath);
                        try {
                            ImageIO.write(image, "jpg", outputFile);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }

                        System.out.println("Matching image found: " + imageFile.getAbsolutePath());
                    }
                }
            }
        });
searchImageForInputColor.addActionListener(
        e -> {        String directoryPathForInputColor = "C:\\Users\\AbdAlrahman\\Desktop\\saves";
            String outputDirectoryPathForInputColor = "C:\\Users\\AbdAlrahman\\Desktop\\saves\\input_Image";

            File directory = new File(directoryPathForInputColor);
            File[] imageFiles = directory.listFiles();

            List<File> similarImages = new ArrayList<>();

            Color targetColor = new Color(255, 0, 0); // اللون المطلوب (يمكن تعديله هنا)

            for (File imageFile : imageFiles) {
                if (imageFile.isFile()) {
                    BufferedImage image;
                    try {
                        image = ImageIO.read(imageFile);
                    } catch (IOException eX) {
                        eX.printStackTrace();
                        continue;
                    }

                    if (image == null) {
                        continue; // تجاهل الصورة إذا كانت غير صالحة
                    }

                    boolean isSimilar = checkColorSimilarity(image, targetColor);

                    if (isSimilar) {
                        similarImages.add(imageFile);
                    }
                }
            }

            File outputDirectory = new File(outputDirectoryPathForInputColor);
            if (!outputDirectory.exists()) {
                outputDirectory.mkdirs();
            }

            if (!similarImages.isEmpty()) {
                System.out.println("Similar images:");
                for (File imageFile : similarImages) {
                    String outputImagePath = outputDirectoryPathForInputColor + File.separator + imageFile.getName();
                    File outputFile = new File(outputImagePath);
                    try {
                        ImageIO.write(ImageIO.read(imageFile), "jpg", outputFile);
                        System.out.println(outputImagePath);
                    } catch (IOException eX) {
                        eX.printStackTrace();
                    }
                }
            } else {
                System.out.println("No similar images found.");
            }
        });
searchForMostImage.addActionListener(
        e -> {
            String targetImagePath = "C:\\Users\\AbdAlrahman\\Desktop\\saves\\12.jpg";
            String directoryPath = "C:\\Users\\AbdAlrahman\\Desktop\\saves";

            File directory = new File(directoryPath);
            File[] imageFiles = directory.listFiles();

            BufferedImage targetImage;
            try {
                targetImage = ImageIO.read(new File(targetImagePath));
            } catch (IOException ex) {
                ex.printStackTrace();
                return;
            }

            List<File> similarImages = new ArrayList<>();

            for (File imageFile : imageFiles) {
                if (imageFile.isFile()) {
                    BufferedImage image;
                    try {
                        image = ImageIO.read(imageFile);
                        if (image == null) {
                            continue;
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        continue;
                    }

                    if (image.getWidth() != targetImage.getWidth() || image.getHeight() != targetImage.getHeight()) {
                        continue; // تخطي الصورة إذا كانت ليست لها نفس الأبعاد
                    }

                    double difference = compareImages(targetImage, image);

                    double threshold = 100.0;
                    if (difference < threshold) {
                        similarImages.add(imageFile);
                    }
                }
            }

            if (!similarImages.isEmpty()) {
                System.out.println("Similar images found:");
                for (File similarImage : similarImages) {
                    System.out.println(similarImage.getAbsolutePath());
                }
            } else {
                System.out.println("No similar images found.");
            }
        });
searchImageForRedColorImage.addActionListener(
        e -> {
            String targetImagePathForRedImage = pathForInitialImage;
            String directoryPathForRedImage = "C:\\Users\\AbdAlrahman\\Desktop\\saves";
            String outputDirectoryPathForRedImage = "C:\\Users\\AbdAlrahman\\Desktop\\saves\\redImage"; // مسار المجلد الذي سيتم تخزين الصور فيه

            File directory = new File(directoryPathForRedImage);
            File[] imageFiles = directory.listFiles();

            BufferedImage targetImage;
            try {
                targetImage = ImageIO.read(new File(targetImagePathForRedImage));
            } catch (IOException ex) {
                ex.printStackTrace();
                return;
            }

            List<File> similarImages = new ArrayList<>();

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
                        continue;
                    }

                    double redRatio = getRedRatio(image);

                    // يمكن تعديل القيمة هنا بناءً على مدى اللون الأحمر المطلوب
                    double redThreshold = 0.4;

                    if (redRatio >= redThreshold) {
                        similarImages.add(imageFile);
                    }
                }
            }

            // إنشاء مجلد لتخزين الصور الناتجة
            File outputDirectory = new File(outputDirectoryPathForRedImage);
            if (!outputDirectory.exists()) {
                outputDirectory.mkdirs();
            }

            if (!similarImages.isEmpty()) {
                System.out.println("Similar images:");
                for (File imageFile : similarImages) {
                    // حفظ الصور في المجلد الناتج
                    String outputImagePath = outputDirectoryPathForRedImage + File.separator + imageFile.getName();
                    File outputFile = new File(outputImagePath);
                    try {
                        ImageIO.write(ImageIO.read(imageFile), "jpg", outputFile);
                        System.out.println(outputImagePath);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            } else {
                System.out.println("No similar images found.");
            }
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

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
