import java.util.*;

public class NodeIterator<E> implements Iterator<E>
{
  private ListNode<E> nextNode;
  
  public NodeIterator(ListNode<E> node)
  {
    nextNode = node;
  }
  
  public boolean hasNext()
  {
    if (nextNode != null)
      return true;
    else
      return false;
  }
  
  public E next()
  {
    E value = nextNode.getValue();
    nextNode = nextNode.getNext();
    return value;
  }
  
  public void remove()
  {
    throw new UnsupportedOperationException();
  }
}