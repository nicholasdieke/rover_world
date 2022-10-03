// Internal action code for project ia_submission

package ia_submission;

import jason.*;
import jason.asSemantics.*;
import jason.asSyntax.*;

public class update_visited extends DefaultInternalAction {

    @Override
    public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception {
        // execute the internal action
//        ts.getAg().getLogger().info("executing internal action 'ia_submission.update_visited'");
//        if (true) { // just to show how to throw another kind of exception
//            throw new JasonException("not implemented!");
//        }
    	
    	int x = (int) ((NumberTerm) args[0]).solve();
    	int y = (int) ((NumberTerm) args[1]).solve();
        
        Maze.addVisited(x, y);

        // everything ok, so returns true
        return true;
    }
}
