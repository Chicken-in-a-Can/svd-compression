import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageMatrix{
    public double[][] img_matrix_rgb;

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

        int mat_width = width;
        int mat_height = height;
        if(width % 32 != 0){
            mat_width += 32 - (width % 32);
        }
        if(height % 32 != 0){
            mat_height += 32 - (height % 32);
        }
        img_matrix_rgb = new double[mat_width][mat_height];

        int row, col, argb;
        int rgb;

        for(int pixel = 0; pixel < width * height; pixel++){

            col = pixel % width;
            row = pixel / width;

            argb = input_image.getRGB(col, row);

            rgb = argb & 0xFFFFFF;

            img_matrix_rgb[col][row] = (double) rgb;
        }
    }
}
