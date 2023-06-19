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

public class SearchCropImageForMostColor extends JFrame {
    private JPanel panel;
    private BufferedImage targetImage;

    public SearchCropImageForMostColor() {
        setTitle("Similar Images by Most Common Color");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JButton selectImageButton = new JButton("Select Target Image");
        selectImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(SearchCropImageForMostColor.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        targetImage = ImageIO.read(selectedFile);
                        if (targetImage != null) {
                            showColorPalette();
                            searchForSimilarImages();
                        } else {
                            System.out.println("Invalid image file.");
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        add(selectImageButton, BorderLayout.NORTH);
        add(new JScrollPane(panel), BorderLayout.CENTER);

        setVisible(true);
    }

    private void showColorPalette() {
        panel.removeAll();

        int targetWidth = targetImage.getWidth();
        int targetHeight = targetImage.getHeight();

        JPanel colorPalettePanel = new JPanel();
        colorPalettePanel.setPreferredSize(new Dimension(targetWidth / 2, targetHeight / 2));
        colorPalettePanel.setBackground(Color.WHITE);

        for (int x = 0; x < targetWidth; x += 10) {
            for (int y = 0; y < targetHeight; y += 10) {
                int pixel = targetImage.getRGB(x, y);
                JPanel colorPanel = new JPanel();
                colorPanel.setPreferredSize(new Dimension(10, 10));
                colorPanel.setBackground(new Color(pixel));
                colorPalettePanel.add(colorPanel);
            }
        }

        panel.add(colorPalettePanel);
        panel.revalidate();
        panel.repaint();
    }

    private void searchForSimilarImages() {
        String directoryPath = "C:\\Users\\AbdAlrahman\\Desktop\\saves";

        File directory = new File(directoryPath);
        File[] imageFiles = directory.listFiles();

        List<BufferedImage> similarImages = new ArrayList<>();

        int targetWidth = targetImage.getWidth();
        int targetHeight = targetImage.getHeight();

        for (File imageFile : imageFiles) {
            if (imageFile.isFile()) {
                BufferedImage image;
                try {
                    image = ImageIO.read(imageFile);
                    if (image != null) {
                        if (image.getWidth() >= targetWidth && image.getHeight() >= targetHeight) {
                            BufferedImage croppedImage = cropImage(image, targetWidth, targetHeight);
                            double difference = compareImages(targetImage, croppedImage);
                            double threshold = 100.0;
                            if (difference < threshold) {
                                similarImages.add(croppedImage);
                            }
                        }
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

        if (!similarImages.isEmpty()) {
            for (BufferedImage similarImage : similarImages) {
                JPanel imagePanel = new JPanel() {
                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        Graphics2D g2d = (Graphics2D) g;
                        for (int x = 0; x < similarImage.getWidth(); x++) {
                            for (int y = 0; y < similarImage.getHeight(); y++) {
                                int pixel = similarImage.getRGB(x, y);
                                g2d.setColor(new Color(pixel));
                                g2d.fillRect(x, y, 1, 1);
                            }
                        }
                    }
                };
                imagePanel.setPreferredSize(new Dimension(targetWidth / 2, targetHeight / 2));
                imagePanel.setBackground(Color.WHITE);
                panel.add(imagePanel);
            }
        } else {
            System.out.println("No similar images found.");
        }

        panel.revalidate();
        panel.repaint();
    }

    private BufferedImage cropImage(BufferedImage image, int width, int height) {
        int x = (image.getWidth() - width) / 2;
        int y = (image.getHeight() - height) / 2;

        if (x < 0 || y < 0 || x + width > image.getWidth() || y + height > image.getHeight()) {
            return null; // Invalid crop dimensions
        }

        return image.getSubimage(x, y, width, height);
    }

    private double compareImages(BufferedImage image1, BufferedImage image2) {

        return Math.random() * 100;
    }

}
