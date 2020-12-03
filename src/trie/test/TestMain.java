package trie.test;


import trie.Asserts;
import trie.Trie;

/**
 * @author Aurora
 */
public class TestMain {

    static void test1() {
        Trie<Integer> trie = new Trie<>();
        trie.add("cat", 1);
        trie.add("dog", 2);
        trie.add("catalog", 3);
        trie.add("cast", 4);
        trie.add("小码哥", 5);
        Asserts.test(trie.size() == 5);

        Asserts.test(trie.starsWith("do"));
        Asserts.test(trie.starsWith("c"));
        Asserts.test(trie.starsWith("ca"));
        Asserts.test(trie.starsWith("cat"));
        Asserts.test(trie.starsWith("cata"));
        Asserts.test(!trie.starsWith("hehe"));
        Asserts.test(trie.get("小码哥") == 5);
        Asserts.test(trie.remove("cat") == 1);
        Asserts.test(trie.remove("catalog") == 3);
        Asserts.test(trie.remove("cast") == 4);
        Asserts.test(trie.size() == 2);
        Asserts.test(trie.starsWith("小"));
        Asserts.test(trie.starsWith("do"));
        Asserts.test(!trie.starsWith("c"));
    }

    public static void main(String[] args) {
        test1();

    }
}
