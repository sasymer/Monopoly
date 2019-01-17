import java.util.*;

//This class is used only by Game. No other object should have access to it.
//Board provides only two methods to reveal board structure.
//Board does not know locations of players.
public class Board
{
  private static String[] MAP = {
    "put our string here"};
  
  private static int INVALID = -2;  //e.g. wall
  private static int VALID = -1;

  private int[][] grid;  //grid[x][y] == (INVALID or VALID (no door) or room# (if door))
  
  public Board()
  {
    grid = new int[MAP[0].length()][MAP.length];
    for (int x = 0; x < grid.length; x++)
    {
      for (int y = 0; y < grid[0].length; y++)
      {
        char ch = MAP[y].charAt(x);
        if (ch == 'X')
          grid[x][y] = INVALID;
        else if (ch == '.')
          grid[x][y] = VALID;
        else
          grid[x][y] = ch - '0';
      }
    }
  }
  
  //returns true iff valid square on grid (i.e. not a wall or room)
  public boolean isValid(Location loc)
  {
    return loc.getX() >= 0 && loc.getX() < grid.length &&
      loc.getY() >= 0 && loc.getY() < grid[0].length &&
      grid[loc.getX()][loc.getY()] != INVALID;
  }
  
  //pre:  loc is valid or room
  //post: set of valid adjacent locations (including room locations)
  public Set<Location> getValidAdjacentLocations(Location loc)
  {
    Set<Location> locs = new HashSet<Location>();
    
    if (loc.isRoom())
    {
      //loc is a room. must add squares outside doors.
      for (int x = 0; x < grid.length; x++)
      {
        for (int y = 0; y < grid[0].length; y++)
        {
          if (grid[x][y] == loc.getRoom())
            locs.add(new Location(x, y));
        }
      }
    }
    else
    {
      //loc is not a room.  must be in a hallway somewhere
      locs.add(new Location(loc.getX(), loc.getY() - 1));
      locs.add(new Location(loc.getX() - 1, loc.getY()));
      locs.add(new Location(loc.getX() + 1, loc.getY()));
      locs.add(new Location(loc.getX(), loc.getY() + 1));
      
      //remove invalid locations
      Iterator<Location> it = locs.iterator();
      while (it.hasNext())
      {
        if (!isValid(it.next()))
          it.remove();
      }
      
      //if loc is square outside door, then must add room
      if (grid[loc.getX()][loc.getY()] >= 0)
        locs.add(new Location(grid[loc.getX()][loc.getY()]));
    }
    
    return locs;
  }
}