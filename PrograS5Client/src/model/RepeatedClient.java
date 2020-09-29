package model;

public class RepeatedClient {

	public String type = "RepeatedClient";
	private boolean repeated;

	public RepeatedClient() {

	}

	public RepeatedClient(boolean repeated) {

		this.repeated = repeated;
	}

	public String getType() {
		return type;
	}

	public boolean isRepeated() {
		return repeated;
	}

}
