import org.junit.Before;
import org.junit.Test;


import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class AutocompleteGraphTest {
    AutocompleteGraph graph;
    List<String> wordList;

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
    public void find() {
    }

    @Test
    public void contains() {
    }

    @Test
    public void getWord() {
    }

    @Test
    public void findNextWord() {
    }

    @Test
    public void autocomplete() {
    }
}