package org.hightail;

public class AuthenticationInfo {
    private static String username = "";
    private static String password = "";

    public static void setUsername(String username) {
        AuthenticationInfo.username = username;
    }
    
    public static void setPassword(String password) {
        AuthenticationInfo.password = password;
    }
    
    public static String getUsername() {
        return username;
    }
    
    public static String getPassword() {
        return password;
    }
}
