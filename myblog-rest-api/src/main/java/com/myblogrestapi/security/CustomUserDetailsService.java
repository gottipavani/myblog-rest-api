package com.myblogrestapi.security;

import com.myblogrestapi.entity.Roles;
import com.myblogrestapi.entity.Users;
import com.myblogrestapi.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service

public class CustomUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        Users user = userRepository.findByUsernameOrEmail(usernameOrEmail , usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("user not found with username :" + usernameOrEmail));

        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(), mapRolesToAuthorities(user.getRoless()));
    }

    private Collection< ? extends GrantedAuthority> mapRolesToAuthorities(Set<Roles> roless){
        return roless.stream().map(roles ->new SimpleGrantedAuthority(roles.getName())).collect(Collectors.toList());
    }
}
