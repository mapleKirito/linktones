package com.linktones.mapleres;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MapleResApplicationTests {

    @Autowired
    private StringEncryptor stringEncryptor;

    @Test
    void contextLoads() {
    }

    @Test
    public void encrypt() {
        System.out.println("PWD: " + stringEncryptor.encrypt("yu563Teh1AutEnJDr4zF7I7z3YPj3R"));
    }

}
