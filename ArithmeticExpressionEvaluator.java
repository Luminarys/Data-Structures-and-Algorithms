import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//Takes an Equation with all token separated by spaces, converts infix to postfix, then evaluates the postfix expression
//Doubles used in calculation for accuracy
//Uses Stacks for primary data structure
//Tested using the equation: 5 + ( 3 * ( 4 + 2 ) ) + 6 * ( 3 + 2 ) / 5
//Post fix evaluates this to: 5 3 4 2 + * + 6 3 2 + * 5 / + 
//Infix evaluates this to: 29.0
public class ArithmeticExpressionEvaluator {

	public static void main(String[] args) {
		//Testing Equation
		String eq="5 + ( 3 * ( 4 + 2 ) ) + 6 * ( 3 + 2 ) / 5";
		//Prep for Lexing
		System.out.println("Infixed: "+eq );
		StringTokenizer st= new StringTokenizer(eq);
		String[] s=new String[st.countTokens()];
		int i=0;
		 while (st.hasMoreTokens()) {
	         s[i]=st.nextToken();
	         i++;
	     }
		 //Infix to Postfix
		 String postFixed= infixToPostfix(s,0);
		 System.out.println("Post fixed: "+ postFixed);
		 //Lex the Postfixed Equation
		 StringTokenizer pt= new StringTokenizer(postFixed);
			String[] p=new String[pt.countTokens()];
			int z=0;
			 while (pt.hasMoreTokens()) {
		         p[z]=pt.nextToken();
		         z++;
		     }
		//Evaluate the postfix expression
		 String answer = evaluatePostfix(p);
		 System.out.println("Final Result: "+answer);
	}

	private static String evaluatePostfix(String[] p) {
        Stack<String> vals = new Stack<String>();
      	Pattern number=Pattern.compile("[0-9]");
 		Pattern operator=Pattern.compile("[-+/*]");
 		Matcher numMatcher;
		Matcher opMatcher;
		//Use a single stack approach
		//Add numbers to stack until we hit operator
		//Perform first operator on previous two on stack
		//push the result on top of the stack, repeat
        for(int i=0;i<p.length;i++){
      
    		numMatcher = number.matcher(p[i]);
        	opMatcher = operator.matcher(p[i]);
        	//If the current token is a number, push it on top of the stack
        	if(numMatcher.find()){
        		vals.push(p[i]);
        	}else{
        		//For an operator, pop the top two numbers and run the operator on them
        		//Then push the resulting value on top of the stack
        		String i1=vals.pop();
        		String i2=vals.pop();
        		vals.push(Double.toString(performOp(i1,i2,p[i])));
        	}
        }
        //The final value should have been pushed to the top of the stack, pop it for the return
		return vals.pop();
	}
	//Perform the operation between two strings - Uses doubles for accuracy
	private static Double performOp(String i1, String i2, String sop) {
		char op = sop.charAt(0);
		double a = Double.valueOf(i1);
		double b = Double.valueOf(i2);
		 switch (op)
	        {
	        case '+':
	            return (double) (a + b);
	        case '-':
	            return (double) (b - a);
	        case '*':
	            return (double) (a * b);
	        case '/':
	            if (b == 0)
	                throw new
	                UnsupportedOperationException("Cannot divide by zero");
	            return (double) (b / a);
	        case '^':
	        	return Math.pow(a, b);
	        }
	        return 0.0;
	}

	private static String infixToPostfix(String[] s, int depth) {
		//Init Stack for operators
        Stack<String> ops = new Stack<String>();
        String re="";
        //Init Regex's for matching
        Pattern number=Pattern.compile("[0-9]");
		Pattern operator=Pattern.compile("[-+/*]");
		Matcher numMatcher;
		Matcher opMatcher;
		//Parse through the tokens, adding numbers to a final string and putting operators on a stack.
		//Whenever the current operator on top of the stack has precedence over the operator being checked, we'll output the stack and add the current operator on top
		//Whenever curent operator on top of the stack does not have precedence over the operator being checked, we'll simply add the current operator to the top of the stack
		//Whenever Parenthesis are encountered, we recursively deal with them by parsing through the section then running the infixToPostfix method on the section within the parenthesis
		//Until the entire equation within the parenthesis has been succesfully parsed
        for(int i=0;i<s.length;i++){
        	//Establish Matchers for the current Token
        	numMatcher = number.matcher(s[i]);
        	opMatcher = operator.matcher(s[i]);
        	//If its a number, we'll put it in our output string
        	if(numMatcher.find()){
        		re+=s[i]+" ";
        	//If its the start of a parenthesis statement, parse till we find the end of the statement and evaluate that expression with another a call
        	}else if(s[i].equals("(")){
            	int parenCount=0;
        		int z = 0;
        		for(int j=i;j<s.length;j++){
        			if(j>i&&s[j].equals("(")){
        				parenCount++;
        			}
        			if(s[j].equals(")")){
        				if(parenCount==0){
        				re+=infixToPostfix(Arrays.copyOfRange(s,i+1,j),depth+1);
        			//	System.out.println("Parenthesis evaluated to:" + infixToPostfix(Arrays.copyOfRange(s,i+1,j))+":from below");
        			//	System.out.println("Parenthesis Array = "+Arrays.toString(Arrays.copyOfRange(s,i+1,j)));
        				z=j;
        				break;
        				}else{
        					parenCount--;
        				}
        			}
        			z=j;
        		}
    			i=z;
    			//If its an operator check for precedence with the top of the stack
    			//If theres precedence, we'll parse through the stack and write out all the operators
    			//If theres no precedence, simply add current operator to the top of the stack
    			if(depth==0){
    				re+=ops.pop()+" ";
    			}
        	}else if(opMatcher.find()){
        		if(ops.size()==0){
        			ops.push(s[i]);
        		}else if(ops.size()>0){
        			if(hasPrecedence(s[i],ops.peek())){
        				for(int x=0;x<ops.size()+1;x++){
            				re+=ops.pop()+" ";
        				}        				
        				ops.push(s[i]);
        			}else{
        				ops.push(s[i]);
        			}
        		}
        	}
        }
        //If theres anything left on the stack, add it to the end
        if(ops.size()>0){
        	for(int x=0;x<ops.size()+1;x++){
    			re+=ops.pop()+" ";
			}             	
        }
        
        
		return re;
	}
	//Does op2 have precedence over op1
	 public static boolean hasPrecedence(String op1, String op2)
	 
	    {
	        if (op2.equals("(") || op2.equals(")"))
	            return false;
	        if ((op1 == "^") && (op2 == "*" || op2 == "/"))
	            return false;
	        if ((op1 == "^") && (op2 == "+" || op2 == "-"))
	            return false;
	        if ((op1.equals( "*") || op1.equals("/")) && (op2.equals("+") || op2.equals("-")))
	            return false;
	        else
	            return true;
	    }
}
//Stack Data structure
//Implements Peek for convenience.
class Stack<Item>{
    private int N;                
    private Node<Item> first;    

    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
    }

    public Stack() {
        first = null;
        N = 0;
    }
    public boolean isEmpty() {
        return first == null;
    }
    public int size() {
        return N;
    }
    public void push(Item item) {
        Node<Item> oldfirst = first;
        first = new Node<Item>();
        first.item = item;
        first.next = oldfirst;
        N++;
    }
    public void flip(){
  
    }
    
    public Item pop() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        Item item = first.item;        
        first = first.next;           
        N--;
        return item;                   
    }
    public Item peek() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        return first.item;
    }

}
