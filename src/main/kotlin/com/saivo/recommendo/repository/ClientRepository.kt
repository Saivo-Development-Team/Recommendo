package com.saivo.recommendo.repository

import com.saivo.recommendo.model.infrastructure.Client
import org.springframework.data.repository.CrudRepository

interface ClientRepository : CrudRepository<Client, String>