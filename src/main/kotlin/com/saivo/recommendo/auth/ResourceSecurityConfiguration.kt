package com.saivo.recommendo.auth

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.TokenStore


@Configuration
class ResourceSecurityConfiguration : ResourceServerConfigurerAdapter() {

    @Autowired
    private val tokenStore: TokenStore? = null

    override fun configure(http: HttpSecurity?) {
        http!!.authorizeRequests()
                .antMatchers("/api/**").authenticated()
                .antMatchers("/**").permitAll()
    }

    override fun configure(resources: ResourceServerSecurityConfigurer) {
        resources.tokenStore(tokenStore).resourceId(System.getenv("RESOURCE_ID"))
    }
}