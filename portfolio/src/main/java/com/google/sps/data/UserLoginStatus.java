package com.google.sps.data;

// Class representing the login status of user
public final class UserLoginStatus {
    private final boolean isLoggedIn;
    private final String urlToRedirect;

    public UserLoginStatus(boolean _isLoggedIn, String _urlToRedirect){
        isLoggedIn = _isLoggedIn;
        urlToRedirect = _urlToRedirect;
    }
}
