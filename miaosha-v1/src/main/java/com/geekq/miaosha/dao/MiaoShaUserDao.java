package com.geekq.miaosha.dao;

import com.geekq.miaosha.domain.MiaoshaUser;
import org.apache.ibatis.annotations.*;

@Mapper
public interface MiaoShaUserDao {

    @Select("select * from miaosha_user where nickname = #{nickname}")
    public MiaoshaUser getByNickname(@Param("nickname") String nickname);

    @Select("select * from miaosha_user where id = #{id}")
    public MiaoshaUser getById(@Param("id") long id);


    @Update("update miaosha_user set password = #{password},private_key = #{privateKey} where id = #{id}")
    public void update(MiaoshaUser toBeUpdate);


//    @Insert("insert into miaosha_user (id , nickname ,password , salt ,head,register_date,last_login_date)value (#{id},#{nickname},#{password},#{salt},#{head},#{registerDate},#{lastLoginDate}) ")
//    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
//    public void insertMiaoShaUser(MiaoshaUser miaoshaUser);

    @Insert("insert into miaosha_user (private_key,id_num,real_name,id , nickname ,password , salt ,head,register_date,last_login_date)value (#{privateKey},#{idNum},#{realName},#{id},#{nickname},#{password},#{salt},#{head},#{registerDate},#{lastLoginDate}) ")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    public void insertMiaoShaUser(MiaoshaUser miaoshaUser);

}