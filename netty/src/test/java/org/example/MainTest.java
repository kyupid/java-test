package org.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainTest {

    private final Queue<Socket> queue = new ConcurrentLinkedQueue<>();

    private boolean flag;

    @Test
    public void testApp() throws InterruptedException {
        Thread serverThread = new Thread(() -> Main.main(null));
        Thread clientThread = new Thread(this::startApp);

        serverThread.start();
        clientThread.start();

        clientThread.join();
        serverThread.join();
    }

    private void startApp() {
        for (int i = 0; i < 1000; i++) {
            try (Socket socket = new Socket("localhost", 9999)) {
                queue.add(socket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        int count = 0;
        while (queue.size() > 0) {
            Socket socket = queue.poll();
            System.out.println(queue.size());
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String response = in.readLine();
                System.out.println("Received response: " + response);
                socket.close();
                Thread.sleep(100);
            } catch (IOException e) {
                System.out.println("close fail");
            } catch (InterruptedException e) {
                System.out.println("sleep fail");
            }
        }
        System.out.println("done");
    }

    private static String randomUrl() {
        return "https://www." + randomString() + ".com";
    }

    private static String randomString() {
        String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        int length = random.nextInt(10) + 5; // 5에서 14 사이의 길이로 무작위 설정

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(alphabet.length());
            char randomChar = alphabet.charAt(index);
            sb.append(randomChar);
        }

        return sb.toString();
    }

}
