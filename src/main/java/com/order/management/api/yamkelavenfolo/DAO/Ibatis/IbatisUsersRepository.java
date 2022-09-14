package com.order.management.api.yamkelavenfolo.DAO.Ibatis;

import com.order.management.api.yamkelavenfolo.DAO.Users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface IbatisUsersRepository {
    @Select("SELECT * FROM users WHERE username = #{username}")
    Users selectUserByUsername(String username);
}
