import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

public class SVDio{
    public static void saveCompressed(String filename, SVDcompress img_compressed){
        byte[] write_bytes = new byte[0];
        File compressed_file = new File(filename);
        try(FileOutputStream file_out_stream = new FileOutputStream(compressed_file)){
            write_bytes = add_byte_arrays(write_bytes, to_bytes(img_compressed.original_dimensions));
            write_bytes = add_byte_arrays(write_bytes, to_bytes(img_compressed.u_compressed.length));
            write_bytes = add_byte_arrays(write_bytes, to_bytes(img_compressed.u_compressed[0].length));

            System.out.println("Converting compressed U to bytes");
            for(int i = 0; i < img_compressed.u_compressed.length; i++){
                byte[] outer_temp_byte_arr = new byte[0];
                for(int j = 0; j < img_compressed.u_compressed[i].length; j++){
                    byte[] inner_temp_byte_arr = new byte[0];
                    for(int k = 0; k < img_compressed.u_compressed[i][j].length; k++){
                        inner_temp_byte_arr = add_byte_arrays(inner_temp_byte_arr, to_bytes(img_compressed.u_compressed[i][j][k]));
                    }
                    outer_temp_byte_arr = add_byte_arrays(outer_temp_byte_arr, inner_temp_byte_arr);
                }
                write_bytes = add_byte_arrays(write_bytes, outer_temp_byte_arr);
            }

            System.out.println("Converting compressed Sigma to bytes");
            for(int i = 0; i < img_compressed.sigma_compressed.length; i++){
                byte[] outer_temp_byte_arr = new byte[0];
                for(int j = 0; j < img_compressed.sigma_compressed[i].length; j++){
                    outer_temp_byte_arr = add_byte_arrays(outer_temp_byte_arr, to_bytes(img_compressed.sigma_compressed[i][j]));
                }
                write_bytes = add_byte_arrays(write_bytes, outer_temp_byte_arr);
            }

            System.out.println("Converting compressed V transpose to bytes");
            for(int i = 0; i < img_compressed.vt_compressed.length; i++){
                byte[] outer_temp_byte_arr = new byte[0];
                for(int j = 0; j < img_compressed.vt_compressed[i].length; j++){
                    byte[] inner_temp_byte_arr = new byte[0];
                    for(int k = 0; k < img_compressed.vt_compressed[i][j].length; k++){
                        inner_temp_byte_arr = add_byte_arrays(inner_temp_byte_arr, to_bytes(img_compressed.vt_compressed[i][j][k]));
                    }
                    outer_temp_byte_arr = add_byte_arrays(outer_temp_byte_arr, inner_temp_byte_arr);
                }
                write_bytes = add_byte_arrays(write_bytes, outer_temp_byte_arr);
            }


            System.out.println("Writing array to output file: " + filename);
            file_out_stream.write(write_bytes);
        } catch(IOException e){
            System.err.println("Could not write to " + filename);
            return;
        }
        
    }
    public static float[] readCompressed(String filename){
        float[] file_arr = new float[0];
        
        SVDdecompress svd_decompress = new SVDdecompress();

        try{
            File compressed_file = new File(filename);
            byte[] file_content = Files.readAllBytes(compressed_file.toPath());

            int height = read_int(file_content, 0);
            int width = read_int(file_content, 4);
            int compressed_height = read_int(file_content, 8);
            int compressed_width = read_int(file_content, 12);

            svd_decompress.original_dimensions = new int[]{height, width};
            svd_decompress.u_compressed = new float[compressed_height][compressed_width][32][8];
            svd_decompress.sigma_compressed = new float[compressed_height][compressed_width][8];
            svd_decompress.vt_compressed = new float[compressed_height][compressed_width][8][32];

            int index = 16;
            for(int i = 0; i < compressed_height; i++){
                for(int j = 0; j < compressed_width; j++){
                    for(int k = 0; k < 32; k++){
                        for(int l = 0; l < 8; l++){
                            svd_decompress.u_compressed[i][j][k][l] = read_float(file_content, index);
                            index += 4;
                        }
                    }
                }
            }
            for(int i = 0; i < compressed_height; i++){
                for(int j = 0; j < compressed_width; j++){
                    for(int k = 0; k < 8; k++){
                        svd_decompress.sigma_compressed[i][j][k] = read_float(file_content, index);
                        index += 4;
                    }
                }
            }
            for(int i = 0; i < compressed_height; i++){
                for(int j = 0; j < compressed_width; j++){
                    for(int k = 0; k < 8; k++){
                        for(int l = 0; l < 32; l++){
                            svd_decompress.vt_compressed[i][j][k][l] = read_float(file_content, index);
                            index += 4;
                        }
                    }
                }
            }
        }
        catch(IOException e){
            System.err.println("Could not read " + filename);
        }
        return file_arr;
    }

    public static int read_int(byte[] array, int index){
        int return_int = 0;
        return_int |= ((array[index] & 0xFF) << 24);
        return_int |= ((array[index + 1] & 0xFF) << 16);
        return_int |= ((array[index + 2] & 0xFF) << 8);
        return_int |= ((array[index + 3] & 0xFF));

        return return_int;
    }

    public static float read_float(byte[] array, int index){
        float return_float = Float.intBitsToFloat(read_int(array, index));

        return return_float;
    }

    public static byte[] to_bytes(int num){
        byte[] return_bytes = new byte[4];

        return_bytes[0] = (byte) ((num >> 24) & 0xFF);
        return_bytes[1] = (byte) ((num >> 16) & 0xFF);
        return_bytes[2] = (byte) ((num >> 8) & 0xFF);
        return_bytes[3] = (byte) (num & 0xFF);

        return return_bytes;
    }

    public static byte[] to_bytes(int[] array){
        byte[] return_bytes = new byte[array.length * 4];

        for(int i = 0; i < array.length; i++){
            byte[] chunk = to_bytes(array[i]);

            return_bytes[i * 4] = chunk[0];
            return_bytes[i * 4 + 1] = chunk[1];
            return_bytes[i * 4 + 2] = chunk[2];
            return_bytes[i * 4 + 3] = chunk[3];
        }

        return return_bytes;
    }

    public static byte[] to_bytes(double num){
        byte[] return_bytes = new byte[8];

        Long byte_bits = Double.doubleToLongBits(num);
        for(int i = 0; i < 8; i++){
            return_bytes[i] = (byte)((byte_bits >> ((7 - i) * 8)) & 0xFF);
        }

        return return_bytes;
    }

    public static byte[] to_bytes(double[] array){
        byte[] return_bytes = new byte[array.length * 8];

        for(int i = 0; i < array.length; i++){
            byte[] chunk = to_bytes(array[i]);

            for(int j = 0; j < 8; j++){
                return_bytes[8*i + j] = chunk[j];
            }
        }

        return return_bytes;
    }

    public static byte[] to_bytes(float num){
        byte[] return_bytes = new byte[4];

        int byte_bits = Float.floatToIntBits(num);
        for(int i = 0; i < 4; i++){
            return_bytes[i] = (byte)((byte_bits >> ((3 - i) * 8)) & 0xFF);
        }

        return return_bytes;
    }

    public static byte[] to_bytes(float[] array){
        byte[] return_bytes = new byte[array.length * 4];

        for(int i = 0; i < array.length; i++){
            byte[] chunk = to_bytes(array[i]);

            for(int j = 0; j < 4; j++){
                return_bytes[4*i + j] = chunk[j];
            }
        }

        return return_bytes;
    }

    public static byte[] add_byte_arrays(byte[] first_array, byte[] second_array){
        byte[] return_bytes = new byte[first_array.length + second_array.length];
        for(int i = 0; i < first_array.length; i++){
            return_bytes[i] = first_array[i];
        }
        for(int i = 0; i < second_array.length; i++){
            return_bytes[i + first_array.length] = second_array[i];
        }

        return return_bytes;
    }
}
