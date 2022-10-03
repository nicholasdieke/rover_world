// Agent blank in project ia_submission

/* Initial beliefs and rules */

/* Initial goals */

!start.

/* Plans */

+!start : true <- scan(6)
					!move_around.					

+!move_around : true <- .print("Starting gold hunt");
					move(10, 3);
					rover.ia.log_movement(10,3);
					scan(6);
					!move_around;
					.
					

-!move_around : obstructed(XTravelled, YTravelled, XLeft, YLeft) <- .print("Wait"); 
															rover.ia.log_movement(XTravelled,YTravelled);
															.wait(2000);
															!move_around;
															.
					
+ insufficient_energy(Action) : true <- .print("I have run out of energy! I cannot ", Action).
										
@resource_found[atomic]	
+ resource_found(ArtefactType, Qty, XDist, YDist) : true <- .print("I have found ", ArtefactType);
															 rover.ia.get_distance_from_base(XFromBase, YFromBase);
															 rover.ia.get_map_size(Width, Height);
															 ia_submission.quick_route(XDist-XFromBase, YDist-YFromBase, Width, Height, XOptimal, YOptimal)
															.send(s2_agent_collect, tell, resource_alert(ArtefactType, Qty, XOptimal, YOptimal));
															.

