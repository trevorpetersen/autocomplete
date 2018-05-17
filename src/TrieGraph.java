public interface TrieGraph {
    void insert(String word);
    void delete(String word);
    TrieNode find(String word);
    boolean contains(String word);
}
