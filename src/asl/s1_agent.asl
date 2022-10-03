// Agent blank in project ia_submission

/* Initial beliefs and rules */

/* Initial goals */

!move_around.

/* Plans */
					

+!move_around : true <- .print("I am going to explore.");
					move(4, 4);
					rover.ia.log_movement(4,4);
					scan(3).
					
+!safe_collect(ArtefactType, Qty) : true <- .print("I am collecting ", ArtefactType)
															 if (Qty >= 3) {
															 for ( .range(I,1,3) ) {
															 		collect("Gold")
															       }
															 } else {
															 	for ( .range(I,1,Qty) ) {
															 		collect("Gold")
															       }															 
															 }.
															 
+!safe_deposit(ArtefactType, Qty): true <- .print("I am depositing ", ArtefactType)
															 if (Qty >= 3) {
															 for ( .range(I,1,3) ) {
															 		deposit("Gold");
															       }
															 } else {
															 	for ( .range(I,1,Qty) ) {
															 		deposit("Gold");
															       }															 
															 }.															 
					
+ resource_not_found : true <- ! move_around.



+ resource_found(ArtefactType, Qty, Xdist, Ydist) : true <- .print("I am moving to a ", ArtefactType);
															 move(Xdist, Ydist)
															 rover.ia.log_movement(Xdist, Ydist)
															 !safe_collect(ArtefactType, Qty)
															 
															 rover.ia.get_distance_from_base(XFromBase, YFromBase);
															 .print("I am returning to base")
															 move(XFromBase, YFromBase);
															 !safe_deposit(ArtefactType, Qty)
															 rover.ia.clear_movement_log;
															 !move_around;
															 .
