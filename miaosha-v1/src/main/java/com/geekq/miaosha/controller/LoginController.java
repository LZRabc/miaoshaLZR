package com.geekq.miaosha.controller;


import com.geekq.miaosha.common.enums.ResultStatus;
import com.geekq.miaosha.common.resultbean.ResultGeekQ;
import com.geekq.miaosha.domain.MiaoshaUser;
import com.geekq.miaosha.redis.redismanager.RedisLua;
import com.geekq.miaosha.service.MiaoShaUserService;
import com.geekq.miaosha.utils.ValidatorUtil;
import com.geekq.miaosha.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static com.geekq.miaosha.common.Constanst.COUNTLOGIN;
import static com.geekq.miaosha.common.enums.ResultStatus.CHANGE_SUCCESS;
import static com.geekq.miaosha.redis.MiaoShaUserKey.getByNickName;
import com.geekq.miaosha.pojo.*;
@Controller
//@RequestMapping("/login")
public class LoginController {

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private MiaoShaUserService userService;






    @RequestMapping("/create_token")
    @ResponseBody
    public String createToken(HttpServletResponse response, @Valid LoginVo loginVo) {
        logger.info(loginVo.toString());
        String token = userService.createToken(response, loginVo);
        return token;
    }



    @RequestMapping("/login")
    @ResponseBody
    public ResultGeekQ<loginPojo> dologinL(HttpServletResponse response, @Valid LoginVo loginVo) {
        ResultGeekQ<loginPojo> result = ResultGeekQ.build();
        logger.info(loginVo.toString());
        boolean temp=ValidatorUtil.isMobile(loginVo.getUsername());
        if(temp){
            MiaoshaUser user = userService.getById(Long.parseLong(loginVo.getUsername()));
            Boolean loginInfo=userService.login(response, loginVo,1);
            if(!loginInfo){
                result.withError(ResultStatus.FAIL.getCode(), ResultStatus.FAIL.getMessage());
                result.setStatus(ResultStatus.FAIL);
                return result;
            }
            loginPojo data=new loginPojo();
            data.setToken(createToken(response, loginVo));
            data.setRealName(user.getRealName());
            data.setUsername(user.getNickname());
            result.setData(data);
            result.withSuccess(ResultStatus.SUCCESS.getCode(), ResultStatus.SUCCESS.getMessage());
            result.setStatus(ResultStatus.SUCCESS);
            return result;
        }
        else {
            MiaoshaUser user = userService.getByNickName(loginVo.getUsername());
            Boolean loginInfo =userService.login(response, loginVo,2);
            if(!loginInfo){
                result.withError(ResultStatus.FAIL.getCode(), ResultStatus.FAIL.getMessage());
                result.setStatus(ResultStatus.FAIL);
                return result;
            }
            loginPojo data=new loginPojo();
            data.setToken(createToken(response, loginVo));
            data.setRealName(user.getRealName());
            data.setUsername(user.getNickname());
            result.setData(data);
            result.withSuccess(ResultStatus.SUCCESS.getCode(), ResultStatus.SUCCESS.getMessage());
            result.setStatus(ResultStatus.SUCCESS);
            return result;
        }
    }
}
