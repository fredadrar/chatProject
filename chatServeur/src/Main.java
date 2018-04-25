import com.google.gson.Gson;

import dao.PostBean;
import dao.UserBean;

public class Main {

	public static void main(String[] args) {

		Gson gson = new Gson();
		UserBean user = new UserBean();
		PostBean post = new PostBean();

		user.setPseudo("ducon");

		post.setContenu("test lorem ipsum");
		post.setUser(user);

		String jsonMessage = gson.toJson(post);
		System.out.println(jsonMessage);

	}
}
