import java.util.ArrayList;
import java.util.HashSet;

public class RoomList {
	ArrayList<ChatRoom> allRooms;
	HashSet<String> allTopics;
	
	public RoomList(){
		this.allRooms = new ArrayList<ChatRoom>();
		this.allTopics = new HashSet<String>();
	}
	
	public void addRoom(ChatRoom r){
		allRooms.add(r);
		allTopics.add(r.getTopic());
	}
	public void deleteRoom(ChatRoom r){
		allRooms.remove(r);
		allTopics.remove(r.getTopic());
	}
	
	public synchronized boolean isTopicAvailable(String t){
		boolean avail = !allTopics.contains(t);
		return avail;
	}

}
