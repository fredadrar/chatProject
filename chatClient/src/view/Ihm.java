package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import controler.Controler;
import dao.PostBean;
import dao.UserBean;

public class Ihm {

	private JFrame frame;
	private JTextField inputBarre;
	private JTextField pseudoBarre;
	private JButton btnRefresh;
	private JButton btnSend;
	private JLabel pseudoLabel;
	private JButton pseudoBtn;
	private JTextArea chatWindow;
	private JTextArea userList;
	private Controler controler;
	private JScrollPane scrollPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Ihm window = new Ihm();
					window.frame.setVisible(true);

					Controler controler = new Controler();

					controler.setIhm(window);

					window.setControler(controler);
					controler.clickRefresh();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Ihm() {
		initialize();
	}

	// ------------------------------------
	// Methodes de mise à jour graphique
	// ------------------------------------

	public void visibleElement(boolean login, boolean chat) {

		pseudoLabel.setVisible(login);
		pseudoBarre.setVisible(login);
		pseudoBtn.setVisible(login);

		chatWindow.setVisible(chat);
		userList.setVisible(chat);
		scrollPane.setVisible(chat);
		inputBarre.setVisible(chat);
		btnRefresh.setVisible(chat);
		btnSend.setVisible(chat);
	}

	public void updateChatWindow(ArrayList<PostBean> liste) {
		StringBuilder allMessage = new StringBuilder("");

		for (int i = 0; i < liste.size(); i++) {
			PostBean message = liste.get(i);
			allMessage.append(message.getUser().getPseudo() + " : " + message.getContenu() + "\n");

		}
		chatWindow.setText(allMessage.toString());

	}

	public void updateUserList(ArrayList<UserBean> liste) {
		for (int i = 0; i < liste.size(); i++) {
			UserBean user = liste.get(i);
			userList.setText(user.getPseudo());
		}

	}

	public void clearInputBarre() {
		inputBarre.setText("");
	}

	/// -----------------------------------
	// Methode public
	// ------------------------------------
	public void setVisible(boolean visible) {
		frame.setVisible(visible);
	}

	/*******************
	 * Methode private
	 */

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(95, 158, 160));
		frame.setBounds(100, 100, 1092, 629);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		// -----------------------------
		// JTextField
		// -----------------------------

		inputBarre = new JTextField();
		inputBarre.setBounds(22, 528, 826, 35);
		frame.getContentPane().add(inputBarre);
		inputBarre.setColumns(10);

		pseudoBarre = new JTextField();
		pseudoBarre.setBounds(321, 18, 254, 20);
		frame.getContentPane().add(pseudoBarre);
		pseudoBarre.setColumns(10);

		userList = new JTextArea();
		userList.setEditable(false);
		userList.setBackground(new Color(255, 255, 255));
		userList.setBounds(885, 55, 162, 447);
		frame.getContentPane().add(userList);
		userList.setColumns(10);

		// -----------------------------
		// JButton
		// -----------------------------

		btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				controler.clickRefresh();
			}
		});
		btnRefresh.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
		btnRefresh.setBounds(885, 549, 162, 23);
		frame.getContentPane().add(btnRefresh);

		btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controler.clickSend(inputBarre.getText());
			}
		});
		btnSend.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
		btnSend.setBounds(885, 515, 162, 23);
		frame.getContentPane().add(btnSend);

		pseudoBtn = new JButton("Enter");
		pseudoBtn.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
		pseudoBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controler.clickPseudo(pseudoBarre.getText());
			}
		});
		pseudoBtn.setBounds(612, 16, 89, 23);
		frame.getContentPane().add(pseudoBtn);

		// -----------------------------
		// JLabel
		// -----------------------------

		pseudoLabel = new JLabel("Pseudo : ");
		pseudoLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
		pseudoLabel.setBounds(248, 10, 45, 35);
		frame.getContentPane().add(pseudoLabel);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(34, 57, 814, 447);
		frame.getContentPane().add(scrollPane);

		chatWindow = new JTextArea();
		chatWindow.setLineWrap(true);

		scrollPane.setViewportView(chatWindow);
		chatWindow.setColumns(10);

	}

	public void setControler(Controler controler) {
		this.controler = controler;
	}

	public void setErrorMessage(String errorMessage) {

	}
}
