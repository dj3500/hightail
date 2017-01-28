package org.hightail;

public class AuthenticationInfo {
    private final String username;
    private final String password;
    
    public AuthenticationInfo(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    public String GetUsername() {
        return this.username;
    }
    
    public String GetPassword() {
        return this.password;
    }
}
