package cs213.photoAlbum.app;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import cs213.photoAlbum.control.Client;
import cs213.photoAlbum.model.Backend;
import cs213.photoAlbum.view.LoginView;

public class PhotoAlbumApp {

	public static void main(String[] args) {

		Client c = new Client(new Backend());
		
		ShutdownHook sdh = new ShutdownHook(c);
		sdh.attachShutDownHook();
		
		LoginView loginView = new LoginView(c);
		loginView.setLocationRelativeTo(null);
		loginView.setVisible(true);
		
		loginView.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
}


class ShutdownHook {
	
	private Client client;

	public ShutdownHook(Client c) { this.client = c; }
	
	public void attachShutDownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				client.writeUsers();
			}
		});
	}
}
