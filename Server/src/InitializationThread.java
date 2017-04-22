import java.net.*;
import java.io.*;
import java.util.*;

public class InitializationThread extends Thread{
	Socket theClient;
	RoomList allRooms;
	ActiveUsersList allUsers;

	public InitializationThread(Socket s, RoomList rl, ActiveUsersList aul){
		this.theClient = s;
		this.allRooms = rl;
		this.allUsers = aul;
	}

	public void run(){
		try{
			// Connect the salesrep to the client
			System.out.println("[SALESREP]: MAKING CONTACT");
			Scanner clientIn = new Scanner(theClient.getInputStream());
			PrintWriter clientOut = new PrintWriter(theClient.getOutputStream());
			System.out.println("[SALESREP]: CONTACT MADE TO CLIENT");
			
			// Send all the rooms to the client
			System.out.println("[SALESREP]: GIVING CLIENT BROCHURE");
			clientOut.println(allRooms.getAllRooms().size());
			clientOut.flush();
			for(ChatRoom room : allRooms.getAllRooms()){
				clientOut.println(room.getTopic());
				clientOut.flush();
			}

			// Send all the users to the client

			// Wait for the client to tell you where they want to go
			System.out.println("[SALESREP]: WAITING FOR CLIENT TO DECIDE");
			String room = clientIn.nextLine();
			System.out.println("[SALESREP]: CLIENT PICKED ROOM " + room);
			
			// Make a new room if they desire (Spawn a new server on new port)
			if(!allRooms.isTopic(room)){
				System.out.println("[SALESREP]: CLIENT WANTS TO OPEN A NEW ROOM");
				allRooms.addRoom(room);
			}
			
			// SEND THE CLIENT THE PORT TO CONNECT TO
			System.out.println("[SALESREP]: SENDING CLIENT TO ROOM");
			clientOut.println(allRooms.getPort(room));
			clientOut.flush();
			
			clientIn.close();
			clientOut.close();
			theClient.close();
		}
		catch(IOException e){
			System.err.println(e);
		}
	}
}
