/**
 * This class is an exception extending exception that is to be thrown when a read file is in a format other then
 * what the program wants.
 * @author qs5834mm Samuel Andrews
 *
 */
class FileFormatException extends Exception {
 
	private static final long serialVersionUID = 1L;

	public FileFormatException(String s) {
        super(s);
    }
}