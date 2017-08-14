package com.onefactor.dsp.auth;

import io.belov.soyuz.bus.GuavaBusEvent;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Created on 14.02.17.
 */
@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class UserAuthenticatedEvent implements GuavaBusEvent {

    private String mail;

}
