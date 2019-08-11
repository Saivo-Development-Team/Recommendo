package com.saivo.recommendo.model.infrastructure

import com.saivo.recommendo.model.domain.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails


class AuthUser(user: User) : User(
        email = user.email,
        roles = user.roles,
        enabled = user.enabled,
        password = user.password,
        lastname = user.lastname,
        firstname = user.firstname,
        preferences = user.preferences,
        accountNotLocked = user.accountNotLocked,
        accountNotExpired = user.accountNotExpired,
        recommendations = user.recommendations,
        credentialsNotExpired = user.credentialsNotExpired
), UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val grants = arrayListOf<GrantedAuthority>()
        super.roles.forEach { role ->
            grants.add(SimpleGrantedAuthority(role.role_type) as GrantedAuthority)
        }
        return grants
    }

    override fun isEnabled(): Boolean {
        return super.enabled
    }

    override fun getUsername(): String {
        return super.email
    }

    override fun isCredentialsNonExpired(): Boolean {
        return super.credentialsNotExpired
    }

    override fun getPassword(): String {
        return super.password
    }

    override fun isAccountNonExpired(): Boolean {
        return super.accountNotExpired
    }

    override fun isAccountNonLocked(): Boolean {
        return super.accountNotLocked
    }
}

