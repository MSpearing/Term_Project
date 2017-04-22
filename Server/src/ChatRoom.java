import java.util.ArrayList;

public class ChatRoom {
	ArrayList<User> members;
	String topic;
	String description;
	
	public ChatRoom(String top){
		this.topic=top;
		this.description=new String("");
		this.members = new ArrayList<User>();	
	}
	public ChatRoom(String top, String about){
		this.topic=top;
		this.description=about;
		this.members = new ArrayList<User>();		
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
