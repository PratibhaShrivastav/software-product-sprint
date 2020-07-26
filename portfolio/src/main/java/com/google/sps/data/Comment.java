package com.google.sps.data;

public final class Comment{
    private final String commentMsg;
    private final String userEmail;
    private final long timestamp;
    private final String imageURL;

    public Comment(String _commentMsg, String _userEmail, long _timestamp, String _imageURL){
        commentMsg = _commentMsg;
        userEmail = _userEmail;
        timestamp = _timestamp;
        imageURL = _imageURL;
    }
}