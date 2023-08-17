import java.io.File;
import java.io.IOException;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.FileInputStream;

public class ComparePngByteVsPixel {

    public static void main(String[] args) {

        File file1 = new File("img1.png");
        File file2 = new File("img2.png");

        boolean result = false;


        //Method 1: SLOW
        
        System.out.println("Method 1 start");
        
        try {
            result = compareByBytes(file1, file2);
        } catch (IOException e) {}

        System.out.println("Finished comparing " + file1.getPath() + " and " + file2.getPath() + ": " + result + "\n");


        //Method 2: FAST
        
        System.out.println("Method 2 start");

        try {
            result = compareByPixels(file1, file2);
        } catch (IOException e) {

        }

        System.out.println("Finished comparing " + file1.getPath() + " and " + file2.getPath() + ": " + result + "\n");
    }

    //Byte by Byte
    public static boolean compareByBytes(File f1, File f2) throws IOException {


        String filePath1 = f1.getPath();
        String filePath2 = f2.getPath();

        // Debug
        //System.out.println("comparing " + filePath1 + " and " + filePath2);


        try (FileInputStream fis1 = new FileInputStream(filePath1); FileInputStream fis2 = new FileInputStream(filePath2)) {

            int byte1;
            int byte2;

            do {
                byte1 = fis1.read();
                byte2 = fis2.read();

                if (byte1 != byte2) {

                    return false;
                }

            } while (byte1 != -1 && byte2 != -1);

            return byte1 == -1 && byte2 == -1;
        } catch (Exception e) {
            System.out.println("An Exception occured");
        }

        return false;
    }


    //Pixel by Pixel
    public static boolean compareByPixels(File f1, File f2) throws IOException {

        // Debug
        /*
        String filePath1 = f1.getPath();
        String filePath2 = f2.getPath();
        System.out.println("comparing " + filePath1 + " and " + filePath2);
        */

        BufferedImage img1 = ImageIO.read(f1);
        BufferedImage img2 = ImageIO.read(f2);

        // check if ! same dimensions
        if (img1.getHeight() != img2.getHeight() ||
            img1.getWidth() != img2.getWidth()) {
            return false;
        }

        // check if colors matching, pixel by pixel
        for (int y = 0; y < img1.getHeight(); y++) {
            for (int x = 0; x < img1.getWidth(); x++) {

                int p1 = img1.getRGB(x, y);
                int p2 = img2.getRGB(x, y);

                Color color1 = new Color(p1, true);
                Color color2 = new Color(p2, true);

                if (!color1.equals(color2)) {
                    return false;
                }
            }
        }
        return true;
    }
}