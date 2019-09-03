package edu.jxau.community;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @title: community
 * @ClassName Test.java
 * @Description:
 * @Author: liam
 * @Version:
 **/
public class Test {

    @org.junit.Test
    public void test(){
        String fileName = "liam.jpg";
        String suffix = fileName.substring(fileName.lastIndexOf(".")+1);
        String sub = fileName.substring(1,4);

        System.out.println(suffix);
        System.out.println(sub);
    }
}
