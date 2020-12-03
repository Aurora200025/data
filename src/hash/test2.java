package hash;

import hash.map.HashMap;
import hash.map.LinkedHashMap;
import hash.map.Map;
import hash.model.Key;

public class test2 {

    public static void main(String[] args) {
        System.out.println("zzh");
        test4(new LinkedHashMap<>());
    }

    static void test4(LinkedHashMap<Object, Integer> map) {
//        map.put("rose", 2);
//        map.put("jim", 3);
//        map.put("jake", 4);
//        map.remove("jack");
//        map.remove("jim");
        for (int i = 1; i <= 10; i++) {
            map.put("test" + i, i);
            map.put(new Key(i), i);
        }
//        for (int i = 5; i <= 7; i++) {
//            Asserts.test(map.remove(new Key(i)) == i);
//        }
//        for (int i = 1; i <= 3; i++) {
//            map.put(new Key(i), i + 5);
//        }
        Asserts.test(map.size() == 19);
        Asserts.test(map.get(new Key(1)) == 6);
        Asserts.test(map.get(new Key(2)) == 7);
        Asserts.test(map.get(new Key(3)) == 8);
        Asserts.test(map.get(new Key(4)) == 4);
        Asserts.test(map.get(new Key(5)) == null);
        Asserts.test(map.get(new Key(6)) == null);
        Asserts.test(map.get(new Key(7)) == null);
        Asserts.test(map.get(new Key(8)) == 8);
        map.traversal(new Map.Visitor<Object, Integer>() {
            public boolean visit(Object key, Integer value) {
                System.out.println(key + "-----" + value);
                return false;
            }
        });

//
//        java.util.LinkedHashMap
//        java.util.LinkedHashSet
    }
}
