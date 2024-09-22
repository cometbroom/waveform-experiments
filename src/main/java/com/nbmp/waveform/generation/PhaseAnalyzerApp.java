/* (C)2024 */
package com.nbmp.waveform.generation;

import java.util.List;

import javafx.stage.Stage;

import com.nbmp.waveform.graph.GraphDashboard;
import com.nbmp.waveform.guides.PhaseDifferenceGuide;
import com.nbmp.waveform.guides.SineWaveGuide;

public class PhaseAnalyzerApp {

  public static void analyzePhaseRelationships(Stage stage) {
    analyzePhaseRelationships(stage, 1, 0.9);
  }

  public static void analyzePhaseRelationships(
      Stage stage, double frequencyWave1, double frequencyWave2) {
    var graph = GraphDashboard.builder().build();
    var generator = new EfficientWaveGeneration(graph);

    var sine1 = new SineWaveGuide(frequencyWave1);
    var sine2 = new SineWaveGuide(frequencyWave2);

    var phaseWave = new PhaseDifferenceGuide(sine1, sine2);
    var seriesList = generator.generateWithWrappers(List.of(sine1, sine2, phaseWave));

    graph.addSeries(seriesList).view(stage);
  }
}
