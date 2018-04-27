package controler;

import dao.PostBean;

public class CheckUtils {

	public static void checkPost(PostBean message) throws Exception {
		if (message == null) {
			throw new Exception("Le message est nul");

		} else if (message.getUser() == null) {
			throw new Exception("Le user est nul");

		} else if (message.getUser().getPseudo() == null || message.getUser().getPseudo().trim().length() == 0) {
			throw new Exception("Le pseudo de l'user est nul");

		}
	}

}
