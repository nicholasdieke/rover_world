// Internal action code for project ia_submission

package ia_submission;

import jason.*;
import jason.asSemantics.*;
import jason.asSyntax.*;

public class getPosForArray extends DefaultInternalAction {

    @Override
    public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception {
    	    	
    	int XDist = (int) ((NumberTerm) args[0]).solve();
    	int YDist = (int) ((NumberTerm) args[1]).solve();
    	int Width = (int) ((NumberTerm) args[2]).solve();
    	int Height = (int) ((NumberTerm) args[3]).solve();
    	
    	int XOptimal = XDist%Width;
    	int YOptimal = YDist%Height;
    	    	
    	if(XOptimal>Width/2) XOptimal = XOptimal-Width;
    	else if(XOptimal<-(Width/2)) XOptimal = XOptimal+Width;
    	if(YOptimal>Height/2) YOptimal = YOptimal-Height;
    	else if(YOptimal<-(Height/2)) YOptimal = YOptimal+Height;
    	
    	XOptimal += (Width/2);
    	YOptimal += (Height/2);
    	
    	if(XOptimal==Width) XOptimal = 0;
    	if(YOptimal==Height) YOptimal = 0;
    	
    	return un.unifies(new NumberTermImpl(XOptimal), args[4]) && un.unifies(new NumberTermImpl(YOptimal), args[5]);
    	
        // execute the internal action
//        ts.getAg().getLogger().info("executing internal action 'ia_submission.random_number'");
//        if (true) { // just to show how to throw another kind of exception
//            throw new JasonException("not implemented!");
//        }

        // everything ok, so returns true
//        return true;
    }
}
