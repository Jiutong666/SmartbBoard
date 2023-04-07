package sample;


public class LoginUserHolder {


    private static User current;

    public static void setCurrent(User user){
        current = user;
    }

    public static User getCurrent() {
        return current;
    }



}
