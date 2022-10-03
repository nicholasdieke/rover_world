package ia_submission;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Collections;


public class Map {
	
	private static Map map = null;
	
	public static int GRID_WIDTH, GRID_HEIGHT;
	
	static int[][] map_array = null;
	
	public static void setResourceOnMap(int type, int x, int y) {
		 // Flipped because of the way 2d arrays work. First [] indicates the "row" and the second the "column"
		map_array[y][x] = type;

	}
	
	public static void initialise(int width, int height) {
		GRID_WIDTH = width;
		GRID_HEIGHT = height;
		map_array = new int[GRID_HEIGHT][GRID_WIDTH];
		
	}
	
	
	public static int[][] listToArray(List<Point> list)
    {
		int[][] path_arr = new int[list.size()][2];

	    for (int i = 0; i < list.size(); i++){
	      path_arr[i][0] = list.get(i).x;
	      path_arr[i][1] = list.get(i).y;
	    }
        return path_arr;
    }
	
	public static int[][] getGoldLocations()
    {
		List<Point> goldLocs = new ArrayList<Point>();
		
		for (int i = 0 ; i < GRID_HEIGHT; i++) {
		    for(int j = 0 ; j < GRID_WIDTH ; j++)
		    {
		         if ( map_array[i][j] == 2)
		         {	
		        	 System.out.println("Gold at "+i+","+j);
		              goldLocs.add(new Point(i,j));
		         }
		    }
		}
		
        return listToArray(goldLocs);
    }
	
	public static int[][] getDiamondLocations()
    {
		List<Point> diamondLocs = new ArrayList<Point>();
		
		for (int i = 0 ; i < GRID_HEIGHT; i++) {
		    for(int j = 0 ; j < GRID_WIDTH ; j++)
		    {
		         if ( map_array[i][j] == 3)
		         {
		        	 diamondLocs.add(new Point(i,j));
		         }
		    }
		}
		
        return listToArray(diamondLocs);
    }
	
	public static int[][] findPath(Point from, Point destination)
    {
		System.out.println("Looking for path from "+from.x+","+from.y+" to "+destination.x+","+destination.y);
	    Node startNode = new Node(null, from, 0, ManhattanDistance(from, destination), ManhattanDistance(from, destination));
		Node goalNode = new Node(null, destination, 0, 0,0);
	    
	    //list of closed nodes
	    List<Node> open = new ArrayList<Node>();            
	    List<Node> closed = new ArrayList<Node>();
	    
	    //Add starting point
	    open.add(startNode);  
	    
	    while(open.size() > 0) {
	    	
	    	Node node = getBestNode(open);
	    	
	    	if(node.p.x == goalNode.p.x && node.p.y == goalNode.p.y) {
	            System.out.println("Goal reached");
	            return getOptimalPath(node);
	        }
	    	
	    	open.remove(node);
	    	closed.add(node);
	    	
	    	List<Node> neighbors = getNeighbors(node);
	    	
	    	for(Node n : neighbors) {
	            float g_score = node.g + 1;
	            float h_score = ChebyshevDistance(n.p, goalNode.p);
	            float f_score = g_score + h_score;

	            if(isInList(closed,n) && f_score >= n.f) 
	                continue;

	            if(!isInList(open,n) || f_score < n.f) {
	                n.g = g_score;
	                n.h = h_score;
	                n.f = f_score;
	                if(!open.contains(n)) {
	                    open.add(n);
	                }
	            }
	        }
	    	
	    
	    }

        return null;
    }
	
	 private static int[][] getOptimalPath(Node node){
		    Node currentNode = node;
		    
		    List<Point> path = new ArrayList<Point>();  
		    

		    while (currentNode.parent != null){

		      path.add(new Point(currentNode.p.x-currentNode.parent.p.x, currentNode.p.y-currentNode.parent.p.y));

		    currentNode = currentNode.parent;
		    }
		    
		    Collections.reverse(path);
		  

		    return listToArray(path);
		}
	
	private static boolean isInList(List<Node> list, Node node){
	    for (Node n : list){
	      if(n.p.x == node.p.x && n.p.y == node.p.y) {
	        return true;
	      }  
	    }
	    return false;
	  }

	private static List<Node> getNeighbors(Node node) {
		
		List<Node> neighbours = new ArrayList<Node>();
		if (node.p.y + 1 >= 0 && node.p.y + 1  < GRID_HEIGHT && node.p.x + 1 >= 0 && node.p.x + 1  < GRID_HEIGHT && !isObstacle(node.p.x + 1,node.p.y + 1)) neighbours.add(new Node(node, new Point(node.p.x + 1, node.p.y + 1), 0,0,0)); 
        if (node.p.y - 1 >= 0 && node.p.y - 1  < GRID_HEIGHT && node.p.x - 1 >= 0 && node.p.x - 1  < GRID_HEIGHT && !isObstacle(node.p.x - 1,node.p.y - 1)) neighbours.add(new Node(node, new Point(node.p.x - 1, node.p.y - 1), 0,0,0)); 
        if (node.p.x + 1 >= 0 && node.p.x + 1  < GRID_HEIGHT && node.p.y - 1 >= 0 && node.p.y - 1  < GRID_HEIGHT &&  !isObstacle(node.p.x + 1,node.p.y - 1)) neighbours.add(new Node(node, new Point(node.p.x + 1, node.p.y - 1), 0,0,0)); 
        if (node.p.x - 1 >= 0 && node.p.x - 1  < GRID_HEIGHT && node.p.y + 1 >= 0 && node.p.y + 1  < GRID_HEIGHT && !isObstacle(node.p.x - 1,node.p.y + 1)) neighbours.add(new Node(node, new Point(node.p.x - 1, node.p.y + 1), 0,0,0)); 


        if (node.p.y + 1 >= 0 && node.p.y + 1  < GRID_HEIGHT && !isObstacle(node.p.x,node.p.y + 1)) neighbours.add(new Node(node, new Point(node.p.x, node.p.y + 1), 0,0,0)); // S
        if (node.p.y - 1 >= 0 && node.p.y - 1  < GRID_HEIGHT && !isObstacle(node.p.x,node.p.y - 1)) neighbours.add(new Node(node, new Point(node.p.x, node.p.y - 1), 0,0,0)); // N
        if (node.p.x + 1 >= 0 && node.p.x + 1  < GRID_HEIGHT && !isObstacle(node.p.x + 1,node.p.y)) neighbours.add(new Node(node, new Point(node.p.x + 1, node.p.y), 0,0,0)); // E
        if (node.p.x - 1 >= 0 && node.p.x - 1  < GRID_HEIGHT && !isObstacle(node.p.x - 1,node.p.y)) neighbours.add(new Node(node, new Point(node.p.x - 1, node.p.y), 0,0,0)); // W
        
        
        return neighbours;
		}

	private static Node getBestNode(List<Node> arr) {
		
		Node node = arr.get(0);
		
		int count = 0;
		for (int i = 0; i < arr.size(); i++) {
			if (arr.get(i).f < node.f) {
				count = i;
			}
		}
		
		return arr.get(count);
	}
	
	public  static boolean isObstacle(int child_x, int child_y) {
		return map_array[child_y][child_x] == 1;
			
	}

	public static int ManhattanDistance(Point from, Point dest) {
		return Math.abs(dest.x-from.x) + Math.abs(dest.y-from.y);
	}
	
	public static int ChebyshevDistance(Point from, Point dest) {
		return Math.max(Math.abs(dest.x-from.x), Math.abs(dest.y-from.y));
	}

	public static int[][] getMapArray() {
		// TODO Auto-generated method stub
		return map_array;
	}

}

class Node {
	Point p;
	float g, h, f;
	Node parent;
	public List<Point> neighbors;
    
    Node(Node parent, Point p, float g, float h, float f) {
    	this.parent = parent;
        this.p = p;
        this.g = g;
        this.h = h;
        this.f = f;
        this.neighbors = new ArrayList<Point>();
    }
}

class Point {
    int x;
    int y;
    
    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

