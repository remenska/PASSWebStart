package info.remenska.PASS.monitor.mCRL2;
import java.io.FileNotFoundException;

import info.remenska.PASS.monitor.mCRL2.Main;

import info.remenska.PASS.wizards.DisciplinedEnglishPage;

public class TestMain {
	public static void main(String args[]) throws FileNotFoundException, NullPointerException, NotMonitorableException, Exception{
		String resultModel = new String();
		Main main = new Main();
		String formula = "[(! StorageManagementDB_removeTasks([tasks(1,Staged,1)]))*.JobDB_setJobStatus(1,Staging).(! (JobDB_setJobStatus(1,Staged) || StorageManagementDB_removeTasks([tasks(1,Staged,1)])))*.StorageManagementDB_removeTasks([tasks(1,Staged,1)])]false";
//		String formula = "[(!R )*. P . (! ( S || R ))*. R ] false";
//		resultModel = main.generateMonitor(new String[] {"/home/daniela/IBM/rationalsdp/workspace1/UML2mCRL2/modelFlattened.mcrl2",
//				formula,
//				"/home/daniela/IBM/rationalsdp/workspace1/UML2mCRL2/modelFlattened.mcrl2"
//				, "false"});
		
		resultModel = main.generateMonitor(new String[] {"/home/daniela/IBM/rationalsdp/workspace1/UML2mCRL2/modelFlattened.mcrl2",
				formula,
				"/home/daniela/IBM/rationalsdp/workspace1/UML2mCRL2/modelFlattened.mcrl2"
				, "false"});
		
		System.out.println("resulting model in: " + resultModel);
	}

}