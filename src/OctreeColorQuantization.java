import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class OctreeColorQuantization {
    private static class OctreeNode {
        private int redSum;
        private int greenSum;
        private int blueSum;
        private int pixelCount;
        private OctreeNode[] children;

        public OctreeNode() {
            redSum = 0;
            greenSum = 0;
            blueSum = 0;
            pixelCount = 0;
            children = null;
        }
    }

    public static BufferedImage quantizeImage(BufferedImage image, int colorCount) {
        int width = image.getWidth();
        int height = image.getHeight();
        int[] pixels = image.getRGB(0, 0, width, height, null, 0, width);

        OctreeNode root = buildOctree(pixels, colorCount);

        BufferedImage quantizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        int[] quantizedPixels = new int[pixels.length];

        for (int i = 0; i < pixels.length; i++) {
            int color = pixels[i];
            int quantizedColor = getQuantizedColor(color, root);
            quantizedPixels[i] = quantizedColor;
        }

        quantizedImage.setRGB(0, 0, width, height, quantizedPixels, 0, width);

        return quantizedImage;
    }

    private static OctreeNode buildOctree(int[] pixels, int colorCount) {
        OctreeNode root = new OctreeNode();

        for (int color : pixels) {
            int red = (color >> 16) & 0xFF;
            int green = (color >> 8) & 0xFF;
            int blue = color & 0xFF;

            addColor(red, green, blue, root, 0);
        }

        reduceOctree(root, colorCount);

        return root;
    }

    private static void addColor(int red, int green, int blue, OctreeNode node, int level) {
        if (node.children == null) {
            node.redSum += red;
            node.greenSum += green;
            node.blueSum += blue;
            node.pixelCount++;
        } else {
            int bit = 7 - level;
            int index = ((red >> bit) & 1) | ((green >> bit) & 1) << 1 | ((blue >> bit) & 1) << 2;

            if (node.children[index] == null) {
                node.children[index] = new OctreeNode();
            }

            addColor(red, green, blue, node.children[index], level + 1);
        }
    }

    private static void reduceOctree(OctreeNode node, int colorCount) {
        if (node.children == null) {
            return;
        }

        if (node.pixelCount <= colorCount) {
            node.children = null;
            return;
        }

        int totalRed = 0;
        int totalGreen = 0;
        int totalBlue = 0;
        int totalPixels = 0;

        for (OctreeNode child : node.children) {
            if (child != null) {
                totalRed += child.redSum;
                totalGreen += child.greenSum;
                totalBlue += child.blueSum;
                totalPixels += child.pixelCount;
            }
        }

        node.redSum = totalRed;
        node.greenSum = totalGreen;
        node.blueSum = totalBlue;
        node.pixelCount = totalPixels;

        for (OctreeNode child : node.children) {
            if (child != null) {
                reduceOctree(child, colorCount);
            }
        }
    }

    private static int getQuantizedColor(int color, OctreeNode node) {
        if (node.children == null) {
            return getColor(node.redSum / node.pixelCount, node.greenSum / node.pixelCount, node.blueSum / node.pixelCount);
        }

        int red = (color >> 16) & 0xFF;
        int green = (color >> 8) & 0xFF;
        int blue = color & 0xFF;

        int bit = 7;
        int index = ((red >> bit) & 1) | ((green >> bit) & 1) << 1 | ((blue >> bit) & 1) << 2;

        if (node.children[index] == null) {
            int closestIndex = getClosestChildIndex(node, red, green, blue);
            return getColor(node.children[closestIndex].redSum / node.children[closestIndex].pixelCount,
                    node.children[closestIndex].greenSum / node.children[closestIndex].pixelCount,
                    node.children[closestIndex].blueSum / node.children[closestIndex].pixelCount);
        }

        return getQuantizedColor(color, node.children[index]);
    }

    private static int getClosestChildIndex(OctreeNode node, int red, int green, int blue) {
        int closestIndex = 0;
        int closestDistance = Integer.MAX_VALUE;

        for (int i = 0; i < node.children.length; i++) {
            if (node.children[i] != null) {
                int distance = getColorDistance(node.children[i].redSum / node.children[i].pixelCount,
                        node.children[i].greenSum / node.children[i].pixelCount,
                        node.children[i].blueSum / node.children[i].pixelCount, red, green, blue);
                if (distance < closestDistance) {
                    closestIndex = i;
                    closestDistance = distance;
                }
            }
        }

        return closestIndex;
    }

    private static int getColor(int red, int green, int blue) {
        return (red << 16) | (green << 8) | blue;
    }

    private static int getColorDistance(int red1, int green1, int blue1, int red2, int green2, int blue2) {
        int redDiff = red1 - red2;
        int greenDiff = green1 - green2;
        int blueDiff = blue1 - blue2;
        return redDiff * redDiff + greenDiff * greenDiff + blueDiff * blueDiff;
    }

    public static void main(String[] args) {
        try {
            BufferedImage image = ImageIO.read(new File("C:\\Users\\AbdAlrahman\\Desktop\\10.png"));
            BufferedImage quantizedImage = quantizeImage(image, 100000);

            // عرض الصورة الأصلية
            ImageIcon originalIcon = new ImageIcon(image);
            JLabel originalLabel = new JLabel(originalIcon);
            JFrame originalFrame = new JFrame("Original Image");
            originalFrame.getContentPane().add(originalLabel);
            originalFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            originalFrame.pack();
            originalFrame.setVisible(true);

            // عرض الصورة المحسوبة
            ImageIcon quantizedIcon = new ImageIcon(quantizedImage);
            JLabel quantizedLabel = new JLabel(quantizedIcon);
            JFrame quantizedFrame = new JFrame("Quantized Image");
            quantizedFrame.getContentPane().add(quantizedLabel);
            quantizedFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            quantizedFrame.pack();
            quantizedFrame.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
