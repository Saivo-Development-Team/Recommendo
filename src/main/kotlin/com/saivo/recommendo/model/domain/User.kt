package com.saivo.recommendo.model.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.saivo.recommendo.model.infrastructure.Role
import com.saivo.recommendo.model.objects.ProfileImage
import org.hibernate.annotations.GenericGenerator
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.io.Serializable
import javax.persistence.*
import javax.persistence.CascadeType.ALL


@Entity
@Table(name = "users")
data class User(
  @Id
  @GeneratedValue(generator = "pg-uuid", strategy = GenerationType.AUTO)
  @GenericGenerator(name = "pg-uuid", strategy = "uuid2")
  val id: String? = "",
  @Column(name = "email", nullable = false)
  val email: String,
  val enabled: Boolean,
  val lastname: String,
  val firstname: String,
  val phoneNumber: String? = null,
  val profileImageLink: String? = null,
  val accountNotLocked: Boolean,
  val accountNotExpired: Boolean,
  val credentialsNotExpired: Boolean,

  @Column(name = "password", nullable = false)
  @JsonProperty("password", access = JsonProperty.Access.WRITE_ONLY)
  val userPassword: String,

  @JsonIgnore
  @OneToOne(targetEntity = ProfileImage::class)
  val profileImage: ProfileImage? = null,

  @OrderColumn
  @OneToMany(fetch = FetchType.EAGER, cascade = [ALL], targetEntity = Role::class)
  val roles: Set<Role>? = setOf(),

  @OrderColumn
  @ManyToMany(fetch = FetchType.EAGER, cascade = [ALL], targetEntity = Preference::class)
  val preferences: Set<Preference>? = setOf(),

  @OrderColumn
  @ManyToMany(fetch = FetchType.EAGER, cascade = [ALL], targetEntity = Recommendation::class)
  val recommendations: Set<Recommendation>? = setOf()
) : UserDetails, Serializable {

  @JsonIgnore
  override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
    val grants = arrayListOf<GrantedAuthority>()
    this.roles?.forEach {
      grants += SimpleGrantedAuthority(it.role_type) as GrantedAuthority
    }
    return grants
  }

  @JsonIgnore
  override fun isEnabled(): Boolean {
    return this.enabled
  }

  @JsonIgnore
  override fun getUsername(): String {
    return this.email
  }

  @JsonIgnore
  override fun isCredentialsNonExpired(): Boolean {
    return this.credentialsNotExpired
  }

  @JsonIgnore
  override fun getPassword(): String {
    return this.userPassword
  }

  @JsonIgnore
  override fun isAccountNonExpired(): Boolean {
    return this.accountNotExpired
  }

  @JsonIgnore
  override fun isAccountNonLocked(): Boolean {
    return this.accountNotLocked
  }

  @JsonIgnore
  fun encode(function: (String) -> String): User {
    return this.copy(userPassword = function(userPassword.replace('"', ' ').trim()))
  }

  fun updated(user: User, data: Pair<String, Any>? = null): User {
    return this.copy(
      lastname = user.lastname,
      firstname = user.firstname,
      phoneNumber = user.phoneNumber,
      profileImageLink = user.profileImageLink,
      accountNotLocked = user.accountNotLocked,
      accountNotExpired = user.accountNotExpired,
      credentialsNotExpired = user.credentialsNotExpired,
      roles = user.roles,
      preferences = user.preferences,
      recommendations = user.recommendations
    )
  }

}