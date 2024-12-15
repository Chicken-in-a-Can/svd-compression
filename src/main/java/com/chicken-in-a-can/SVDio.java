public class SVDio{
    public static void saveCompressed(String filename, int num_values, SVDcompress img_compressed){
        
    }
    public static int[] readCompressed(String filename){
        int[] file_arr = new int[0];

        return file_arr;
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
            byte[] chunk = to_bytes(i);

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
            byte[] chunk = to_bytes(i);

            for(int j = 0; j < 8; j++){
                return_bytes[8*i + j] = chunk[j];
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
