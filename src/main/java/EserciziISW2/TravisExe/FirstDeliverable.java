package EserciziISW2.TravisExe;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FirstDeliverable {
	
	private static final String PROJNAME ="STDCXX";
	private static final Integer STEP = 1000;
	
	private static String readAll(Reader rd) throws IOException {
	      StringBuilder sb = new StringBuilder();
	      int cp;
	      while ((cp = rd.read()) != -1) {
	         sb.append((char) cp);
	      }
	      return sb.toString();
	   }
	
	
	//Function for extract a Json from a url
	public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
	      InputStream is = new URL(url).openStream();	     
	      try( InputStreamReader tmp = new InputStreamReader(is, StandardCharsets.UTF_8);
	    	   BufferedReader rd = new BufferedReader(tmp);
	    	)  
	      
	      {
	         String jsonText = readAll(rd);
	         return new JSONObject(jsonText);
	         
	       } finally {
	         is.close();
	       }
	   }
	
	public static void main(String[] args) throws IOException, JSONException{
		
		Integer start = 0;
		Integer end = 0;
		Integer total = 1;
		FileWriter fileWriter = null;
		List<String> tickets = new ArrayList<>();
		 
		do {
			
			//Only gets a max of STEP at a time, so must do this multiple times if total >STEP
			
			end = start + STEP;
			
			String url = "https://issues.apache.org/jira/rest/api/2/search?jql=project=%22"
					+ PROJNAME + "%22AND%22resolution%22=%22fixed%22&fields=key,resolutiondate,created&startAt="
					+ start.toString() + "&maxResults=" + end.toString();
			
			JSONObject json = readJsonFromUrl(url);
			total = json.getInt("total");
			
			JSONArray issues = json.getJSONArray("issues");
			
			try {
				
				String fileName = PROJNAME + "_FixedTickets.csv";
				fileWriter = new FileWriter(fileName);
				fileWriter.append("Index,Tickets Key,Create date,Resolution Date");
	            fileWriter.append("\n");
				
				for (; start < total && start < end; start++) {
					
					JSONObject tmp = issues.getJSONObject(start%STEP);
					JSONObject date = tmp.getJSONObject("fields");
					
					fileWriter.append(start.toString());
		            fileWriter.append(",");
		            fileWriter.append(tmp.get("key").toString());
	                fileWriter.append(",");
	                //Local date (yyyy-MM-dd)
		            fileWriter.append(date.get("created").toString());
		            fileWriter.append(",");
		            fileWriter.append(date.get("resolutiondate").toString());
		            fileWriter.append("\n");
		            tickets.add(tmp.get("key").toString());
		         }
				
			}catch (Exception e) {
	            System.out.println("Error in csv writer");
	            e.printStackTrace();
	         } finally {
	            try {
	               fileWriter.flush();
	               fileWriter.close();
	            } catch (IOException e) {
	               System.out.println("Error while flushing/closing fileWriter !!!");
	               e.printStackTrace();
	            }
	         }
						
		}while(start < total); 
		
	}
}
