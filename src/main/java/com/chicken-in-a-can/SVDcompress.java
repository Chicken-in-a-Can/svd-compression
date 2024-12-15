import org.apache.commons.math3.linear.SingularValueDecomposition;

import java.util.Arrays;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;

public class SVDcompress{
    public int[] original_dimensions = new int[2];
    public double[][][][] u_compressed;
    public double[][][] sigma_compressed;
    public double[][][][] vt_compressed;

    public SVDcompress(ImageMatrix img_matrix){
        original_dimensions = img_matrix.original_dimensions;
        Array2DRowRealMatrix rgb_matrix;
        SingularValueDecomposition[][] rgb_decomp = new SingularValueDecomposition[img_matrix.img_matrix_rgb.length / 32][img_matrix.img_matrix_rgb[0].length / 32];

        u_compressed = new double[img_matrix.img_matrix_rgb.length / 32][img_matrix.img_matrix_rgb[0].length / 32][32][8];
        sigma_compressed = new double[img_matrix.img_matrix_rgb.length / 32][img_matrix.img_matrix_rgb[0].length / 32][32];
        vt_compressed = new double[img_matrix.img_matrix_rgb.length / 32][img_matrix.img_matrix_rgb[0].length / 32][8][32];

        for(int i = 0; i < rgb_decomp.length; i++){
            for(int j = 0; j < rgb_decomp[i].length; j++){
                rgb_matrix = new Array2DRowRealMatrix(chunk(img_matrix.img_matrix_rgb, i, j, 32));
                rgb_decomp[i][j] = new SingularValueDecomposition(rgb_matrix);
                u_compressed[i][j] = transpose(Arrays.copyOfRange(transpose(rgb_decomp[i][j].getU().getData()), 0, 7));
                sigma_compressed[i][j] = Arrays.copyOfRange(get_diagonal(rgb_decomp[i][j].getS().getData(), 32), 0, 8);
                vt_compressed[i][j] = Arrays.copyOfRange(rgb_decomp[i][j].getVT().getData(), 0, 7);
            }
        }
    }

    public double[][] chunk(double[][] main, int row, int col, int size){
        double[][] chunk = new double[size][size];
        
        for(int i = 0; i < size; i++){
            chunk[i] = Arrays.copyOfRange(main[row * size + i], col, col + size);
        }

        return chunk;
    }

    public static double[] get_diagonal(double[][] array, int size){
        double[] return_array = new double[size];
        for(int i = 0; i < size; i++){
            return_array[i] = array[i][i];
        }

        return return_array;
    }

    public static double[][] transpose(double[][] array){
        double[][] return_array = new double[array[0].length][array.length];

        for(int i = 0; i < array.length; i++){
            for(int j = 0; j < array[0].length; j++){
                return_array[j][i] = array[i][j];
            }
        }

        return return_array;
    }

    public static SingularValueDecomposition[][] transpose(SingularValueDecomposition[][] array){
        SingularValueDecomposition[][] return_array = new SingularValueDecomposition[array[0].length][array.length];

        for(int i = 0; i < array.length; i++){
            for(int j = 0; j < array[0].length; j++){
                return_array[j][i] = array[i][j];
            }
        }

        return return_array;
    }

}
