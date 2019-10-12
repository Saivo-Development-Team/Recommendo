package com.saivo.recommendo.controller.api

import com.saivo.recommendo.model.infrastructure.Client
import com.saivo.recommendo.model.objects.Response
import com.saivo.recommendo.service.ClientService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
class ClientController {

  @Autowired
  lateinit var clientService: ClientService

  @ResponseBody
  @GetMapping("/api/clients")
  @PreAuthorize("#oauth2.hasScope('read_all')")
  fun getAllClients(): MutableIterable<Client> = clientService.getClients()

  @GetMapping("/api/clients/{clientId}")
  @PreAuthorize("#oauth2.hasScope('read_all')")
  fun getClient(@PathVariable("clientId") clientId: String) = clientService.getClientById(clientId)

  @PutMapping("/clients/update")
  fun updateClient(@RequestBody client: Client) = clientService.saveClient(client, action = "UPDATE")

  @PostMapping("/clients/register")
  fun registerClient(@RequestBody clientSecret: String) = clientService.saveClient(secret = clientSecret, action = "REGISTER")
}