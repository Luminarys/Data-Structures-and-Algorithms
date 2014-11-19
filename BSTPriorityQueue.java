package datastructures.and.algorithms;


/**
 *
 * @author ezra
 */
class LLNode{
    Integer val;
    LLNode left=null;
    LLNode right=null;
    LLNode prev=null;
    
    public LLNode(int val){
        this.val=val;
    }
    public String toString(){
        return ""+val;
    }
    public int size(){
        int count = 0;
        if(this.left!=null){
            count+=1;
            count+=this.left.size();
        }
        if(this.right!=null){
            count+=1;
            count+=this.right.size();
        }
        return count;
    }
}
public class BSTPriorityQueue {
    LLNode root;
    int size = 0;
    public BSTPriorityQueue(int val){
        root = new LLNode(val);
        size=1;
    }
    //Get the top item
    public int get(){
        int v = root.val;
        remove();
        return v;
    }
    
    public void put(int val){
        put(val,root);
        
        size++;
    }
    public void print(int depth){
          int count = 0;
         System.out.println("The Root Node is " + root.val);
        print(root,1);
        
    }
    public void print(LLNode n,int depth){
        boolean left,right;

        if(n.left!=null){
            System.out.println("Left Node of "+n.val+" at Level "+depth+" is "+n.left.val);
            left = true;
        }else{
            left = false;

        }
        if(n.right!=null){
            right = true;
            System.out.println("Right Node of "+n.val+"  at Level "+depth+" is "+n.right.val);
        }else{
            right = false;
        }
        if(left){
            print(n.left,depth+1);

        }
        if(right){
            print(n.right,depth+1);
  
        }
        
    }
    public void put(int val, LLNode n){
        LLNode t = new LLNode(val);
        if(n.left==null){
            t.prev=n;
            n.left=t;
            swim(t);
        }else if(n.right==null){
            t.prev=n;
            n.right=t;
            swim(t);
        }else{
            if(n.left.size()>n.right.size()){
                put(val,n.right);
            }else if(n.left.size()<=n.right.size()){
                put(val,n.left);
            }
        }
    }
    private void remove() {
        //Replace Top LLNode with ridiculously low value
        //Sink the node, then remove it
        LLNode t = new LLNode(-999999);
        t.left = root.left;
        t.right = root.right;
        root.left = null;
        root.right = null;
        root = t;
        validateTree(root);
        sink(root);
        
    }
    //Sink a node by exchanging up the greater of its two children
   private void sink(LLNode t){
       boolean first = true;
       while(t.left!=null||t.right!=null){
           if(t.left==null){
              t=exchangeUp(t.right).right;
              if(first){
                  root = t.prev;
                  first = false;
              }

           }else if(t.right==null){
                t=exchangeUp(t.left).left;
                if(first){
                  root = t.prev;
                  first = false;
              }

           }else{
               //ExchangeUP with LARGER value
               if(t.left.val>t.right.val){
                   LLNode nt=exchangeUp(t.left);
                    if(first){
                        validateTree(nt);
                  root = nt;
                  first = false;
              }
                    t = nt.left;
               }else{
                  LLNode nt=exchangeUp(t.right);
                   if(first){
                    validateTree(nt);
                  root = nt;
                  first = false;
                   }
                  t = nt.right;
                

               }
           }
           validateTree(root);

       }
       //Remove the node from the tree
       if(t.prev.left.val==t.val){
           t.prev.left=null;
       }else{
           t.prev.right=null;
       }
   }
       
    private void swim(LLNode t) {
        //Compare t with parent
       while(t.prev!=null){
            if(t.val>t.prev.val){
            t=exchangeUp(t);
            //ENSURE TREE IS VALID - all downward/upward links should point in the correct directions
            validateTree(root);
            }else{
                break;
            }
        }
       if(t.prev==null){
           root=t;
           
       }
    }
public static boolean isEqual(LLNode o1, LLNode o2) {
    return o1 == o2 || (o1 != null && o1.equals(o2));
}
    private LLNode exchangeUp(LLNode t) {
        LLNode left = t.prev.left;
        LLNode right = t.prev.right;
        LLNode prev = t.prev.prev;
        LLNode cleft=t.left;
        LLNode cright=t.right;
        int vcheck=t.prev.val;
        LLNode cprev = t;
        if(!isEqual(t,left)){
          
          t.left=left;
          if(t.left!=null){
          t.left.prev=t;
          }
        }else{
            t.left=t.prev;
        }
         if(!isEqual(t,right)){
          t.right=right;
          if(t.right!=null){
          t.right.prev=t;
          }
        }else{
          t.right=t.prev;
         }
        t.prev.left=cleft;
        t.prev.right=cright;
        t.prev.prev=t;
        t.prev = prev;
        if(t.prev!=null){
        if(t.prev.left.val==vcheck){
            t.prev.left=t;
        }else{
            t.prev.right=t;
        }
        }
        return t;
    }
    //Ensures links are properly connected in a downward fashion - repoints upward links based on downward ones
    private void validateTree(LLNode n) {
        boolean left,right;

        if(n.left!=null){
            n.left.prev=n;
            left = true;
        }else{
            left = false;
          //  System.out.println("Left LLNode at Level "+depth+" is empty");

        }
        if(n.right!=null){
            n.right.prev=n;

            right = true;
        }else{
            right = false;
            //System.out.println("Right LLNode at Level "+depth+" is empty");
        }
        if(left){
            validateTree(n.left);

        }
        if(right){
            validateTree(n.right);
  
        }
        
    }    
}
