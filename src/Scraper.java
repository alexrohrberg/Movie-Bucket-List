import java.io.IOException;
import java.io.PrintWriter;
import java.awt.EventQueue;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Scraper {
  public ArrayList<Movie> movieList = new ArrayList<Movie>();
  public Movie[] movieArray;

  public Movie[] scapeIMDB() throws IOException {
    Document doc = Jsoup.connect("https://www.imdb.com/chart/top/").timeout(6000).get();
    Elements body = doc.select("tbody.lister-list");

    // System.out.println(body.select("tr").size());

    for (Element e : body.select("tr")) {
      String img = e.select("td.posterColumn img").attr("src");

      String title = e.select("td.posterColumn img").attr("alt");

      String contributors = e.select("td.titleColumn a").attr("title");

      String tempYear = e.select("td.titleColumn span.secondaryInfo").text();
      int year = Integer.parseInt(tempYear.replaceAll("[()]", ""));

      double rating = Double.parseDouble(e.select("td.ratingColumn.imdbRating strong").text().trim());

      movieList.add(new Movie(img, title, contributors, year, rating));

    }

    FileWriter writer = new FileWriter("TopFilms.txt");
    for (Movie str : movieList) {
      writer.write(str.toString() + "\n");
    }
    writer.close();

    if (movieList.isEmpty()) {
      return null;
    }
    int size = movieList.size();
    Movie[] output = new Movie[size];
    for (int i = 0; i < size; i++) {
      output[i] = movieList.get(i);
    }
    return output;

  }

  public String[] getTitlesAsArray() throws IOException {
    Document doc = Jsoup.connect("https://www.imdb.com/chart/top/").timeout(6000).get();
    Elements body = doc.select("tbody.lister-list");
    String[] movies = new String[250];
    int i = 0;
    for (Element e : body.select("tr")) {
      String title = e.select("td.posterColumn img").attr("alt");
      movies[i] = title;
      i++;
    }
    return movies;
  }

  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          MovieBucketUI frame = new MovieBucketUI();
          frame.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });

  }

}
