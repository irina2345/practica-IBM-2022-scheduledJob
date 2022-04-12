package com.practica.ibm.scheduledjob.models;

import lombok.Getter;
import lombok.Setter;

public class Contract {

    private @Getter @Setter int contractNumber;

    private @Getter @Setter double contractAmount;

    private @Getter @Setter String effectiveFrom;

    private @Getter @Setter int periodInDays;

}
