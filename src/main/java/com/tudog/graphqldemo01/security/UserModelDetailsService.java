package com.tudog.graphqldemo01.security;

import java.util.Optional;

import com.tudog.graphqldemo01.entity.User;
import com.tudog.graphqldemo01.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class UserModelDetailsService implements UserDetailsService {

   private final Logger log = LoggerFactory.getLogger(UserModelDetailsService.class);

   private final UserRepository userRepository;

   public UserModelDetailsService(UserRepository userRepository) {
      this.userRepository = userRepository;
   }

   @Override
   @Transactional
   public UserDetails loadUserByUsername(final String account) {
      log.debug("Authenticating user '{}'", account);

      // return userRepository.findByAccount(account)
      //    .map(user -> createSpringSecurityUser(account, user))
      //    .orElseThrow(() -> new UsernameNotFoundException("User " + account + " was not found in the database"));
      Optional<User> user = userRepository.findByAccount(account);
      UserDetails userDetails = this.createSpringSecurityUser(account, user.get());
      return userDetails;
   }

   private org.springframework.security.core.userdetails.User createSpringSecurityUser(String account, User user) {
      return new org.springframework.security.core.userdetails.User(account,
         user.getPassword(),
         AuthorityUtils.createAuthorityList("ROLE_USER"));
   }
}
