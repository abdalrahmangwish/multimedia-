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

public class SearchResizeImageForMostColor extends JFrame {
    private JPanel panel;
    private BufferedImage targetImage;

    public SearchResizeImageForMostColor() {
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
                int result = fileChooser.showOpenDialog(SearchResizeImageForMostColor.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        targetImage = ImageIO.read(selectedFile);
                        if (targetImage != null) {
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

    private void searchForSimilarImages() {
        panel.removeAll();

        String directoryPath = "C:\\Users\\AbdAlrahman\\Desktop\\saves";
        String outputDirectoryPath = "C:\\Users\\AbdAlrahman\\Desktop\\saves\\oc";

        File directory = new File(directoryPath);
        File[] imageFiles = directory.listFiles();

        if (imageFiles != null) {
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
                                BufferedImage croppedImage = cropAndResizeImage(image, targetWidth, targetHeight, 100, 100);
                                double difference = compareImages(targetImage, croppedImage);
                                double threshold = 100.0;
                                if (difference < threshold) {
                                    similarImages.add(croppedImage);

                                    // Save the similar image to the output directory
                                    File outputFile = new File(outputDirectoryPath, imageFile.getName());
                                    ImageIO.write(croppedImage, "png", outputFile);
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
        } else {
            System.out.println("No image files found in the directory.");
        }

        panel.revalidate();
        panel.repaint();
    }

    private BufferedImage cropAndResizeImage(BufferedImage image, int width, int height, int scaledWidth, int scaledHeight) {
        int x = (image.getWidth() - width) / 2;
        int y = (image.getHeight() - height) / 2;

        if (x < 0 || y < 0 || x + width > image.getWidth() || y + height > image.getHeight()) {
            return null; // Invalid crop dimensions
        }

        BufferedImage croppedImage = image.getSubimage(x, y, width, height);

        // Resize the cropped image
        Image scaledImage = croppedImage.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);

        // Create a new BufferedImage to hold the scaled image
        BufferedImage resizedImage = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_ARGB);

        // Draw the scaled image onto the new BufferedImage
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(scaledImage, 0, 0, null);
        g2d.dispose();

        return resizedImage;
    }

    private double compareImages(BufferedImage image1, BufferedImage image2) {
        // Image comparison logic here
        // This method should return a numerical value indicating the similarity between the two images
        // You can use various image comparison algorithms such as pixel-based comparison or feature-based comparison

        // Dummy implementation, returning a random value between 0 and 100
        return Math.random() * 100;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SearchResizeImageForMostColor();
            }
        });
    }
}
