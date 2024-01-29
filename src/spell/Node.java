package spell;

public class Node implements INode{
  private int FreqVal = 0;
  private INode[] children = new Node[ALPHABET_SIZE];
  static final int ALPHABET_SIZE = 26;
  @Override
  public int getValue() {
    return FreqVal;
  }

  @Override
  public void incrementValue() {
    FreqVal += 1;
  }

  @Override
  public INode[] getChildren() {
    return children;
  }
}
