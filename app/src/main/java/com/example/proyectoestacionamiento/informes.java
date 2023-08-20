package com.example.proyectoestacionamiento;

import androidx.appcompat.app.AppCompatActivity;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
public class informes extends AppCompatActivity {
    private GraphicalView chartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Crear una serie de datos
        XYSeries series = new XYSeries("Ejemplo de gráfico de líneas");
        series.add(1, 5);
        series.add(2, 7);
        series.add(3, 3);
        series.add(4, 8);

        // Crear un conjunto de datos y añadir la serie
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        dataset.addSeries(series);

        // Crear un renderizador para la serie
        XYSeriesRenderer seriesRenderer = new XYSeriesRenderer();
        seriesRenderer.setColor(Color.BLUE);
        seriesRenderer.setLineWidth(2);

        // Crear un renderizador múltiple y añadir el renderizador de la serie
        XYMultipleSeriesRenderer multipleSeriesRenderer = new XYMultipleSeriesRenderer();
        multipleSeriesRenderer.addSeriesRenderer(seriesRenderer);

        // Configurar el renderizador múltiple
        multipleSeriesRenderer.setChartTitle("Ejemplo de gráfico de líneas");
        multipleSeriesRenderer.setXTitle("Eje X");
        multipleSeriesRenderer.setYTitle("Eje Y");

        // Crear la vista del gráfico
        chartView = ChartFactory.getLineChartView(this, dataset, multipleSeriesRenderer);

        // Añadir la vista del gráfico al FrameLayout
        FrameLayout chartContainer = findViewById(R.id.chart_container);
        chartContainer.addView(chartView);
    }
}