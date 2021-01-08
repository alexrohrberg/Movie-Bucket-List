import java.util.ArrayList;

public class User extends ArrayList<Object> {
  private ArrayList<Movie> bucket;
  private double watched;
  private double myWatched;
  private double topWatched;

  public User() {
    bucket = new ArrayList<Movie>();
    watched = 0;
    myWatched = 0.0;
    topWatched = 0.0;
  }

  public User(ArrayList<Movie> bucket) {
    this.bucket = bucket;
    watched = 0;
    myWatched = 0.0;
    topWatched = 0.0;
  }
  
  public void incrementWatched() {
    watched++;
  }
  
  public void decrementWatched() {
    watched--;
  }
  
  public double getWatched() {
    return watched;
  }
  
  public void setWatched(double watched) {
    this.watched = watched;
  }
  
  public double getMyWatched() {
    return myWatched;
  }

  public void setMyWatched(double myWatched) {
    this.myWatched = myWatched;
  }

  public double getTopWatched() {
    return topWatched;
  }

  public void setTopWatched(double topWatched) {
    this.topWatched = topWatched;
  }

  public ArrayList<Movie> getBucket() {
    return bucket;
  }

  public void addMovie(Movie movie) {
    bucket.add(movie);
  }

  public void addMovie(String movie) {
    bucket.add(new Movie(movie));
  }

  public void removeMovie(Movie movie) {
    bucket.remove(movie);
  }

  public void removeMovie(int position) {
    bucket.remove(position);
  }

  public void clearMovies() {
    bucket.clear();
  }

  public boolean containsMovie(Movie movie) {
    return (bucket.contains(movie)) ? true : false;
  }

  public boolean containsMovie(String movie) {
    if (bucket.isEmpty()) {
      return false;
    }
    for (Movie cur : bucket) {
      if (cur.getTitle().equals(movie)) {
        return true;
      }
    }
    return false;
  }

  public void printMovies() {
    for (Movie cur : bucket) {
      System.out.println(cur.getTitle());
    }
  }

  @Override
  public Object[] toArray() {
    String[] movieTitles = new String[bucket.size()];
    for (int i = 0; i < bucket.size(); i++) {
      movieTitles[i] = bucket.get(i).getTitle();
    }
    return movieTitles;
  }

}
