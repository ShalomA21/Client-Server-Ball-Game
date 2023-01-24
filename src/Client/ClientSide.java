package Client;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientSide implements AutoCloseable {
    final int port = 8888;
    private final Scanner Input;
    private final PrintWriter Output;
    private int UserID;

    public ClientSide() throws Exception {
        Socket socket = new Socket("localhost", port);
        Input = new Scanner(socket.getInputStream());
        Output = new PrintWriter(socket.getOutputStream(), true);
        String log = Input.nextLine();
        UserID = Integer.parseInt(log.split(" ")[2]);//part of creating unique ID
        if (log.split(" ")[0].trim().compareToIgnoreCase("success") != 0)
            throw new Exception(log);
    }

    public int GetUserID() { return UserID;
    }

    public void OnlineUsers() {
        Output.println("INGAME");
        String log = Input.nextLine();
        System.out.println(log);
    }
    public void ShowPossession(){
        Output.println("LOG");
        String who = Input.nextLine();
        System.out.println(who);
    }

    public boolean CheckPossession() {
        Output.println("POSSESSION");
        return Boolean.parseBoolean(Input.nextLine());
    }

    public void Pass(int Passer, int Receiver) throws Exception {
        Output.println("PASS " + Passer + " " + Receiver);
        String line = Input.nextLine();
        if (line.trim().compareToIgnoreCase("success") != 0)
            throw new Exception(line);
    }

    @Override
    public void close() throws Exception {
        Input.close();
        Output.close();
    }

}
