import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageMatrix{
    public int[][] img_matrix_red;
    public int[][] img_matrix_green;
    public int[][] img_matrix_blue;

    public ImageMatrix(String img_path){
        BufferedImage input_image;
        try{
             input_image = ImageIO.read(new File(img_path));
        } catch(IOException e){
            System.err.println("Unable to open image " + img_path);
            return;
        }

        int width = input_image.getWidth();
        int height = input_image.getHeight();

        img_matrix_red = new int[width][height];
        img_matrix_blue = new int[width][height];
        img_matrix_green = new int[width][height];

        int row, col, argb;
        int red, green, blue;

        for(int pixel = 0; pixel < width * height; pixel++){

            col = pixel % width;
            row = pixel / width;

            argb = input_image.getRGB(col, row);

            red = (argb >> 16) & 0xFF;
            green = (argb >> 8) & 0xFF;
            blue = (argb) & 0xFF;

            img_matrix_red[row][col] = red;
            img_matrix_green[row][col] = green;
            img_matrix_blue[row][col] = blue;
        }
    }
}
