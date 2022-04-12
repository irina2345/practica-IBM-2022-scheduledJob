package com.practica.ibm.scheduledjob.repositories;

import com.practica.ibm.scheduledjob.models.Settings;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SettingsRepository extends MongoRepository<Settings, String> {

    Optional<Settings> findByAdminEmail(String adminEmail);

}
