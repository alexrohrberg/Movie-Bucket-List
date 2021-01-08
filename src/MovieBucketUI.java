import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.http.client.ClientProtocolException;

import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;
import javax.swing.SwingConstants;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class MovieBucketUI extends JFrame {

  private JPanel contentPane;
  private User curUser;
  Scraper scrape;
  public JList list250;
  public JList myList;
  public JScrollPane scrollPane;
  public JScrollPane myScroll;
  public Events event;
  public JPanel panel;
  public DefaultListModel<String> model;
  public JLabel lblMoviePoster;
  public JLabel lbl250Stat;
  public JLabel lblMyListStat;
  public JLabel lblRating;
  public JLabel lblActors;
  public JLabel lblDirector;
  public JLabel lblTitle;

  /**
   * Create the frame.
   * 
   * @throws IOException
   */
  public MovieBucketUI() throws IOException {
    curUser = new User();
    event = new Events();
    scrape = new Scraper();

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(250, 150, 1000, 600);
    setUndecorated(true);
    contentPane = new JPanel();
    contentPane.setBorder(null);
    setContentPane(contentPane);
    contentPane.setLayout(null);

    panel = new JPanel();
    panel.setBorder(null);
    panel.setBounds(0, 0, 1000, 600);
    contentPane.add(panel);
    panel.setLayout(null);
    panel.setBackground(new Color(0, 0, 0, 0));

    scrape.scapeIMDB();
    String[] movies = scrape.getTitlesAsArray();

    list250 = new JList(movies);
    list250.addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent e) {
        try {
          event.updateMovieInfo();
          event.updateMovieCover();
        } catch (ClientProtocolException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        } catch (IOException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }
      }
    });
    list250.setBorder(null);
    list250.setFont(new Font("Roboto", Font.PLAIN, 14));
    list250.setForeground(Color.decode("#8EA9FF"));
    scrollPane = new JScrollPane(list250);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    scrollPane.setBorder(null);
    scrollPane.setBounds(685, 125, 250, 300);
    scrollPane.setVisible(true);
    panel.add(scrollPane);

    model = new DefaultListModel<>();
    String[] myMovies = (String[]) curUser.toArray();
    myList = new JList(model);
    myList.addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent e) {
        try {
          event.updateMovieInfo();
          event.updateMovieCover();
        } catch (ClientProtocolException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        } catch (IOException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }
      }
    });
    for (int i = 0; i < myMovies.length; i++) {
      model.addElement(myMovies[i]);
    }
    myList.setBorder(null);
    myList.setFont(new Font("Roboto", Font.PLAIN, 14));
    myList.setForeground(Color.decode("#8EA9FF"));
    myScroll = new JScrollPane(myList);
    myScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    myScroll.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
    myScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    myScroll.setBorder(null);
    myScroll.setBounds(685, 125, 250, 300);
    myScroll.setVisible(false);
    panel.add(myScroll);

    JLabel lblWelcome = new JLabel("Welcome");
    lblWelcome.setForeground(Color.WHITE);
    lblWelcome.setFont(new Font("Roboto", Font.PLAIN, 50));
    lblWelcome.setBounds(30, 38, 217, 58);
    panel.add(lblWelcome);

    JLabel lblPlay = new JLabel("▶");
    lblPlay.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        try {
          event.getTrailer();
        } catch (ClientProtocolException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        } catch (IOException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }
      }
    });
    lblPlay.setHorizontalAlignment(SwingConstants.CENTER);
    lblPlay.setForeground(new Color(142, 169, 255));
    lblPlay.setFont(new Font("Roboto", Font.PLAIN, 16));
    lblPlay.setBounds(781, 528, 61, 16);
    panel.add(lblPlay);

    JLabel lblAdd = new JLabel("Add");
    lblAdd.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if (scrollPane.isVisible()) {
          event.addItem();
          event.updateStats();
        }
      }
    });
    lblAdd.setFont(new Font("Roboto", Font.PLAIN, 16));
    lblAdd.setForeground(Color.decode("#8EA9FF"));
    lblAdd.setHorizontalAlignment(SwingConstants.CENTER);
    lblAdd.setBounds(693, 527, 61, 16);
    panel.add(lblAdd);

    JLabel lblDelete = new JLabel("Delete");
    lblDelete.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if (myScroll.isVisible()) {
          if (myList.getSelectedIndex() != -1) {
            int index = myList.getSelectedIndex();
            Movie cur = curUser.getBucket().get(index);
            if (cur.getWatched()) {
              cur.setWatched(false);
              curUser.decrementWatched();
            }
            event.removeItem();
            event.updateStats();
          }
        }
      }
    });
    lblDelete.setHorizontalAlignment(SwingConstants.CENTER);
    lblDelete.setFont(new Font("Roboto", Font.PLAIN, 16));
    lblDelete.setForeground(Color.decode("#8EA9FF"));
    lblDelete.setBounds(858, 526, 61, 16);
    panel.add(lblDelete);

    JLabel addButton = new JLabel("");
    addButton.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if (scrollPane.isVisible()) {
          event.addItem();
          event.updateStats();
        }
      }
    });
    addButton
        .setIcon(new ImageIcon("./img/WhiteButton.png"));
    addButton.setHorizontalAlignment(SwingConstants.CENTER);
    addButton.setBounds(668, 518, 114, 36);
    panel.add(addButton);

    JLabel delButton = new JLabel("");
    delButton.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if (myScroll.isVisible()) {
          if (myList.getSelectedIndex() != -1) {
            int index = myList.getSelectedIndex();
            Movie cur = curUser.getBucket().get(index);
            if (cur.getWatched()) {
              cur.setWatched(false);
              curUser.decrementWatched();
            }
            event.removeItem();
            event.updateStats();
          }
        }
      }
    });
    delButton
        .setIcon(new ImageIcon("./img/WhiteButton.png"));
    delButton.setHorizontalAlignment(SwingConstants.CENTER);
    delButton.setBounds(833, 518, 114, 36);
    panel.add(delButton);

    JLabel playButton = new JLabel("");
    playButton.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        try {
          event.getTrailer();
        } catch (IOException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }
      }
    });
    playButton.setIcon(
        new ImageIcon("./img/PlayBackground.png"));
    playButton.setFont(new Font("Roboto", Font.PLAIN, 13));
    playButton.setBounds(781, 518, 61, 36);
    panel.add(playButton);

    JLabel lblMyList = new JLabel("My List");
    lblMyList.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        event.switchLists();
      }
    });
    lblMyList.setHorizontalAlignment(SwingConstants.CENTER);
    lblMyList.setForeground(Color.WHITE);
    lblMyList.setFont(new Font("Roboto", Font.PLAIN, 16));
    lblMyList.setBounds(768, 37, 61, 16);
    panel.add(lblMyList);

    JLabel lblTop250 = new JLabel("Top 250");
    lblTop250.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        event.switchLists();
      }
    });
    lblTop250.setHorizontalAlignment(SwingConstants.CENTER);
    lblTop250.setForeground(new Color(142, 169, 255));
    lblTop250.setFont(new Font("Roboto", Font.PLAIN, 16));
    lblTop250.setForeground(Color.decode("#8EA9FF"));
    lblTop250.setBounds(892, 37, 61, 16);
    panel.add(lblTop250);

    JLabel top250Button = new JLabel("");
    top250Button.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        event.switchLists();
      }
    });
    top250Button.setIcon(
        new ImageIcon("./img/ToggleButtonWhite.png"));
    top250Button.setBounds(858, 21, 125, 46);
    panel.add(top250Button);

    JLabel myListButton = new JLabel("");
    myListButton.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        event.switchLists();
      }
    });
    myListButton.setIcon(
        new ImageIcon("./img/ToggleButtonBlue.png"));
    myListButton.setBounds(733, 21, 250, 46);
    panel.add(myListButton);

    JLabel lblReset = new JLabel("Reset All");
    lblReset.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        event.resetAll();
      }
    });
    lblReset.setForeground(Color.RED);
    lblReset.setFont(new Font("Roboto", Font.PLAIN, 13));
    lblReset.setBounds(53, 549, 61, 16);
    panel.add(lblReset);

    JLabel resetButton = new JLabel("");
    resetButton.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        event.resetAll();
      }
    });
    resetButton.setHorizontalAlignment(SwingConstants.CENTER);
    resetButton
        .setIcon(new ImageIcon("./img/WhiteButton.png"));
    resetButton.setBounds(25, 539, 114, 36);
    panel.add(resetButton);

    JLabel movieTextBackground = new JLabel("");
    movieTextBackground.setBounds(680, 90, 259, 416);
    panel.add(movieTextBackground);
    movieTextBackground
        .setIcon(new ImageIcon("./img/ListSpace.png"));

    lblMoviePoster = new JLabel("");
    lblMoviePoster
        .setIcon(new ImageIcon("./img/MovieBox.png"));
    lblMoviePoster.setBounds(34, 131, 217, 349);

    panel.add(lblMoviePoster);

    JLabel lblWatchedToggle = new JLabel("Toggle Watched");
    lblWatchedToggle.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        event.toggleWatched();
        event.updateStats();
        event.updateMovieInfo();
      }
    });
    lblWatchedToggle.setFont(new Font("Roboto", Font.PLAIN, 16));
    lblWatchedToggle.setBounds(438, 60, 127, 21);
    lblWatchedToggle.setForeground(Color.decode("#8EA9FF"));
    panel.add(lblWatchedToggle);

    JLabel watchedButton = new JLabel("");
    watchedButton.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        event.toggleWatched();
        event.updateStats();
        event.updateMovieInfo();
      }
    });
    watchedButton.setIcon(
        new ImageIcon("./img/WatchedButton.png"));
    watchedButton.setBounds(415, 52, 175, 36);
    panel.add(watchedButton);

    lblRating = new JLabel("0%");
    lblRating.setBounds(298, 153, 40, 16);
    lblRating.setFont(new Font("Roboto", Font.PLAIN, 20));
    lblRating.setForeground(Color.WHITE);
    panel.add(lblRating);

    lblTitle = new JLabel("Movie Title");
    lblTitle.setHorizontalAlignment(SwingConstants.RIGHT);
    lblTitle.setBounds(346, 153, 228, 16);
    lblTitle.setFont(new Font("Roboto", Font.PLAIN, 15));
    lblTitle.setForeground(Color.WHITE);
    panel.add(lblTitle);

    lblDirector = new JLabel("Director");
    lblDirector.setHorizontalAlignment(SwingConstants.CENTER);
    lblDirector.setBounds(298, 181, 267, 55);
    lblDirector.setFont(new Font("Roboto", Font.PLAIN, 30));
    lblDirector.setForeground(Color.WHITE);
    panel.add(lblDirector);

    lblActors = new JLabel("Actors Include");
    lblActors.setHorizontalAlignment(SwingConstants.CENTER);
    lblActors.setBounds(298, 248, 267, 16);
    lblActors.setFont(new Font("Roboto", Font.PLAIN, 15));
    lblActors.setForeground(Color.WHITE);
    panel.add(lblActors);

    JLabel lblYourWatchingStats = new JLabel("My Watching Stats");
    lblYourWatchingStats.setHorizontalAlignment(SwingConstants.CENTER);
    lblYourWatchingStats.setBounds(298, 316, 267, 44);
    lblYourWatchingStats.setFont(new Font("Roboto", Font.PLAIN, 25));
    lblYourWatchingStats.setForeground(Color.WHITE);
    panel.add(lblYourWatchingStats);

    JLabel lblTop250Label = new JLabel("Top 250");
    lblTop250Label.setBounds(298, 372, 135, 35);
    lblTop250Label.setFont(new Font("Roboto", Font.PLAIN, 30));
    lblTop250Label.setForeground(Color.WHITE);
    panel.add(lblTop250Label);

    lbl250Stat = new JLabel("0.0%");
    lbl250Stat.setHorizontalAlignment(SwingConstants.RIGHT);
    lbl250Stat.setBounds(451, 372, 114, 35);
    lbl250Stat.setFont(new Font("Roboto", Font.PLAIN, 30));
    lbl250Stat.setForeground(Color.WHITE);
    panel.add(lbl250Stat);

    JLabel lblMyListLabel = new JLabel("My List");
    lblMyListLabel.setBounds(298, 419, 135, 36);
    lblMyListLabel.setFont(new Font("Roboto", Font.PLAIN, 30));
    lblMyListLabel.setForeground(Color.WHITE);
    panel.add(lblMyListLabel);

    lblMyListStat = new JLabel("0.0%");
    lblMyListStat.setHorizontalAlignment(SwingConstants.RIGHT);
    lblMyListStat.setBounds(445, 419, 120, 36);
    lblMyListStat.setFont(new Font("Roboto", Font.PLAIN, 30));
    lblMyListStat.setForeground(Color.WHITE);
    panel.add(lblMyListStat);

    JLabel lblMovieStats = new JLabel("");
    lblMovieStats
        .setIcon(new ImageIcon("./img/StatsPanel.png"));
    lblMovieStats.setBounds(279, 131, 311, 157);
    panel.add(lblMovieStats);

    JLabel lblUserStats = new JLabel("");
    lblUserStats
        .setIcon(new ImageIcon("./img/UserStats.png"));
    lblUserStats.setBounds(279, 305, 311, 175);
    panel.add(lblUserStats);

    JLabel leftBox = new JLabel("");
    leftBox.setOpaque(true);
    leftBox.setBorder(null);
    leftBox.setIcon(new ImageIcon("./img/LeftRec.png"));
    leftBox.setBounds(0, 0, 625, 600);
    panel.add(leftBox);
    leftBox.setBackground(new Color(0, 0, 0, 0));

    JLabel rightBox = new JLabel("");
    rightBox.setOpaque(true);
    rightBox.setBorder(null);
    rightBox.setIcon(new ImageIcon("./img/RightRec.png"));
    rightBox.setBounds(0, 0, 1000, 600);
    panel.add(rightBox);

    setBackground(new Color(0, 0, 0, 0));
    contentPane.setBackground(new Color(0, 0, 0, 0));
    panel.setBackground(new Color(0, 0, 0, 0));
    leftBox.setBackground(new Color(0, 0, 0, 0));
    rightBox.setBackground(new Color(0, 0, 0, 0));

  }

  public class Events {
    public void addItem() {
      Object cur = list250.getSelectedValue();
      if (cur != null && !curUser.containsMovie((String) cur)) {
        curUser.addMovie((String) cur);
        model.addElement((String) cur);
      }

      // curUser.printMovies();
    }

    public void removeItem() {
      int cur = myList.getSelectedIndex();
      Object obj = myList.getSelectedValue();
      if (cur != -1) {
        curUser.removeMovie(cur);
        model.removeElement((String) obj);
      }
      // curUser.printMovies();
    }

    public void switchLists() {
      if (scrollPane.isVisible()) {
        scrollPane.setVisible(false);
        myScroll.setVisible(true);
      } else {
        scrollPane.setVisible(true);
        myScroll.setVisible(false);
      }
    }

    public void resetAll() {
      scrollPane.setVisible(true);
      myScroll.setVisible(false);
      model.removeAllElements();
      curUser.clearMovies();
      curUser.setMyWatched(0.0);
      curUser.setTopWatched(0.0);
      curUser.setWatched(0.0);
      updateStats();
      JFrame test = new JFrame();
      test.setUndecorated(true);
      JOptionPane.showMessageDialog(test, "Reset Complete");
    }

    public void getTrailer() throws ClientProtocolException, IOException {
      String movie = "";
      if (scrollPane.isVisible()) {
        movie = (String) list250.getSelectedValue();
      } else
        movie = (String) myList.getSelectedValue();
      // System.out.println(movie);
      YouTube yt = new YouTube(movie);
      yt.getTrailer();
    }

    public void updateStats() {
      curUser.setTopWatched(round(curUser.getWatched() / 250, 2));
      if (!(curUser.getBucket().size() == 0)) {
        curUser.setMyWatched(round(curUser.getWatched() / curUser.getBucket().size(), 2));
      } else
        curUser.setMyWatched(0.0);

      lbl250Stat.setText(curUser.getTopWatched() * 100 + "%");
      lblMyListStat.setText(curUser.getMyWatched() * 100 + "%");
    }

    public void updateMovieInfo() {
      String cur;
      int dex;
      Movie mov;

      if (scrollPane.isVisible()) {
        cur = (String) list250.getSelectedValue();
      } else {
        cur = (String) myList.getSelectedValue();
      }

      for (int i = 0; i < scrape.movieList.size(); i++) {
        if (scrape.movieList.get(i).getTitle().equals(cur)) {
          dex = i;
          mov = scrape.movieList.get(dex);
          lblTitle.setText(mov.getTitle());
          lblActors.setText(mov.getActors());
          lblDirector.setText(mov.getDirector());
          lblRating.setText(Double.toString(mov.getRating()));
        }
      }

    }

    public void updateMovieCover() throws ClientProtocolException, IOException {
      GoogleImages img = null;
      String test = "";
      if (scrollPane.isVisible()) {
        if (list250.getSelectedIndex() != -1) {
          img = new GoogleImages(list250.getSelectedValue().toString());
          test = img.getImage();
        }
      } else {
        if (myList.getSelectedIndex() != -1) {
          img = new GoogleImages(myList.getSelectedValue().toString());
          test = img.getImage();
        }
      }

      // System.out.println(test);
      if (!test.equals("")) {
        URL url = new URL(test);
        BufferedImage image = ImageIO.read(url);
        image = resize(image, 217, 349);
        lblMoviePoster.setIcon((new ImageIcon(image)));
        lblMoviePoster.setBounds(34, 131, 217, 349);
        lblMoviePoster.setVisible(true);
      }
    }

    public void toggleWatched() {
      int dex = -1;
      if (!scrollPane.isVisible()) {
        if (myList.getSelectedIndex() != -1) {
          dex = myList.getSelectedIndex();
          Movie cur = curUser.getBucket().get(dex);
          if (!cur.getWatched()) {
            cur.setWatched(true);
            curUser.incrementWatched();
          } else {
            cur.setWatched(false);
            curUser.decrementWatched();
          }
        }
      } else {
        JFrame test = new JFrame();
        test.setUndecorated(true);
        JOptionPane.showMessageDialog(test, "You can only mark movies as watched from your list. Please switch lists.");
      }

    }

  }

  public static BufferedImage resize(BufferedImage img, int newW, int newH) {
    Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
    BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

    Graphics2D g2d = dimg.createGraphics();
    g2d.drawImage(tmp, 0, 0, null);
    g2d.dispose();

    return dimg;
  }

  public static double round(double value, int places) {
    if (places < 0)
      throw new IllegalArgumentException();

    BigDecimal bd = BigDecimal.valueOf(value);
    bd = bd.setScale(places, RoundingMode.HALF_UP);
    return bd.doubleValue();
  }
}
