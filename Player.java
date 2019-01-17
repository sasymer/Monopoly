import java.util.*;

public class Player
{
  private int amountMoney;
  //private SimpleMap<String, Integer> account;
  private int jailCard;
  private int propertyIndex;
  private int turnsInJail;
  private int playerNum;
  private boolean isAI;
  
  public Player(int location, int num, boolean ai)
  {
    amountMoney = 1500;
    jailCard = 0;
    propertyIndex = location;
    turnsInJail = 0;
    playerNum = num;
    isAI = ai;
  }
  
  public boolean isAI()
  {
    return isAI;
  }
  
  public int getPlayer()
  {
    return playerNum;
  }

  //if this number gets beyond 3, they have to pay $50 to get out of jail
  public void addJailTurn()
  { 
    turnsInJail++;
  }
  
  public int turnsInJail()
  {
    return turnsInJail;
  }
  
  public int numJailCards()
  {
    return jailCard;
  }
  
  public boolean inJail()
  {
    return turnsInJail >= 1;
  }
  
  public void getOutofJail()
  {
    turnsInJail = 0;
  }
  
  public int getMoney()
  {
    return amountMoney;
  }
  
  public void addMoney(int money)
  {
    amountMoney+=money;
  }
  
  public boolean hasJailCard()
  {
    return jailCard >= 1;
  }
  
  public void addJailCard()
  {
    jailCard++;
  }
  
  public void usedJailCard()
  {
    jailCard--;
    turnsInJail = 0;
  }
  
  public int getLocation()
  {
    return propertyIndex;
  }
  
  public void changeLocation(int index)
  {
    propertyIndex = index;
  }
  
}