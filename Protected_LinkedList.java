


import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


/**
 * A Protected_LinkedList is fundamentally just a housing for the java.util LinkedList. Functionally, it is meant to
 * provide the ability to be locked while certain tasks are being performed on it. The only implementation of that here is
 * in the RandomizeList method, which ReadWriteLocks the list while it is benig shuffled. Otherwise, the only method in it
 * @author qs5834mm Samuel Andrews
 *
 * @param <E> an object of any kind.
 */
public class Protected_LinkedList<E> {

	ReadWriteLock rwlock; //form the lock
	LinkedList<E> list;
	Lock readLock; ///Piece out the lock
	Lock writeLock;
	
	
	/**
	 * Constructor of Protected_LinkedList takes only a Java.util LinkedList as an argument an serves to allow one
	 * to lock the list while modifying it.
	 * @param inp 	A LinkedList holding any type of object
	 */
	public Protected_LinkedList (LinkedList<E> inp) {
		
		list = inp;
		rwlock = new ReentrantReadWriteLock(); //build the locks
		readLock = rwlock.readLock(); 
		writeLock = rwlock.writeLock();
		
	}
	
	/**
	 * Method RandomizeList will invoke the Collection.shuffle method of on the LinkedList inside of this object,
	 * but will also readLock the list whilst this is happening to avoid logical errors. It will sleep the thread
	 * running this program by .25s seconds after it does this action so that it can be used more effectively in
	 * with the initial thread, although this bit could also exists there for the same effect.
	 */
	public void RandomizeList ()
	{
		readLock.lock();
		Collections.shuffle(list);
		readLock.unlock();
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * method getLinkedList() returns the LinkedList held by this Protected_LinkedList. This will need
	 * to be invoked to access or modify the list in any way other then the RandomizeListFunction
	 * @return list, a LinkedList held by the object of type E
	 */
	public LinkedList<E> getLinkedList()
	{
		return list;
	}

	
	
	
	
}
