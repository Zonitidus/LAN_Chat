package view;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

import control.ChatController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ChatWindow extends Stage {

	private Scene scene;
	private TextField messageTF;
	private Button sendBtn;
	private TextField clientID;
	private Button sendDirectionBtn;
	private TextArea messageArea;
	private ChatController control;
	private String name;
	private String to;

	private VBox clients;

	public ChatWindow(String clientId) {
		
		this.name = clientId;

		this.clients = new VBox();
		this.messageTF = new TextField();
		this.sendBtn = new Button("Enviar");
		this.messageArea = new TextArea();
		this.clientID = new TextField();
		this.sendDirectionBtn = new Button("Enviar directo");

		VBox vbox = new VBox();
		vbox.getChildren().add(messageTF);
		vbox.getChildren().add(sendBtn);
		vbox.getChildren().add(clientID);
		vbox.getChildren().add(sendDirectionBtn);
		vbox.getChildren().add(messageArea);

		HBox container = new HBox();
		container.getChildren().add(this.clients);
		container.getChildren().add(vbox);

		scene = new Scene(container, 400, 400);
		this.setScene(scene);

		control = new ChatController(this);
	}

	public TextField getMessageTF() {
		return messageTF;
	}

	public void setMessageTF(TextField messageTF) {
		this.messageTF = messageTF;
	}

	public Button getSendBtn() {
		return sendBtn;
	}

	public void setSendBtn(Button sendBtn) {
		this.sendBtn = sendBtn;
	}

	public TextArea getMessageArea() {
		return messageArea;
	}

	public void setMessageArea(TextArea messageArea) {
		this.messageArea = messageArea;
	}

	public TextField getClientID() {
		return clientID;
	}

	public void setClientID(TextField clientID) {
		this.clientID = clientID;
	}

	public Button getSendDirectionBtn() {
		return sendDirectionBtn;
	}

	public void setSendDirectionBtn(Button sendDirectionBtn) {
		this.sendDirectionBtn = sendDirectionBtn;
	}

	public ChatController getControl() {
		return control;
	}

	public void setControl(ChatController control) {
		this.control = control;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public VBox getClients() {
		return clients;
	}

	public void setClients(VBox clients) {
		this.clients = clients;
	}

}
