package com.order.management.api.yamkelavenfolo.service;

import com.order.management.api.yamkelavenfolo.DAO.Ibatis.IbatisUsersRepository;
import com.order.management.api.yamkelavenfolo.DAO.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UsersDetailsService implements UserDetailsService {

    @Autowired
    IbatisUsersRepository usersDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Users users = usersDAO.selectUserByUsername(username);

            if (users != null) {
                return new User(users.getUsername(), users.getPassword(), new ArrayList<>());
            }
            throw new UsernameNotFoundException("Username=" + username + " was not found");
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }
}
