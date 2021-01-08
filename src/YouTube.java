import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * This example demonstrates the use of the {@link ResponseHandler} to simplify
 * the process of processing the HTTP response and releasing associated
 * resources.
 */
public class YouTube {
  private String movieName = "";
  
  public YouTube(String movie) {
    movieName = movie;
  }
  
  public void getTrailer() throws ClientProtocolException, IOException {
    if (movieName == null) {
      return;
    }
    CloseableHttpClient httpclient = HttpClients.createDefault();
    try {
      if (movieName!= null) {
        movieName = movieName.replaceAll("\\s", "+");
      }
      HttpGet httpget = new HttpGet("https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=1&q="
          + movieName + "Trailer" + "&key={APIKEY}");

      //System.out.println("Executing request " + httpget.getRequestLine());

      // Create a custom response handler
      ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

        @Override
        public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
          int status = response.getStatusLine().getStatusCode();
          if (status >= 200 && status < 300) {
            HttpEntity entity = response.getEntity();
            return entity != null ? EntityUtils.toString(entity) : null;
          } else {
            throw new ClientProtocolException("Unexpected response status: " + status);
          }
        }

      };
      String responseBody = httpclient.execute(httpget, responseHandler);
      //System.out.println(responseBody);

      String jsonString = responseBody;
      JSONObject obj = new JSONObject(jsonString);
      String videoID = "";
      JSONArray arr = obj.getJSONArray("items");
      for (int i = 0; i < arr.length(); i++) {
        videoID = arr.getJSONObject(i).getJSONObject("id").getString("videoId");
      }
      String url = "https://www.youtube.com/watch?v=" + videoID;
      System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
      WebDriver drive = new ChromeDriver(); 
      drive.get(url);
      
    } finally {
      httpclient.close();
    }
  }
 

}
