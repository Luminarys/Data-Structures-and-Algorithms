package datastructures.and.algorithms;

//Node class. Contains a Print method(for easy debugging) and a length method which counts the amount of children
class Node {
	int val;
	Node next;
 
	Node(int x) {
		val = x;
		next = null;
	}
        public void print(){
               System.out.print(val + " ");
            if(next!=null){
                next.print();
                
            }
        }
        public int len(){
            int c = 0;
            Node n = this;
            while(n.next!=null){
                n=n.next;
                c+=1;
            }
            return c;
        }
}
 
public class LinkedListNaturalMergeSort {
 
	// Sort method - Given an LL, it will find the first two natural runs, merge them,
        //then recall itself using the LL with the two natural runs removed, before merging the init. two
        //natural runs with the results of the recursive call
	public static Node sort(Node head) {
            //Since modifying stuff causes all instances to be modified, we need duplicates to be stored that are immutable
                final Node h = copy(head);
                final Node h3 = copy(head);
		if (head == null || head.next == null)
			return head;

		// count total number of elements, use this to check if we are at the end of the list
		int count = head.len()+1;
		Node l = head;
                
                Node merged=head;
                //Get the first Natural Run
		Node h1 = getSegment(l);  
                //if the entire sequence is already in sorted order, just return that sequence
                if(h1.len()==count){
                    return h1;
                }
                //Find Node where the list was cutoff and use this to start looking for 2nd natural run
                Node temp = findPos(h1,h);
                //Get the second Natural run
		Node h2 = getSegment(temp);
                //merge natural runs
		merged = merge(h1, h2);
                
                int len = merged.len()+1;
                //check if the two natural runs comprised the remainder of the list, if it did return the merged list
                if(merged.len()+1==count){
                     System.out.println("Finished bottom iteration of Mergesort, final seq is: ");
                merged.print();
                System.out.println();
                    return merged;
                }
                //if we still aren't at the end, get the node(remainder of LL) where the 2nd natural run ended.
                Node next = findPos(len,h3);
                System.out.println("Finished one iteration of Mergesort, now merging: ");
                merged.print();
                System.out.print("with ");
                next.print();
                System.out.println();
                //Now what we do is call the method again, using the linked list without the two natural runs we found
                //We will merge this result with what we find
		return merge(merged,sort(next));
	}
 
	public static Node merge(Node l, Node r) {
		Node p1 = l;
		Node p2 = r;
 
		Node newHead = new Node(100);
		Node pNew = newHead;
 
		while (p1 != null || p2 != null) {
 
			if (p1 == null) {
				pNew.next = new Node(p2.val);
				p2 = p2.next;
				pNew = pNew.next;
			} else if (p2 == null) {
				pNew.next = new Node(p1.val);
				p1 = p1.next;
				pNew = pNew.next;
			} else {
				if (p1.val < p2.val) {
					pNew.next = new Node(p1.val);
					p1 = p1.next;
					pNew = pNew.next;
				} else if (p1.val == p2.val) {
					pNew.next = new Node(p1.val);
					pNew.next.next = new Node(p1.val);
					pNew = pNew.next.next;
					p1 = p1.next;
					p2 = p2.next;
 
				} else {
					pNew.next = new Node(p2.val);
					p2 = p2.next;
					pNew = pNew.next;
				}
			}
		}
 
		return newHead.next;
	}
 
	public static void main(String[] args) {
		Node n1 = new Node(12);
		Node n2 = new Node(11);
		Node n3 = new Node(13);
 
		Node n4 = new Node(7);
		Node n5 = new Node(8);
		Node n6 = new Node(6);
                Node n7 = new Node(9);
		Node n8 = new Node(5);
		Node n9 = new Node(4);
 
		Node n10 = new Node(-1);
		Node n11 = new Node(2);
		Node n12 = new Node(1);
		n1.next = n2;
		n2.next = n3;
		n3.next = n4;
		n4.next = n5;
		n5.next = n6;
                n6.next = n7;
		n7.next = n8;
		n8.next = n9;
		n9.next = n10;
		n10.next = n11;
                n11.next = n12;
		n1 = sort(n1);
 
		printList(n1);
	}
 
	public static void printList(Node x) {
		if(x != null){
			System.out.print(x.val + " ");
			while (x.next != null) {
				System.out.print(x.next.val + " ");
				x = x.next;
			}
			System.out.println();
		}
 
	}
    private static Node attachNode(Node n,int v){
        Node c = n;
        while(n.next!=null){
            n=n.next;
        }
        n.next = new Node(v);
        return c;
    }
    private static Node getSegment(Node l) {
        //Find and return a natural run
        Node head = l;
        //Loop down a natural run until the natural run is no longer valid
        while(l.next!=null){
            if(l.next.val > l.val){
            l=l.next;
            }else{
                break;
            }
        }
        l.next=null;
        return head;
        
    }
    private static Node findPos(Node end,Node complete){
        
        while(end.next!=null){
            end=end.next;
            complete=complete.next;
        }
        return complete.next;
    }

    private static Node copy(Node head) {
        Node n = new Node(head.val);
        while(head.next!=null){
            n = attachNode(n,head.next.val);
            head = head.next;
        }
        return n;
    }

    private static Node findPos(int len, Node h) {
        for(int i =0;i<len;i++){
            h=h.next;
        }
        return h;
    }
}