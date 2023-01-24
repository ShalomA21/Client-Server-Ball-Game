package Server;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
//The code used to pass the ball,check for possession ,logout of the game,auto pass upon termination and create a unique ID was inspired by code I found
//on https://github.com/sat8n/ball-game/blob/276f23e0de8c9a26ff14708f2f05e18fd536d222/BallGameJava/src/gameServer/Game.java

public class ClientHandler implements Runnable {
    private final Socket Servsocket;
    private Start start;

    public ClientHandler(Socket Servsocket, Start start) {
        this.Servsocket = Servsocket;
        this.start = start;
    }

    @Override
    public void run() {
        int UserID = 0;
        try (
                Scanner Input = new Scanner(Servsocket.getInputStream());
                PrintWriter Output = new PrintWriter(Servsocket.getOutputStream(), true)) {
            try {
                UserID = start.CreateUserID();
                System.out.println("New User online! UserID " + UserID + " has logged in.");
                start.OnlineUsers();

                Output.println("SUCCESS connection: " + UserID);

                while (true) {
                    String line = Input.nextLine();
                    String[] substrings = line.split(" ");
                    switch(substrings[0].toLowerCase()) {
                        case "ingame":
                            Output.println(start.OnlineUsers());
                            break;
                        case "possession":
                            Output.println(start.CheckPossession(UserID));
                            break;
                        case "pass":
                            int Passer = Integer.parseInt(substrings[1]);
                            int Receiver = Integer.parseInt(substrings[2]);
                            start.Pass(Passer, Receiver);
                            Output.println("SUCCESS");
                            break;
                        case "log":
                            Output.println(start.ShowPossession());
                            break;
                        default:
                            throw new Exception("Command not recognized: " + substrings[0]);
                    }
                }
            } catch (Exception e) {
                Output.println("PROBLEM " + e.getMessage());
                Servsocket.close();
            }
        } catch (Exception e) { }
        finally {
            System.out.println("UserID " + UserID + " disconnected.");
            boolean CheckPossession = start.CheckPossession(UserID);
            start.Logout(UserID);
            if (CheckPossession && start.OnlineUsers().length() != 0) {
                start.AutoPass(UserID);
                start.ServerMessages();
            }
        }
    }
}