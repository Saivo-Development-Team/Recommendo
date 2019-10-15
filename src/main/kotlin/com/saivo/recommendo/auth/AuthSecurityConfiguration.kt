package com.saivo.recommendo.auth

import com.saivo.recommendo.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
class AuthSecurityConfiguration : WebSecurityConfigurerAdapter() {

  @Autowired
  private lateinit var userService: UserService

  @Autowired
  private lateinit var encoder: PasswordEncoder

  @Bean
  override fun authenticationManagerBean(): AuthenticationManager {
    return super.authenticationManagerBean()
  }

  override fun configure(auth: AuthenticationManagerBuilder?) {
    auth?.userDetailsService(userService)?.passwordEncoder(encoder)
  }
}