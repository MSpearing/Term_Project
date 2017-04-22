
public class Message {
	
	private long time;
	private String author;
	private String content;
	
	public Message(){
		
	}
	
	public Message(String usr, String tim, String con){
		this.author = usr;
		this.time = Long.parseLong(tim);
		this.content = con;
	}
	
	public String getUser(){
		return author;
	}
	
	public String getContent(){
		return content;
	}
	
	public long getTime(){
		return time;
	}
	
	public void setUser(String usr){
		this.author = usr;
	}
	
	public void setTime(long tim){
		this.time = tim;
	}
	
	public void setContent(String con){
		this.content = con;
	}
}
