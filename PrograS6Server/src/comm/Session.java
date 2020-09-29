package comm;

import java.io.IOException;
import java.net.Socket;

import comm.Receptor.OnMessageListener;

public class Session {

	private String id;
	private Socket socket;
	private Receptor receptor;
	private Emisor emisor;
	private OnMessageListener listener;

	public Session(String id, Socket socket) {
		this.socket = socket;
		try {
			this.id = id;
			this.emisor = new Emisor(socket.getOutputStream());
			this.receptor = new Receptor(socket.getInputStream(), this);
			this.receptor.start();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

	public Session(Socket socket) {
		this.socket = socket;
		try {
			this.emisor = new Emisor(socket.getOutputStream());
			this.receptor = new Receptor(socket.getInputStream(), this);
			this.receptor.start();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

	public Emisor getEmisor() {
		return this.emisor;

	}

	public Receptor getReceptor() {
		return this.receptor;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void closeConnection() {

		try {
			this.socket.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}

	}
}
