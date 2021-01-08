import java.util.ArrayList;

public class Movie {

  private String imgUrl;
  private String title;
  private String contributors;
  private int year;
  private double rating;
  private boolean watched;

  public Movie() {
    imgUrl = "";
    title = "";
    contributors = "";
    year = 0;
    rating = 0;
  }

  public Movie(String imgUrl, String title, String contributors, int year, double rating) {
    this.imgUrl = imgUrl;
    this.title = title;
    this.contributors = contributors;
    this.year = year;
    this.rating = rating;
  }

  public Movie(String title) {
    this.title = title;
  }

  public String getImgUrl() {
    return imgUrl;
  }

  public void setImgUrl(String imgUrl) {
    this.imgUrl = imgUrl;
  }

  public String getTitle() {
    return title;
  }

  public String getContributors() {
    return contributors;
  }

  public void addContributors(String contributors) {
    this.contributors = this.contributors + contributors;
  }

  public int getYear() {
    return year;
  }

  public double getRating() {
    return rating;
  }

  public boolean getWatched() {
    return watched;
  }

  public void setWatched(boolean watched) {
    this.watched = watched;
  }

  public String getDirector() {
    if (contributors == null) {
      return null;
    }
    int dex = contributors.indexOf("(") - 1;
    return contributors.substring(0, dex);
  }

  public String getActors() {
    if (contributors == null) {
      return null;
    }
    int dex = contributors.indexOf(")") + 3;
    return contributors.substring(dex, contributors.length());
  }

  public String toString() {
    return title + "\n" + year + "\n" + "Rating: " + rating + "\n" + contributors + "\n" + imgUrl + "\n";
  }

}
