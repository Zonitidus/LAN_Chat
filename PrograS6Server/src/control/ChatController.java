package control;

import comm.Receptor.OnMessageListener;
import comm.Session;
import javafx.application.Platform;
import model.Client;
import model.ConnectedClients;
import model.DirectMessage;
import model.Generic;
import model.Message;
import model.RepeatedClient;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

import com.google.gson.Gson;

import comm.TCPConnection;
import view.ChatWindow;
import comm.TCPConnection.OnConnectionListener;

public class ChatController implements OnMessageListener, OnConnectionListener {

	private ChatWindow view;
	private TCPConnection connection;

	public ChatController(ChatWindow view) {
		this.view = view;
		init();
	}

	public void init() {
		connection = TCPConnection.getInstance();
		connection.setPuerto(5000);
		connection.start();
		connection.setConnectionListener(this);
		connection.setMessageListener(this);
	}

	@Override
	public void onMessage(String message, Session session) {

		Gson gson = new Gson();
		Generic type = gson.fromJson(message, Generic.class);
		switch (type.getType()) {
		case "Message":
			connection.sendBroadCast(message);
			showHistory(message);
			break;
		case "Client":

			Client client = gson.fromJson(message, Client.class);
			session.setId(client.getName());

			boolean repeated = connection.isRepeated(client.getName());
			RepeatedClient rc = new RepeatedClient(repeated);
			String json1 = gson.toJson(rc);
			connection.confirmNewConnection(client.getName(), json1);

			if (repeated) {
				connection.removeFromWaitList(session);
				showHistory(session.getId()+" REJECTED");
			}
			else {
				connection.connectClient(session);
				showHistory(session.getId()+" ACCEPTED");
			}
			break;
		case "DirectMessage":
			DirectMessage dm = gson.fromJson(message, DirectMessage.class);
			String json = gson.toJson(dm);
			connection.sendDirectMessage(dm.getTo(), json);
			showHistory(json);
			break;
		case "RepeatedClient":

			ArrayList<String> connectedClients = new ArrayList<>();

			for (Session s : this.connection.getSessions()) {
				connectedClients.add(s.getId());
			}

			this.connection.sendBroadCast(gson.toJson(new ConnectedClients(connectedClients)));

			break;
		}

	}

	private void showHistory(String msg) {
		
		Platform.runLater(

				() -> {

					view.getMessageArea().appendText("->"+msg+"\n");
				}

		);
	}

	@Override
	public void onConnection(String id) {
		Platform.runLater(

				() -> {

					view.getMessageArea().appendText("<<< Nuevo cliente! --> " + id + ">>>\n");
				}

		);
	}

}
