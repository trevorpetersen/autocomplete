public class TrieNode {
    private char value;
    private TrieNode[] children;
    private TrieNode parent;
    private boolean isEnd;

    public TrieNode(char value, boolean isEnd){
        this.value = value;
        this.isEnd = isEnd;
        children = new TrieNode[26];
    }

    public char getValue(){
        return value;
    }

    public TrieNode getParent(){
        return parent;
    }

    public void setIsEnd(boolean isEnd){
        this.isEnd = isEnd;
    }

    public boolean isEnd(){
        return isEnd;
    }

    public void setParent(TrieNode node){
        parent = node;
    }

    public boolean hasParent(){
        return parent != null;
    }

    public void setChild(char c, TrieNode child){
        children[convertCharToIndex(c)] = child;
    }

    public TrieNode getChild(char c){
        return children[convertCharToIndex(c)];
    }

    public boolean hasChild(char c){
        return children[convertCharToIndex(c)] != null;
    }

    public boolean hasAnyChildren(){
        for(int i = 0; i < children.length; i++){
            if(children[i] != null){
                return true;
            }
        }
        return false;
    }

    public TrieNode getFirstChild(){
        for(int i = 0; i < children.length; i++){
            if(children[i] != null){
                return children[i];
            }
        }

        return null;
    }

    public TrieNode getFirstChild(char after){
        for(int i = convertCharToIndex(after) + 1; i < children.length; i++){
            if(children[i] != null){
                return children[i];
            }
        }

        return null;
    }

    private int convertCharToIndex(char c){
        return ((int) c) - ((int) 'a');
    }
}
