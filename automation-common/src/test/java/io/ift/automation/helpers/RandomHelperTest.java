package io.ift.automation.helpers;

import com.google.common.collect.Lists;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class RandomHelperTest {

    @Test
    public void testGetInstance() throws Exception {
        Assert.assertNotNull(RandomHelper.getInstance());
    }

    @Test
    public void testGenerateMobilePhoneNumber() throws Exception {
        Assert.assertEquals(RandomHelper.getInstance().generateMobilePhoneNumber().length(), 11);
    }

    @Test
    public void testGenerateMobilePhoneNumber1() throws Exception {
        Assert.assertEquals(RandomHelper.getInstance().generateMobilePhoneNumber(12).length(),12);
    }

    @Test
    public void testGenerateUserName() throws Exception {
        Assert.assertNotNull(RandomHelper.getInstance().generateUserName());

    }

    @Test
    public void testGenerateAge() throws Exception {
        Assert.assertNotNull(RandomHelper.getInstance().generateAge());
    }

    @Test
    public void testGetBirthYear() throws Exception {
        Assert.assertNotNull(RandomHelper.getInstance().getBirthYear(12),"2003");
    }

    @Test
    public void testGetBirthMonth() throws Exception {
        Assert.assertNotNull(RandomHelper.getInstance().getBirthMonth());
    }

    @Test
    public void testGetBirthDay() throws Exception {
        Assert.assertNotNull(RandomHelper.getInstance().getBirthDay(12, 1990));
    }

    @Test
    public void testGenerateIdCardNumberByAge() throws Exception {
        Assert.assertNotNull(RandomHelper.getInstance().generateIdCardNumberByAge(12));
    }

    @Test
    public void testGenerateIdCardNumber() throws Exception {
        Assert.assertNotNull(RandomHelper.getInstance().generateIdCardNumber());
    }

    @Test
    public void testGenerateReferenceId() throws Exception {
        Assert.assertNotNull(RandomHelper.getInstance().generateReferenceId());
    }

    @Test
    public void testGenerateQQNumber() throws Exception {
        Assert.assertNotNull(RandomHelper.getInstance().generateQQNumber());
    }

    @Test
    public void testGenerateRandomValue() throws Exception {
        Assert.assertNotNull(RandomHelper.getInstance().generateRandomValue(400));
    }

    @Test
    public void testIntInAGivenRange() throws Exception {
        int t = RandomHelper.getInstance().getFixedLengthInt(100,300);
        Assert.assertTrue(t>100);
        Assert.assertTrue(t < 300);
    }

    @Test
    public void testRandomFrom(){
        Assert.assertTrue(Lists.newArrayList("学区房", "首套", "投资", "婚房", "地铁").contains(
                RandomHelper.getRandomFrom(Lists.newArrayList("学区房", "首套", "投资", "婚房", "地铁"))));
    }
}
