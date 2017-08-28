package io.ift.automation.commonflows.base.api;

import io.ift.automation.testscaffold.apitest.BaseJsonEntity;

/**
 * Created by patrick on 15/4/23.
 *  Base Response :
 *
 /*code	status	描述
 0	OK	操作成功
 1	REQUEST_TIMEOUT	请求超时
 2	FORBIDDEN	禁止操作
 3	UNAUTHORIZED	没有授权或者权限不够
 4	NOT_FOUND	资源不存在
 5	ALREADY_EXISTS	资源已经存在,违反唯一性约束
 6	FIELD_INVALID	字段不合法,具体见详细错误信息
 7	BAD_REQUEST	请求不合法,具体见详细错误信息
 -1	INTERNAL_SERVER_ERROR	服务器内部错误,具体见详细错误信息
 * @version $Id$
 */


public class OpenAPIBaseResponse extends BaseJsonEntity{

    private String status;
    private String code;
    private String message;

    public OpenAPIBaseResponse(String status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public OpenAPIBaseResponse() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static OpenAPIBaseResponse OK(){
        return new OpenAPIBaseResponse("0","OK","操作成功");
    }

    public static OpenAPIBaseResponse REQUEST_TIMEOUT(){
        return new OpenAPIBaseResponse("1","REQUEST_TIMEOUT","请求超时");
    }
    public static OpenAPIBaseResponse FORBIDDEN(){
        return new OpenAPIBaseResponse("2","FORBIDDEN","禁止操作");
    }
    public static OpenAPIBaseResponse UNAUTHORIZED(){
        return new OpenAPIBaseResponse("3","UNAUTHORIZED","没有授权或者权限不够");
    }
    public static OpenAPIBaseResponse NOT_FOUND(){
        return new OpenAPIBaseResponse("4","NOT_FOUND","资源不存在");
    }
    public static OpenAPIBaseResponse ALREADY_EXISTS(){
        return new OpenAPIBaseResponse("5","ALREADY_EXISTS","资源已经存在,违反唯一性约束");
    }
    public static OpenAPIBaseResponse FIELD_INVALID(){
        return new OpenAPIBaseResponse("6","FIELD_INVALID","字段不合法,具体见详细错误信息");
    }
    public static OpenAPIBaseResponse BAD_REQUEST(){
        return new OpenAPIBaseResponse("7","BAD_REQUEST","请求不合法,具体见详细错误信息");
    }
    public static OpenAPIBaseResponse INTERNAL_SERVER_ERROR(){
        return new OpenAPIBaseResponse("-1","INTERNAL_SERVER_ERROR","服务器内部错误,具体见详细错误信息");
    }

    public static OpenAPIBaseResponse builder(String code){
       if("OK".equalsIgnoreCase(code)){
           return OK();
       }

        if("REQUEST_TIMEOUT".equalsIgnoreCase(code)){
            return REQUEST_TIMEOUT();
        }
        if("FORBIDDEN".equalsIgnoreCase(code)){
            return FORBIDDEN();
        }
        if("UNAUTHORIZED".equalsIgnoreCase(code)){
            return UNAUTHORIZED();
        }
        if("NOT_FOUND".equalsIgnoreCase(code)){
            return NOT_FOUND();
        }
        if("ALREADY_EXISTS".equalsIgnoreCase(code)){
            return ALREADY_EXISTS();
        }
        if("FIELD_INVALID".equalsIgnoreCase(code)){
            return FIELD_INVALID();
        }
        if("BAD_REQUEST".equalsIgnoreCase(code)){
            return BAD_REQUEST();
        }
        if("INTERNAL_SERVER_ERROR".equalsIgnoreCase(code)){
            return INTERNAL_SERVER_ERROR();
        }
        return OK();
    }



//    public static void main(String[] args) {
//        String spec ="0\tOK\t操作成功\n" +
//                "1\tREQUEST_TIMEOUT\t请求超时\n" +
//                "2\tFORBIDDEN\t禁止操作\n" +
//                "3\tUNAUTHORIZED\t没有授权或者权限不够\n" +
//                "4\tNOT_FOUND\t资源不存在\n" +
//                "5\tALREADY_EXISTS\t资源已经存在,违反唯一性约束\n" +
//                "6\tFIELD_INVALID\t字段不合法,具体见详细错误信息\n" +
//                "7\tBAD_REQUEST\t请求不合法,具体见详细错误信息\n" +
//                "-1\tINTERNAL_SERVER_ERROR\t服务器内部错误,具体见详细错误信息";
//        String[] temp = spec.split("\n");
//        for (String s : temp) {
//            String[] parameters = s.split("\t");
//            System.out.println("public static OpenAPIBaseResponse "+parameters[1]+"(){");
//            System.out.println(String.format("return new OpenAPIBaseResponse(\"%s\",\"%s\",\"%s\");"
//                    ,parameters[0],parameters[1],parameters[2]));
//            System.out.println("}");
//        }
//
//        for (String s : temp) {
//            String[] parameters = s.split("\t");
//            System.out.println(String.format("if(\"%s\".equalsIgnoreCase(code)){\n" +
//                    "           return %s();\n" +
//                    "       } ",parameters[1],parameters[1]));
//        }
//
//    }
}
