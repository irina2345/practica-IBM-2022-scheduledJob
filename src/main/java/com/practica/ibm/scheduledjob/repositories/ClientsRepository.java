package com.practica.ibm.scheduledjob.repositories;

import com.practica.ibm.scheduledjob.models.Client;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientsRepository extends MongoRepository<Client, String> {


}
