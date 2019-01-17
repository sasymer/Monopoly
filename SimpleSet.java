import java.util.*;

public class SimpleSet<E> implements Iterable<E>
{
  private ListNode<E> first;
  private int size;
  
  public SimpleSet()
  {
    first = null;
    size = 0;
  }
  
  public int size()
  {
    return size;
  }
  
  public boolean contains(Object obj)
  {
    ListNode<E> temp = first;
    while (temp != null)
    {
      if (temp.getValue().equals(obj))
        return true;
      temp = temp.getNext();
    }
    return false;
  }
  
  public boolean add(E obj)
  {
    if (!contains(obj))
    {
      first = new ListNode<E>(obj, first);
      size++;
      return true;
    }
    else
      return false;
  }
  
  public boolean remove(Object obj)
  {
    if (contains(obj))
    {
      ListNode<E> temp = first;
      if (temp != null && temp.getValue().equals(obj))
      {
        first = first.getNext();
        size--;
        return true;
      }
      while (temp.getNext() != null)
      {
        if (temp.getNext().getValue().equals(obj)) //if next equals obj
        {
          temp.setNext(temp.getNext().getNext());
          size--;
          return true;
        }
        else
          temp = temp.getNext();
      }
      return false;
    }
    else
      return false;
  }
  
  public Iterator<E> iterator()
  {
    return new NodeIterator(first);
  }
  
  public SimpleSet test()
  {
    SimpleSet<String> set = new SimpleSet<String>();
    System.out.println(set.size() == 0);
    System.out.println(!set.contains("A"));
    System.out.println(set.add("A"));
    System.out.println(set.size() == 1);
    System.out.println(set.contains("A"));
    System.out.println(!set.contains("B"));
    System.out.println(set.add("B"));
    System.out.println(set.size() == 2);
    System.out.println(set.contains("A"));
    System.out.println(set.contains("B"));
    System.out.println(set.add("C"));
    System.out.println(set.size() == 3);
    System.out.println(set.contains("A"));
    System.out.println(set.contains("B"));
    System.out.println(set.contains("C"));
    System.out.println(!set.contains("D"));
    
//    
    System.out.println(!set.add("A"));
   
    System.out.println(set.size() == 3);
    System.out.println(!set.add("B"));
    System.out.println(set.size() == 3);
    System.out.println(!set.add("C"));
    System.out.println(set.size() == 3);
    System.out.println(set.add("D"));
    System.out.println(set.add("E"));

    System.out.println(set.size() == 5);
    System.out.println(!set.remove("F"));
    
    System.out.println(set.size() == 5);
    System.out.println(set.remove("C"));
    System.out.println(set.size() == 4);
    return set;
//    System.out.println(!set.contains("C"));
//    System.out.println(set.remove("A"));
//    System.out.println(set.size() == 3);
//    System.out.println(!set.contains("A"));
//    System.out.println(set.remove("E"));
//    System.out.println(set.size() == 2);
//    System.out.println(!set.contains("E"));
//    System.out.println(set.contains("B"));
//    System.out.println(set.contains("D"));
  }
  
  
  
  public String toString()
  {
    String s = "";
    ListNode<E> temp = first;
    while (temp != null)
    {
      s += temp.getValue() + " ";
      temp = temp.getNext();
    }
    return s;
  }
}