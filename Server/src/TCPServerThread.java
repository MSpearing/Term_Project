import java.net.*;
import java.io.*;
import java.util.*;

public class TCPServerThread extends Thread{
	//declare any global variables here
	Socket theClient;
	RoomList allRooms;
	ActiveUsersList allUsers;
	
	
	public TCPServerThread(Socket s, RoomList rl, ActiveUsersList aul){
		this.theClient = s;
		this.allRooms=rl;
		this.allUsers=aul;
	}
	public TCPServerThread(Socket s){
		this.theClient = s;
	}
	
	public void run(){
		Scanner sc = null;
		try{
			sc = new Scanner(theClient.getInputStream());
			PrintWriter pout = new PrintWriter(theClient.getOutputStream());
			
			String msg = sc.nextLine(); // msg from Client

			System.out.println("[TCPServer] received from Client: "+msg);
			
			pout.println("[TCPServer] I can hear you, your port is: "+ theClient.getPort() 
			+"/n my local port is: "+ theClient.getLocalPort()
			+"/n your inetaddress is: "+ theClient.getInetAddress());
			pout.flush();
			theClient.close();
			//scanner.close(); --> if use a scanner on the msg
		}
		catch(IOException e){
			System.err.println(e);
		}
		finally{
			sc.close();
		}
	}

}
