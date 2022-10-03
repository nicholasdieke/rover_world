// Agent blank in project ia_submission

/* Initial beliefs and rules */

/* Initial goals */

!move_around.

/* Plans */

+!move_around : true <- .print("Starting gold hunt");
					scan(6)
					move(10, 3);
					rover.ia.log_movement(10,3);
					rover.ia.get_distance_from_base(XFromBase, YFromBase);
					.print("My distance from base: ",XFromBase,",",YFromBase);
					!move_around
					.
					

-!move_around : obstructed(XTravelled, YTravelled, XLeft, YLeft) <- .print("Wait"); 
															rover.ia.log_movement(XTravelled,YTravelled);
															.wait(2000);
															!move_around;
															.	
//@avoid(XTravelled, YTravelled, XLeft, YLeft)[atomic]														
+! avoid(XTravelled, YTravelled, XLeft, YLeft) : true <- .print("There is someone in my way");
					rover.ia.get_distance_from_base(XFromBase, YFromBase);
					.print("Before: ",XFromBase,",",YFromBase);
					.print("Log: ",XTravelled,",",YTravelled," Left: ",XLeft,",",YLeft);
					rover.ia.log_movement(XTravelled,YTravelled);
					rover.ia.get_distance_from_base(XFromBase2, YFromBase2);
					.print("Before: ",XFromBase2,",",YFromBase2);
					.random(X)
					.random(Y)
					.print("Random movement: ",math.round(X),",",math.round(Y));
					move(math.round(X), math.round(Y))
					rover.ia.log_movement(math.round(X), math.round(Y));
					!move_around
					.
					
+ insufficient_energy(Action) : true <- .print("I have run out of energy! I cannot ", Action).


@resource_found[atomic]
+ resource_found(ArtefactType, Qty, XDist, YDist) : true <- .print("I have found ", ArtefactType);
															 rover.ia.get_distance_from_base(XFromBase, YFromBase);
															 .print("My distance from base: ",XFromBase,",",YFromBase);
															 rover.ia.get_map_size(Width, Height);
															 .print("Map size: ",Width,",",Height);
															 .print("Distance to resource: ",XDist,",",YDist);
															 ia_submission.quick_route(XDist-XFromBase, YDist-YFromBase, Width, Height, XOptimal, YOptimal)
															if (ArtefactType == "Gold") {
																.send(s3_agent_collect_gold, tell, resource_alert(ArtefactType, Qty, XOptimal, YOptimal));
															}
															elif (ArtefactType == "Diamond") {
																.send(s3_agent_collect_diamond, tell, resource_alert(ArtefactType, Qty, XOptimal, YOptimal));
															}
															.

