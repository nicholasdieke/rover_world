// Agent blank in project ia_submission

/* Initial beliefs and rules */

last_move(0, 0).
type(0).
rotate(0).


/* Initial goals */

!move_around.

/* Plans */


+!move_around : true <- for ( .range(I,1,2) ) {
			 		.broadcast(tell,resource_found("Gold", 99, 1000, 0));
					.broadcast(tell,resource_found("Diamond", 99, 1000, 0));
			       }
 					.print("Starting gold hunt");
					?rotate(R);
					if(R == 0){
						move(-8, -2);
						rover.ia.log_movement(-8,-2);
					} elif(R == 1){
						move(8, 2);
						rover.ia.log_movement(8,2);
					}
					elif(R == 2){
						move(8, -2);
						rover.ia.log_movement(8,-2);
					}
					elif(R == 3){
						move(-8, 2);
						rover.ia.log_movement(-8,2);
					}
					
					-last_move(_,_)[source(self)];
					+last_move(0, 0)[source(self)];
					rover.ia.get_distance_from_base(XFromBase, YFromBase);
					rover.ia.get_map_size(Width, Height);
				 	ia_submission.quick_route(XFromBase, YFromBase, Width, Height, XOptimal, YOptimal);
				 	.print("My distance from base: ",XOptimal,",",YOptimal);
					scan(4);
					!move_around;
					.
					
-!move_around : obstructed(XTravelled, YTravelled, XLeft, YLeft)[source(percept)] <- .print("Obstructed");
																	+stuck[source(self)];
																	-obstructed(_,_,_,_)[source(percept)];
																	rover.ia.log_movement(XTravelled,YTravelled);
																	rover.ia.get_distance_from_base(Xdist,Ydist)
																	!avoid(XTravelled, YTravelled, XLeft, YLeft);
																	.																
																	
+! avoid(Xt, Yt, Xl, Yl): true <- .print("Avoidance plan: Started");
									.print("Travelled: ",Xt, ",",Yt);
									.print("Left: ",Xl, ",",Yl);
									scan(1);
									.findall(ob(X,Y) , resource_found("Obstacle",_,X,Y)[source(percept)], L);
									?last_move(LastX, LastY);
									ia_submission.avoid_block(L, LastX, LastY, XOptimal, YOptimal);
									move(XOptimal, YOptimal);
									rover.ia.log_movement(XOptimal,YOptimal);
									-last_move(_,_)[source(self)];
									+last_move(XOptimal, YOptimal)[source(self)];
									-stuck[source(self)];
									-resource_found(_, _, _, _)[source(percept)]
									!move_around;
									.
									
-! avoid(Xt, Yt, Xl, Yl): true <- .print("Stuck");.
					
+ insufficient_energy(Action)[source(percept)] : true <- .print("I have run out of energy! I cannot ", Action);
										.
										

@resource_found[atomic]															
+ resource_found(ArtefactType, Qty, XDist, YDist)[source(percept)] : true <- if(not (ArtefactType == "Obstacle")){
																?type(Type);
																if (Type == 0) {
																	-type(0)[source(self)];
																	+type(ArtefactType)[source(self)];
																	if (ArtefactType == "Gold") {
																		.send(s6_agent, tell, define_type("Diamond"));
																	}
																	elif (ArtefactType == "Diamond") {
																		.send(s6_agent, tell, define_type("Gold"));
																	}
																	!resource_plan(ArtefactType, Qty, XDist, YDist);
																} elif (Type == ArtefactType){
																	!resource_plan(ArtefactType, Qty, XDist, YDist);
																}
																
																 }
																 .
																 
+!resource_plan(ArtefactType, Qty, XDist, YDist) : true <-.print("I have found ", Qty, " ",ArtefactType, " at XDist: ", XDist," and YDist: ",YDist);
															 	move(XDist, YDist)
															 	rover.ia.log_movement(XDist,YDist);
															 	-last_move(_,_)[source(self)];
																+last_move(XDist, YDist)[source(self)];
																!safe_collect(ArtefactType, Qty)
																rover.ia.get_distance_from_base(XFromBase, YFromBase);
																rover.ia.get_map_size(Width, Height);
															 	ia_submission.quick_route(XFromBase, YFromBase, Width, Height, XOptimal, YOptimal);
															 	.print("My distance from base: ",XOptimal,",",YOptimal);
																 .print("I am returning to base");
																 move(XOptimal, YOptimal);
																 !safe_deposit(ArtefactType, Qty);
																 rover.ia.clear_movement_log;
														.
																 
-!resource_plan(ArtefactType, Qty, XDist, YDist) : obstructed(XTravelled, YTravelled, XLeft, YLeft)[source(percept)] <- .print("Obstructed on the way back");
																	rover.ia.log_movement(XTravelled,YTravelled);
																	-obstructed(_,_,_,_)[source(percept)];
																	!get_home(XTravelled, YTravelled, XLeft, YLeft);
																	.

@get_home(XTravelled, YTravelled, XLeft, YLeft)[atomic]																
+!get_home(XTravelled, YTravelled, XLeft, YLeft) : true <- .print("Starting way back");
															+stuck[source(self)];
															scan(2);
															.findall(ob(X,Y) , resource_found("Obstacle",_,X,Y), L);
															?last_move(LastX, LastY);
															ia_submission.find_home(L,XLeft, YLeft, LastX, LastY, XAvoid, YAvoid);
															.print("Should move: ",XAvoid,",",YAvoid);
															if(XAvoid == -1 & YAvoid == -1){
																.print("Move the rest: ",XLeft,",",YLeft);
																move(-1, -1);
																rover.ia.log_movement(-1,-1);	
																move(XLeft+1, YLeft+1);
																rover.ia.log_movement(XLeft+1,YLeft+1);	
																.print("Deposit after backtrack");
																!safe_deposit(ArtefactType, Qty);
																rover.ia.clear_movement_log;
															} else {
																move(XAvoid, YAvoid);
																rover.ia.log_movement(XAvoid,YAvoid);
																-last_move(_,_)[source(self)];
																+last_move(XAvoid, YAvoid)[source(self)];
																!get_home(XAvoid, YAvoid, XLeft-XAvoid, YLeft-YAvoid);
															}
															.
															
+ define_type(ArtefactType)[source(s6_agent)] : true <- -type(0)[source(self)];
														+type(ArtefactType)[source(self)];.

//@safe_collect[atomic]																
+!safe_collect(ArtefactType, Qty) : true <- .print("I am collecting ", ArtefactType)
															 if (Qty >= 2) {
															 for ( .range(I,1,2) ) {
															 		collect(ArtefactType)
															       }
															 } else {
															 	for ( .range(I,1,Qty) ) {
															 		collect(ArtefactType)
															       }															 
															 }
															 .print("I have finished collecting ");
															 -carrying(_)[source(self)];
															 +carrying(1)[source(self)];
															 .

-!safe_collect(ArtefactType, Qty) : true <- .print("Resource is already gone").															 
															 
//@safe_deposit[atomic]																														 
+!safe_deposit(ArtefactType, Qty): true <- .print("I am depositing ", ArtefactType)
															 if (Qty >= 2) {
															 for ( .range(I,1,2) ) {
															 		deposit(ArtefactType);
															       }
															 } else {
															 	for ( .range(I,1,Qty) ) {
															 		deposit(ArtefactType);
															       }															 
															 }
															 -carrying(_)[source(self)];
															 +carrying(0)[source(self)];
															 ?rotate(R);
															 -rotate(_)[source(self)];
															 if (R == 3){
															 	+rotate(0)[source(self)];
															 } else {
															 +rotate(R+1)[source(self)];
															 }
															 .	

