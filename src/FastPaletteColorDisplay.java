import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import javax.imageio.ImageIO;

public class FastPaletteColorDisplay extends JFrame {

    private BufferedImage originalImage;
    private JPanel imagePanel;
    private JPanel palettePanel;

    public FastPaletteColorDisplay() {
        setTitle("Fast Palette Color Display");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        imagePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (originalImage != null) {
                    g.drawImage(originalImage, 0, 0, null);
                }
            }
        };

        palettePanel = new JPanel();

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(imagePanel, BorderLayout.WEST);
        getContentPane().add(palettePanel, BorderLayout.CENTER);
    }

    void loadImageForPalette(String imagePath) {
        try {
            originalImage = ImageIO.read(new File(imagePath));
            displayPaletteColors();
            repaint();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displayPaletteColors() {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        // Define the region of interest (ROI) in the image
        int roiX = width / 4;  // Starting X coordinate of the ROI
        int roiY = height / 4; // Starting Y coordinate of the ROI
        int roiWidth = width / 2;   // Width of the ROI
        int roiHeight = height / 2; // Height of the ROI

        // Get unique colors from the ROI
        int[] uniqueColors = getUniqueColors(roiX, roiY, roiWidth, roiHeight);

        // Create a panel with the palette colors
        JPanel colorPalette = createColorPalette(uniqueColors, roiWidth, roiHeight);

        palettePanel.removeAll();
        palettePanel.add(colorPalette);
        palettePanel.setPreferredSize(new Dimension(roiWidth, roiHeight));

        palettePanel.setBackground(Color.WHITE);
        imagePanel.setBackground(Color.WHITE);

        pack();
    }

    private int[] getUniqueColors(int x, int y, int width, int height) {
        int[] pixels = originalImage.getRGB(x, y, width, height, null, 0, width);

        // Use HashSet to store unique colors
        HashSet<Integer> colorSet = new HashSet<>();

        // Add unique colors to the set
        for (int color : pixels) {
            colorSet.add(color);
        }

        // Convert the set to an array
        int[] uniqueColors = new int[colorSet.size()];
        int index = 0;
        for (int color : colorSet) {
            uniqueColors[index++] = color;
        }

        return uniqueColors;
    }

    private JPanel createColorPalette(int[] colors, int width, int height) {
        JPanel palette = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int centerX = getWidth() / 2;
                int centerY = getHeight() / 2;
                int radius = Math.min(getWidth(), getHeight()) / 2 - 10;
                double angleStep = 2 * Math.PI / colors.length;

                for (int i = 0; i < colors.length; i++) {
                    int color = colors[i];
                    int x = (int) (centerX + radius * Math.cos(i * angleStep));
                    int y = (int) (centerY + radius * Math.sin(i * angleStep));

                    g.setColor(new Color(color));
                    g.fillOval(x - 5, y - 5, 10, 10);
                }
            }
        };

        palette.setPreferredSize(new Dimension(width, height));

        return palette;
    }


}
