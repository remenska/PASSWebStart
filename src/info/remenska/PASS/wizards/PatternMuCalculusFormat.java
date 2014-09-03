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
			  patterns.put("Globally", new HashMap<String, String>());
			  patterns.put("Before R", new HashMap<String, String>());
			  patterns.put("Before R variant", new HashMap<String, String>()); //like until, if #R# does ! happen, the property still must hold
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
			  patternsMonitorable.put("Between Q and R variant", new HashMap<String, Boolean>()); // only the last #Q# starts the interval, similar to after #Q# variant
			  patternsMonitorable.put("After Q until R", new HashMap<String, Boolean>());
			  patternsMonitorable.put("After Q until R variant", new HashMap<String, Boolean>()); // only the last #Q# starts the interval, similar to after #Q# variant
				  
	   addPattern("Globally", "Absence", "[true*. #P# ] false" ); 
	   addPattern("Before R", "Absence", "[(! #R# )*. #P# . (! #R# )*. #R# ] false"); 
	   addPattern("Before R variant", "Absence", "[(! #R# )*. #P# ] false"); 
	   addPattern("After Q", "Absence", "[(! #Q# )*. #Q# . true*. #P# ] false");
//	   addPattern("After Q variant", "Absence", "[true*. #Q# (! Q)*](([true*. Q]false) => [true*. P]false)");
	   addPattern("After Q variant", "Absence", "[true*. #Q# ](([true*. #Q# ]false) => [true*. ( #P# + #Q# )]false)"); 
	   addPattern("Between Q and R", "Absence", "[true*. #Q# . (! #R# )*. #P# . (! #R# )*. #R# ] false" );
	   addPattern("Between Q and R variant", "Absence", "[true*. #Q# ](([true*. #Q# ]false) => [(! #R# )*. #P# . (! #R# )*. #R# ] false) " ); 
	   addPattern("After Q until R", "Absence", "[true*. #Q# . (! #R# )*. #P# ] false");
	   addPattern("After Q until R variant", "Absence", "[true*. #Q# ](([true*. #Q# ]false) => [(! #R# )*. #P# ] false)");

	   addPattern("Globally", "ExistenceUnfair","mu X. <true> true && [! #P# ] X" );
	   addPattern("Before R", "ExistenceUnfair", "[(! #P# )*. #R# ] false");
	   addPattern("Before R variant", "ExistenceUnfair", "mu X. ( [! #P# ]X && [ #R# ]false && <true>true)");
	   addPattern("After Q", "ExistenceUnfair", "[(! #Q# )*. #Q# ] mu X. (<true> true && [! #P# ] X)");
	   addPattern("After Q variant", "ExistenceUnfair", "[true*. #Q# ](([true*. #Q# ] false) => mu X. ([! #P# ]X && <true>true) && [ #Q# ]false)");
	   addPattern("Between Q and R", "ExistenceUnfair", "[true*. #Q# . (! ( #P# || #R# ))*. #R# ] false");
	   addPattern("Between Q and R variant","ExistenceUnfair", "[true*. #Q# ](([true*. #Q# ] false) => [(! ( #P# || #R# ))*. #R# ] false)");
	   addPattern("After Q until R", "ExistenceUnfair", "[true*. #Q# ] mu X. <true> true && [ #R# ] false && [! #P# ] X");
	   addPattern("After Q until R variant", "ExistenceUnfair", "[true*. #Q# ](([true*. #Q# ] false) =>  mu X. <true> true && [ #R# ] false && [! #P# ] X) ");
	   
	   // NEW NEW NEW
	   addPattern("Globally", "ExistenceFair", "[ (! #P# )* ] <(! #P# )*. #P# >true" ); 
	   addPattern("Before R", "ExistenceFair", "[(! #P# )*. #R# ] false"); 
	   addPattern("Before R variant", "ExistenceFair", "[ (! ( #P# || #R# ))* ) ] <(! #R# )*. #P# > true"); 
	   addPattern("After Q", "ExistenceFair", "[(! #Q# )*. #Q# . (! #P# )*]<(! #P# )*. #P#> true"); 
	   addPattern("After Q variant", "ExistenceFair", "[true*. #Q# ](([true*. #Q# ] false) => [ (! #P# )* ] <(! #P# )*. #P# >true)"); 
	   addPattern("Between Q and R", "ExistenceFair", "[true*. #Q# . (! ( #P# || #R# ))*. #R# ] false"); //ok
	   addPattern("Between Q and R variant","ExistenceFair", "[true*. #Q# ](([true*. #Q# ] false) => [(! ( #P# || #R# ))*. #R# ] false)");
	   addPattern("After Q until R", "ExistenceFair", "[true*. #Q# . (!( #P# || #R# ))*] <(! #R# )*. #P# >true "); // ok
	   addPattern("After Q until R variant", "ExistenceFair", "[true*. #Q# ](([true*. #Q# ] false) => [ (!( #P# || #R# ))*] <(! #R# )*. #P# >true  ) "); 
	   
	   addPattern("Globally", "Universality", "[true*. #P# ] false ");
	   addPattern("Before R", "Universality", "[(! #R# )*. #P# . (! #R# )*. #R# ] false ");
	   addPattern("Before R variant", "Universality", "[(! #R# )*. #P# ] false "); 
	   addPattern("After Q", "Universality", "[(! #Q# )*. #Q# . true*. #P# ] false ");
	   addPattern("Between Q and R", "Universality", "[true*. #Q# . (! #R# )*. #P# . (! #R# )*. #R# ] false ");
	   addPattern("After Q variant", "Universality", "[true*. #Q# ](([true*. #Q# ]false) => [true*. ( #P# + #Q# )]false )"); 
	   addPattern("After Q until R", "Universality", "[true*. #Q# . (! #R# )*. #P# ] false ");
	   addPattern("Between Q and R variant", "Universality", "[true*. #Q# ](([true*. #Q# ]false ) => [(! #R# )*. #P# . (! #R# )*. #R# ] false) " ); 
	   
	   addPattern("Globally", "Precedence", "[(! #P# )*. #S# ] false ");
	   addPattern("Before R" , "Precedence" , "[(! ( #P# || #R# ))*. #S# . (! #R# )*. #R# ] false " );
	   addPattern("Before R variant" , "Precedence" , "[ (! ( #P# || #R# ))*. #S# ] false " );
	   addPattern("After Q", "Precedence", "[(! #Q# )*. #Q# . (! #P# )*. #S# ] false "); 
	   addPattern("After Q variant", "Precedence", "[true*. #Q# ](([true*. #Q# ]false) => [(! #P# )*. #S# ] false)  ");
	   addPattern("Between Q and R", "Precedence", "[true*. #Q# . (! ( #P# || #R# ))*. #S# . (! #R# )*. #R# ] false");
	   addPattern("Between Q and R variant", "Precedence", "[true*. #Q# ](([true*. #Q# ]false) => [(! ( #P# || #R# ))*. #S# . (! #R# )*. #R# ] false)");
	   addPattern("After Q until R", "Precedence", "[true*. #Q# . (! ( #P# || #R# ))*. #S# ] false ");
	   addPattern("After Q until R variant", "Precedence", "[true*. #Q# ](([true*. #Q# ]false) => [(! ( #P# || #R# ))*. #S# ] false )");
	
	   addPattern("Globally", "Precedence variant", "mu X. ([! #P# ] X && <true>true && [ #S# ]false)");
	   addPattern("Before R" , "Precedence variant" , "mu X.[! ( #P# || #R# )]X && <true>true && [ #S# . (! #R# ). #R# ] false" );
	   addPattern("Before R variant", "Precedence variant" , "mu X.[! ( #P# || #R# )]X && <true>true && [ #S# ] false" );
	   addPattern("After Q",  "Precedence variant" , "[(! #Q# )*. #Q# ] mu X. ([! #P# ] X && <true>true && [ #S# ]false)" );
	   addPattern("After Q variant", "Precedence variant" , "[true*. #Q# ](([true*. #Q# ]false) => mu X. ([! #P# ] X && <true>true && [ #S# ]false)" );
	   addPattern("Between Q and R", "Precedence variant" , "[(! #Q# )*. #Q# ] mu X.[! ( #P# || #R# )]X && <true>true && [ #S#. (! #R# ). #R# ] false" );
	   addPattern("Between Q and R variant",  "Precedence variant" , "[true*. #Q# ](([true*. #Q# ]false) => mu X.[! ( #P# || #R# )]X && <true>true && [ #S# . (! #R# ). #R# ] false" );
	   addPattern("After Q until R", "Precedence variant" , "[(! #Q# )*. #Q# ] mu X. ([! ( #P# || #R# )] X && <true>true && [ #S# ]false)" );
	   addPattern("After Q until R variant", "Precedence variant" , "[true*. #Q# ](([true*. #Q# ]false) => mu X.[! ( #P# || #R# )]X && <true>true && [ #S# ] false" );
	   
	   addPattern("Globally", "ResponseUnfair", "[true*. #P# ] mu X. <true> true && [! #S# ] X ");
	   addPattern("Before R", "ResponseUnfair", "[(! #R# )*. #P# . (! ( #S# || #R# ))*. #R# ] false ");
	   addPattern("Before R variant", "ResponseUnfair", "[(! #R# )*. #P# ] mu X. <true> true && [ #R# ]false && [! #S# ] X ");
	   addPattern("After Q", "ResponseUnfair", "[(! #Q# )*. #Q# . true*. #P# ] mu X. <true> true && [! #S# ] X " );
	   addPattern("After Q variant", "ResponseUnfair", "[true*. #Q# ]( ([true*. #Q# ]false) => [true*. #P# ] (mu X. <true>true && [! #S# ] X ) ) " );
	   addPattern("Between Q and R", "ResponseUnfair", "[true*. #Q# . (! #R# )*. #P#. (! ( #S# || #R# ))*. #R# ] false");
	   addPattern("Between Q and R variant", "ResponseUnfair", "[true*. #Q# ]( ([true*. #Q# ]false) => [(! #R# )*. #P# . (! ( #S# || #R# ))*. #R# ] false)");
	   addPattern("After Q until R", "ResponseUnfair", "[true*. #Q# . (! #R# )*. #P# ] mu X. <true> true && [ #R# ] false && [! #S# ] X");
	   addPattern("After Q until R variant", "ResponseUnfair", "[true*. #Q# ]( ([true*. #Q# ]false) => [(! #R# )*. #P# ] (mu X. <true> true && [ #R# ] false && [! #S# ] X)) ");
	   
	   // NEW NEW NEW
	   addPattern("Globally", "ResponseFair", "[true*. #P# . (! #S# )*] <true*. #S# > true");
	   addPattern("Before R", "ResponseFair", "[(! #R# )*. #P# . (! ( #S# || #R# ))*. #R# ] false ");
	   addPattern("Before R variant", "ResponseFair", "[(! #R# )*. #P# . (! ( #S# || #R# ))*] <(! #R# )*. #S# > true"); 
	   addPattern("After Q", "ResponseFair", "[(! #Q# )*. #Q# . true*. #P# . (! #S )*] <true*. #S# > true" );
	   addPattern("After Q variant", "ResponseFair", "[true*. #Q# ]( ([true*. #Q# ]false) => [true*. #P# . (! #S# )*] <true*. #S# > true ) " ); 
	   addPattern("Between Q and R", "ResponseFair", "[true*. #Q# . (! #R# )*. #P#. (! ( #S# || #R# ))*. #R# ] false");
	   addPattern("Between Q and R variant", "ResponseFair", "[true*. #Q# ]( ([true*. #Q# ]false) => [(! #R# )*. #P# . (! ( #S# || #R# ))*. #R# ] false)"); 
	   addPattern("After Q until R", "ResponseFair", "[true*. #Q# . (! #R# )*. #P# . (! ( #S# || #R# ))*] <(! #R# )*.  #S# > true");                           
	   addPattern("After Q until R variant", "ResponseFair", "[true*. #Q# ]( ([true*. #Q# ]false) => [(! #R# )*. #P# .(! ( #S# || #R# ))* ]<(! #R# )*. #S# > true) ");
	   
	   addPattern("Globally", "Response variant Unfair", "mu X.( [! #P# ]X && <true>true && [true*. #P# ](mu Y. [! #S# ]Y && <true>true))");
	   addPattern("Before R", "Response variant Unfair", "((mu X. [! ( #R# || #P# )]X && <true>true) && [true*. #P# . (! ( #R# || #S# ))*. #R# ]false )");
	   addPattern("Before R variant", "Response variant Unfair", "mu X. ( [! #P# ]X && <true>true && [true*. #P# ](mu Y.<true> true && [ #R# ]false && [! #S# ] Y))");
	   addPattern("After Q", "Response variant Unfair", "[(! #Q# )*. #Q# ]( (mu X. [! #P# ]X && <true>true) && [true*. #P# ](mu Y. <true>true && [! #S# ] Y) )");
	   addPattern("After Q variant", "Response variant Unfair", "[true*. #Q# ]( ([true*. #Q# ]false) => ( mu X.( [! #P# ]X && <true>true && [true*. #P# ](mu Y. [! #S# ]Y && <true>true)))");
	   addPattern("Between Q and R", "Response variant Unfair", "[(! #Q# )*. #Q# ]((mu X. [! ( #P# || #R# )]X && <true>true) && [true*. #P# . (! ( #R# || #S# ))*. #R# ]false )");
	   addPattern("Between Q and R variant", "Response variant Unfair", "[true*. #Q# ](([true*. #Q# ]false) => ((mu X. [! ( #P# || #R# )]X && <true>true) && [true*. #P# . (! ( #R# || #S# ))*. #R# ]false ))");
	   addPattern("After Q until R", "Response variant Unfair", "[true*. #Q# ]( mu X. ( [! ( #P# || #R# )]X && <true>true && [true*. #P# ](mu Y.<true> true && [ #R# ]false && [! #S# ] Y)) )");
	   addPattern("After Q until R variant", "Response variant Unfair", "[true*. #Q# ](([true*. #Q# ]false) =>  mu X. ( [! ( #P# || #R# )]X && <true>true && [true*. #P# ](mu Y.<true> true && [ #R# ]false && [! #S# ] Y)))");
	  
	   // NEW NEW NEW
	   addPattern("Globally", "Response variant Fair", "[(! #P# )* ]<true*. #P# >true && [true*. #P# . (! #S# )*]<true*. #S# >true");
	   addPattern("Before R", "Response variant Fair", "[ (! ( #R# || #P# ))*] <true*. #P# > true && [true*. #P# . (!( #R# || #S# ))*. #R# ] false");
	   addPattern("Before R variant", "Response variant Fair", "[(! #P# )*] <true*. #P# > true && [true*. #P# . (!( #R || #S#))*]<(! #R# )*. #S# >true");
	   addPattern("After Q", "Response variant Fair", "[(! #Q# )*. #Q# . (! #P# )*]<true*. #P# > true && [true*. #P#. (! #S# )*]<true*. #S# >true");
	   addPattern("After Q variant", "Response variant Fair", "[true*. #Q# ]( ([true*. #Q# ]false) => ([ (! #P# )*]<true*. #P# >true && [true*. #P# . (! #S# )*]<true*. #S# > true))");	   
	   addPattern("Between Q and R", "Response variant Fair", "[(! #Q# )*. #Q# . (!( #P# || #R# ))*]<true*. #P# > && [true*. #P#. (! (#R# || #S#))*. #R# ]false");
	   addPattern("Between Q and R variant", "Response variant Fair", "[true*. #Q# ](([true*. #Q# ]false) => ([(!(#P# || #R#))*]<true*. #P#>true && [true*. #P# . (!( #R# || #S#))* . #R# ]false))");
	   addPattern("After Q until R", "Response variant Fair", "[true*. #Q# . (!( #P# || #R# ))*]<true*. #P# >true && [true*. #P# . (!( #R# || #S# ))*]<(! #R# )*. #S# > true");
	   addPattern("After Q until R variant", "Response variant Fair", "[true*. #Q# ](([true*. #Q# ]false) => ([ (!( #P# || #R#))*]<true*. #P# >true && [true*. #P# . (!( #R# || #S# ))*]<(! #R# )*. #S# > true))");
	
	   
	   addPattern("Globally", "Precedence Chain 1", "[(! #S# )*. (nil | ( #S# . (! #T# )*)). #P# ] false " );
	   addPattern("Before R", "Precedence Chain 1", " [(! ( #S# || #R# ))*. (nil | ( #S# . (! ( #T# || #R# ))*)). #P# . (! #R# )*. #R# ] false ");
	   addPattern("Before R variant", "Precedence Chain 1", " [(! ( #S# || #R# ))*. (nil | ( #S# . (! ( #T# || #R# ))*)). #P# ] false ");
	   addPattern("After Q", "Precedence Chain 1", " [(! #Q# )*. #Q# . (! #S# )*. (nil | (S . (! #T# )*)). #P# ] false ");
	   addPattern("After Q variant", "Precedence Chain 1", " [true*. #Q# ]( ([true*. #Q# ]false) => [(! #S# )*. (nil | (S . (! #T# )*)). #P# ] false) ");
	   
	   // TODO: the above can be simplified to remove the nil; example: [A*.(nil || R).Q]false = [A*.R.Q]false && [A*.Q]false = [A*.R.Q + A*.R]false
	   addPattern("Between Q and R", "Precedence Chain 1", "[true*. #Q# . (! ( #S# || #R# ))*. (nil | ( #S# . (! ( #T# || #R# ))*)). #P# .(! #R# )*. #R# ] false");
	   addPattern("Between Q and R variant", "Precedence Chain 1", "[true*. #Q#](([true*. #Q# ]false) =>[(! ( #S# || #R# ))*. (nil | ( #S# . (! ( #T# || #R# ))*)). #P# .(! #R# )*. #R# ] false ");
	   addPattern("After Q until R", "Precedence Chain 1", " [true*. #Q# . (! ( #S# || #R# ))*. (nil | ( #S# . (! ( #T# || #R# ))*)). #P# ] false ");
	   addPattern("After Q until R variant", "Precedence Chain 1", "[true*. #Q# ](([true*. #Q# ]false) => [(! ( #S# || #R# ))*. (nil | ( #S# . (! ( #T# || #R# ))*)). #P# ] false ) ");
	  
	   addPattern("Globally", "Precedence Chain 2", " [(! #P# )*. #S# . (! #T# )*. #T# ] false ");
	   addPattern("Before R", "Precedence Chain 2", " [(! ( #P# || #R# ))*. #S# . (! ( #T# || #R# ))*. #T# . (! #R# )*. #R# ] false ");
	   addPattern("Before R variant", "Precedence Chain 2", "[ (! ( #P# || #R# ))*. #S# . (! ( #T# || #R# ))*. #T# ] false");
	   addPattern("After Q", "Precedence Chain 2", "[(! #Q# )*. #Q# . (! #P# )*. #S# . (! #T# )*. #T# ] false ");
	   addPattern("After Q variant", "Precedence Chain 2", "[true*. #Q# ]( ([true*. #Q# ]false) => [ (! #P# )*. #S# . (! #T# )*. #T# ] false) ");
	   addPattern("Between Q and R", "Precedence Chain 2", "  [true*. #Q# . (! ( #P# || #R# ))*. #S# . (! ( #T# || #R# ))*. #T# . (! #R# )*. #R# ] false ");
	   addPattern("Between Q and R variant", "Precedence Chain 2", "[true*. #Q# ]( ([true*. #Q# ]false) => [(! ( #P# || #R# ))*. #S# . (! ( #T# || #R# ))*. #T# . (! #R# )*. #R# ] false ) ");
	   addPattern("After Q until R", "Precedence Chain 2", " [true*. #Q# . (! ( #P# || #R# ))*. #S# . (! ( #T# || #R# ))*. #T# ] false ");
	   addPattern("After Q until R variant", "Precedence Chain 2", "[true*. #Q# ]( ([true*. #Q# ]false) => [(! ( #P# || #R# ))*. #S# . (! ( #T# || #R# ))*. #T# ] false )");
	   
	   addPattern("Globally", "Response Chain 1", " [true*. #S# . (! #T# )*. #T# ] mu X. <true> true && [! #P# ] X ");
	   addPattern("Before R", "Response Chain 1", " [true*. #S# . (! #T# )*. #T# . (! #P# )*. #R# ] false ");
	   addPattern("Before R variant", "Response Chain 1", " [(! ( #S# || #R# ))*. #S# . (! ( #T# || #R# ))*. #T# ] mu X. <true> true && [ #R# ] false && [! #P# ] X");
	   addPattern("After Q", "Response Chain 1", " [(! #Q# )*. #Q# . true*. #S# . (! #T# )*. #T# ] mu X. <true> true && [! #P# ] X " );
	   addPattern("After Q variant", "Response Chain 1", " [true*. #Q# ](([true*. #Q# ]false) => [ true*. #S# . (! #T# )*. #T# ] (mu X. <true> true && [! #P# ] X)) " );
	   addPattern("Between Q and R", "Response Chain 1", "[true*. #Q# . (! ( #S# || #R# ))*. #S# . (! ( #T# || #R# ))*. #T# .(! ( #P# || #R# ))*. #R# ] false");
	   addPattern("Between Q and R variant", "Response Chain 1", "[true*. #Q# ](([true*. #Q# ]false) => [(! ( #S# || #R# ))*. #S# . (! ( #T# || #R# ))*. #T# .(! ( #P# || #R# ))*. #R# ] false)");
	   addPattern("After Q until R", "Response Chain 1", "[true*. #Q# . (! ( #S# || #R# ))*. #S# . (! ( #T# || #R# ))*. #T# ] mu X. <true> true && [ #R# ] false && [! #P# ] X ");
	   addPattern("After Q until R variant", "Response Chain 1", "[true*. #Q# ](([true*. #Q# ]false) => [(! ( #S# || #R# ))*. #S# . (! ( #T# || #R# ))*. #T# ] mu X. <true> true && [ #R# ] false && [! #P# ] X )");
	   
	   addPattern("Globally", "Response Chain 2", "[true*. #P# ] mu X. <true> true && [ #S# ] mu Y. (<true> true && [! #T# ] Y) && [! #S# ] X " );
	   addPattern("Before R", "Response Chain 2", "[(! #R# )*. #P# . (! ( #S# || #R# ))*. (nil | (S . (! ( #T# || #R# ))*)). #R# ] false ");
	   addPattern("Before R variant", "Response Chain 2", "[(! #R# )*. #P# ] mu X. <true> true && [ #R# ] false && [ #S# ] mu Y. (<true> true && [ #R# ] false && [! #T# ] Y) && [! #S# ] X ");
	   addPattern("After Q", "Response Chain 2", "[(! #Q# )*. #Q# . true*. #P# ] mu X. <true> true && [ #S# ] mu Y. (<true> true && [! #T# ] Y) && [! #S# ] X ");
	   addPattern("After Q variant", "Response Chain 2", "[true*. #Q# ]( ([true*. #Q# ]false) => [true*. #P# ] (mu X. <true> true && [ #S# ] mu Y. (<true> true && [! #T# ] Y) && [! #S# ] X)) ");
	   addPattern("Between Q and R", "Response Chain 2", "[true*. #Q# . (! #R# )*. #P# . (! ( #S# || #R# ))*. (nil | ( #S# . (! ( #T# || #R# ))*)). #R# ] false ");
	   addPattern("Between Q and R variant", "Response Chain 2", "[true*. #Q# ]( ([true*. #Q# ]false) => [(! #R# )*. #P# . (! ( #S# || #R# ))*. (nil | ( #S# . (! ( #T# || #R# ))*)). #R# ] false )");
	   addPattern("After Q until R", "Response Chain 2", "[true*. #Q# . (! #R# )*. #P# ] mu X. <true> true && [ #R# ] false && [ #S# ] mu Y. (<true> true && [ #R# ] false && [! #T# ] Y) && [! #S# ] X ");
	   addPattern("After Q until R variant", "Response Chain 2", "[true*. #Q# ]( ([true*. #Q# ]false) => [(! #R# )*. #P# ] mu X. <true> true && [ #R# ] false && [ #S# ] mu Y. (<true> true && [ #R# ] false && [! #T# ] Y) && [! #S# ] X) ");
	   
	   
	   addPattern("Globally", "Constrained Response Chain 2", "[true*. #P# ] mu X . <true> true && [ #S# ] mu Y. (<true> true && [ #Z# ] false && [! #T# ] Y) && [! #S# ] X " );
	   addPattern("Before R", "Constrained Response Chain 2", "[(! #R# )*. #P# . (! ( #S# || #R# ))*. (nil | ( #S# . (nil | ((! ( #T# || #R# ))*. #Z# )). (! ( #T# || #R# ))*)). #R# ] false ");
	   addPattern("Before R variant", "Constrained Response Chain 2", "[(! #R# )*. #P# ] mu X. <true> true && [ #R# ] false && [ #S# ] mu Y. (<true> true && [ #Z# || #R# ] false && [! #T# ] Y) && [! #S# ] X ");
	   addPattern("After Q", "Constrained Response Chain 2", "[(! #Q# )*. #Q# . true*. #P# ] mu X. <true> true && [ #S# ] mu Y. (<true> true && [ #Z# ] false && [! #T# ] Y) && [! #S# ] X");
	   addPattern("After Q variant", "Constrained Response Chain 2", "[true*. #Q# ]( ([true*. #Q# ]false) => [true*. #P# ]( mu X. <true> true && [ #S# ] mu Y. (<true> true && [ #Z# ] false && [! #T# ] Y) && [! #S# ] X))");
	   addPattern("Between Q and R", "Constrained Response Chain 2", "[true*. #Q# . (! #R# )*. #P# . (! ( #S# || #R# ))*. (nil | ( #S# . (nil | ((! ( #T# || #R# ))*. #Z# )). (! ( #T# || #R# ))*)). #R# ] false ");
	   addPattern("Between Q and R variant", "Constrained Response Chain 2", "[true*. #Q# ]( ([true*. #Q# ]false) => [(! #R# )*. #P# . (! ( #S# || #R# ))*. (nil | ( #S# . (nil | ((! ( #T# || #R# ))*. #Z# )). (! ( #T# || #R# ))*)). #R# ] false )");
	   addPattern("After Q until R", "Constrained Response Chain 2", "[true*. #Q# . (! #R# )*. #P# ] mu X. <true> true && [ #R# ] false && [ #S# ] mu Y. (<true> true && [ #Z# || #R# ] false && [! #T# ] Y) && [! #S# ] X ");
	   addPattern("After Q until R variant", "Constrained Response Chain 2", "[true*. #Q# ]( ([true*. #Q# ]false) => [(! #R# )*. #P# ] mu X. <true> true && [ #R# ] false && [ #S# ] mu Y. (<true> true && [ #Z# || #R# ] false && [! #T# ] Y) && [! #S# ] X) ");
	   
	   
	  addPattern("Globally", "Bounded Existence", " [(! #P# )*. #P# . (! #P# )*. #P# ] false ");
	  addPattern("Before R", "Bounded Existence", " [(! #R# )*. #P# . (! #R# )*. #P# . (! #R# )*. #R# ] false ");
	  addPattern("Before R variant", "Bounded Existence", " [(! #R# )*. #P# . (! #R# )*. #P# ] false ");
	  addPattern("After Q", "Bounded Existence", " [(! #Q# )*. #Q# . (! #P# )*. #P# . (! #P# )*. #P# ] false ");
	  addPattern("After Q variant", "Bounded Existence", "[true*. #Q# ]( ([true*. #Q# ]false) => [(! #P# )*. #P# . (! #P# )*. #P# ] false )");
	  addPattern("Between Q and R", "Bounded Existence", " [true*. #Q# . (! #R# )*. #P# . (! #R# )*. #P# . (! #R# )*. #R# ] false ");
	  addPattern("Between Q and R variant", "Bounded Existence", "[true*. #Q# ]( ([true*. #Q# ]false) => [(! #R# )*. #P# . (! #R# )*. #P# . (! #R# )*. #R# ] false )");
	  addPattern("After Q until R", "Bounded Existence" , " [true*. #Q# . (! #R# )*. #P# . (! #R# )*. #P# ] false ");
	  addPattern("After Q until R variant", "Bounded Existence" , "[true*. #Q# ]( ([true*. #Q# ]false) => [(! #R# )*. #P# . (! #R# )*. #P# ] false ) ");
	 
	  // NEW NEW NEW
	   addPattern("Globally", "AlwaysEnabled", " [true*]< #P# > true " ); 
	   addPattern("Before R", "AlwaysEnabled", "nu X.(< #P# >true && [(! #R# )]X)");  //TODO
	   addPattern("Before R variant", "AlwaysEnabled", "[ (! #R# )*] < #P# > true"); 
	   addPattern("After Q", "AlwaysEnabled", " [(! #Q# )*. #Q# . true*]< #P# > true"); 
	   addPattern("After Q variant", "AlwaysEnabled", " [true*. #Q# ](([true*. #Q# ] false) => < #P# > true)"); 
	   addPattern("Between Q and R", "AlwaysEnabled", "TODO"); //TODO
	   addPattern("Between Q and R variant","AlwaysEnabled", "TODO"); //TODO
	   addPattern("After Q until R", "AlwaysEnabled", "[true*. #Q#. (!  #R# )*] < #P# > true "); 
	   addPattern("After Q until R variant", "AlwaysEnabled", " [true*. #Q# ](([true*. #Q# ] false) => [ (! #R# )*] < #P# > true)"); 
	   
	  ////  ////  ////  ////  ////  ////  ////  ////  ////  ////  ////  ////  ////  ////
	   addPatternMonitorable("Globally", "Absence", Boolean.TRUE );
	   addPatternMonitorable("Before R", "Absence", Boolean.TRUE );
	   addPatternMonitorable("Before R variant", "Absence", Boolean.TRUE );
	   addPatternMonitorable("After Q", "Absence", Boolean.TRUE );
	   addPatternMonitorable("After Q variant", "Absence", Boolean.FALSE ); //TODO: check After #Q# variants if they are monitorable, they have implication sign! 
	   addPatternMonitorable("Between Q and R", "Absence",Boolean.TRUE );
	   addPatternMonitorable("Between Q and R variant", "Absence",Boolean.FALSE ); //TODO
	   addPatternMonitorable("After Q until R","Absence", Boolean.TRUE );
	   addPatternMonitorable("After Q until R variant","Absence", Boolean.FALSE ); //TODO
	   
	   addPatternMonitorable("Globally", "ExistenceUnfair",Boolean.FALSE );
	   addPatternMonitorable("Before R", "ExistenceUnfair", Boolean.TRUE );
	   addPatternMonitorable("Before R variant", "ExistenceUnfair", Boolean.FALSE );
	   addPatternMonitorable("After Q", "ExistenceUnfair", Boolean.FALSE );
	   addPatternMonitorable("After Q variant", "ExistenceUnfair", Boolean.FALSE );//TODO: check After #Q# variants if they are monitorable, they have implication sign! 
	   addPatternMonitorable("Between Q and R", "ExistenceUnfair", Boolean.TRUE );
	   addPatternMonitorable("Between Q and R variant", "ExistenceUnfair", Boolean.FALSE ); //TODO
	   addPatternMonitorable("After Q until R", "ExistenceUnfair", Boolean.FALSE );
	   addPatternMonitorable("After Q until R variant", "ExistenceUnfair", Boolean.FALSE ); //TODO
	   
	   // NEW NEW NEW 
	   addPatternMonitorable("Globally", "ExistenceFair",Boolean.FALSE );
	   addPatternMonitorable("Before R", "ExistenceFair", Boolean.TRUE );
	   addPatternMonitorable("Before R variant", "ExistenceFair", Boolean.FALSE );
	   addPatternMonitorable("After Q", "ExistenceFair", Boolean.FALSE );
	   addPatternMonitorable("After Q variant", "ExistenceFair", Boolean.FALSE );//TODO: check After #Q# variants if they are monitorable, they have implication sign! 
	   addPatternMonitorable("Between Q and R", "ExistenceFair", Boolean.TRUE );
	   addPatternMonitorable("Between Q and R variant", "ExistenceFair", Boolean.FALSE ); //TODO
	   addPatternMonitorable("After Q until R", "ExistenceFair", Boolean.FALSE );
	   addPatternMonitorable("After Q until R variant", "ExistenceFair", Boolean.FALSE ); //TODO
	   
	   
	   addPatternMonitorable("Globally", "Universality", Boolean.TRUE );
	   addPatternMonitorable("Before R", "Universality", Boolean.TRUE );
	   addPatternMonitorable("Before R variant", "Universality", Boolean.FALSE ); //TODO: check
	   addPatternMonitorable("After Q", "Universality", Boolean.TRUE );
	   addPatternMonitorable("After Q variant", "Universality", Boolean.FALSE );//TODO: check After #Q# variants if they are...
	   addPatternMonitorable("Between Q and R", "Universality", Boolean.TRUE );
	   addPatternMonitorable("Between Q and R variant", "Universality", Boolean.FALSE ); //TODO
	   addPatternMonitorable("After Q until R", "Universality", Boolean.TRUE );
	   addPatternMonitorable("After Q until R variant", "Universality", Boolean.TRUE ); //TODO
	   
	   addPatternMonitorable("Globally", "Precedence", Boolean.TRUE );
	   addPatternMonitorable("Before R" , "Precedence" , Boolean.TRUE );
	   addPatternMonitorable("Before R variant" , "Precedence" , Boolean.TRUE );
	   addPatternMonitorable("After Q", "Precedence", Boolean.TRUE );
	   addPatternMonitorable("After Q variant", "Precedence", Boolean.FALSE );//TODO: check After #Q# variants if they are...
	   addPatternMonitorable("Between Q and R", "Precedence", Boolean.TRUE );
	   addPatternMonitorable("Between Q and R variant", "Precedence", Boolean.FALSE ); //TODO
	   addPatternMonitorable("After Q until R", "Precedence",  Boolean.TRUE );
	   addPatternMonitorable("After Q until R variant", "Precedence",  Boolean.FALSE ); //TODO
	   
	   addPatternMonitorable("Globally", "Precedence variant", Boolean.FALSE ); //TODO
	   addPatternMonitorable("Before R" , "Precedence variant" , Boolean.FALSE );
	   addPatternMonitorable("Before R variant" , "Precedence variant" , Boolean.FALSE );
	   addPatternMonitorable("After Q", "Precedence variant", Boolean.FALSE );
	   addPatternMonitorable("After Q variant", "Precedence variant", Boolean.FALSE );//TODO: check After #Q# variants if they are...
	   addPatternMonitorable("Between Q and R", "Precedence variant", Boolean.FALSE );
	   addPatternMonitorable("Between Q and R variant", "Precedence variant", Boolean.FALSE ); //TODO
	   addPatternMonitorable("After Q until R", "Precedence variant",  Boolean.FALSE );
	   addPatternMonitorable("After Q until R variant", "Precedence variant",  Boolean.FALSE ); //TODO
	   
	   addPatternMonitorable("Globally", "ResponseUnfair", Boolean.FALSE );
	   addPatternMonitorable("Before R", "ResponseUnfair", Boolean.TRUE );
	   addPatternMonitorable("Before R variant", "ResponseUnfair", Boolean.FALSE );
	   addPatternMonitorable("After Q", "ResponseUnfair", Boolean.FALSE );
	   addPatternMonitorable("After Q variant", "ResponseUnfair", Boolean.FALSE );//TODO: check After #Q# variants if they are...
	   addPatternMonitorable("Between Q and R", "ResponseUnfair", Boolean.TRUE );
	   addPatternMonitorable("Between Q and R variant", "ResponseUnfair", Boolean.FALSE ); //TODO
	   addPatternMonitorable("After Q until R", "ResponseUnfair", Boolean.FALSE );
	   addPatternMonitorable("After Q until R variant", "ResponseUnfair", Boolean.FALSE ); //TODO
	   
	   // NEW NEW NEW
	   addPatternMonitorable("Globally", "ResponseFair", Boolean.FALSE );
	   addPatternMonitorable("Before R", "ResponseFair", Boolean.TRUE );
	   addPatternMonitorable("Before R variant", "ResponseFair", Boolean.FALSE );
	   addPatternMonitorable("After Q", "ResponseFair", Boolean.FALSE );
	   addPatternMonitorable("After Q variant", "ResponseFair", Boolean.FALSE );//TODO: check After #Q# variants if they are...
	   addPatternMonitorable("Between Q and R", "ResponseFair", Boolean.TRUE );
	   addPatternMonitorable("Between Q and R variant", "ResponseFair", Boolean.FALSE ); //TODO
	   addPatternMonitorable("After Q until R", "ResponseFair", Boolean.FALSE );
	   addPatternMonitorable("After Q until R variant", "ResponseFair", Boolean.FALSE ); //TODO
	   
	   
	   addPatternMonitorable("Globally", "Response variant Unfair", Boolean.FALSE );
	   addPatternMonitorable("Before R", "Response variant Unfair", Boolean.FALSE );
	   addPatternMonitorable("Before R variant", "Response variant Unfair", Boolean.FALSE );
	   addPatternMonitorable("After Q", "Response variant Unfair", Boolean.FALSE );
	   addPatternMonitorable("After Q variant", "Response variant Unfair", Boolean.FALSE );//TODO: check After #Q# variants if they are...
	   addPatternMonitorable("Between Q and R", "Response variant Unfair", Boolean.FALSE );
	   addPatternMonitorable("Between Q and R variant", "Response variant Unfair", Boolean.FALSE ); //TODO
	   addPatternMonitorable("After Q until R", "Response variant Unfair", Boolean.FALSE );
	   addPatternMonitorable("After Q until R variant", "Response variant Unfair", Boolean.FALSE ); //TODO
	   
	   // NEW NEW NEW
	   addPatternMonitorable("Globally", "Response variant Fair", Boolean.FALSE );
	   addPatternMonitorable("Before R", "Response variant Fair", Boolean.FALSE );
	   addPatternMonitorable("Before R variant", "Response variant Fair", Boolean.FALSE );
	   addPatternMonitorable("After Q", "Response variant Fair", Boolean.FALSE );
	   addPatternMonitorable("After Q variant", "Response variant Fair", Boolean.FALSE );//TODO: check After #Q# variants if they are...
	   addPatternMonitorable("Between Q and R", "Response variant Fair", Boolean.FALSE );
	   addPatternMonitorable("Between Q and R variant", "Response variant Fair", Boolean.FALSE ); //TODO
	   addPatternMonitorable("After Q until R", "Response variant Fair", Boolean.FALSE );
	   addPatternMonitorable("After Q until R variant", "Response variant Fair", Boolean.FALSE ); //TODO
	   
	   addPatternMonitorable("Globally", "Precedence Chain 1", Boolean.TRUE );
	   addPatternMonitorable("Before R", "Precedence Chain 1", Boolean.TRUE);
	   addPatternMonitorable("Before R variant", "Precedence Chain 1", Boolean.TRUE);
	   addPatternMonitorable("After Q", "Precedence Chain 1", Boolean.TRUE);
	   addPatternMonitorable("After Q variant", "Precedence Chain 1", Boolean.FALSE);//TODO: check After #Q# variants if they are...
	   addPatternMonitorable("Between Q and R", "Precedence Chain 1", Boolean.TRUE);
	   addPatternMonitorable("Between Q and R variant", "Precedence Chain 1", Boolean.FALSE); //TODO
	   addPatternMonitorable("After Q until R", "Precedence Chain 1", Boolean.TRUE);
	   addPatternMonitorable("After Q until R variant", "Precedence Chain 1", Boolean.FALSE); //TODO
	  
	   
	   addPatternMonitorable("Globally", "Precedence Chain 2", Boolean.TRUE);
	   addPatternMonitorable("Before R", "Precedence Chain 2", Boolean.TRUE);
	   addPatternMonitorable("Before R", "Precedence Chain 2", Boolean.TRUE);
	   addPatternMonitorable("After Q", "Precedence Chain 2", Boolean.TRUE);
	   addPatternMonitorable("After Q variant", "Precedence Chain 2", Boolean.FALSE);//TODO: check After #Q# variants if they are...
	   addPatternMonitorable("Between Q and R", "Precedence Chain 2", Boolean.TRUE);
	   addPatternMonitorable("After Q until R", "Precedence Chain 2",Boolean.TRUE);
	   addPatternMonitorable("Between Q and R variant", "Precedence Chain 2", Boolean.FALSE); //TODO
	   addPatternMonitorable("After Q until R variant", "Precedence Chain 2",Boolean.FALSE); //TODO
	   
	   addPatternMonitorable("Globally", "Response Chain 1", Boolean.FALSE);
	   addPatternMonitorable("Before R", "Response Chain 1", Boolean.TRUE);
	   addPatternMonitorable("Before R variant", "Response Chain 1", Boolean.FALSE);
	   addPatternMonitorable("After Q", "Response Chain 1", Boolean.FALSE );
	   addPatternMonitorable("After Q variant", "Response Chain 1", Boolean.FALSE );//TODO: check After #Q# variants if they are...
	   addPatternMonitorable("Between Q and R", "Response Chain 1", Boolean.TRUE);
	   addPatternMonitorable("After Q until R", "Response Chain 1", Boolean.FALSE);
	   addPatternMonitorable("Between Q and R variant", "Response Chain 1", Boolean.FALSE); //TODO
	   addPatternMonitorable("After Q until R variant", "Response Chain 1", Boolean.FALSE); //TODO
	   
	   addPatternMonitorable("Globally", "Response Chain 2", Boolean.FALSE);
	   addPatternMonitorable("Before R", "Response Chain 2", Boolean.TRUE);
	   addPatternMonitorable("Before R variant", "Response Chain 2", Boolean.FALSE);
	   addPatternMonitorable("After Q", "Response Chain 2", Boolean.FALSE);
	   addPatternMonitorable("After Q variant", "Response Chain 2", Boolean.FALSE);//TODO: check After #Q# variants if they are...
	   addPatternMonitorable("Between Q and R", "Response Chain 2", Boolean.TRUE);
	   addPatternMonitorable("After Q until R", "Response Chain 2", Boolean.FALSE);
	   addPatternMonitorable("Between Q and R variant", "Response Chain 2", Boolean.FALSE); //TODO
	   addPatternMonitorable("After Q until R variant", "Response Chain 2", Boolean.FALSE); //TODO
	   
	   
	   addPatternMonitorable("Globally", "Constrained Response Chain 2", Boolean.FALSE );
	   addPatternMonitorable("Before R", "Constrained Response Chain 2", Boolean.TRUE);
	   addPatternMonitorable("Before R variant", "Constrained Response Chain 2", Boolean.FALSE);
	   addPatternMonitorable("After Q", "Constrained Response Chain 2", Boolean.FALSE);
	   addPatternMonitorable("After Q variant", "Constrained Response Chain 2", Boolean.FALSE);//TODO: check After #Q# variants if they are...
	   addPatternMonitorable("Between Q and R", "Constrained Response Chain 2", Boolean.TRUE);
	   addPatternMonitorable("After Q until R", "Constrained Response Chain 2", Boolean.FALSE);
	   addPatternMonitorable("Between Q and R variant", "Constrained Response Chain 2", Boolean.FALSE); //TODO
	   addPatternMonitorable("After Q until R variant", "Constrained Response Chain 2", Boolean.FALSE); //TODO
	   
	   
	   addPatternMonitorable("Globally", "Bounded Existence", Boolean.TRUE);
	   addPatternMonitorable("Before R", "Bounded Existence", Boolean.TRUE);
	   addPatternMonitorable("Before R variant", "Bounded Existence", Boolean.TRUE);
	   addPatternMonitorable("After Q", "Bounded Existence", Boolean.TRUE);
	   addPatternMonitorable("After Q variant", "Bounded Existence", Boolean.FALSE);//TODO: check After #Q# variants if they are...
	   addPatternMonitorable("Between Q and R", "Bounded Existence", Boolean.TRUE);
	   addPatternMonitorable("After Q until R", "Bounded Existence" , Boolean.TRUE);
	   addPatternMonitorable("Between Q and R variant", "Bounded Existence", Boolean.FALSE); //TODO
	   addPatternMonitorable("After Q until R variant", "Bounded Existence" , Boolean.FALSE); //TODO
	  
	   addPatternMonitorable("Globally", "AlwaysEnabled", Boolean.FALSE);
	   addPatternMonitorable("Before R", "AlwaysEnabled", Boolean.FALSE);
	   addPatternMonitorable("Before R variant", "AlwaysEnabled", Boolean.FALSE);
	   addPatternMonitorable("After Q", "AlwaysEnabled", Boolean.FALSE);
	   addPatternMonitorable("After Q variant", "AlwaysEnabled", Boolean.FALSE);
	   addPatternMonitorable("Between Q and R", "AlwaysEnabled", Boolean.FALSE);
	   addPatternMonitorable("After Q until R", "AlwaysEnabled" , Boolean.FALSE);
	   addPatternMonitorable("Between Q and R variant", "AlwaysEnabled", Boolean.FALSE); //TODO
	   addPatternMonitorable("After Q until R variant", "AlwaysEnabled" , Boolean.FALSE); //TODO
	   
//	  Questionnaire.answ12.setScope("Globally");
	  Questionnaire.answ12.setScope("Globally");
	  Questionnaire.answ12.setToolTip("Heres a cool tooltip for Globally scope");

	  Questionnaire.answ111111.setScope("After Q");
	  Questionnaire.answ111111.setToolTip("Heres a cool tooltip for After-Q scope");

	  Questionnaire.answ111112.setScope("After Q variant"); 
	  Questionnaire.answ111112.setToolTip("Heres a cool tooltip for After-last-Q scope");

	  Questionnaire.answ111211.setScope("Before R variant"); 
	  Questionnaire.answ111211.setToolTip("Heres a cool tooltip for Until-R scope");

	  Questionnaire.answ111212.setScope("Before R"); 
	  Questionnaire.answ111212.setToolTip("Heres a cool tooltip for Before-R scope");

	  Questionnaire.answ1131111.setScope("After Q until R");
	  Questionnaire.answ1131111.setToolTip("Heres a cool tooltip for After-Q-Until-R scope");

	  Questionnaire.answ1131211.setScope("After Q until R variant"); 
	  Questionnaire.answ1131211.setToolTip("Heres a cool tooltip for After-Q-Until-R-variant scope");

	  Questionnaire.answ1131112.setScope("Between Q and R");
	  Questionnaire.answ1131112.setToolTip("Heres a cool tooltip for Between-Q-and-R scope");
	  
	  Questionnaire.answ1131212.setScope("Between Q and R variant"); 
	  Questionnaire.answ1131212.setToolTip("Heres a cool tooltip for Between-Q-and-R-variant scope");

	  Questionnaire.aansw1111.setBehavior("Absence");
	  Questionnaire.aansw1111.setToolTip("Heres a cool tooltip for Absence behavior");

	  Questionnaire.aansw111212.setBehavior("ExistenceUnfair");
	  Questionnaire.aansw111212.setToolTip("Informally it states that we do NOT assume the underlying OS scheduler of the running system that is modeled, to be fair. " +
		  		"So if a single process gets all the execution time, while other processes are not given a fair chance to run, and the " +
		  		"event A may never happen as a result of that, then the property will not hold. If it is expected that such process starvation can happen in practise, and it should" +
		  		"be taken into consideration, then this is the right answer.");

	  Questionnaire.aansw111211.setBehavior("ExistenceFair");
	  Questionnaire.aansw111211.setToolTip("Informally it states that we assume each process of the system is given a fair chance to execute. " +
		  		"The model typically abstracts away from the underlying operating system and hence of the OS scheduler. As a result," +
		  		"there could be unrealistic scenarios in the model execution, where, for example, a single process gets all execution time. We want to disregard such unrealistic loops.");

	  Questionnaire.aansw1113.setBehavior("Bounded Existence");
	  Questionnaire.aansw1113.setToolTip("Heres a cool tooltip for Bounded Existence behavior");

	  Questionnaire.aansw121212.setBehavior("Precedence"); 
	  Questionnaire.aansw121212.setToolTip("Heres a cool tooltip for Precedence behavior");

	  Questionnaire.aansw121211.setBehavior("Precedence variant");
	  Questionnaire.aansw121211.setToolTip("Heres a cool tooltip for Precedence variant behavior");

	  Questionnaire.aansw1211.setToolTip("E.g.: Say resource is shared by many users, and the program which monitors may have to " +
			  "fulfill the requests of each user to access the resource. " +
			  "If the resource is mutually exclusive, in general not all the requests can be fulfilled immediately, " +
			  "as the resource may be already occupied when a request arrives; " +
			  "so the monitor must schedule the allocation of the resource in a way that the following property holds:" +
			  "\"Every request for the resource is eventually acknowledged\". ");
	  
//	  Questionnaire.aansw1222.setBehavior("Response");
	  Questionnaire.aansw12111212.setBehavior("ResponseUnfair");
	  Questionnaire.aansw12111212.setToolTip("Informally it states that we do NOT assume the underlying OS scheduler of the running system that is modeled, to be fair. " +
		  		"So if a single process gets all the execution time, while other processes are not given a fair chance to run, and the " +
		  		"response event B may never happen as a result of that, then the property will not hold. If it is expected that such process starvation can happen in practise, and it should" +
		  		"be taken into consideration, then this is the right answer.");

	  Questionnaire.aansw12111211.setBehavior("ResponseFair");
	  Questionnaire.aansw12111211.setToolTip("Informally it states that we assume each process of the system is given a fair chance to execute. " +
		  		"The model typically abstracts away from the underlying operating system and hence of the OS scheduler. As a result," +
		  		"there could be unrealistic scenarios in the model execution, where, for example, a single process gets all execution time. We want to disregard such unrealistic loops.");

	  Questionnaire.aansw12111112.setBehavior("Response variant Unfair"); 
	  Questionnaire.aansw12111112.setToolTip("Informally it states that we do NOT assume the underlying OS scheduler of the running system that is modeled, to be fair. " +
		  		"So if a single process gets all the execution time, while other processes are not given a fair chance to run, and the " +
		  		"response event B may never happen as a result of that, then the property will not hold. If it is expected that such process starvation can happen in practise, and it should" +
		  		"be taken into consideration, then this is the right answer.");

	  Questionnaire.aansw12111111.setBehavior("Response variant Fair");
	  Questionnaire.aansw12111111.setToolTip("Informally it states that we assume each process of the system is given a fair chance to execute. " +
		  		"The model typically abstracts away from the underlying operating system and hence of the OS scheduler. As a result," +
		  		"there could be unrealistic scenarios in the model execution, where, for example, a single process gets all execution time. We want to disregard such unrealistic loops.");

	  Questionnaire.aansw1114.setBehavior("Universality");
	  Questionnaire.aansw1114.setToolTip("The events in such case are typically getter methods for querying the state. E.g., the action could be of the form:\n getTemperature(24), getStatus(Success)");

	  // OK
	  Questionnaire.aansw131111.setBehavior("Response Chain 2");
	  Questionnaire.aansw131111.setToolTip("Heres a cool tooltip for Response Chain 2 behavior");

	  Questionnaire.aansw131112.setBehavior("Constrained Response Chain 2");
	  Questionnaire.aansw131112.setToolTip("Heres a cool tooltip for Constrained Response Chain 2 behavior");

	  //TODO HERE
	  Questionnaire.aansw1115.setBehavior("AlwaysEnabled");
	  Questionnaire.aansw1115.setToolTip("Heres a cool tooltip for AlwaysEnabled behavior");

	  Questionnaire.aansw1312.setBehavior("Response Chain 1");
	  Questionnaire.aansw1312.setToolTip("Heres a cool tooltip for Response Chain 1 behavior");

	  Questionnaire.aansw1313.setBehavior("Precedence Chain 1");
	  Questionnaire.aansw1313.setToolTip("Heres a cool tooltip for Precedence Chain 1 behavior");

	  Questionnaire.aansw1314.setBehavior("Precedence Chain 2");
	  Questionnaire.aansw1314.setToolTip("Heres a cool tooltip for Precedence Chain 2 behavior");

   }


}
	
