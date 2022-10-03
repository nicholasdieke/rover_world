// Agent blank in project ia_submission

/* Initial beliefs and rules */

/* Initial goals */

/* Plans */

@start[atomic]
+start[source(s4_agent_scan)] : true <- ia_submission.getResourceLocations("Diamond", Locations);
					
					for (.member( Loc, Locations)){
						.nth(0, Loc, X);
						.nth(1, Loc, Y);
						.print("Finding path to diamond: ",X,",",Y);
						ia_submission.getPath(Y, X, Path);
						for (.member( Step, Path)){
						.nth(0, Step, XMove);
						.nth(1, Step, YMove);
						move(XMove, YMove);
						rover.ia.log_movement(XMove, YMove);
						}
						scan(1);
						?resource_found("Diamond",Qty,0,0)
						.print("Found ",Qty," diamond");
						!safe_collect("Diamond",Qty);
						.reverse(Path, ReversedPath);
						for (.member( Step, ReversedPath)){
							.nth(0, Step, XMove);
							.nth(1, Step, YMove);
							move(-XMove, -YMove);
							rover.ia.log_movement(-XMove, -YMove);						
						}
						!safe_deposit("Diamond",Qty);	
						-resource_found("Diamond",_,0,0);
						rover.ia.clear_movement_log;				
					}
					.print("Finished collecting Diamonds");
					.
					
					
+!safe_collect(ArtefactType, Qty) : true <- .print("I am collecting ",Qty," ", ArtefactType)
															rover.ia.check_config(Capacity,Scanrange,Resourcetype)

															 if (Qty >= Capacity) {
															 for ( .range(I,1,Capacity) ) {
															 		collect(ArtefactType)
															       }
															 } else {
															 	for ( .range(I,1,Qty) ) {
															 		collect(ArtefactType)
															       }													 
															 }.
															 
+!safe_deposit(ArtefactType, Qty): true <- .print("I am depositing ",Qty," ", ArtefactType);
															rover.ia.check_config(Capacity,Scanrange,Resourcetype)
															 if (Qty >= Capacity) {
															 for ( .range(I,1,Capacity) ) {
															 		deposit(ArtefactType);
															 		.abolish(resources_left(_));
															       +resources_left(Qty-Capacity);
															       }
															       
															 } else {
															 	for ( .range(I,1,Qty) ) {
															 		deposit(ArtefactType);
															 		.abolish(resources_left(_));
															       }															 
															 }.															 

+ obstructed(XTravelled, YTravelled, XLeft, YLeft) : true <- .print("There is someone in my way");
															rover.ia.log_movement(XTravelled,YTravelled);
															rover.ia.get_map_size(Width, Height);
															scan(2);
															?obstacles(Obs);
															.print("Obs: ",Obs);
															ia_submission.avoid_block(Obs, Width, Height, XOptimal, YOptimal);
															.print("Recommendation: ",XOptimal,", ",YOptimal);
															move(XOptimal, YOptimal);
															rover.ia.log_movement(XOptimal,YOptimal);
															!move_around.
					
//+ resource_not_found : true <- ! move_around.

+ insufficient_energy(Action) : true <- .print("I have run out of energy! I cannot ", Action).

//@resource_alert[atomic]
//+ resource_alert(ArtefactType, Qty, XDist, YDist)[source(s4_agent_scan)] : true <-
//															+resources_left(Qty);
//															while(resources_left(R_left) & R_left > 0){
//																.print("I am moving to ",R_left," ", ArtefactType);
//																 move(XDist, YDist);
//																 rover.ia.log_movement(XDist, YDist);																 
//																 !safe_collect(ArtefactType, R_left);
//																 rover.ia.get_distance_from_base(XFromHome, YFromHome);
//																 .print("I am returning to base");
//																 move(XFromHome, YFromHome);
//																 !safe_deposit(ArtefactType, R_left);
//																 rover.ia.clear_movement_log;
//															 }
//															 .abolish(resource_alert(ArtefactType, _ , XDist, YDist)[source(s2_agent_scan)]);
//															 .
