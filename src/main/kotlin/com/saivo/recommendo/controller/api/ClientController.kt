package com.saivo.recommendo.controller.api

import com.saivo.recommendo.model.infrastructure.Client
import com.saivo.recommendo.service.ClientService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
class ClientController {

    @Autowired
    val clientService: ClientService? = null

    @ResponseBody
    @GetMapping("/api/clients")
    @PreAuthorize("#oauth2.hasScope('read_all')")
    fun getAllClients(): MutableIterable<Client> = clientService!!.getClients()

    @GetMapping("/api/clients/{clientId}")
    @PreAuthorize("#oauth2.hasScope('read_all')")
    fun getClient(@PathVariable("clientId") clientId: String): Client {
        return clientService!!.getClientById(clientId)
    }

    @PutMapping("/api/clients/{clientId}")
    fun updateClient(@RequestBody client: Client, @PathVariable("clientId") clientId: String): String {
        return clientService!!.saveClient(client, "update")
    }

    @PostMapping("/clients/register")
    fun registerClient(@RequestBody clientSecret: String): String {
        return clientService!!.saveClient(secret =  clientSecret, action = "register")
    }
}