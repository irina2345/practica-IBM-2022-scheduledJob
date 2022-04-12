package com.practica.ibm.scheduledjob;

import com.practica.ibm.scheduledjob.services.ContractsService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ScheduledjobApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(ScheduledjobApplication.class, args);

		ContractsService contractsService = applicationContext.getBean(ContractsService.class);
		contractsService.alertExpiringContracts();

		System.exit(SpringApplication.exit(applicationContext, () -> 0));
	}

}
