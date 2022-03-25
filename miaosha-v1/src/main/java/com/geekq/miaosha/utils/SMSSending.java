package com.geekq.miaosha.utils;/*-------------------------------------------------------------------------
 * Name:   哈夫曼编码源
 * Date:   2022/3/17
 * Author: Ingrid
 *------------------------------------------------------------------------*/

/**
 * @Description:
 * @author: LZR
 * @date: 2022年03月17日 10:48
 */
// This file is auto-generated, don't edit it. Thanks.


import com.aliyun.tea.*;
import com.aliyun.dysmsapi20170525.*;
import com.aliyun.dysmsapi20170525.models.*;
import com.aliyun.teaopenapi.*;
import com.aliyun.teaopenapi.models.*;

public class SMSSending {

    /**
     * 使用AK&SK初始化账号Client
     * @param
     * @param
     * @return Client
     * @throws Exception
     */
    public static com.aliyun.dysmsapi20170525.Client createClient() throws Exception {
        String accessKeyId="LTAI5tRynS34rVkVmRQhuEfe";
        String accessKeySecret="jR3FudxCUD8Y43ze8JEbK3bRh1yCC4";
        Config config = new Config()
                // 您的AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 您的AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = "dysmsapi.aliyuncs.com";
        return new com.aliyun.dysmsapi20170525.Client(config);
    }

    public static void main(String[] args_) throws Exception {
        java.util.List<String> args = java.util.Arrays.asList(args_);
        com.aliyun.dysmsapi20170525.Client client = SMSSending.createClient();
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setSignName("阿里云短信测试")
                .setTemplateCode("SMS_154950909")
                .setPhoneNumbers("19198256317")
                .setTemplateParam("{\"code\":\"1234\"}");
        // 复制代码运行请自行打印 API 的返回值
        client.sendSms(sendSmsRequest);
    }
}


