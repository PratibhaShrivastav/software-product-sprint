package com.google.sps.servlets;

import com.google.sps.data.UserLoginStatus;
import com.google.appengine.api.datastore.*;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/user-auth-status")
public class AuthenticationServlet extends HttpServlet {

    private static final Gson GSON = new Gson();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserService userService = UserServiceFactory.getUserService();
        boolean isLoggedIn = userService.isUserLoggedIn();
        String urlToRedirect;
        if (userService.isUserLoggedIn()) {
            urlToRedirect = userService.createLogoutURL("/");
        }
        else{
            urlToRedirect = userService.createLoginURL("/");
        }
        UserLoginStatus userLoginStatus = new UserLoginStatus(isLoggedIn, urlToRedirect);
        response.setContentType("application/json");
        response.getWriter().println(GSON.toJson(userLoginStatus));
    }
}