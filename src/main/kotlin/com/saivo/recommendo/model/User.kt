package com.saivo.recommendo.model

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.*
import java.util.stream.Collectors



@Entity
@Table(name = "Users")
data class User(
        val email: String,

        @Column(name = "username", nullable = false, unique = true)
        val _username: String,

        val lastname: String,
        val firstname: String,

        @Column(name = "password")
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) val _password: String,

        val enabled: Boolean,
        val credentials_expired: Boolean,
        val account_expired: Boolean,
        val account_locked: Boolean,

        @OneToMany @OrderColumn val roles: Set<Role>,
        @OneToMany @OrderColumn val preferences: Set<Preference>,
        @OneToMany @OrderColumn val recommendations: Set<Recommendation>
) : ModelWithID(),UserDetails {

        override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
                return roles
                        .stream()
                        .map { role -> SimpleGrantedAuthority("ROLE_${role.role_type}" ) }
                        .collect(Collectors.toList())
        }

        override fun isEnabled(): Boolean {
                return enabled
        }

        override fun getUsername(): String {
                return _username
        }

        override fun isCredentialsNonExpired(): Boolean {
                return credentials_expired
        }

        override fun getPassword(): String {
                return _password
        }

        override fun isAccountNonExpired(): Boolean {
                return account_expired
        }

        override fun isAccountNonLocked(): Boolean {
                return account_locked
        }

}