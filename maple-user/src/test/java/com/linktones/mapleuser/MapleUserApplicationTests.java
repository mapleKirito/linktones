package com.linktones.mapleuser;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class MapleUserApplicationTests {

    @Test
    void contextLoads() {
        List<String> list=new ArrayList<>();
        List<String> list2=new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list2.add("2");
        list2.add("3");
        list2.add("4");
        list.removeAll(list2);
        System.out.println(list.toString());
    }



}
