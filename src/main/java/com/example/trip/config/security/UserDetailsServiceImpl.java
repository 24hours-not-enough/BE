package com.example.trip.config.security;

import com.example.trip.domain.User;
import com.example.trip.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) { this.userRepository = userRepository; }

    public UserDetails loadUserByUsername(String socialaccountId) throws UsernameNotFoundException {
        User user = userRepository.findBySocialaccountId(socialaccountId).orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 사용자입니다."));
        return new UserDetailsImpl(user);
    }
}
