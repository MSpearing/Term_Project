import java.net.*;
import java.io.*;
import java.util.*;

public class TCPServerThread extends Thread{
	//declare any global variables here
	Socket theClient;
	RoomList allRooms;
	ActiveUsersList allUsers;
	Message message;
	ArrayList<Message> allMessages;
	
	public TCPServerThread(Socket s, RoomList rl, ActiveUsersList aul){
		this.theClient = s;
		this.allRooms=rl;
		this.allUsers=aul;
	}
	public TCPServerThread(Socket s, Message newMessage, ArrayList<Message> allMsg){
		this.theClient = s;
		this.message = newMessage;
		this.allMessages = allMsg;
	}
	
	public void run(){
		Scanner sc = null;
		try{
			sc = new Scanner(theClient.getInputStream());
			PrintWriter pout = new PrintWriter(theClient.getOutputStream());
			long time = sc.nextLong();
			System.out.println("[TCPServer] TIME OF POST: "+time);
			message.setTime(time);
			sc.nextLine();
			String author = sc.nextLine();
			System.out.println("[TCPServer]: AUTHOR OF POST: "+author);
			message.setUser(author);
			if(author.equals("GETALL")){
				System.out.println("[TCPServer]: NEW POSTS TO SEND: " + (allMessages.size() - (int)message.getTime()));
				pout.println(allMessages.size() - (int)message.getTime());
				pout.flush();
				for(int i = (int)message.getTime(); i < allMessages.size(); i++){
					pout.println(allMessages.get(i).getTime());
					pout.println(allMessages.get(i).getUser());
					pout.println(allMessages.get(i).getContent());
					pout.flush();
				}
				return;
			}
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
