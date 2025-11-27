package bg.sofia.uni.fmi.netpr.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server2D {

    private static final int SERVER_PORT = 4444;
    private static final int MAX_EXECUTOR_THREADS = 10;

    public static void main(String[] args) {

        try (ExecutorService executor = Executors.newFixedThreadPool(MAX_EXECUTOR_THREADS);
            ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {

            Thread.currentThread().setName("Server Thread");
            System.out.println("Server started and listening for connect requests");

            Socket clientSocket;
            while (true) {
                clientSocket = serverSocket.accept();
                System.out.println("Accepted connection request from client " + clientSocket.getInetAddress());

                ClientRequestHandler clientHandler = new ClientRequestHandler(clientSocket);
                executor.execute(clientHandler);
            }

        } catch (IOException e) {
            throw new RuntimeException("There is a problem with the server socket", e);
        }
    }
}
