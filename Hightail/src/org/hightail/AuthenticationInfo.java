package org.hightail;

public class AuthenticationInfo {
    private static String username = "";
    private static String password = "";

    public static void SetUsername(String username) {
        AuthenticationInfo.username = username;
    }
    
    public static void SetPassword(String password) {
        AuthenticationInfo.password = password;
    }
    
    public static String GetUsername() {
        return username;
    }
    
    public static String GetPassword() {
        return password;
    }
}
