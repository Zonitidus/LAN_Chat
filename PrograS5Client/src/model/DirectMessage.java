package model;

public class DirectMessage {

	public String type = "DirectMessage";
	private String from;
	private String body;
	private String to;

	public DirectMessage(String from, String body, String to) {

		this.from = from;
		this.body = body;
		this.to = to;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

}
