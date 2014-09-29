package info.remenska.PASS.monitor.mCRL2;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

import info.remenska.PASS.monitor.mCRL2.Main;

import info.remenska.PASS.wizards.DisciplinedEnglishPage;

public class TestMain {
	public static void main(String args[]) throws FileNotFoundException, NullPointerException, NotMonitorableException, Exception{
		String resultModel = new String();
		Main main = new Main();
//		String formula = "[(! StorageManagementDB_removeTasks([tasks(1,Staged,1)]))*.JobDB_setJobStatus(1,Staging).(! (JobDB_setJobStatus(1,Done) || StorageManagementDB_removeTasks([tasks(1,Staged,1)])))*.StorageManagementDB_removeTasks([tasks(1,Staged,1)])]false";
//		String formula = "[(! ready)*][coin]false"; // ready must happen before coin
//		String formula = "[(!R )*. P . (! ( S || R ))*. R ] false"; // S must respond to P, before R happens
//		String formula = "[true*. __prListLock_acquire . (! __prListLock_release )*. __prListLock_acquire ] false";
//		String formula = "[(! c__createAndQueueTask(task(2,false,RegisterFile)) )*. __pendingQueueSize(__queueSize) . (! ( __pendingQueueGet(None) || c__createAndQueueTask(task(2,false,RegisterFile)) ))*. c__createAndQueueTask(task(2,false,RegisterFile)) ] false";
		String formula = "[true*. Q . (! ( P || R ))*. R ] false";
		
//		resultModel = main.generateMonitor(new String[] {"/home/daniela/remenska/Documents/LHCb/GEOC/ProcessPool/ProcessPoolSmaller.mcrl2",
//				formula,
//				"/home/daniela/remenska/Documents/LHCb/GEOC/ProcessPool/ProcessPoolSmaller.mcrl2"
//				, "false"});
		
		resultModel = main.generateMonitorVisual(new String[] {"/home/daniela/IBM/rationalsdp/workspace1/UML2mCRL2/Test.mcrl2",
				formula,
				"/home/daniela/IBM/rationalsdp/workspace1/UML2mCRL2/Test.mcrl2"
				, "true"});
		System.out.println("resulting model in: " + resultModel);


		
		 String[] cmd_mcrl22lps = { "mcrl22lps", "-v", resultModel, resultModel + ".lps" };
		 String[] cmd_lps2lts = { "lps2lts", "-v", resultModel + ".lps", resultModel + ".lts" };
		 String[] cmd_ltsconvert = { "ltsconvert", "-v", "-e", "trace",resultModel +".lts", resultModel + ".dot" }; // MINIMIZED MODULO TRACE-EQUIVALENCE!!
		 String[] cmd_dot = { "dot", "-Tps",  resultModel +".dot","-o", resultModel + ".ps" };
//		 String[] cmd_gs = { "gs",  resultModel + ".ps" };

		 	try{

		    //----
			    System.out.println("-----------");
		    System.out.println("running mcrl22lps:");
		    System.out.println("-----------");
		 	Process p_mclr22lps = Runtime.getRuntime().exec(cmd_mcrl22lps);
		 	p_mclr22lps.waitFor();
	        BufferedReader stdInput_mcrl22lps = new BufferedReader(new 
	                InputStreamReader(p_mclr22lps.getInputStream()));
	        BufferedReader stdError_mcrl22lps = new BufferedReader(new 
	                InputStreamReader(p_mclr22lps.getErrorStream()));
	        String s_mcrl22lps = null;
	        while ((s_mcrl22lps = stdInput_mcrl22lps.readLine()) != null) {
	            System.out.println(s_mcrl22lps);
	        }
	        while ((s_mcrl22lps = stdError_mcrl22lps.readLine()) != null) {
	            System.out.println(s_mcrl22lps);
	        }
	        
	        //----
		    System.out.println("-----------");
		    System.out.println("running lps2lts:");
		    System.out.println("-----------");
	        Process p_lps2lts = Runtime.getRuntime().exec(cmd_lps2lts);
	        p_lps2lts.waitFor();
	        
	        BufferedReader stdInput_lps2lts = new BufferedReader(new 
	                InputStreamReader(p_lps2lts.getInputStream()));
	        BufferedReader stdError_lps2lts = new BufferedReader(new 
	                InputStreamReader(p_lps2lts.getErrorStream()));
	        
	        String s_lps2lts = null;
	        while ((s_lps2lts = stdInput_lps2lts.readLine()) != null) {
	            System.out.println(s_lps2lts);
	        }
	        while ((s_lps2lts = stdError_lps2lts.readLine()) != null) {
	            System.out.println(s_lps2lts);
	        }
		    System.out.println("-----------");
		    System.out.println("converting to a GraphViz format:");
		    System.out.println("-----------");
	        Process p_ltsconvert = Runtime.getRuntime().exec(cmd_ltsconvert);
	        p_ltsconvert.waitFor();
	        
	        BufferedReader stdInput_ltsconvert = new BufferedReader(new 
	                InputStreamReader(p_ltsconvert.getInputStream()));
	        BufferedReader stdError_ltsconvert = new BufferedReader(new 
	                InputStreamReader(p_ltsconvert.getErrorStream()));
	        
	        String s_ltsconvert = null;
	        while ((s_ltsconvert = stdInput_ltsconvert.readLine()) != null) {
	            System.out.println(s_ltsconvert);
	        }
	        while ((s_ltsconvert = stdError_ltsconvert.readLine()) != null) {
	            System.out.println(s_ltsconvert);
	        }
	        
		    System.out.println("-----------");
		    System.out.println("The monitor is saved at:" + resultModel + ".dot");
		    System.out.println("-----------");
		    System.out.println("Attempting to convert it to PostScript with dot...(GraphViz should be installed!)");

	        Process p_dot = Runtime.getRuntime().exec(cmd_dot);
	        p_dot.waitFor();
	        
	        BufferedReader stdInput_dot = new BufferedReader(new 
	                InputStreamReader(p_dot.getInputStream()));
	        BufferedReader stdError_dot = new BufferedReader(new 
	                InputStreamReader(p_dot.getErrorStream()));
	        String s_dot = null;
	        while ((s_dot = stdInput_dot.readLine()) != null) {
	            System.out.println(s_dot);
	        }
		    boolean error = false;
	        while ((s_dot = stdError_dot.readLine()) != null) {
	            System.out.println(s_dot);
	            error = true;
	        }
	        //----
	        if(!error){
	        	System.out.println("The monitor in PostScript format is saved at:" + resultModel + ".ps");
			    System.out.println("-----------");
//	        	System.out.println("Attempting to open it with GhostScript, if it doesn't work, use your favorite viewer");
//	        	
//		        Process p_gs = Runtime.getRuntime().exec(cmd_gs);
//		        p_gs.waitFor();
//		        
//		        BufferedReader stdInput_gs = new BufferedReader(new 
//		                InputStreamReader(p_gs.getInputStream()));
//		        BufferedReader stdError_gs = new BufferedReader(new 
//		                InputStreamReader(p_gs.getErrorStream()));
//		        String s_gs = null;
//		        while ((s_gs = stdInput_gs.readLine()) != null) {
//		            System.out.println(s_gs);
//		        }
//		        while ((s_gs = stdError_gs.readLine()) != null) {
//		            System.out.println(s_gs);
//		        }
	        }
		 	} catch (Exception err) {
		 	      err.printStackTrace();
		    }
	        //----
		
	}

}