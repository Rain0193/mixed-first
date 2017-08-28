package io.ift.automation.helpers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.UnsupportedEncodingException;


public class RestTemplateClientHelperTest {

    @Test
    public void testHttpRequest() throws UnsupportedEncodingException {


        RestTemplateClientHelper clientHelper = RestTemplateClientHelper.getHttpClientImplInstance();
        String urlLogin = "http://open.dooioo.org/login?username=admin&password=1";
        ResponseEntity response = clientHelper.call(urlLogin, HttpMethod.POST);
        response= clientHelper.call(
                java.net.URLDecoder.decode("http://open.dooioo.org/rest/docs?module=%E5%AE%A2%E6%BA%90API&api=%E5%AE%A2%E6%BA%90-%E5%B8%A6%E7%9C%8B%E5%88%97%E8%A1%A8&version=v5", "UTF-8"),HttpMethod.GET);

        response= clientHelper.call("http://open.dooioo.org/rest/docs?module=客源API&api=客源-带看列表&version=v5",HttpMethod.GET);
        System.out.println(response.getBody().toString());
        Assert.assertEquals(response.getStatusCode().is2xxSuccessful(),true);
//        System.out.println(response.getBody().toString());
//
////        String url = "http://10.8.1.105:7070/index";
//        RestTemplateClientHelper helper = RestTemplateClientHelper.getHttpClientImplInstance();
////        ResponseEntity response = helper.call(url, HttpMethod.GET);
////        helper.addHeader("Set-Cookie",response.getHeaders().get("Set-Cookie"));
//        //String url2= "http://login.dooioo.org/login?userCode=80848&password=1288583357";
//        String url2= "http://login.dooioo.org/login?usercode=110863&password=PW_123456";
//        helper.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
////        MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
////        map.add("userCode", "80848");
////        map.add("password", "1288583357");
//        //map.add("ref", "http://blog.dooioo.org/");
//
//        ResponseEntity login = helper.call(url2, HttpMethod.POST);
//        System.out.println(login.getBody().toString());
////        JSONObject o = (JSONObject)JSON.parse(login.getBody().toString());
////        String loginAt= o.getString("loginAt");
////        String version=  o.getString("version");
////        String token = o.getString("message");
////        helper.addHeader("Authorization",token);
////        helper.addHeader("loginAt",loginAt);
////        helper.addHeader("version",version);
////        System.out.println(login);
////        String url3 = "http://10.8.1.105:7070/signedReservation/taskCount";
////
////        ResponseEntity taskAccount1 = helper.call(url3,HttpMethod.GET);
////        System.out.println(taskAccount1);
////        String url4= "http://10.8.1.105:7070/signedReservation/changeSignedEmp?" +
////                "signerOrgId=20069&" +
////                "signerEmpNo=81267&id=4830&orderId=53692";
////        helper.getHeaders().setContentType(MediaType.APPLICATION_FORM_URLENCODED);
////        ResponseEntity taskAccount = helper.call(url4,HttpMethod.POST);
////
////        System.out.println(taskAccount);
    }

}
