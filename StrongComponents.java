import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

//Standalone implementation of the Kosaraju-Sharir Algorithm
//Takes input graphs.txt in format:
//Lines 1 & 2
//[# of nodes]
//[# of edges]
//Lines 3 - 3 + Number of edges
//[node1] [node2] where node1 is a directed edge to node2

public class StrongComponents{
	//Representation of the edges. edges[i] will contain the ArrayList of all edges connected to node i
	public static ArrayList<ArrayList<Integer>> edges = new ArrayList<ArrayList<Integer>>();
	//Reversed representation of edges i.e. if edges[x][something] = j then reversedEdges[j][something] = x
	public static ArrayList<ArrayList<Integer>> reversedEdges = new ArrayList<ArrayList<Integer>>();
	//List of visited nodes that will be used for DFS searches
	public static ArrayList<Integer> visitedNodes = new ArrayList<Integer>();
	//Global storage of the order of nodes obtained from depthFirstOrder
	public static ArrayList<Integer> nodeOrdering = new ArrayList<Integer>();
	//Global storage for the currently examined kernel for the 2nd stage of the Kosaraku-Sharir Algorith,
	public static ArrayList<Integer> kernel = new ArrayList<Integer>();

	public static void main(String args[]){
		BufferedReader br = null;
		try{
			String cLine[];
			br = new BufferedReader(new FileReader("graph.txt"));
			int nodeNum = Integer.parseInt(br.readLine());
			int edgeNum = Integer.parseInt(br.readLine());
			//Initialize the vertices
			for(int i = 0;i < nodeNum;i++){
				edges.add(new ArrayList<Integer>());
				reversedEdges.add(new ArrayList<Integer>());
			}
			//Verify our amounts are correct
			System.out.println("We have " + nodeNum + " vertices and " + edgeNum + " edges");
			//Read in the edges and put them into the array
			for(int i = 0;i < edgeNum;i++){
				String line = br.readLine();
				//line = line.substring(1);
				//System.out.println(line);
				cLine = line.split("\\s+");
				int n1 = Integer.parseInt(cLine[0]);
				int n2 = Integer.parseInt(cLine[1]);
				//Add the newly created edge to edges and reversedEdges
				edges.get(n1).add(n2);
				reversedEdges.get(n2).add(n1);
				//System.out.println("Reading in line "+i);
			}
			//Run Depth First Order in numerical order on the reversed digraph
			for(int i = 0;i < nodeNum;i++){
				depthFirstOrder(i);
			}
			//Reverse the node ordering ArrayList Obtained
			Collections.reverse(nodeOrdering);
			//for(int i:nodeOrdering){System.out.print(i+" ");}
			System.out.println("");
			//Clear visited since it will be used again for DFS
			visitedNodes.clear();
			int count = 1;
			//Now we can DFS based on the Reverse Post Order of the Reversed Di-Graph
			for(int i:nodeOrdering){
				//Don't want to redo a kernel that we've already visited so we need to make sure that
				//anything already visited won't be visited again
				if(!visitedNodes.contains(i)){
					//Call DFS from the next unchecked node in the reverse Post Order node list
					depthFirstSearch(i);
					System.out.print("Kernel #"+count+" contains nodes: ");
					for(int j:kernel){System.out.print(j+" ");}
					System.out.print("\n");
					count++;
					//Clear the kernel so we can build it again in the next iteration
					kernel.clear();
				}
			}
		}catch (IOException e){
			System.out.println("IO ERROR: Check Input and Program");
		}
	}
	//Depth first order takes a node and runs a DFS on all connected nodes in the
	//reversed DiGraph, adding unseen nodes to the visited list.
	//Once we finish the for loop, we can add the node to the node ordering Array List which will eventually result
	//in a list of nodes in pre-order. We can simply reverse this to obtain the reverse post order list.
	public static void depthFirstOrder(int node){
		//System.out.println("Now checking node "+ node);
		//System.out.println("Visited Nodes contains "+ visitedNodes.size() + " nodes");
		if(!visitedNodes.contains(node)){
			visitedNodes.add(node);
			//System.out.println("Added node to visited");
			if(!reversedEdges.get(node).isEmpty()){
				//for(int i:edges.get(node)){System.out.print(i + " ");}
				for(int i:reversedEdges.get(node)){
					//System.out.println("Now checking connection to node "+ reversedEdges.get(node).get(i));
					depthFirstOrder(i);
				}
			}
			nodeOrdering.add(node);
		}
	}
	//Standard DFS, using recursion rather than a stack(to save memory)
	//Upon running DFS on all the connected nodes to the input node, it will commit the input node
	//To the kernel that is currently being built.
	public static void depthFirstSearch(int node){
		if(!visitedNodes.contains(node)){
			visitedNodes.add(node);
			if(!edges.get(node).isEmpty()){
				for(int i:edges.get(node)){
					depthFirstSearch(i);
				}
			}
			//Append the current node to the list of nodes in the currently examined kernel
			kernel.add(node);
		}
	}

}
