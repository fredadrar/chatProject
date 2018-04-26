package controler;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import dao.ErrorBean;
import dao.PostBean;
import dao.UserBean;
import modele.BddAccess;

@Path("/WebService")
public class WebService {

	public static final Gson gson = new Gson();

	// http://localhost:8080/chatServeur/rest/WebService/helloWorld
	@Path("/helloWorld")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String hello() {
		System.out.println("HellowWorld !!");
		return "HelloWorld";
	}

	// Envoyer un message à la base :
	// http://localhost:8080/chatServeur/rest/WebService/sendPost
	@POST
	@Path("/sendPost")
	@Consumes(MediaType.APPLICATION_JSON)

	public Response sendPost(String jsonMessage) {

		PostBean message = gson.fromJson(jsonMessage, PostBean.class);

		try {
			CheckUtils.checkPost(message);
			BddAccess.savePost(message);
			return Response.status(200).build();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ErrorBean errorBean = new ErrorBean(e.getMessage());
			return Response.status(253).entity(gson.toJson(errorBean)).build();

		}

	}

	// Récupérer les messages de la base :
	// http://localhost:8080/chatServeur/rest/WebService/getPosts
	@GET
	@Path("/getPosts")
	@Produces(MediaType.APPLICATION_JSON)

	public Response getPosts() throws Exception {
		Gson gson = new Gson();
		ArrayList<PostBean> listePosts = BddAccess.getPosts();
		return Response.status(200).entity(gson.toJson(listePosts)).build();
	}

	// Envoyer un user à la base :
	// http://localhost:8080/chatServeur/rest/WebService/sendUser
	@POST
	@Path("/sendUser")
	@Consumes(MediaType.APPLICATION_JSON)

	public Response sendUser(String jsonUser) throws Exception {
		Gson gson = new Gson();
		UserBean user = gson.fromJson(jsonUser, UserBean.class);

		BddAccess.saveUser(user);
		return Response.status(200).build();
	}

	// Récupérer les users de la base :
	// http://localhost:8080/chatServeur/rest/WebService/getUsers
	@GET
	@Path("/getUsers")
	@Produces(MediaType.APPLICATION_JSON)

	public Response getUsers() throws Exception {
		Gson gson = new Gson();
		ArrayList<UserBean> listeUsers = BddAccess.getConnectedUsers();
		return Response.status(200).entity(gson.toJson(listeUsers)).build();
	}
}
