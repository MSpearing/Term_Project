import java.io.*;
import java.net.*;

public class Coordinator {
	static final int landingPort = 8080;
	static RoomList RL = new RoomList(landingPort);
	static ActiveUsersList AUL = new ActiveUsersList();
	public static void main(String args[]) throws InterruptedException{
		// All clients will be connected through this port initially
		RL.addRoom("Default");
		Thread doorway = new Thread(){
			public void run(){
				try{
					ServerSocket greeter = new ServerSocket(landingPort);
					Socket s;
					System.out.println("[COORDINATOR]: OPEN FOR BUSINESS ON PORT " + landingPort);
					while((s=greeter.accept()) != null){
						System.out.println("[COORDINATOR]: CLIENT CONNECTED");
						Thread t = new InitializationThread(s, RL, AUL);
						t.start();
						System.out.println("[COORDINATOR]: CLIENT PASSED OFF");
					}
					greeter.close();
					
				} catch(IOException e) {
					System.out.println("[COORDINATOR]: BURNING DOWN");
					System.err.println(e);
				}
			}
		};
		doorway.start();
		doorway.join();
		System.out.println("[COORDINATOR]: CLOSED FOR BUSINESS");
	}
}
