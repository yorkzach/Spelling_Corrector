package spell;

public class Trie implements ITrie{
  private int WordCounter =0;
  private int NodeCounter=1;
  private INode rootNode=new Node();

  @Override
  public void add(String word) {
    INode current = rootNode;
    word = word.toLowerCase();
    for (char c : word.toCharArray()) {
      int index= c - 'a';
      if (current.getChildren()[index]==null){
        current.getChildren()[index] = new Node();
        NodeCounter++;
      }
      current = current.getChildren()[index];
    }
    if(current.getValue()==0){
      WordCounter++;
    }
    current.incrementValue();
  }

  @Override
  public INode find(String word) {
    INode current;
    word = word.toLowerCase();
    current = rootNode;
    for (char c: word.toCharArray()){
      int index = c-'a';
      if (current.getChildren()[index]==null){
        return null;
      }
      current = current.getChildren()[index];
    }
    if(current.getValue()==0){
      return null;
    }
    /**
     * Goes through recursively and finds all the nodes
     * and the last node has to have a count > 0
     */
    return current;
  }

  @Override
  public int getWordCount() {
    /**
     * everytime you add a node, increment a node counter variable,
     * and everytime you add a unique word increment word counter variable,
     * but not if the word has already been added
     */
    return WordCounter;
  }

  @Override
  public int getNodeCount() {
    return NodeCounter;
  }

  @Override
  public String toString(){
    StringBuilder word = new StringBuilder();
    StringBuilder words = new StringBuilder();

    toStringHelper(rootNode, word, words);

    return words.toString();
  }

  private void toStringHelper(INode node, StringBuilder word, StringBuilder words){
    if(node.getValue() > 0){
        words.append(word.toString());
        words.append('\n');
    }
    INode[] children = node.getChildren();

    for(int i = 0; i < children.length; i++){
      if(children[i] != null){
        char c = (char) ('a' + i);
        word.append(c);
        toStringHelper(children[i], word, words);
        word.deleteCharAt(word.length() - 1);
      }
    }
  }

  @Override
  public int hashCode(){
    int total = WordCounter * NodeCounter;
    INode[] children = rootNode.getChildren();
    for(int i = 0; i < children.length; i++){
      if(children[i] != null){
        total =  total * i;
        break;
      }
    }
    // WordCounter + NodeCounter + NonNullIndex
    // add the index value for first non-null children nodes of the root node
    return total;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Trie trie = (Trie) o;
    if (this == o) return true;
    return WordCounter == trie.WordCounter && NodeCounter == trie.NodeCounter && nodesEqual(rootNode, trie.rootNode);
    /** Traverse every node, if every value, count, null
     * matches return true, if not false
     */
  }
  private boolean nodesEqual(INode node1, INode node2) {
    if (node1 == null && node2 == null) {
      return true;
    }
    if (node1 == null || node2 == null) {
      return false;
    }
    if (node1.getValue() != node2.getValue()) {
      return false;
    }
    for (int i = 0; i < 26; i++) {
      if (!nodesEqual(node1.getChildren()[i], node2.getChildren()[i])) {
        return false;
      }
    }
    return true;
  }
}
