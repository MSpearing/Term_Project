
public class User {
	int portNum;
	String name;
	
	public User(String username, int num){
		this.name=username;
		this.portNum = num;
	}
	
	public String getName(){
		return name;
	}
	
	public int getPort(){
		return portNum;
	}
	
	public void setName(String newName){
		this.name = newName;
	}
}
