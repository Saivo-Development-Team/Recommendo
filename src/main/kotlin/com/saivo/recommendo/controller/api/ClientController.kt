package com.saivo.recommendo.controller.api

import com.saivo.recommendo.model.infrastructure.Client
import com.saivo.recommendo.service.ClientService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
class ClientController {

    @Autowired
    val clientService: ClientService? = null

    @GetMapping("/api/clients")
    fun getAllClients(): MutableIterable<Client> {
        return clientService!!.getClients()
    }

    @GetMapping("/api/clients/{clientId}")
    fun getClient(@PathVariable("clientId") clientId: String): Client {
        return clientService!!.getClientById(clientId)
    }

    @PostMapping("/api/clients")
    fun addClient(@RequestBody client: Client): String {
        return clientService!!.addClient(client)
    }

}