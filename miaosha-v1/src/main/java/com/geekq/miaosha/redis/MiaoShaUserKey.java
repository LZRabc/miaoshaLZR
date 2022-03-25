package com.geekq.miaosha.redis;

public class MiaoShaUserKey extends BasePrefix {


    public static final int TOKEN_EXPIRE = 3600*24 * 2;
    public MiaoShaUserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
    public static MiaoShaUserKey token = new MiaoShaUserKey(TOKEN_EXPIRE, "tk");
    public static MiaoShaUserKey getById = new MiaoShaUserKey(0, "id");
    public static MiaoShaUserKey getByNickName = new MiaoShaUserKey(0, "nickName");





}
