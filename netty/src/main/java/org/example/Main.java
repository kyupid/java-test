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
            Socket clientSocket = serverSocket.accept();

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            String request;
            String response;

            while((request = in.readLine()) != null) {
                if ("Done".equals(request))
                    break;
                response = getResponse(request);
                out.println(response);
            }
        } catch (IOException e) {
            System.out.println(e);
        }

    }

    private static String getResponse(String request) {
        return "hello response";
    }
}