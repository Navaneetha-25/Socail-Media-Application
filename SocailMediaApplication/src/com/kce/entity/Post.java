package com.kce.entity;

import java.util.ArrayList;
import java.util.List;

public class Post {

    private String content;
    private User author;
    private int likes;
    private List<Comment> comments = new ArrayList<>();

    public Post(String content, User author) {
        this.content = content;
        this.author = author;
        this.likes = 0;
    }

    public String getContent() { return content; }
    public User getAuthor() { return author; }
    public int getLikes() { return likes; }
    public List<Comment> getComments() { return comments; }

    public void likePost() {
        likes++;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }
}