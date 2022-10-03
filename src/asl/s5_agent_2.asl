// Agent s5_agent in project ia_submission

/* Initial beliefs and rules */

last_move(0,0).
carrying("Gold",0).

/* Initial goals */

/* Plans */

+start : true <- ia_submission.restartMaze(X);
					!move_around;
					.

//@update_map(Type, Qty, XDist, YDist)[atomic]					
//+!update_map(Type, Qty, XDist, YDist) : true <- rover.ia.get_distance_from_base(XFromBase, YFromBase);
//												 rover.ia.get_map_size(Width, Height);
//									          ia_submission.getPosForMazeArray(XDist-XFromBase, YDist-YFromBase, Width, Height, XOptimal, YOptimal)																
//											  ia_submission.update_map(Type, XOptimal, YOptimal); 
////										        if(not stuck) {-resource_found(Type, Qty, XDist, YDist)[source(percept)]};
//												.	

//@move_around[atomic]
+!move_around : true <- .print("I am going to explore.");
						scan(1);
						.print("I have scanned")
						?last_move(LastX, LastY)
						?carrying(ArtefactType, Qty);
						rover.ia.get_distance_from_base(XFromBase, YFromBase);
						 rover.ia.get_map_size(Width, Height);
						 ia_submission.s5_map_pos(-XFromBase, -YFromBase, Width, Height, CurrentX, CurrentY);
						 ia_submission.s5_map_pos(-10+XFromBase, 29+YFromBase, Width, Height, EndX, EndY);
						.findall(ob(X,Y) , resource_found("Obstacle",_,X,Y), L);
						ia_submission.maze_route(L, LastX, LastY, Qty, CurrentX, CurrentY, EndX, EndY, XOptimal, YOptimal);
						ia_submission.update_visited(CurrentX, CurrentY);
						.print("Distance to end: ",EndX,",",EndY);
						if (EndX==0 & EndY==0){
							.print("END REACHED");
							!safe_deposit(ArtefactType, Qty);
//							!update_map("End", 0, 0, 0);
//							.send(s5_agent_2, tell, start);
						} else{
						
							if (XOptimal == -1 & YOptimal == -1){
								.print("Starting backtrack");
								!backtrack;
							} else {
								.print("Looking for next move");
								move(XOptimal, YOptimal);
								rover.ia.log_movement(XOptimal,YOptimal);
								.print("Moved X: ",XOptimal, " and Y: ", YOptimal);
							}
							
							-last_move(_,_);
							+last_move(XOptimal, YOptimal);
							-obstacles(_);
							!move_around
						}.

@backtrack[atomic]						
+! backtrack : true <- ia_submission.backtrack(Path);
							for (.member( Step, Path)){
								.nth(0, Step, XMove);
								.nth(1, Step, YMove);
								.print("Backtrack: ",XMove,",",YMove);
								move(XMove, YMove);
								rover.ia.log_movement(XMove, YMove);
							}.
					
														 
					
+ insufficient_energy(Action) : true <- .print("I have run out of energy! I cannot ", Action).

+ obstructed(XTravelled, YTravelled, XLeft, YLeft) : true <- .print("There is someone in my way")
																!move_around.					
					
+ resource_not_found : true <- ! move_around.

@resource_found(ArtefactType, Qty, Xdist, Ydist)[atomic]
+ resource_found(ArtefactType, Qty, Xdist, Ydist) : true <-  ?carrying(ArtefactType1, Qty1);
															
//																!update_map(ArtefactType, Qty, Xdist, Ydist);

														if(ArtefactType == "Obstacle") {
																.findall(ob(X,Y) , resource_found("Obstacle",_,X,Y), L);
																+obstacles(L);
															}
															elif(Qty1 == 0) {
																.print("I have found ", Qty, " ",ArtefactType, " at XDist: ", Xdist," and YDist: ",Ydist);
															 	move(Xdist, Ydist)
															 	rover.ia.log_movement(Xdist,Ydist);
															 	-last_move(_,_);
																+last_move(Xdist, Ydist);
																!safe_collect(ArtefactType, Qty);
																.findall(ob(X,Y) , resource_found("Obstacle",_,X,Y), L);
																+obstacles(L);
															}
															.
															
+!safe_collect(ArtefactType, Qty) : true <- .print("I am collecting ", ArtefactType)
															 if (Qty >= 5) {
															 for ( .range(I,1,5) ) {
															 		collect(ArtefactType)
															       }
															 } else {
															 	for ( .range(I,1,Qty) ) {
															 		collect(ArtefactType)
															       }															 
															 }
															 .print("I have finished collecting ");
															 -carrying(_,_);
															 +carrying(ArtefactType, Qty);
															 .
															 
+!safe_deposit(ArtefactType, Qty): true <- .print("I am depositing ", ArtefactType)
															 if (Qty >= 5) {
															 for ( .range(I,1,5) ) {
															 		deposit(ArtefactType);
															       }
															 } else {
															 	for ( .range(I,1,Qty) ) {
															 		deposit(ArtefactType);
															       }															 
															 }
															 -carrying(_,_);
															 +carrying("Gold",0);
															 .

