/* (C)2024 */
package com.nbmp.waveform.guides;

import javafx.scene.chart.XYChart;

import com.nbmp.waveform.generation.BufferedSeries;
import com.nbmp.waveform.generation.EfficientWaveGeneration;
import com.nbmp.waveform.generation.Generator;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Guide {
  protected EfficientWaveGeneration generator;
  protected XYChart.Series<Number, Number> series;
  protected boolean isInteractive = false;
  protected double currentValue = Double.NEGATIVE_INFINITY;
  protected final BufferedSeries<Number, Number> buffer;

  public Guide() {
    this("Guide");
  }

  public Guide(String name) {
    // 100 ms of buffer
    this(name, (int) Generator.SAMPLE_RATE / 10);
  }

  public Guide(String name, int bufferSize) {
    series = new XYChart.Series<>();
    series.setName(name);
    buffer = new BufferedSeries<>(bufferSize, series);
  }

  abstract Double compute(Double t, Double timeStep);

  public void addPoint(Double t, Double timeStep) {
    currentValue = compute(t, timeStep);
    buffer.addPoint(t, currentValue);
  }

  public void bindForRegeneration(EfficientWaveGeneration generator) {
    this.generator = generator;
    this.isInteractive = true;
  }

  //  public void regenerateSeries() {
  //    if (isInteractive) {
  //      this.series.getData().clear();
  //      this.generator.regenerate();
  //    }
  //  }

  protected void setupOptions(GuideOptions[] options) {
    for (GuideOptions option : options) {
      switch (option) {
        case REGENERATION -> this.isInteractive = true;
      }
    }
  }
}
