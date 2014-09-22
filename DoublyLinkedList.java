import java.util.ListIterator;
import java.util.NoSuchElementException;

//DoublyLinked List Implementation. Uses Nodes that have a prev and next node value.
//Implements an Iterator to traverse through the list and perform basic operations, which are built upon to complete the more complex tasks like removing a node based on an index
public class DoublyLinkedList implements Iterable<String>{
	public int size;        
    private Node be;     
    private Node af;
    //Constructer initializes two attached nodes
	public DoublyLinkedList(){
		be = new Node();
		af = new Node();
		be.next = af;
		af.prev = be;
	}
	//Basic addition method adds a node to the end of the list
    public void add(String val) {
        Node last = af.prev;
        Node x = new Node();
        x.val = val;
        x.next = af;
        x.prev = last;
        af.prev = x;
        last.next = x;
        size++;
    }
    //Method for getting an iterator
    public ListIterator<String> iterator()  { return new listIterator(); }
    
    //Uses add to add a node to the beginning
    public void addBeg(String val){
    	add(val,0);
    }
    
    //Uses add to add a node to the end
    public void addEnd(String val){
    	add(val,size);
    }
    //Uses remove to remove the node at the start of the list
    public void removeBeg(){
    	remove(1);
    }
    
    //Uses remove to remove a node at the end of the list
    public void removeEnd(){
    	remove(size);
    }
    
    //Main method tests the class with sample input
	public static void main(String[] args) {
		DoublyLinkedList list = new DoublyLinkedList();
		//Adds in a node to the end of the list
		list.add("hello");
		list.add("world");
		list.add("lol");
        //Adds the indicated value as the Nth value
        list.add("test",1);
        list.addBefore("hello","this");
		//Removes the Nth value or the Node with the given string value, using remove() and removeNode() respectively
		list.removeNode("test");
		//Moves a node to the back of the list
		list.moveToBack("hello");
		//Moves a node to the fronst of the list
		list.moveToFront("lol");
		//Print Ln for verification
		System.out.println(list.toString());

	}
	//Moves a specified node to the front by removing it from the list and using addBeg()
	public void moveToFront(String val){
	   removeNode(val);
	   addBeg(val);
	}
	//Moves a specified node to the end by removing it from the list and using addEnd()

	public void moveToBack(String val){
		removeNode(val);
		addEnd(val);
	}
	
	//Looks for the given node and adds a node before it by subtracting one from the index and using that in the add() call
	public void addBefore(String val,String ins){
	    ListIterator<String> it = this.iterator();  
	    boolean found=false;
	   for(int i=0;i<this.size;i++){
		    if(it.next().equals(val)){
		    	 add(ins,it.nextIndex()-1);
		    	 found=true;
		    	 break;
		    }
	   }
		if(!found)throw new NoSuchElementException();
	}
	
	//Looks for the given node and adds a node after it by using the index in the add() call
	public void addAfter(String val,String ins){
	    ListIterator<String> it = this.iterator();  
	    boolean found=false;
	   for(int i=0;i<this.size;i++){
		    if(it.next().equals(val)){
		    	 add(ins,it.nextIndex());
		    	 found=true;
		    	 break;
		    }
	   }
		if(!found)throw new NoSuchElementException();

	}
	
	//Adds a given node to the list at a specified index.
	//Searches through the list incrementing or decrementing until it arrives at the item.
	//If the node doesn't exist, an exception is throw, if not the given node is added
	public void add(String val, int index){
        ListIterator<String> it = this.iterator();  
        while(it.nextIndex()!=index){
			if(it.nextIndex()<index){
				if(it.hasNext()){
					it.next();
				}else{
					throw new NoSuchElementException();
				}
			}else{
				if(it.hasPrevious()){
					it.previous();
				}else{
					throw new NoSuchElementException();
				}
			}
		}
		it.add(val);
	}
	
	//Uses a similar approach to add(), but removes the value that has been iterated to rather than adding onto it
	public void remove(int index){
		 ListIterator<String> it = this.iterator();  
	        while(it.nextIndex()!=index){
				if(it.nextIndex()<index){
					if(it.hasNext()){
						it.next();
					}else{
						throw new NoSuchElementException();
					}
				}else{
					if(it.hasPrevious()){
						it.previous();
					}else{
						throw new NoSuchElementException();
					}
				}
			}
			it.remove();
	}
	//Removes a specific node by iterating through the list until its found then removing that node
	//If the node does not exist, and exception is thrown
	public void removeNode(String val){
	    ListIterator<String> it = this.iterator();  
	    boolean found=false;
	   for(int i=0;i<this.size;i++){
		    if(it.next().equals(val)){
		    	remove(it.nextIndex());
		    	 found=true;
		    	 break;
		    }
	   }
		if(!found)throw new NoSuchElementException();
	}
	
	//Converts the list to a string, for convenience
	 public String toString() {
	        StringBuilder s = new StringBuilder();
	        for (String item : this)
	            s.append(item + " ");
	        return s.toString();
	    }
	 
	//Base node Data Structure
	//Contains information of the value of the node, and the next and previous nodes
	private class Node{
	public String val;
	public Node next;
	public Node prev;
	}
	//Iterator for DoublyLinkedList
	private class listIterator implements ListIterator<String> {
		//The currently selected node
		public Node current=be.next;
		//The last accessed node, for when we want to iterate backwards
	    public Node lastAccessed=null;
	    //The iterator's current position in the list
	    public int pos=0;
	    //Adds a node to the list at the current position by altering the next and prev values of nodes
		public void add(String val) {
			 	Node prev = current.prev;
	            Node insert = new Node();
	            Node cur = current;
	            insert.val = val;
	            prev.next = insert;
	            insert.next = cur;
	            cur.prev = insert;
	            insert.prev = prev;
	            size++;
	            pos++;
	            lastAccessed = null;
		}
		public boolean hasNext() {
			return pos < size;
		}

		public boolean hasPrevious() {
			return pos > 0;		
			}
		//returns the value of the next node and sets the current node to the next one as well as lastAccessed to the current node before incrementing pos
		public String next() {
			try{
				lastAccessed = current;
				String val = current.val;
				current = current.next;
				pos++;
				return val;
			}catch(NoSuchElementException e){
				System.err.println("There is no Next Node");
			}
			return null;
		}
		public int nextIndex() {
			return pos;
		}
		//Returns the value of the previous node and sets the current node to the previous one as well as lastAccessed to the current node before decrementing pos
		//Throws an exception if lastAccesssed doesn't exist
		public String previous() {
			try{
				current = current.prev;
				lastAccessed = current;
				String val = current.val;
				pos--;
				return val;
			}catch(NoSuchElementException e){
				 e.printStackTrace();
				System.err.println("There is no Previous Node");
			}
			return null;
		}

		public int previousIndex() {
			return size-1;
		}
		//Removes the current node
		//If there are no nodes then it throws an error
		public void remove() {
			try{
			       Node prev = lastAccessed.prev;
		            Node next = lastAccessed.next;
		            prev.next = next;
		            next.prev = prev;
		            size--;
		            if (current == lastAccessed){
		                current = next;
		            }else{
		                pos--;
		            }
		            lastAccessed = null;
			}catch(IllegalStateException e){
				e.printStackTrace();
				System.out.println("Could not remove: Last Accessed does not exist");
			}
		}
		//Sets the previously accessed node's value to the input
		//If the last accessed node doesn't exist, an error is thrown
		public void set(String arg0) {
			 try{
	            lastAccessed.val = arg0;
			 }catch(IllegalStateException e){
				 e.printStackTrace();
					System.err.println("There is no Last Accessed Node");

			 }
		}


	}

}

