package fuxiTest;




import java.util.HashMap;
import java.util.Map;

public class jichu {
    public static void main(String[] args) {
        Map map = new HashMap();
        map.put("d","2");
        map.put("w",3);
        System.out.println("通过Map.keySet遍历key和value：");
        for (Object key : map.keySet()) {
            System.out.println("key= "+ key + " and value= " + map.get(key));
        }
    }
}
