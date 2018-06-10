package kejuntong.com.test;

import java.util.ArrayList;

public class myClass {

    public static void main(String[] args){

        ArrayList<String> testList = new ArrayList<>();
        for (int i=0; i<10; i++){
            testList.add(null);
        }

        testList.set(3, "wow");


        testList.set(3, "adfasdf");
        System.out.println(testList);
    }

}
