import java.io.BufferedReader;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * This class will kick-start and set up all of the threads and data (from Riddles.txt) to accomplish the tasks as outlined
 * in the JAVA and COMMUNICATIONs final assignment. Other then a method to find the current directory of its location, it
 * is merely a main that makes use the other classes within the project. 
 * @author qs5834mm Samuel Andrews
 *
 */
public class Initial {
	
	/**
	 * Method getCurrentProjectDirectory() returns the absolute location for the current directory that this project
	 * currently inhabits as a string
	 * @return a string containing the file path the projects current directory
	 */
	static String getCurrentProjectDirectory() {
		return new File("").getAbsolutePath(); // makes a file for the method then uses a class method to get the abs.
												// location
	}
	
	
	
	/**
	 * This main is the catalyst for all of the logic performed in the various networked threads proceeding it.
	 * The only output it will itself produce that is seen in the console is the project directory, length of 
	 * the Riddle source file, and a confirmation that the client threads are down and that the socket master
	 * is ready to end. It will first read in the riddles and answer pairs as unique objects known as Riddle Objects
	 * and ultimately store them in a protected linked list and store the riddles alone in a string array. Assuming the read goes well,
	 * it will then send the linked list to a new Server Socket master it then builds while preparing the clients with
	 * the riddle array. It will then start those threads which will network as intended produce the question-answer output
	 * desried.
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		// set-up the files for the threads via extracting them from Riddles.txt
				String projectDirectory = getCurrentProjectDirectory(); // grabs the location of the current directory
				System.out.println("Riddles from Project Directory: " + projectDirectory); // prints it

				String file = projectDirectory + "\\Riddles.txt";

				// get the length in bits
				File Riddle = new File(file);
				System.out.println("Length: " + Riddle.length() + " bytes");
				
				System.out.println();

				LinkedList<Riddle_Obj> Rid_Store = new LinkedList<Riddle_Obj>(); // linked list for line storage
				
				String[] Riddles = new String[25]; //array of the riddles for the clients

				//Riddle_Obj pair = new Riddle_Obj();
				
				String r = null; //stores the riddle and answer for use in construction of Riddle_Obj for the linked list
				String a = null;
				
				boolean determine = false; //used for logic in the reader
				
				int iter = 1; //keeps track of what line we are on
				int rcount = 0; //keeps track of the riddle being read into array
				
				// Reader
				FileReader fr;
				try {

					fr = new FileReader(file);

				} catch (FileNotFoundException e) {

					e.printStackTrace();

					return;

				}

				BufferedReader br = new BufferedReader(fr);

				String inputString = null;
				try {
					while ((inputString = br.readLine()) != null) {
						
						if(determine == false)
						{
												
							//if the file is not formatted correctly, we will let the user know and end the program
							//it also tells them where the problem occurred
							
							
								
							
							if(inputString.substring(1,7).equals("Riddle") == false)
							{
								br.close();
								throw new FileFormatException("File not in proper format, an issue was found on line " + iter);
								
							}
							
							
							
						r = inputString.substring(inputString.lastIndexOf(">") + 1); //add and remove labels assumes the label ends with ">"
						determine = true;											 //could also just substring everything after the 8th (7th in java)
						Riddles[rcount] = r; //read riddles into array
						rcount++; //increment position of next riddle
						iter++;
						}
						else
						{
							
							//if the file is not formatted correctly, we will let the user know and end the program
							//it also tells them where the problem occurred
							if(inputString.substring(1,7).equals("Answer") == false)
							{
								br.close();
								throw new FileFormatException("File not in proper format, an issue was found on line " + iter);
							}
							
							a = inputString.substring(inputString.lastIndexOf(">") + 1);
							Rid_Store.add(new Riddle_Obj(r,a));							
							determine = false;
							iter++;
							
						}
						
						
					}

					br.close(); //closes the reader

				} catch (IOException e) {

					e.printStackTrace();
				} catch(FileFormatException ex)
				{
					System.out.println(ex.getMessage());
					System.out.println("Ending program");
					
					System.exit(-1);
				}
		
				
				
				//ready to process threads
				
				Protected_LinkedList<Riddle_Obj> pRid_Store = new Protected_LinkedList<Riddle_Obj>(Rid_Store);
				
				//create the master Server Socket, send it the protected linked list
				Server_Socket Master = new Server_Socket(pRid_Store);
				
				
				//create three Clients implementing runnable. Theoretically you could do up 999 at once
				//before the server socket would run out of unique server threads to pair.
				ClientThread A = new ClientThread("A_Client", Riddles);
				
				Thread A_Client = new Thread(A, "A_Client");

				
				ClientThread B = new ClientThread("B_Client", Riddles);
				
				Thread B_Client = new Thread(B, "B_Client");
				
				
				ClientThread C = new ClientThread("C_Client", Riddles);
				
				Thread C_Client = new Thread(C, "C_Client");
				
				
				//start the master and clients
				Master.start();
				
				A_Client.start();
				
				B_Client.start();
				
				C_Client.start();
					
				
				//sees if the clients are still alive, otherwise shuffles the linked list every .25 seconds
				while(A_Client.isAlive() == true && B_Client.isAlive() == true && C_Client.isAlive() == true)
				{
					
					
				pRid_Store.RandomizeList();
				
				}
				
				System.out.println("All Clients have completed their tasks, closing master server socket.");
				
				try {
					Master.End(); //ends the master server socket thread when clients are done
				} catch (IOException e) {e.printStackTrace();}
					
				
				//The initial thread ends
				
		
	}

}