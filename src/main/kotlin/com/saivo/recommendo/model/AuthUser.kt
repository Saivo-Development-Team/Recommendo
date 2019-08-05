package com.saivo.recommendo.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails


class AuthUser(user: User) : User(user), UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val grants = arrayListOf<GrantedAuthority>()
        super.roles.forEach { role ->
            grants.add(SimpleGrantedAuthority(role.role_type))
        }
        return grants
    }

    override fun isEnabled(): Boolean {
        return super.enabled
    }

    override fun getUsername(): String {
        return super.username
    }

    override fun isCredentialsNonExpired(): Boolean {
        return super.credentialsExpired
    }

    override fun getPassword(): String {
        return super.password
    }

    override fun isAccountNonExpired(): Boolean {
        return super.accountExpired
    }

    override fun isAccountNonLocked(): Boolean {
        return super.accountLocked
    }
}

