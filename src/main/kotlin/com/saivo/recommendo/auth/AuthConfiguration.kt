package com.saivo.recommendo.auth

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.TokenStore
import javax.sql.DataSource

@Configuration
class AuthConfiguration: AuthorizationServerConfigurerAdapter() {

    @Autowired
    private val dataSource: DataSource? = null

    @Autowired
    private val tokenStore: TokenStore? = null

    @Autowired
    private val encoder: PasswordEncoder? = null

    @Autowired
    private val authenticationManager: AuthenticationManager? = null

    override fun configure(security: AuthorizationServerSecurityConfigurer?) {
        security!!.checkTokenAccess("isAuthenticated()")
    }

    override fun configure(clients: ClientDetailsServiceConfigurer?) {
        clients!!.jdbc(dataSource)
                .passwordEncoder(encoder)
                .withClient("oauth2-client")
                .secret(encoder!!.encode("oauth-secret"))
                .authorizedGrantTypes("password")
                .scopes("all")
    }

    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer?) {
        endpoints!!.tokenStore(tokenStore).authenticationManager(authenticationManager)
    }
}