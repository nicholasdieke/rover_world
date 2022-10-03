// Agent blank in project ia_submission

/* Initial beliefs and rules */

last_move(0, 0).

/* Initial goals */

!start.

/* Plans */

+!start : true <- rover.ia.get_map_size(Width, Height);
					ia_submission.initialise_map(Width, Height);
					scan(6);
					!move_around.					

+!move_around : true <- .print("Starting gold hunt");
					move(10, 3);
					rover.ia.log_movement(10,3);
					-last_move(_,_);
					+last_move(0, 0);
					scan(6);
					!move_around;
					.
					
-!move_around : obstructed(XTravelled, YTravelled, XLeft, YLeft) <- .print("Obstructed");
																	+stuck;
																	-obstructed(_,_,_,_)[source(percept)];
																	rover.ia.log_movement(10-XLeft,3-YLeft);
																	rover.ia.get_distance_from_base(Xdist,Ydist)
																	!avoid(Xt, Yt, Xl, Yl);
																	.
+!update_map(Type, Qty, XDist, YDist) : true <- rover.ia.get_distance_from_base(XFromBase, YFromBase);
												 rover.ia.get_map_size(Width, Height);
									          ia_submission.getPosForArray(XDist-XFromBase, YDist-YFromBase, Width, Height, XOptimal, YOptimal)																
											  ia_submission.update_map(Type, XOptimal, YOptimal); 
										        if(not stuck) {-resource_found(Type, Qty, XDist, YDist)[source(percept)]};
												.																	
																	
+! avoid(Xt, Yt, Xl, Yl): true <- .print("Avoidance plan: Started");
									scan(1);
									.findall(ob(X,Y) , resource_found("Obstacle",_,X,Y), L);
									?last_move(LastX, LastY);
									ia_submission.avoid_block(L, LastX, LastY, XOptimal, YOptimal);
									move(XOptimal, YOptimal);
									rover.ia.log_movement(XOptimal,YOptimal);
									-last_move(_,_);
									+last_move(XOptimal, YOptimal);
									-stuck;
									-resource_found(_, _, _, _)[source(percept)]
									!move_around;
									.
									
-! avoid(Xt, Yt, Xl, Yl): true <- .print("Stuck");
										rover.ia.log_movement(Xt,Yt);
										+done;
										!update_map("Obstacle", 1, 0, 0);
										if (other_is_done) {.send(s4_agent_collect_gold, tell, start);}.
					
+ insufficient_energy(Action) : true <- .print("I have run out of energy! I cannot ", Action);
										+done;
										!update_map("Obstacle", 1, 0, 0);
										if (other_is_done) {.send(s4_agent_collect_gold, tell, start);}
										.
										
+ agent_done : true <- +other_is_done;
					if (done) {.send(s4_agent_collect_gold, tell, start);}.
										
+ getDiamond[source(s4_agent_collect_gold)] : true <- .send(s4_agent_collect_diamond, tell, start);
						.
+ resource_found(ArtefactType, Qty, XDist, YDist) : true <- !update_map(ArtefactType, Qty, XDist, YDist);
															.

