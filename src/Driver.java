import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Driver {
    public static void main(String[] args){
        String dict = "/home/trevor/IdeaProjects/autocomplete/src/words.txt";
        AutocompleteGraph graph = new AutocompleteGraph();

        List<String> words = readTextFile(dict);

        for(int i = 0; i < words.size(); i++){
            if(isAlpha(words.get(i))){
                graph.insert(words.get(i));
            }

            if(isAlpha(words.get(i)) && !graph.contains(words.get(i))){
                System.out.println("Failed to insert " + words.get(i));
            }
        }

        System.out.println(graph.autocomplete("hel", 5));
    }

    public static List<String> readTextFile(String filename){
        List<String> words = new ArrayList<>();
        try{
            File file = new File(filename);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line = null;
            while( (line = bufferedReader.readLine()) != null)
                words.add(line.toLowerCase());

            return words;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isAlpha(String name) {
        char[] chars = name.toCharArray();

        for (char c : chars) {
            if(!Character.isLetter(c)) {
                return false;
            }
        }

        return true;
    }
}
