import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatRoom extends Thread {
	ArrayList<User> members;
	String topic;
	String description;
	int port;

	public ChatRoom(String top, int port){
		this.topic=top;
		this.description=new String("");
		this.members = new ArrayList<User>();	
		this.port = port;
		System.out.println("[CHATROOM " + topic + "]: ACTIVATING ON PORT " + port);
	}
	public ChatRoom(String top, String about, int port){
		this.topic=top;
		this.description=about;
		this.members = new ArrayList<User>();		
		this.port = port;
		System.out.println("[CHATROOM " + topic + "]: ACTIVATING ON PORT " + port);
	}

	public void run(){
		try{
			ServerSocket listener = new ServerSocket(port);
			System.out.println("[CHATROOM " + topic + "]: INITIALIZED ON PORT " + port);

			Socket s;
			while((s=listener.accept()) != null){
				System.out.println("[CHATROOM " + topic + "]: CLIENT RECIEVED");
				Thread t = new TCPServerThread(s);
				t.start();
			}
			listener.close();
		} catch(IOException e){
			System.err.println(e);
		}
	}
	public void join(User newU){
		members.add(newU);
	}

	public void leave(User bye){
		members.remove(bye);
	}

	public String getTopic(){
		return topic;
	}

}