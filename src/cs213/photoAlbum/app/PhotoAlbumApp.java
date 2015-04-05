package cs213.photoAlbum.app;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import cs213.photoAlbum.control.Client;
import cs213.photoAlbum.model.Backend;
import cs213.photoAlbum.view.LoginView;

public class PhotoAlbumApp {

	public static void main(String[] args) {
		
		Client client = new Client(new Backend());
		/*CmdView cmdView = new CmdView(client);
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter 'quit' to exit");
		while (true) {
			
			String input = sc.nextLine();
			cmdView.parseInput(input, sc);
		}*/
		
		LoginView loginView = new LoginView(client);
		
		loginView.setLocationRelativeTo(null);
		loginView.setVisible(true);
		loginView.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
	}
}
