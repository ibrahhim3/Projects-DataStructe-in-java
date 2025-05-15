import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class SpellChecker {
    private static final char[] ALPHABET = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    
    public static void main(String[] args) throws IOException {
        GTUHashSet<String> dictionary = new GTUHashSet<>();
        
        // Track memory usage
        Runtime runtime = Runtime.getRuntime();
        runtime.gc();
        long beforeMemory = runtime.totalMemory() - runtime.freeMemory();
        //

        BufferedReader reader = new BufferedReader(new FileReader("dictionary.txt"));
        String word;
        while ((word = reader.readLine()) != null) {
            dictionary.add(word.trim());
        }
        reader.close();
        
        // Measure memory after loading
        runtime.gc();
        long afterMemory = runtime.totalMemory() - runtime.freeMemory();
        double memoryUsed = (afterMemory - beforeMemory) / (1024.0 * 1024.0);
        System.out.printf("Memory used while loading dictionary: %.2f MB\n", memoryUsed);
        // 

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter a word: ");
            String input = scanner.nextLine().trim();
            /* one of the words in the dictionary is exit so i had to remove this condition
            if (input.equalsIgnoreCase("exit")) {
                break;
            }
            */

            long startTime = System.nanoTime();
            
            if (dictionary.contains(input)) {
                System.out.println("Correct.");
            } else {
                System.out.println("Incorrect.");
                System.out.print("Suggestions: ");
                
                //StringList is our own custom list
                StringList suggestions = new StringList();
                
                //
                GTUHashSet<String> checked = new GTUHashSet<>();
                checked.add(input);
                //

                // Generate all edit distance 1 variants
                for (String variant : generateEditDistance1(input)) {
                    if (dictionary.contains(variant) && !checked.contains(variant)) {
                        suggestions.add(variant);
                        checked.add(variant);
                    }
                }
                
                // Generate edit distance 2 variants
                for (String variant1 : generateEditDistance1(input)) {
                    for (String variant2 : generateEditDistance1(variant1)) {
                        if (dictionary.contains(variant2) && !checked.contains(variant2)) {
                            suggestions.add(variant2);
                            checked.add(variant2);
                        }
                    }
                }
                
                System.out.println(suggestions);
            }
            
            long endTime = System.nanoTime();
            System.out.printf("Lookup and suggestion took %.2f ms\n", (endTime - startTime) / 1e6);
            
            System.out.println("Total Collisions: " + dictionary.getCollisionCount());
        }
        
      //  scanner.close();
    }
    
    private static String[] generateEditDistance1(String word) {
        StringList result = new StringList();
        
        // Deletion
        for (int i = 0; i < word.length(); i++) {
            result.add(word.substring(0, i) + word.substring(i + 1));
        }
        
        // Insertion
        for (int i = 0; i <= word.length(); i++) {
            for (char c : ALPHABET) {
                result.add(word.substring(0, i) + c + word.substring(i));
            }
        }
        
        // Substitution
        for (int i = 0; i < word.length(); i++) {
            for (char c : ALPHABET) {
                if (c != word.charAt(i)) {
                    result.add(word.substring(0, i) + c + word.substring(i + 1));
                }
            }
        }
        
        // Transposition
        for (int i = 0; i < word.length() - 1; i++) {
            result.add(word.substring(0, i) + word.charAt(i + 1) + word.charAt(i) + word.substring(i + 2));
        }
        
        return result.toArray();
    }
}
