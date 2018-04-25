package modele;

import dao.ErrorBean;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpUtils {

	public static String sendPostOk(String url, String paramJson) throws Exception {
		System.out.println("Url: " + url);
		OkHttpClient client = new OkHttpClient();
		MediaType JSON = MediaType.parse("application/json; charset=utf-8");
		// Corps de la requête
		RequestBody body = RequestBody.create(JSON, paramJson);
		// Création de la requete
		Request req = new Request.Builder().url(url).post(body).build();
		// Executionde la requête
		Response rep = client.newCall(req).execute();
		// Analyse du code retour
		if (rep.code() < 200 || rep.code() > 299) {
			throw new Exception("Réponse du serveur incorrect : " + rep.code());
		} else if (rep.code() == 253) {
			ErrorBean errorBean = WsUtils.gson.fromJson(rep.body().string(), ErrorBean.class);
			throw new Exception("Une erreur est survenue : " + errorBean.getMessage());

		} else {
			// Résultat de la requete.
			return rep.body().string();
		}
	}

	public static String sendGetOk(String url) throws Exception {
		System.out.println("Url : " + url);
		OkHttpClient client = new OkHttpClient();
		// Création de la requete Request
		Request request = new Request.Builder().url(url).build();
		// Execution de la requête
		Response response = client.newCall(request).execute();

		// Analyse du code retour
		if (response.code() < 200 || response.code() > 299) {
			throw new Exception("Réponse du serveur incorrect : " + response.code());
		} else if (response.code() == 253) {
			ErrorBean errorBean = WsUtils.gson.fromJson(response.body().string(), ErrorBean.class);
			throw new Exception("Une erreur est survenue : " + errorBean.getMessage());

		} else {
			// Résultat de la requete.
			return response.body().string();
		}
	}

}
