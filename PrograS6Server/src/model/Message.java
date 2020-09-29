package model;

public class Message {

	public String type = "Message";
	private String id;
	private String body;
	private String date;
	
	public Message() {
		
	}
	
	public Message(String id,String body,String date) {
		this.id = id;
		this.body = body;
		this.date = date;
	}
	
	public Message(String id,String body) {
		this.id = id;
		this.body = body;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	
	
	
}
