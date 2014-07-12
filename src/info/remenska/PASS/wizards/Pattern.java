package info.remenska.PASS.wizards;


import java.util.HashMap;

public class Pattern {
  public static HashMap<String,HashMap<String,String>> patterns;
  public static HashMap<String, HashMap<String,Boolean>> patternsMonitorable; 
   public static void addPattern(String scope, String behavior, String formula){
	   HashMap<String,String> behaviors = patterns.get(scope);
	   behaviors.put(behavior, formula);
	   patterns.put(scope,behaviors);
   }
   
   public static void addPatternMonitorable(String scope, String behavior, Boolean isMonitorable){
	   HashMap<String,Boolean> behaviorsMonitorable = patternsMonitorable.get(scope);
	   behaviorsMonitorable.put(behavior, isMonitorable);
	   patternsMonitorable.put(scope,behaviorsMonitorable);

   }
   
   public static void fill(){
	   
	   patterns = new HashMap<String,HashMap<String,String>>();
			  patterns.put("Globally", new HashMap<String, String>());
			  patterns.put("Before R", new HashMap<String, String>());
			  patterns.put("Before R variant", new HashMap<String, String>()); //like until, if R does not happen, the property still must hold
			  patterns.put("After Q", new HashMap<String, String>());
			  patterns.put("After Q variant", new HashMap<String, String>());
			  patterns.put("Between Q and R", new HashMap<String, String>());
			  patterns.put("Between Q and R variant", new HashMap<String, String>());
			  patterns.put("After Q until R", new HashMap<String, String>());
			  patterns.put("After Q until R variant", new HashMap<String, String>());

			  patternsMonitorable = new HashMap<String,HashMap<String,Boolean>>();
			  patternsMonitorable.put("Globally", new HashMap<String, Boolean>());
			  patternsMonitorable.put("Before R", new HashMap<String, Boolean>());
			  patternsMonitorable.put("Before R variant", new HashMap<String, Boolean>());
			  patternsMonitorable.put("After Q", new HashMap<String, Boolean>());
			  patternsMonitorable.put("After Q variant", new HashMap<String, Boolean>());
			  patternsMonitorable.put("Between Q and R", new HashMap<String, Boolean>());
			  patternsMonitorable.put("Between Q and R variant", new HashMap<String, Boolean>()); // only the last Q starts the interval, similar to after Q variant
			  patternsMonitorable.put("After Q until R", new HashMap<String, Boolean>());
			  patternsMonitorable.put("After Q until R variant", new HashMap<String, Boolean>()); // only the last Q starts the interval, similar to after Q variant
				  
	   addPattern("Globally", "Absence", "[true*. P ] false" ); 
	   addPattern("Before R", "Absence", "[(not R )*. P . (not R )*. R ] false"); 
	   addPattern("Before R variant", "Absence", "[(not R )*. P ] false"); 
	   addPattern("After Q", "Absence", "[(not Q )*. Q . true*. P ] false");
//	   addPattern("After Q variant", "Absence", "[true*. Q (not Q)*](([true*. Q]false) => [true*. P]false)");
	   addPattern("After Q variant", "Absence", "[true*. Q ](([true*. Q ]false) => [true*. ( P + Q )]false)"); 
	   addPattern("Between Q and R", "Absence", "[true*. Q . (not R )*. P . (not R )*. R ] false" );
	   addPattern("Between Q and R variant", "Absence", "[true*. Q ](([true*. Q ]false) => [(not R )*. P . (not R )*. R ] false) " ); 
	   addPattern("After Q until R", "Absence", "[true*. Q . (not R )*. P ] false");
	   addPattern("After Q until R variant", "Absence", "[true*. Q ](([true*. Q ]false) => [(not R )*. P ] false)");

	   addPattern("Globally", "Existence","mu X. <true> true and [not P ] X" );
	   addPattern("Before R", "Existence", "[(not P )*. R ] false");
	   addPattern("Before R variant", "Existence", "mu X. ( [not P ]X && [ R ]false and <true>true)");
	   addPattern("After Q", "Existence", "[(not Q )*. Q ] mu X. (<true> true and [not P ] X)");
//	   addPattern("After Q variant", "Existence", "[true*. Q](([true*. Q] false) => mu X. ([not P]X and <true>true)");
	   addPattern("After Q variant", "Existence", "[true*. Q ](([true*. Q ] false) => mu X. ([not P ]X and <true>true) and [ Q ]false)");
	   addPattern("Between Q and R", "Existence", "[true*. Q . (not ( P or R ))*. R ] false");
	   addPattern("Between Q and R variant","Existence", "[true*. Q ](([true*. Q ] false) => [(not ( P or R ))*. R ] false)");
	   addPattern("After Q until R", "Existence", "[true*. Q ] mu X. <true> true and [ R ] false and [not P ] X");
	   addPattern("After Q until R variant", "Existence", "[true*. Q ](([true*. Q ] false) =>  mu X. <true> true and [ R ] false and [not P ] X) ");
	   

	   
	   addPattern("Globally", "Universality", "[true*. P ] false ");
	   addPattern("Before R", "Universality", "[(not R )*. P . (not R )*. R ] false ");
	   addPattern("Before R variant", "Universality", "[(not R )*. P ] false "); 
	   addPattern("After Q", "Universality", "[(not Q )*. Q . true*. P ] false ");
	   addPattern("Between Q and R", "Universality", "[true*. Q . (not R )*. P . (not R )*. R ] false ");
	   addPattern("After Q variant", "Universality", "[true*. Q ](([true*. Q ]false) => [true*. ( P + Q )]false )"); 
	   addPattern("After Q until R", "Universality", "[true*. Q . (not R )*. P ] false ");
	   addPattern("Between Q and R variant", "Universality", "[true*. Q ](([true*. Q ]false ) => [(not R )*. P . (not R )*. R ] false) " ); 
	   
	   addPattern("Globally", "Precedence", "[(not P )*. S ] false ");
	   addPattern("Before R" , "Precedence" , "[(not ( P or R ))*. S . (not R )*. R ] false " );
	   addPattern("Before R variant" , "Precedence" , "[ (not ( P or R ))*. S ] false " );
	   addPattern("After Q", "Precedence", "[(not Q )*. Q . (not P )*. S ] false "); 
	   addPattern("After Q variant", "Precedence", "[true*. Q ](([true*. Q ]false) => [(not P )*. S ] false)  ");
	   addPattern("Between Q and R", "Precedence", "[true*. Q . (not ( P or R ))*. S . (not R )*. R ] false");
	   addPattern("Between Q and R variant", "Precedence", "[true*. Q ](([true*. Q ]false) => [(not ( P or R ))*. S . (not R )*. R ] false)");
	   addPattern("After Q until R", "Precedence", "[true*. Q . (not ( P or R ))*. S ] false ");
	   addPattern("After Q until R variant", "Precedence", "[true*. Q ](([true*. Q ]false) => [(not ( P or R ))*. S ] false )");
	
	   addPattern("Globally", "Precedence variant", "mu X. ([not P ] X and <true>true and [ S ]false)");
	   addPattern("Before R" , "Precedence variant" , "mu X.[not ( P or R )]X and <true>true and [ S . (not R ). R ] false" );
	   addPattern("Before R variant", "Precedence variant" , "mu X.[not ( P or R )]X and <true>true and [ S ] false" );
	   addPattern("After Q",  "Precedence variant" , "[(not Q )*. Q ] mu X. ([not P ] X and <true>true and [ S ]false)" );
	   addPattern("After Q variant", "Precedence variant" , "[true*. Q ](([true*. Q ]false) => mu X. ([not P ] X and <true>true and [ S ]false)" );
	   addPattern("Between Q and R", "Precedence variant" , "[(not Q )*. Q ] mu X.[not ( P or R )]X and <true>true and [ S. (not R ). R ] false" );
	   addPattern("Between Q and R variant",  "Precedence variant" , "[true*. Q ](([true*. Q ]false) => mu X.[not ( P or R )]X and <true>true and [ S . (not R ). R ] false" );
	   addPattern("After Q until R", "Precedence variant" , "[(not Q )*. Q ] mu X. ([not ( P or R )] X and <true>true and [ S ]false)" );
	   addPattern("After Q until R variant", "Precedence variant" , "[true*. Q ](([true*. Q ]false) => mu X.[not ( P or R )]X and <true>true and [ S ] false" );
	   
	   addPattern("Globally", "Response", "[true*. P ] mu X. <true> true and [not S ] X ");
	   addPattern("Before R", "Response", "[(not R )*. P . (not ( S or R ))*. R ] false ");
	   addPattern("Before R variant", "Response", "[(not R )*. P ] mu X. <true> true and [ R ]false and [not S ] X ");
	   addPattern("After Q", "Response", "[(not Q )*. Q . true*. P ] mu X. <true> true and [not S ] X " );
	   addPattern("After Q variant", "Response", "[true*. Q ]( ([true*. Q ]false) => [true*. P ] (mu X. <true>true and [not S ] X ) ) " );
	   addPattern("Between Q and R", "Response", "[true*. Q . (not R )*. P. (not ( S or R ))*. R ] false");
	   addPattern("Between Q and R variant", "Response", "[true*. Q ]( ([true*. Q ]false) => [(not R )*. P . (not ( S or R ))*. R ] false)");
	   addPattern("After Q until R", "Response", "[true*. Q . (not R )*. P ] mu X. <true> true and [ R ] false and [not S ] X");
	   addPattern("After Q until R variant", "Response", "[true*. Q ]( ([true*. Q ]false) => [(not R )*. P ] (mu X. <true> true and [ R ] false and [not S ] X)) ");
	   
	   addPattern("Globally", "Response variant", "mu X.( [not P ]X and <true>true and [true*. P ](mu Y. [not S ]Y and <true>true))");
	   addPattern("Before R", "Response variant", "((mu X. [not ( R or P )]X and <true>true) and [true*. P . (not ( R or S ))*. R ]false )");
	   addPattern("Before R variant", "Response variant", "mu X. ( [not P ]X and <true>true and [true*. P ](mu Y.<true> true and [ R ]false and [not S ] Y))");
	   addPattern("After Q", "Response variant", "[(not Q )*. Q ]( (mu X. [not P ]X && <true>true) and [true*. P ](mu Y. <true>true and [not S ] Y) )");
	   addPattern("After Q variant", "Response variant", "[true*. Q ]( ([true*. Q ]false) => ( mu X.( [not P ]X and <true>true and [true*. P ](mu Y. [not S ]Y and <true>true)))");
	   addPattern("Between Q and R", "Response variant", "[(not Q )*. Q ]((mu X. [not ( P or R )]X and <true>true) and [true*. P . (not ( R or S ))*. R ]false )");
	   addPattern("Between Q and R variant", "Response variant", "[true*. Q ](([true*. Q ]false) => ((mu X. [not ( P or R )]X and <true>true) and [true*. P . (not ( R or S ))*. R ]false ))");
	   addPattern("After Q until R", "Response variant", "[true*. Q ]( mu X. ( [not ( P or R )]X and <true>true and [true*. P ](mu Y.<true> true and [ R ]false and [not S ] Y)) )");
	   addPattern("After Q until R variant", "Response variant", "[true*. Q ](([true*. Q ]false) =>  mu X. ( [not ( P or R )]X and <true>true and [true*. P ](mu Y.<true> true and [ R ]false and [not S ] Y)))");
	   
	   addPattern("Globally", "Precedence Chain 1", "[(not S )*. (nil | ( S . (not T )*)). P ] false " );
	   addPattern("Before R", "Precedence Chain 1", " [(not ( S or R ))*. (nil | ( S . (not ( T or R ))*)). P . (not R )*. R ] false ");
	   addPattern("Before R variant", "Precedence Chain 1", " [(not ( S or R ))*. (nil | ( S . (not ( T or R ))*)). P ] false ");
	   addPattern("After Q", "Precedence Chain 1", " [(not Q )*. Q . (not S )*. (nil | (S . (not T )*)). P ] false ");
	   addPattern("After Q variant", "Precedence Chain 1", " [true*. Q ]( ([true*. Q ]false) => [(not S )*. (nil | (S . (not T )*)). P ] false) ");
	   // TODO: the above is not "or", it's |
	   // TODO: the above can be simplified to remove the nil; example: [A*.(nil or R).Q]false = [A*.R.Q]false AND [A*.Q]false = [A*.R.Q + A*.R]false
	   addPattern("Between Q and R", "Precedence Chain 1", "[true*. Q . (not ( S or R ))*. (nil | ( S . (not ( T or R ))*)). P .(not R )*. R ] false");
	   addPattern("Between Q and R variant", "Precedence Chain 1", "[true*.Q ](([true*. Q ]false) =>[(not ( S or R ))*. (nil | ( S . (not ( T or R ))*)). P .(not R )*. R ] false ");
	   addPattern("After Q until R", "Precedence Chain 1", " [true*. Q . (not ( S or R ))*. (nil | ( S . (not ( T or R ))*)). P ] false ");
	   addPattern("After Q until R variant", "Precedence Chain 1", "[true*. Q ](([true*. Q ]false) => [(not ( S or R ))*. (nil | ( S . (not ( T or R ))*)). P ] false ) ");
	  
	   addPattern("Globally", "Precedence Chain 2", " [(not P )*. S . (not T )*. T ] false ");
	   addPattern("Before R", "Precedence Chain 2", " [(not ( P or R ))*. S . (not ( T or R ))*. T . (not R )*. R ] false ");
	   addPattern("Before R variant", "Precedence Chain 2", "[ (not ( P or R ))*. S . (not ( T or R ))*. T ] false");
	   addPattern("After Q", "Precedence Chain 2", "[(not Q )*. Q . (not P )*. S . (not T )*. T ] false ");
	   addPattern("After Q variant", "Precedence Chain 2", "[true*. Q ]( ([true*. Q ]false) => [ (not P )*. S . (not T )*. T ] false) ");
	   addPattern("Between Q and R", "Precedence Chain 2", "  [true*. Q . (not ( P or R ))*. S . (not ( T or R ))*. T . (not R )*. R ] false ");
	   addPattern("Between Q and R variant", "Precedence Chain 2", "[true*. Q ]( ([true*. Q ]false) => [(not ( P or R ))*. S . (not ( T or R ))*. T . (not R )*. R ] false ) ");
	   addPattern("After Q until R", "Precedence Chain 2", " [true*. Q . (not ( P or R ))*. S . (not ( T or R ))*. T ] false ");
	   addPattern("After Q until R variant", "Precedence Chain 2", "[true*. Q ]( ([true*. Q ]false) => [(not ( P or R ))*. S . (not ( T or R ))*. T ] false )");
	   
	   addPattern("Globally", "Response Chain 1", " [true*. S . (not T )*. T ] mu X. <true> true and [not P ] X ");
	   addPattern("Before R", "Response Chain 1", " [true*. S . (not T )*. T . (not P )*. R ] false ");
	   addPattern("Before R variant", "Response Chain 1", " [(not ( S or R ))*. S . (not ( T or R ))*. T ] mu X. <true> true and [ R ] false and [not P ] X");
	   addPattern("After Q", "Response Chain 1", " [(not Q )*. Q . true*. S . (not T )*. T ] mu X. <true> true and [not P ] X " );
	   addPattern("After Q variant", "Response Chain 1", " [true*. Q ](([true*. Q ]false) => [ true*. S . (not T )*. T ] (mu X. <true> true and [not P ] X)) " );
	   addPattern("Between Q and R", "Response Chain 1", "[true*. Q . (not ( S or R ))*. S . (not ( T or R ))*. T .(not ( P or R ))*. R ] false");
	   addPattern("Between Q and R variant", "Response Chain 1", "[true*. Q ](([true*. Q ]false) => [(not ( S or R ))*. S . (not ( T or R ))*. T .(not ( P or R ))*. R ] false)");
	   addPattern("After Q until R", "Response Chain 1", "[true*. Q . (not ( S or R ))*. S . (not ( T or R ))*. T ] mu X. <true> true and [ R ] false and [not P ] X ");
	   addPattern("After Q until R variant", "Response Chain 1", "[true*. Q ](([true*. Q ]false) => [(not ( S or R ))*. S . (not ( T or R ))*. T ] mu X. <true> true and [ R ] false and [not P ] X )");
	   
	   addPattern("Globally", "Response Chain 2", "[true*. P ] mu X. <true> true and [ S ] mu Y. (<true> true and [not T ] Y) and [not S ] X " );
	   addPattern("Before R", "Response Chain 2", "[(not R )*. P . (not ( S or R ))*. (nil | (S . (not ( T or R ))*)). R ] false ");
	   addPattern("Before R variant", "Response Chain 2", "[(not R )*. P ] mu X. <true> true and [ R ] false and [ S ] mu Y. (<true> true and [ R ] false and [not T ] Y) and [not S ] X ");
	   addPattern("After Q", "Response Chain 2", "[(not Q )*. Q . true*. P ] mu X. <true> true and [ S ] mu Y. (<true> true and [not T ] Y) and [not S ] X ");
	   addPattern("After Q variant", "Response Chain 2", "[true*. Q ]( ([true*. Q ]false) => [true*. P ] (mu X. <true> true and [ S ] mu Y. (<true> true and [not T ] Y) and [not S ] X)) ");
	   addPattern("Between Q and R", "Response Chain 2", "[true*. Q . (not R )*. P . (not ( S or R ))*. (nil | ( S . (not ( T or R ))*)). R ] false ");
	   addPattern("Between Q and R variant", "Response Chain 2", "[true*. Q ]( ([true*. Q ]false) => [(not R )*. P . (not ( S or R ))*. (nil | ( S . (not ( T or R ))*)). R ] false )");
	   addPattern("After Q until R", "Response Chain 2", "[true*. Q . (not R )*. P ] mu X. <true> true and [ R ] false and [ S ] mu Y. (<true> true and [ R ] false and [not T ] Y) and [not S ] X ");
	   addPattern("After Q until R variant", "Response Chain 2", "[true*. Q]( ([true*. Q]false) => [(not R)*. P] mu X. <true> true and [R] false and [S] mu Y. (<true> true and [R] false and [not T] Y) and [not S] X) ");
	   
	   
	   addPattern("Globally", "Constrained Response Chain 2", "[true*. P ] mu X . <true> true and [ S ] mu Y. (<true> true and [ Z ] false and [not T ] Y) and [not S ] X " );
	   addPattern("Before R", "Constrained Response Chain 2", "[(not R )*. P . (not ( S or R ))*. (nil | ( S . (nil | ((not ( T or R ))*. Z )). (not ( T or R ))*)). R ] false ");
	   addPattern("Before R variant", "Constrained Response Chain 2", "[(not R )*. P ] mu X. <true> true and [ R ] false and [ S ] mu Y. (<true> true and [ Z or R ] false and [not T ] Y) and [not S ] X ");
	   addPattern("After Q", "Constrained Response Chain 2", "[(not Q )*. Q . true*. P ] mu X. <true> true and [ S ] mu Y. (<true> true and [ Z ] false and [not T ] Y) and [not S ] X");
	   addPattern("After Q variant", "Constrained Response Chain 2", "[true*. Q ]( ([true*. Q ]false) => [true*. P ]( mu X. <true> true and [ S ] mu Y. (<true> true and [ Z ] false and [not T ] Y) and [not S ] X))");
	   addPattern("Between Q and R", "Constrained Response Chain 2", "[true*. Q . (not R )*. P . (not ( S or R ))*. (nil | ( S . (nil | ((not ( T or R ))*. Z )). (not ( T or R ))*)). R ] false ");
	   addPattern("Between Q and R variant", "Constrained Response Chain 2", "[true*. Q ]( ([true*. Q ]false) => [(not R )*. P . (not ( S or R ))*. (nil | ( S . (nil | ((not ( T or R ))*. Z )). (not ( T or R ))*)). R] false )");
	   addPattern("After Q until R", "Constrained Response Chain 2", "[true*. Q . (not R )*. P ] mu X. <true> true and [ R ] false and [ S ] mu Y. (<true> true and [ Z or R ] false and [not T ] Y) and [not S ] X ");
	   addPattern("After Q until R variant", "Constrained Response Chain 2", "[true*. Q ]( ([true*. Q ]false) => [(not R )*. P ] mu X. <true> true and [ R ] false and [ S ] mu Y. (<true> true and [ Z or R ] false and [not T ] Y) and [not S ] X) ");
	   
	   
	  addPattern("Globally", "Bounded Existence", " [(not P )*. P . (not P )*. P ] false ");
	  addPattern("Before R", "Bounded Existence", " [(not R )*. P . (not R )*. P . (not R )*. R ] false ");
	  addPattern("Before R variant", "Bounded Existence", " [(not R )*. P . (not R )*. P ] false ");
	  addPattern("After Q", "Bounded Existence", " [(not Q )*. Q . (not P )*. P . (not P )*. P ] false ");
	  addPattern("After Q variant", "Bounded Existence", "[true*. Q ]( ([true*. Q ]false) => [(not P )*. P . (not P )*. P ] false )");
	  addPattern("Between Q and R", "Bounded Existence", " [true*. Q . (not R )*. P . (not R )*. P . (not R )*. R ] false ");
	  addPattern("Between Q and R variant", "Bounded Existence", "[true*. Q ]( ([true*. Q ]false) => [(not R )*. P . (not R )*. P . (not R )*. R ] false )");
	  addPattern("After Q until R", "Bounded Existence" , " [true*. Q . (not R )*. P . (not R )*. P ] false ");
	  addPattern("After Q until R variant", "Bounded Existence" , "[true*. Q ]( ([true*. Q ]false) => [(not R )*. P . (not R )*. P ] false ) ");
	  ////  ////  ////  ////  ////  ////  ////  ////  ////  ////  ////  ////  ////  ////
	   addPatternMonitorable("Globally", "Absence", Boolean.TRUE );
	   addPatternMonitorable("Before R", "Absence", Boolean.TRUE );
	   addPatternMonitorable("Before R variant", "Absence", Boolean.TRUE );
	   addPatternMonitorable("After Q", "Absence", Boolean.TRUE );
	   addPatternMonitorable("After Q variant", "Absence", Boolean.FALSE ); //TODO: check After Q variants if they are monitorable, they have implication sign!
	   addPatternMonitorable("Between Q and R", "Absence",Boolean.TRUE );
	   addPatternMonitorable("Between Q and R variant", "Absence",Boolean.FALSE ); //TODO
	   addPatternMonitorable("After Q until R","Absence", Boolean.TRUE );
	   addPatternMonitorable("After Q until R variant","Absence", Boolean.FALSE ); //TODO
	   
	   addPatternMonitorable("Globally", "Existence",Boolean.FALSE );
	   addPatternMonitorable("Before R", "Existence", Boolean.TRUE );
	   addPatternMonitorable("Before R variant", "Existence", Boolean.FALSE );
	   addPatternMonitorable("After Q", "Existence", Boolean.FALSE );
	   addPatternMonitorable("After Q variant", "Existence", Boolean.FALSE );//TODO: check After Q variants if they are monitorable, they have implication sign!
	   addPatternMonitorable("Between Q and R", "Existence", Boolean.TRUE );
	   addPatternMonitorable("Between Q and R variant", "Existence", Boolean.FALSE ); //TODO
	   addPatternMonitorable("After Q until R", "Existence", Boolean.FALSE );
	   addPatternMonitorable("After Q until R variant", "Existence", Boolean.FALSE ); //TODO
	   
	   addPatternMonitorable("Globally", "Universality", Boolean.TRUE );
	   addPatternMonitorable("Before R", "Universality", Boolean.TRUE );
	   addPatternMonitorable("Before R variant", "Universality", Boolean.FALSE ); //TODO: check
	   addPatternMonitorable("After Q", "Universality", Boolean.TRUE );
	   addPatternMonitorable("After Q variant", "Universality", Boolean.FALSE );//TODO: check After Q variants if they are...
	   addPatternMonitorable("Between Q and R", "Universality", Boolean.TRUE );
	   addPatternMonitorable("Between Q and R variant", "Universality", Boolean.FALSE ); //TODO
	   addPatternMonitorable("After Q until R", "Universality", Boolean.TRUE );
	   addPatternMonitorable("After Q until R variant", "Universality", Boolean.TRUE ); //TODO
	   
	   addPatternMonitorable("Globally", "Precedence", Boolean.TRUE );
	   addPatternMonitorable("Before R" , "Precedence" , Boolean.TRUE );
	   addPatternMonitorable("Before R variant" , "Precedence" , Boolean.TRUE );
	   addPatternMonitorable("After Q", "Precedence", Boolean.TRUE );
	   addPatternMonitorable("After Q variant", "Precedence", Boolean.FALSE );//TODO: check After Q variants if they are...
	   addPatternMonitorable("Between Q and R", "Precedence", Boolean.TRUE );
	   addPatternMonitorable("Between Q and R variant", "Precedence", Boolean.FALSE ); //TODO
	   addPatternMonitorable("After Q until R", "Precedence",  Boolean.TRUE );
	   addPatternMonitorable("After Q until R variant", "Precedence",  Boolean.FALSE ); //TODO
	   
	   addPatternMonitorable("Globally", "Precedence variant", Boolean.FALSE ); //TODO
	   addPatternMonitorable("Before R" , "Precedence variant" , Boolean.FALSE );
	   addPatternMonitorable("Before R variant" , "Precedence variant" , Boolean.FALSE );
	   addPatternMonitorable("After Q", "Precedence variant", Boolean.FALSE );
	   addPatternMonitorable("After Q variant", "Precedence variant", Boolean.FALSE );//TODO: check After Q variants if they are...
	   addPatternMonitorable("Between Q and R", "Precedence variant", Boolean.FALSE );
	   addPatternMonitorable("Between Q and R variant", "Precedence variant", Boolean.FALSE ); //TODO
	   addPatternMonitorable("After Q until R", "Precedence variant",  Boolean.FALSE );
	   addPatternMonitorable("After Q until R variant", "Precedence variant",  Boolean.FALSE ); //TODO
	   
	   addPatternMonitorable("Globally", "Response", Boolean.FALSE );
	   addPatternMonitorable("Before R", "Response", Boolean.TRUE );
	   addPatternMonitorable("Before R variant", "Response", Boolean.FALSE );
	   addPatternMonitorable("After Q", "Response", Boolean.FALSE );
	   addPatternMonitorable("After Q variant", "Response", Boolean.FALSE );//TODO: check After Q variants if they are...
	   addPatternMonitorable("Between Q and R", "Response", Boolean.TRUE );
	   addPatternMonitorable("Between Q and R variant", "Response", Boolean.FALSE ); //TODO
	   addPatternMonitorable("After Q until R", "Response", Boolean.FALSE );
	   addPatternMonitorable("After Q until R variant", "Response", Boolean.FALSE ); //TODO
	   
	   addPatternMonitorable("Globally", "Response variant", Boolean.FALSE );
	   addPatternMonitorable("Before R", "Response variant", Boolean.FALSE );
	   addPatternMonitorable("Before R variant", "Response variant", Boolean.FALSE );
	   addPatternMonitorable("After Q", "Response variant", Boolean.FALSE );
	   addPatternMonitorable("After Q variant", "Response variant", Boolean.FALSE );//TODO: check After Q variants if they are...
	   addPatternMonitorable("Between Q and R", "Response variant", Boolean.FALSE );
	   addPatternMonitorable("Between Q and R variant", "Response variant", Boolean.FALSE ); //TODO
	   addPatternMonitorable("After Q until R", "Response variant", Boolean.FALSE );
	   addPatternMonitorable("After Q until R variant", "Response variant", Boolean.FALSE ); //TODO
	   
	   addPatternMonitorable("Globally", "Precedence Chain 1", Boolean.TRUE );
	   addPatternMonitorable("Before R", "Precedence Chain 1", Boolean.TRUE);
	   addPatternMonitorable("Before R variant", "Precedence Chain 1", Boolean.TRUE);
	   addPatternMonitorable("After Q", "Precedence Chain 1", Boolean.TRUE);
	   addPatternMonitorable("After Q variant", "Precedence Chain 1", Boolean.FALSE);//TODO: check After Q variants if they are...
	   addPatternMonitorable("Between Q and R", "Precedence Chain 1", Boolean.TRUE);
	   addPatternMonitorable("Between Q and R variant", "Precedence Chain 1", Boolean.FALSE); //TODO
	   addPatternMonitorable("After Q until R", "Precedence Chain 1", Boolean.TRUE);
	   addPatternMonitorable("After Q until R variant", "Precedence Chain 1", Boolean.FALSE); //TODO
	  
	   
	   addPatternMonitorable("Globally", "Precedence Chain 2", Boolean.TRUE);
	   addPatternMonitorable("Before R", "Precedence Chain 2", Boolean.TRUE);
	   addPatternMonitorable("Before R", "Precedence Chain 2", Boolean.TRUE);
	   addPatternMonitorable("After Q", "Precedence Chain 2", Boolean.TRUE);
	   addPatternMonitorable("After Q variant", "Precedence Chain 2", Boolean.FALSE);//TODO: check After Q variants if they are...
	   addPatternMonitorable("Between Q and R", "Precedence Chain 2", Boolean.TRUE);
	   addPatternMonitorable("After Q until R", "Precedence Chain 2",Boolean.TRUE);
	   addPatternMonitorable("Between Q and R variant", "Precedence Chain 2", Boolean.FALSE); //TODO
	   addPatternMonitorable("After Q until R variant", "Precedence Chain 2",Boolean.FALSE); //TODO
	   
	   addPatternMonitorable("Globally", "Response Chain 1", Boolean.FALSE);
	   addPatternMonitorable("Before R", "Response Chain 1", Boolean.FALSE);
	   addPatternMonitorable("Before R variant", "Response Chain 1", Boolean.FALSE);
	   addPatternMonitorable("After Q", "Response Chain 1", Boolean.FALSE );
	   addPatternMonitorable("After Q variant", "Response Chain 1", Boolean.FALSE );//TODO: check After Q variants if they are...
	   addPatternMonitorable("Between Q and R", "Response Chain 1", Boolean.TRUE);
	   addPatternMonitorable("After Q until R", "Response Chain 1", Boolean.FALSE);
	   addPatternMonitorable("Between Q and R variant", "Response Chain 1", Boolean.FALSE); //TODO
	   addPatternMonitorable("After Q until R variant", "Response Chain 1", Boolean.FALSE); //TODO
	   
	   addPatternMonitorable("Globally", "Response Chain 2", Boolean.FALSE);
	   addPatternMonitorable("Before R", "Response Chain 2", Boolean.TRUE);
	   addPatternMonitorable("Before R variant", "Response Chain 2", Boolean.FALSE);
	   addPatternMonitorable("After Q", "Response Chain 2", Boolean.FALSE);
	   addPatternMonitorable("After Q variant", "Response Chain 2", Boolean.FALSE);//TODO: check After Q variants if they are...
	   addPatternMonitorable("Between Q and R", "Response Chain 2", Boolean.TRUE);
	   addPatternMonitorable("After Q until R", "Response Chain 2", Boolean.FALSE);
	   addPatternMonitorable("Between Q and R variant", "Response Chain 2", Boolean.FALSE); //TODO
	   addPatternMonitorable("After Q until R variant", "Response Chain 2", Boolean.FALSE); //TODO
	   
	   
	   addPatternMonitorable("Globally", "Constrained Response Chain 2", Boolean.FALSE );
	   addPatternMonitorable("Before R", "Constrained Response Chain 2", Boolean.TRUE);
	   addPatternMonitorable("Before R variant", "Constrained Response Chain 2", Boolean.FALSE);
	   addPatternMonitorable("After Q", "Constrained Response Chain 2", Boolean.FALSE);
	   addPatternMonitorable("After Q variant", "Constrained Response Chain 2", Boolean.FALSE);//TODO: check After Q variants if they are...
	   addPatternMonitorable("Between Q and R", "Constrained Response Chain 2", Boolean.TRUE);
	   addPatternMonitorable("After Q until R", "Constrained Response Chain 2", Boolean.FALSE);
	   addPatternMonitorable("Between Q and R variant", "Constrained Response Chain 2", Boolean.FALSE); //TODO
	   addPatternMonitorable("After Q until R variant", "Constrained Response Chain 2", Boolean.FALSE); //TODO
	   
	   
	   addPatternMonitorable("Globally", "Bounded Existence", Boolean.TRUE);
	   addPatternMonitorable("Before R", "Bounded Existence", Boolean.TRUE);
	   addPatternMonitorable("Before R variant", "Bounded Existence", Boolean.TRUE);
	   addPatternMonitorable("After Q", "Bounded Existence", Boolean.TRUE);
	   addPatternMonitorable("After Q variant", "Bounded Existence", Boolean.FALSE);//TODO: check After Q variants if they are...
	   addPatternMonitorable("Between Q and R", "Bounded Existence", Boolean.TRUE);
	   addPatternMonitorable("After Q until R", "Bounded Existence" , Boolean.TRUE);
	   addPatternMonitorable("Between Q and R variant", "Bounded Existence", Boolean.FALSE); //TODO
	   addPatternMonitorable("After Q until R variant", "Bounded Existence" , Boolean.FALSE); //TODO
	  
	  Questionnaire.answ12.setScope("Globally");
	  Questionnaire.answ111111.setScope("After Q");
	  Questionnaire.answ111112.setScope("After Q variant"); 
	  
	  Questionnaire.answ111211.setScope("Before R variant"); 
	  Questionnaire.answ111212.setScope("Before R"); 
	  
	  Questionnaire.answ1131111.setScope("After Q until R");
	  Questionnaire.answ1131211.setScope("After Q until R variant"); 

	  Questionnaire.answ1131112.setScope("Between Q and R");
	  Questionnaire.answ1131212.setScope("Between Q and R variant"); 

	  Questionnaire.aansw1111.setBehavior("Absence");
	  
	  Questionnaire.aansw1112.setBehavior("Existence");
	  Questionnaire.aansw1113.setBehavior("Bounded Existence");
	  Questionnaire.aansw121212.setBehavior("Precedence"); 
	  Questionnaire.aansw121211.setBehavior("Precedence variant");
//	  Questionnaire.aansw1222.setBehavior("Response");
	  Questionnaire.aansw121112.setBehavior("Response");
	  Questionnaire.aansw121111.setBehavior("Response variant"); 
	  Questionnaire.aansw1114.setBehavior("Universality");

	  // OK
	  Questionnaire.aansw131111.setBehavior("Response Chain 2");
	  Questionnaire.aansw131112.setBehavior("Constrained Response Chain 2");

	  
	  
	  Questionnaire.aansw1312.setBehavior("Response Chain 1");
	  Questionnaire.aansw1313.setBehavior("Precedence Chain 1");
	  Questionnaire.aansw1314.setBehavior("Precedence Chain 2");
   }
   

}
	
