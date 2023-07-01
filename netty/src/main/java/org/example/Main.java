package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        int portNumber = 8080;
        try (ServerSocket serverSocket = new ServerSocket(portNumber)){
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (IOException e) {
            System.out.println("exception occurred");
        }
    }

    private static void handleClient(Socket clientSocket) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            String request;
            String response;

            while ((request = in.readLine()) != null) {
                if ("Done".equals(request))
                    break;
                response = getResponse(request);
                out.println(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getResponse(String request) {
        return Thread.currentThread().getName();
    }
}
