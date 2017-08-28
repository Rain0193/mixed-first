package io.ift.automation.helpers.database.codegenerator;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class JPAGeneratorTest {

    @Test
    public void testGenerate() throws Exception {
        JPAGenerator g = new JPAGenerator("T_bms_order");
        g.generate();
    }
}