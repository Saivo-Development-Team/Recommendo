package com.saivo.recommendo.repository

import com.saivo.recommendo.model.infrastructure.Client
import org.springframework.data.repository.CrudRepository
import org.springframework.web.bind.annotation.RestController

@RestController
interface ClientRepository : CrudRepository<Client, String>