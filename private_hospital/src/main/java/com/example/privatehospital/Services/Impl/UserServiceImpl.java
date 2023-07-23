package com.example.privatehospital.Services.Impl;

import com.example.privatehospital.Services.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new ApplicationUsers(usersService.getByUsrName(s).orElseThrow(() -> new UsernameNotFoundException("Username Not Found")));;
    }
}
