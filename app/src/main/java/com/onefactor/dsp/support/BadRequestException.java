package com.onefactor.dsp.support;

import io.belov.soyuz.err.Errors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created on 26.03.17.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BadRequestException extends RuntimeException {
    private Errors errors;
}
