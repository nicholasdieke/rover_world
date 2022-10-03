// Internal action code for project ia_submission

package ia_submission;

import jason.*;
import jason.asSemantics.*;
import jason.asSyntax.*;

public class initialise_map extends DefaultInternalAction {

    @Override
    public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception {
        // execute the internal action
//        ts.getAg().getLogger().info("executing internal action 'ia_submission.initialise_map'");
//        if (true) { // just to show how to throw another kind of exception
//            throw new JasonException("not implemented!");
//        }
    	
    	int width = (int) ((NumberTerm) args[0]).solve();
    	int height = (int) ((NumberTerm) args[1]).solve();
    	
    	Map.initialise(width, height);

        // everything ok, so returns true
        return true;
    }
}
