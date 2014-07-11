package info.remenska.PASS.wizards;



public class QuestionnaireBackup {
	
	public static TreeNode<String> scopeQuestionTree = new TreeNode<String>("Is the behavior only required to hold within a restricted interval(s) in the event sequence?                                    \t\t                                          ", true);
	public static TreeNode<String> behaviorQuestionTree = new TreeNode<String>("How many events of primary interest are there in this behavior?", true);
	public static 	TreeNode<String> answ11 = scopeQuestionTree.addChild("Yes, the behavior is only required to hold within restricted interval(s) in the event sequence. ", false);
	public static 	TreeNode<String> quest111 = answ11.addChild("Which of the following choices best describes the restricted interval(s)?", true);
	public static 		TreeNode<String> answ1111 = quest111.addChild("There can be at most one restricted interval in the event sequence and it has a \n starting delimiter, START: the behavior is required to hold from an occurence of START \n through to the end of the event sequence. ", false);
	public static 			TreeNode<String> quest11111 = answ1111.addChild("What if there are multiple occurences of START before the end of the event sequence?", true);
	public static 				TreeNode<String> answ111111 = quest11111.addChild("Only the first occurence of START starts the restricted interval; later occurences of START do not have an effect.", false); // AFTER-Q
	public static 				TreeNode<String> answ111112 = quest11111.addChild("Only the last occurence of START starts the restricted interval; each occurence of START resets the beginning of a restricted interval. ", false); //AFTER-Q variant (ASK if possible)
	public static 		TreeNode<String> answ1112 = quest111.addChild("There can be at most one restricted interval in the event sequence and it has an ending \n delimiter, END: the behavior is required to hold from the start of the event sequence \n through to the first occurence of END. ", false);
	public static 			TreeNode<String> quest11121 = answ1112.addChild("If END does not occur, is the behavior still required to hold, until the end of the event sequence?", true);
	public static 				TreeNode<String> answ111211 = quest11121.addChild("Yes, if END does not occur, the behavior is required to hold throughout the entire event sequence.", false); // BEFORE-R variant
	public static 				TreeNode<String> answ111212 = quest11121.addChild("No, if END does not occur, the behavior not required to hold anywhere in the event sequence.", false); // BEFORE-R

	public static 		TreeNode<String> answ1113 = quest111.addChild("A restricted interval in the event sequence can have both a starting delimiter, START, and an \n ending delimiter, END. The behavior is required to hold from an occurence of START \n through to the end of that restricted interval. ", false);
	public static 			TreeNode<String> quest11131 = answ1113.addChild("What happens if there are multiple occurences of START without an occurence of END in between them?", true);
	public static 				TreeNode<String> answ11311 = quest11131.addChild("Only the first of those occurences of START potentially starts a restricted interval; later occurences of START within that restricted interval do not have an effect.", false);
	public static 				TreeNode<String> answ11312 = quest11131.addChild("Only the last of those occurences of START potentially starts a restricted interval; each of those occurences of START resets the beginning of that restricted interval. ", false);
	public static 			TreeNode<String> quest11132 = answ1113.addChild("If an occurence of START is not followed by an occurence of END, is the behavior still required to hold, until the end of the event sequence?", true);
			public static 				TreeNode<String> answ111321 = quest11132.addChild("Yes, if END does not occur subsequently, then the behavior is required to hold until the end of the event sequence.", false); // AFTER-Q-UNTILL-R 
			public static 				TreeNode<String> answ111322 = quest11132.addChild("No, if END does not occur subsequently, then the behavior is not required to hold for the remainder of the event sequence.", false); // BETWEEN-Q-AND-R
			public static 			TreeNode<String> quest11133 =  answ1113.addChild("What happens if START occurs after the end of a restricted interval?", true); // stupid question, should be removed.
			public static 				TreeNode<String> answ111331 = quest11133.addChild("That new occurence of START would potentially start a new restricted interval. The situation would thus be the same \n as after the first occurence of START, meaning that the restrictions described on any \n events that take place after that first occurence would again apply.", false); // STUPID QUESTION, should be removed
			public static 				TreeNode<String> answ111332 = quest11133.addChild("The new occurence of START does not have an effect; no new occurence of START will ever start a new restricted interval. ", false); // STUPID QUESTION, should be removed
			public static 		TreeNode<String> quest112 = answ11.addChild("Are there any exceptional events, such that if any of them occurs, the property behavior \n may not be required to hold? ", true); // STUPID QUESTION, should be removed
			public static 			TreeNode<String> answ1121 = quest112.addChild("No.", false); // SEE IF POSSIBLE TO IMPLEMENT
			public static 			TreeNode<String> answ1122 = quest112.addChild("Yes, if an exceptional event X occurs, the property behavior \n may not be required to hold", false);
			public static 				TreeNode<String> quest11221 = answ1122.addChild("Please select one of the following options related to the scope:", true);
			public static 					TreeNode<String> answ112211 = quest11221.addChild("If the exceptional event occurs outside the designated scope, it will have no effect. It only matters \n when the exceptional event occurs inside the scope.", false);
			public static 					TreeNode<String> answ112212 = quest11221.addChild("No matter where the exceptional event occurs, outside or inside the designated scope, it may have an effect", false);
		
			public static TreeNode<String> answ12 = scopeQuestionTree.addChild("No, the behavior is required to hold throughout the entire event sequence", false); // GLOBALLY
			public static 	TreeNode<String> quest121 = answ12.addChild("Are there any exceptional events such that if any of them occurs the property behavior may not be required to hold?", true);	
			public static 		TreeNode<String> answ1211 = quest121.addChild("No, none.", false);
			public static 		TreeNode<String> answ1212 = quest121.addChild("Yes, if exceptional event X occurs, the property behavior may not be required to hold.", false);
		    
//////
			public static TreeNode<String> aansw11 = behaviorQuestionTree.addChild("One event.", false);
			public static 	TreeNode<String> qquest111 = aansw11.addChild("Which of the following choices best describes the restriction on A?", true);
			public static 		TreeNode<String> aansw1111 = qquest111.addChild("A is never allowed to occur", false); // ABSENCE
			public static 			TreeNode<String> qquest1111 = aansw1111.addChild("Are there any exceptional events, such that if any of them occurs - the property \n behavior may not be required to hold?", true);
			public static 				TreeNode<String> aansw11111 = qquest1111.addChild("No, there is none.", false);
			public static 				TreeNode<String> aansw11112 = qquest1111.addChild("Yes, if an exceptional event X occurs when A has not yet occured, the behavior is not required to hold.", false);
			public static 					TreeNode<String> qquest111121 = aansw11112.addChild("What if the exceptional event occurs after a behavior violation has been found (A has occured)?", true);
			public static 						TreeNode<String> aansw1111211 = qquest111121.addChild("In such a case, the event sequence is not acceptable.", false);
			public static 						TreeNode<String> aansw1111212 = qquest111121.addChild("Even in that case, the event sequence is still considered acceptable", false);
			public static 		TreeNode<String> aansw1112 = qquest111.addChild("A is allowed to occur at least once.", false);	// EXISTENCE			
			public static 			TreeNode<String> qquest11121 = aansw1112.addChild("Are there any exceptional events, such that if any of them occurs, \n the property behavior may not be required to hold?", true);
			public static 				TreeNode<String> aansw111211 = qquest11121.addChild("No.", false);
			public static 				TreeNode<String> aansw111212 = qquest11121.addChild("Yes, if exceptional event X occurs, A is not required to occur.", false);
		
			public static 		TreeNode<String> aansw1113 = qquest111.addChild("A is allowed to occur exactly once.", false); // stupid answer, UNIVERSALITY MISSING!
			public static 			TreeNode<String> qquest11131 = aansw1113.addChild("Is there any exceptional event, such that if it occurs, the property behavior may not be required to hold?", true);
			public static 				TreeNode<String> aansw111311 = qquest11131.addChild("No, none.", false);
			public static 				TreeNode<String> aansw111312 = qquest11131.addChild("Yes, if exceptional event X occurs when A has not yet occured,\n the behavior is not required to hold. ", false);
			public static 					TreeNode<String> qquest1113121 = aansw111312.addChild("What if the exceptional event occurs after the violation has been found (A has occured more then once)?", true);
			public static 						TreeNode<String> aansw11121211 = qquest1113121.addChild("In such case, the event sequence is not acceptable.", false);
			public static 						TreeNode<String> aansw11121212 = qquest1113121.addChild("Even in such case, the event sequence is still considered acceptable.", false);
			public static 					TreeNode<String> qquest1113122 = aansw111312.addChild("What if the exceptional event occurs even \n while the behavioral requirement has not been fulfilled (i.e., A has not yet occured)?", true);
			public static 						TreeNode<String> aansw11131221 = qquest1113122.addChild("In that case, the behavior is not required to hold.", false);
			public static 					TreeNode<String> aansw11131222 = qquest1113122.addChild("In that case, the behavior is still required to hold", false);
					
			public static TreeNode<String> aansw12 = behaviorQuestionTree.addChild("Two events.", false);
			public static 	TreeNode<String> qquest121 = aansw12.addChild("Which of the following best describes how A and B interact?", true);
			public static 		TreeNode<String> aansw1211 = qquest121.addChild("If A occurs, B is required to occur subsequently", false);
			public static 			TreeNode<String> qquest12111 = aansw1211.addChild("Is A required to occur?", true);
			public static 				TreeNode<String> aansw121111 = qquest12111.addChild("Yes, A is required to occur.", false); // can it be implemented?
			public static 				TreeNode<String> aansw121112 = qquest12111.addChild("No, A is not required to occur.", false); // RESPONSE
			public static 		TreeNode<String> aansw1212 = qquest121.addChild("B is not allowed to occur until after A occurs.", false); // PRECEDENCE 
			public static 			TreeNode<String> qquest12121 = aansw1212.addChild("Is A required to occur, whether or not B eventually occurs?", true);
			public static 				TreeNode<String> aansw121211 = qquest12121.addChild("Yes, A is required to occur, whether or not B eventually occurs.", false); // can it be implemented by a conjunction of the original formula AND formula of A must happen eventually 
			public static 				TreeNode<String> aansw121212 = qquest12121.addChild("No, A is not required to occur and, if it does not occur, B is never allowed to occur.", false); // PRECEDENCE 
			public static 		TreeNode<String> aansw1213 = qquest121.addChild("Both statements describe how A and B interact: if A occurs, B is required to occur subsequently, and B is not allowed to occur until after A occurs.", false); // stupid answer, should be removed
			public static 			TreeNode<String> qquest12131 = aansw1213.addChild("Is event A required to occur? ", true);
			public static 				TreeNode<String> aansw121311 = qquest12131.addChild("Yes, event A is required to occur.", false);
			public static 				TreeNode<String> aansw121312 = qquest12131.addChild("No, event A is not required to occur.", false);
	
			public static 	TreeNode<String> qquest122 = aansw12.addChild("After A occurs, is A allowed to occur again before the first subsequent B occurs?", true);
			public static 	TreeNode<String> aansw1221 = qquest122.addChild("Yes, A is allowed to occur again, zero or more times, before the first subsequent B occurs", false); //can it be implemented?? [(not R)*. P. (not (S or R))*. R] false will become [(not R)*. P. P*. (not (S or R))*. R] false
			public static 		TreeNode<String> aansw1222 = qquest122.addChild("No, A is not allowed to occur again before the first subsequent B occurs.", false); // RESPONSE
			public static 	TreeNode<String> qquest123 = aansw12.addChild("After A and the first subsequent B occur, is A allowed to occur again?", true);
			public static 		TreeNode<String> aansw1232 = qquest123.addChild("No, A is not allowed to occur again.", false); 
			public static 			TreeNode<String> qquest12321 = aansw1232.addChild("Is event B allowed to occur again? ", true);
			public static 				TreeNode<String> aansw123211 = qquest12321.addChild("Yes, event B is allowed to occur again zero or more times.", false);
			public static 				TreeNode<String> aansw123212 = qquest12321.addChild("No, event B is not allowed to occur again.", false);
			public static 	TreeNode<String> aansw1231 = qquest123.addChild("Yes, A is allowed to occur again.", false);
			public static 			TreeNode<String> qquest12311 = aansw1231.addChild("Is B allowed to occur again?", true);
			public static 				TreeNode<String> aansw123111 = qquest12311.addChild("No, B is not allowed to occur again.", false);
			public static 				TreeNode<String> aansw123112 = qquest12311.addChild("Yes, B is allowed to occur again, but not until after another A occurs. \n If another A does occur, the situation is the same as when the first A occured, meaning that \n the restrictions described on any events that take place after that occurence would again apply", false); 
			public static 				TreeNode<String> aansw123113 = qquest12311.addChild("Yes, B is allowed to occur again, zero or more times, whether or not another A occurs.", false); // RESPONSE
			public static 					TreeNode<String> qquest1231131 = aansw123113.addChild("If another A does occur, is the situation the same as when the first A occured, \n meaning that the restrictions described on any events that take place after that occurence would again apply?", true);
			public static 						TreeNode<String> aansw12311311 = qquest1231131.addChild("Yes, if A does occur again, the restrictions described on those events would again apply.", false); // RESPONSE
			public static 						TreeNode<String> aansw12311312 = qquest1231131.addChild("No, even if A does not occur again, there are no restrictions on the occurences of any future events.", false);
		
			public static 	TreeNode<String> qquest124 = aansw12.addChild("Are there any exceptional events such that if any of them occurs, the property behavior may not be required to hold?", true);
			public static 		TreeNode<String> aansw1241 = qquest124.addChild("No, there are no exceptional events. ", false);
			public static 		TreeNode<String> aansw1242 = qquest124.addChild("Yes, if exceptional event X occurs, the event sequence may be considered as acceptable.", false);
			public static 			TreeNode<String> qquest12421 = aansw1242.addChild("What if the exceptional event occurs after a behavior violation has been found?", true);
			public static 				TreeNode<String> aansw124211 = qquest12421.addChild("In that case, the event sequence is not acceptable", false);
			public static 				TreeNode<String> aansw124212 = qquest12421.addChild("Even in such case the event sequence is considered acceptable.", false);
			
			public static TreeNode<String> aansw13 = behaviorQuestionTree.addChild("Three events.", false);
			public static 	TreeNode<String> qquest131 = aansw13.addChild("Which of the following best describes the relationship between A, B and C?", true);
				public static TreeNode<String> aansw1311 = qquest131.addChild("If A occurs, then B followed by C is required to occur subsequently.", false); // RESPONSE CHAIN S, T respond to P
				public static TreeNode<String> aansw1312 = qquest131.addChild("If A occurs followed by B, then C is required to occur subsequently.", false); // RESPONSE CHAIN P responds to S, T
				public static TreeNode<String> aansw1313 = qquest131.addChild("C is not allowed to occur until A, followed by B, occurs.", false); // PRECEDENCE CHAIN S, T precede P
				public static TreeNode<String> aansw1314 = qquest131.addChild("B followed by C is not allowed to occur, until A occurs.", false); // PRECENDENCE CHAIN P precedes S, T


}