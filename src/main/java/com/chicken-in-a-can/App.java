public class App{
    public static void main(String[] args){
        if(args[0].equals("--compress")){
            ImageMatrix img_matrix = new ImageMatrix(args[1]);
            SVDcompress img_compressed = new SVDcompress(img_matrix);
        } else if(args[0].equals("--decompress")){

        } else{
            System.err.println("Specify either `--compress` or `--decompress`");
        }
    }
}
