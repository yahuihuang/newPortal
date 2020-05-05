package com.tssco.userservice;

import com.tssco.userservice.client.AuthServiceClient;
import com.tssco.userservice.dao.UserDao;
import com.tssco.userservice.dto.UserLoginDTO;
import com.tssco.userservice.entity.JWT;
import com.tssco.userservice.entity.User;
import com.tssco.userservice.exception.UserLoginException;
import com.tssco.userservice.utils.BPwdEncoderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceDetail implements UserDetailsService {
    @Autowired
    private UserDao userRepository;

    @Autowired
    AuthServiceClient client;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public User insertUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(BPwdEncoderUtil.BCryptPassword(password));
        return userRepository.save(user);
    }

    public UserLoginDTO login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserLoginException("username錯誤.");
        }

        if (BPwdEncoderUtil.matches(password, user.getPassword()) == false) {
            throw new UserLoginException("password錯誤.");
        }

        JWT jwt=client.getToken("Basic dXNlci1zZXJ2aWNlOjEyMzQ1Ng==","password",username,password);
        if (jwt == null) {
            throw new UserLoginException("取得jwt錯誤.");
        }

        UserLoginDTO userLoginDTO=new UserLoginDTO();
        userLoginDTO.setJwt(jwt);
        userLoginDTO.setUser(user);
        return userLoginDTO;
    }
}
