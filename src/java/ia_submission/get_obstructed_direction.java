// Internal action code for project ia_submission

package ia_submission;

import jason.*;
import jason.asSemantics.*;
import jason.asSyntax.*;

public class get_obstructed_direction extends DefaultInternalAction {

    @Override
    public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception {
        // execute the internal action
//        ts.getAg().getLogger().info("executing internal action 'ia_submission.get_obstructed_direction'");
//        if (true) { // just to show how to throw another kind of exception
//            throw new JasonException("not implemented!");
//        }
    	
    	int XLeft = (int) ((NumberTerm) args[0]).solve();
    	int YLeft = (int) ((NumberTerm) args[1]).solve();
    	
    	int XOptimal = 0;
    	int YOptimal = 0;
    	
    	if (XLeft>0 && YLeft>0) {XOptimal = 1; YOptimal = -1;}
    	else if (XLeft<0 && YLeft<0) {XOptimal = -1; YOptimal = 1;}
    	else if (XLeft==0 && YLeft<0) {XOptimal = 1; YOptimal = -1;}
    	else if (XLeft==0 && YLeft>0) {XOptimal = 1; YOptimal = 1;}
    	else if (XLeft>0 && YLeft==0) {XOptimal = 1; YOptimal = 1;}
    	else if (XLeft<0 && YLeft==0) {XOptimal = -1; YOptimal = 1;}
    	else if (XLeft>0 && YLeft<0) {XOptimal = 1; YOptimal = 1;}
    	else if (XLeft<0 && YLeft>0) {XOptimal = -1; YOptimal = -1;}


        // everything ok, so returns true
    	return un.unifies(new NumberTermImpl(XOptimal), args[2]) && un.unifies(new NumberTermImpl(YOptimal), args[3]);
    }
}
