public class Card
{
  private int type; //if chance, 0. If community chest, 1.
  private String message;
  
  public Card(int cardType, String input)
  {
    type = cardType;
    message = input;
  }
  
  public String getMessage()
  {
    return message;
  }
  
  
}