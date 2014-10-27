package info.remenska.PASS.monitor.mCRL2;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

import info.remenska.PASS.monitor.mCRL2.Main;

import info.remenska.PASS.wizards.DisciplinedEnglishPage;

public class TestMain1 {
	public static void main(String args[]) throws FileNotFoundException, NullPointerException, NotMonitorableException, Exception{
		String resultModel = new String();
		Main main = new Main();
//		String formula = "[(! StorageManagementDB_removeTasks([tasks(1,Staged,1)]))*.JobDB_setJobStatus(1,Staging).(! (JobDB_setJobStatus(1,Done) || StorageManagementDB_removeTasks([tasks(1,Staged,1)])))*.StorageManagementDB_removeTasks([tasks(1,Staged,1)])]false";
//		String formula = "[(! ready)*][coin]false";
		String formula = " [true*.  __prListLock_release . (!  __prListLock_acquire )*.  __pendingQueuePut(task(1,false)) ] false";
		resultModel = main.generateMonitor(new String[] {"/home/daniela/remenska/Documents/LHCb/GEOC/ProcessPool/ProcessPoolReductions.mcrl2",
				formula,
				"/home/daniela/remenska/Documents/LHCb/GEOC/ProcessPool/ProcessPoolReductions.mcrl2"
				, "false"});
		
//		resultModel = main.generateMonitorVisual(new String[] {"/home/daniela/IBM/rationalsdp/workspace1/UML2mCRL2/Test.mcrl2",
//				formula,
//				"/home/daniela/IBM/rationalsdp/workspace1/UML2mCRL2/Test.mcrl2"
//				, "false"});
		System.out.println("resulting model in: " + resultModel);

		/*
		
		 String[] cmd_mcrl22lps = { "mcrl22lps", "-v", resultModel, resultModel + ".lps" };
		 String[] cmd_lps2lts = { "lps2lts", "-v", resultModel + ".lps", resultModel + ".lts" };
		 String[] cmd_ltsgraph = { "ltsgraph", "-v", resultModel + ".lts" };

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
		    System.out.println("running ltsview:");
		    System.out.println("-----------");
	        Process p_ltsgraph = Runtime.getRuntime().exec(cmd_ltsgraph);
	        p_lps2lts.waitFor();
	        
	        BufferedReader stdInput_ltsgraph = new BufferedReader(new 
	                InputStreamReader(p_ltsgraph.getInputStream()));
	        BufferedReader stdError_ltsgraph = new BufferedReader(new 
	                InputStreamReader(p_ltsgraph.getErrorStream()));
	        
	        String s_ltsgraph = null;
	        while ((s_ltsgraph = stdInput_ltsgraph.readLine()) != null) {
	            System.out.println(s_ltsgraph);
	        }
	        while ((s_ltsgraph = stdError_ltsgraph.readLine()) != null) {
	            System.out.println(s_ltsgraph);
	        }
	        
	        //----

		 	} catch (Exception err) {
		 	      err.printStackTrace();
		    }
	        //----
		 	
	*/
	}

}