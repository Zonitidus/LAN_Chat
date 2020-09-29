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
import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;
import java.util.Scanner;


import comm.Receptor.OnMessageListener;
import comm.TCPConnection.OnConnectionListener;

public class TCPConnection extends Thread {

	private static TCPConnection instance;
	private Socket socket;
	private String ip = "127.0.1.1";
	private int puerto = 5000;
	private Receptor receptor;
	private Emisor emisor;
	private OnMessageListener listener;
	private OnConnectionListener connectionListener;
	
	private  TCPConnection() {
		
	}
	
	public static synchronized TCPConnection getInstance() {
		
		if(instance == null) {
			instance = new TCPConnection();
		}
		return instance;
	}
	
	public static synchronized void restartTCP() {
		instance = new TCPConnection();
	}
	
	@Override
	public void run() {

		try {
		
			System.out.println("Conectando al servidor...");
			socket = new Socket(this.ip,this.puerto);
			System.out.println("Conectado.");
			
			this.receptor = new Receptor(this.socket.getInputStream());
			this.receptor.setConnectionListener(this.connectionListener);
			this.receptor.start();
			
			this.emisor = new Emisor(this.socket.getOutputStream());
			this.connectionListener.onConnection();
				
		}catch(IOException e) {
			
		}
			
	}
	
	public void closeConnection() {
		try {
			this.socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setConnectionListener(OnConnectionListener connectionListener) {
		this.connectionListener = connectionListener;
	}

	public void setListerOnMessage(OnMessageListener listener) {
		this.receptor.setList(listener);
	}

	public void setPuerto(int puerto) {
	
		this.puerto = puerto;
		
	}

	public Emisor getEmisor() {
		return this.emisor;
		
	}

	public void setIp(String ip) {
		this.ip = ip;
		
	}
	
	public interface OnConnectionListener{
		public void onConnection();
		public void onConnection(String msg);
		public void confirmConnection(boolean repeated);
	}
	

}
