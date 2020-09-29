package view;

import control.ConnectionController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ConnectionWindow extends Stage{
	
	private Scene scene;
	private Button btnConnect;
	private TextField name;
	
	private ConnectionController control;
	
	public ConnectionWindow() {
		
		this.btnConnect = new Button("Conectar");
		this.name = new TextField();
		
		VBox vbox = new VBox();
		vbox.getChildren().add(name);
		vbox.getChildren().add(btnConnect);
		scene = new Scene(vbox,400,400);
		this.setScene(scene);
		control = new ConnectionController(this);
	}

	public Button getBtnConnect() {
		return btnConnect;
	}

	public void setBtnConnect(Button btnConnect) {
		this.btnConnect = btnConnect;
	}
	
	public TextField getName() {
		return this.name;
	}
	

}
