package SenArchPackage;

public class Session {

    public static int userId;        // store user id
    public static String username;   // store username (optional for greetings etc.)

    public static void clear() {
        userId = 0;
        username = null;
    }
}
