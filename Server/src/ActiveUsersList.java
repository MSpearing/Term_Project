import java.util.ArrayList;
import java.util.HashSet;

public class ActiveUsersList {
	ArrayList<User> allUsers;
	HashSet<String> userNames;
	
	public ActiveUsersList(){
		this.allUsers = new ArrayList<User>();
		this.userNames = new HashSet<String>();
	}
	
	public void signIn(User newU){
		allUsers.add(newU);
		userNames.add(newU.getName());
	}
	
	public void signOut(User bye){
		allUsers.remove(bye);
		userNames.remove(bye.getName());
	}
	
	public synchronized boolean isNameAvailable(String name){
		boolean avail = !userNames.contains(name);
		return avail;
	}
}
