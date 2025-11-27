package bg.sofia.uni.fmi.netpr.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Array2DFiller {
    int[][] array;

    public Array2DFiller( int arrayX, int arrayY) {
        array = new int[arrayY][arrayX];
    }

    public String fillAndGetResultConcurrent() throws InterruptedException {
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < array.length; ++i) {
            int finalI = i;
            Thread currThread = new Thread(() -> {
                for (int j = 0; j < array[finalI].length; ++j) {
                    array[finalI][j] = ThreadLocalRandom.current().nextInt(100, 999);
                }
            });
            currThread.start();
            threads.add(currThread);
        }

        for (Thread currThread: threads) {
            currThread.join();
        }

        return Arrays.deepToString(array);
    }

    public String fillAndGetResult() {
        for (int i = 0; i < array.length; ++i) {
            for (int j = 0; j < array[i].length; ++j) {
                array[i][j] = ThreadLocalRandom.current().nextInt(100, 999);
            }
        }

        return Arrays.deepToString(array);
    }
}
