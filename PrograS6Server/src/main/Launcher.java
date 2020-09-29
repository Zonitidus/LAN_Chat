package main;

import com.sun.media.jfxmedia.locator.ConnectionHolder;

import javafx.application.Application;
import javafx.stage.Stage;
import view.ChatWindow;

public class Launcher extends Application {

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage arg0) throws Exception {
		ChatWindow connectionWindow = new ChatWindow();
		connectionWindow.show();
		
	}
	
}
