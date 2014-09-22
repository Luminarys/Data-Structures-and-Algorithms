import java.util.ListIterator;
import java.util.NoSuchElementException;


public class DoublyLinkedList implements Iterable<String>{
	public int size;        
    private Node be;     
    private Node af;
	public DoublyLinkedList(){
		be = new Node();
		af = new Node();
		be.next = af;
		af.prev = be;
	}
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
    public ListIterator<String> iterator()  { return new listIterator(); }
    public void addBeg(String val){
    	add(val,0);
    }
    public void addEnd(String val){
    	add(val,size);
    }
    public void removeBeg(){
    	remove(1);
    }
    public void removeEnd(){
    	remove(size);
    }
	public static void main(String[] args) {
		DoublyLinkedList list = new DoublyLinkedList();
		list.add("hello");
		list.add("world");
		list.add("lol");
		System.out.println(list.toString());
        ListIterator<String> iterator = list.iterator();
        //Adds the indicated value as the Nth value
        list.add("test",1);
        list.addBefore("hello","this");
		System.out.println(list.toString());
		//Removes the Nth value
		list.removeNode("test");
		System.out.println(list.toString());
		list.moveToBack("hello");
		list.moveToFront("lol");
		System.out.println(list.toString());

	}
	public void moveToFront(String val){
	   removeNode(val);
	   addBeg(val);
	}
	public void moveToBack(String val){
		removeNode(val);
		addEnd(val);
	}
	
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
	 public String toString() {
	        StringBuilder s = new StringBuilder();
	        for (String item : this)
	            s.append(item + " ");
	        return s.toString();
	    }
	class Node{
	public String val;
	public Node next;
	public Node prev;
	}
	
	private class listIterator implements ListIterator<String> {
		public Node current=be.next;
	    public Node lastAccessed=null;
	    public int pos=0;
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
		public String getVal(){
			return current.val;
					
		}
		public int getSize(){
			return size;
		}
		@Override
		public boolean hasNext() {
			return pos < size;
		}
	
		

		@Override
		public boolean hasPrevious() {
			return pos > 0;		
			}

		@Override
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

		@Override
		public int nextIndex() {
			return pos;
		}

		@Override
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

		@Override
		public int previousIndex() {
			return size-1;
		}

		@Override
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

