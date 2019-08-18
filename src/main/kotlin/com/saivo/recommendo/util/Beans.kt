package com.saivo.recommendo.util

import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.security.oauth2.provider.approval.ApprovalStore
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore
import org.springframework.stereotype.Component
import javax.sql.DataSource
import org.springframework.security.oauth2.provider.token.DefaultTokenServices




@Component
class Beans {

    @Autowired
    private val dataSource: DataSource? = null

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun tokenStore(): TokenStore {
        return JdbcTokenStore(dataSource)
        // Bug -> JdbcTokenStore.readAccessToken [162] EmptyResultDataAccessException is thrown
        // return JdbcTokenStoreBean(dataSource)  -> No RefreshToken Is produced
    }

    @Bean
    fun approvalStore() : ApprovalStore{
        return JdbcApprovalStore(dataSource)
    }

    @Bean
    fun tokenServices(): DefaultTokenServices {
        val defaultTokenServices = DefaultTokenServices()
        defaultTokenServices.setTokenStore(tokenStore())
        defaultTokenServices.setSupportRefreshToken(true)
        return defaultTokenServices
    }
}

class JdbcTokenStoreBean(dataSource: DataSource?): JdbcTokenStore(dataSource){
    override fun readAccessToken(tokenValue: String?): OAuth2AccessToken {
        val log = LogFactory.getLog(JdbcTokenStore::class.java)
        var accessToken: OAuth2AccessToken? = null
        try {
            accessToken = DefaultOAuth2AccessToken(tokenValue)
        } catch (e: EmptyResultDataAccessException) {
            if (log.isInfoEnabled) {
                log.info("Failed to find access token for token $tokenValue")
            }
        } catch (e: IllegalArgumentException) {
            log.warn("Failed to deserialize access token for $tokenValue", e)
            removeAccessToken(tokenValue)
        }
        return accessToken!!
    }
}
