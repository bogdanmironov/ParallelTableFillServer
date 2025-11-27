package bg.sofia.uni.fmi.netpr.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientRequestHandler implements Runnable {

    private final Socket socket;

    public ClientRequestHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        Thread.currentThread().setName("Client Request Handler for " + socket.getRemoteSocketAddress());

        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            String inputLine;
            while ((inputLine = in.readLine()) != null) {

                String[] inputParams = inputLine.split("\\s+");

                if (inputParams.length != 2) {
                    out.println("-Wrong number of parameters");
                    continue;
                }

                int x, y;
                try {
                    x = Integer.parseInt(inputParams[0]);
                    y = Integer.parseInt(inputParams[1]);
                } catch (Exception e) {
                    out.println("-Parameters must be integers");
                    continue;
                }

                Array2DFiller filler = new Array2DFiller(x, y);

                long startTime = System.nanoTime();
                String result = filler.fillAndGetResultConcurrent();
                long endTime = System.nanoTime();

                long duration = endTime - startTime;
                out.println(String.format("Processing took %d nanoseconds", duration));
                out.println(result);

                startTime = System.nanoTime();
                filler.fillAndGetResult();
                endTime = System.nanoTime();

                duration = endTime - startTime;
                out.println(String.format("Otherwise it would have taken %d nanoseconds", duration));
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}