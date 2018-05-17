import java.util.ArrayList;
import java.util.List;

public class AutocompleteGraph implements TrieGraph{
    TrieNode root;

    public AutocompleteGraph(){
        root = new TrieNode(' ', false);
    }

    @Override
    public void insert(String word) {
        TrieNode current = root;
        for(int i = 0; i < word.length(); i++){
            char currentChar = Character.toLowerCase(word.charAt(i));
            if(current.hasChild(currentChar)){
                current = current.getChild(currentChar);
            }else{
                TrieNode newNode = new TrieNode(currentChar, i == word.length()-1);
                current.addChild(currentChar, newNode);
                newNode.setParent(current);
                current = newNode;
            }
        }
        current.setIsEnd(true);
    }

    @Override
    public void delete(String word) {

    }

    @Override
    public TrieNode find(String word) {
        TrieNode current = root;
        for(int i = 0; i < word.length(); i++){
            char currentChar = word.charAt(i);
            if(current.hasChild(currentChar)){
                current = current.getChild(currentChar);
            }else{
                return null;
            }
        }

        return current;
    }

    @Override
    public boolean contains(String word) {
        TrieNode current = root;
        for(int i = 0; i < word.length(); i++){
            char currentChar = word.charAt(i);
            if(current.hasChild(currentChar)){
                current = current.getChild(currentChar);
            }else{
                return false;
            }
        }

        return current.isEnd();
    }

    public String getWord(TrieNode end){
        StringBuilder stringBuilder = new StringBuilder();
        while (end != null && end != root){
            stringBuilder.insert(0, end.getValue());
            end = end.getParent();
        }

        return stringBuilder.toString();
    }

    public TrieNode findNextWord(TrieNode node){
        if(node.hasAnyChildren()){
            TrieNode current = node.getFirstChild();
            while(!current.isEnd()){
                current = current.getFirstChild();
            }

            return current;
        }else{
            TrieNode prev = node;
            TrieNode current = node.getParent();
            TrieNode firstChild = null;

            while(current != null){
                firstChild = current.getFirstChild(prev.getValue());
                if(firstChild != null){
                    break;
                }

                prev = current;
                current = current.getParent();
            }

            if(firstChild == null){
                return null;
            }

            while(!firstChild.isEnd()){
                firstChild = firstChild.getFirstChild();
            }

            return firstChild;

        }
    }

    public List<String> autocomplete(String word, int n){
        List<String> suggestions = new ArrayList<>();
        TrieNode current = find(word);

        for(int i = 0; i < n; i++){
            if(current == null){
                break;
            }

            TrieNode next = current.getFirstChild();

            if(next == null){
                break;
            }

            suggestions.add(getWord(next));
            current = next;
        }

        return suggestions;
    }
}