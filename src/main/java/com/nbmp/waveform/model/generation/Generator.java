/* (C)2024 */
package com.nbmp.waveform.model.generation;

import com.nbmp.waveform.application.AppConstants;

import lombok.Getter;

@Getter
public abstract class Generator {
  public static final int SAMPLE_RATE = AppConstants.SAMPLE_RATE;
  public static double timeStep = 1.0 / SAMPLE_RATE;
}
