import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import javax.imageio.ImageIO;

public class PaletteColorDisplay extends JFrame {

    private BufferedImage originalImage;
    private JPanel imagePanel;
    private JPanel palettePanel;

    public PaletteColorDisplay() {
        setTitle("Palette Color Display");
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

    private void loadImageForPalette(String imagePath) {
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

        // Get unique colors from the image
        int[] uniqueColors = getUniqueColors();

        // Print the number of unique colors
        System.out.println("Number of unique colors: " + uniqueColors.length);

        // Create a panel with the palette colors
        JPanel colorPalette = createColorPalette(uniqueColors);

        palettePanel.removeAll();
        palettePanel.add(colorPalette);
        palettePanel.setPreferredSize(new Dimension(width, height));

        palettePanel.setBackground(Color.WHITE);
        imagePanel.setBackground(Color.WHITE);

        pack();
    }

    private int[] getUniqueColors() {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        int[] pixels = originalImage.getRGB(0, 0, width, height, null, 0, width);

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

    private JPanel createColorPalette(int[] colors) {
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

        palette.setPreferredSize(new Dimension(colors.length * 10, 200));

        return palette;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PaletteColorDisplay app = new PaletteColorDisplay();
            app.loadImageForPalette("C:\\Users\\AbdAlrahman\\Desktop\\saves\\15.jpg");
            app.setVisible(true);
        });
    }
}
