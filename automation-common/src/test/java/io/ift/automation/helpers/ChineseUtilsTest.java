package io.ift.automation.helpers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class ChineseUtilsTest {

    private static final Logger logger = LogManager.getLogger(ChineseUtilsTest.class.getName());
    @Test
    public void testGetSingleChineseCharactor() throws Exception {
        String a = ChineseUtils.getSingleChineseCharactor();
        logger.info(StringHelper.toUTF8String(a));
        logger.info(ChineseUtils.getSingleChineseCharactor());
    }

    @Test
    public void testGetFixedLengthChineseCharactors(){
        String a = ChineseUtils.getFixedLengthChinese(12);
        logger.info("random_chinese={}",a);
        Assert.assertEquals(a.length(),12);
    }

    @Test
    public void testRandomAddress(){
        String address = ChineseUtils.getRandomAddress();
        logger.info(address);
    }

    @Test
    public void testGetRandomLengthChinese(){
        String t = ChineseUtils.getRandomLengthChinese(2,4);
        logger.info(t);
    }
}