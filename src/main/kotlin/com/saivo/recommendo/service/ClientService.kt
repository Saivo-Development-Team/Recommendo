package com.saivo.recommendo.service

import com.saivo.recommendo.model.infrastructure.Client
import com.saivo.recommendo.repository.ClientRepository
import com.saivo.recommendo.util.createUUID
import com.saivo.recommendo.util.exception.ClientNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ClientService {

    @Autowired
    val clientRepository: ClientRepository? = null

    fun getClientById(clientId: String): Client {
        return clientRepository!!.findById(clientId).orElseThrow {
            ClientNotFoundException()
        }
    }

    fun isClient(clientId: String): Boolean {
        try {
            return getClientById(clientId).clientId!!.isNotEmpty()
        } catch (e: ClientNotFoundException) {
            println(e)
        }
        return false
    }

    fun getClients(): MutableIterable<Client> {
        return clientRepository!!.findAll()
    }

    fun saveClient(client: Client, action: String = "register"): String {
        return clientRepository!!.save(client.apply {
            clientId = createUUID({isClient(clientId!!)}, clientId!!)
        }).clientId!!
    }
}