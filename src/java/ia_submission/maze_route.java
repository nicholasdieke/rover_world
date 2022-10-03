// Internal action code for project ia_submission

package ia_submission;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.apache.commons.codec.binary.StringUtils;

import jason.*;
import jason.asSemantics.*;
import jason.asSyntax.*;

public class maze_route extends DefaultInternalAction {

    @Override
    public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception {
        // execute the internal action
//        ts.getAg().getLogger().info("executing internal action 'ia_submission.avoid_block'");
//        if (true) { // just to show how to throw another kind of exception
//            throw new JasonException("not implemented!");
//        }
    	
    	String blocks = ((ListTerm) args[0]).toString();
    	int LastX = (int) ((NumberTerm) args[1]).solve();
    	int LastY = (int) ((NumberTerm) args[2]).solve();
    	int carrying = (int) ((NumberTerm) args[3]).solve();
    	int currentX = (int) ((NumberTerm) args[4]).solve();
    	int currentY = (int) ((NumberTerm) args[5]).solve();
    	int endX = (int) ((NumberTerm) args[6]).solve();
    	int endY = (int) ((NumberTerm) args[7]).solve();
    	    	
    	System.out.println("Blocks: "+blocks);
    	System.out.println("Last Move: "+LastX+","+LastY);
    	System.out.println("Current: "+currentX+","+currentY);
    	System.out.println("End: "+endX+","+endY);
    	Maze.printVisited();
    	    	    	
    	int XOptimal = 0;
    	int YOptimal = 0;
    	
    	int lastIndex = 0;
    	int count = 0;

    	while (lastIndex != -1) {

    	    lastIndex = blocks.indexOf("ob", lastIndex);

    	    if (lastIndex != -1) {
    	        count++;
    	        lastIndex += 7;
    	    }
    	}

//    	System.out.println(count+"obstacles");
    	
    	
    	if (count == 3) {
    		XOptimal = -1;
         	YOptimal = -1;
    	} else {
//        	System.out.println("Current: "+currentX+","+currentY);

    	if (count == 1) {
    		Maze.addToVisit(currentX, currentY);
    	}
    	
//    	if (carrying == 1) {
//    		Map.ManhattanDistance(new Point(currentX, currentY), new Point(endX,endY));
//    	} else {
    		
    	 if(!blocks.contains("ob(0,1)") && !Maze.hasVisited(currentX, currentY+1) && !(LastX == 0 && LastY == -1)){
    		XOptimal = 0;
         	 YOptimal = 1;
    	}  else if (!blocks.contains("ob(1,0)") && !Maze.hasVisited(currentX+1, currentY) && !(LastX == -1 && LastY == 0)){
    		XOptimal = 1;
          	 YOptimal = 0;
          	
    	} else if (!blocks.contains("ob(-1,0)") && !Maze.hasVisited(currentX-1, currentY) && !(LastX == 1 && LastY == 0)){
    		XOptimal = -1;
       	 YOptimal = 0;
    	}  else if (!blocks.contains("ob(0,-1)") && !Maze.hasVisited(currentX, currentY-1) && currentY>0 && !(LastX == 0 && LastY == 1)){
    		XOptimal = 0;
         	 YOptimal =-1;
   	} else {
   		Maze.printToVisit();
   		Stack<Point> s = Maze.getToVisit();
   		s.pop();
   		Point p = s.pop();
   		System.out.println("getNext() result: "+p.x+","+p.y);
   		XOptimal = p.x-currentX;
    	 YOptimal =p.y-currentY;
   		
   	}
    	}
    	    	

        // everything ok, so returns true
    	return un.unifies(new NumberTermImpl(XOptimal), args[8]) && un.unifies(new NumberTermImpl(YOptimal), args[9]);

    }
}
