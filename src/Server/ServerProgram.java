package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerProgram {
    private final static int port = 8888;
    private final static Start START = new Start();

    public static void main(String[] args) {
        START.Play();
        RunProgram();
    }

    private static void RunProgram() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Waiting for incoming connections...");
            while (true) {
                Socket sock = serverSocket.accept();
                new Thread(new ClientHandler(sock, START)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
