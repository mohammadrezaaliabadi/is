package is.db.datastructure;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public  class Join {
    public static <A,B> List nestedLoopJoin(A[] arrA, Field fieldA, B[] arrB, Field fieldB) throws IllegalAccessException {
        List list = new LinkedList();
        Map map = new HashMap<>();
        List temp = new LinkedList();
        fieldA.setAccessible(true);
        fieldB.setAccessible(true);
        for (A a:arrA) {
            for (B b:arrB){
                if (fieldA.get(a)==fieldB.get(b)){
                    temp.add(b);
                }
            }
            if (temp.size()>0){
                map.put(arrA[0].getClass().getSimpleName(),a);
                map.put(arrB[0].getClass().getSimpleName(),temp);
                temp =new LinkedList();
            }
            if (map.size()>0){
                list.add(map);
                map = new HashMap();
            }
        }
        return list;
    }
    public static <A,B> List nestedLoopJoin(A[] arrA, String fieldNameA, B[] arrB, String fieldNameB) throws IllegalAccessException, NoSuchFieldException {
        return nestedLoopJoin(arrA,arrA[0].getClass().getDeclaredField(fieldNameA),arrB,arrB[0].getClass().getDeclaredField(fieldNameB));
    }
}
