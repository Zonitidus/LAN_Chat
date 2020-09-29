package comm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class Receptor extends Thread {

	private InputStream is;
	public OnMessageListener listener;
	private Session session;
	
	public Receptor(InputStream is, Session session){
		this.is = is;
		this.session = session;
	}
	
	@Override
	public void run() {
		
		BufferedReader breader = new BufferedReader(new InputStreamReader(this.is));
		
		while(true) {
			
			try {
				String msg = breader.readLine();
				if(msg != null) {
					if(this.listener != null) {
						System.out.println("Recibido" +msg);
						listener.onMessage(msg, session);
					}
				}
				else {
					TCPConnection.getInstance().disconnect(session);
					session.closeConnection();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	public void setList(OnMessageListener listener) {
		this.listener = listener;
	}
	
	
	public interface OnMessageListener{

		public void onMessage(String message, Session session);
	}

	
	
}
