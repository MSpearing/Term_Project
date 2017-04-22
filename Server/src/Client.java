import java.util.*;
import java.io.*;
import java.net.*;

public class Client {
	//args = hostAddress and tcpPort
	public static void main (String[] args){
		try{
			String hostAddress;
			int tcpPort;

			hostAddress = "localhost";//args[0];
			tcpPort= 8080; //Integer.parseInt(args[1]);
			System.out.println("[CLIENT] DIRECTIONS RECIEVED: "+args[0] +":"+ args[1]);
			Scanner sc = new Scanner(System.in);
			//System.out.println("scanner initiated");

			// Connect the client with the coordinator
			Socket greeter = new Socket(hostAddress, tcpPort);
			PrintStream greeterOut = new PrintStream(greeter.getOutputStream());
			Scanner greeterIn = new Scanner(greeter.getInputStream());

			// Subscribe the client to a chat room
			// Get the room, one per line
			String room;
			System.out.println("[CLIENT]: READING BROCHURE");

			int numRooms = Integer.parseInt(greeterIn.nextLine());
			for(int i = 0; i < numRooms; i++){
				room = greeterIn.nextLine();
				System.out.println(room);
			}
			//while(!sc.hasNext()){}
			String input = sc.nextLine();
			System.out.println(input);
			greeterOut.println(input);
			greeterOut.flush();
			System.out.println("[CLIENT]: I'VE MADE MY DECISION!");
			// Client can stay as long as they want
			int roomPort = greeterIn.nextInt();
			System.out.println("[CLIENT]: PORT RECIEVED: " + roomPort);
			greeter.close();
			greeterOut.close();
			greeterIn.close();
			System.out.println("[CLIENT]: SALESREP RELEASED");
			//Socket server = new Socket(hostAddress, roomPort);
			//PrintStream serverOut = new PrintStream(server.getOutputStream());
			//Scanner serverIn = new Scanner(server.getInputStream());
			System.out.println("[CLIENT]: WAITING FOR MESSAGE");
			while(sc.hasNext()){
				//System.out.println("inside while loop");
				String message = sc.nextLine();
				//System.out.println("msg = "+message);
				// TODO This works up to here
				System.out.println("[CLIENT] POSTING: " +message);
				tcpSend(message, hostAddress, roomPort);
				//System.out.println("[CLIENT] RECIEVED NEW: " + fromServer);
			}
			sc.close();
			//serverOut.close();
			//serverIn.close();
			//server.close();
		}
		catch(Exception e){
			System.err.println(e);
		}		
	}

	/// Parameters: (1) message to send (2) client's hostAddress (3) client's port
	/// Returns: message received from Server
	private static void tcpSend(String msg, String hostAddress, int tcpPort)
			throws IOException{
		Socket server = new Socket(hostAddress, tcpPort);
		PrintStream pout = new PrintStream(server.getOutputStream());
		Scanner serverIn = new Scanner(server.getInputStream());
		System.out.println("[CLIENT]: CONNECTED TO ROOM");
		pout.println("Author 1");
		pout.println(System.currentTimeMillis());
		pout.println(msg);
		pout.flush();
		serverIn.close();
		pout.close();
		server.close();

	}


}
