import java.io.*;
import java.util.*;
import java.net.*;

public class Server {
	//args = tcpPort
	public static void main (String[] args) throws InterruptedException {
		final int tcpPort;
		
//		tcpPort = Integer.parseInt(args[0]);
		tcpPort = 6789;
		
		//declare any global variables here
		final RoomList RL = new RoomList();
		final ActiveUsersList AUL = new ActiveUsersList();
		
		Thread tcp = new Thread(){
			public void run(){
				try{
					ServerSocket listener = new ServerSocket(tcpPort);
					Socket s;
					
					while ((s=listener.accept()) != null){
						Thread t = new TCPServerThread(s, RL, AUL);
						t.start();
					}
				}
				catch(IOException e){
					System.err.println(e);
				}
			}
		};
		tcp.start();
		tcp.join();
	}
}
