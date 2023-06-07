import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class HistogramColorExample extends JPanel {
    private int[] histogram;

    public HistogramColorExample(int[] histogram) {
        this.histogram = histogram;
        setPreferredSize(new Dimension(256, 200));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int maxValue = getMaxValue(histogram);
        int barWidth = getWidth() / histogram.length;

        for (int i = 0; i < histogram.length; i++) {
            int barHeight = (int) (((double) histogram[i] / maxValue) * getHeight());
            int x = i * barWidth;
            int y = getHeight() - barHeight;

            Color color = new Color(i, i, i); // لون رمادي

            g.setColor(color);
            g.fillRect(x, y, barWidth, barHeight);
        }
    }

    private int getMaxValue(int[] histogram) {
        int maxValue = Integer.MIN_VALUE;
        for (int value : histogram) {
            if (value > maxValue) {
                maxValue = value;
            }
        }
        return maxValue;
    }

    public static void main(String[] args) {
        BufferedImage image = null; // تحميل الصورة هنا

        int[] histogram = new int[256];
        int width = image.getWidth();
        int height = image.getHeight();

        // حساب ال histogram للوان الصورة
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;

                int gray = (red + green + blue) / 3;
                histogram[gray]++;
            }
        }

        JFrame frame = new JFrame("Color Histogram");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new HistogramColorExample(histogram));
        frame.pack();
        frame.setVisible(true);
    }
}