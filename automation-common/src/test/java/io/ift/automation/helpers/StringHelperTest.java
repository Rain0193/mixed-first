package io.ift.automation.helpers;

import com.google.common.base.Function;
import com.google.common.base.Splitter;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringHelperTest {

    @org.testng.annotations.Test
    public void testMd5() throws Exception {
        Assert.assertNotNull(StringHelper.md5("testings"));
    }

    @org.testng.annotations.Test
    public void testJoin() throws Exception {
        String[] test = {"test","test1","test2"};
        Function function = o -> o.toString()+"s";
        Assert.assertEquals(StringHelper.join(test, function, ","), "tests,test1s,test2s");

    }

    @org.testng.annotations.Test
    public void testJoin_forArray() throws Exception {
        String[] test = {"test","test1","test2"};
        Function function = new Function() {
            @Override
            public Object apply(Object o) {
                return o.toString()+"s";
            }
        };

        Assert.assertEquals(StringHelper.join(test,function),"teststest1stest2s");

    }

    @org.testng.annotations.Test
    public void testSubString() throws Exception {
        String target = "this is testing,hello world";
        Assert.assertEquals(StringHelper.subStringFromMatcher(target, "test", 1, true),"testing,hello world");
        Assert.assertEquals(StringHelper.subStringFromMatcher(target, "test", 1, false), "ing,hello world");

    }

    @org.testng.annotations.Test
    public void testBigDecimalRoundUpToString() throws Exception {
        BigDecimal b = new BigDecimal("12.9999");
        Assert.assertEquals(StringHelper.bigDecimalRoundUpToString(b), "13.00");
        Assert.assertEquals(StringHelper.formatMoneyNumber("12.9999"),"13.00");
    }

    @org.testng.annotations.Test
    public void testToUTF8String() throws Exception {
        String target= "测试字符串；";
        Assert.assertNotNull(StringHelper.toUTF8String(target));
    }

    @Test
    public void testIsEmptyOrBlank(){
        String emptyString_1= null;
        String emptyString_2="";
        String blankString_1="   ";
        Assert.assertFalse(StringHelper.isNotEmptyOrNotBlankString(emptyString_1));
        Assert.assertFalse(StringHelper.isNotEmptyOrNotBlankString(emptyString_2));
//        Assert.assertTrue(StringHelper.isNotEmptyOrNotBlankString(blankString_1));
        Assert.assertTrue(StringHelper.isNotEmptyOrNotBlankString("Test  "));

    }

    @Test
    public void testSplitCamelCase() throws Exception {
        String a = "testElement";
        Assert.assertEquals(StringHelper.splitCamelCase(a), "Test Element");
    }

    @Test
    public void testSplit_Slash(){
        String path ="com/test/automation";
        Assert.assertEquals(path.split("/").length, 3);
        System.out.println("a.json".substring(0, "a.json".lastIndexOf(".json")));

    }

    @Test
    public void testStringExtract(){
        String original ="您获取的超级密码为【-1403707537】，使用超级密码登陆将会被记录，请妥善保管！";
        Pattern pattern = Pattern.compile("(-\\d{10})", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(original);
        String result ="";
        if (matcher.find()) {
            result= matcher.group();
            System.out.println(result);
            System.out.println(result);
        }

        Assert.assertEquals(result, "-1403707537");
    }

    @Test
    public void testExtract() throws Exception {
        String extracted = StringHelper.extract("changeTab('sale')","['](.*)[']");
        Assert.assertEquals(extracted,"'sale'");
    }

    @Test
    public void testFloatString(){
        Float f1 = Float.valueOf("350.0000");
        Float f2 = Float.valueOf("350");
        Assert.assertEquals(f1,f2);
    }

    @Test
    public void extract_url(){
        String test= "http://fy.dooioo.org/property/20150623110842180ZRNVPP6PKDGY0EM";
        String url =  test.substring(test.lastIndexOf("/") + 1, test.length());
        Assert.assertEquals(url,"20150623110842180ZRNVPP6PKDGY0EM");

        String propertyId = StringHelper.substringAfterLast(test, "/");
        Assert.assertEquals(propertyId,"20150623110842180ZRNVPP6PKDGY0EM");
    }

    @Test
    public void replace_by_pattern(){
        String t = "{\"contactList\":[{\"contactName\":\"杨\",\"phoneList\"" +
                ":[{\"phoneNumberDecrypt\":\"1381****232\",\"phoneNumber\":\"sJI0eh3MUcfOl5MxomTRCA==\",\"province\":\"上海\",\"city\":\"上海\",\"telExtNumber\":\"77697531\",\"connectedTotal\":0,\"callTotal\":0,\"status\":0}],\"residence\":\"中国\",\"relationShip\":\"业主\",\"status\":\"0\"}],\"code\":0,\"telType\":\"业主\",\"message\":\"可以联系\",\"status\":\"ok\"}";
        Pattern p = Pattern.compile("\"phoneNumber\":\"*\"");
        t=t.replaceAll("(\"phoneNumber\":\"(\\S+)[\",\"p])","");
        System.out.println(t);
    }

    @Test
    public void extract_money(){
        String number="1,000.00 [ 大写 : 壹仟元整 ] ";
        Pattern.compile("^(0|[1-9]\\d{0,11})\\.(\\d\\d)$");
        System.out.println(StringHelper.extract(number,"^(.*)[\\[]"));
    }

    @Test
    public void test_split(){
        String sql1="inqury.source";
        Assert.assertEquals(CollectionsHelper.lastElement(sql1.replaceAll("\\.", "_").split("_")), "source");
    }

    @Test
    public void extractIndex(){
        String index ="orgList(234)";
//        System.out.println(StringHelper.extract(index,"\\[.*?\\]")
//                .replaceAll("\\[","").replaceAll("\\]",""));

        Assert.assertEquals(StringHelper.extract(index, "\\(.*\\)"), "(234)");
    }

    @Test
    public void replace_text(){
        String sql = "select top 1 propertyNo as houseSourceNo,e.estateName , e.Address as estateAddress,e.EstateNo,p.RoomNo,p.PropertyID\n" +
                "        from agencyjizhong..property p with(nolock)\n" +
                "        left join agencyjizhong..Estate e with(nolock) on p.estateID = e.EstateID\n" +
                "        where p.FlagDeleted = 0 and p.saleStatus ='出售'\n" +
                "        and EstateName is not null and e.Address is not null and EstateNo is not null and p.RoomNo is not null\n" +
                "        and not exists(\n" +
                "            select 1 from T_FMS_CASE c with(nolock) where p.propertyNo = c.HOURSE_SOURCE_NO\n" +
                "        )\n" +
                "        and IsHidden=0\n" +
                "        and p.PropertyUsage!='商铺'\n" +
                "        and len(AreaID)>5";

        System.out.println(sql.replaceAll("top (\\d)", "top 3"));
    }

    @Test
    public void test_split_simple(){
        String category="test/test\\yyu";
        System.out.println(category.replace("/","."));
        System.out.println(category.replaceAll("\\\\","."));
        String replaced = category.replaceAll(Pattern.quote(File.separator), Pattern.quote("/"));
        System.out.println(replaced);
    }

    @Test
    public void testRemoveQuestionQuote(){
        String result="http://test.baidu.com?q=test";
        int index =result.indexOf("?");
        System.out.println(index);
        System.out.println(result.substring(0,index));
    }

    @Test
    public void testRemoveQuestionQuote_1(){
        String result="http://test.baidu.com?q={test}";
        System.out.println(result.replaceAll("\\{test\\}","1234"));
    }
}
