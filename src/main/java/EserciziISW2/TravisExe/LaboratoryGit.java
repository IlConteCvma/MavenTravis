package EserciziISW2.TravisExe;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.eclipse.jgit.api.Git;

import org.eclipse.jgit.api.errors.*;
import org.eclipse.jgit.revwalk.RevCommit;


public class LaboratoryGit {
	
	//private static final String URL = "https://github.com/IlConteCvma/Test-gitLab";
	//private static final String PATH = "C:\\Users\\marca\\Desktop\\Test";
	private static final String URL = "https://github.com/apache/stdcxx.git";
	private static final String PATH = "C:\\Users\\marca\\Desktop\\STDCXX";
	
	public static void getCommitByWord(Git git,String word) {
		
		
		try {
			Iterable<RevCommit> commits = git.log().call();
			Iterator<RevCommit> itr = commits.iterator();
			
			while(itr.hasNext()) {
				RevCommit element = itr.next();
				
				String comment = element.getFullMessage();
				
				//if (comment.contains(word)) {
					System.out.println("Comment: " + comment);
					System.out.println("ID: " + element.getId());
					System.out.println("Name: " + element.getName());
					System.out.println("Date: " + element.getCommitTime());
					System.out.println("Name 2: " + element.getFullMessage().toString().contains(""));
				//}
				
			}

			
			
		} catch (Throwable  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
		
	}
	
	
	public static void main(String[] args) throws InvalidRemoteException, TransportException, GitAPIException{
		
		
		
		//Git git = Git.cloneRepository().setURI(URL)
			//	.setDirectory(new File(PATH)).call();
		
		Git git = null;
		try {
			git = Git.open(new File(PATH));
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getCommitByWord(git,"smell");
		
		
	}
}
