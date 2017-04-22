import java.util.ArrayList;
import java.util.HashMap;

public class RoomList {
	private ArrayList<ChatRoom> allRooms;
	private HashMap<String, Integer> allTopics;
	private int nextOpenPort;
	
	public RoomList(int landingPort){
		this.allRooms = new ArrayList<ChatRoom>();
		this.allTopics = new HashMap<String, Integer>();
		this.nextOpenPort = landingPort;
	}
	public ArrayList<ChatRoom> getAllRooms(){
		return(allRooms);
	}
	
	public synchronized void addRoom(String topic){
		// Assumes a check has already been made for existence of room
		nextOpenPort = nextOpenPort + 1;		
		ChatRoom r = new ChatRoom(topic, nextOpenPort);
		r.start();
		System.out.println("[ROOMLIST]: CHATROOM STARTED ON " + nextOpenPort);
		allRooms.add(r);
		allTopics.put(r.getTopic(), nextOpenPort);
		return;
	}
	public synchronized int addRoom(String topic, String desc){
		// Assumes a check has already been made for existence of room
		nextOpenPort = nextOpenPort + 1;
		ChatRoom r = new ChatRoom(topic, desc, nextOpenPort);
		r.start();
		allRooms.add(r);
		allTopics.put(r.getTopic(), nextOpenPort);
		return nextOpenPort;
	}
	
	public void deleteRoom(ChatRoom r){
		allRooms.remove(r);
		allTopics.remove(r.getTopic());
	}
	
	public int getPort(String room){
		return(allTopics.get(room));
	}
	
	public synchronized boolean isTopic(String t){
		boolean avail = allTopics.keySet().contains(t);
		return avail;
	}

}
