package io.github.followsclosley.competition;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public abstract class AbstractCompetition<AI> {

    protected final MetricRegistry registry = new MetricRegistry();
    private final List<AI> ais = new ArrayList<>();
    private int numberOfSimulations = 1000;
    private AbstractMatch[][] matches;

    public AbstractCompetition<AI> add(AI ai) {
        ais.add(ai);
        return this;
    }

    public AbstractCompetition<AI> simulations(int numberOfSimulations) {
        this.numberOfSimulations = numberOfSimulations;
        return this;
    }

    public AbstractCompetition<AI> run() {

        int size = ais.size();
        int total = size * size - size;

        this.matches = new AbstractMatch[ais.size()][ais.size()];

        for (int i = 0, x = 0; x < size; x++) {
            AI player1 = ais.get(x);
            for (int y = 0; y < size; y++) {

                AI player2 = ais.get(y);

                if (x != y) {
                    System.out.println(++i + "/" + total + " : " + player1 + " vs. " + player2);
                    matches[x][y] = run(player1, player2, numberOfSimulations);
                }
            }
        }

        return this;
    }

    public abstract AbstractMatch run(AI ai1, AI ai2, int numberOfSimulations);

    public AbstractCompetition<AI> printSummary() {
        return printSummary("templates/index.vm");
    }

    public AbstractCompetition<AI> printSummary(String template) {
        try (InputStream input = this.getClass().getClassLoader().getResourceAsStream(template)) {
            if (input != null) try (Reader reader = new InputStreamReader(input)) {
                VelocityEngine ve = new VelocityEngine();
                ve.init();

                VelocityContext context = new VelocityContext();
                context.put("matches", matches);
                context.put("ais", ais);

                StringWriter writer = new StringWriter();
                if (!ve.evaluate(context, writer, template, reader)) {
                    System.out.println("Failed to convert the template.");
                }

                System.out.println(writer);
            } else {
                System.out.println("Could not load template: '" + template + "'.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return this;
    }

    public AbstractCompetition<AI> printMetrics() {
        ConsoleReporter reporter = ConsoleReporter.forRegistry(registry)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .outputTo(new PrintStream(System.out))
                .build();
        //reporter.start(1, TimeUnit.SECONDS);
        //reporter.stop();
        reporter.report();

        return this;
    }
}
