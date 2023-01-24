package Server;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//The code used to pass the ball,check for possession,logout of the game,auto pass upon termination and create a unique ID was inspired by code I found
//on https://github.com/sat8n/ball-game/blob/276f23e0de8c9a26ff14708f2f05e18fd536d222/BallGameJava/src/gameServer/Game.java

public class Start {
    public static final List<UserProfile> userProfiles = new ArrayList<>();
    private int Numbers;


    public void Play() {
    }

    private void CreateUser(int UserID, boolean possession) {
        UserProfile userProfile = new UserProfile(UserID, possession);
        userProfiles.add(userProfile);
    }

    public int CreateUserID() {
        synchronized (userProfiles) {

            Numbers = 0;
            if (userProfiles.size() != 0) {
                for (UserProfile userProfile : userProfiles)
                    if (Numbers < userProfile.GetUserID())
                        Numbers = userProfile.GetUserID();
            }
            int usersID = Numbers + 10;
            if (userProfiles.size() < 1)
                CreateUser(usersID,true);
            else
                CreateUser(usersID,false);
            return usersID;
        }
    }

    public String OnlineUsers() {
        String loggedin = "";
        if (userProfiles.size() == 1) {
            loggedin = loggedin + "1 User has logged in";
        }
        else
            loggedin = loggedin + userProfiles.size() + " User logged in";
        for (UserProfile userProfile : userProfiles) {
            if (userProfile.GetPossession() == true) {
                loggedin = loggedin +("( UserID " + userProfile.GetUserID() + ", is in possession of the ball) ");
            }
        }
        return loggedin;
    }

    public void ServerMessages (){
        if (userProfiles.size() != 0) {
            if (userProfiles.size() == 1)
                System.out.println("User's Online: " + userProfiles.size());
        }
        else
            System.out.println("There are no players in the game.");
        for (UserProfile userProfile : userProfiles) {
            if (userProfile.GetPossession() == true) {
                System.out.println("( UserID " + userProfile.GetUserID() + ", is in possession of the ball) ");
            }
        }
    }

    public String ShowPossession(){
        String ball = "";
        for (UserProfile userProfile : userProfiles) {
            if (userProfile.GetPossession() == true) {
                ball = ball +("( UserID " + userProfile.GetUserID() + ", is in possession of the ball) ");
            }
        }
        if (userProfiles.size() == 1) {
            ball = ball + "1 User logged in";
        }
        else
            ball = ball + userProfiles.size() + " User logged in";
        return ball;
    }


    public void Logout(int UserID) {
        int Disconnect = 0;
        for (int c = 0; c < userProfiles.size(); c++) {
            if (UserID == userProfiles.get(c).GetUserID())
                Disconnect = c;
        }
        userProfiles.remove(Disconnect);
    }

    public boolean CheckPossession(int UserID) {
        boolean Possession = false;
        for (UserProfile userProfile : userProfiles)
            if (UserID == userProfile.GetUserID())
                Possession = userProfile.GetPossession();
        return Possession;
    }


    public void Pass(int Passer, int Receiver) {
        int Pass = 0;
        int Receive = 0;
        for (int a = 0; a < userProfiles.size(); a++) {
            if (Passer == userProfiles.get(a).GetUserID())
                Pass = a;
        }
        for (int b = 0; b < userProfiles.size(); b++) {
            if (Receiver == userProfiles.get(b).GetUserID())
                Receive = b;
        }
        UserProfile passer = userProfiles.get(Pass);
        UserProfile receiver = userProfiles.get(Receive);

        passer.setPossession(false);
        receiver.setPossession(true);

        if (passer != receiver)
            System.out.println("UserID " + Passer + " played the ball to UserID " + Receiver);
        else
            System.out.println("No User with that ID the ball remains with you " + Passer + ".");
    }

    public void AutoPass(int PrevPasser) {
        Random rand = new Random();
        int Target = 0;
        if (userProfiles.size() != 0) {
            Target = rand.nextInt(userProfiles.size());
            userProfiles.get(Target).setPossession(true);
            System.out.println("A User has left Automatically passing ball to UserID: " + userProfiles.get(Target).GetUserID());
        }
    }
}