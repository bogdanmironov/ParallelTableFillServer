package bg.sofia.uni.fmi.netpr.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client2D {

    private static final int SERVER_PORT = 4444;

    public static void main(String[] args) {

        try (Socket socket = new Socket("localhost", SERVER_PORT);
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Connected to the server.");

            while (true) {
                System.out.println("Enter dimensions of matrix:<int X> <int Y>");
                String message = scanner.nextLine();

                if ("quit".equals(message)) {
                    break;
                }

                writer.println(message);

                String reply = reader.readLine();
                if ('-' == reply.charAt(0)) {
                    System.out.println(reply);
                    continue;
                }

                String result = reader.readLine();
                String longTime = reader.readLine();
                System.out.println(result);
                System.out.println(reply);
                System.out.println(longTime);
            }

        } catch (IOException e) {
            throw new RuntimeException("There is a problem with the network communication", e);
        }
    }

}
