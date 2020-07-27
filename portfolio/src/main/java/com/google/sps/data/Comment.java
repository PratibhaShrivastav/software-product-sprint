package com.google.sps.data;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public final class Comment{
    private final String commentMsg;
    private final String userEmail;
    private final long timestamp;
    private final String imageURL;

}