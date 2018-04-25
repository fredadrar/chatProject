package controler;

import java.util.ArrayList;

import dao.PostBean;
import dao.UserBean;
import modele.WsUtils;
import view.Ihm;

public class Controler {

	private Ihm ihm;

	// DATA
	private UserBean user;
	private ArrayList<UserBean> userList;
	private ArrayList<PostBean> postList;
	private String errorMessage;

	public Controler() {
		userList = new ArrayList<>();
		postList = new ArrayList<>();
	}

	private void refreshScreen() {

		if (user == null) {
			ihm.visibleElement(true, false);
		} else {
			ihm.visibleElement(false, true);
		}

		ihm.updateChatWindow(postList);
		ihm.updateUserList(userList);
		ihm.setErrorMessage(errorMessage);

	}

	/*
	 * public void sendPost() {
	 * 
	 * }
	 */

	public void clickRefresh() {

		try {
			errorMessage = "";
			userList = WsUtils.getUsers();
			postList = WsUtils.getPosts();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			errorMessage = e.getMessage();
		}

		refreshScreen();

	}

	public void clickSend(String contenu) {
		PostBean post = new PostBean();
		post.setContenu(contenu);
		post.setUser(user);
		try {
			WsUtils.sendPost(post);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		refreshScreen();

	}

	public void setIhm(Ihm ihm) {
		this.ihm = ihm;
	}

	public void clickPseudo(String pseudo) {
		UserBean userTemp = new UserBean();
		userTemp.setPseudo(pseudo);
		userTemp.setConnected(true);
		try {
			WsUtils.sendUser(user);
			user = userTemp;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			errorMessage = e.getMessage();
		}
		refreshScreen();

	}

}
