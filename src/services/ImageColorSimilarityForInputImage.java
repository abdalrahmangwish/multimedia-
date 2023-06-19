package services;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageColorSimilarityForInputImage extends JFrame {
    private JPanel panel;
    private Color selectedColor;

    public ImageColorSimilarityForInputImage() {
        setTitle("Similar Images by Color");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JButton selectColorButton = new JButton("Select Color");
        selectColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JColorChooser colorChooser = new JColorChooser();
                selectedColor = JColorChooser.showDialog(ImageColorSimilarityForInputImage.this, "Select Color", selectedColor);
                if (selectedColor != null) {
                    searchForSimilarImages();
                    printSelectedColorRGB();
                }
            }
        });

        add(selectColorButton, BorderLayout.NORTH);
        add(new JScrollPane(panel), BorderLayout.CENTER);

        setVisible(true);
    }

    private void searchForSimilarImages() {
        panel.removeAll();

        String directoryPathForInputColor = "C:\\Users\\AbdAlrahman\\Desktop\\saves";

        File directory = new File(directoryPathForInputColor);
        File[] imageFiles = directory.listFiles();

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

                boolean isSimilar = checkColorSimilarityForInputImage(image, selectedColor);

                if (isSimilar) {
                    similarImages.add(imageFile);
                }
            }
        }

        if (!similarImages.isEmpty()) {
            System.out.println("Similar images:");
            for (File imageFile : similarImages) {
                try {
                    BufferedImage image = ImageIO.read(imageFile);
                    ImageIcon icon = new ImageIcon(image.getScaledInstance(200, 200, Image.SCALE_DEFAULT)); // تغيير حجم الصورة
                    JLabel label = new JLabel(icon);
                    panel.add(label);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } else {
            System.out.println("No similar images found.");
        }

        panel.revalidate();
        panel.repaint();
    }

    private boolean checkColorSimilarityForInputImage(BufferedImage image, Color targetColor) {
        int width = image.getWidth();
        int height = image.getHeight();

        int targetRGB = targetColor.getRGB();
        double similarityThreshold = 0.6; // عتبة التشابه (يمكن تعديلها هنا)

        int similarPixels = 0;
        int totalPixels = width * height;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                if (isSimilarColor(rgb, targetRGB, similarityThreshold)) {
                    similarPixels++;
                }
            }
        }

        double similarityPercentage = (double) similarPixels / totalPixels;
        return similarityPercentage >= similarityThreshold;
    }

    private boolean isSimilarColor(int rgb1, int rgb2, double similarityThreshold) {
        Color color1 = new Color(rgb1);
        Color color2 = new Color(rgb2);

        int redDiff = Math.abs(color1.getRed() - color2.getRed());
        int greenDiff = Math.abs(color1.getGreen() - color2.getGreen());
        int blueDiff = Math.abs(color1.getBlue() - color2.getBlue());

        double totalDiff = redDiff + greenDiff + blueDiff;
        double maxDiff = 3 * 255; // الفرق الأقصى الممكن في قيمة اللون الأحمر والأخضر والأزرق

        double similarity = 1 - (totalDiff / maxDiff);
        return similarity >= similarityThreshold;
    }

    private void printSelectedColorRGB() {
        int red = selectedColor.getRed();
        int green = selectedColor.getGreen();
        int blue = selectedColor.getBlue();
        System.out.println("Selected color RGB: " + red + ", " + green + ", " + blue);
    }


}
