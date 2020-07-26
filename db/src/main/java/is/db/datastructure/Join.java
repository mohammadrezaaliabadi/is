package is.db.datastructure;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Join<A,B> {
    public List nestedLoopJoin(A[] arrA, Field fieldA, B[] arrB,Field fieldB) throws IllegalAccessException {
        List list = new LinkedList();
        List cList = new LinkedList();
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
                cList.add(a);
                cList.add(temp);
                temp =new LinkedList();
            }
            if (cList.size()>0){
                list.add(cList);
                cList = new LinkedList();
            }
        }
        return list;
    }
}
