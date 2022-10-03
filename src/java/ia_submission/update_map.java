// Internal action code for project ia_submission

package ia_submission;

import java.util.Arrays;

import jason.*;
import jason.asSemantics.*;
import jason.asSyntax.*;

public class update_map extends DefaultInternalAction {

    @Override
    public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception {
        // execute the internal action
//        ts.getAg().getLogger().info("executing internal action 'ia_submission.update_map'");
//        if (true) { // just to show how to throw another kind of exception
//            throw new JasonException("not implemented!");
//        }
        String resourceType = ((StringTerm) args[0]).toString();
    	int x = (int) ((NumberTerm) args[1]).solve();
    	int y = (int) ((NumberTerm) args[2]).solve();
    	
    	if (!resourceType.contains("End")) {
    		int r = resourceType.contains("Obstacle") ? 1 : resourceType.contains("Gold")? 2 : 3;
        	
        	
        	Map.setResourceOnMap(r, x, y);
    	}
    	
    	
    	    	
//    	System.out.println(Arrays.deepToString(Map.getMapArray()));

        // everything ok, so returns true
        return true;
    }
}
