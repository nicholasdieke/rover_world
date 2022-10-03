// Internal action code for project ia_submission

package ia_submission;

import jason.*;
import jason.asSemantics.*;
import jason.asSyntax.*;

public class getPath extends DefaultInternalAction {

    @Override
    public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception {
        // execute the internal action
//        ts.getAg().getLogger().info("executing internal action 'ia_submission.getPaths'");
//        if (true) { // just to show how to throw another kind of exception
//            throw new JasonException("not implemented!");
//        }
    	
    	int x = (int) ((NumberTerm) args[0]).solve();
    	int y = (int) ((NumberTerm) args[1]).solve();
    	
    	int[][] path = Map.findPath(new Point (Map.GRID_HEIGHT/2, Map.GRID_WIDTH/2),new Point(x,y));
    		
//    	Converting to ListTerm	
		ListTerm pathTerm = new ListTermImpl();

		for (int i =0; i < path.length; i++) {
		    ListTerm row = new ListTermImpl();
		    for (int j=0; j<2; j++) {
		         row.add(new NumberTermImpl(path[i][j]));
		   }
		    pathTerm.add(row);
		}
    	
        // everything ok, so returns true
    		return un.unifies(pathTerm, args[2]);
    }
}
