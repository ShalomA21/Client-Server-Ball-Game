package Client;

import java.util.Scanner;

public class ClientProgram {
    public static void main(String[] args) {
        try {
            Scanner in = new Scanner(System.in);
            try (ClientSide clientSide = new ClientSide()) {
                System.out.println("Logged in successfully!");
                System.out.println("WELCOME");
                clientSide.OnlineUsers();

                while (true) {
                    System.out.println("This UserID is " + clientSide.GetUserID() + ".");
                    System.out.println("Press 1 to pass the ball[1]");
                    int command = Integer.parseInt(in.nextLine());
                    switch (command) {
                        case 1 :
                            if (clientSide.CheckPossession()) {
                                clientSide.ShowPossession();
                                System.out.println("Enter the ID of the user you want to pass the ball to: ");
                                int Passer = clientSide.GetUserID();
                                int Receiver = Integer.parseInt(in.nextLine());
                                clientSide.Pass(Passer, Receiver);
                                clientSide.ShowPossession();
                            }
                            else
                                System.out.println("You do not have the ball dummy!");
                            break;
                        default:
                            System.out.println("Command not recognized: " + command);
                            break;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
