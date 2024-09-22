package com.nbmp.waveform.guides;

import com.nbmp.waveform.generation.EfficientWaveGeneration;
import com.nbmp.waveform.generation.Generator;
import com.nbmp.waveform.graph.SliderBox;
import com.nbmp.waveform.models.SliderTarget;
import com.nbmp.waveform.ui_elements.WaveSlider;
import com.nbmp.waveform.utils.MathConstants;
import javafx.scene.chart.XYChart;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.function.Consumer;

@Getter
@Setter
@NoArgsConstructor
public class SmartGuide implements Adjustable, BoundToSlider, Guide {

    private XYChart.Series<Number, Number> series = new XYChart.Series<>();
    private EfficientWaveGeneration generator;
    private boolean isInteractive = false;

    public SmartGuide(EfficientWaveGeneration generator) {
        this.generator = generator;
    }

    @Override
    public Double compute(Double t, Double timeStep) {
        double computedValue = computeWaveValue(t);
        return computedValue;
    }

    protected Double computeWaveValue(Double t) {
        return 0.0;
    }

    public void addPoint(Double t, Double timeStep) {
        series.getData().add(new XYChart.Data<>(t, compute(t, timeStep)));
    }

    @Override
    public Consumer<Double> recompute(SliderTarget target) {
        return (t) -> {};
    }

    @Override
    public void addSlider(String name, SliderTarget target, Generator generator) {
        var slider = new WaveSlider(recompute(target));
        slider.setName("%s for %s".formatted(name, target.name()));
        SliderBox.addSlider(slider.getLabel(), slider);
        this.isInteractive = true;
        if (generator instanceof EfficientWaveGeneration) {
            this.generator = (EfficientWaveGeneration) generator;
        }
    }

    public void makeReactive() {
        this.isInteractive = true;
    }

    public void regenerateSeries() {
        series.getData().clear();
        this.generator.regenerate();
    }
}
