package com.practica.ibm.scheduledjob.services;

import com.practica.ibm.scheduledjob.models.Client;
import com.practica.ibm.scheduledjob.models.Contract;
import com.practica.ibm.scheduledjob.models.Settings;
import com.practica.ibm.scheduledjob.repositories.ClientsRepository;
import com.practica.ibm.scheduledjob.repositories.SettingsRepository;
import com.practica.ibm.scheduledjob.util.AlertingMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ContractsService {

    @Autowired
    ClientsRepository clientsRepository;

    @Autowired
    SettingsRepository settingsRepository;

    @Autowired
    AlertingService alertingService;

    public void alertExpiringContracts() {
        List<Client> clients = clientsRepository.findAll();

        for (Client client : clients) {
            Optional<Settings> settingsOptional = settingsRepository.findByAdminEmail(client.getAdminEmail());
            if (settingsOptional.isPresent()) {
                for (Contract contract : client.getContracts()) {
                    if (willExpire(contract, settingsOptional.get().getDaysBeforeAlerting())) {
                        AlertingMethods alertingMethod = settingsOptional.get().getAlertingMethod();
                        switch (alertingMethod) {
                            case EMAIL: {
                                alertingService.sendEmailAlert(
                                        client.getAdminEmail(), contract.getContractNumber(), client.getName());
                                break;
                            }
                            case SMS: {
                                alertingService.sendSMSAlert();
                                break;
                            }
                            default: {
                                System.out.println("Alerting method " + alertingMethod + " unrecognised.");
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean willExpire(Contract contract, int daysToExpiration) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        try {
            Date contractBeginingDate = simpleDateFormat.parse(contract.getEffectiveFrom());
            Instant now = new Date().toInstant();
            Instant expirationInstant = contractBeginingDate.toInstant().plus(contract.getPeriodInDays(), ChronoUnit.DAYS);
            Instant alertInstant = expirationInstant.minus(daysToExpiration, ChronoUnit.DAYS);

            return now.isAfter(alertInstant);
        } catch (ParseException parseException) {
            System.out.println("Failed to parse contract beginning date: " + contract.getEffectiveFrom());
        }
        return false;
    }

}
