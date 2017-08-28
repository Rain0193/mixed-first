package io.ift.automation;

import io.ift.automation.helpers.RestTemplateClientHelper;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.UnsupportedEncodingException;


/**
 * Created by patrick on 16/5/4.
 */
public class TestLogin {

    @Test
    public void testLogin() throws UnsupportedEncodingException {
//        String body="{\"mobile_phone_no\":\"13764385337\",\"password\":\"13764385337\",\"pic_verify_code\":\"rt56\"}";
//        System.out.println(body);

//        MultiValueMap<String, Object> request = new LinkedMultiValueMap<String, Object>();
//        request.add("mobile_phone_no", "13764385337");
//        request.add("password", "13764385337");
//        request.add("pic_verify_code", "56rt");

//        result = restTemplate.postForObject(url, request, JSONObject.class);
//        ResponseEntity result = RestTemplateClientHelper.defaultInstance().getTemplate().postForObject("https://moapi.lianjia.com/user/account/loginByPasswordV2",
//                request, String.class);

//        String body1 = JSONHelper.toJSONString(request);
        LoginRequest request = new LoginRequest();
        request.setMobile_phone_no("13764385337");
        request.setPassword("13764385337");
        request.setPic_verify_code("56rt");
        ResponseEntity response = RestTemplateClientHelper.defaultInstance()
                .addHeader("Lianjia-App-Secret","0a34a9366d438e6ac5ae3480d024c4ef")
                .addHeader("Lianjia-App-Id","2015091511107")
//                .addHeader("cache-control", "no-cache")
//               .restAndAddHeader("Accept",MediaType.APPLICATION_JSON.toString())
                .setContentType(MediaType.APPLICATION_JSON)
                .post("https://moapi.lianjia.com/user/account/loginByPasswordV2", request);

        System.out.println(response.getBody().toString());

    }

    public static class LoginRequest{
        private String mobile_phone_no;
        private String password;
        private String pic_verify_code;


        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getMobile_phone_no() {
            return mobile_phone_no;
        }

        public void setMobile_phone_no(String mobile_phone_no) {
            this.mobile_phone_no = mobile_phone_no;
        }

        public String getPic_verify_code() {
            return pic_verify_code;
        }

        public void setPic_verify_code(String pic_verify_code) {
            this.pic_verify_code = pic_verify_code;
        }
    }
}
