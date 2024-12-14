public class App{
    public static void main(String[] args){
        ImageMatrix img_matrix = new ImageMatrix(args[0]);
        SVDcompress img_compressed = new SVDcompress(img_matrix);
    }
}
