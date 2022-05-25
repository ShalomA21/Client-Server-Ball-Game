package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerProgram
{
    private final static int port = 8888;
    private static final Bank bank = new Bank();
    private static String ball;


    public static void main(String[] args)
    {
        bank.createAccount(1, 1001, 100);
        bank.createAccount(2, 1002, 0);
        bank.createAccount(3, 1003, 0);
        bank.createAccount(4, 1004, 0);

        RunServer();

    }

    private static void RunServer() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Waiting for incoming connections...");
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(new ClientHandler(socket, bank)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}