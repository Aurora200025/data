package mapping;

import Set.ListSet;
import Set.Set;
import Set.TreeSet;
import Set.file.FileInfo;
import Set.file.Files;

public class TestMain {

    public static void main(String[] args) {



        FileInfo fileInfo = Files.read("D:\\JavaTest\\Data", new String[]{"java"});
        System.out.println("文件个数：" + fileInfo.getFiles());
        System.out.println("代码行数：" + fileInfo.getLines());
        String[] words = fileInfo.words();
        System.out.println("单词个数：" + fileInfo.words().length);

        Map<String, Integer> map = new TreeMap<String, Integer>();
        Times.test("TreeMap", new Times.Task() {
            @Override
            public void execute() {
                for (int i = 0; i < words.length; i++) {
                    Integer count = map.get(words[i]);
                    count = count == null ? 0 : count;
                    map.put(words[i], count + 1);
                }
                System.out.println(map.size());
            }
        });
        System.out.println("treeMap去重后单词个数："+map.size());


        Set<String> treeSet = new TreeSet<String>();
        Times.test("TreeSet", new Times.Task() {
            @Override
            public void execute() {
                for (int i = 0; i < words.length; i++) {
                    treeSet.add(words[i]);
                }
            }
        });
        System.out.println("treeSet去重后单词个数："+treeSet.size());


        Set<String> listSet = new ListSet<String>();
        Times.test("ListSet", new Times.Task() {
            @Override
            public void execute() {
                for (int i = 0; i < words.length; i++) {
                    listSet.add(words[i]);
                }
            }
        });
        System.out.println("listSet去重后单词个数：" + listSet.size());




//        TreeMap<String, Integer> treeMap = new TreeMap<>();
//        treeMap.put("c", 2);
//        treeMap.put("a", 5);
//        treeMap.put("b", 6);
//        treeMap.put("a", 8);
        map.traversal(new Map.Visitor<String, Integer>() {
            @Override
            public boolean visit(String key, Integer value) {
                System.out.println("key=" + key + "，value=" + value);
                return true;
            }
        });
    }
}
