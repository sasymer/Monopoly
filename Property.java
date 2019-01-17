public class Property
{
  //have hotel on property
  private boolean hasHotel;
  
  //# houses on property
  private int houses;
  
  //price of property
  private int price;
 
  //property is mortgaged
  private boolean mortgaged;
  
  //cost to build house on property (if possible)
  private int rent;
  private int house1;
  private int house2;
  private int house3;
  private int house4;
  private int hotel;
  private int mortgPrice;
  
  private String color;
  
  //name of the property
  private String name;
  
  private boolean monopolized;
  
  public Property(String s, int p, int r, int h1, int h2, int h3, int h4, int h, int m, String c)
  {
    hasHotel = false;
    rent = r;
    houses = 0;
    house1 = h1;
    house2 = h2;
    house3 = h3;
    house4 = h4;
    hotel = h;
    price = p;
    mortgaged = false;
    name = s;
    color = c;
    mortgPrice = m;
    monopolized = false;
  }
  
  public int plainRent()
  {
    return rent;
  }
  
  public int getNumHouses()
  {
    return houses;
  }
  
  public boolean hasHotel()
  {
    return hasHotel;
  }
  
  public int hotelPrice()
  {
    return hotel;
  }
  
  public void removeHotel()
  {
    hasHotel = false;
  }
  
  public boolean addHotel()
  {
    if (hasHotel)
      return false;
    else
    {
      hasHotel = true;
      return true;
    }
  }
  
  public void removeHouses()
  {
    houses = 0;
  }
  
  public void setNumHouses(int i)
  {
    houses = i;
  }
  
  public int getPrice()
  {
    return price;
  }
  
  public int getRent()
  {
    if (getNumHouses() == 0 && !hasHotel)
      return rent;
    else if (getNumHouses() == 1)
      return house1;
    else if (getNumHouses() == 2)
      return house2;
    else if (getNumHouses() == 3)
      return house3;
    else if (getNumHouses() == 4)
      return house4;
    else
      return hotel;
  }
  
  public String getName()
  {
    return name;
  }
  
  public String getColor()
  {
    return color;
  }
  
  public void addHouse()
  {
    houses++;
  }
  
  public int mortPrice()
  {
    return mortgPrice;
  }
  
  public int housePrice()
  {
    if (color.equals("brown") || color.equals("light blue"))
      return 50;
    else if (color.equals("pink") || color.equals("orange"))
      return 100;
    else if (color.equals("red") || color.equals("yellow"))
      return 150;
    else
      return 200;
                       
  }
  
  public void makeMonopolized()
  {
    monopolized = true;
  } 
  
  public boolean isMonopolized()
  {
    return monopolized;
  }
}
