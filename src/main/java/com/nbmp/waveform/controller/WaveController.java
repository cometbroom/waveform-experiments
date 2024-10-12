/* (C)2024 */
package com.nbmp.waveform.controller;

import java.util.LinkedList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;

import org.springframework.stereotype.Component;

import com.nbmp.waveform.model.dto.WavePropsSliders;
import com.nbmp.waveform.model.generation.ChaosSynthesis;
import com.nbmp.waveform.model.generation.IndependentSynthesis;
import com.nbmp.waveform.model.generation.Synthesis;
import com.nbmp.waveform.view.WavesRegister;

@Component
public class WaveController {
  public Label statusLabel;
  public LineChart<Number, Number> resultWaveformChart;
  @FXML public ComboBox<String> synthesisMode;
  @FXML private Slider frequencySlider;
  @FXML public Slider frequencySlider2;
  @FXML private Label sliderLabel;
  @FXML private Label sliderLabel2;
  @FXML private LineChart<Number, Number> waveformChart;

  private SynthesisViewer synthesisViewer;
  private WavePropsSliders slider1;
  private WavePropsSliders slider2;

  private List<WavesRegister> waves = new LinkedList<>();

  public enum WaveType {
    SINE,
    SQUARE,
    TRIANGLE,
    SAWTOOTH
  }

  public void createButton() {}

  @FXML
  public void initialize() {
    var sineWave =
        WavesRegister.createWaveform("sine1", WaveType.SINE, 5, 1).addToChart(waveformChart);
    var sineWave2 =
        WavesRegister.createWaveform("sine2", WaveType.SINE, 10, 1).addToChart(resultWaveformChart);
    Synthesis synthesis = new IndependentSynthesis(sineWave, sineWave2);
    int duration = 1;

    synthesisMode.getItems().addAll("Independent", "Chaos");
    synthesisMode.getSelectionModel().select(0);
    synthesisMode
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            (observableValue, s, t1) -> {
              if (t1.equals("Independent")) {
                IndependentSynthesis independentSynthesis =
                    new IndependentSynthesis(sineWave, sineWave2);
                synthesisViewer.setSynthesis(independentSynthesis);
                synthesisViewer.synthesizeForPair(slider1, slider2);
              }
              if (t1.equals("Chaos")) {
                ChaosSynthesis chaosSynthesis = new ChaosSynthesis(sineWave, sineWave2);
                synthesisViewer.setSynthesis(chaosSynthesis);
                synthesisViewer.synthesizeForPair(slider1, slider2);
              }
            });

    synthesisViewer = new SynthesisViewer(sineWave, sineWave2, synthesis, duration);
    slider1 = new WavePropsSliders(sineWave, frequencySlider, sliderLabel);
    slider2 = new WavePropsSliders(sineWave2, frequencySlider2, sliderLabel2);

    slider1.addListenerAccordingToTarget(WavePropsSliders.Target.FREQUENCY);
    slider2.addListenerAccordingToTarget(WavePropsSliders.Target.FREQUENCY);
    synthesisViewer.synthesizeForPair(slider1, slider2);
  }
}
