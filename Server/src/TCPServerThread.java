import java.net.*;
import java.io.*;
import java.util.*;

public class TCPServerThread extends Thread{
	//declare any global variables here
	Socket theClient;
	RoomList allRooms;
	ActiveUsersList allUsers;
	Message message;
	
	
	public TCPServerThread(Socket s, RoomList rl, ActiveUsersList aul){
		this.theClient = s;
		this.allRooms=rl;
		this.allUsers=aul;
	}
	public TCPServerThread(Socket s, Message newMessage){
		this.theClient = s;
		this.message = newMessage;
	}
	
	public void run(){
		Scanner sc = null;
		try{
			sc = new Scanner(theClient.getInputStream());
			PrintWriter pout = new PrintWriter(theClient.getOutputStream());
			String author = sc.nextLine();
			System.out.println("[TCPServer]: AUTHOR OF POST: "+author);
			message.setUser(author);
			long time = sc.nextLong();
			System.out.println("[TCPServer] TIME OF POST: "+time);
			message.setTime(time);
			sc.nextLine();
			String msg = sc.nextLine();
			System.out.println("[TCPServer] POST CONTENT: "+msg);
			message.setContent(msg);
			pout.flush();
			pout.close();
			sc.close();
			theClient.close();
			//scanner.close(); --> if use a scanner on the msg
		}
		catch(IOException e){
			System.err.println(e);
		}
	}

}
