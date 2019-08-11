package com.saivo.recommendo.model.infrastructure

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "oauth_client_details")
class Client(
        @Id @Column(name = "client_id")
        var clientId: String? = null,

        @Column(name = "authorities")
        var authorities: String? = null,

        @Column(name = "access_token_validity")
        var accessTokenValidity: Long? = null,

        @Column(name = "autoapprove")
        var autoapprove: String? = null,

        @Column(name = "additional_information")
        var additionalInformation: String? = null,

        @Column(name = "refresh_token_validity")
        var refreshTokenValidity: Long? = null,

        @Column(name = "web_server_redirect_uri")
        var webServerRedirectUri: String? = null,

        @Column(name = "resource_ids")
        var resourceIds: String? = null,

        @Column(name = "client_secret")
        var clientSecret: String? = null,

        @Column(name = "scope")
        var scope: String? = null,

        @Column(name = "authorized_grant_types")
        var authorizedGrantTypes: String? = null
)
