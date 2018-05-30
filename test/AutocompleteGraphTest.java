import org.junit.Before;
import org.junit.Test;


import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class AutocompleteGraphTest {
    private AutocompleteGraph graph;
    private List<String> wordList;

    @Before
    public void setup(){
        wordList = new ArrayList<>();
        wordList.add("cat");
        wordList.add("cats");
        wordList.add("cake");
        wordList.add("dog");
        wordList.add("abra");
        wordList.add("kadabra");
        wordList.add("dodge");
        wordList.add("table");
        wordList.add("tab");

        graph = new AutocompleteGraph(wordList);
    }

    @Test
    public void insert1() {
        AutocompleteGraph insertTestGraph = new AutocompleteGraph();

        assertFalse(insertTestGraph.contains("test"));
        assertFalse(insertTestGraph.contains("abba"));
        assertFalse(insertTestGraph.contains("ab"));


        insertTestGraph.insert("test");
        insertTestGraph.insert("abba");


        assertTrue(insertTestGraph.contains("test"));
        assertTrue(insertTestGraph.contains("abba"));

        assertFalse(insertTestGraph.contains("a"));
        assertFalse(insertTestGraph.contains("ab"));
        assertFalse(insertTestGraph.contains("abb"));

        insertTestGraph.insert("abb");


        assertFalse(insertTestGraph.contains("a"));
        assertFalse(insertTestGraph.contains("ab"));
        assertTrue(insertTestGraph.contains("abb"));
        assertTrue(insertTestGraph.contains("abba"));
    }

    @Test
    public void insert2(){
        AutocompleteGraph insertTestGraph = new AutocompleteGraph();

        String[] words = new String[]{"bat","batter","battered","bats","bate","bait","bart"};

        for(String word : words){
            assertFalse(insertTestGraph.contains(word));
            insertTestGraph.insert(word);
        }

        for(String word : words){
            assertTrue(insertTestGraph.contains(word));
        }

        assertFalse(insertTestGraph.contains("b"));
        assertFalse(insertTestGraph.contains("ba"));
        assertFalse(insertTestGraph.contains("batss"));
        assertFalse(insertTestGraph.contains("batt"));
    }

    @Test
    public void delete1() {
        for(String word : wordList){
            assertTrue(word + " should be in the graph", graph.contains(word));
            graph.delete(word);
            assertFalse(graph.contains(word));
        }
    }

    @Test
    public void delete2() {
        List<String> testList = new ArrayList<>();
        testList.add("but");
        testList.add("butt");
        testList.add("button");
        testList.add("butter");
        testList.add("buttered");

        //Forward
        AutocompleteGraph testGraph = new AutocompleteGraph(testList);

        for(String word : testList){
            testGraph.delete(word);
            assertFalse(testGraph.contains(word));
        }

        //Backwards
        testGraph = new AutocompleteGraph(testList);

        for(int i = testList.size() -1; i >= 0; i--){
            String word = testList.get(i);
            testGraph.delete(word);
            assertFalse(testGraph.contains(word));
        }

        //Leave some words in
        testGraph = new AutocompleteGraph(testList);

        for(int i = 1; i < testList.size(); i = i + 2){
            String word = testList.get(i);
            String previousWord = testList.get(i-1);
            testGraph.delete(word);
            assertFalse(testGraph.contains(word));
            assertTrue(testGraph.contains(previousWord));
        }

    }

    @Test
    public void find1() {
        for(String word : wordList){
            assertEquals(word, graph.getWord(graph.find(word)));
        }

        assertNull(graph.find("asdfhorevsekb"));
        assertNotNull(graph.find("ca"));
    }

    @Test
    public void contains() {
        for(String word : wordList){
            assertTrue(graph.contains(word));
        }

        assertFalse(graph.contains("ca"));
        assertFalse(graph.contains("asdfasdf"));
        assertFalse(graph.contains("catss"));
        assertFalse(graph.contains("plret"));

    }

    @Test
    public void getWord() {
        for(String word : wordList){
            assertEquals(word, graph.getWord(graph.find(word)));
        }
    }

    @Test
    public void findNextWord() {
        List<String> testList = new ArrayList<>();
        testList.add("but");
        testList.add("butt");
        testList.add("butter");
        testList.add("buttered");
        testList.add("button");
        testList.add("bz");
        testList.add("can");
        testList.add("cin");
        testList.add("daz");

        AutocompleteGraph testGraph = new AutocompleteGraph(testList);

        for(int i = 0; i < testList.size() -1; i++){
            TrieNode current = testGraph.find(testList.get(i));

            TrieNode next = testGraph.findNextWord(current);
            TrieNode lookup = testGraph.find(testList.get(i+1));
            assertEquals(testGraph.getWord(next) + " = " + testGraph.getWord(lookup), next, lookup);
        }
    }

    @Test
    public void autocomplete() {
        List<String> testList = new ArrayList<>();
        testList.add("but");
        testList.add("butt");
        testList.add("butter");
        testList.add("buttered");
        testList.add("button");
        testList.add("bz");
        testList.add("can");
        testList.add("cin");
        testList.add("daz");

        AutocompleteGraph testGraph = new AutocompleteGraph(testList);

        int n = 2;
        for(int i = 0; i < testList.size() - 1 - n; i++){
            List<String> autoWords = testGraph.autocomplete(testList.get(i), n);

            assertEquals(n, autoWords.size());
            for(int j = 0; j < autoWords.size(); j++){
                assertEquals(autoWords.get(j), testList.get(i + j + 1));
            }

        }
    }
}