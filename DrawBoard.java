//Some of the colors aren’t popping right to the corresponding properties
import java.awt.*;  //for Graphics
import javax.swing.*;  //for JComponent, JFrame
import java.net.*;  //for URL
import java.util.*;

import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class DrawBoard extends JComponent implements MouseListener,ActionListener
{
  private Image board;
  private Image dog;
  private Image car;
  private Image boat;
  private Image shoe;
  private Image house1;
  private Image house2;
  private Image house3;
  private Image house4;
  private Image hotel;
  private Image circle;
  private Image[] dieImages;
  private boolean start;
  private Game game;
  private JLabel infoLabel;
  private JLabel infoLabel2;
  private JLabel turnLabel;
  private String labelInfo;
  private boolean inBuyHouses;
  
  private JButton mortgageButton;
  private JButton mortgageHouseButton;
  private JButton buyHouseButton;
  private JButton buyHotelButton;
  private JButton useJailCardButton;
  private JButton newGameButton;
  private JButton mortgageHotelButton;

  private ArrayList<String> playerInfos;
  private ArrayList<String> textColors;
  JFrame frame;
  
  private Location lastLocationClicked;
  //private int numPlayers;
  
  public DrawBoard(Game g)
  {
    game = g;
    int numPlayers = game.getNumPlayers();
    playerInfos = new ArrayList<String>();
    start = true;
    frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //frame.
    frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.LINE_AXIS));
    
    JPanel leftPanel = new JPanel();
    leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));
    leftPanel.setOpaque(false);
    frame.getContentPane().add(leftPanel);
    
    inBuyHouses = false;
    
    turnLabel = new JLabel("", SwingConstants.CENTER);
    mortgageButton = new JButton("Mortgage Property");
    mortgageHouseButton = new JButton("Mortgage House");
    mortgageHotelButton = new JButton("Mortgage Hotel");
    buyHouseButton = new JButton("Buy House");
    buyHotelButton = new JButton("Buy Hotel");
    useJailCardButton = new JButton("Use Jail Card");
    newGameButton = new JButton("New Game");
    
    buyHouseButton.setActionCommand("house");
    buyHouseButton.addActionListener(this);
    mortgageButton.setActionCommand("property");
    mortgageButton.addActionListener(this);
    mortgageHouseButton.setActionCommand("mortHouse");
    mortgageHouseButton.addActionListener(this);
    mortgageHotelButton.setActionCommand("mortHotel");
    mortgageHotelButton.addActionListener(this);
    buyHotelButton.setActionCommand("hotel");
    buyHotelButton.addActionListener(this);
    useJailCardButton.setActionCommand("jailCard");
    useJailCardButton.addActionListener(this);
    newGameButton.setActionCommand("new");
    newGameButton.addActionListener(this);
    
    
    //sellPropertyButton.setAction(sellProperty);
   // buyHouseButton.setAction(game.);
    
    leftPanel.add(turnLabel);
    leftPanel.add(buyHouseButton);
    leftPanel.add(buyHotelButton);
    leftPanel.add(mortgageButton);
    leftPanel.add(mortgageHouseButton);
    leftPanel.add(mortgageHotelButton);
    leftPanel.add(useJailCardButton);
    leftPanel.add(newGameButton);

    if (numPlayers>2)
      setPreferredSize(new Dimension(950, 798));
    else
      setPreferredSize(new Dimension(800, 798));
    frame.getContentPane().setBackground(new Color(205, 230, 208));
    frame.getContentPane().add(this);
    addMouseListener(this);

    infoLabel = new JLabel("", SwingConstants.RIGHT);
    infoLabel.setPreferredSize(new Dimension(145, 200));
    
    infoLabel2 = new JLabel("", SwingConstants.RIGHT);
    infoLabel.setPreferredSize(new Dimension(145, 200));
    
    //infoLabel.setText("<html>line 1<br/>line 2</html>");
    frame.getContentPane().add(infoLabel);
    if (numPlayers>2)
      frame.getContentPane().add(infoLabel2);
    frame.pack();
    URL url = getClass().getResource("monopoly-board.jpg");
    if (url == null)
      throw new RuntimeException("file not found:  " + "monopoly-board.jpg");
    board = new ImageIcon(url).getImage();
    frame.setVisible(true);
    
    int players = 0;
    dog = null;
    car = null;
    shoe = null;
    boat = null;
    while (numPlayers > 0)
    {
      if (players == 0)
      {
        URL url1 = getClass().getResource("feinberg.png");
        if (url1 == null)
          throw new RuntimeException("file not found:  " + "feinberg.png");
        dog = new ImageIcon(url1).getImage();
      }
      else if (players == 1)
      {
        URL url2 = getClass().getResource("stout.png");
        if (url2 == null)
          throw new RuntimeException("file not found:  " + "stout.png");
        car = new ImageIcon(url2).getImage();
      }
      else if (players == 2)
      {
        URL url3 = getClass().getResource("boat.png");
        if (url3 == null)
          throw new RuntimeException("file not found:  " + "boat.png");
        boat = new ImageIcon(url3).getImage();
      }
      else if (players == 3)
      {
        URL url4 = getClass().getResource("shoe.png");
        if (url4 == null)
          throw new RuntimeException("file not found:  " + "shoe.png");
        shoe = new ImageIcon(url4).getImage();
      }
      players++;
      numPlayers--;
      frame.setVisible(true);
    }   
    URL url5 = getClass().getResource("house1.png");
    if (url5 == null)
      throw new RuntimeException("file not found:  " + "house1.png");
    house1 = new ImageIcon(url5).getImage();
    
    URL url6 = getClass().getResource("house2.png");
    if (url6 == null)
      throw new RuntimeException("file not found:  " + "house2.png");
    house2 = new ImageIcon(url6).getImage();
    
    URL url7 = getClass().getResource("house3.png");
    if (url7 == null)
      throw new RuntimeException("file not found:  " + "house3.png");
    house3 = new ImageIcon(url7).getImage();
    
    URL url8 = getClass().getResource("house4.png");
    if (url8 == null)
      throw new RuntimeException("file not found:  " + "house4.png");
    house4 = new ImageIcon(url8).getImage();  
     
    URL urlHotel = getClass().getResource("hotel.png");
    if (urlHotel == null)
      throw new RuntimeException("file not found: " + "hotel.png");
    hotel = new ImageIcon(urlHotel).getImage();
    frame.setVisible(true);
    
    URL urlCircle = getClass().getResource("circle.png");
    if (urlCircle == null)
      throw new RuntimeException("file not found:  " + "circle.png");
    circle = new ImageIcon(urlCircle).getImage(); 
    frame.setVisible(true);
    
    
    dieImages = new Image[7];
    for (int i = 1; i <= 6; i++)
    {
      String fileName = "die" + i + ".png";
      url = getClass().getResource(fileName);
      if (url == null)
        throw new RuntimeException("file not found:  " + fileName);
      dieImages[i] = new ImageIcon(url).getImage();
    }
    
    
    textColors = new ArrayList<String>();
    //blue
    for (int i = 0; i<2; i++)
      textColors.add("<font color=#226ecc>");
    //green
    for (int i = 0; i<3; i++)
      textColors.add("<font color=#27a31b>");
    //yellow
    for (int i = 0; i<3; i++)
      textColors.add("<font color=#fffb72>");
    //red
    for (int i = 0; i<3; i++)
      textColors.add("<font color=#ff0000>");
    //orange
    for (int i = 0; i<3; i++)
      textColors.add("<font color=#ff7700>");
    //pink
    for (int i = 0; i<3; i++)
      textColors.add("<font color=#ff00bb>");
    //light blue
    for (int i = 0; i<3; i++)
      textColors.add("<font color=#63d7ff>");
    //brown
    for (int i = 0; i<2; i++)
      textColors.add("<font color=#6d3b18>");
  }
  
  public boolean inBuyHouses()
  {
    return inBuyHouses;
  }
  
  public void setBuyHouses(boolean x)
  {
    inBuyHouses = x;
  }
  
  //magically called whenever java feels like it
  public void paintComponent(Graphics g)
  {  
    g.drawImage(board, 0, 0, null); 
    ArrayList<Location> locations = game.getPropertyLocations();
    
    int numPlayers = game.getNumPlayers();
    
    int locationP1 = game.getPlayer(1).getLocation();
    Location p1 = locations.get(locationP1);
    g.drawImage(dog, p1.getX(), p1.getY(), null);
    
    if (numPlayers >= 2)
    {
      int locationP2 = game.getPlayer(2).getLocation();
      Location p2 = locations.get(locationP2);
      g.drawImage(car, p2.getX(), p2.getY(), null);
    }
    
    if (numPlayers >= 3)
    {
      int locationP3 = game.getPlayer(3).getLocation();
      Location p3 = locations.get(locationP3);
      g.drawImage(boat, p3.getX(), p3.getY(), null);
    }
    
    if (numPlayers == 4)
    {
      int locationP4 = game.getPlayer(4).getLocation();
      Location p4 = locations.get(locationP4);
      g.drawImage(shoe, p4.getX(), p4.getY(), null);
    }
    turnLabel.setText("<html> Player " + (game.getTurn()+1) + "'s turn </html>");
    updateLabel();
    
    ///HOUSES
    ArrayList<Location> houseLocs = game.getHouseLocations();
    SimpleMap<String, Property> props = game.getProperties();
    for (int i = 0; i < houseLocs.size(); i++)
    {
      if (houseLocs.get(i) != null)
        drawHouses(g, houseLocs.get(i), props.get(game.getAllSpots().get(i)).getNumHouses());
    }
    
    ArrayList<Location> hotelLocs = game.getHotelLocations();
    for (int i = 0; i < hotelLocs.size(); i++)
    {
      if (hotelLocs.get(i) != null)
        drawHotels(g, hotelLocs.get(i), props.get(game.getAllSpots().get(i)).hasHotel());
    }
    
    //draw circles on the locations you can click to get a house
    Player currentPlayer = game.getCurrentPlayer();
    ArrayList<Integer> indexesCanAddHouses = game.indexesCanAddHouses(currentPlayer);
    for (Integer i : indexesCanAddHouses)
    {
      Location loc = game.getPropertyLocations().get(i);
      g.drawImage(circle, loc.getX(), loc.getY(), null);
    }
    
    updateLabel();  
    
    int[] dice = game.getDice();
    int d1 = dice[0];
    int d2 = dice[1];

    g.drawImage(dieImages[d1], 300, 350, null);
    g.drawImage(dieImages[d2], 425, 350, null);
   
  }
  
  public void drawHotels(Graphics g, Location loc, boolean hasHotel)
  {
    if (hasHotel) //if it should have a hotel there
      g.drawImage(hotel, loc.getX(), loc.getY(), null);
  }
  
   public void updateLabel()
  {
    int numPlayers = game.getNumPlayers();
    
    if (numPlayers<=2)
    {
      playerInfos = new ArrayList<String>();
      for (int i = 0; i<numPlayers; i++)
      {
        if (game.getTurn()==i)
          playerInfos.add("<br/> <b><u> Player " + (i+1) + "</b></u>" + ": $" + game.getPlayer(i+1).getMoney() + "</font>");
        else
          playerInfos.add("<br/> Player " + (i+1) + ": $" + game.getPlayer(i+1).getMoney() + "</font>");
        playerInfos.add("<br/> Properties");
        SimpleMap<String, Player> properties = game.getPropertyOwners();
        //System.out.println(properties.toString());
        int index = 0;
        for(String key: properties.keySet())
        {
          if (properties.get(key)!=null)
          {
            if (properties.get(key).equals(game.getPlayer(i+1)))
            {
              playerInfos.add("<br/> " + textColors.get(index) + "    " + key + "</font>");
            }
          }
          //System.out.println(index);
          index++;
        }
        playerInfos.add("<br/> ");
        playerInfos.add("<br/> Railroads");
        SimpleMap<String, Player> railroads = game.getRROwners();
        // System.out.println(railroads.toString());
        for(String key: railroads.keySet())
        {
          if (railroads.get(key)!=null)
          {
            if (railroads.get(key).equals(game.getPlayer(i+1)))
            {
              playerInfos.add("<br/> " + "    " + key);
            }
          }
        }
        playerInfos.add("<br/> ");
        playerInfos.add("<br/> Utilities");
        SimpleMap<String, Player> utilities = game.getUtilityOwners();
        // System.out.println(properties.toString());
        for(String key: utilities.keySet())
        {
          if (utilities.get(key)!=null)
          {
            if (utilities.get(key).equals(game.getPlayer(i+1)))
            {
              playerInfos.add("<br/> " + "    " + key);
            }
          }
        }
        playerInfos.add("<br/> ");
        playerInfos.add("<br/> Get out of Jail Cards: " + game.getPlayer(i+1).numJailCards());
        playerInfos.add("<br/> ");
        if (game.getPlayer(i+1).inJail())
        {
          playerInfos.add("<br/> Number of turns in jail: " + game.getPlayer(i+1).turnsInJail());
        }
        playerInfos.add("<br/> ");
        
      }
      //System.out.println(playerInfos.size());
      labelInfo = "<html>";
      for (int i = 0; i<playerInfos.size(); i++)
      {
        labelInfo+=playerInfos.get(i);
      }
      labelInfo += "</html>";
      infoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
      infoLabel.setText(labelInfo);
    }
    else
    {
      playerInfos = new ArrayList<String>();
      for (int i = 0; i<2; i++)
      {
        
        if (game.getTurn()==i)
          playerInfos.add("<br/> <b><u> Player " + (i+1) + "</b></u>" + ": $" + game.getPlayer(i+1).getMoney() + "</font>");
        else
          playerInfos.add("<br/> Player " + (i+1) + ": $" + game.getPlayer(i+1).getMoney() + "</font>");
        playerInfos.add("<br/> Properties");
        SimpleMap<String, Player> properties = game.getPropertyOwners();
        //System.out.println(properties.toString());
        int index = 0;
        for(String key: properties.keySet())
        {
          if (properties.get(key)!=null)
          {
            if (properties.get(key).equals(game.getPlayer(i+1)))
            {
              playerInfos.add("<br/> " + textColors.get(index) + "    " + key + "</font>");
            }
          }
          //System.out.println(index);
          index++;
        }
        playerInfos.add("<br/> ");
        playerInfos.add("<br/> Railroads");
        SimpleMap<String, Player> railroads = game.getRROwners();
        // System.out.println(railroads.toString());
        for(String key: railroads.keySet())
        {
          if (railroads.get(key)!=null)
          {
            if (railroads.get(key).equals(game.getPlayer(i+1)))
            {
              playerInfos.add("<br/> " + "    " + key);
            }
          }
        }
        playerInfos.add("<br/> ");
        playerInfos.add("<br/> Utilities");
        SimpleMap<String, Player> utilities = game.getUtilityOwners();
        // System.out.println(properties.toString());
        for(String key: utilities.keySet())
        {
          if (utilities.get(key)!=null)
          {
            if (utilities.get(key).equals(game.getPlayer(i+1)))
            {
              playerInfos.add("<br/> " + "    " + key);
            }
          }
        }
        playerInfos.add("<br/> ");
        playerInfos.add("<br/> Get out of Jail Cards: " + game.getPlayer(i+1).numJailCards());
        playerInfos.add("<br/> ");
        if (game.getPlayer(i+1).inJail())
        {
          playerInfos.add("<br/> Number of turns in jail: " + game.getPlayer(i+1).turnsInJail());
        }
        playerInfos.add("<br/> ");
        
      }
      labelInfo = "<html>";
      for (int i = 0; i<playerInfos.size(); i++)
      {
        labelInfo+=playerInfos.get(i);
      }
      labelInfo += "</html>";
      infoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
      infoLabel.setText(labelInfo);
      
      //////2nd right label
      playerInfos = new ArrayList<String>();
      for (int i = 2; i<numPlayers; i++)
      {
        
        if (game.getTurn()==i)
          playerInfos.add("<br/> <b><u> Player " + (i+1) + "</b></u>" + ": $" + game.getPlayer(i+1).getMoney() + "</font>");
        else
          playerInfos.add("<br/> Player " + (i+1) + ": $" + game.getPlayer(i+1).getMoney() + "</font>");
        playerInfos.add("<br/> Properties");
        SimpleMap<String, Player> properties = game.getPropertyOwners();
        //System.out.println(properties.toString());
        int index = 0;
        for(String key: properties.keySet())
        {
          if (properties.get(key)!=null)
          {
            if (properties.get(key).equals(game.getPlayer(i+1)))
            {
              playerInfos.add("<br/> " + textColors.get(index) + "    " + key + "</font>");
            }
          }
          //System.out.println(index);
          index++;
        }
        playerInfos.add("<br/> ");
        playerInfos.add("<br/> Railroads");
        SimpleMap<String, Player> railroads = game.getRROwners();
        // System.out.println(railroads.toString());
        for(String key: railroads.keySet())
        {
          if (railroads.get(key)!=null)
          {
            if (railroads.get(key).equals(game.getPlayer(i+1)))
            {
              playerInfos.add("<br/> " + "    " + key);
            }
          }
        }
        playerInfos.add("<br/> ");
        playerInfos.add("<br/> Utilities");
        SimpleMap<String, Player> utilities = game.getUtilityOwners();
        // System.out.println(properties.toString());
        for(String key: utilities.keySet())
        {
          if (utilities.get(key)!=null)
          {
            if (utilities.get(key).equals(game.getPlayer(i+1)))
            {
              playerInfos.add("<br/> " + "    " + key);
            }
          }
        }
        playerInfos.add("<br/> ");
        playerInfos.add("<br/> Get out of Jail Cards: " + game.getPlayer(i+1).numJailCards());
        playerInfos.add("<br/> ");
        if (game.getPlayer(i+1).inJail())
        {
          playerInfos.add("<br/> Number of turns in jail: " + game.getPlayer(i+1).turnsInJail());
        }
        playerInfos.add("<br/> ");
        
      }
      //System.out.println(playerInfos.size());
      labelInfo = "<html>";
      for (int i = 0; i<playerInfos.size(); i++)
      {
        labelInfo+=playerInfos.get(i);
      }
      labelInfo += "</html>";
      infoLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
      infoLabel2.setText(labelInfo);
    }
  }
  
  public void drawPlayer(Graphics g, int x, int y)
  {
    
  }
  
  public void drawHouses(Graphics g, Location loc, int numHouses)
  {
    if (numHouses == 1)
      g.drawImage(house1, loc.getX(), loc.getY(), null);
    else if (numHouses == 2)
      g.drawImage(house2, loc.getX(), loc.getY(), null);
    else if (numHouses == 3)
      g.drawImage(house3, loc.getX(), loc.getY(), null);
    else if (numHouses == 4)      
      g.drawImage(house4, loc.getX(), loc.getY(), null);
    else {}
  }
  
  public void showMessageDialog(String message)
  {
    JOptionPane.showMessageDialog(this, message);
  }
  
  public void getClick()
  {
    
  }
  
  public void update()
  {
    repaint();  //java:  "when i have time, call paintComponent"
    try{Thread.sleep(100);}catch(Exception e){}
  }
  
  public void labelToString()
  {
    for (int i = 0; i<playerInfos.size(); i++)
    {
      System.out.println(i+ ": " + playerInfos.get(i));
    }
  }
  
  public void mouseExited(MouseEvent e)
  {
  }
  
  public void mouseEntered(MouseEvent e)
  {
  }
  
  public void mouseReleased(MouseEvent e)
  {
  }
  
  public void mousePressed(MouseEvent e)
  {
    lastLocationClicked = new Location(e.getX(), e.getY());
  }
  
  public void mouseClicked(MouseEvent e)
  {
  }
  
  //waits for user to click on screen, and returns coordinates
  public Location getClickedLocation()
  {
    lastLocationClicked = null;
    while (lastLocationClicked == null)
    {
      try{Thread.sleep(100);}catch(Exception e){};
    }
    return lastLocationClicked;
  }
  
  public void actionPerformed(ActionEvent e)
  {
    String command = e.getActionCommand();
    final Player player = game.getCurrentPlayer();
    if (command.equals("house"))
    {
      
      new Thread()
      {
        public void run()
        {
          game.buyHouses(player);
        }
      }.start();
    }
    else if (command.equals("hotel"))
    {
      new Thread()
      {
        public void run()
        {
          game.buyHotel(player);
        }
      }.start();
    }
    else if (command.equals("jailCard"))
    {
      new Thread()
      {
        public void run()
        {
          if (player.inJail() && player.hasJailCard())
          {
            player.getOutofJail();
            player.usedJailCard();
          }
        }
      }.start();
    }
    else if (command.equals("property"))
    {
      new Thread()
      {
        public void run()
        {
          ArrayList<String> allprops = game.getAllProperties(player);
          if (!allprops.isEmpty())
            game.mortgageProp(player);
        }
      }.start();
    }
    else if (command.equals("mortHouse"))
    {
      new Thread()
      {
        public void run()
        {
          if (!game.whereCanAddHouses(player).isEmpty() || !game.whereCanAddHotel(player).isEmpty())
            game.mortgageHouse(player);
        }
      }.start();
    }
    else if (command.equals("mortHotel"))
    {
      new Thread()
      {
        public void run()
        {
          game.mortgageHotel(player); //!!!!!
        }
      }.start();
    }
    else if (command.equals("new"))
    {
      new Thread()
      {
        public void run()
        {
          game.newGame();
        }
      }.start();
    }
  }
}


 

