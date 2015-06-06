

package Zenefits;

import java.util.*;


/**
 * Java class for finding the minimal vertex cover in a given graph.
 * @author venkatramreddykunta
 */
public class MinimalVertexCoverOptimized {
    //Priority Queue backed up a max heap and custom Node Comparator
    Queue<Node> maxHeap;
    //Map for storing the vertices and associated custom Node object
    HashMap<Integer,Node> keyMap;
    
    //Default constructor
    MinimalVertexCoverOptimized(){
        NodeComparator nodeComp=new NodeComparator();
        maxHeap=new PriorityQueue<>(nodeComp);
        keyMap=new HashMap<>();
    }
    
    //Initialize the data members
    public void addAll(Map<Integer,Set<Integer>> adjList){
        for(Integer key:adjList.keySet()){
            Node current=new Node(key,adjList.get(key)); 
            keyMap.put(key, current);            
            maxHeap.add(current);
        }      
    }
    
    //Greedy Choice : Remove the vertex with maximum number of edges
    //@return : A vertex with maximum edge count 
    public Integer removeMax(){
        //Extract the max edge count vertex from the graph
        Node current=this.maxHeap.remove();
        //Isolate the max vertex from the graph
        this.keyMap.remove(current.key);
        //For each neighbour of the max edge count vertex 
        for(Integer neighbour:current.neighbours){
            //Extract the current neigbour
            Node temp = keyMap.get(neighbour);
            //Remove the max edge count vertex from the neighbours set
            temp.neighbours.remove(current.key);
            /*
                If the node was directly updated in the priority queue, heapification won't happen
                Therefore we remove the current neighbour from Priority Queue and then re-insert into the Priority Queue
                Time taken for removal and rehepification is O(logn)
                *** Hence complexity reduced from O(n) to O(k*logn) where k is the average number of edges
            */
            //Remove the neigbour from the max heap
            maxHeap.remove(temp);
            //If the neighbour has more than one edges then re-insert the node into the priority queue
            if(temp.neighbours.size()>0){
                maxHeap.add(temp);
            }
        }
        //Return the max edge count vertex
        return current.key;
    }
    

    public static void main(String[] args) {
        
        MinimalVertexCoverOptimized vertexCoverInstance=new MinimalVertexCoverOptimized();
        //Given input adjacency list
        Map<Integer,Set<Integer>> adjList = new HashMap<>();
        int stage=0;
        Set<Integer> edges = new HashSet(Arrays.asList(2,3,4));
        adjList.put(1,edges);        
        edges = new HashSet(Arrays.asList(1));
        adjList.put(2,edges);
         edges = new HashSet(Arrays.asList(1,5));
        adjList.put(3,edges);
        edges = new HashSet(Arrays.asList(1,5));
        adjList.put(4,edges);
        edges = new HashSet(Arrays.asList(3,4));
        adjList.put(5,edges);
        //Initialize the data members of the class from the adjacency list
        vertexCoverInstance.addAll(adjList);
        //Output set for sending the results
        Set<Integer> output = new HashSet<>();
        //Each time remove the max element from the heap until the priority queue is empty
        while(!vertexCoverInstance.maxHeap.isEmpty()){
            System.out.println("Stage:"+(++stage));
            //Print the current state of priority Queue
            System.out.println("Priority Queue : "+vertexCoverInstance.maxHeap);
            //Current state of the key map
            System.out.println("Key Map : "+vertexCoverInstance.keyMap);
            //Remove the max edge count vertex
            Integer vertex = vertexCoverInstance.removeMax();
            System.out.println("Max Edge Count Vertex at stage "+stage+" is "+vertex);
            output.add(vertex);
        }
        //Print results
        System.out.println("Minimal Vertex Cover : "+output);
    }
    
}

/*
    POJO class that identifies a given vertex in a graph
*/
class Node {
    Set<Integer> neighbours;
    int key;
    Node(int key,Set<Integer> neighbours){
        this.key=key;
        this.neighbours=neighbours;
    }
    public String toString(){
        return "("+key+","+neighbours+")";
    }
}

class NodeComparator implements Comparator<Node>{

    @Override
    public int compare(Node first, Node second) {
         return first.neighbours.size()<second.neighbours.size()?1:-1;
    }
    
    
}