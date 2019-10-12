package com.saivo.recommendo.service

import com.saivo.recommendo.model.infrastructure.Client
import com.saivo.recommendo.model.objects.Response
import com.saivo.recommendo.repository.ClientRepository
import com.saivo.recommendo.util.createUUID
import com.saivo.recommendo.util.exception.ClientNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class ClientService {

  @Autowired
  private lateinit var clientRepository: ClientRepository

  @Autowired
  private lateinit var encoder: PasswordEncoder

  fun getClientById(clientId: String): Client = clientRepository.findById(clientId).orElseThrow {
    ClientNotFoundException()
  }

  fun isClient(clientId: String): Boolean {
    runCatching {
      getClientById(clientId).clientId!!.isNotEmpty()
    }.onSuccess {
      return it
    }
    return false
  }

  fun getClients(): MutableIterable<Client> {
    return clientRepository.findAll()
  }

  fun saveClient(client: Client? = null, secret: String = "", action: String = ""): Response {
    when (action) {
      "REGISTER" -> {
        return Response(data = registerClient(secret))
      }
      "UPDATE" -> {
        client?.let {
          return Response(data = updateClient(it))
        }
      }
    }
    return Response()
  }

  fun registerClient(secret: String): String? {
    return clientRepository.save(Client().apply {
      scope = "register"
      clientId = UUID.randomUUID().toString().takeIf { !isClient(it) }
      clientSecret = encoder.encode(secret.filter { it != '"' })
      resourceIds = System.getenv("RESOURCE_ID")
      accessTokenValidity = 3600
      refreshTokenValidity = 3600
      authorizedGrantTypes = "client_credentials"
    }).clientId
  }

  fun updateClient(client: Client): String? {
    return clientRepository.save(getClientById(client.clientId!!).apply {
      scope += ",${client.scope}"
      resourceIds += ",${client.resourceIds}"
      accessTokenValidity = client.accessTokenValidity
      refreshTokenValidity = client.refreshTokenValidity
      authorizedGrantTypes += ",${client.authorizedGrantTypes}"
    }).clientId
  }
}