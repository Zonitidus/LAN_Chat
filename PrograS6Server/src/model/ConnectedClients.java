package model;

import java.util.ArrayList;

public class ConnectedClients {

	private String type = "ConnectedClients";
	private ArrayList<String> clients;

	public ConnectedClients() {
	}

	public ConnectedClients(ArrayList<String> clients) {
		this.clients = clients;
	}

	public ArrayList<String> getClients() {
		return clients;
	}

	public void setClients(ArrayList<String> clients) {
		this.clients = clients;
	}

	public String getType() {
		return type;
	}

}
