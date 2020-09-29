package control;

import com.google.gson.Gson;

import comm.TCPConnection;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.Client;
import model.RepeatedClient;
import view.ChatWindow;
import view.ConnectionWindow;

public class ConnectionController implements TCPConnection.OnConnectionListener {
	
	private ConnectionWindow view;
	private TCPConnection connection;
	
	public ConnectionController(ConnectionWindow view) {
		this.view = view;
		init();
	}
	
	public void init() {
		connection = TCPConnection.getInstance();
		connection.setConnectionListener(this);
		
		view.getBtnConnect().setOnAction(
				
				(e) ->{
					connection.setIp("127.0.1.1");
					connection.setPuerto(5000);
					connection.start();
				}
				
				);
	}

	@Override
	public void onConnection() {
		
		Gson gson = new Gson();
		Client client = new Client(view.getName().getText());
		String json = gson.toJson(client);
		connection.getEmisor().setMessage(json);
	}

	@Override
	public void onConnection(String clientId) {

		Gson gson = new Gson();
		RepeatedClient repe = gson.fromJson(clientId, RepeatedClient.class);
		if(repe.isRepeated()) {
			connection.closeConnection();
			
			Platform.runLater(
					
					()->{
						Alert a = new Alert(AlertType.ERROR);
						a.setContentText("Repeated client name.");
						a.show();
					}
					);
			TCPConnection.restartTCP();
		}
		else {
			
			Platform.runLater(
					
					()->{
						ChatWindow window = new ChatWindow(this.view.getName().getText());
						window.show();
						view.close();
					}
					);
		}
		
		
		
	}

	@Override
	public void confirmConnection(boolean repeated) {
		
		Gson gson = new Gson();
		RepeatedClient rep = new RepeatedClient(repeated);
		
		String json = gson.toJson(rep);
		
		connection.getEmisor().setMessage(json);
	}
	

}
