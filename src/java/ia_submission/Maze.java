package ia_submission;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Maze {
	
	static List<Point> visited = new ArrayList<Point>();            
    static Stack<Point> to_visit = new Stack<Point>();
    
    public static void addVisited(int x, int y) {
    	Point p = new Point(x, y);
    	if(!isInList(visited, p))
    	visited.add(p);
//    	printList("Visited", visited);
    }
    
    public static void addToVisit(int x, int y) {
    	to_visit.add(new Point(x, y));
//    	printList("To visit", to_visit);
    	
    }
    
    public static void printToVisit() {
    	printList("To visit", to_visit);
    	
    }
    
    public static void printVisited() {
    	printList("Visited", visited);
    	
    }
    
    public static Stack<Point> getToVisit() {
    	return to_visit;
    }
    
    private static boolean isInList(List<Point> list, Point point){
	    for (Point p : list){
	      if(p.x == point.x && p.y == point.y) {
	        return true;
	      }  
	    }
	    return false;
	  }
    
    public static void printList(String name, List<Point> list) {
    	System.out.print(name+": ");
    	for(Point p : list) {
        	System.out.print("("+p.x+","+p.y+") ");
    	}
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

	public static int[][] getBacktrackingPath() {
		
		List<Point> steps = new ArrayList<Point>();
		
		List<Point> visited_clone= new ArrayList<Point>(visited);
		Collections.reverse(visited_clone);
		printList("Reverse Visited: ",visited_clone);
		Point next_edge = null;
		if(to_visit.size() > 0) {
			next_edge = to_visit.pop();
		}
		
		for(int i = 0; i<visited_clone.size()-1; i++) {
			int x = visited_clone.get(i).x-visited_clone.get(i+1).x;
			int y = visited_clone.get(i).y-visited_clone.get(i+1).y;
			
			if(next_edge != null && visited_clone.get(i).x == next_edge.x && visited_clone.get(i).y == next_edge.y) {
				System.out.println("Arrived at edge");
				break;
			}
			
			steps.add(new Point(-x, -y));
			System.out.println("Go from "+visited_clone.get(i).x+","+visited_clone.get(i).y+" to "+visited_clone.get(i+1).x+","+visited_clone.get(i+1).y);
		}

		return listToArray(steps);
	}

	public static boolean hasVisited(int x, int y) {
		return isInList(visited, new Point(x, y));
	}

	public static void restart() {
		visited = new ArrayList<Point>();            
	    to_visit = new Stack<Point>();
		
	}
    

}
