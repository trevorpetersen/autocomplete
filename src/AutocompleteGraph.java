import java.util.ArrayList;
import java.util.List;

public class AutocompleteGraph implements TrieGraph{
    TrieNode root;

    public AutocompleteGraph(){
        root = new TrieNode(' ', false);
    }

    public AutocompleteGraph(List<String> wordList){
        this();
        for(String word : wordList){
            insert(word);
        }
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
                current.setChild(currentChar, newNode);
                newNode.setParent(current);
                current = newNode;
            }
        }
        current.setIsEnd(true);
    }

    @Override
    public void delete(String word) {
        TrieNode node = find(word);
        if(node == null){
            return;
        }

        node.setIsEnd(false);

        while(!node.hasAnyChildren() && !node.isEnd() && node.hasParent()){
            TrieNode parent = node.getParent();
            parent.setChild(node.getValue(), null);
            node = parent;
        }
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
        if(current == null){
            return suggestions;
        }
        
        for(int i = 0; i < n; i++) {
            current = findNextWord(current);
            if(current == null){
                return suggestions;
            }else{
                suggestions.add(getWord(current));
            }
        }
        return suggestions;
    }
}
