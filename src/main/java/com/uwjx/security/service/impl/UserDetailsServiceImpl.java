package com.uwjx.security.service.impl;

import com.uwjx.security.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private static List<User> users = new ArrayList<>();

    static {
        List<String> roles1 = new ArrayList<>();
        roles1.add("ROLE_ADMIN");
        roles1.add("ROLE_USER");
        List<String> permission1 = new ArrayList<>();
        permission1.add("READ");
        permission1.add("DELETE");

        users.add(new User("user1", "password1", roles1 , permission1));

        List<String> roles2 = new ArrayList<>();
        roles2.add("ROLE_USER");
        List<String> permission2 = new ArrayList<>();
        permission2.add("READ");
        users.add(new User("admin1", "password2", roles2 , permission2));
    }

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.warn("开始检查用户名");
        User user = users.stream()
                .filter(user1 -> user1.getUsername().equals(username)).findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("不存在的用户名：" + username));
        List<GrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
        user.getPermissions().forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission)));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                passwordEncoder.encode(user.getPassword()),
                authorities
        );
    }
}
