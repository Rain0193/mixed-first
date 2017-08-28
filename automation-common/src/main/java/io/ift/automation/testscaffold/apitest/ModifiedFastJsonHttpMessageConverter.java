package io.ift.automation.testscaffold.apitest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

import io.ift.automation.logging.TestResultLogger;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

public class ModifiedFastJsonHttpMessageConverter extends AbstractHttpMessageConverter<Object> {
    public static final Charset UTF8 = Charset.forName("UTF-8");
    private Charset charset;
    private SerializerFeature[] features;

    public ModifiedFastJsonHttpMessageConverter() {
        super(new MediaType("application", "json", UTF8), new MediaType("application", "*+json", UTF8));
        this.charset = UTF8;
        this.features = new SerializerFeature[]{SerializerFeature.WriteMapNullValue,SerializerFeature.WriteSlashAsSpecial};
    }

    protected boolean supports(Class<?> clazz) {
        return true;
    }

    public Charset getCharset() {
        return this.charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
//        TestResultLogger.log("start read input message:");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        InputStream in = inputMessage.getBody();
        byte[] buf = new byte[1024];

        while(true) {
            int bytes = in.read(buf);
            if(bytes == -1) {
                byte[] bytes1 = baos.toByteArray();
                if(clazz.equals(String.class)) return baos.toString("UTF-8");
                try {
                    return JSON.parseObject(bytes1, 0, bytes1.length, this.charset.newDecoder(), clazz);
                }catch (Exception e){
                    TestResultLogger.error("读取返回值出现错误,e={}",e);
                    return baos.toString("UTF-8");
                }
            }

            if(bytes > 0) {
                baos.write(buf, 0, bytes);
            }
        }
    }

    protected void writeInternal(Object obj, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
//        TestResultLogger.log("start read out message:");
        OutputStream out = outputMessage.getBody();
       String text = JSON.toJSONString(obj,features);
        byte[] bytes = text.getBytes(this.charset);
        out.write(bytes);
    }
}
