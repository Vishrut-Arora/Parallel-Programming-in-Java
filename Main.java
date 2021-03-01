//Vishrut Arora
//2019399
import java.util.*;
import java.lang.*;
import java.io.*;
import java.util.concurrent.*;



interface Details{
	public int get_tree_height();
	public void set_tree_height(int a);
	public int calculate_tree_depth(int k,Node root);
	public int calculate_node_depth_util(Node node, int data, int level,int k);
	public int calculate_node_depth(Node node, int data,int k);
}
class Node 
{ 
	private int key; 
	private Vector<Node> child;

	public int get_key(){
		return this.key;
	} 
	public Vector<Node> get_child(){
		return this.child;
	}
	public void set_key(int key){
		this.key=key;
	}
	public void set_child(Node a){
		this.child.add(a);
	}
	public void set_vectorNode(){
		this.child=new Vector<Node>();
	}
};



class Random_Tree_Create implements Details{

	private static Random_Tree_Create instance =new Random_Tree_Create();//Singleton Design Pattern

	private int ind;
	private static int height_tree=0;
	private static int check_nodes=0;

	private Random_Tree_Create(){										//private constructor

	}
	public static Random_Tree_Create getInstance(){						//get instance of object created
		return instance;
	}
public Node newNode(int value) 
{ 
	Node new_Node = new Node(); 
	new_Node.set_key(value); 
	new_Node.set_vectorNode();
	//new_Node.set_child(new Vector<Node>()); 
	return new_Node; 
}  

// Function to build full k-ary tree 
public Node Build_Tree_util(int A[], int n, 
						int k, int h) 
{ 
	// For null tree 
	if (n <= 0) 
		return null; 

	Node new_Node = newNode(A[ind]); 
	if (new_Node == null) 
	{ 
		System.out.println("Memory error" ); 
		return null; 
	} 

	// For adding k children to a node 
	for (int i = 0; i < k; i++) 
	{ 

		// Check if ind is in range of array 
		// Check if height of the tree is greater than 1 
		if (ind < n - 1 && h > 1) 
		{ 
			ind++; 

			// Recursively add each child 
			new_Node.set_child(Build_Tree_util(A, n, k, h - 1)); 
		} 
		else
		{ 
			new_Node.set_child(null); 
		} 
	} 
	return new_Node; 
} 


public Node Build_Tree(int[] A, int n, int k, int in) 
{ 
	int ht = (int)Math.ceil(Math.log((double)n * (k - 1) + 1) / 
								Math.log((double)k)); 
	ind = in; 
	return Build_Tree_util(A, n, k, ht); 
} 


public void printTree(int k,Node root) 
{ 
	if (root == null) 
		return; 
	for (int i = 0; i < k; i++) {
		printTree(k,root.get_child().get(i)); 
		if(root.get_child().get(i)!=null){
		System.out.println("Parent: "+root.get_key()+" Child: "+root.get_child().get(i).get_key());
	}
	}
	//System.out.print(root.key + " "); 
}

// Function to print postorder traversal of the tree
public void postorderUtil(int k,Node root,int start,int end){
	for(int i=start;i<=end;i++){
		postorder(k,root,i,root);
	}
} 
public void postorder(int k,Node root,int node,Node root1) 
{ 
	if (root == null) 
		return; 
	for (int i = 0; i < k; i++) 
		postorder(k, root.get_child().get(i), node,root1); 
	if(root.get_key()==node){
		++check_nodes;
		System.out.println("Node: "+node+" at depth "+calculate_node_depth(root1,node,k));
	}
	//System.out.print(root.key + " "); 
}
public int get_check_nodes(){
	return check_nodes;
}

@Override
public int calculate_tree_depth(int k,Node root){
	
	        if (root == null) 
            return 0; 
        else 
        { 

            int depth[]=new int[k];
            int index=0;
            int max=depth[0];
            for(int i=0;i<k;i++){
            	depth[i]=calculate_tree_depth(k,root.get_child().get(i));
            	if(depth[i]>max){
            		max=depth[i];
            		index=i;
            	}
            }
          
            return (depth[index]+1); 
        }
}
@Override
public int get_tree_height(){
	return this.height_tree;
}
@Override
public void set_tree_height(int height_tree){
	this.height_tree=height_tree;
}

	@Override
    public int calculate_node_depth_util(Node node, int data, int level,int k)  
    { 
        if (node == null) 
            return 0; 
   
        if (node.get_key() == data) 
            return level; 
        int downlevel=0;
        for(int i=0;i<k;i++){
        	downlevel=calculate_node_depth_util(node.get_child().get(i), data, level + 1,k);
        	       if (downlevel != 0) 
            return downlevel; 
        }   
            return downlevel;
    } 
   
    @Override
    public int calculate_node_depth(Node node, int data,int k)  
    { 
        return calculate_node_depth_util(node, data, 1,k); 
    }

}


class Multithread implements Runnable, Details//implements Runnable
{   private int k;
	private Node root;
	private int start;
	private int end;
	private static int height_tree;
	Multithread(int k,Node root,int start,int end){
		this.k=k;
		this.root=root;
		this.start=start;
		this.end=end;
	}
 void postorder(int k,Node root,int node,Node root1) 
{ 
	if (root == null) 
		return; 
	for (int i = 0; i < k; i++){ 
		postorder(k,root.get_child().get(i),node,root1);
		}
		if(root.get_key()==node){
			System.out.println("Node: "+node+" at depth "+calculate_node_depth(root1,node,k));
		} 

}
@Override
public void run(){
	for(int i=start;i<=end;i++){
	postorder(k,root,i,root);
}
}
@Override
public int calculate_tree_depth(int k,Node root){
	
	        if (root == null) 
            return 0; 
        else 
        { 

            int depth[]=new int[k];
            int index=0;
            int max=depth[0];
            for(int i=0;i<k;i++){
            	depth[i]=calculate_tree_depth(k,root.get_child().get(i));
            	if(depth[i]>max){
            		max=depth[i];
            		index=i;
            	}
            }
          
            return (depth[index]+1); 
        }
}
@Override
public int get_tree_height(){
	return this.height_tree;
}
@Override
public void set_tree_height(int height_tree){
	this.height_tree=height_tree;
}
	@Override
    public int calculate_node_depth_util(Node node, int data, int level,int k)  
    { 
        if (node == null) 
            return 0; 
   
        if (node.get_key() == data) 
            return level; 
        int downlevel=0;
        for(int i=0;i<k;i++){
        	downlevel=calculate_node_depth_util(node.get_child().get(i), data, level + 1,k);
        	       if (downlevel != 0) 
            return downlevel; 
        }   
            return downlevel;
    } 
   
    @Override
    public int calculate_node_depth(Node node, int data,int k)  
    { 
        return calculate_node_depth_util(node, data, 1,k); 
    }
}

class ThreadPool extends RecursiveAction implements Details{
	private int k;
	private Node root;
	private int node;
	private static int height_tree;
	private Node root1;
	ThreadPool(int k,Node root,int node,Node root1){
		this.k=k;
		this.root=root;
		this.node=node;
		this.root1=root1;
	}
@Override
	public void compute(){
		//ThreadPool abc[]=new ThreadPool[this.end-this.start+1]; 
	if (root == null) 
		return; 
	for (int i = 0; i < k; i++){ 

		ThreadPool abc1 =new ThreadPool(this.k,this.root.get_child().get(i),this.node,this.root1);
		//ThreadPool abc2 =new ThreadPool(this.k,this.root.child.get(i),this.node+1,this.start,this.end);
		abc1.fork();
		//abc1.compute();
		//invokeAll(abc1);
		//abc1.helpQuiesce();
	}
		if(root.get_key()==node){
			System.out.println("Node: "+node+" at depth "+calculate_node_depth(root1,node,k));
		} 

	//System.out.print(root.key + " "); 
	}
@Override
public int calculate_tree_depth(int k,Node root){
	
	        if (root == null) 
            return 0; 
        else 
        { 

            int depth[]=new int[k];
            int index=0;
            int max=depth[0];
            for(int i=0;i<k;i++){
            	depth[i]=calculate_tree_depth(k,root.get_child().get(i));
            	if(depth[i]>max){
            		max=depth[i];
            		index=i;
            	}
            }
          
            return (depth[index]+1); 
        }
}
@Override
public int get_tree_height(){
	return this.height_tree;
}
@Override
public void set_tree_height(int height_tree){
	this.height_tree=height_tree;
}
	@Override
    public int calculate_node_depth_util(Node node, int data, int level,int k)  
    { 
        if (node == null) 
            return 0; 
   
        if (node.get_key() == data) 
            return level; 
        int downlevel=0;
        for(int i=0;i<k;i++){
        	downlevel=calculate_node_depth_util(node.get_child().get(i), data, level + 1,k);
        	       if (downlevel != 0) 
            return downlevel; 
        }   
            return downlevel;
    } 
   
    @Override
    public int calculate_node_depth(Node node, int data,int k)  
    { 
        return calculate_node_depth_util(node, data, 1,k); 
    }


}

class efficiency{
	private double time_squential;
	private double time_parallel;
	//private int number_threads;

	public double speedup(){
		return (this.time_squential/this.time_parallel);
	}
	public double parallel_efficiency(){
		return (speedup()/(double)Runtime.getRuntime().availableProcessors());
	}
	public void set_time_sequential(double time_squential){
		this.time_squential=time_squential;
	}
	public void set_time_parallel(double time_parallel){
		this.time_parallel=time_parallel;
	}


}
class Main{

	public static void main(String args[]) throws InterruptedException
{

	Scanner sc=new Scanner(System.in);
	int ind = 0; 
	int n = 10000;
	int number_threads=0;
	double time_squential=0;
	double time_parallel=0;

	System.out.print("Enter the number of nodes: ");
	n=sc.nextInt();
	System.out.println("\nEnter the range of nodes you want to check:");
	System.out.print("Start: ");
	int start=sc.nextInt();
	System.out.print("\nEnd: ");
	int end=sc.nextInt();
	System.out.println("\nChoose:\n1)Explicit Multithreading\n2)ForkJoinPool\n");
	int option=sc.nextInt();
	System.out.println("\nEnter number of threads");
	number_threads=sc.nextInt();
	
	Random rand=new Random();
	int k = rand.nextInt(5)+1;
	int preorder[]=new int[n];
	int[] arr_distinct=new int[100001];
	for(int i=0;i<n;i++){
		while(arr_distinct[preorder[i]]!=0 || preorder[i]==0){
	    preorder[i]=rand.nextInt(100000)+1;
	}
	    ++arr_distinct[preorder[i]];
	}
	//int preorder[] = { 1, 2, 5, 6, 7, 3, 8, 9, 1000000, 4 }; 
	double startTime_check = System.nanoTime();
		
	Random_Tree_Create rc=Random_Tree_Create.getInstance();

	Node root = rc.Build_Tree(preorder, n, k, ind);
	rc.set_tree_height(rc.calculate_tree_depth(k,root));
	double endTime_check = System.nanoTime();
	rc.printTree(k,root);
	System.out.println("Time taken to build the Tree: "+(endTime_check-startTime_check)/1000000+" ms");
	System.out.println("Height of the Tree: "+rc.get_tree_height());
	int st=start;
	int ed=end;
 double startTime2 = System.nanoTime();
 rc.postorderUtil(k,root,start,end);
 if(rc.get_check_nodes()==0){
 	System.out.println("\nNo nodes were found that match the given range");
 } 		
 double endTime2 = System.nanoTime();
 double duration2 = (endTime2 - startTime2);
time_squential=duration2/1000000;
 System.out.println("Time Taken for Parallel Processing via Sequential: "+duration2/1000000+" ms\n");
	if(option==1){
	Multithread[] a=new Multithread[number_threads];
	for(int i=0;i<number_threads;i++){
		if(i==(number_threads-1))
		{
			a[i]=new Multithread(k,root,st,ed);
			//System.out.println("("+st+","+ed+")");
		}
		else
		{
			a[i]=new Multithread(k,root,st,st+(ed-st)/(number_threads-i));
			//System.out.println("("+st+","+(st+(ed-st)/(number_threads-i))+")");
		}

		st=st+((ed-st)/(number_threads-i)) +1;
	}
if(rc.get_check_nodes()==0){
 	System.out.println("No nodes were found that match the given range");
 } 
 a[0].set_tree_height(rc.get_tree_height());
	if(number_threads==1){
		Thread t1=new Thread(a[0]);
		double startTime = System.nanoTime();
		t1.start();
		t1.join();
		double endTime = System.nanoTime();
		System.out.println("Time Taken for Parallel Processing via Multithreading: "+(endTime-startTime)/1000000+" ms");
		time_parallel=(endTime-startTime)/1000000;
	}
	else if(number_threads==2){
		Thread t1=new Thread(a[0]);
		Thread t2=new Thread(a[1]);
		double startTime = System.nanoTime();
		t1.start(); t2.start();
		t1.join();	t2.join();
		double endTime = System.nanoTime();
		System.out.println("Time Taken for Parallel Processing via Multithreading: "+(endTime-startTime)/1000000+" ms");	
		time_parallel=(endTime-startTime)/1000000;	
	}
	else if(number_threads==3){
		Thread t1=new Thread(a[0]);
		Thread t2=new Thread(a[1]);
		Thread t3=new Thread(a[2]);
		double startTime = System.nanoTime();
		t1.start(); t2.start(); t3.start();
		t1.join();  t2.join();	t3.join();
		double endTime = System.nanoTime();
		System.out.println("Time Taken for Parallel Processing via Multithreading: "+(endTime-startTime)/1000000+" ms");	
		time_parallel=(endTime-startTime)/1000000;	
	}
	else if(number_threads==4){
		Thread t1=new Thread(a[0]);
		Thread t2=new Thread(a[1]);
		Thread t3=new Thread(a[2]);
		Thread t4=new Thread(a[3]);
		double startTime = System.nanoTime();
		t1.start(); t2.start(); t3.start();	t4.start();
		t1.join();  t2.join();	t3.join();  t4.join();
		double endTime = System.nanoTime();
		System.out.println("Time Taken for Parallel Processing via Multithreading: "+(endTime-startTime)/1000000+" ms");	
		time_parallel=(endTime-startTime)/1000000;	
	}
	else{
		System.out.println("Number of threads entered are not in the range 1-4");
	}
	
	}
	else if(option==2){
		int str=start;
		ForkJoinPool pool =new ForkJoinPool(number_threads);
			ThreadPool task[]=new ThreadPool[end-start+1];
			for(int i=0;i<(end-start+1);i++){
				task[i]=new ThreadPool(k,root,str,root);
				//task[i].helpQuiesce();
				//pool.invoke(task[i]);
				++str;
			}
			task[0].set_tree_height(rc.get_tree_height());
			double startTime = System.nanoTime();
			for(int i=0;i<(end-start+1);i++){
				task[i].helpQuiesce();
				pool.invoke(task[i]);
			}
			double endTime = System.nanoTime();
			if(rc.get_check_nodes()==0){
 					System.out.println("No nodes were found that match the given range");
 				} 
		System.out.println("Time Taken for Parallel Processing via ForkJoinPool: "+(endTime-startTime)/1000000+" ms");
		time_parallel=(endTime-startTime)/1000000;

	}
		efficiency e=new efficiency();
		e.set_time_sequential(time_squential);
		e.set_time_parallel(time_parallel);
		System.out.println("\nSpeedup: "+e.speedup());
		//e.set_number_threads(number_threads);
		System.out.println("Parallel Efficiency: "+e.parallel_efficiency());
} 
} 

