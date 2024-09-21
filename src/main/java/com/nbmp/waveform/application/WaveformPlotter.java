/* (C)2024 */
package com.nbmp.waveform.application;

import javafx.application.Application;
import javafx.stage.Stage;

import com.nbmp.waveform.generation.SineWaveGenerator;
import com.nbmp.waveform.graph.GraphDashboard;

public class WaveformPlotter extends Application {

  private final String STAGE_TITLE = "Sine Wave";

  @Override
  public void start(Stage stage) {
    stage.setTitle(STAGE_TITLE);

    var graph = GraphDashboard.builder().build();
    var sine1 = SineWaveGenerator.builder().graph(graph).frequency(1).build();
    var sine2 = SineWaveGenerator.builder().graph(graph).frequency(0.9).build();
    graph.addSeries(sine1, sine2);
    graph.view(stage);
  }

  public static void main(String[] args) {
    launch(args);
  }
}
