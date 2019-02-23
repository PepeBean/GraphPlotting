package com.example.graphplotting;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    // Variable used to ensure on button click any existing graph is removed before adding a new one
    private boolean generated = false;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialising buttons
        final Button button1 = findViewById(R.id.lineButton);
        final Button button2 = findViewById(R.id.barButton);
        final Button button3 = findViewById(R.id.mixedButton);
        final Button resetButton = findViewById(R.id.resetButton);

        // Initialising graph and its properties
        final GraphView graph = (GraphView) findViewById(R.id.graph);
        graph.getGridLabelRenderer().setHorizontalAxisTitle("X Axis");
        graph.getGridLabelRenderer().setVerticalAxisTitle("Y Axis");
        graph.getViewport().setScrollable(true);
        graph.getViewport().setScalable(true);
        String[] xAxis = new String[10];

        // Labelling X-axis
        for (int i = 0; i < 10; i++) {
            xAxis[i] = "School " + (i + 1);
        }
        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setHorizontalLabels(xAxis);
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

        // Listeners for all buttons
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                lineGraph(graph);
                resetButton.setVisibility(View.VISIBLE);
            }
        });


        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                barChart(graph);
                resetButton.setVisibility(View.VISIBLE);
            }

            ;
        });


        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mixedGraph(graph);
                resetButton.setVisibility(View.VISIBLE);

            }

        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
                startActivity(getIntent());
            }
        });

    }


    // Method generating an array of random data points
    private DataPoint[] generateData() {
        Random mRand = new Random();
        int count = 10;
        DataPoint[] values = new DataPoint[count];
        for (int i = 0; i < count; i++) {
            double x = i;
            double f = mRand.nextDouble() * 0.15 + 0.3;
            double y = Math.sin(i * f + 2) + mRand.nextDouble() * 0.3;
            DataPoint v = new DataPoint(x, y);
            values[i] = v;
        }
        return values;
    }


    // Method called to create a line graph
    public void lineGraph(GraphView graph) {
        if (generated) {
            graph.removeAllSeries();
            generated = false;
        }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(generateData());
        series.setDrawBackground(true);
        graph.addSeries(series);

        generated = true;
    }

    // Method called to create a bar chart
    public void barChart(GraphView graph) {
        if (generated) {
            graph.removeAllSeries();
            generated = false;
        }

        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(generateData());
        graph.addSeries(series);

        generated = true;
    }

    // Method called to create a graph with a bar chart and a line graph on top of each other
    public void mixedGraph(GraphView graph) {
        if (generated) {
            graph.removeAllSeries();
            generated = false;
        }

        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(generateData());
        graph.addSeries(series);

        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>(generateData());
        series2.setColor(Color.GREEN);
        graph.addSeries(series2);

        generated = true;
    }
}