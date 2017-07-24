
import org.junit.Test;


public class Snippets {
	
  
  /* this can be used to save raw-byte-data into a file. e.g. a PDF you download */
	@Test
	public void rawBinaryDataToFileDownload(String url, String fileToWrite) {
    	URL obj = new URL(url);
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
      
      con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", USER_AGENT);
			//con.setRequestProperty("X-Cookie", "token=" + token);
  
			InputStream is = con.getInputStream();
			FileOutputStream fos = new FileOutputStream(new File(fileToWrite));  
			int inByte;
			while((inByte = is.read()) != -1)
			     fos.write(inByte);
			is.close();
			fos.close();
			con.disconnect();
	}

}
