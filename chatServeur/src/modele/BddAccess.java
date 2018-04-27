package modele;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import dao.PostBean;
import dao.UserBean;

public class BddAccess {

	// private static ArrayList<PostBean> listePosts = new ArrayList<>();
	// private static ArrayList<UserBean> listeUsers = new ArrayList<>();

	public static String URL = "jdbc:mysql://localhost:3306/chatproject";
	public static final String LOGIN = "root";
	public static final String PASSWORD = "";

	// requetes
	// ajouter un user dans la base :
	private final static String QUERY_SAVE_USER = "INSERT INTO user (pseudo, last_request_time) VALUES (?, ?);";
	private final static String QUERY_SAVE_POST = "INSERT INTO post (contenu, heure, id_user) VALUES (?, ?, ?);";
	// chercher
	private final static String QUERY_FIND_CONNECTED_USERS = "SELECT * FROM user WHERE last_request_time > (?)";
	private final static String QUERY_FIND_POSTS = "SELECT * FROM post INNER JOIN user ON post.id_user = user.id";
	private final static String QUERY_GET_USER_ID = "SELECT id FROM user WHERE pseudo like ?";
	private final static String QUERY_UPDATE_USER = "UPDATE user SET last_request_time= (?) WHERE pseudo = (?);";

	public static void saveUser(UserBean user) throws Exception {

		Connection con = null;
		PreparedStatement stmt = null;
		PreparedStatement stmtCheck = null;

		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			con = DriverManager.getConnection(URL, LOGIN, PASSWORD);

			stmtCheck = con.prepareStatement(QUERY_GET_USER_ID);
			stmtCheck.setString(1, user.getPseudo());

			ResultSet resultSet = stmtCheck.executeQuery();

			if (resultSet.next() == false) {
				stmt = con.prepareStatement(QUERY_SAVE_USER);
				// Remplir la requête
				stmt.setString(1, user.getPseudo());
				stmt.setLong(2, user.getLastRequestTime());
				// Lancer la requête
				stmt.executeUpdate();
			} else {
				throw new Exception("User déjà existant !");
			}
		} finally {
			// On ferme la connexion
			if (con != null) {
				try {
					con.close();
				} catch (final SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	// else {
	// throw new Exception("Ce user existe déjà !");
	// }

	public static boolean checkIfUserExists(UserBean user) throws Exception {
		Connection con = null;
		PreparedStatement stmt = null;

		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			con = DriverManager.getConnection(URL, LOGIN, PASSWORD);

			stmt = con.prepareStatement(QUERY_GET_USER_ID);
			stmt.setString(1, user.getPseudo());

			ResultSet resultSet = stmt.executeQuery();
			if (resultSet.next() == false) {
				return false;
			} else {
				return true;
			}
		} finally {
			// On ferme la connexion
			if (con != null) {
				try {
					con.close();
				} catch (final SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@SuppressWarnings("finally")
	public static ArrayList<UserBean> getConnectedUsers() throws Exception {
		Connection con = null;
		PreparedStatement stmt = null;
		long currentTime = Instant.ofEpochMilli(0L).until(Instant.now(), ChronoUnit.MILLIS);
		ArrayList<UserBean> listeUsers = new ArrayList<>();

		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			con = DriverManager.getConnection(URL, LOGIN, PASSWORD);
			stmt = con.prepareStatement(QUERY_FIND_CONNECTED_USERS);
			stmt.setLong(1, currentTime - 300000);

			ResultSet resultSet = stmt.executeQuery();
			while (resultSet.next()) {
				UserBean user = rsetToUser(resultSet);
				listeUsers.add(user);
			}
		} finally {
			if (con != null) {// On ferme la connexion
				try {
					con.close();
				} catch (final SQLException e) {
					e.printStackTrace();
				}
			}
			return listeUsers;
		}
	}

	public static void savePost(PostBean message) throws Exception {
		Connection con = null;
		PreparedStatement stmtSavePost = null;
		PreparedStatement stmtGetUserId = null;

		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			con = DriverManager.getConnection(URL, LOGIN, PASSWORD);

			stmtGetUserId = con.prepareStatement(QUERY_GET_USER_ID);
			stmtGetUserId.setString(1, message.getUser().getPseudo());

			ResultSet resultSet = stmtGetUserId.executeQuery();
			resultSet.next();
			int userId = resultSet.getInt("id");

			stmtSavePost = con.prepareStatement(QUERY_SAVE_POST);
			// Remplir la requête
			stmtSavePost.setString(1, message.getContenu());
			stmtSavePost.setLong(2, message.getHeure());
			stmtSavePost.setInt(3, userId);
			// Lancer la requête
			stmtSavePost.executeUpdate();
		} finally {
			// On ferme la connexion
			if (con != null) {
				try {
					con.close();
				} catch (final SQLException e) {
					e.printStackTrace();
				}
			}
			// listePosts = getPosts();
		}
	}

	// public static int getUserId(UserBean user) throws Exception {
	// Connection con = null;
	// PreparedStatement stmt = null;
	//
	// try {
	// DriverManager.registerDriver(new com.mysql.jdbc.Driver());
	// con = DriverManager.getConnection(URL, LOGIN, PASSWORD);
	// stmt = con.prepareStatement(QUERY_GET_USER_ID);
	// stmt.setString(1, user.getPseudo());
	//
	// ResultSet resultSet = stmt.executeQuery(QUERY_GET_USER_ID);
	// return resultSet.getInt("id");
	//
	// } finally {
	// if (con != null) {// On ferme la connexion
	// try {
	// con.close();
	// } catch (final SQLException e) {
	// e.printStackTrace();
	// }
	// }
	// }
	// }
	// public static void savePost(PostBean message) {
	// listePosts.add(message);
	// }

	@SuppressWarnings("finally")
	public static ArrayList<PostBean> getPosts() throws Exception {
		ArrayList<PostBean> listePosts = new ArrayList<>();
		Connection con = null;
		Statement stmt = null;
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			con = DriverManager.getConnection(URL, LOGIN, PASSWORD);
			stmt = con.createStatement();
			ResultSet rset = stmt.executeQuery(QUERY_FIND_POSTS);
			while (rset.next()) {
				PostBean post = rsetToPost(rset);
				listePosts.add(post);
			}
		} finally {
			if (con != null) {// On ferme la connexion
				try {
					con.close();
				} catch (final SQLException e) {
					e.printStackTrace();
				}
			}
			return listePosts;
		}
	}

	private static UserBean rsetToUser(ResultSet rset) throws SQLException {
		UserBean user = new UserBean();
		user.setPseudo(rset.getString("pseudo"));
		user.setLastRequestTime(rset.getLong("last_request_time"));
		return user;
	}

	private static PostBean rsetToPost(ResultSet rset) throws SQLException {
		PostBean post = new PostBean();
		UserBean user = new UserBean();

		user.setPseudo(rset.getString("pseudo"));
		user.setLastRequestTime(rset.getLong("last_request_time"));

		post.setContenu(rset.getString("contenu"));
		post.setHeure(rset.getLong("heure"));
		post.setUser(user);

		return post;
	}

	// public static ArrayList<PostBean> getPosts() {
	// return listePosts;
	// }

	// public static void saveUser(UserBean user) {
	// listeUsers.add(user);
	// }

	// public static ArrayList<UserBean> getConnectedUsers() {
	// return listeUsers;
	// }

	public static void updateUserTimestamp(UserBean user) throws Exception {
		Connection con = null;
		PreparedStatement stmt = null;
		long currentTime = Instant.ofEpochMilli(0L).until(Instant.now(), ChronoUnit.MILLIS);

		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			con = DriverManager.getConnection(URL, LOGIN, PASSWORD);

			stmt = con.prepareStatement(QUERY_UPDATE_USER);
			stmt.setLong(1, currentTime);
			stmt.setString(2, user.getPseudo());

			stmt.executeUpdate(QUERY_UPDATE_USER);

		} finally {
			if (con != null) {// On ferme la connexion
				try {
					con.close();
				} catch (final SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
