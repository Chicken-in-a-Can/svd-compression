import org.apache.commons.math3.linear.SingularValueDecomposition;

import java.util.Arrays;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;

public class SVDcompress{
    SingularValueDecomposition[][] rgb_decomp;

    public SVDcompress(ImageMatrix img_matrix){
        Array2DRowRealMatrix rgb_matrix;
        rgb_decomp = new SingularValueDecomposition[img_matrix.img_matrix_rgb.length / 32][img_matrix.img_matrix_rgb[0].length / 32];

        for(int i = 0; i < rgb_decomp.length; i++){
            for(int j = 0; j < rgb_decomp[i].length; j++){
                rgb_matrix = new Array2DRowRealMatrix(chunk(img_matrix.img_matrix_rgb, i, j));
                rgb_decomp[i][j] = new SingularValueDecomposition(rgb_matrix);
            }
        }
    }
    public double[][] chunk(double[][] main, int row, int col){
        double[][] chunk = new double[32][32];
        
        
        for(int i = 0; i < 32; i++){
            chunk[i] = Arrays.copyOfRange(main[col * 32 + i], row, row + 31);
        }

        return chunk;
    }
}
