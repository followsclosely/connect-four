package io.github.followsclosley.connect;

import io.github.followsclosley.connect.ai.ScoreStrategy;
import io.github.followsclosley.connect.impl.ai.Dummy;
import io.github.jaron.connect.JaronBot;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class Competition {

    private List<ArtificialIntelligence> ais = new ArrayList<>();

    public Competition add(ArtificialIntelligence ai){
        ais.add(ai);
        return this;
    }

    public void run(){

        int numberOfSimulations = 100;

        int size = ais.size();
        Match[][] matches = new Match[ais.size()][ais.size()];

        for(int x=0; x<size; x++){
            ArtificialIntelligence player1 = ais.get(x);
            for(int y=0; y<size; y++){
                if( x != y){
                    ArtificialIntelligence player2 = ais.get(y);
                    System.out.println(player1 + " vs. " + player2);
                    matches[x][y] = new Match(player1, player2).run(numberOfSimulations);
                }
            }
        }

        //TODO: Replace this with velocity

        System.out.print("|  | Class Name | ");
        for(int y=1; y<=size; y++) {
            System.out.print( " #" + y + " | ");
        }
        System.out.println();

        System.out.print("| ---: | :--- | ");
        for(int y=1; y<=size; y++) {
            System.out.print(" :---: |");
        }
        System.out.println();

        for(int x=0; x<size; x++){
            ArtificialIntelligence player1 = ais.get(x);
            System.out.print("| #" + (x+1) + "|" + player1.getClass().getName() + "|");
            for(int y=0; y<size; y++) {
                if( matches[x][y] != null) {
                    //float winPercentage = (Math.round((float)matches[x][y].getWins(player1.getColor()) / numberOfSimulations * 10000)) / 100;
                    int wins = matches[x][y].getWins(player1.getColor());
                    System.out.print((wins/(numberOfSimulations/100f)) + "%");
                } else {
                    System.out.print("-");
                }
                System.out.print("|");
            }
            System.out.println();
        }

        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.init();

        VelocityContext context = new VelocityContext();
        context.put("matches", matches);

        Template t = velocityEngine.getTemplate("./competition/src/main/java/index.vm");

        StringWriter writer = new StringWriter();
        t.merge( context, writer );

        System.out.println( writer.toString() );
    }



    public static void main(String[] args)
    {
        new Competition()
                .add(new Dummy(1))
                .add(new JaronBot(2))
                .add(new ScoreStrategy(3))
                .run();
    }
}