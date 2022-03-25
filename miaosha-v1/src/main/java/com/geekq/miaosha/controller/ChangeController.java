
package com.geekq.miaosha.controller;

import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.geekq.miaosha.common.resultbean.ResultGeekQ;
import com.geekq.miaosha.service.MiaoShaUserService;
import com.geekq.miaosha.utils.SMSSending;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Random;

import static com.geekq.miaosha.common.enums.ResultStatus.*;


/**
 * @Description:
 * @author: LZR
 * @date: 2022年03月20日 10:56
 */

@Controller
public class ChangeController {
    @Autowired
    private MiaoShaUserService userService;
    @Autowired
    private HttpServletRequest httpServletRequest;

    @RequestMapping("/changePassword")
    @ResponseBody
    public ResultGeekQ<Object> changePassword(HttpServletResponse response,
                                        @RequestParam("newPassword") String passWord,
                                        @RequestParam("verificationCode") String verifyCode,
                                        @RequestParam("tel") String tel) {
        ResultGeekQ<Object> result = ResultGeekQ.build();
        //校验验证码
        String inSessionOtpCode = (String) this.httpServletRequest.getSession().getAttribute("changePassword:"+tel);

        if(!com.alibaba.druid.util.StringUtils.equals(verifyCode,inSessionOtpCode)){
            result.withError(CODE_FAIL.getCode(), CODE_FAIL.getMessage());
            result.setStatus(CODE_FAIL);
            return result;
        }
        this.httpServletRequest.getSession().removeAttribute("changePassword:"+tel);
        String salt="1a2b3c4d";
        boolean changeInfo = userService.changePassword(response,passWord,salt,tel);
        if (!changeInfo) {
            result.withError(CHANGE_FAIL.getCode(), CHANGE_FAIL.getMessage());
            result.setStatus(CHANGE_FAIL);
            return result;
        }
        result.withSuccess(CHANGE_SUCCESS.getCode(), CHANGE_SUCCESS.getMessage());
        result.setStatus(CHANGE_SUCCESS);
        return result;
    }

    //改密码验证码获取接口
    //用户获取otp短信接口
    @RequestMapping(value = "/getChangePasswordVerificationCode",method = {RequestMethod.GET})
    @ResponseBody
    public ResultGeekQ<String> getOtp(@RequestParam(name="tel")String telphone) throws Exception {
        //需要按照一定的规则生成OTP验证码
        Random random = new Random();

        int randomInt =  random.nextInt(8999);
        randomInt += 1000;
        String otpCode = String.valueOf(randomInt);

        //将OTP验证码同对应用户的手机号关联，使用httpsession的方式绑定他的手机号与OTPCODE
        httpServletRequest.getSession().setAttribute("changePassword:"+telphone,otpCode);
        //设置有效期五分钟
        httpServletRequest.getSession().setMaxInactiveInterval(300);

        //将OTP验证码通过短信通道发送给用户,省略
        System.out.println("telphone = " + telphone + " & otpCode = "+otpCode);



        com.aliyun.dysmsapi20170525.Client client = SMSSending.createClient();
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setSignName("阿里云短信测试")
                .setTemplateCode("SMS_154950909")
                .setPhoneNumbers(telphone)
                .setTemplateParam("{\"code\":\""+otpCode+"\"}");
        // 复制代码运行请自行打印 API 的返回值
        SendSmsResponse response =  client.sendSms(sendSmsRequest);
        System.out.println(response.body.getCode());
        System.out.println(response.body.getMessage());
        ResultGeekQ<String> result = ResultGeekQ.build();
        result.setData(response.getBody().getMessage());
        return result;
    }

}

