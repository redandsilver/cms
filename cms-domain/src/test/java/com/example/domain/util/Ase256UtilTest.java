package com.example.domain.util;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class Ase256UtilTest {



    @Test
    void encrypt() {
        String encrypt = Aes256Util.encrypt("Hello World!");
        assertEquals(Aes256Util.decrypt(encrypt),"Hello World");
    }

    @Test
    void decrypt() {
    }
}