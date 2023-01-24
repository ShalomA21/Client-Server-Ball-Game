package Server;

public class UserProfile {
    private final int UserID;
    private boolean Possession;

    public UserProfile(int UserID, boolean Possession) {
        this.UserID = UserID;
        this.Possession = Possession;
    }

    public int GetUserID() {
        return UserID;
    }

    public boolean GetPossession() {
        return Possession;
    }

    public void setPossession(boolean GotBall) {
        Possession = GotBall;
    }
}
