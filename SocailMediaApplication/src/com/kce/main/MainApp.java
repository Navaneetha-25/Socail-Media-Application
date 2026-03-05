package com.kce.main;

import java.util.List;
import java.util.Scanner;

import com.kce.entity.Comment;
import com.kce.entity.Notification;
import com.kce.entity.Post;
import com.kce.entity.User;
import com.kce.service.SocialMediaService;

public class MainApp {

	static Scanner sc = new Scanner(System.in);
	static SocialMediaService service = new SocialMediaService();
	static User currentUser = null;

	public static void main(String[] args) {

		while (true) {

			if (currentUser == null) {
				System.out.println("\n1. Signup");
				System.out.println("2. Login");
				System.out.println("3. Exit");
				System.out.print("Choice: ");
				int ch = sc.nextInt();
				sc.nextLine();

				switch (ch) {
				case 1:
					signup();
					break;
				case 2:
					login();
					break;
				case 3:
					System.exit(0);
				}
			} else {
				userMenu();
			}
		}
	}

	private static void signup() {
		System.out.print("Name: ");
		String name = sc.nextLine();
		System.out.print("DOB: ");
		String dob = sc.nextLine();
		System.out.print("Age: ");
		int age = sc.nextInt();
		sc.nextLine();
		System.out.print("Location: ");
		String loc = sc.nextLine();
		System.out.print("Occupation: ");
		String occ = sc.nextLine();

		service.signup(name, dob, age, loc, occ);
		System.out.println("Signup Successful!");
	}

	private static void login() {
		System.out.print("Enter Name: ");
		String name = sc.nextLine();
		currentUser = service.findUser(name);

		if (currentUser != null)
			System.out.println("Login Successful!");
		else
			System.out.println("User not found!");
	}

	private static void userMenu() {

		System.out.println("\n1. Send Friend Request");
		System.out.println("2. Accept Friend Request");
		System.out.println("3. View Friends");
		System.out.println("4. Create Post");
		System.out.println("5. View Feed");
		System.out.println("6. View Notifications");
		System.out.println("7. Logout");

		System.out.print("Choice: ");
		int ch = sc.nextInt();
		sc.nextLine();

		switch (ch) {

		case 1:
			for (User u : service.getAllUsers())
				if (!u.getName().equals(currentUser.getName()))
					System.out.println(u.getName());

			System.out.print("Enter name: ");
			String name = sc.nextLine();
			User to = service.findUser(name);

			if (to != null)
				service.sendRequest(currentUser, to);
			break;

		case 2:
			List<User> req = currentUser.getFriendRequests();
			for (int i = 0; i < req.size(); i++)
				System.out.println((i + 1) + ". " + req.get(i).getName());

			if (!req.isEmpty()) {
				System.out.print("Enter number: ");
				int num = sc.nextInt();
				sc.nextLine();
				service.acceptRequest(currentUser, num - 1);
			}
			break;

		case 3:
			for (User f : currentUser.getFriends()) {
				System.out.println("Name: " + f.getName());
				System.out.println("Age: " + f.getAge());
				System.out.println("Location: " + f.getLocation());
				System.out.println("Occupation: " + f.getOccupation());
			}
			break;

		case 4:
			System.out.print("Enter content: ");
			String content = sc.nextLine();
			service.createPost(currentUser, content);
			break;

		case 5:
			service.showFeed(currentUser);

			System.out.print("\nEnter Post ID to interact (0 to skip): ");
			int id = sc.nextInt();
			sc.nextLine();

			if (id > 0) {
				Post p = service.getPostFromFeed(currentUser, id);

				if (p == null) {
					System.out.println("Invalid Post ID");
					break;
				}

				System.out.println("1. Like");
				System.out.println("2. Comment");
				System.out.println("3. Share");
				int opt = sc.nextInt();
				sc.nextLine();

				if (opt == 1) {
					p.likePost();
					p.getAuthor().getNotifications().add(new Notification(currentUser.getName() + " liked your post."));
				}

				else if (opt == 2) {
					System.out.print("Enter comment: ");
					String msg = sc.nextLine();
					Comment comment = new Comment(currentUser, msg);
					p.addComment(comment);

					p.getAuthor().getNotifications()
							.add(new Notification(currentUser.getName() + " commented on your post."));
				}

				else if (opt == 3) {
					System.out.print("Share with: ");
					String uname = sc.nextLine();
					User shareUser = service.findUser(uname);

					if (shareUser != null) {
						shareUser.getNotifications()
								.add(new Notification(currentUser.getName() + " shared a post: " + p.getContent()));
					}
				}
			}
			break;
		case 6:
			service.showNotifications(currentUser);
			break;

		case 7:
			currentUser = null;
			break;
		}
	}
}