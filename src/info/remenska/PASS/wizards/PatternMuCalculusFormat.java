package info.remenska.PASS.wizards;

import java.util.HashMap;

public class PatternMuCalculusFormat {
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
//		  System.out.println(patterns.keySet());
			  patterns.put("Globally", new HashMap<String, String>());
			  patterns.put("Before R", new HashMap<String, String>());
			  patterns.put("Before RRR variant", new HashMap<String, String>()); //like until, if RRR does ! happen, the property still must hold
			  patterns.put("After Q", new HashMap<String, String>());
			  patterns.put("After QQQ variant", new HashMap<String, String>());
			  patterns.put("Between QQQ and R", new HashMap<String, String>());
			  patterns.put("Between QQQ and RRR variant", new HashMap<String, String>());
			  patterns.put("After QQQ until R", new HashMap<String, String>());
			  patterns.put("After QQQ until RRR variant", new HashMap<String, String>());

			  patternsMonitorable = new HashMap<String,HashMap<String,Boolean>>();
//			  System.out.println(patterns.keySet());
			  patternsMonitorable.put("Globally", new HashMap<String, Boolean>());
			  patternsMonitorable.put("Before R", new HashMap<String, Boolean>());
			  patternsMonitorable.put("Before RRR variant", new HashMap<String, Boolean>());
			  patternsMonitorable.put("After Q", new HashMap<String, Boolean>());
			  patternsMonitorable.put("After QQQ variant", new HashMap<String, Boolean>());
			  patternsMonitorable.put("Between QQQ and R", new HashMap<String, Boolean>());
			  patternsMonitorable.put("Between QQQ and RRR variant", new HashMap<String, Boolean>()); // only the last QQQ starts the interval, similar to after QQQ variant
			  patternsMonitorable.put("After QQQ until R", new HashMap<String, Boolean>());
			  patternsMonitorable.put("After QQQ until RRR variant", new HashMap<String, Boolean>()); // only the last QQQ starts the interval, similar to after QQQ variant
				  
	   addPattern("Globally", "Absence", "[true*. PPP ] false" ); 
	   addPattern("Before R", "Absence", "[(! RRR )*. PPP . (! RRR )*. RRR ] false"); 
	   addPattern("Before RRR variant", "Absence", "[(! RRR )*. PPP ] false"); 
	   addPattern("After Q", "Absence", "[(! QQQ )*. QQQ . true*. PPP ] false");
//	   addPattern("After QQQ variant", "Absence", "[true*. QQQ (! Q)*](([true*. Q]false) => [true*. P]false)");
	   addPattern("After QQQ variant", "Absence", "[true*. QQQ ](([true*. QQQ ]false) => [true*. ( PPP + QQQ )]false)"); 
	   addPattern("Between QQQ and R", "Absence", "[true*. QQQ . (! RRR )*. PPP . (! RRR )*. RRR ] false" );
	   addPattern("Between QQQ and RRR variant", "Absence", "[true*. QQQ ](([true*. QQQ ]false) => [(! RRR )*. PPP . (! RRR )*. RRR ] false) " ); 
	   addPattern("After QQQ until R", "Absence", "[true*. QQQ . (! RRR )*. PPP ] false");
	   addPattern("After QQQ until RRR variant", "Absence", "[true*. QQQ ](([true*. QQQ ]false) => [(! RRR )*. PPP ] false)");

	   addPattern("Globally", "Existence","mu X. <true> true && [! PPP ] X" );
	   addPattern("Before R", "Existence", "[(! PPP )*. RRR ] false");
	   addPattern("Before RRR variant", "Existence", "mu X. ( [! PPP ]X && [ RRR ]false && <true>true)");
	   addPattern("After Q", "Existence", "[(! QQQ )*. QQQ ] mu X. (<true> true && [! PPP ] X)");
//	   addPattern("After QQQ variant", "Existence", "[true*. Q](([true*. Q] false) => mu X. ([! P]X && <true>true)");
	   addPattern("After QQQ variant", "Existence", "[true*. QQQ ](([true*. QQQ ] false) => mu X. ([! PPP ]X && <true>true) && [ QQQ ]false)");
	   addPattern("Between QQQ and R", "Existence", "[true*. QQQ . (! ( PPP || RRR ))*. RRR ] false");
	   addPattern("Between QQQ and RRR variant","Existence", "[true*. QQQ ](([true*. QQQ ] false) => [(! ( PPP || RRR ))*. RRR ] false)");
	   addPattern("After QQQ until R", "Existence", "[true*. QQQ ] mu X. <true> true && [ RRR ] false && [! PPP ] X");
	   addPattern("After QQQ until RRR variant", "Existence", "[true*. QQQ ](([true*. QQQ ] false) =>  mu X. <true> true && [ RRR ] false && [! PPP ] X) ");
	   
	   //TODO: missing Universality scope variations! ! ! 
	   // actually it's the same as absence, but we're just using val(xx)  instead of false
	   
	   addPattern("Globally", "Universality", "[true*. PPP ] false ");
	   addPattern("Before R", "Universality", "[(! RRR )*. PPP . (! RRR )*. RRR ] false ");
	   addPattern("Before RRR variant", "Universality", "[(! RRR )*. PPP ] false "); 
	   addPattern("After Q", "Universality", "[(! QQQ )*. QQQ . true*. PPP ] false ");
	   addPattern("Between QQQ and R", "Universality", "[true*. QQQ . (! RRR )*. PPP . (! RRR )*. RRR ] false ");
	   addPattern("After QQQ variant", "Universality", "[true*. QQQ ](([true*. QQQ ]false) => [true*. ( PPP + QQQ )]false )"); 
	   addPattern("After QQQ until R", "Universality", "[true*. QQQ . (! RRR )*. PPP ] false ");
	   addPattern("Between QQQ and RRR variant", "Universality", "[true*. QQQ ](([true*. QQQ ]false ) => [(! RRR )*. PPP . (! RRR )*. RRR ] false) " ); 
	   
	   addPattern("Globally", "Precedence", "[(! PPP )*. SSS ] false ");
	   addPattern("Before R" , "Precedence" , "[(! ( PPP || RRR ))*. SSS . (! RRR )*. RRR ] false " );
	   addPattern("Before RRR variant" , "Precedence" , "[ (! ( PPP || RRR ))*. SSS ] false " );
	   addPattern("After Q", "Precedence", "[(! QQQ )*. QQQ . (! PPP )*. SSS ] false "); 
	   addPattern("After QQQ variant", "Precedence", "[true*. QQQ ](([true*. QQQ ]false) => [(! PPP )*. SSS ] false)  ");
	   addPattern("Between QQQ and R", "Precedence", "[true*. QQQ . (! ( PPP || RRR ))*. SSS . (! RRR )*. RRR ] false");
	   addPattern("Between QQQ and RRR variant", "Precedence", "[true*. QQQ ](([true*. QQQ ]false) => [(! ( PPP || RRR ))*. SSS . (! RRR )*. RRR ] false)");
	   addPattern("After QQQ until R", "Precedence", "[true*. QQQ . (! ( PPP || RRR ))*. SSS ] false ");
	   addPattern("After QQQ until RRR variant", "Precedence", "[true*. QQQ ](([true*. QQQ ]false) => [(! ( PPP || RRR ))*. SSS ] false )");
	
	   addPattern("Globally", "Precedence variant", "mu X. ([! PPP ] X && <true>true && [ SSS ]false)");
	   addPattern("Before R" , "Precedence variant" , "mu X.[! ( PPP || RRR )]X && <true>true && [ SSS . (! RRR ). RRR ] false" );
	   addPattern("Before RRR variant", "Precedence variant" , "mu X.[! ( PPP || RRR )]X && <true>true && [ SSS ] false" );
	   addPattern("After Q",  "Precedence variant" , "[(! QQQ )*. QQQ ] mu X. ([! PPP ] X && <true>true && [ SSS ]false)" );
	   addPattern("After QQQ variant", "Precedence variant" , "[true*. QQQ ](([true*. QQQ ]false) => mu X. ([! PPP ] X && <true>true && [ SSS ]false)" );
	   addPattern("Between QQQ and R", "Precedence variant" , "[(! QQQ )*. QQQ ] mu X.[! ( PPP || RRR )]X && <true>true && [ S. (! RRR ). RRR ] false" );
	   addPattern("Between QQQ and RRR variant",  "Precedence variant" , "[true*. QQQ ](([true*. QQQ ]false) => mu X.[! ( PPP || RRR )]X && <true>true && [ SSS . (! RRR ). RRR ] false" );
	   addPattern("After QQQ until R", "Precedence variant" , "[(! QQQ )*. QQQ ] mu X. ([! ( PPP || RRR )] X && <true>true && [ SSS ]false)" );
	   addPattern("After QQQ until RRR variant", "Precedence variant" , "[true*. QQQ ](([true*. QQQ ]false) => mu X.[! ( PPP || RRR )]X && <true>true && [ SSS ] false" );
	   
	   addPattern("Globally", "Response", "[true*. PPP ] mu X. <true> true && [! SSS ] X ");
	   addPattern("Before R", "Response", "[(! RRR )*. PPP . (! ( SSS || RRR ))*. RRR ] false ");
	   addPattern("Before RRR variant", "Response", "[(! RRR )*. PPP ] mu X. <true> true && [ RRR ]false && [! SSS ] X ");
	   addPattern("After Q", "Response", "[(! QQQ )*. QQQ . true*. PPP ] mu X. <true> true && [! SSS ] X " );
	   addPattern("After QQQ variant", "Response", "[true*. QQQ ]( ([true*. QQQ ]false) => [true*. PPP ] (mu X. <true>true && [! SSS ] X ) ) " );
	   addPattern("Between QQQ and R", "Response", "[true*. QQQ . (! RRR )*. P. (! ( SSS || RRR ))*. RRR ] false");
	   addPattern("Between QQQ and RRR variant", "Response", "[true*. QQQ ]( ([true*. QQQ ]false) => [(! RRR )*. PPP . (! ( SSS || RRR ))*. RRR ] false)");
	   addPattern("After QQQ until R", "Response", "[true*. QQQ . (! RRR )*. PPP ] mu X. <true> true && [ RRR ] false && [! SSS ] X");
	   addPattern("After QQQ until RRR variant", "Response", "[true*. QQQ ]( ([true*. QQQ ]false) => [(! RRR )*. PPP ] (mu X. <true> true && [ RRR ] false && [! SSS ] X)) ");
	   
	   addPattern("Globally", "Response variant", "mu X.( [! PPP ]X && <true>true && [true*. PPP ](mu Y. [! SSS ]Y && <true>true))");
	   addPattern("Before R", "Response variant", "((mu X. [! ( RRR || PPP )]X && <true>true) && [true*. PPP . (! ( RRR || SSS ))*. RRR ]false )");
	   addPattern("Before RRR variant", "Response variant", "mu X. ( [! PPP ]X && <true>true && [true*. PPP ](mu Y.<true> true && [ RRR ]false && [! SSS ] Y))");
	   addPattern("After Q", "Response variant", "[(! QQQ )*. QQQ ]( (mu X. [! PPP ]X && <true>true) && [true*. PPP ](mu Y. <true>true && [! SSS ] Y) )");
	   addPattern("After QQQ variant", "Response variant", "[true*. QQQ ]( ([true*. QQQ ]false) => ( mu X.( [! PPP ]X && <true>true && [true*. PPP ](mu Y. [! SSS ]Y && <true>true)))");
	   addPattern("Between QQQ and R", "Response variant", "[(! QQQ )*. QQQ ]((mu X. [! ( PPP || RRR )]X && <true>true) && [true*. PPP . (! ( RRR || SSS ))*. RRR ]false )");
	   addPattern("Between QQQ and RRR variant", "Response variant", "[true*. QQQ ](([true*. QQQ ]false) => ((mu X. [! ( PPP || RRR )]X && <true>true) && [true*. PPP . (! ( RRR || SSS ))*. RRR ]false ))");
	   addPattern("After QQQ until R", "Response variant", "[true*. QQQ ]( mu X. ( [! ( PPP || RRR )]X && <true>true && [true*. PPP ](mu Y.<true> true && [ RRR ]false && [! SSS ] Y)) )");
	   addPattern("After QQQ until RRR variant", "Response variant", "[true*. QQQ ](([true*. QQQ ]false) =>  mu X. ( [! ( PPP || RRR )]X && <true>true && [true*. PPP ](mu Y.<true> true && [ RRR ]false && [! SSS ] Y)))");
	   
	   addPattern("Globally", "Precedence Chain 1", "[(! SSS )*. (nil | ( SSS . (! TTT )*)). PPP ] false " );
	   addPattern("Before R", "Precedence Chain 1", " [(! ( SSS || RRR ))*. (nil | ( SSS . (! ( TTT || RRR ))*)). PPP . (! RRR )*. RRR ] false ");
	   addPattern("Before RRR variant", "Precedence Chain 1", " [(! ( SSS || RRR ))*. (nil | ( SSS . (! ( TTT || RRR ))*)). PPP ] false ");
	   addPattern("After Q", "Precedence Chain 1", " [(! QQQ )*. QQQ . (! SSS )*. (nil | (S . (! TTT )*)). PPP ] false ");
	   addPattern("After QQQ variant", "Precedence Chain 1", " [true*. QQQ ]( ([true*. QQQ ]false) => [(! SSS )*. (nil | (S . (! TTT )*)). PPP ] false) ");
	   // TODO: the above is ! "or", it's |
	   // TODO: the above can be simplified to remove the nil; example: [A*.(nil || R).Q]false = [A*.R.Q]false && [A*.Q]false = [A*.R.Q + A*.R]false
	   addPattern("Between QQQ and R", "Precedence Chain 1", "[true*. QQQ . (! ( SSS || RRR ))*. (nil | ( SSS . (! ( TTT || RRR ))*)). PPP .(! RRR )*. RRR ] false");
	   addPattern("Between QQQ and RRR variant", "Precedence Chain 1", "[true*.Q ](([true*. QQQ ]false) =>[(! ( SSS || RRR ))*. (nil | ( SSS . (! ( TTT || RRR ))*)). PPP .(! RRR )*. RRR ] false ");
	   addPattern("After QQQ until R", "Precedence Chain 1", " [true*. QQQ . (! ( SSS || RRR ))*. (nil | ( SSS . (! ( TTT || RRR ))*)). PPP ] false ");
	   addPattern("After QQQ until RRR variant", "Precedence Chain 1", "[true*. QQQ ](([true*. QQQ ]false) => [(! ( SSS || RRR ))*. (nil | ( SSS . (! ( TTT || RRR ))*)). PPP ] false ) ");
	  
	   addPattern("Globally", "Precedence Chain 2", " [(! PPP )*. SSS . (! TTT )*. TTT ] false ");
	   addPattern("Before R", "Precedence Chain 2", " [(! ( PPP || RRR ))*. SSS . (! ( TTT || RRR ))*. TTT . (! RRR )*. RRR ] false ");
	   addPattern("Before RRR variant", "Precedence Chain 2", "[ (! ( PPP || RRR ))*. SSS . (! ( TTT || RRR ))*. TTT ] false");
	   addPattern("After Q", "Precedence Chain 2", "[(! QQQ )*. QQQ . (! PPP )*. SSS . (! TTT )*. TTT ] false ");
	   addPattern("After QQQ variant", "Precedence Chain 2", "[true*. QQQ ]( ([true*. QQQ ]false) => [ (! PPP )*. SSS . (! TTT )*. TTT ] false) ");
	   addPattern("Between QQQ and R", "Precedence Chain 2", "  [true*. QQQ . (! ( PPP || RRR ))*. SSS . (! ( TTT || RRR ))*. TTT . (! RRR )*. RRR ] false ");
	   addPattern("Between QQQ and RRR variant", "Precedence Chain 2", "[true*. QQQ ]( ([true*. QQQ ]false) => [(! ( PPP || RRR ))*. SSS . (! ( TTT || RRR ))*. TTT . (! RRR )*. RRR ] false ) ");
	   addPattern("After QQQ until R", "Precedence Chain 2", " [true*. QQQ . (! ( PPP || RRR ))*. SSS . (! ( TTT || RRR ))*. TTT ] false ");
	   addPattern("After QQQ until RRR variant", "Precedence Chain 2", "[true*. QQQ ]( ([true*. QQQ ]false) => [(! ( PPP || RRR ))*. SSS . (! ( TTT || RRR ))*. TTT ] false )");
	   
	   addPattern("Globally", "Response Chain 1", " [true*. SSS . (! TTT )*. TTT ] mu X. <true> true && [! PPP ] X ");
	   addPattern("Before R", "Response Chain 1", " [true*. SSS . (! TTT )*. TTT . (! PPP )*. RRR ] false ");
	   addPattern("Before RRR variant", "Response Chain 1", " [(! ( SSS || RRR ))*. SSS . (! ( TTT || RRR ))*. TTT ] mu X. <true> true && [ RRR ] false && [! PPP ] X");
	   addPattern("After Q", "Response Chain 1", " [(! QQQ )*. QQQ . true*. SSS . (! TTT )*. TTT ] mu X. <true> true && [! PPP ] X " );
	   addPattern("After QQQ variant", "Response Chain 1", " [true*. QQQ ](([true*. QQQ ]false) => [ true*. SSS . (! TTT )*. TTT ] (mu X. <true> true && [! PPP ] X)) " );
	   addPattern("Between QQQ and R", "Response Chain 1", "[true*. QQQ . (! ( SSS || RRR ))*. SSS . (! ( TTT || RRR ))*. TTT .(! ( PPP || RRR ))*. RRR ] false");
	   addPattern("Between QQQ and RRR variant", "Response Chain 1", "[true*. QQQ ](([true*. QQQ ]false) => [(! ( SSS || RRR ))*. SSS . (! ( TTT || RRR ))*. TTT .(! ( PPP || RRR ))*. RRR ] false)");
	   addPattern("After QQQ until R", "Response Chain 1", "[true*. QQQ . (! ( SSS || RRR ))*. SSS . (! ( TTT || RRR ))*. TTT ] mu X. <true> true && [ RRR ] false && [! PPP ] X ");
	   addPattern("After QQQ until RRR variant", "Response Chain 1", "[true*. QQQ ](([true*. QQQ ]false) => [(! ( SSS || RRR ))*. SSS . (! ( TTT || RRR ))*. TTT ] mu X. <true> true && [ RRR ] false && [! PPP ] X )");
	   
	   addPattern("Globally", "Response Chain 2", "[true*. PPP ] mu X. <true> true && [ SSS ] mu Y. (<true> true && [! TTT ] Y) && [! SSS ] X " );
	   addPattern("Before R", "Response Chain 2", "[(! RRR )*. PPP . (! ( SSS || RRR ))*. (nil | (S . (! ( TTT || RRR ))*)). RRR ] false ");
	   addPattern("Before RRR variant", "Response Chain 2", "[(! RRR )*. PPP ] mu X. <true> true && [ RRR ] false && [ SSS ] mu Y. (<true> true && [ RRR ] false && [! TTT ] Y) && [! SSS ] X ");
	   addPattern("After Q", "Response Chain 2", "[(! QQQ )*. QQQ . true*. PPP ] mu X. <true> true && [ SSS ] mu Y. (<true> true && [! TTT ] Y) && [! SSS ] X ");
	   addPattern("After QQQ variant", "Response Chain 2", "[true*. QQQ ]( ([true*. QQQ ]false) => [true*. PPP ] (mu X. <true> true && [ SSS ] mu Y. (<true> true && [! TTT ] Y) && [! SSS ] X)) ");
	   addPattern("Between QQQ and R", "Response Chain 2", "[true*. QQQ . (! RRR )*. PPP . (! ( SSS || RRR ))*. (nil | ( SSS . (! ( TTT || RRR ))*)). RRR ] false ");
	   addPattern("Between QQQ and RRR variant", "Response Chain 2", "[true*. QQQ ]( ([true*. QQQ ]false) => [(! RRR )*. PPP . (! ( SSS || RRR ))*. (nil | ( SSS . (! ( TTT || RRR ))*)). RRR ] false )");
	   addPattern("After QQQ until R", "Response Chain 2", "[true*. QQQ . (! RRR )*. PPP ] mu X. <true> true && [ RRR ] false && [ SSS ] mu Y. (<true> true && [ RRR ] false && [! TTT ] Y) && [! SSS ] X ");
	   addPattern("After QQQ until RRR variant", "Response Chain 2", "[true*. Q]( ([true*. Q]false) => [(! R)*. P] mu X. <true> true && [R] false && [S] mu Y. (<true> true && [R] false && [! T] Y) && [! S] X) ");
	   
	   
	   addPattern("Globally", "Constrained Response Chain 2", "[true*. PPP ] mu X . <true> true && [ SSS ] mu Y. (<true> true && [ ZZZ ] false && [! TTT ] Y) && [! SSS ] X " );
	   addPattern("Before R", "Constrained Response Chain 2", "[(! RRR )*. PPP . (! ( SSS || RRR ))*. (nil | ( SSS . (nil | ((! ( TTT || RRR ))*. ZZZ )). (! ( TTT || RRR ))*)). RRR ] false ");
	   addPattern("Before RRR variant", "Constrained Response Chain 2", "[(! RRR )*. PPP ] mu X. <true> true && [ RRR ] false && [ SSS ] mu Y. (<true> true && [ ZZZ || RRR ] false && [! TTT ] Y) && [! SSS ] X ");
	   addPattern("After Q", "Constrained Response Chain 2", "[(! QQQ )*. QQQ . true*. PPP ] mu X. <true> true && [ SSS ] mu Y. (<true> true && [ ZZZ ] false && [! TTT ] Y) && [! SSS ] X");
	   addPattern("After QQQ variant", "Constrained Response Chain 2", "[true*. QQQ ]( ([true*. QQQ ]false) => [true*. PPP ]( mu X. <true> true && [ SSS ] mu Y. (<true> true && [ ZZZ ] false && [! TTT ] Y) && [! SSS ] X))");
	   addPattern("Between QQQ and R", "Constrained Response Chain 2", "[true*. QQQ . (! RRR )*. PPP . (! ( SSS || RRR ))*. (nil | ( SSS . (nil | ((! ( TTT || RRR ))*. ZZZ )). (! ( TTT || RRR ))*)). RRR ] false ");
	   addPattern("Between QQQ and RRR variant", "Constrained Response Chain 2", "[true*. QQQ ]( ([true*. QQQ ]false) => [(! RRR )*. PPP . (! ( SSS || RRR ))*. (nil | ( SSS . (nil | ((! ( TTT || RRR ))*. ZZZ )). (! ( TTT || RRR ))*)). R] false )");
	   addPattern("After QQQ until R", "Constrained Response Chain 2", "[true*. QQQ . (! RRR )*. PPP ] mu X. <true> true && [ RRR ] false && [ SSS ] mu Y. (<true> true && [ ZZZ || RRR ] false && [! TTT ] Y) && [! SSS ] X ");
	   addPattern("After QQQ until RRR variant", "Constrained Response Chain 2", "[true*. QQQ ]( ([true*. QQQ ]false) => [(! RRR )*. PPP ] mu X. <true> true && [ RRR ] false && [ SSS ] mu Y. (<true> true && [ ZZZ || RRR ] false && [! TTT ] Y) && [! SSS ] X) ");
	   
	   
	  addPattern("Globally", "Bounded Existence", " [(! PPP )*. PPP . (! PPP )*. PPP ] false ");
	  addPattern("Before R", "Bounded Existence", " [(! RRR )*. PPP . (! RRR )*. PPP . (! RRR )*. RRR ] false ");
	  addPattern("Before RRR variant", "Bounded Existence", " [(! RRR )*. PPP . (! RRR )*. PPP ] false ");
	  addPattern("After Q", "Bounded Existence", " [(! QQQ )*. QQQ . (! PPP )*. PPP . (! PPP )*. PPP ] false ");
	  addPattern("After QQQ variant", "Bounded Existence", "[true*. QQQ ]( ([true*. QQQ ]false) => [(! PPP )*. PPP . (! PPP )*. PPP ] false )");
	  addPattern("Between QQQ and R", "Bounded Existence", " [true*. QQQ . (! RRR )*. PPP . (! RRR )*. PPP . (! RRR )*. RRR ] false ");
	  addPattern("Between QQQ and RRR variant", "Bounded Existence", "[true*. QQQ ]( ([true*. QQQ ]false) => [(! RRR )*. PPP . (! RRR )*. PPP . (! RRR )*. RRR ] false )");
	  addPattern("After QQQ until R", "Bounded Existence" , " [true*. QQQ . (! RRR )*. PPP . (! RRR )*. PPP ] false ");
	  addPattern("After QQQ until RRR variant", "Bounded Existence" , "[true*. QQQ ]( ([true*. QQQ ]false) => [(! RRR )*. PPP . (! RRR )*. PPP ] false ) ");
	  ////  ////  ////  ////  ////  ////  ////  ////  ////  ////  ////  ////  ////  ////
	   addPatternMonitorable("Globally", "Absence", Boolean.TRUE );
	   addPatternMonitorable("Before R", "Absence", Boolean.TRUE );
	   addPatternMonitorable("Before RRR variant", "Absence", Boolean.TRUE );
	   addPatternMonitorable("After Q", "Absence", Boolean.TRUE );
	   addPatternMonitorable("After QQQ variant", "Absence", Boolean.FALSE ); //TODO: check After QQQ variants if they are monitorable, they have implication sign! 
	   addPatternMonitorable("Between QQQ and R", "Absence",Boolean.TRUE );
	   addPatternMonitorable("Between QQQ and RRR variant", "Absence",Boolean.FALSE ); //TODO
	   addPatternMonitorable("After QQQ until R","Absence", Boolean.TRUE );
	   addPatternMonitorable("After QQQ until RRR variant","Absence", Boolean.FALSE ); //TODO
	   
	   addPatternMonitorable("Globally", "Existence",Boolean.FALSE );
	   addPatternMonitorable("Before R", "Existence", Boolean.TRUE );
	   addPatternMonitorable("Before RRR variant", "Existence", Boolean.FALSE );
	   addPatternMonitorable("After Q", "Existence", Boolean.FALSE );
	   addPatternMonitorable("After QQQ variant", "Existence", Boolean.FALSE );//TODO: check After QQQ variants if they are monitorable, they have implication sign! 
	   addPatternMonitorable("Between QQQ and R", "Existence", Boolean.TRUE );
	   addPatternMonitorable("Between QQQ and RRR variant", "Existence", Boolean.FALSE ); //TODO
	   addPatternMonitorable("After QQQ until R", "Existence", Boolean.FALSE );
	   addPatternMonitorable("After QQQ until RRR variant", "Existence", Boolean.FALSE ); //TODO
	   
	   addPatternMonitorable("Globally", "Universality", Boolean.TRUE );
	   addPatternMonitorable("Before R", "Universality", Boolean.TRUE );
	   addPatternMonitorable("Before RRR variant", "Universality", Boolean.FALSE ); //TODO: check
	   addPatternMonitorable("After Q", "Universality", Boolean.TRUE );
	   addPatternMonitorable("After QQQ variant", "Universality", Boolean.FALSE );//TODO: check After QQQ variants if they are...
	   addPatternMonitorable("Between QQQ and R", "Universality", Boolean.TRUE );
	   addPatternMonitorable("Between QQQ and RRR variant", "Universality", Boolean.FALSE ); //TODO
	   addPatternMonitorable("After QQQ until R", "Universality", Boolean.TRUE );
	   addPatternMonitorable("After QQQ until RRR variant", "Universality", Boolean.TRUE ); //TODO
	   
	   addPatternMonitorable("Globally", "Precedence", Boolean.TRUE );
	   addPatternMonitorable("Before R" , "Precedence" , Boolean.TRUE );
	   addPatternMonitorable("Before RRR variant" , "Precedence" , Boolean.TRUE );
	   addPatternMonitorable("After Q", "Precedence", Boolean.TRUE );
	   addPatternMonitorable("After QQQ variant", "Precedence", Boolean.FALSE );//TODO: check After QQQ variants if they are...
	   addPatternMonitorable("Between QQQ and R", "Precedence", Boolean.TRUE );
	   addPatternMonitorable("Between QQQ and RRR variant", "Precedence", Boolean.FALSE ); //TODO
	   addPatternMonitorable("After QQQ until R", "Precedence",  Boolean.TRUE );
	   addPatternMonitorable("After QQQ until RRR variant", "Precedence",  Boolean.FALSE ); //TODO
	   
	   addPatternMonitorable("Globally", "Precedence variant", Boolean.FALSE ); //TODO
	   addPatternMonitorable("Before R" , "Precedence variant" , Boolean.FALSE );
	   addPatternMonitorable("Before RRR variant" , "Precedence variant" , Boolean.FALSE );
	   addPatternMonitorable("After Q", "Precedence variant", Boolean.FALSE );
	   addPatternMonitorable("After QQQ variant", "Precedence variant", Boolean.FALSE );//TODO: check After QQQ variants if they are...
	   addPatternMonitorable("Between QQQ and R", "Precedence variant", Boolean.FALSE );
	   addPatternMonitorable("Between QQQ and RRR variant", "Precedence variant", Boolean.FALSE ); //TODO
	   addPatternMonitorable("After QQQ until R", "Precedence variant",  Boolean.FALSE );
	   addPatternMonitorable("After QQQ until RRR variant", "Precedence variant",  Boolean.FALSE ); //TODO
	   
	   addPatternMonitorable("Globally", "Response", Boolean.FALSE );
	   addPatternMonitorable("Before R", "Response", Boolean.TRUE );
	   addPatternMonitorable("Before RRR variant", "Response", Boolean.FALSE );
	   addPatternMonitorable("After Q", "Response", Boolean.FALSE );
	   addPatternMonitorable("After QQQ variant", "Response", Boolean.FALSE );//TODO: check After QQQ variants if they are...
	   addPatternMonitorable("Between QQQ and R", "Response", Boolean.TRUE );
	   addPatternMonitorable("Between QQQ and RRR variant", "Response", Boolean.FALSE ); //TODO
	   addPatternMonitorable("After QQQ until R", "Response", Boolean.FALSE );
	   addPatternMonitorable("After QQQ until RRR variant", "Response", Boolean.FALSE ); //TODO
	   
	   addPatternMonitorable("Globally", "Response variant", Boolean.FALSE );
	   addPatternMonitorable("Before R", "Response variant", Boolean.FALSE );
	   addPatternMonitorable("Before RRR variant", "Response variant", Boolean.FALSE );
	   addPatternMonitorable("After Q", "Response variant", Boolean.FALSE );
	   addPatternMonitorable("After QQQ variant", "Response variant", Boolean.FALSE );//TODO: check After QQQ variants if they are...
	   addPatternMonitorable("Between QQQ and R", "Response variant", Boolean.FALSE );
	   addPatternMonitorable("Between QQQ and RRR variant", "Response variant", Boolean.FALSE ); //TODO
	   addPatternMonitorable("After QQQ until R", "Response variant", Boolean.FALSE );
	   addPatternMonitorable("After QQQ until RRR variant", "Response variant", Boolean.FALSE ); //TODO
	   
	   addPatternMonitorable("Globally", "Precedence Chain 1", Boolean.TRUE );
	   addPatternMonitorable("Before R", "Precedence Chain 1", Boolean.TRUE);
	   addPatternMonitorable("Before RRR variant", "Precedence Chain 1", Boolean.TRUE);
	   addPatternMonitorable("After Q", "Precedence Chain 1", Boolean.TRUE);
	   addPatternMonitorable("After QQQ variant", "Precedence Chain 1", Boolean.FALSE);//TODO: check After QQQ variants if they are...
	   addPatternMonitorable("Between QQQ and R", "Precedence Chain 1", Boolean.TRUE);
	   addPatternMonitorable("Between QQQ and RRR variant", "Precedence Chain 1", Boolean.FALSE); //TODO
	   addPatternMonitorable("After QQQ until R", "Precedence Chain 1", Boolean.TRUE);
	   addPatternMonitorable("After QQQ until RRR variant", "Precedence Chain 1", Boolean.FALSE); //TODO
	  
	   
	   addPatternMonitorable("Globally", "Precedence Chain 2", Boolean.TRUE);
	   addPatternMonitorable("Before R", "Precedence Chain 2", Boolean.TRUE);
	   addPatternMonitorable("Before R", "Precedence Chain 2", Boolean.TRUE);
	   addPatternMonitorable("After Q", "Precedence Chain 2", Boolean.TRUE);
	   addPatternMonitorable("After QQQ variant", "Precedence Chain 2", Boolean.FALSE);//TODO: check After QQQ variants if they are...
	   addPatternMonitorable("Between QQQ and R", "Precedence Chain 2", Boolean.TRUE);
	   addPatternMonitorable("After QQQ until R", "Precedence Chain 2",Boolean.TRUE);
	   addPatternMonitorable("Between QQQ and RRR variant", "Precedence Chain 2", Boolean.FALSE); //TODO
	   addPatternMonitorable("After QQQ until RRR variant", "Precedence Chain 2",Boolean.FALSE); //TODO
	   
	   addPatternMonitorable("Globally", "Response Chain 1", Boolean.FALSE);
	   addPatternMonitorable("Before R", "Response Chain 1", Boolean.TRUE);
	   addPatternMonitorable("Before RRR variant", "Response Chain 1", Boolean.FALSE);
	   addPatternMonitorable("After Q", "Response Chain 1", Boolean.FALSE );
	   addPatternMonitorable("After QQQ variant", "Response Chain 1", Boolean.FALSE );//TODO: check After QQQ variants if they are...
	   addPatternMonitorable("Between QQQ and R", "Response Chain 1", Boolean.TRUE);
	   addPatternMonitorable("After QQQ until R", "Response Chain 1", Boolean.FALSE);
	   addPatternMonitorable("Between QQQ and RRR variant", "Response Chain 1", Boolean.FALSE); //TODO
	   addPatternMonitorable("After QQQ until RRR variant", "Response Chain 1", Boolean.FALSE); //TODO
	   
	   addPatternMonitorable("Globally", "Response Chain 2", Boolean.FALSE);
	   addPatternMonitorable("Before R", "Response Chain 2", Boolean.TRUE);
	   addPatternMonitorable("Before RRR variant", "Response Chain 2", Boolean.FALSE);
	   addPatternMonitorable("After Q", "Response Chain 2", Boolean.FALSE);
	   addPatternMonitorable("After QQQ variant", "Response Chain 2", Boolean.FALSE);//TODO: check After QQQ variants if they are...
	   addPatternMonitorable("Between QQQ and R", "Response Chain 2", Boolean.TRUE);
	   addPatternMonitorable("After QQQ until R", "Response Chain 2", Boolean.FALSE);
	   addPatternMonitorable("Between QQQ and RRR variant", "Response Chain 2", Boolean.FALSE); //TODO
	   addPatternMonitorable("After QQQ until RRR variant", "Response Chain 2", Boolean.FALSE); //TODO
	   
	   
	   addPatternMonitorable("Globally", "Constrained Response Chain 2", Boolean.FALSE );
	   addPatternMonitorable("Before R", "Constrained Response Chain 2", Boolean.TRUE);
	   addPatternMonitorable("Before RRR variant", "Constrained Response Chain 2", Boolean.FALSE);
	   addPatternMonitorable("After Q", "Constrained Response Chain 2", Boolean.FALSE);
	   addPatternMonitorable("After QQQ variant", "Constrained Response Chain 2", Boolean.FALSE);//TODO: check After QQQ variants if they are...
	   addPatternMonitorable("Between QQQ and R", "Constrained Response Chain 2", Boolean.TRUE);
	   addPatternMonitorable("After QQQ until R", "Constrained Response Chain 2", Boolean.FALSE);
	   addPatternMonitorable("Between QQQ and RRR variant", "Constrained Response Chain 2", Boolean.FALSE); //TODO
	   addPatternMonitorable("After QQQ until RRR variant", "Constrained Response Chain 2", Boolean.FALSE); //TODO
	   
	   
	   addPatternMonitorable("Globally", "Bounded Existence", Boolean.TRUE);
	   addPatternMonitorable("Before R", "Bounded Existence", Boolean.TRUE);
	   addPatternMonitorable("Before RRR variant", "Bounded Existence", Boolean.TRUE);
	   addPatternMonitorable("After Q", "Bounded Existence", Boolean.TRUE);
	   addPatternMonitorable("After QQQ variant", "Bounded Existence", Boolean.FALSE);//TODO: check After QQQ variants if they are...
	   addPatternMonitorable("Between QQQ and R", "Bounded Existence", Boolean.TRUE);
	   addPatternMonitorable("After QQQ until R", "Bounded Existence" , Boolean.TRUE);
	   addPatternMonitorable("Between QQQ and RRR variant", "Bounded Existence", Boolean.FALSE); //TODO
	   addPatternMonitorable("After QQQ until RRR variant", "Bounded Existence" , Boolean.FALSE); //TODO
	  
//	  Questionnaire.answ12.setScope("Globally");
	  Questionnaire.answ12.setScope("Globally");
	  Questionnaire.answ111111.setScope("After Q");
	  Questionnaire.answ111112.setScope("After QQQ variant"); 
	  
	  Questionnaire.answ111211.setScope("Before RRR variant"); 
	  Questionnaire.answ111212.setScope("Before R"); 
	  
	  Questionnaire.answ1131111.setScope("After QQQ until R");
	  Questionnaire.answ1131211.setScope("After QQQ until RRR variant"); 

	  Questionnaire.answ1131112.setScope("Between QQQ and R");
	  Questionnaire.answ1131212.setScope("Between QQQ and RRR variant"); 

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
   
   public void printOut(){
	   System.out.println(patterns.get("Globally").get("Response Chain 2"));
   }

}
	
