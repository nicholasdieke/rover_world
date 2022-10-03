// Internal action code for project ia_submission

package ia_submission;

import jason.*;
import jason.asSemantics.*;
import jason.asSyntax.*;

public class backtrack extends DefaultInternalAction {

    @Override
    public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception {
        // execute the internal action
//        ts.getAg().getLogger().info("executing internal action 'ia_submission.backtrack'");
//        if (true) { // just to show how to throw another kind of exception
//            throw new JasonException("not implemented!");
//        }

    	int[][] locs = Maze.getBacktrackingPath();

//    	Converting to ListTerm	
		ListTerm pathTerm = new ListTermImpl();

		for (int i =0; i < locs.length; i++) {
		    ListTerm row = new ListTermImpl();
		    for (int j=0; j<2; j++) {
		         row.add(new NumberTermImpl(locs[i][j]));
		   }
		    pathTerm.add(row);
		}

        // everything ok, so returns true
        return un.unifies(pathTerm, args[0]);
    }
}
