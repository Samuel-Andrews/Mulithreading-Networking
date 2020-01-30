/**
 * A Riddle_Obj is a simple storage object that contains two strings, one that represents a Riddle, and another to represent
 * an answer to said riddle. The idea is that this is to be used to store both objects to allow for easy sorting.
 * @author qs5834mm Samuel Andrews
 *
 */
public class Riddle_Obj {
	
	private String Riddle;
	private String Answer;
	
	/**
	 * Null constructor for Riddle_Obj creates initializes the data as empty strings
	 */
	public Riddle_Obj ()
	{
		Riddle = "";
		Answer = "";
				
	}
	
	/**
	 * Recommended constructor for Riddle_Obj
	 * @param r A string meant to represent a riddle stored by the object
	 * @param a A string meant to represent an answer to the associated  riddle stored by the object
	 */
	public Riddle_Obj (String r, String a)
	{
		Riddle = r;
		Answer = a;
				
	}
	
	/**
	 * Method getRiddle	returns the riddle as a string
	 * @return the String meant to represent a riddle
	 */
	String getRiddle() {return Riddle;}
	
	/**
	 * Method getAnswer returns the answer as a string
	 * @return the String meant to represent an answer to a riddle
	 */
	String getAnswer() {return Answer;}
	
	/**
	 * Method setRiddle takes an input as a string and sets it as the riddle
	 * @param new_Riddle a string that will become the new riddle
	 */
	void setRiddle(String new_Riddle) {this.Riddle = new_Riddle;}
	
	/**
	 * Method setAnswer takes an input as a string and sets it as the riddle
	 * @param new_Answer a string that will become the answer to a riddle
	 */
	void setAnswer(String new_Answer) {this.Answer = new_Answer;}
	
	/**
	 * Method reset useful for testing or debugging that returns the Riddle_Obj to its defualt state
	 * of containing two empty strings
	 */
	void reset() {Riddle = ""; Answer = "";}
	
	/**
	 * Method display will print out both contained strings in the order of Riddle, Answer on two 
	 * consecutive lines. This allows for quick and easy viewing of its contents.
	 */
	void display() {
		
		System.out.println(Riddle);
		
		System.out.println(Answer);
		
		
		}
	

	

	
	
	
	

}
