// Internal action code for project ia_submission

package ia_submission;

import java.util.ArrayList;
import java.util.List;

import jason.*;
import jason.asSemantics.*;
import jason.asSyntax.*;

public class avoid_block extends DefaultInternalAction {

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
    	    	    	    	
    	int XOptimal = 0;
    	int YOptimal = 0;
    	
    	System.out.println("Blocks: "+blocks);
    	System.out.println("Last Move: "+LastX+","+LastY);
    	    	
    	if(!blocks.contains("ob(0,1)") && !(LastX == 0 && LastY == -1)){
    		XOptimal = 0;
         	 YOptimal = 1;
    	} else if (!blocks.contains("ob(0,-1)") && !(LastX == 0 && LastY == 1)){
    		XOptimal = 0;
         	 YOptimal =-1;
    	}else if (!blocks.contains("ob(1,0)") && !(LastX == -1 && LastY == 0)){
    		XOptimal = 1;
         	 YOptimal = 0;
         	
    	} else if (!blocks.contains("ob(-1,0)") && !(LastX == 1 && LastY == 0)){
    		XOptimal = -1;
         	 YOptimal = 0;
    	}  else {
    		XOptimal = -LastX;
         	YOptimal = -LastY;
    	}
    	
    	
        // everything ok, so returns true
    	return un.unifies(new NumberTermImpl(XOptimal), args[3]) && un.unifies(new NumberTermImpl(YOptimal), args[4]);

    }
}
