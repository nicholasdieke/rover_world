// Internal action code for project ia_submission

package ia_submission;

import java.util.ArrayList;
import java.util.List;

import jason.*;
import jason.asSemantics.*;
import jason.asSyntax.*;

public class find_home extends DefaultInternalAction {

    @Override
    public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception {
// execute the internal action
//        ts.getAg().getLogger().info("executing internal action 'ia_submission.avoid_block'");
//        if (true) { // just to show how to throw another kind of exception
//            throw new JasonException("not implemented!");
//        }
    	
    	String blocks = ((ListTerm) args[0]).toString();
    	int x = (int) ((NumberTerm) args[1]).solve();
    	int y = (int) ((NumberTerm) args[2]).solve();
    	int LastX = (int) ((NumberTerm) args[3]).solve();
    	int LastY = (int) ((NumberTerm) args[4]).solve();
    	    	    	    	
    	int XOptimal = 0;
    	int YOptimal = 0;
    	
    	System.out.println("Blocks: "+blocks);
    	System.out.println("From: "+x+","+y+" to home");
    	System.out.println("Last Move: "+LastX+","+LastY);
    	
    	int lastIndex = 0;
    	int count = 0;

    	while (lastIndex != -1) {

    	    lastIndex = blocks.indexOf("ob", lastIndex);

    	    if (lastIndex != -1) {
    	        count++;
    	        lastIndex += 7;
    	    }
    	}

    	System.out.println(count+" obstacles");
    	
    	if (count == 0) {
    		XOptimal = -1;
        	YOptimal = -1;
    	} else {
    	
	    	Point current = new Point(x,y);
	    		    	
	    	List<Node> neighbours = new ArrayList<Node>();
	
	        if (!blocks.contains("ob(0,1)") && !(LastX == 0 && LastY == -1)) {
	        	Point p = new Point(0,1);
	        	neighbours.add(new Node(null, p, 0, Map.ChebyshevDistance(new Point(current.x, current.y-1), new Point(0,0)),0));
	        }
	        if (!blocks.contains("ob(0,-1)") && !(LastX == 0 && LastY == 1)) {
	        	Point p = new Point(0, -1);
	        	neighbours.add(new Node(null, p, 0, Map.ChebyshevDistance(new Point(current.x, current.y+1), new Point(0,0)),0));
	        }
	        if (!blocks.contains("ob(1,0)") && !(LastX == -1 && LastY == 0)) {
	        	Point p = new Point(1, 0);
	        	neighbours.add(new Node(null, p, 0, Map.ChebyshevDistance(new Point(current.x-1, current.y), new Point(0,0)),0));
	        }
	        if (!blocks.contains("ob(-1,0)") && !(LastX == 1 && LastY == 0)) {
	        	Point p = new Point(-1, 0);
	        	neighbours.add(new Node(null, p, 0, Map.ChebyshevDistance(new Point(current.x+1, current.y), new Point(0,0)),0));
	        }
	        if (!blocks.contains("ob(-1,-1)") && !(LastX == 1 && LastY == 1)) {
	        	Point p = new Point(-1, -1);
	        	neighbours.add(new Node(null, p, 0, Map.ChebyshevDistance(new Point(current.x+1, current.y+1), new Point(0,0)),0));
	        }
	        if (!blocks.contains("ob(1,1)") && !(LastX == -1 && LastY == -1)) {
	        	Point p = new Point(1, 1);
	        	neighbours.add(new Node(null, p, 0, Map.ChebyshevDistance(new Point(current.x-1, current.y-1), new Point(0,0)),0));
	        }
	        if (!blocks.contains("ob(-1,1)") && !(LastX == 1 && LastY == -1)) {
	        	Point p = new Point(-1, 1);
	        	neighbours.add(new Node(null, p, 0, Map.ChebyshevDistance(new Point(current.x+1, current.y-1), new Point(0,0)),0));
	        }
	        if (!blocks.contains("ob(1,-1)") && !(LastX == -1 && LastY == 1)) {
	        	Point p = new Point(1, -1);
	        	neighbours.add(new Node(null, p, 0, Map.ChebyshevDistance(new Point(current.x-1, current.y+1), new Point(0,0)),0));
	        }
	        
	        Node finalMove = neighbours.get(0);
	    	for(Node n : neighbours) {
	    		System.out.println("Neighbour at "+n.p.x+","+n.p.y+" with h="+n.h);
	    		if(n.h < finalMove.h) finalMove = n;
	        }
	    	
	    	XOptimal = finalMove.p.x;
	    	YOptimal = finalMove.p.y;
    	}
    	    	
    	
    	
    	
        // everything ok, so returns true
    	return un.unifies(new NumberTermImpl(XOptimal), args[5]) && un.unifies(new NumberTermImpl(YOptimal), args[6]);

    }
}
