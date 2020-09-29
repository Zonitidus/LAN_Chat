package comm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import comm.TCPConnection.OnConnectionListener;

public class Receptor extends Thread {

	private InputStream is;

	public OnMessageListener listener;
	public OnConnectionListener connectionListener;

	public Receptor(InputStream is) {
		this.is = is;
	}

	@Override
	public void run() {

		BufferedReader breader = new BufferedReader(new InputStreamReader(this.is));

		while (true) {

			try {
				String msg = breader.readLine();

				if (this.listener != null)
					listener.onMessage(msg);
				else if (this.connectionListener != null)
					this.connectionListener.onConnection(msg);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	public void setList(OnMessageListener listener) {
		this.listener = listener;
	}

	public interface OnMessageListener {

		public void onMessage(String message);
	}

	public void setConnectionListener(OnConnectionListener connectionListener) {
		this.connectionListener = connectionListener;
	}

}
