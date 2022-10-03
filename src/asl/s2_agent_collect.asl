// Agent blank in project ia_submission

/* Initial beliefs and rules */

/* Initial goals */

/* Plans */
					
					
+!safe_collect(ArtefactType, Qty) : true <- .print("I am collecting ",Qty," ", ArtefactType);
															rover.ia.check_config(Capacity,Scanrange,Resourcetype);

															 if (Qty >= Capacity) {
															 for ( .range(I,1,Capacity) ) {
															 		collect(ArtefactType);
															       }
															 } else {
															 	for ( .range(I,1,Qty) ) {
															 		collect(ArtefactType);
															       }													 
															 }.
															 
+!safe_deposit(ArtefactType, Qty): true <- .print("I am depositing ",Qty," ", ArtefactType);
															rover.ia.check_config(Capacity,Scanrange,Resourcetype)
															 if (Qty >= Capacity) {
															 for ( .range(I,1,Capacity) ) {
															 		deposit(ArtefactType);
															 		-resources_left(_);
															       +resources_left(Qty-Capacity);
															       }
															       
															 } else {
															 	for ( .range(I,1,Qty) ) {
															 		deposit(ArtefactType);
															 		-resources_left(_);
															       }															 
															 }.															 

					
+ resource_not_found : true <- ! move_around.

+ insufficient_energy(Action) : true <- .print("I have run out of energy! I cannot ", Action).

@resource_alert[atomic]
+ resource_alert(ArtefactType, Qty, XDist, YDist) : true <- .count(resource_alert(ArtefactType,_, XDist,YDist),N);
															if (N == 1){
																!resource_plan(ArtefactType, Qty, XDist, YDist);
															}.
										
														
+!resource_plan(ArtefactType, Qty, XDist, YDist) : true <- -resources_left(_);
															+resources_left(Qty);
															while(resources_left(R_left) & R_left > 0){
																.print("I am moving to ",R_left," ", ArtefactType," at ",XDist,",",YDist);
																 move(XDist, YDist);
																 rover.ia.log_movement(XDist, YDist);																 
																 !safe_collect(ArtefactType, R_left);
																 rover.ia.get_distance_from_base(XFromHome, YFromHome);
																 .print("I am returning to base");
																 move(XFromHome, YFromHome);
																 !safe_deposit(ArtefactType, R_left);
																 rover.ia.clear_movement_log;
															 }
															 .abolish(resource_alert(ArtefactType, _ , XDist, YDist));
															 .
															 
-!resource_plan(ArtefactType, Qty, XDist, YDist) : obstructed(XTravelled, YTravelled, XLeft, YLeft) <- .print("Obstructed");
																	rover.ia.log_movement(XTravelled,YTravelled);
																	-obstructed(_,_,_,_)[source(percept)];
																	?resources_left(R_left);
																	 !avoid(XTravelled, YTravelled, XLeft, YLeft, ArtefactType, R_left, XDist, YDist);
																	.
	
@avoid(XTravelled, YTravelled, XLeft, YLeft, Type, R_left, XDist, YDist)[atomic]														
+! avoid(XTravelled, YTravelled, XLeft, YLeft, Type, R_left, XDist, YDist) : true <- .print("There is someone in my way");
					ia_submission.get_obstructed_direction(XLeft, YLeft, XMove, YMove);
					.print("Smart Movement: ",XMove,",",YMove);
					move(XMove, YMove);
					rover.ia.log_movement(XMove, YMove);
					if(not carrying){
						.print("Enter not carrrying");
						move(XLeft-XMove, YLeft-YMove);
						 rover.ia.log_movement(XLeft-XMove, YMove);																 
						 !safe_collect(Type, R_left);
						 +carrying;
					}
					rover.ia.get_distance_from_base(XFromHome, YFromHome);
					.print("Moving back home");
					move(XFromHome, YFromHome);
					 !safe_deposit(Type, R_left);
					 -carrying;
					 rover.ia.clear_movement_log;
					.abolish(resource_alert(Type, _ , XDist, YDist)[source(s2_agent_scan)]);
					.												
