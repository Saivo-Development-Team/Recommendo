package com.saivo.recommendo.auth

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.TokenStore


@Configuration
class ResourceSecurityConfiguration : ResourceServerConfigurerAdapter() {

  @Autowired
  private lateinit var tokenStore: TokenStore

  override fun configure(http: HttpSecurity?) {
    http!!.authorizeRequests()
      .antMatchers(HttpMethod.POST, "/clients/register").permitAll()
      .antMatchers("/preference/**").authenticated()
      .antMatchers("/user/**").authenticated()
      .antMatchers("/api/**").authenticated()
      .antMatchers("/**").permitAll()
  }

  override fun configure(resources: ResourceServerSecurityConfigurer) {
    resources.tokenStore(tokenStore).resourceId(System.getenv("RESOURCE_ID"))
  }
}