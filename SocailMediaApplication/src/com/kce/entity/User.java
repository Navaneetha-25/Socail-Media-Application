package com.kce.entity;

import java.util.ArrayList;
import java.util.List;

public class User {
	private String name;
	private String dob;
	private int age;
	private String location;
	private String occupation;
	
	private List<User> friends=new ArrayList();
	private List<User> friendRequests=new ArrayList();
	private List<Post> posts=new ArrayList();
	private List<Notification> notifications=new ArrayList();
	
	public User(String name, String dob, int age, String location, String occupation) {
		super();
		this.name = name;
		this.dob = dob;
		this.age = age;
		this.location = location;
		this.occupation = occupation;
	}
	
	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public List<User> getFriends() {
		return friends;
	}
	public void setFriends(List<User> friends) {
		this.friends = friends;
	}
	public List<User> getFriendRequests() {
		return friendRequests;
	}
	public void setFriendRequests(List<User> friendRequests) {
		this.friendRequests = friendRequests;
	}
	public List<Post> getPosts() {
		return posts;
	}
	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}
	public List<Notification> getNotifications() {
		return notifications;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setNotifications(List<Notification> notifications) {
		this.notifications = notifications;
	}
	
	public void addFriend(User user) {
		friends.add(user);
	}
	
	
}
