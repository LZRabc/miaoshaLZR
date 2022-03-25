package com.geekq.miaosha.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MiaoshaUser {
    //手机号码
    private Long id;
    //就是username
    private String nickname;
    private String password;

    private String realName;
    private String idNum;
    private String salt;

    //下面的不认识
    private String head;
    private Date registerDate;
    private Date lastLoginDate;
    private Integer loginCount;

    //加密相关信息
    private String privateKey;



    @Override
    public String toString() {
        return "Logininfo{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", head='" + head + '\'' +
                ", registerDate=" + registerDate +
                ", lastLoginDate=" + lastLoginDate +
                ", loginCount=" + loginCount +
                '}';
    }
}
