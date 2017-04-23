import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatRoom extends Thread {
	private ArrayList<User> members;
	private String topic;
	private String description;
	private ArrayList<Message> content;
	private int port;

	public ChatRoom(String top, int port){
		this.content = new ArrayList<Message>();
		this.topic=top;
		this.description=new String("");
		this.members = new ArrayList<User>();	
		this.port = port;
		System.out.println("[CHATROOM " + topic + "]: ACTIVATING ON PORT " + port);
	}
	public ChatRoom(String top, String about, int port){
		this.content = new ArrayList<Message>();
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
			Message newMessage = new Message();
			while((s=listener.accept()) != null){
				System.out.println("[CHATROOM " + topic + "]: CLIENT RECIEVED");
				Thread t = new TCPServerThread(s, newMessage, content);
				t.start();
				t.join();
				if(!newMessage.getUser().equals("GetALL")){
					content.add(new Message(newMessage.getUser(), newMessage.getTime() + "", newMessage.getContent()));
				}
			}
			System.out.println("[CHATROOM " + topic + "]: NO MORE CLIENTS....");
			listener.close();
		} catch(IOException e){
			System.err.println(e);
		} catch (InterruptedException e) {
			e.printStackTrace();
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