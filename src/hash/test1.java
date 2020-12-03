package hash;


import hash.map.HashMap2;
import hash.map.Map;
import hash.model.Key;

public class test1 {

    public static void main(String[] args) {
        HashMap2<Object, Integer> map = new HashMap2<>();
        for (int i = 1; i <= 19; i++) {
            map.put(new Key(i), i);
        }
        map.put(new Key(4), 100);
        System.out.println(map.size());
        System.out.println(map.get(new Key(1)));
        map.traversal(new Map.Visitor<Object, Integer>() {
            @Override
            public boolean visit(Object key, Integer value) {
                System.out.println(key+"----"+value);
                return false;
            }
        });
        map.print();
        System.out.println(map.get(new Key(4)));
    }
}
