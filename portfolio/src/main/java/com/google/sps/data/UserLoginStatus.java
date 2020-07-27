package com.google.sps.data;
import lombok.AllArgsConstructor;

// Class representing the login status of user
@AllArgsConstructor
public final class UserLoginStatus {
    private final boolean isLoggedIn;
    private final String urlToRedirect;

}
