package com.linktones.mapleres;

import com.linktones.mapleres.utils.ResUtils;
import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MapleResApplicationTests {

    @Autowired
    private StringEncryptor stringEncryptor;
    @Autowired
    private ResUtils resUtils;

    @Test
    void contextLoads() {
    }

    @Test
    void getResTypeTest() {
        resUtils.getResType("upload/YDS/s_inthum/0/1000/000/006/00000622.JPG");
    }

    @Test
    public void encrypt() {


    }

}
