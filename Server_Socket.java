import java.io.*;
import java.net.SocketException;
import java.util.*;
import java.net.ServerSocket;

/**
 * This class extending 
 * Thread is a Server socket that clients will connect to over port 5000. Once connected, it will create
 * and pair them with a server thread with and randomly generated between 0 and 999. This thread will
 * continue once started until it the thread (should be the initial) invokes it to end, which is expected
 * to happen once all of the clients end. The only output to console this class will produce is a message indicate
 * it is ending or an error message if something unexpectedly fails.
 * @author qs5834mm Samuel Andrews
 *
 */
public class Server_Socket extends Thread {

	Protected_LinkedList<Riddle_Obj> list; //what is used to compare riddles to answers
	
	ServerSocket serverSocket = null;
	
	boolean cleanExit = false; //indicates whether the socket will end via the End method or unexpectedly
 	
	
	/**
	 * Constructor of Server Socket, which only needs its list as an argument
	 * @param list, a Protected_LinkedList storing Riddle_Obj
	 */
	Server_Socket(Protected_LinkedList<Riddle_Obj> list) {
		
		this.list = list;
		
		try {
			serverSocket = new ServerSocket(5000); //establish itself at port 5000
		} catch (IOException e) {}
	}
	
	
	/**
	 * Method End will close the ServerSocket, causing the process to throw a SocketException which is
	 * then handled by the Server_Socket and ends the program. This method will also indicate to that
	 * catch statement that the exception is intentional due to this method via the boolean cleanExit
	 * @throws IOException
	 */
	 protected void End() throws IOException {
		 
		 System.out.println("Server Socket Closing");
		 cleanExit = true; //lets the catch block for SocketException that its end is intended
	     serverSocket.close();
	     return;

	    }
	
	
	 /**
	  * This run will establish the server socket's port at 5000 and then randomly generate a name for
	  * a potential server thread as it waits for a connection. Once that connection request comes in,
	  * it will pass of the client to that specific server thread and repeat the previous logic indefinitely
	  * until instructed to end by the thread that created it.
	  * 
	  */
	public void run() {
		
	
		String Socketname; //name of newly generated sockets
		
		LinkedList<String> SocketusedNames = new LinkedList<String>(); // linked list for socket name storage

		
		
		try {
			serverSocket = new ServerSocket(5000); //establish itself at port 5000
		} catch (IOException e) {

		}
		
		   
		   while (true) {
			   
			   if(serverSocket.isClosed() == true)
			   {
				   break;
			   }
	           	
				try {
					
					Socketname = Integer.toString((int)(Math.random()*1000)); //generate a random name 0 to 999
					
					
					
					while ( SocketusedNames.contains(Socketname)) //make sure the name hasn't been used yet.
					{
						Socketname = Integer.toString((int)(Math.random()*1000)); 
					}
					
					SocketusedNames.add(Socketname);
					
					
					ServerThread A = null;
					A = new ServerThread( Socketname, serverSocket.accept(),  list); //generates a randomly named ServerThread
					Thread Server = new Thread(A, Socketname);
					Server.start(); //invoke the run
					
					
				}catch(SocketException ex) //will be thrown intentionally when the End is invoked
				{
					if (cleanExit == false)
					{
						System.out.println("ERROR: Connection cut off as the result of an error."); //for when a SocketException is not the result of our End method
						ex.printStackTrace();
					} else
					{
						System.exit(1); //when the initial thread closes the socket via the End method, this thread will die.
					}
					
					
				}catch (IOException e) {
					e.printStackTrace();
				} 
		
				
				
				
				
				
				
	        }
		   
	
	}
	
	

}
