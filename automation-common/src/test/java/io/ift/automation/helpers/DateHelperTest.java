package io.ift.automation.helpers;

import org.apache.commons.lang3.tuple.Triple;
import org.joda.time.DateTime;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateHelperTest {

    @Test
    public void testFormateDate() throws Exception {
        //java.text.ParseException: Unparseable date: "1998-09-03" does not match
        // (\p{Nd}++)\Q-\E(\p{Nd}++)\Q-\E(\p{Nd}++)\QT\E(\p{Nd}++)\Q:\E(\p{Nd}++)\Q:\E(\p{Nd}++)(GMT[+\-]\d{0,1}\
        // d{2}|[+\-]\d{2}:?\d{2}|\QAcre 时间\E|\QACT\E|\QADT\E|\QAFT\E|\QAKDT\E|\QAKST\E|\QAlma-Ata
        // 时间\E|\QALMT\E|\QAMST\E|\QAMT\E|\QANAT\E|\QAqtau 时间\E|\QAqtobe 时间\E|\QAQTT\E|\QART\E|\QAST\E|\QAZOST\E|
        // \QAZOT\E|\QAZST\E|\QAZT\E|\QBDT\E|\QBNT\E|\QBOT\E|\QBRST\E|\QBRT\E|\QBST\E|\QBTT\E|\QCAT\E|\QCCT\E|
        // \QCDT\E|\QCEST\E|\QCET\E|\QCHADT\E|\QChamorro 标准时间\E|\QCHAST\E|\QChoibalsan 时间\E|\QCHOT\E|\QChST\E|
        // \QCHUT\E|\QCKT\E|\QCLST\E|\QCLT\E|\QCOT\E|\QCST\E|\QCVT\E|\QCWST\E|\QCXT\E|\QDAVT\E|\QDDUT\E|\QDumont-d'Urville 时间\E|\QEASST\E|\QEAST\E|\QEAT\E|\QECT\E|\QEDT\E|\QEEST\E|\QEET\E|\QEGST\E|\QEGT\E|\QEST\E|\QFET\E|\QFJST\E|\QFJT\E|\QFKT\E|\QFNT\E|\QGALT\E|\QGAMT\E|\QGET\E|\QGFT\E|\QGILT\E|\QGMT\E|\QGST\E|\QGYT\E|\QHADT\E|\QHAST\E|\QHKT\E|\QHOVT\E|\QHST\E|\QICT\E|\QIDT\E|\QIOT\E|\QIRDT\E|\QIRKT\E|\QIRST\E|\QIST\E|\QJST\E|\QKGT\E|\QKosrae 时间\E|\QKOST\E|\QKRAT\E|\QKST\E|\QLHST\E|\QLine 岛时间\E|\QLINT\E|\QMagadan 时间\E|\QMAGT\E|\QMART\E|\QMAWT\E|\QMDT\E|\QMeST\E|\QMET\E|\QMHT\E|\QMIST\E|\QMMT\E|\QMountain 夏令时\E|\QMountain 标准时间\E|\QMSK\E|\QMST\E|\QMUT\E|\QMVT\E|\QMYT\E|\QNCT\E|\QNDT\E|\QNFT\E|\QNovosibirsk 时间\E|\QNOVT\E|\QNPT\E|\QNRT\E|\QNST\E|\QNUT\E|\QNZDT\E|\QNZST\E|\QOMST\E|\QOral 时间\E|\QORAT\E|\QPDT\E|\QPET\E|\QPETT\E|\QPGT\E|\QPHOT\E|\QPHT\E|\QPKT\E|\QPMDT\E|\QPMST\E|\QPONT\E|\QPST\E|\QPWT\E|\QPYST\E|\QPYT\E|\QQYZT\E|\QQyzylorda 时间\E|\QRET\E|\QROTT\E|\QSAKT\E|\QSAMT\E|\QSAST\E|\QSBT\E|\QSCT\E|\QSGT\E|\QSRT\E|\QSST\E|\QSYOT\E|\QSyowa 时间\E|\QTAHT\E|\QTFT\E|\QTJT\E|\QTKT\E|\QTLT\E|\QTMT\E|\QTOT\E|\QTVT\E|\QULAT\E|\QUTC\E|\QUYST\E|\QUYT\E|\QUZT\E|\QVET\E|\QVLAT\E|\QVOLT\E|\QVOST\E|\QVUT\E|\QWAKT\E|\QWAST\E|\QWAT\E|\QWEST\E|\QWET\E|\QWFT\E|\QWGST\E|\QWGT\E|\QWIB\E|\QWIT\E|\QWITA\E|\QWSDT\E|\QWST\E|\QYAKT\E|\QYekaterinburg 时间\E|\QYEKT\E|\Q不丹时间\E|\Q丘克时间\E|\Q东加时间\E|\Q东印度尼西亚时间\E|\Q东帝汶时间\E|\Q东格林岛夏令时\E|\Q东格林岛时间\E|\Q东欧夏令时\E|\Q东欧时间\E|\Q东部夏令时\E|\Q东部夏令时 (塔斯马尼亚)\E|\Q东部夏令时 (新南威尔斯)\E|\Q东部夏令时 (维多利亚)\E|\Q东部标准时间\E|\Q东部标准时间 (塔斯马尼亚)\E|\Q东部标准时间 (新南威尔斯)\E|\Q东部标准时间 (昆士兰)\E|\Q东部标准时间 (维多利亚)\E|\Q东非时间\E|\Q中国标准时间\E|\Q中央夏令时\E|\Q中央夏令时 (南澳大利亚)\E|\Q中央夏令时 (南澳大利亚/新南威尔斯)\E|\Q中央标准时间\E|\Q中央标准时间 (北领地)\E|\Q中央标准时间 (南澳大利亚)\E|\Q中央标准时间 (南澳大利亚/新南威尔斯)\E|\Q中欧夏令时\E|\Q中欧时间\E|\Q中西部标准时间 (澳大利亚)\E|\Q中部印度尼西亚时间\E|\Q中非时间\E|\Q乌兹别克斯坦时间\E|\Q乌拉圭夏令时\E|\Q乌拉圭时间\E|\Q乌斯季涅拉时间\E|\Q乔治亚时间\E|\Q亚塞拜然夏令时\E|\Q亚塞拜然时间\E|\Q亚库次克时间\E|\Q亚美尼亚时间\E|\Q亚速尔群岛夏令时\E|\Q亚速尔群岛时间\E|\Q亚马逊夏令时\E|\Q亚马逊时间\E|\Q以色列夏令时\E|\Q以色列标准时间\E|\Q伊尔库次克时间\E|\Q伊朗夏令时\E|\Q伊朗标准时间\E|\Q伏尔加格勒时间\E|\Q佛德角时间\E|\Q克拉斯诺亚尔斯克时间\E|\Q冈比亚时间\E|\Q加拉巴哥时间\E|\Q加纳时间\E|\Q协调世界时间\E|\Q南乔治亚标准时间\E|\Q南非标准时间\E|\Q印度支那时间\E|\Q印度标准时间\E|\Q印度洋地带时间\E|\Q厄瓜多尔时间\E|\Q古巴夏令时\E|\Q古巴标准时间\E|\Q可可斯群岛时间\E|\Q吉伯特群岛时间\E|\Q吉尔吉斯斯坦时间\E|\Q吐鲁瓦时间\E|\Q哥伦比亚时间\E|\Q土库曼时间\E|\Q圣诞岛时间\E|\Q圭亚那时间\E|\Q塔吉克斯坦时间\E|\Q塞席尔群岛时间\E|\Q复活岛夏令时\E|\Q复活岛时间\E|\Q夏威夷-阿留申群岛夏令时\E|\Q夏威夷-阿留申群岛标准时间\E|\Q夏威夷标准时间\E|\Q大溪地岛时间\E|\Q大西洋夏令时\E|\Q大西洋标准时间\E|\Q太平洋夏令时\E|\Q太平洋标准时间\E|\Q委内瑞拉时间\E|\Q威克时间\E|\Q孟加拉时间\E|\Q尼泊尔时间\E|\Q巴基斯坦时间\E|\Q巴布亚新几内亚时间\E|\Q巴拉圭夏令时\E|\Q巴拉圭时间\E|\Q巴西利亚夏令时\E|\Q巴西利亚时间\E|\Q帛琉时间\E|\Q库伦时间\E|\Q库克群岛时间\E|\Q库页岛时间\E|\Q彼得罗巴甫洛夫斯克时间\E|\Q戴维斯时间\E|\Q所罗门群岛时间\E|\Q托克劳群岛时间\E|\Q摩里西斯时间\E|\Q文莱时间\E|\Q斐济夏令时\E|\Q斐济时间\E|\Q新加勒多尼亚时间\E|\Q新加坡时间\E|\Q新西兰夏令时\E|\Q新西兰标准时间\E|\Q日本标准时间\E|\Q智利夏令时\E|\Q智利时间\E|\Q查萨姆夏令时\E|\Q查萨姆标准时间\E|\Q格林威治时间\E|\Q梅特拉卡特拉标准时间\E|\Q汉德加时间\E|\Q沙马拉时间\E|\Q法属南极时间\E|\Q法属圭亚那时间\E|\Q波斯湾标准时间\E|\Q波纳佩时间\E|\Q海参崴时间\E|\Q爱尔兰夏令时\E|\Q玻利维亚时间\E|\Q瓦利斯及福杜纳群岛时间\E|\Q瓦奴阿图时间\E|\Q留尼旺岛时间\E|\Q皮埃尔岛及密克隆岛夏令时\E|\Q皮埃尔岛及密克隆岛标准时间\E|\Q皮特康岛标准时间\E|\Q福克兰群岛时间\E|\Q科布多时间\E|\Q秘鲁时间\E|\Q纽威岛时间\E|\Q纽芬兰夏令时\E|\Q纽芬兰标准时间\E|\Q缅甸时间\E|\Q罗瑟拉时间\E|\Q苏利南时间\E|\Q英国夏令时\E|\Q莫斯托克时间\E|\Q莫斯科标准时间\E|\Q莫森时间\E|\Q菲尼克斯群岛时间\E|\Q菲律宾时间\E|\Q萨摩亚群岛标准时间\E|\Q西印度尼西亚时间\E|\Q西格林兰岛夏令时\E|\Q西格林兰岛时间\E|\Q西欧夏令时\E|\Q西欧时间\E|\Q西萨摩亚夏令时\E|\Q西萨摩亚时间\E|\Q西部标准时间 (澳大利亚)\E|\Q西非夏令时\E|\Q西非时间\E|\Q诺福克时间\E|\Q诺鲁时间\E|\Q豪公夏令时\E|\Q豪公标准时间\E|\Q费尔南多德诺罗尼亚时间\E|\Q远东欧时间\E|\Q鄂木斯克时间\E|\Q阿富汗时间\E|\Q阿拉伯标准时间\E|\Q阿拉斯加夏令时\E|\Q阿拉斯加标准时间\E|\Q阿根廷时间\E|\Q阿那底河时间\E|\Q韩国标准时间\E|
        // \Q香港时间\E|\Q马克萨斯时间\E|\Q马尔代夫时间\E|\Q马来西亚时间\E|\Q马绍尔群岛时间\E|\Q麦夸里岛时间\E)
        Date date = new Date();
        date.setTime(1111111100000L);
        Assert.assertEquals(DateHelper.formatDate(date, "yyyy-MM-dd"),"2005-03-18");

    }

    @Test
    public void testConvertFromLong() throws Exception {
        Assert.assertNotNull(DateHelper.convertFromLong(1111111111100L));
    }

    @Test
    public void testIsSameDate() throws Exception {
        Date date1 = new Date();
        Date date2 = new Date();
        Assert.assertTrue(DateHelper.isSameDate(date1, date2));
    }

    @Test
    public void testIsSameDate1() throws Exception {
        Calendar cal = Calendar.getInstance();
        Calendar cal1 = Calendar.getInstance();
        Assert.assertTrue(DateHelper.isSameDate(cal,cal1));
    }

    @Test
    public void testFormatISODateTime() throws Exception {
        Date date = new Date();
        date.setTime(1111111100000L);
        System.out.println(DateHelper.formatISODateTime(date));
        Assert.assertEquals(DateHelper.formatISODateTime(date), "2005-03-18T09:58:20");
    }

    @Test
    public void testFormatISODateTime1() throws Exception {
       Assert.assertEquals(DateHelper.formatISODateTime("2005-03-18T09:58:20"),"2005-03-18T09:58:20");
    }

    @Test
    public void testConvertToDate() throws Exception {
        Date date = DateHelper.convertToDateFormat("2005-03-18T09:58:20");
        System.out.println(date.getDate());
        DateTime t = DateTime.parse("2005-03-18T09:58:20");
        System.out.println(t.dayOfMonth().get());
        System.out.println(t.monthOfYear().get());
        System.out.println(t.year().get());
        System.out.println(DateTime.parse("2005-03-18T09:58:20"));
    }

    @Test
    public void testConvertDateFormat() throws Exception {
        Triple t = DateHelper.convertDate("2005-03-18");
        Assert.assertEquals(t.getLeft().toString(), "2005");
        Assert.assertEquals(t.getMiddle().toString(), "3");
        Assert.assertEquals(t.getRight().toString(), "18");
    }

    @Test
    public void testDateComparison() throws ParseException {
        Date dt1= DateHelper.convertToDateFormat("2012-12-10 00:00:00.0");
        Date dt2= DateHelper.convertToDateFormat("2012-12-10");
        Assert.assertEquals(dt1,dt2);

    }

    @Test
    public void testDateConvert(){
        Triple t= DateHelper.convertDate("2001年12月");
        Assert.assertEquals(t.getLeft(), "2001");
        Assert.assertEquals(t.getMiddle(), "12");
    }

    @Test
    public void testDateConvert_time(){
        Triple t= DateHelper.convertDate("2010-02-04 00:00:00.0");
        Assert.assertEquals(t.getLeft(), "2010");
        Assert.assertEquals(t.getMiddle(), "2");
    }

    @Test
    public void test_format_time(){
        System.out.println(DateHelper.formatDate(new Date(), 30));
    }

    @Test
    public void test_format_time_negative(){
        System.out.println(DateHelper.formatDate(new Date(), -1030));
    }

//    @Test
//    public void test_get_paid_month(){
//        Assert.assertEquals(DateHelper.getLatestPaidMonth(),11);
//    }

    @Test
    public void test_long_to_date() throws ParseException {
        Date date = new Date();
        date.setTime(1454051357780L);
        System.out.println(date);
        System.out.println(DateHelper.formatDate(date, "yyyy-MM-dd hh:mm:ss.SSS"));

        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
        Date d= dateFormat.parse("2016-01-29 15:09:17.780");
        System.out.println(d.getTime());

    }
}
