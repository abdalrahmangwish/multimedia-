import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.DataBuffer;
import java.awt.image.IndexColorModel;
import java.io.File;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class TheFirstPart extends JFrame implements  ActionListener {
    //make interface
    JButton selectImage;
    JButton medianCut;
    JButton kMeansButton;
    JButton indexButton;
    String imagePath;
    JLabel initialImageLabel;
    JLabel ImageLabelForAllLogarithm;
    JLabel wlecomeLabel;
    File FileNameForBufferedImage;

    String newPathForCutImage= "C:\\Users\\AbdAlrahman\\Desktop\\saves\\MCut_image\\"+ getImageName()+".gif";
    String newPathForKMImage= "C:\\Users\\AbdAlrahman\\Desktop\\saves\\KM_image\\"+ getImageName()+".gif";
    String newPathForIndexImage= "C:\\Users\\AbdAlrahman\\Desktop\\saves\\index_image\\"+ getImageName()+".gif";

    TheFirstPart() {

        this.setName("The First Part ");
        setResizable(false);
        this.setTitle("Selection Image");
        this.setSize(2200, 1200);
        this.setVisible(true);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        selectImage = new JButton("selectImage");
        selectImage.setBounds(50, 50, 150, 30);
        this.add(selectImage);
        selectImage.addActionListener(this);
        initialImageLabel = new JLabel();
        initialImageLabel.setBounds(50, 120, 500, 500);
        this.add(initialImageLabel);
        medianCut = new JButton("Median Cut Color");
        medianCut.setBounds(500, 50, 200, 30);
        this.add(medianCut);
        kMeansButton =new JButton("K-Means Color");
        kMeansButton.setBounds(700 ,50,200,30);
        this.add(kMeansButton);
        ImageLabelForAllLogarithm =  new JLabel();
        ImageLabelForAllLogarithm.setBounds(1200,1200,500,500 );
        this.add(ImageLabelForAllLogarithm);
        indexButton = new JButton("index Image");
        indexButton.setBounds(900, 50 , 200 , 30);
        this.add(indexButton);

        // TODO toKMeansColorLogarithm
        medianCut.addActionListener(
                e -> {
                    int numColors = 1000; // عدد الألوان المطلوبة في الصورة المقتطعة
                    BufferedImage quantizedImage = null;
                    try {
                        // قراءة الصورة الأصلية
                        BufferedImage originalImage = ImageIO.read(new File(imagePath));

                        // تنفيذ خوارزمية Median Cut Color Quantization
                        quantizedImage = quantizeImageForCut(originalImage, numColors);

                        // حفظ الصورة المقتطعة
                        File outputFile = new File(newPathForCutImage);
                        ImageIO.write(quantizedImage, "png"+"jpg"+"gif", outputFile);

                        System.out.println("تم إنشاء الصورة المقتطعة بنجاح.");

                    } catch (IOException ex) {
                        System.out.println("حدث خطأ أثناء معالجة الصورة: " + ex.getMessage());
                    }
                    assert quantizedImage != null;
                    ImageLabelForAllLogarithm.setIcon(new ImageIcon(quantizedImage));
                }
        );
        kMeansButton.addActionListener(
                e -> {
                    try {
            // قراءة الصورة من ملف
            BufferedImage image = ImageIO.read(new File(imagePath));

            // تطبيق خوارزمية K-Means على الصورة
            int k = 16; // عدد التجميعات المطلوبة
            BufferedImage quantizedImageForKM = quantizeImageForKM(image, k);


            File outputImage = new File(newPathForKMImage);
            ImageIO.write(quantizedImageForKM, "png", outputImage);

            System.out.println("Image indexed successfully!");

            // عرض الصورة المحسوبة في إطار جديد
            JFrame frame = new JFrame();
            frame.getContentPane().setLayout( new FlowLayout());
            frame.getContentPane().add(new JLabel(new ImageIcon(quantizedImageForKM)));
            frame.pack();
            frame.setVisible(true);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
                }
        );
        indexButton.addActionListener(
                e -> {
                    BufferedImage originalImage = null;
                    try {
                        originalImage = ImageIO.read(new File(imagePath));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    // Convert image to indexed image
                    BufferedImage indexImage = convertToIndexImage(originalImage);

                    // Save indexed image
                    try {
                        ImageIO.write(indexImage, "png", new File(newPathForIndexImage));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
        );
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter fileNameExtensionFilter =
                new FileNameExtensionFilter("Extension Supported", "jpg", "png", "jpeg", "gif");
        fileChooser.setFileFilter(fileNameExtensionFilter);
        int selected = fileChooser.showOpenDialog(this);
        File file = fileChooser.getSelectedFile();
        String getSelectedImage = file.getAbsolutePath();
        System.out.println(file);
        FileNameForBufferedImage =file;
        System.out.println(FileNameForBufferedImage);
        ImageIcon imageIcon = new ImageIcon(getSelectedImage); // load the image to a imageIcon
        Image image = imageIcon.getImage(); // transform it
        Image newimg = ((Image) image).getScaledInstance(500, 500, Image.SCALE_SMOOTH); // scale it the smooth way
        imageIcon = new ImageIcon(newimg);
        initialImageLabel.setIcon(imageIcon);
        String filename = file.getAbsolutePath();
        imagePath = filename;

    }
 //TODO ******************************************************************************************************************************
    //TODO This Methods For Cut
public static BufferedImage quantizeImageForCut(BufferedImage image, int numColors) {
    List<Color> pixels = getPixelsForCut(image);
    List<Color> quantizedColors = quantizeColorsForCut(pixels, numColors);
    return replaceColorsForCut(image, quantizedColors);
}

    private static List<Color> getPixelsForCut(BufferedImage image) {
        List<Color> pixels = new ArrayList<>();
        int width = image.getWidth();
        int height = image.getHeight();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = new Color(image.getRGB(x, y));
                pixels.add(color);
            }
        }
        return pixels;
    }

    private static List<Color> quantizeColorsForCut(List<Color> pixels, int numColors) {
        // Perform Median Cut Color Quantization algorithm here
        // ...

        // Placeholder code to return a subset of unique colors
        List<Color> quantizedColorsForCut = new ArrayList<>();
        for (int i = 0; i < numColors && i < pixels.size(); i++) {
            Color color = pixels.get(i);
            if (!quantizedColorsForCut.contains(color)) {
                quantizedColorsForCut.add(color);
            }
        }
        return quantizedColorsForCut;
    }

    private static BufferedImage replaceColorsForCut(BufferedImage image, List<Color> colors) {
        BufferedImage quantizedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        int width = image.getWidth();
        int height = image.getHeight();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = new Color(image.getRGB(x, y));
                Color closestColor = findClosestColorForCut(color, colors);
                quantizedImage.setRGB(x, y, closestColor.getRGB());
            }
        }
        return quantizedImage;
    }

    private static Color findClosestColorForCut(Color color, List<Color> colors) {
        Color closestColor = colors.get(0);
        int minDistance = getDistanceForCut(color, closestColor);
        for (int i = 1; i < colors.size(); i++) {
            Color currentColor = colors.get(i);
            int distance = getDistanceForCut(color, currentColor);
            if (distance < minDistance) {
                closestColor = currentColor;
                minDistance = distance;
            }
        }
        return closestColor;
    }
    private static int getDistanceForCut(Color color1, Color color2) {
        int redDiff = color1.getRed() - color2.getRed();
        int greenDiff = color1.getGreen() - color2.getGreen();
        int blueDiff = color1.getBlue() - color2.getBlue();
        return redDiff * redDiff + greenDiff * greenDiff + blueDiff * blueDiff;
    }
// TODO ****************************************************************************************************************************
    //TODO This Methods For KM
public static BufferedImage quantizeImageForKM(BufferedImage image, int k) {
    int width = image.getWidth();
    int height = image.getHeight();

    // إنشاء قائمة بجميع الألوان في الصورة
    java.util.List<Color> colors = new java.util.ArrayList<>();
    for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
            Color pixelColor = new Color(image.getRGB(x, y));
            colors.add(pixelColor);
        }
    }

    // استدعاء خوارزمية K-Means لتجميع الألوان
    java.util.List<Color> centroids = runKMeans(colors, k);

    // إنشاء الصورة المحسوبة
    BufferedImage quantizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
            Color pixelColor = new Color(image.getRGB(x, y));
            Color closestColor = findClosestColorForKM(pixelColor, centroids);
            quantizedImage.setRGB(x, y, closestColor.getRGB());
        }
    }

    return quantizedImage;
}

    private static java.util.List<Color> runKMeans(java.util.List<Color> colors, int k) {
        // توليد مراكز عشوائية للتجميعات اللونية
        java.util.List<Color> centroids = new java.util.ArrayList<>();
        for (int i = 0; i < k; i++) {
            Color randomColor = colors.get((int) (Math.random() * colors.size()));
            centroids.add(randomColor);
        }

        // حساب المراكز الجديدة وتحديثها حتى تتوقف عملية التجميع
        boolean converged = false;
        while (!converged) {
            // تعيين الألوان للمجموعات الأقرب
            java.util.List<java.util.List<Color>> clusters = assignToClusters(colors, centroids);

            // حساب المراكز الجديدة
            java.util.List<Color> newCentroids = calculateNewCentroidsKM(clusters);

            // التحقق من توقف عملية التجميع
            if (centroids.equals(newCentroids)) {
                converged = true;
            } else {
                centroids = newCentroids;
            }
        }

        return centroids;
    }

    private static java.util.List<java.util.List<Color>> assignToClusters(java.util.List<Color> colors,
                                                                          java.util.List<Color> centroids) {
        java.util.List<java.util.List<Color>> clusters = new java.util.ArrayList<>();

        for (int i = 0; i < centroids.size(); i++) {
            clusters.add(new java.util.ArrayList<>());
        }

        for (Color color : colors) {
            int closestCentroidIndex = findClosestCentroidIndexKM(color, centroids);
            clusters.get(closestCentroidIndex).add(color);
        }

        return clusters;
    }

    private static int findClosestCentroidIndexKM(Color color, java.util.List<Color> centroids) {
        int closestIndex = 0;
        double closestDistance = Double.MAX_VALUE;

        for (int i = 0; i < centroids.size(); i++) {
            Color centroid = centroids.get(i);
            double distance = calculateColorDistanceForKM(color, centroid);

            if (distance < closestDistance) {
                closestIndex = i;
                closestDistance = distance;
            }
        }

        return closestIndex;
    }

    private static java.util.List<Color> calculateNewCentroidsKM(java.util.List<java.util.List<Color>> clusters) {
        java.util.List<Color> newCentroidsKM = new java.util.ArrayList<>();

        for (java.util.List<Color> cluster : clusters) {
            if (!cluster.isEmpty()) {
                int sumRed = 0;
                int sumGreen = 0;
                int sumBlue = 0;

                for (Color color : cluster) {
                    sumRed += color.getRed();
                    sumGreen += color.getGreen();
                    sumBlue += color.getBlue();
                }

                int avgRed = sumRed / cluster.size();
                int avgGreen = sumGreen / cluster.size();
                int avgBlue = sumBlue / cluster.size();

                Color newCentroidKM = new Color(avgRed, avgGreen, avgBlue);
                newCentroidsKM.add(newCentroidKM);
            }
        }

        return newCentroidsKM;
    }

    private static Color findClosestColorForKM(Color targetColor, java.util.List<Color> colors) {


        int closestIndexKM = 0;
        double closestDistanceKM = Double.MAX_VALUE;

        for (int i = 0; i < colors.size(); i++) {
            Color color = colors.get(i);
            double distance = calculateColorDistanceForKM(targetColor, color);

            if (distance < closestDistanceKM) {
                closestIndexKM = i;
                closestDistanceKM = distance;
            }
        }

        return colors.get(closestIndexKM);
    }

    private static double calculateColorDistanceForKM(Color color1, Color color2) {
        int redDiff = color1.getRed() - color2.getRed();
        int greenDiff = color1.getGreen() - color2.getGreen();
        int blueDiff = color1.getBlue() - color2.getBlue();

        return Math.sqrt(redDiff * redDiff + greenDiff * greenDiff + blueDiff * blueDiff);
    }
  //TODO **************************************************************************************************************
    //TODO This Methods To Save Index Image
    static IndexColorModel createIndexColorModel() {
        // Define the color palette for indexing
        int[] colors = {
                0xFF000000, // Black
                0xFF0000FF, // Blue
                0xFF00FF00, // Green
                0xFFFF0000, // Red
                // Add more colors as needed
        };
        int transferType = DataBuffer.TYPE_BYTE;

        // Create the index color model
        return new IndexColorModel(8, colors.length, colors, 0, false, -1,transferType);

    }
    public static BufferedImage convertToIndexImage(BufferedImage originalImage) {
        // Create color palette
        IndexColorModel colorModel = createColorModel();

        // Create indexed image
        BufferedImage indexImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(),
                BufferedImage.TYPE_BYTE_INDEXED, colorModel);

        // Draw original image on indexed image
        Graphics g = indexImage.getGraphics();
        g.drawImage(originalImage, 0, 0, null);
        g.dispose();

        return indexImage;
    }

    private static IndexColorModel createColorModel() {
        // Define your color palette
        byte[] reds = {0, 127, (byte) 255};
        byte[] greens = {0, 127, (byte) 255};
        byte[] blues = {0, 127, (byte) 255};

        return new IndexColorModel(8, reds.length, reds, greens, blues);}
    //TODO Change Image Name And Get New Name
    public static String getImageName(){
        int upperbound = 100000;

        Random rand = new Random();

        int int_random = rand.nextInt(upperbound);

        return "imageName"+int_random;
    }


}
