import java.util.*;
import java.io.*;
import java.net.*;

public class Client {
	//args = hostAddress and tcpPort
	public static void main (String[] args){
		try{
			String hostAddress;
			int tcpPort;
			
			hostAddress = args[0];
			tcpPort= Integer.parseInt(args[1]);
			
			System.out.println("args: "+args[0] +" "+ args[1]);
			
			Scanner sc = new Scanner(System.in);
			System.out.println("scanner initiated");
			
			while(sc.hasNext()){
				System.out.println("inside while loop");
				
				String message = sc.nextLine();
				System.out.println("msg = "+message);
			
				System.out.println("[CLIENT] sending to server: " +message);
				String fromServer = tcpSend(message, hostAddress, tcpPort);
				System.out.println("[CLIENT] received from server: " + fromServer);
			}
		}
		catch(Exception e){
			System.err.println(e);
		}		
	}
	
	
	/// Parameters: (1) message to send (2) client's hostAddress (3) client's port
	/// Returns: message received from Server
	private static String tcpSend(String msg, String hostAddress, int tcpPort)
		throws IOException{
		Socket server = new Socket(hostAddress, tcpPort);
		PrintStream pout = new PrintStream(server.getOutputStream());
		Scanner serverIn = new Scanner(server.getInputStream());
		
		pout.println(msg);
		pout.flush();
		
		while(!serverIn.hasNext()){}
		
		String msgFromServer = serverIn.nextLine();
		while(serverIn.hasNextLine()){
			msgFromServer += "\n" + serverIn.nextLine();
		}
		
		serverIn.close();
		server.close();
		
		return msgFromServer;
		
	}
}
