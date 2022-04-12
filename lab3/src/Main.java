import java.io.*;
import java.util.*;

public class Main {

    private static final int N = 30;
    private static File f = new File("result.txt");
    private static FileOutputStream fos = null;
    private static int[] vector = new int[N];
    private static int[][] matrix = new int[N][N]; 

    private static void creatingFileAttributes() {
        try {
            f.createNewFile();
            fos = new FileOutputStream(f, true);
            (new FileOutputStream(f, false)).write("".getBytes());
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void filling(){
        for(int i = 0; i < N; i++) {
            vector[i] = (int)(Math.random()*N);
        }
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                matrix[i][j] = (int)(Math.random()*N);
            }
        }
    }

    private static void writingToFile() {
        try {
            for(int i = 0; i < N; i++) {
                fos.write(String.format("%3d ", vector[i]).getBytes());
            }
            fos.write("\n".getBytes());
            for(int i = 0; i < N; i++) {
                fos.write("\n".getBytes());
                for(int j = 0; j < N; j++) {
                    fos.write(String.format("%3d ", matrix[i][j]).getBytes());
                }
            }
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        creatingFileAttributes();
        filling();
        writingToFile();
    }

}