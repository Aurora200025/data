package Set;


import Set.file.FileInfo;
import Set.file.Files;

public class TestMain {

    public static void main(String[] args) {

        FileInfo fileInfo = Files.read("D:\\JavaTest\\Data", new String[]{"java"});
        System.out.println("文件个数：" + fileInfo.getFiles());
        System.out.println("代码行数：" + fileInfo.getLines());
        String[] words = fileInfo.words();
        System.out.println("单词个数：" + fileInfo.words().length);

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

//        Set<Integer> listSet = new ListSet<Integer>();
//        listSet.add(10);
//        listSet.add(11);
//        listSet.add(12);
//        listSet.add(13);
//        listSet.add(14);
//        listSet.traversal(new Set.Visitor<Integer>() {
//            @Override
//            boolean visit(Integer element) {
//                System.out.println(element);
//                return false;
//            }
//        });
//        System.out.println("-------------------------");
//        System.out.println("-------------------------");
//        System.out.println("-------------------------");
//        Set<Integer> treeSet = new TreeSet<Integer>();
//        treeSet.add(11);
//        treeSet.add(22);
//        treeSet.add(33);
//        treeSet.add(44);
//        treeSet.add(55);
//        treeSet.traversal(new Set.Visitor<Integer>() {
//            @Override
//            boolean visit(Integer element) {
//                System.out.println(element);
//                return false;
//            }
//        });
    }
}
