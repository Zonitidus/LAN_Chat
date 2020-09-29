package control;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

import com.google.gson.Gson;

import comm.Receptor.OnMessageListener;
import javafx.application.Platform;
import javafx.scene.control.Button;
import model.Client;
import model.ConnectedClients;
import model.DirectMessage;
import model.Generic;
import model.Message;
import model.RepeatedClient;
import comm.TCPConnection;
import view.ChatWindow;

public class ChatController implements OnMessageListener {

	private ChatWindow view;
	private TCPConnection connection;
	private String name;
	private ArrayList<String> clients;
	private String selectedClient;

	private static String TODOS = "Todos";

	public ChatController(ChatWindow view) {
		this.view = view;
		this.name = this.view.getName();
		this.clients = new ArrayList<String>();
		init();
	}

	public void init() {

		Gson gson = new Gson();

		this.connection = TCPConnection.getInstance();
		this.connection.setListerOnMessage(this);
		this.connection.getEmisor().setMessage(gson.toJson(new RepeatedClient(false)));

		this.view.setOnCloseRequest((e) -> {

			this.connection.closeConnection();

		});

		this.view.getSendBtn().setOnAction((e) -> {
			String name = this.name;
			String msg = this.view.getMessageTF().getText();
		});

		view.getSendBtn().setOnAction(

				(e) -> {
					
					if (this.selectedClient.equals(this.TODOS)) {

						Message msg = new Message(this.name, this.view.getMessageTF().getText());
						this.connection.getEmisor().setMessage(gson.toJson(msg));

						view.getMessageTF().setText("");
					} else {
						
						DirectMessage dm = new DirectMessage(this.name, this.view.getMessageTF().getText(), this.selectedClient);
						this.connection.getEmisor().setMessage(gson.toJson(dm));
						
						view.getMessageTF().setText("");
					}
				}

		);
	}

	@Override
	public void onMessage(String message) {

		Gson gson = new Gson();
		Generic type = gson.fromJson(message, Generic.class);

		switch (type.getType()) {
		case "ConnectedClients":

			ConnectedClients cc = gson.fromJson(message, ConnectedClients.class);

			Platform.runLater(() -> {

				ArrayList<String> clients = cc.getClients();
				this.view.getClients().getChildren().clear();

				this.clients = cc.getClients();
				
				Button todos = new Button(this.TODOS);
				todos.setPrefSize(500, 40);
				todos.setOnAction((e) -> {

					this.selectedClient = todos.getText();

				});
				
				this.view.getClients().getChildren().add(todos);
				
				Button yo = new Button(this.name+" (yo)");
				yo.setPrefSize(500, 40);
				yo.setDisable(true);
				
				this.view.getClients().getChildren().add(yo);
				
				for (int i = 0; i < this.clients.size(); i++) {
					
					if(!this.clients.get(i).equals(this.name)) {
						System.out.println();
						Button temp = new Button(this.clients.get(i));
						temp.setPrefSize(500, 40);
						temp.setOnAction((e) -> {

							this.selectedClient = temp.getText();

						});

						this.view.getClients().getChildren().add(temp);
					}
				}

			});

			break;

		case "Message":

			Message msg = gson.fromJson(message, Message.class);
			if (msg.getId().equalsIgnoreCase(this.view.getName())) {
				Platform.runLater(

						() -> {

							view.getMessageArea().appendText(msg.getId() + " (Yo) : " + msg.getBody() + "\n");
						}

				);
			} else {
				Platform.runLater(

						() -> {

							view.getMessageArea().appendText(msg.getId() + " : " + msg.getBody() + "\n");
						}

				);
			}
			break;

		case "DirectMessage":

			DirectMessage dm = gson.fromJson(message, DirectMessage.class);

			Platform.runLater(

					() -> {
						view.getMessageArea().appendText("(Privado) " + dm.getFrom() + " : " + dm.getBody() + "\n");
					}

			);

			break;
		}

	}

}
