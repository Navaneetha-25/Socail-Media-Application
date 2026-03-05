package com.kce.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.kce.entity.Comment;
import com.kce.entity.Notification;
import com.kce.entity.Post;
import com.kce.entity.User;

public class SocialMediaService {

	private List<User> users = new ArrayList<>();

	public User signup(String name, String dob, int age, String location, String occupation) {
		User user = new User(name, dob, age, location, occupation);
		users.add(user);
		return user;
	}

	public User findUser(String name) {
		for (User u : users) {
			if (u.getName().equalsIgnoreCase(name))
				return u;
		}
		return null;
	}

	public List<User> getAllUsers() {
		return users;
	}
	
	public void sendRequest(User from, User to) {

	    if (to == null) {
	        System.out.println("User not found.");
	        return;
	    }

	    if (from.getFriends().contains(to)) {
	        System.out.println("You are already friends.");
	        return;
	    }

	    if (to.getFriendRequests().contains(from)) {
	        System.out.println("Friend request already sent.");
	        return;
	    }

	    to.getFriendRequests().add(from);
	    to.getNotifications().add(
	        new Notification(from.getName() + " sent you a friend request.")
	    );

	    System.out.println("Friend request sent successfully.");
	}
	
	public void acceptRequest(User user, int index) {

	    if (index < 0 || index >= user.getFriendRequests().size()) {
	        System.out.println("Invalid selection.");
	        return;
	    }

	    User requester = user.getFriendRequests().get(index);

	    user.addFriend(requester);
	    requester.addFriend(user);

	    user.getFriendRequests().remove(index);

	    requester.getNotifications().add(
	        new Notification(user.getName() + " accepted your friend request.")
	    );

	    System.out.println("Friend request accepted.");
	}

	public void createPost(User user, String content) {
		user.getPosts().add(new Post(content, user));
	}

	public void showFeed(User user) {

		List<Post> feed = new ArrayList<>();

		for (User friend : user.getFriends()) {
			feed.addAll(friend.getPosts());
		}

		Collections.reverse(feed);

		if (feed.isEmpty()) {
			System.out.println("No posts available.");
			return;
		}

		int i = 1;
		for (Post p : feed) {
			System.out.println("\nPost ID: " + i++);
			System.out.println("Author: " + p.getAuthor().getName());
			System.out.println("Content: " + p.getContent());
			System.out.println("Likes: " + p.getLikes());

			if (p.getComments().isEmpty()) {
				System.out.println("Comments: None");
			} else {
				for (Comment c : p.getComments()) {
					System.out.println(c.getUser().getName() + ": " + c.getMessage());
				}
			}
		}
	}

	public Post getPostFromFeed(User user, int id) {

		List<Post> feed = new ArrayList<>();

		for (User friend : user.getFriends()) {
			feed.addAll(friend.getPosts());
		}

		Collections.reverse(feed);

		if (id > 0 && id <= feed.size())
			return feed.get(id - 1);

		return null;
	}

	public void showNotifications(User user) {

		if (user.getNotifications().isEmpty()) {
			System.out.println("No notifications.");
			return;
		}

		for (Notification n : user.getNotifications()) {
			System.out.println(n.getMessage());
		}

		user.getNotifications().clear();
	}
}