import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.imageio.ImageIO;

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
public class GoogleImages {
  private String movieName = "";
  
  public GoogleImages(String movie) {
    movieName = movie;
  }
  
  public String getImage() throws ClientProtocolException, IOException {
    CloseableHttpClient httpclient = HttpClients.createDefault();
    try {
      if (movieName!= null) {
        movieName = movieName.replaceAll("\\s", "+");
      }
      HttpGet httpget = new HttpGet("https://www.googleapis.com/customsearch/v1?key={API KEY}"
          + "&cx=007848735119382299257:5knfgks8jss&num=1&searchType=image&ImgSize=large&q=" + movieName + "Movie+Cover");

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
      String coverUrl = "";
      JSONArray arr = obj.getJSONArray("items");
      for (int i = 0; i < arr.length(); i++) {
        coverUrl = arr.getJSONObject(i).getString("link");
      }
      return coverUrl;
      
    } finally {
      httpclient.close();
    }
  }
 

}