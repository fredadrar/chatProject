package modele;

import java.util.ArrayList;

import dao.PostBean;
import dao.UserBean;

public class BddAccess {

	private static ArrayList<PostBean> listePosts = new ArrayList<>();
	private static ArrayList<UserBean> listeUsers = new ArrayList<>();

	public static void savePost(PostBean message) {
		listePosts.add(message);
	}

	public static ArrayList<PostBean> getPosts() {
		return listePosts;
	}

	public static void saveUser(UserBean user) {
		listeUsers.add(user);
	}

	public static ArrayList<UserBean> getUsers() {
		return listeUsers;
	}

	public static UserBean updateUser(UserBean user, boolean isConnected) {
		return user;
	}
}
