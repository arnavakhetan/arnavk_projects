package dictionary;

import java.util.Arrays;

/** The Driver class for CompactPrefixTree */
public class Driver {
    public static void main(String[] args) {
            Dictionary dict = new CompactPrefixTree();
            dict.add("cat");
            dict.add("cart");
            dict.add("carts");
            dict.add("case");
            dict.add("doge");
            dict.add("doghouse");
            dict.add("wrist");
            dict.add("wrath");
            dict.add("wristle");
            System.out.println(dict.toString());
            // Add other "tests"
            // There is a file with words words_ospd.txt in src/main/resources
            String filename = "src/main/resources/words_ospd.txt";
            Dictionary fileDict = new CompactPrefixTree(filename);
            System.out.println("Check 'cat': " + dict.check("cat"));
            System.out.println("Check 'castle': " + dict.check("castle"));
            System.out.println("Check prefix 'ca': " + dict.checkPrefix("ca"));
            System.out.println("Check prefix 'doz': " + dict.checkPrefix("doz"));
            System.out.println("Suggestions for 'do': " + Arrays.toString(fileDict.suggest("do", 3)));



    }
}
