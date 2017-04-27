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
			System.out.println("[SALESREP]: VERIFYING CLIENT");
			String passCode = clientIn.nextLine();
			if(!passCode.equals("4rfcsql7tgbnwrty3u21")){
				System.out.println("[SALESREP]: BE GONE!");
				clientIn.close();
				theClient.close();
				return;
			}
			
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
			System.out.println("[SALESREP]: DONE WITH CLIENT");
			int tmpPort = allRooms.getPort(room);
			clientOut.println(tmpPort/*allRooms.getPort(room)*/);
			clientOut.flush();
			System.out.println("[SALESREP]: SENT CLIENT PORT NUMBER: " + tmpPort);
			
			
			clientIn.close();
			clientOut.close();
			theClient.close();
		}
		catch(IOException e){
			System.out.println("[INIT THREAD]: BAD ERROR");
			System.err.println(e);
		} catch(Exception e){
			System.out.println("[INIT THREAD]: CONNECTION LOST: CLIENT DISCONNECTED");
			return;
		}
	}
}
