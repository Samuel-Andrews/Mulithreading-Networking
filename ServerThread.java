import java.io.*;
import java.net.Socket;


/**
 * This class implementing Runnable is meant to answer riddles provided by potential client threads
 * by searching through the riddles within its linkedlist of Riddle_obj until it finds said riddle, 
 * and responds with an answer. It will continue to wait for incoming riddles and searching and sending
 * back answers until it picks up the message "END" from the client, at which point it will die.
 * @author qs5834mm
 *
 */
public class ServerThread implements Runnable {

	//data set by arguments
	String name;
	Socket socket;
	Protected_LinkedList<Riddle_Obj> list;
	
	//logic data
	String riddle;
	int listindex;
	int pos;
	boolean found = false;
	
	/**
	 * Constructor of ServerThreads take a variety of arguments in order to ready itself to answer incoming riddles
	 * from potential clients
	 * @param name	A string that IDs the object for identification purposes
	 * @param socket  where it will communicate with the client over, usually serverSocket.accept()
	 * @param list	A Protected_LinkedList containing Riddle Objects, used to answer riddles sent from clients
	 */
	ServerThread(String name, Socket socket, Protected_LinkedList<Riddle_Obj> list){
		this.name = name;
		this.socket = socket;
		this.list = list;
			
	}
	
	
	
	/**
	 * This server will read in strings of Riddles from a client, search for an answer using its Protected_LinkedList
	 * as a reference, and returning the answer along with other identifying information. It will continue this task
	 * Indefinitely until it receives the message "END" from client thread it is connected to, at which point it will
	 * shut itself down.
	 */
	public void run() {

			InputStream inStream = null;
			OutputStream outStream = null;
			
			//set the streams
			
			try {
				inStream = socket.getInputStream();
			
				outStream = socket.getOutputStream();
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			//builds its own readers and writers
			BufferedReader in = new BufferedReader(new InputStreamReader(inStream));
			PrintWriter  out = new PrintWriter(outStream);
			
		while (true) {

			
			try {
				riddle = in.readLine();

				

				if (riddle.equals("END") == true) // end ServerThread when client is done
				{	
					break;
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

			while (true) {

				if(pos >= list.getLinkedList().size())
				{
					pos = 0;
				}
				
				// System.out.println(list.getLinkedList().get(0).getRiddle()); //checks and
				// makes sure the list is changing

				// checks the riddles against the riddles in the list
				if (list.getLinkedList().get(pos).getRiddle().equals(riddle) == true) {
					found = true;
					listindex = pos;
					break;
				}
				pos++;
			}

			if (found = true) {
				out.print(name + " responds: " + list.getLinkedList().get(listindex).getAnswer() + "\n"); //a complicated path that will get you the riddle
				out.flush();
			} else {
				out.print("FAILURE: Riddle not in list");
				out.flush();
			}

		}

		System.out.println(name + " has finished its tasks and is now terminating."); //proceed to shut down

		
		try {
			in.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		

	}

}
