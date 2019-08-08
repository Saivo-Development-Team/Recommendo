package com.saivo.recommendo.auth

import com.saivo.recommendo.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.oauth2.provider.approval.ApprovalStore
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
    private val approval: ApprovalStore? = null

    @Autowired
    private val userService: UserService? = null

    @Autowired
    private val authenticationManager: AuthenticationManager? = null

    override fun configure(security: AuthorizationServerSecurityConfigurer?) {
        security!!.checkTokenAccess("isAuthenticated()")
                .tokenKeyAccess("permitAll")
                .passwordEncoder(encoder)
    }

    override fun configure(clients: ClientDetailsServiceConfigurer?) {
        clients!!.jdbc(dataSource).passwordEncoder(encoder)
    }

    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer?) {
        endpoints!!.tokenStore(tokenStore)
                .authenticationManager(authenticationManager)
                .userDetailsService(userService)
                .approvalStore(approval)
    }
}