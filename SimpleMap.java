import java.util.*;

public class SimpleMap<K, V>
{
  private ArrayList<MapEntry<K, V>> entries;
  
  public SimpleMap()
  {
    entries = new ArrayList<MapEntry<K, V>>();
  }
  
  public int size()
  {
    return entries.size();
  }
  
  public boolean containsKey(Object key)
  {
    for (int i = 0; i < entries.size(); i++)
    {
      if (entries.get(i).getKey().equals(key))
        return true;
    }
    return false;
  }
  
  public V get(Object key)
  {
    for (int i = 0; i < entries.size(); i++)
    {
      if (entries.get(i).getKey().equals(key))
        return entries.get(i).getValue();
    }
    return null;
  }
  
  public V put(K key, V value)
  {
    for (int i = 0; i < entries.size(); i++)
    {
      if (entries.get(i).getKey().equals(key))
      {
        V oldval = entries.get(i).getValue();
        entries.get(i).setValue(value);
        return oldval;
      }
    }
    entries.add(new MapEntry(key, value));
    return null;
  }
  
  public V remove(Object key)
  {
    V value;
    for (int i = 0; i < entries.size(); i++)
    {
      if (entries.get(i).getKey().equals(key))
      {
        value = entries.get(i).getValue();
        entries.remove(i);
        return value;
      }
    }
    return null;
  }
  
  public SimpleSet<K> keySet()
  {
    SimpleSet<K> set = new SimpleSet<K>();
    for (int i = 0; i < entries.size(); i++)
      set.add(entries.get(i).getKey());
    return set;
  }
  
  public String toString()
  {
    return entries.toString();
  }
}