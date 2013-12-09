package info.remenska.PASS.wizards;

public class Questionnaire {
	
	public static TreeNode<String> scopeQuestionTree = new TreeNode<String>("Is the behavior only required to hold within a restricted interval(s) in the event sequence?                                    \t\t                                          ", true);
	public static TreeNode<String> behaviorQuestionTree = new TreeNode<String>("How many events of primary interest are there in this behavior?", true);
	public static 	TreeNode<String> answ11 = scopeQuestionTree.addChild("Yes, the behavior is only required to hold within restricted interval(s) in the event sequence. ", false);
	public static 	TreeNode<String> quest111 = answ11.addChild("Which of the following choices best describes the restricted interval(s)?", true);
	public static 		TreeNode<String> answ1111 = quest111.addChild("There is a restricted interval in the event sequence and it has a \n starting delimiter, START: the behavior is required to hold from an occurrence of START \n through to the end of the event sequence. ", false);
	public static 			TreeNode<String> quest11111 = answ1111.addChild("What if there are multiple occurrences of START before the end of the event sequence?", true);
	public static 				TreeNode<String> answ111111 = quest11111.addChild("Only the first occurrence of START starts the restricted interval; \n later occurrences of START do not have an effect.", false); // AFTER-Q
	public static 				TreeNode<String> answ111112 = quest11111.addChild("Only the last occurrence of START starts the restricted interval; \n each occurrence of START resets the beginning of a restricted interval. ", false); //can it be implemented? AFTER-Q variant (ASK if possible)
	
	public static 		TreeNode<String> answ1112 = quest111.addChild("There is a restricted interval in the event sequence and it has an ending \n delimiter, END: the behavior is required to hold from the start of the event sequence \n through to the first occurrence of END. ", false);
	public static 			TreeNode<String> quest11121 = answ1112.addChild("If END does not occur, is the behavior still required to hold, until the end of the event sequence?", true);
	public static 				TreeNode<String> answ111211 = quest11121.addChild("Yes, if END does not occur, the behavior is required to hold throughout the entire event sequence.", false); // can it be implemented?
	public static 				TreeNode<String> answ111212 = quest11121.addChild("No, if END does not occur, the behavior is not required to hold anywhere in the event sequence.", false); // BEFORE-R

	public static 		TreeNode<String> answ1113 = quest111.addChild("A restricted interval in the event sequence can have both a starting delimiter, START, and an \n ending delimiter, END. The behavior is required to hold from an occurrence of START \n through to the end of that restricted interval. ", false);
	public static 			TreeNode<String> quest11131 = answ1113.addChild("What happens if there are multiple occurrences of START without an occurrence of END in between them?", true);
	public static 				TreeNode<String> answ11311 = quest11131.addChild("Only the first of those occurrences of START potentially starts a restricted interval; \n later occurrences of START within that restricted interval do not have an effect.", false); // this is the default
		public static 			TreeNode<String> quest113111 = answ11311.addChild("If an occurrence of START is not followed by an occurrence of END, is the behavior \n still required to hold, until the end of the event sequence?", true);
		public static 				TreeNode<String> answ1131111 = quest113111.addChild("Yes, if END does not occur subsequently, the behavior is required to hold until the end of the event sequence.", false); // AFTER-Q-UNTILL-R 
		public static 				TreeNode<String> answ1131112 = quest113111.addChild("No, if END does not occur subsequently, the behavior is not required to hold for the remainder of the event sequence.", false); // BETWEEN-Q-AND-R

	public static 				TreeNode<String> answ11312 = quest11131.addChild("Only the last of those occurrences of START potentially starts a restricted interval; \n each of those occurrences of START resets the beginning of that restricted interval. ", false); 
	public static 			TreeNode<String> quest113121 = answ11312.addChild("If an occurrence of START is not followed by occurrence of END, is the behavior \n still required to hold, until the end of the event sequence?", true);
			public static 				TreeNode<String> answ1131211 = quest113121.addChild("Yes, if END does not occur subsequently, then the behavior is required to hold until the end of the event sequence.", false); // AFTER-Q-UNTILL-R  variant // can it be implemented?
			public static 				TreeNode<String> answ1131212 = quest113121.addChild("No, if END does not occur subsequently, then the behavior is not required to hold for the remainder of the event sequence.", false); // BETWEEN-Q-AND-R variant // can it be implemented?
		
			public static TreeNode<String> answ12 = scopeQuestionTree.addChild("No, the behavior is required to hold throughout the entire event sequence", false); // GLOBALLY

			public static TreeNode<String> aansw11 = behaviorQuestionTree.addChild("One event.", false);
			public static 	TreeNode<String> qquest111 = aansw11.addChild("Which of the following choices best describes the restriction on A?", true);
			public static 		TreeNode<String> aansw1111 = qquest111.addChild("A is never allowed to occur", false); // ABSENCE
			public static 		TreeNode<String> aansw1112 = qquest111.addChild("A must eventually occur at least once.", false);	// EXISTENCE			
			public static 		TreeNode<String> aansw1113 = qquest111.addChild("A is allowed to occur at most once.", false); // BOUNDED EXISTENCE 
			public static 		TreeNode<String> aansw1114 = qquest111.addChild("A must always be possible to occur. This is useful for checking if an object state always has a certain value. \nThe events in such case are getter methods for querying the state. ", false); // NEW ONE!
					
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

	
			public static TreeNode<String> aansw13 = behaviorQuestionTree.addChild("Three events.", false);
			public static 	TreeNode<String> qquest131 = aansw13.addChild("Which of the following best describes the relationship between A, B and C?", true);
				public static TreeNode<String> aansw1311 = qquest131.addChild("If A occurs, then B followed by C is required to occur subsequently.", false); 
					public static TreeNode<String> qquest13111 =  aansw1311.addChild("Is there any exceptional event X which must NOT happen between B and C? ", true);
						public static TreeNode<String> aansw131111 = qquest13111.addChild("No there is no such event.", false); // RESPONSE CHAIN S, T respond to P
						public static TreeNode<String> aansw131112 = qquest13111.addChild("Yes, there is an event which must not happen between B and C. ", false); // CONSTRAINED RESPONSE CHAIN 2

				public static TreeNode<String> aansw1312 = qquest131.addChild("If A occurs followed by B, then C is required to occur subsequently.", false); // RESPONSE CHAIN P responds to S, T
				public static TreeNode<String> aansw1313 = qquest131.addChild("C is not allowed to occur until A, followed by B, occurs.", false); // PRECEDENCE CHAIN S, T precede P
				public static TreeNode<String> aansw1314 = qquest131.addChild("B followed by C is not allowed to occur, until A occurs.", false); // PRECENDENCE CHAIN P precedes S, T

}