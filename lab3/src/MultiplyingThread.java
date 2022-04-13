public class MultiplyingThread extends Thread {

    private final int left;
    private final int rigth;

    MultiplyingThread(int threadNumber) {
        this.left = Main.N/Main.threadCount*(threadNumber-1);
        if(threadNumber == Main.threadCount) {
            this.rigth = Main.N;
            return;
        }
        this.rigth = Main.N/Main.threadCount*threadNumber;
    }

    public void run() {
        for(int i = left; i < rigth; i++) {
            for(int j = 0; j < Main.N; j++) {
                Main.result[i] += Main.vector[j] * Main.matrix[j][i];
            }
        }
    }

}