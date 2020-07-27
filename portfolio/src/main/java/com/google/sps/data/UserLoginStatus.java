package com.google.sps.data;
import lombok.Builder;

// Class representing the login status of user
@Builder
public final class UserLoginStatus {
    private final boolean isLoggedIn;
    private final String urlToRedirect;

}
