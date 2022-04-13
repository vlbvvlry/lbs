public class MultiplyingThread extends Thread {

    private int left;
    private int rigth;

    MultiplyingThread(int threadNumber) {
        this.left = Main.N/Main.threadCount*(threadNumber-1);
        this.rigth = Main.N/Main.threadCount*threadNumber;
    }

    public void run() {
        for(int i = left; i < rigth; i++) {
            Main.result[i] = 0;
            for(int j = 0; j < Main.N; j++) {
                Main.result[i] += Main.vector[j] * Main.matrix[j][i];
            }
        }
    }

}