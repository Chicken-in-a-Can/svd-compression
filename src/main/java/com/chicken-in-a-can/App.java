public class App{
    public static void main(String[] args){
        if(args[0].equals("--compress")){
            System.out.println("Converting image to matrix");
            ImageMatrix img_matrix = new ImageMatrix(args[1]);
            System.out.println("Compressing image matrix");
            SVDcompress img_compressed = new SVDcompress(img_matrix);
            String new_filename = args[1] + ".svdc";
            SVDio.saveCompressed(new_filename, img_compressed);
        } else if(args[0].equals("--decompress")){
            float[] content = SVDio.readCompressed(args[1]);

        } else{
            System.err.println("Specify either `--compress` or `--decompress`");
        }
    }
}
