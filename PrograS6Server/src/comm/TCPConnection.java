package comm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import com.google.gson.Gson;

import comm.Receptor.OnMessageListener;
import model.ConnectedClients;
import model.Message;

public class TCPConnection extends Thread {

	private static TCPConnection instance = null;

	// GLOBAL
	private int puerto;
	private ServerSocket server;
	private OnConnectionListener connectionListener;
	private OnMessageListener messageListener;
	private ArrayList<Session> sessions;
	private ArrayList<Session> waitList;

	private TCPConnection() {

		sessions = new ArrayList<Session>();
		waitList = new ArrayList<Session>();
	}

	public static synchronized TCPConnection getInstance() {

		if (instance == null) {
			instance = new TCPConnection();
		}
		return instance;
	}

	@Override
	public void run() {

		try {

			server = new ServerSocket(puerto);

			while (true) {
				System.out.println("Esperando cliente...");
				Socket socket = server.accept();
				System.out.println("Nuevo cliente Conectado.");

				Session session = new Session(socket);
				waitList.add(session);
				setAllSessionsListener(messageListener);
			}

		} catch (IOException e) {

		}

	}

	public void setConnectionListener(OnConnectionListener connectionListener) {
		this.connectionListener = connectionListener;
	}

	public void setAllSessionsListener(OnMessageListener listener) {
		for (int i = 0; i < this.waitList.size(); i++) {
			Session s = this.waitList.get(i);
			s.getReceptor().setList(listener);
		}
	}

	public void setPuerto(int puerto) {

		this.puerto = puerto;

	}

	public interface OnConnectionListener {
		public void onConnection(String id);
	}

	public OnMessageListener getMessageListener() {
		return messageListener;
	}

	public void setMessageListener(OnMessageListener messageListener) {
		this.messageListener = messageListener;
	}

	public void sendBroadCast(String msg) {

		for (int i = 0; i < sessions.size(); i++) {
			Session s = sessions.get(i);
			s.getEmisor().setMessage(msg);
		}

	}

	public ArrayList<Session> getSessions() {
		return this.sessions;
	}

	public void sendDirectMessage(String id, String json) {

		for (int i = 0; i < sessions.size(); i++) {

			if (sessions.get(i).getId().equalsIgnoreCase(id)) {
				sessions.get(i).getEmisor().setMessage(json);
				break;
			}
		}
	}

	public boolean isRepeated(String clientId) {

		for (Session s : this.sessions) {

			if (s.getId().equalsIgnoreCase(clientId))
				return true;

		}
		return false;
	}

	public void connectClient(Session session) {

		removeFromWaitList(session);
		this.sessions.add(session);
		this.connectionListener.onConnection(session.getId());
	}

	public void removeFromWaitList(Session session) {
		this.waitList.remove(getSessionIndex(session, this.waitList));
	}

	public void disconnect(Session session) {

		Gson gson = new Gson();
		String dis = gson.toJson((new Message(session.getId(), "<< -- disconected -- >>")));
		sendBroadCast(dis);
		this.sessions.remove(getSessionIndex(session, this.sessions));

		ArrayList<String> connectedClients = new ArrayList<String>();

		for (Session s : this.sessions) {
			connectedClients.add(s.getId());
		}

		String clientsToShow = gson.toJson(new ConnectedClients(connectedClients));
		sendBroadCast(clientsToShow);

	}

	private int getSessionIndex(Session session, ArrayList<Session> list) {

		int index = -1;

		for (int i = 0; i < list.size(); i++) {

			if (list.get(i).getId().equals(session.getId())) {

				index = i;
				break;
			}

		}

		return index;
	}

	public void confirmNewConnection(String clientId, String repeated) {

		for (int i = 0; i < this.waitList.size(); i++) {

			if (this.waitList.get(i).getId().equals(clientId)) {

				this.waitList.get(i).getEmisor().setMessage(repeated);
				break;
			}
		}
	}

}
