import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;  //for JOptionPane

public class Game {
  public ArrayList<Player> players; 
  public ArrayList<Card> chance; 
  public ArrayList<Card> commChest; 
  public SimpleMap<String, Player> propertyOwners; // key=property name, value=player 
  public SimpleMap<String, Property> properties; //list of properties
  public static DrawBoard board; //the board
  public ArrayList<Location> propertyLocations; //list of locations of properties by index
  public ArrayList<Location> houseLocations;
  public ArrayList<Location> hotelLocations;
  public SimpleMap<String, Player> rrOwners; //name of RR, player
  public SimpleMap<String, Player> utilityOwners; //util name, player
  public SimpleMap<String, Railroad> railroads;
  public SimpleMap<String, Utility> utilities;
  public ArrayList<String> allSpots; //40 long
  public boolean rolledDouble;
  public int turn;
  public int roll;
  public int die1;
  public int die2;
  
  public Game(int numPlayers) {
    players = new ArrayList<Player>();
    if (numPlayers == 1) { //use the AI
      players.add(new Player(0,1, true)); //the AI
      players.add(new Player(0,2, false));
    }
    else { //don't use the AI
      int i = 1;
      while (numPlayers > 0) {
        players.add(new Player(0, i, false));
        numPlayers--;
        i++;
      }
    }
    
    rolledDouble = false;
    roll = 0;
    turn = (int)(Math.random() * players.size());

    //initialize arrayLists of cards
    chance = new ArrayList<Card>();
    chance.add(new Card(0, "Take a ride on the Reading Railroad. If you pass go collect $200."));
    chance.add(new Card(0, "Bank pays you dividend of $50"));
    chance.add(new Card(0, "Advance to Illinois Avenue"));
    chance.add(new Card(0, "Your building and loan matures. Collect $150"));
    chance.add(new Card(0, "Get out of jail free card"));
    chance.add(new Card(0, "Make general repairs on all your property. Pay $25 for each house. Pay $100 for each hotel"));
    chance.add(new Card(0, "Advance token to the nearest railroad and pay the owner \n " + 
                        "twice the rental to which he is otherwise \n " + 
                        "entitled. If railroad is unowned, you may buy it from the bank."));
    chance.add(new Card(0, "Pay poor tax of $15"));
    chance.add(new Card(0, "Take a walk on the Boardwalk"));
    chance.add(new Card(0, "Advance to St. Charles Place"));
    chance.add(new Card(0, "You have been elected chairman of the board. Pay each player $50"));
    chance.add(new Card(0, "Advance token to nearest utility. " +
                        "\nIf unowned, you may buy it from the bank. " +
                        "\nIf owned, throw the dice and pay owner a total of 10 times the amount thrown"));
    chance.add(new Card(0, "Go back 3 spaces"));
    chance.add(new Card(0, "Advance to Go. Collect $200 dollars"));
    chance.add(new Card(0, "Go directly to jail"));
    
    commChest = new ArrayList<Card>();
    commChest.add(new Card(1, "Income Tax Refund. Collect $20"));
    commChest.add(new Card(1, "You are assessed for street repairs. $40 per house $115 per hotel"));
    commChest.add(new Card(1, "You inherit $100!"));
    commChest.add(new Card(1, "Grand Opera Opening. Collect $50 from every player"));
    commChest.add(new Card(1, "Xmas fund matures. Collect $100"));
    commChest.add(new Card(1, "Advance to Go. Collect $200"));
    commChest.add(new Card(1, "Bank Error in your favor. Collect $200"));
    commChest.add(new Card(1, "Get out of jail free card"));
    commChest.add(new Card(1, "Pay hospital $100"));
    commChest.add(new Card(1, "Receive for Services $25"));
    commChest.add(new Card(1, "Go to Jail"));
    commChest.add(new Card(1, "Pay school tax of $150"));
    commChest.add(new Card(1, "Doctors Fee. Pay $50"));
    commChest.add(new Card(1, "From sale of stock you get $45"));
    commChest.add(new Card(1, "Life insurance matures. Collect $100"));
    commChest.add(new Card(1, "You have won second prize in a beauty contest! Collect $10"));
    
    shuffleCards();
    
    propertyOwners = new SimpleMap<String, Player>(); 
    properties = new SimpleMap<String, Property>();
    
    //initialize all the properties with locations
    properties.put("Mediterranean Avenue", new Property("Mediterranean Avenue", 60, 2, 10, 30, 90, 160, 250, 30, "brown"));
    propertyOwners.put("Mediterranean Avenue", null);
    properties.put("Baltic Avenue", new Property("Baltic Avenue", 60, 4, 20, 60, 180, 320, 450, 30, "brown"));
    propertyOwners.put("Baltic Avenue", null);
    
    properties.put("Oriental Avenue", new Property("Oriental Avenue", 100, 6, 30, 90, 270, 400, 550, 50, "light blue"));
    propertyOwners.put("Oriental Avenue", null);
    properties.put("Vermont Avenue", new Property("Vermont Avenue", 100, 6, 30, 90, 270, 400, 550, 50, "light blue"));
    propertyOwners.put("Vermont Avenue", null);
    properties.put("Connecticut Avenue", new Property("Connecticut Avenue", 120, 8, 40, 100, 300, 450, 600, 60, "light blue"));
    propertyOwners.put("Connecticut Avenue", null);
    
    properties.put("St. Charles Place", new Property("St. Charles Place", 140, 10, 50, 150, 450, 625, 750, 70, "pink"));
    propertyOwners.put("St. Charles Place", null);
    properties.put("States Avenue", new Property("States Avenue", 140, 10, 50, 150, 450, 625, 750, 70, "pink"));
    propertyOwners.put("States Avenue", null);
    properties.put("Virginia Avenue", new Property("Virginia Avenue", 160, 12, 60, 180, 500, 700, 900, 80, "pink"));
    propertyOwners.put("Virginia Avenue", null);
    
    properties.put("St. James Place", new Property("St. James Place", 180, 14, 70, 200, 550, 750, 950, 90, "orange"));
    propertyOwners.put("St. James Place", null);
    properties.put("Tennessee Avenue", new Property("Tennessee Avenue", 180, 14, 70, 200, 550, 750, 950, 90, "orange"));
    propertyOwners.put("Tennessee Avenue", null);
    properties.put("New York Avenue", new Property("New York Avenue", 200, 16, 80, 220, 600, 800, 1000, 100, "orange"));
    propertyOwners.put("New York Avenue", null);
    
    properties.put("Kentucky Avenue", new Property("Kentucky Avenue", 220, 18, 90, 250, 700, 875, 1050, 110, "red"));
    propertyOwners.put("Kentucky Avenue", null);
    properties.put("Indiana Avenue", new Property("Indiana Avenue", 220, 18, 90, 250, 700, 875, 1050, 110, "red"));
    propertyOwners.put("Indiana Avenue", null);
    properties.put("Illinois Avenue", new Property("Illinois Avenue", 240, 20, 100, 300, 750, 925, 1100, 120, "red"));
    propertyOwners.put("Illinois Avenue", null);
    
    properties.put("Atlantic Avenue", new Property("Atlantic Avenue", 260, 22, 110, 330, 800, 975, 1150, 130, "yellow"));
    propertyOwners.put("Atlantic Avenue", null);
    properties.put("Ventnor Avenue", new Property("Ventnor Avenue", 260, 22, 110, 330, 800, 975, 1150, 130, "yellow"));
    propertyOwners.put("Ventnor Avenue", null);
    properties.put("Marvin Gardens", new Property("Marvin Gardens", 280, 24, 120, 360, 850, 1025, 1200, 140, "yellow"));
    propertyOwners.put("Marvin Gardens", null);
    
    properties.put("Pacific Avenue", new Property("Pacific Avenue", 300, 26, 130, 390, 900, 1100, 1275, 150, "green"));
    propertyOwners.put("Pacific Avenue", null);
    properties.put("North Carolina Avenue", new Property("North Carolina Avenue", 300, 26, 130, 390, 900, 1100, 1275, 150, "green"));
    propertyOwners.put("North Carolina Avenue", null);
    properties.put("Pennsylvania Avenue", new Property("Pennsylvania Avenue", 320, 28, 150, 450, 1000, 1200, 1400, 160, "green"));
    propertyOwners.put("Pennsylvania Avenue", null);
    
    properties.put("Park Place", new Property("Park Place", 350, 35, 175, 500, 1100, 1300, 1500, 175, "blue"));
    propertyOwners.put("Park Place", null);
    properties.put("Boardwalk", new Property("Boardwalk", 400, 50, 200, 600, 1400, 1700, 2000, 200, "blue"));
    propertyOwners.put("Boardwalk", null);
    
    rrOwners = new SimpleMap<String, Player>();
    rrOwners.put("Reading Railroad", null);
    rrOwners.put("Pennsylvania Railroad", null);
    rrOwners.put("B&O Railroad", null);
    rrOwners.put("Short Line Railroad", null);
    
    railroads = new SimpleMap<String, Railroad>();
    railroads.put("Reading Railroad", new Railroad("Reading Railroad"));
    railroads.put("Pennsylvania Railroad", new Railroad("Pennsylvania Railroad"));
    railroads.put("B&O Railroad", new Railroad("B&O Railroad"));
    railroads.put("Short Line Railroad", new Railroad("Short Line Railroad"));
    
    utilityOwners = new SimpleMap<String, Player>();
    utilityOwners.put("Electric Company", null);
    utilityOwners.put("Water Works", null);
    utilities = new SimpleMap<String, Utility>();
    utilities.put("Electric Company", new Utility("Electric Company"));
    utilities.put("Water Works", new Utility("Water Works"));
    
    //create ArrayList of all property locations
    propertyLocations = new ArrayList<Location>();
    propertyLocations.add(new Location(720, 720)); //go
    propertyLocations.add(new Location(625, 740)); //med
    propertyLocations.add(new Location(565, 740)); //comm
    propertyLocations.add(new Location(495, 740)); //baltic
    propertyLocations.add(new Location(430, 740));
    propertyLocations.add(new Location(365, 740)); //read
    propertyLocations.add(new Location(300, 740)); //ori
    propertyLocations.add(new Location(235, 740)); //ch
    propertyLocations.add(new Location(165, 740)); //verm
    propertyLocations.add(new Location(105, 740)); //conn
    
    propertyLocations.add(new Location(0, 690));
    
    propertyLocations.add(new Location(0, 635));
    propertyLocations.add(new Location(0, 565));
    propertyLocations.add(new Location(0, 500));
    propertyLocations.add(new Location(0, 435));
    propertyLocations.add(new Location(0, 370));
    propertyLocations.add(new Location(0, 305));
    propertyLocations.add(new Location(0, 240));
    propertyLocations.add(new Location(0, 175));
    propertyLocations.add(new Location(0, 110));
    
    propertyLocations.add(new Location(25, 28));
    
    propertyLocations.add(new Location(100, 0));
    propertyLocations.add(new Location(170, 0));
    propertyLocations.add(new Location(233, 0));
    propertyLocations.add(new Location(300, 0));
    propertyLocations.add(new Location(365, 0));
    propertyLocations.add(new Location(430, 0));
    propertyLocations.add(new Location(495, 0));
    propertyLocations.add(new Location(560, 0));
    propertyLocations.add(new Location(625, 0));
    
    propertyLocations.add(new Location(720, 28));
    
    propertyLocations.add(new Location(740, 110));
    propertyLocations.add(new Location(740, 175));
    propertyLocations.add(new Location(740, 240));
    propertyLocations.add(new Location(740, 305));
    propertyLocations.add(new Location(740, 370));
    propertyLocations.add(new Location(740, 435));
    propertyLocations.add(new Location(740, 500));
    propertyLocations.add(new Location(740, 565));
    propertyLocations.add(new Location(740, 633));
    
    propertyLocations.add(new Location(30, 700));
    
    //create ArrayList of all house locations
    houseLocations = new ArrayList<Location>();
    houseLocations.add(null); //go
    houseLocations.add(new Location(640, 690)); //med
    houseLocations.add(null);
    houseLocations.add(new Location(490, 690)); //baltic
    houseLocations.add(null);
    houseLocations.add(null);
    houseLocations.add(new Location(295, 690)); //oriental
    houseLocations.add(null);
    houseLocations.add(new Location(165, 690)); //verm
    houseLocations.add(new Location(100, 690)); //conn
    houseLocations.add(null);
    
    houseLocations.add(new Location(70, 620)); //st ch
    houseLocations.add(null);
    houseLocations.add(new Location(70, 490)); //state
    houseLocations.add(new Location(70, 425)); //virg
    houseLocations.add(null);
    houseLocations.add(new Location(70, 295)); //st j
    houseLocations.add(null);
    houseLocations.add(new Location(70, 165)); //tenn
    houseLocations.add(new Location(70, 100)); //NY
    houseLocations.add(null);
    
    houseLocations.add(new Location(100, 75)); //ken
    houseLocations.add(null);
    houseLocations.add(new Location(230, 75)); //ind
    houseLocations.add(new Location(295, 75)); //ill
    houseLocations.add(null);
    houseLocations.add(new Location(425, 75)); //at
    houseLocations.add(new Location(490, 75)); //ven
    houseLocations.add(null);
    houseLocations.add(new Location(640, 75)); //marv
    houseLocations.add(null);
    
    houseLocations.add(new Location(680, 100)); //pac
    houseLocations.add(new Location(680, 165)); //nc
    houseLocations.add(null);
    houseLocations.add(new Location(680, 295)); //penn
    houseLocations.add(null);
    houseLocations.add(null);
    houseLocations.add(new Location(680, 490)); //park
    houseLocations.add(null);
    houseLocations.add(new Location(680, 620)); //board
    
    //create ArrayList of all hotel locations
    hotelLocations = new ArrayList<Location>();
    hotelLocations.add(null); //go
    hotelLocations.add(new Location(650, 680)); //med 640 690
    hotelLocations.add(null);
    hotelLocations.add(new Location(520, 680)); //baltic
    hotelLocations.add(null);
    hotelLocations.add(null);
    hotelLocations.add(new Location(325, 680)); //oriental
    hotelLocations.add(null);
    hotelLocations.add(new Location(195, 680)); //verm
    hotelLocations.add(new Location(130, 680)); //conn
    hotelLocations.add(null);
    
    hotelLocations.add(new Location(70, 650)); //st ch
    hotelLocations.add(null);
    hotelLocations.add(new Location(70, 520)); //state
    hotelLocations.add(new Location(70, 455)); //virg
    hotelLocations.add(null);
    hotelLocations.add(new Location(70, 325)); //st j
    hotelLocations.add(null);
    hotelLocations.add(new Location(70, 195)); //tenn
    hotelLocations.add(new Location(70, 130)); //NY
    hotelLocations.add(null);
    
    hotelLocations.add(new Location(130, 65)); //ken
    hotelLocations.add(null);
    hotelLocations.add(new Location(195, 65)); //ind
    hotelLocations.add(new Location(325, 65)); //ill
    hotelLocations.add(null);
    hotelLocations.add(new Location(455, 65)); //at
    hotelLocations.add(new Location(520, 65)); //ven
    hotelLocations.add(null);
    hotelLocations.add(new Location(670, 65)); //marv
    hotelLocations.add(null);
    
    hotelLocations.add(new Location(680, 130)); //pac
    hotelLocations.add(new Location(680, 195)); //nc
    hotelLocations.add(null);
    hotelLocations.add(new Location(680, 325)); //penn
    hotelLocations.add(null);
    hotelLocations.add(null);
    hotelLocations.add(new Location(680, 520)); //park
    hotelLocations.add(null);
    hotelLocations.add(new Location(680, 650)); //board
    
    allSpots = new ArrayList<String>();
    allSpots.add("Go");
    allSpots.add("Mediterranean Avenue");
    allSpots.add("Community Chest 1");
    allSpots.add("Baltic Avenue");
    allSpots.add("Income Tax");
    allSpots.add("Reading Railroad");
    allSpots.add("Oriental Avenue");
    allSpots.add("Chance 1");
    allSpots.add("Vermont Avenue");
    allSpots.add("Connecticut Avenue");
    allSpots.add("Just Visiting");
    allSpots.add("St. Charles Place");
    allSpots.add("Electric Company");
    allSpots.add("States Avenue");
    allSpots.add("Virginia Avenue");
    allSpots.add("Pennsylvania Railroad");
    allSpots.add("St. James Place");
    allSpots.add("Community Chest 2");
    allSpots.add("Tennessee Avenue");
    allSpots.add("New York Avenue");
    allSpots.add("Free Parking");
    allSpots.add("Kentucky Avenue");
    allSpots.add("Chance 2");
    allSpots.add("Indiana Avenue");
    allSpots.add("Illinois Avenue");
    allSpots.add("B&O Railroad");
    allSpots.add("Atlantic Avenue");
    allSpots.add("Ventnor Avenue");
    allSpots.add("Water Works");
    allSpots.add("Marvin Gardens");
    allSpots.add("Go to Jail");
    allSpots.add("Pacific Avenue");
    allSpots.add("North Carolina Avenue");
    allSpots.add("Community Chest 3");
    allSpots.add("Pennsylvania Avenue");
    allSpots.add("Short Line Railroad");
    allSpots.add("Chance 3");
    allSpots.add("Park Place");
    allSpots.add("Luxury Tax");
    allSpots.add("Boardwalk");
    allSpots.add("In Jail");
    
    board = new DrawBoard(this);
    board.showMessageDialog("Press OK to start the game.");
    if (numPlayers == 1)
      board.showMessageDialog("Player 1 is the computer. Player 2 is you!");
  }
  
  public void newGame() {
    String[] players = new String[4];
    players[0] = "1";
    players[1] = "2";
    players[2] = "3";
    players[3] = "4";
    Object selected = JOptionPane.showInputDialog(board, "Choose number of players.", "How many players?", JOptionPane.PLAIN_MESSAGE, null, players, players[0]);
    int numPlayers = Integer.parseInt((String)selected);
    Game g = new Game(numPlayers);
    g.play();
  }
  
  public static void main(String[] args) {
    Game g = new Game(2);
    g.play();
  }
  
  public Player getCurrentPlayer() {
    return players.get(turn);
  }
  
  public int getTurn() {
    return turn;
  }
  
  public void nextTurn() {
    if (rolledDouble) { }
    else if (turn < players.size()-1)
      turn++;
    else
      turn = 0;
    rolledDouble = false;
  }
  
  public Player getPlayer(int num) {
    return players.get(num-1);
  }
  
  public int getNumPlayers() {
    return players.size();
  }
  
  public ArrayList<Location> getPropertyLocations() {
    return propertyLocations;
  }
  
  public ArrayList<Location> getHouseLocations() {
    return houseLocations;
  }
  
  public ArrayList<Location> getHotelLocations() {
    return hotelLocations;
  }
  
  public SimpleMap<String, Property> getProperties() {
    return properties;
  }
  
  public ArrayList<String> getAllSpots() {
    return allSpots;
  }
  
  public void play() {
    while (true)
      turn(players.get(turn));
  }
  
  public SimpleMap<String, Player> getPropertyOwners() {
    return propertyOwners;
  }
  
  public SimpleMap<String, Player> getRROwners() {
    return rrOwners;
  }
  
  public SimpleMap<String, Player> getUtilityOwners() {
    return utilityOwners;
  }
  
  public int[] getDice() {
    int[] dice = new int[2];
    dice[0] = die1;
    dice[1] = die2;
    return dice;
  }
  
  public void turn(Player p) { 
    if (p.inJail() && !p.isAI()) {
      board.showMessageDialog("This is your turn in jail number " + p.turnsInJail() + "." +
                              "\nIf you roll a double, you can get out of jail and roll again." +
                              "\nIf this is your third time in jail and you do not roll a double, " +
                              "\nyou will have to pay $50.");
    }
    
    roll = rollDice();
    
    //jail
    if (p.inJail()) {
      if (board.getClickedLocation()!= null) {
        if (rolledDouble) {
          p.getOutofJail(); 
          roll = rollDice(); //roll again
          moveToLocation(p, 10);
        }
        else if (p.turnsInJail() == 3) {
          p.addMoney(-50);
          p.getOutofJail();
          roll = rollDice();
          moveToLocation(p,10);
        }
        else
          p.addJailTurn();
      }
    }
    //not in jail
    if (!p.inJail()) {
      if (!p.isAI() && board.getClickedLocation() != null) {
        move(p, roll); //moves player to this new spot
        landedOnSpot(allSpots.get(p.getLocation()), p, roll); //does whatever at the spot
      }
      else {
        move(p, roll); //moves player to this new spot
        landedOnSpot(allSpots.get(p.getLocation()), p, roll); //does whatever at the spot
      }
    }
    
    //see if everyone has enough money still
    for (Player player : players) {
      if (player.getMoney() <= 0) {
        if (!player.isAI())
          board.showMessageDialog("Player " + (turn+1) + " doesn't have any more money :(. \nIf you can, mortgage some houses or properties to get some money.");
        
        //AI
        if (player.isAI()) {
          mortgageHouse(player);
          if (player.getMoney() <= 0)
            mortgageHotel(player);
          if (player.getMoney() <= 0)
            mortgageProp(player, getPropToMort(player));
        }
      }
      //if player has more than 2000, they win 
      if (player.getMoney() >= 2000)
          board.showMessageDialog("Player " + (turn+1) + " WINS!!! Wow. \nClick New Game for the next players :)");
    }
    
    //buy houses
    if (p.isAI()) {
      buyHouses(p);
      buyHotel(p);
      if (p.getMoney() < 100)
        mortgageProp(p, getPropToMort(p));
    }
    
    if (rolledDouble) { } //same player gets another turn
    else if (turn < players.size()-1)
      turn++;
    else
      turn = 0;
    rolledDouble = false;
    
    board.updateLabel(); 
  }

  public void buyHouses(Player p) {
    //getting a house
    board.setBuyHouses(true);
    ArrayList<String> propsCanAddHouses = whereCanAddHouses(p);
    
    if (!propsCanAddHouses.isEmpty()) { //can put a house somewhere OR HOTEL 
      if (!p.isAI()) {
        String message = "You can build a house on the following properties: ";
        for (String houseprop : propsCanAddHouses)
          message += "\n" + houseprop;
        
        message += "\n\nClick on the location where you want a house."; 
        board.showMessageDialog(message);
        
        Location loc = board.getClickedLocation();
        int propIndex = closestProperty(loc); //get the index of the property you're on
        
        String property = allSpots.get(propIndex); //gets the String of the property you're on
        Property prop = properties.get(property); //Property that house will go on 
        
        while (!propsCanAddHouses.contains(property)) {
          board.showMessageDialog("This is not a valid location to put a house or hotel.\nPlease choose another location or click cancel if you don't want a house.");
          loc = board.getClickedLocation();
          propIndex = closestProperty(loc); //get the index of the property you're on
          
          property = allSpots.get(propIndex); //gets the String of the property you're on
          prop = properties.get(property);
        }
        
        if (propsCanAddHouses.contains(property) && prop != null) {
          Location houseLocation = houseLocations.get(propIndex);
          Object[] choices = {"0", "1", "2", "3", "4"};
          Object selected = JOptionPane.showInputDialog(board, "You can build a house for $" + prop.housePrice() + ". How many do you want?",
                                                        "Number of Houses", JOptionPane.PLAIN_MESSAGE, null, choices, choices[0]);
          
          if (selected != null) {
            int numToBuild = Integer.parseInt((String)selected);
            int numBuilt = numToBuild;
            while (numToBuild > 0 && p.getMoney() > prop.housePrice()) {
              prop.addHouse();
              p.addMoney(-prop.housePrice());
              numToBuild--;
              if (p.getMoney() < prop.housePrice()) {
                board.showMessageDialog("You don't have enough money to pay for another house...Sorry.");
                numToBuild = 0;
              }
            }
          }
        }
      }
      else {
        int maxRent = 0;
        Property propToBuild = properties.get(propsCanAddHouses.get(0));
        for (String prop : propsCanAddHouses) {
          Property pro = properties.get(prop);
          if (pro.getNumHouses() == 0 && pro.getRent() > maxRent) { //doesnt have houses, gets max rent
            maxRent = pro.getRent();
            propToBuild = pro;
          }
        }
        
        if (p.getMoney() > 3*propToBuild.housePrice()) {
          propToBuild.addHouse();
          p.addMoney(-propToBuild.housePrice());
        }
      }
    }
    board.setBuyHouses(false);
  }
  
  
  public void buyHotel(Player p) {
    ArrayList<String> propsCanAddHotel = whereCanAddHotel(p);
    if (!propsCanAddHotel.isEmpty()) { //can put a house somewhere OR HOTEL
      if (!p.isAI()) {
        String message = "You can build a hotel on the following properties: ";
        for (String hotelprop : propsCanAddHotel)
          message += "\n" + hotelprop;
        
        message += "\n\nClick on the a location where you want a hotel."; 
        board.showMessageDialog(message);
        Location loc = board.getClickedLocation();
        
        int propIndex = closestProperty(loc); //get the index of the property you're on

        String property = allSpots.get(propIndex); //gets the String of the property you're on
        Property prop = properties.get(property); //Property that house will go on
        if (propsCanAddHotel.contains(property) && prop != null && p.getMoney() > prop.hotelPrice()) {
          Location hotelLocation = hotelLocations.get(propIndex);
          prop.addHotel();
          p.addMoney(-prop.hotelPrice());
          prop.removeHouses();
        }
        else
          board.showMessageDialog("You don't have enough money to pay for a hotel...Sorry.");
      }
      else {
        int maxRent = 0;
        Property propToBuild = properties.get(propsCanAddHotel.get(0));
        for (String prop : propsCanAddHotel) {
          Property pro = properties.get(prop);
          if (pro.getRent() > maxRent) { //doesnt have houses, gets max rent
            maxRent = pro.getRent();
            propToBuild = pro;
          }
        }
        
        if (p.getMoney() > 3*propToBuild.housePrice()) {
          propToBuild.addHotel();
          propToBuild.removeHouses();
          p.addMoney(-propToBuild.housePrice());
        }
      }
    }
  }
  
  
  public int rollDice() {
    die1 = (int)(Math.random() * 6) + 1;
    die2 = (int)(Math.random() * 6) + 1;
    if (die1 == die2)
      rolledDouble = true;
    board.update();
    return die1 + die2;
  }
  
  
  public void shuffleCards() {
    ArrayList<Card> shuffled = new ArrayList<Card>();
    while (chance.size() > 0)
      shuffled.add(chance.remove((int)(Math.random() * chance.size())));
    chance = shuffled;
    
    ArrayList<Card> shuffledCC = new ArrayList<Card>();
    while (commChest.size() > 0)
      shuffledCC.add(commChest.remove((int)(Math.random() * commChest.size())));
    commChest = shuffledCC;
  }
  
  
  public int closestProperty(Location loc) {
    double minDist = 10000000;
    int minIndex = 0;
    for (int i = 0; i < propertyLocations.size(); i++) {
      double dist = Math.sqrt((loc.getX()-propertyLocations.get(i).getX())*(loc.getX()-propertyLocations.get(i).getX())+(loc.getY()-propertyLocations.get(i).getY())*(loc.getX()-propertyLocations.get(i).getX()));
      if (dist < minDist) {
        minDist = dist;
        minIndex = i;
      }
    }
    //testing
    //System.out.println(minIndex);
    //System.out.println(minDist);
    return minIndex;
  } 
  
  
  public ArrayList<Integer> indexesCanAddHouses(Player player) {
    ArrayList<Integer> propsCanAdd = new ArrayList<Integer>();
    
    int i = 0;
    for (String x : allSpots) {
      if (properties.containsKey(x) && hasMonopoly(player, x))
        propsCanAdd.add(i);
      i++;
    }
    return propsCanAdd;
  }
  
  
  public ArrayList<String> whereCanAddHouses(Player player) {
    ArrayList<String> propsCanAdd = new ArrayList<String>();
    
    SimpleSet<String> props = propertyOwners.keySet();
    for (String x : props) {
      if (hasMonopoly(player, x) && properties.get(x).getNumHouses() < 4  && !properties.get(x).hasHotel()) {
        properties.get(x).makeMonopolized();
        propsCanAdd.add(x);
      }
    }
    return propsCanAdd;
  }
  
  
  public ArrayList<String> whereCanAddHotel(Player player) {
    ArrayList<String> propsCanAddHotel = new ArrayList<String>();
    
    SimpleSet<String> props = propertyOwners.keySet();
    for (String x : props) {
      if (properties.get(x).getNumHouses() == 4)
        propsCanAddHotel.add(x);
    }
    return propsCanAddHotel;
  }
  
  
  //updates the map of property with owner
  //also updates player's money
  public void buyProperty(Player p, String prop) {
    if (propertyOwners.containsKey(prop)) { //if property
      propertyOwners.put(prop, p);
      p.addMoney(-properties.get(prop).getPrice());
      //if monopolized, make property monopolized
    }
    else if (rrOwners.containsKey(prop)) { //railroad
      rrOwners.put(prop, p);
      p.addMoney(-200);
    }
    else { //utility
      utilityOwners.put(prop, p);
      p.addMoney(-150);
    }
    board.update();
  }
  
  
  public void buyProperty(Player p, String prop, int price) {
    if (propertyOwners.containsKey(prop)) //if property
      propertyOwners.put(prop, p);
    else if (rrOwners.containsKey(prop)) //railroad
      rrOwners.put(prop, p);
    else //utility
      utilityOwners.put(prop, p);
    p.addMoney(-price);
    board.update();
  }
  
  
  public boolean hasMonopoly(Player p, String prop) {
    for (String property : properties.keySet()) {
      if (properties.get(property).getColor().equals(properties.get(prop).getColor()) && propertyOwners.get(property) != null && !propertyOwners.get(property).equals(p))
        return false;
      else if (properties.get(property).getColor().equals(properties.get(prop).getColor()) && propertyOwners.get(property) == null)
        return false;
      else {}
    }
    properties.get(prop).makeMonopolized();
    return true;
  }
  
  
  //will always be null
  public ArrayList<String> getAllProperties(Player p) {
    int player = p.getPlayer();
    ArrayList<String> allProperties = new ArrayList<String>();
    SimpleSet<String> props = propertyOwners.keySet();
    for (String prop : props) {
      if (propertyOwners.get(prop) != null && propertyOwners.get(prop).getPlayer() == player)
        allProperties.add(prop);
    }
    SimpleSet<String> rrs = rrOwners.keySet();
    for (String rr : rrs) {
      if (rrOwners.get(rr) != null && rrOwners.get(rr).getPlayer() == player)
        allProperties.add(rr);
    }
    SimpleSet<String> utils = utilityOwners.keySet();
    for (String util : utils) {
      if (utilityOwners.get(util) != null && utilityOwners.get(util).getPlayer() == player)
        allProperties.add(util);
    }
    return allProperties;
  }
  
  
  
  
  
  public String getPropToMort(Player p) {
    ArrayList<String> allProps = getAllProperties(p); //list of all props
    String propToMort = "";
    
    if (allProps.contains("Water Works"))
      propToMort = "Water Works";
    else if (allProps.contains("Electric Company"))
      propToMort = "Electric Company";
    else if (allProps.contains("B&O Railroad"))
      propToMort = "B&O Railroad";
    else if (allProps.contains("Pennsylvania Railroad"))
      propToMort = "Pennsylvania Railroad";
    else if (allProps.contains("Short Line Railroad"))
      propToMort = "Short Line Railroad";
    else if (allProps.contains("Reading Railroad"))
      propToMort = "Reading Railroad";
    else { //it's a property
      int maxMort = 0;
      int i = 0;
      while (i < allProps.size()) {
        String prop = allProps.get(i);
        int mortPrice = properties.get(prop).mortPrice();
        if (!hasMonopoly(p, prop) && mortPrice > maxMort) {
          propToMort = prop;
          maxMort = mortPrice;
        }
        i++;
      }
    }
    return propToMort;
  }
    
  public void mortgageProp(Player p) { //drop down menu
    ArrayList<String> allProps = getAllProperties(p); //list of all properties, including rr's and utilities
    Object[] choices = new Object[allProps.size()];
    for (int i = 0; i < allProps.size(); i++)
      choices[i] = allProps.get(i);
    Object selected = JOptionPane.showInputDialog(board, "Choose the property you would like to mortgage.",
                                                  "Mortage Property", JOptionPane.PLAIN_MESSAGE, null, choices, choices[0]);
    
    String chosen = ((String)selected);
    mortgageProp(p, chosen);
  }
  
  //pre-condition: player owns the property
  public void mortgageProp(Player p, String chosen) {
    if (properties.containsKey(chosen)) { //property
      Property prop = properties.get(chosen);
      propertyOwners.put(chosen, null); // player doesn't own it anymore
      p.addMoney(prop.mortPrice());
      //ALSO DEAL WITH HOUSES - if you mortgage a property with houses, it automatically mortgages all the houses of that color
    }
    else if (railroads.containsKey(chosen)) { //railroad
      Railroad rr = railroads.get(chosen);
      rrOwners.put(chosen, null); // player doesn't own it anymore
      p.addMoney(rr.getMortgage());
    }
    else { //utility
      Utility util = utilities.get(chosen);
      utilityOwners.put(chosen, null); // player doesn't own it anymore
      p.addMoney(75);
    }
  }
  
  public ArrayList<String> propsWithHotel(Player p) {
    ArrayList<String> propsWithHotel = new ArrayList<String>();
    for (String prop : propertyOwners.keySet()) {
      if (propertyOwners.get(prop) != null && propertyOwners.get(prop).getPlayer() == p.getPlayer() && properties.get(prop).hasHotel())
        propsWithHotel.add(prop);
    }
    return propsWithHotel;
  }


  public void mortgageHotel(Player p) {
    ArrayList<String> propsWithHotel = propsWithHotel(p);
 
    Object[] hotelProps = new Object[propsWithHotel.size()];
    
    int j = 0;
    while (j < propsWithHotel.size()) {
      hotelProps[j] = propsWithHotel.get(j);
      j++;
    }
    
    if (p.isAI()) {
      int minRent = 100000;
      String propToRemove = "";
      for (int i = 0; i < hotelProps.length; i++) {
        if (properties.get((String)hotelProps[i]).plainRent() < minRent) {
          minRent = properties.get((String)hotelProps[i]).plainRent();
          propToRemove = ((String)hotelProps[i]);
        }
      }
      
      if (propToRemove != null && propToRemove != "") {
        Property hotelRemover = properties.get(propToRemove);
        int moneyBack = 5 * hotelRemover.housePrice() /2;
        p.addMoney(moneyBack);
        hotelRemover.removeHotel();
      }
    }
    else {
      Object selected = JOptionPane.showInputDialog(board, "Choose which property's hotel you want to mortgage.", "Mortage Property", JOptionPane.PLAIN_MESSAGE, null, hotelProps, hotelProps[0]);
      String chosen = ((String)selected);
      Property property = properties.get(chosen);
      int moneyBack = 5 * property.housePrice() /2;
      p.addMoney(moneyBack);
      property.removeHotel();
    }  
  }
  
  
  public void mortgageHouse(Player p) {
    ArrayList<String> propsCanAddHouses = whereCanAddHouses(p);
    ArrayList<String> propsCanAddHotel = whereCanAddHotel(p);
    ArrayList<String> propsThatHaveHouses = new ArrayList<String>();
    for (String prop : propsCanAddHouses) {
      if (properties.get(prop).getNumHouses() > 0)
        propsThatHaveHouses.add(prop);
    }
    for (String prop : propsCanAddHotel) {
      if (!propsThatHaveHouses.contains(prop))
        propsThatHaveHouses.add(prop);
    }
    Object[] propsWithHouses = new Object[propsThatHaveHouses.size()];
    
    int j = 0;
    while (j < propsThatHaveHouses.size()) {
      propsWithHouses[j] = propsThatHaveHouses.get(j);
      j++;
    }
    
    if (p.isAI()) {
      int minRent = 100000;
      String propToRemove = "";
      for (int i = 0; i < propsWithHouses.length; i++) {
        if (properties.get((String)propsWithHouses[i]).plainRent() < minRent) {
          minRent = properties.get((String)propsWithHouses[i]).plainRent();
          propToRemove = ((String)propsWithHouses[i]);
        }
      }
      
      Property houseRemover = properties.get(propToRemove);
      int numHouses = houseRemover.getNumHouses();
      int moneyBack = numHouses * houseRemover.housePrice() /2;
      p.addMoney(moneyBack);
      houseRemover.removeHouses();
    }
    else {
      Object selected = JOptionPane.showInputDialog(board, "Choose which property's house/s you want to mortgage.", "Mortage Property", JOptionPane.PLAIN_MESSAGE, null, propsWithHouses, propsWithHouses[0]);
      String chosen = ((String)selected);
      Property property = properties.get(chosen);
      
      int num = property.getNumHouses();
      Object[] numHouses = new Object[num];
      int i = 0;
      while (num > 0) {
        numHouses[i] = i+1;
        i++;
        num--;
      }
      Object s = JOptionPane.showInputDialog(board, "How many houses would you like to mortgage?", "Mortage Houses", JOptionPane.PLAIN_MESSAGE, null, numHouses, numHouses[0]);
      int numToMort = (Integer)s;
      
      property.setNumHouses(property.getNumHouses() - numToMort); //gets new number of houses
      int money = property.housePrice() / 2 * numToMort;
      p.addMoney(money);
    } 
  }
  
  
  public void move(Player p, int numSteps) { //numSteps is taken from the random number rolled
    int loc = p.getLocation()+numSteps;
    if (loc > 39) {
      loc = loc - 40;
      p.addMoney(200); //passed Go
    }
    else if (loc == 30) { //go to jail
      loc = 40;
      p.addJailTurn();
    }
    else if (loc == 40)
      loc = 10 + numSteps;
    else { }
    p.changeLocation(loc);
    board.update();
  }
  
  public void moveToLocation(Player p, int loc) {
    p.changeLocation(loc);
    board.update();
  }
  
  
  // do whatever it says on the card
  //including moving player, adding or paying money, etc
  public void doCard(Card c, Player p) {
    String m = c.getMessage();
    if (!p.isAI())
      board.showMessageDialog(c.getMessage());
    if (m.equals("Take a ride on the Reading Railroad. If you pass go collect $200.")) {
      if (p.getLocation() > 5)
        p.addMoney(200);
      moveToLocation(p, 5);
      landedOnSpot("Reading Railroad", p, roll);
    }
    else if (m.equals("Bank pays you dividend of $50"))
      p.addMoney(50);
    else if (m.equals("Advance to Illinois Avenue")) {
      moveToLocation(p, 24);    
      landedOnSpot("Illinois Avenue", p, roll);
    }
    else if (m.equals("Your building and loan matures. Collect $150"))
      p.addMoney(150);
    else if (m.equals("Get out of jail free card"))
      p.addJailCard();
    else if (m.equals("Make general repairs on all your property. Pay $25 for each house. Pay $100 for each hotel")) {
      int numHouses = 0;
      int numHotels = 0;
      for (String s : properties.keySet()) {
        if (propertyOwners.get(s) != null && propertyOwners.get(s).equals(p)) {
          numHouses += properties.get(s).getNumHouses();
          if (properties.get(s).hasHotel())
            numHotels++;
        }
      }
    }
    else if (m.equals("Advance token to the nearest railroad and pay the owner \n " + 
                      "twice the rental to which he is otherwise \n " + 
                      "entitled. If railroad is unowned, you may buy it from the bank.")) {
      if (p.getLocation() < 5)
        moveToLocation(p, 5);
      else if (p.getLocation() < 15)
        moveToLocation(p, 15);
      else if (p.getLocation() < 25)
        moveToLocation(p, 25);
      else
        moveToLocation(p, 35);
      
      landedOnSpot(allSpots.get(p.getLocation()), p, roll);
    }
    else if (m.equals("Pay poor tax of $15"))
      p.addMoney(-15);
    else if (m.equals("Take a walk on the Boardwalk")) {
      moveToLocation(p, 39);
      landedOnSpot("Boardwalk", p, roll);
    }
    else if (m.equals("Advance to St. Charles Place")) {
      moveToLocation(p, 11);
      landedOnSpot("St. Charles Place", p, roll);
    }
    else if (m.equals("You have been elected chairman of the board. Pay each player $50")) {
      for (Player player : players) {
        p.addMoney(-50);
        player.addMoney(50);
      }
    }
    
    else if (m.equals("Advance token to nearest utility. " +
                      "\nIf unowned, you may buy it from the bank. " +
                      "\nIf owned, throw the dice and pay owner a total of 10 times the amount thrown")) {
      if (p.getLocation() < 12)
        moveToLocation(p, 12); //electric company
      else
        moveToLocation(p, 28); //water works
      
      if (utilityOwners.get(allSpots.get(p.getLocation())) != null) { //if utility is owned
        int amtToPay = rollDice() * 10;
        utilityOwners.get(allSpots.get(p.getLocation())).addMoney(amtToPay);
        p.addMoney(-amtToPay);
      }
      else
        landedOnSpot(allSpots.get(p.getLocation()), p, roll);
    }
    else if (m.equals("Go back 3 spaces")) {
      move(p, -3);
      System.out.println(p.getLocation());
      landedOnSpot(allSpots.get(p.getLocation()), p, roll);
    }
    else if (m.equals("Advance to Go. Collect $200 dollars")) {
      moveToLocation(p, 0);
      p.addMoney(200);
    }
    else if (m.equals("Go directly to jail")) {
      moveToLocation(p, 40);
      p.addJailTurn();
    }
    
    //community chest
    else if (m.equals("Income Tax Refund. Collect $20"))
      p.addMoney(20);
    else if (m.equals("You are assessed for street repairs. $40 per house $115 per hotel")) {
      int numHouses = 0;
      int numHotels = 0;
      for (String s : properties.keySet()) {
        if (propertyOwners.get(s) != null && propertyOwners.get(s).equals(p)) {
          numHouses += properties.get(s).getNumHouses();
          if (properties.get(s).hasHotel())
            numHotels++;
        }
      }
      while (numHouses > 0) {
        p.addMoney(-40);
        numHouses--;
      }
      while (numHotels > 0) {
        p.addMoney(-115);
        numHotels--;
      }
    }
    else if (m.equals("You inherit $100!"))
      p.addMoney(100);
    else if (m.equals("Grand Opera Opening. Collect $50 from every player")) {
      for (Player player : players) {
        player.addMoney(-50);
        p.addMoney(50);
      }
    }
    else if (m.equals("Xmas fund matures. Collect $100"))
      p.addMoney(100);
    else if (m.equals("Advance to Go. Collect $200")) {
      moveToLocation(p, 0);
      p.addMoney(200);
    }
    else if (m.equals("Bank Error in your favor. Collect $200"))
      p.addMoney(200);
    else if (m.equals("Get out of jail free card"))
      p.addJailCard();
    else if (m.equals("Pay hospital $100"))
      p.addMoney(-100);
    else if (m.equals("Receive for Services $25"))
      p.addMoney(25);
    else if (m.equals("Go to Jail")) {
      moveToLocation(p, 40);
      p.addJailTurn();
    }
    else if (m.equals("Pay school tax of $150"))
      p.addMoney(-150);
    else if (m.equals("Doctors Fee. Pay $50"))
      p.addMoney(-50);
    else if (m.equals("From sale of stock you get $45"))
      p.addMoney(45);
    else if (m.equals("Life insurance matures. Collect $100"))
      p.addMoney(100);
    else if (m.equals("You have won second prize in a beauty contest! Collect $10"))
      p.addMoney(10);
    else {}
  }
  
  //landed on spot, takes in the name of the place
  public void landedOnSpot(String place, Player p, int roll) //player who landed on spot
  {
    int rent = 0;
    if (utilityOwners.containsKey(place) && utilityOwners.get(place) == null
          || rrOwners.containsKey(place) && rrOwners.get(place) == null
          || propertyOwners.containsKey(place) && propertyOwners.get(place) == null) //not owned yet
    {
      int selected = 0;
      //for AI, 0 = BUY
      if (utilityOwners.containsKey(place)) {
        if (!p.isAI())
          selected = JOptionPane.showConfirmDialog(board, "Do you want to buy " + place + " for $150?", "Buy property?", JOptionPane.YES_NO_OPTION);
        else
          selected = 1;
      }
      else if (rrOwners.containsKey(place)) {
        if (!p.isAI())
          selected = JOptionPane.showConfirmDialog(board, "Do you want to buy " + place + " for $200?", "Buy property?", JOptionPane.YES_NO_OPTION);
      }
      else {
        if (!p.isAI())
          selected = JOptionPane.showConfirmDialog(board, "Do you want to buy " + place + " for $" + properties.get(place).getPrice() + "?", "Buy property?", JOptionPane.YES_NO_OPTION);
        else {
          if (!shouldBuy(p, place))
            selected = 1;
        }
      }
      
      if (selected == JOptionPane.YES_OPTION || selected == 0)
        buyProperty(p,place);
      else if (selected == JOptionPane.NO_OPTION || selected == 1) 
        auction(place);
      else {  }
    }
    else if (utilities.containsKey(place) && utilityOwners.get(place) != null && !utilityOwners.get(place).equals(p)) { //if utility
      if (utilityOwners.get("Electric Company") != null && utilityOwners.get("Water Works") != null && utilityOwners.get("Electric Company").equals(utilityOwners.get("Water Works"))) //owned by both
        rent = roll * 10;
      else
        rent = roll * 4;
      utilityOwners.get(place).addMoney(rent);
    }
    else if (railroads.containsKey(place) && rrOwners.get(place) != null && !rrOwners.get(place).equals(p)) { //if railroad
      int numRRs = 0;
      Player owner = rrOwners.get(place);
      SimpleSet<String> rrNames = railroads.keySet();
      for (String r : rrNames) {
        if (rrOwners.get(r) != null && rrOwners.get(r).equals(owner))
          numRRs++;
      }
      if (numRRs == 1)
        rent = 25;
      else if (numRRs == 2)
        rent = 50;
      else if (numRRs == 3)
        rent = 100;
      else
        rent = 200;
      
      rrOwners.get(place).addMoney(rent);
    }
    else if (properties.containsKey(place) && propertyOwners.get(place) != null && propertyOwners.get(place).getPlayer() != p.getPlayer()) { //property
      //System.out.println("Property rent");
      rent = properties.get(place).getRent();
      //System.out.println("rent " + rent);
      //System.out.println("house number" + properties.get(place).getNumHouses());
      if (properties.get(place).isMonopolized() && properties.get(place).getNumHouses() == 0)
        rent = rent*2;
      //System.out.println("before money" + propertyOwners.get(place).getMoney());
      propertyOwners.get(place).addMoney(rent);
      //System.out.println("after " + propertyOwners.get(place).getMoney());
    }
    else if (place.contains("Community Chest")) {
      Card c = commChest.remove(0);
      doCard(c, p);
      commChest.add(c);
    }
    else if (place.contains("Chance")) {
      Card c = chance.remove(0);
      doCard(c,p);
      chance.add(c);
    }
    else if (place.equals("Income Tax"))
      p.addMoney(-200);
    else if (place.equals("Luxury Tax"))
      p.addMoney(-100);
    else if (place.equals("Go to Jail")) {
      moveToLocation(p, 40);
      p.addJailTurn();
    }
    else {}
    
    p.addMoney(-rent); 
  }
  
  public void auction(String place) {
    int maxBid = 0;
    int bid = 0;
    Player player = players.get(0); 
    Player maxPlayer = players.get(0); 
    int i = 0;
    boolean keepGoing = true;
    
    int timesThrough = 0;
    while ((timesThrough < players.size()) || (timesThrough >= players.size() && keepGoing)) //keep going through until all players have bidded
    {
      player = players.get(i);
      String answer = "";
      if (!player.isAI())
        answer = JOptionPane.showInputDialog(board, "Starting price for " + place + " is $" + maxBid + ". Player " + (i+1) + ": Place your bid");
      else { 
        if (properties.containsKey(place)) {
          String color = properties.get(place).getColor();
          int numOfColor = 0;
          for (String x : properties.keySet()) {
            if (!x.equals(place) && properties.get(x).getColor().equals(color))
              numOfColor++;
          }
          if (numOfColor == 2 && maxBid < 250 && player.getMoney()>maxBid) {
            if (timesThrough == 1 || timesThrough == 0)
              answer = "" + 100;
            else
              answer = "" + (maxBid + maxBid/8);
          }
        }
        if (player.getMoney() > 200 && shouldBuy(player, place) && maxBid < 200) //have enough money, should buy the prop
          answer = "" + (maxBid + 10);
        else
          answer = "";
      }
      
      if (answer == null || answer.equals("")) {  
        if (timesThrough >= players.size()-1) 
          keepGoing = false;
        else {
          if (i == players.size()-1)
            i = 0;
          else
            i++;
        }
      }
      else {
        bid = Integer.parseInt(answer);
        if (bid > maxBid) {
          maxBid = bid;
          maxPlayer = player;
        }
        else if (timesThrough >= players.size())
          keepGoing = false;
        else {}
        
        if (i == players.size()-1)
          i = 0;
        else
          i++;
      }
      timesThrough++;
    }
    if (bid > 0)
      buyProperty(maxPlayer, place, maxBid); 
  }
  
  
  //for AI only, for properties only
  public boolean shouldBuy(Player p, String prop) {
    boolean shouldBuy = true;
    for (String property : propertyOwners.keySet()) {
      //if no one else owns prop of same color, buy
      if (properties.containsKey(prop) && properties.get(property).getColor().equals(properties.get(prop).getColor()) && propertyOwners.get(property) != null && propertyOwners.get(property).getPlayer() != p.getPlayer())
        shouldBuy = false;
    }
    if (prop.equals("Boardwalk") || prop.equals("Park Place") || prop.equals("Baltic Avenue") || prop.equals("Mediterranean Avenue"))
      shouldBuy = false;
    if (prop.equals("Water Works") || prop.equals("Electric Company"))
      shouldBuy = false;
    return shouldBuy;
  }
  
}