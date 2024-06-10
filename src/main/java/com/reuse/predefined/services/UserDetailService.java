package com.reuse.predefined.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import com.reuse.predefined.entities.CustomUserDetail;
import com.reuse.predefined.entities.User;
import com.reuse.predefined.exceptions.classes.ResourceNotFoundException;
import com.reuse.predefined.repositories.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("UserDetailServiceImplementation");
        User user = userRepository.findByEmailId(username).get(0);
        if (user == null) {
            throw new ResourceNotFoundException("User with given usename not exist !!");
        }
        log.info("Total time taken ..... " + stopWatch.getTotalTimeSeconds());
        return new CustomUserDetail(user);
    }

}
