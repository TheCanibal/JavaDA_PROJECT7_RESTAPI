package com.nnk.springboot.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.DBUser;
import com.nnk.springboot.services.DBUserService;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private DBUserService userService;

    /**
     * Recover users in database and create a new UserDetails with the details of
     * our users and his authorities
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	DBUser user = userService.findByUsername(username);
	return new User(user.getUsername(), user.getPassword(), getGrantedAuthorities(user.getRole()));
    }

    /**
     * get user's authorities
     * 
     * @param role role of the user
     * @return authorities
     */
    private List<GrantedAuthority> getGrantedAuthorities(String role) {
	List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
	authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
	return authorities;
    }
}
