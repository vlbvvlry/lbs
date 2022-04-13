import java.io.*;
import java.util.*;

public class Main {

    public static final int N = 12;
    public static final int threadCount = 4;
    public static int[] vector = new int[N];
    public static int[][] matrix = new int[N][N];
    public static int[] result = new int[N];

    private static File f = new File("result.txt");
    private static FileOutputStream fos = null; 

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
            fos.write("\n\nResult:\n".getBytes());
            fos.write(Arrays.toString(result).getBytes()); 
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void multiplying() {
        MultiplyingThread[] mt = new MultiplyingThread[threadCount];
        for(int i = 0; i < threadCount; i++) {
            mt[i] = new MultiplyingThread(i+1);
            mt[i].start();
        }
    }

    public static void main(String[] args) {
        creatingFileAttributes();
        filling();
        multiplying();
        writingToFile();
    }

}