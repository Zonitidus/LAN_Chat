package model;

public class Client {

	private String type = "Client";
	private String name;

	public Client() {

	}
	
	public Client(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}
}
