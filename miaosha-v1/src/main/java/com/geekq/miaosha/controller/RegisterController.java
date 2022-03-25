package com.geekq.miaosha.controller;

import com.alibaba.fastjson.JSON;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.geekq.miaosha.common.resultbean.ResultGeekQ;
import com.geekq.miaosha.service.MiaoShaUserService;
import com.geekq.miaosha.service.MiaoshaService;
import com.geekq.miaosha.utils.SMSSending;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@Controller
//@RequestMapping("/user")
public class RegisterController {

    private static Logger logger = LoggerFactory.getLogger(RegisterController.class);

    @Autowired
    private MiaoShaUserService miaoShaUserService;
    @Autowired
    private MiaoshaService miaoshaService;

    @Autowired
    private HttpServletRequest httpServletRequest;
    @RequestMapping("/do_register")
    public String registerIndex() {
        return "register";
    }

    /**
     * 注册网站
     *
     * @param userName
     * @param passWord
     * @param
     * @return
     */
    ////这里把salt改了
    @RequestMapping("/register")
    @ResponseBody
    public ResultGeekQ<String> register(@RequestParam("username") String userName,
                                        @RequestParam("password") String passWord,
                                        @RequestParam("verificationCode") String verifyCode,
                                        @RequestParam("tel") String tel,
                                        @RequestParam("realName") String realName,
                                        @RequestParam("idNum") String idNum,
                                        HttpServletResponse response) {
        ResultGeekQ<String> result = ResultGeekQ.build();
        //前端这里得传个盐过来
        /**
         * 校验验证码
         */
        String salt="1a2b3c4d";
        String inSessionOtpCode = (String) this.httpServletRequest.getSession().getAttribute("register:"+tel);

        if(!com.alibaba.druid.util.StringUtils.equals(verifyCode,inSessionOtpCode)){
            result.withError(CODE_FAIL.getCode(), CODE_FAIL.getMessage());
            result.setStatus(CODE_FAIL);
            return result;
        }
        this.httpServletRequest.getSession().removeAttribute("register:"+tel);
//        boolean check = miaoshaService.checkVerifyCodeRegister(Integer.valueOf(verifyCode));
//        if (!check) {
//            result.withError(CODE_FAIL.getCode(), CODE_FAIL.getMessage());
//            return result;
//        }
        boolean registerInfo = miaoShaUserService.register(response, userName, passWord,salt,tel,realName,idNum);
        if (!registerInfo) {
            result.withError(RESIGETER_FAIL.getCode(), RESIGETER_FAIL.getMessage());
            result.setStatus(RESIGETER_FAIL);
            return result;
        }
        result.withError(RESIGETR_SUCCESS.getCode(), RESIGETR_SUCCESS.getMessage());
        result.setStatus(RESIGETR_SUCCESS);
        return result;
    }
    //用户获取otp短信接口
    @RequestMapping(value = "/getRegisterVerificationCode",method = {RequestMethod.GET})
    @ResponseBody
    public ResultGeekQ<String> getOtp(@RequestParam(name="tel")String telphone) throws Exception {
        //需要按照一定的规则生成OTP验证码
        Random random = new Random();

        int randomInt =  random.nextInt(8999);
        randomInt += 1000;
        String otpCode = String.valueOf(randomInt);

        //将OTP验证码同对应用户的手机号关联，使用httpsession的方式绑定他的手机号与OTPCODE
        httpServletRequest.getSession().setAttribute("register:"+telphone,otpCode);
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
