
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * This class implementing Runnable outlines a Thread meant to act as a client that will communicate over a network.
 * Once connected, it will go through a provided list of riddles that it will ask the server, printing both the riddle
 * and the correct response to it when it receives it. It will continue this pattern for all riddles in its list, sending
 * out one final "END" message to signal its is finished when completed. The thread will then finish shortly thereafter.
 * It takes a 1 second break after sending each riddle.
 * @author qs5834mm Samuel Andrews
 *
 */
public class ClientThread implements Runnable {

	//data
	String name;
	String[] Riddles;
	
	/**
	 * Constructor of ClientThread allows the object to be identified and provides it with a list of 
	 * Riddles to ask.
	 * @param name	A string used to identify the thread
	 * @param Riddles	A string array meant to contain a list of Riddles to ask.
	 */
	ClientThread(String name, String[] Riddles) 
	{
		this.name = name;
		this.Riddles = Riddles;
	}
	
	
	
	
	/**
	 * When started, this thread will attempt to connect a locally hosted ServerSocket at port 5000, potentially throwing
	 * a UnkownHostException should be unable to locate one. It will then begin to ask (as well as print) the server if a 
	 * the list of riddles it contains until it receives an answer (which it will also print), a process it will repeat for
	 * all elements in the list, at which point it will end after sending one final message, "END", to it the server to indicate
	 * that it is disconnecting.
	 */
	public void run()
	{
		
		Socket clientSocket = null;
		try {

			clientSocket = new Socket("localhost", 5000); //connect locally to port 5000
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		PrintWriter out = null;
		BufferedReader in = null;

		//set up the IO to work with the local network
		try {
			out = new PrintWriter(clientSocket.getOutputStream());
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		} catch (IOException e) {

			e.printStackTrace();
		}
		
		
		
		//go through the whole list twice
		for(int j = 0; j < 2; j++)
		{
			
			for(int i = 0; i < (Riddles.length); i++)
			{
			
			System.out.println(name + " asks: " + Riddles[i]);
			out.write(Riddles[i] + '\n'); //the '\n' makes it work with a relevant .readline
			out.flush();
			
			try {
				
				System.out.println("To " + name + "'s riddle, thread " + in.readLine());
				// in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		
			
			
			}
		}
			
			//done sending requests
			
			System.out.println(name + " has finished its request and is now terminating.");
			out.print("END\n"); //the '\n' makes it work with a relevant .readline
			out.flush();
			
	
			//close everything
			
			try {
				
				in.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
		
			
		
		
		
	}
	
	
}
