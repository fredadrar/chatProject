package modele;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import dao.PostBean;
import dao.UserBean;

public class WsUtils {

	public static final String URL_SERVER = "http://192.168.20.1:8080/chatServeur/rest/WebService";
	public static final String GET_POST = URL_SERVER + "/getPosts";
	public static final String GET_USER = URL_SERVER + "/getUsers";
	public static final String SEND_POST = URL_SERVER + "/sendPost";
	public static final String SEND_USER = URL_SERVER + "/sendUser";

	public static final Gson gson = new Gson();

	public static void sendPost(PostBean post) throws Exception {

		String json = gson.toJson(post);
		OkHttpUtils.sendPostOk(SEND_POST, json);

	}

	public static ArrayList<PostBean> getPosts() throws Exception {
		String json = OkHttpUtils.sendGetOk(GET_POST);
		ArrayList<PostBean> list = gson.fromJson(json, new TypeToken<ArrayList<PostBean>>() {
		}.getType());
		return list;

	}

	public static void sendUser(UserBean user) throws Exception {
		String json = gson.toJson(user);
		OkHttpUtils.sendPostOk(SEND_USER, json);

	}

	public static ArrayList<UserBean> getUsers() throws Exception {
		String json = OkHttpUtils.sendGetOk(GET_USER);
		ArrayList<UserBean> list = gson.fromJson(json, new TypeToken<ArrayList<UserBean>>() {
		}.getType());
		return list;
	}

}
