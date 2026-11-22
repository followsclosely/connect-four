# Connect Four AI Challenge

## Getting Started

### Clone the connect-four Repository

To get started you will need to first download the [code](https://github.com/followsclosely/connect-four). If you are
using IntelliJ IDEA detailed instructions can be found on
[IntelliJ website](https://www.jetbrains.com/help/idea/manage-projects-hosted-on-github.html).

### Create a Subproject

To get started building your own AI you will need to copy the "entries/copy-me-to-get-started" directory. For the sake
of this Getting Started guide we will assume that you copied the directory to entries/my-ai.

This new entries/my-ai directory needs to be registered as a
[subproject](https://docs.gradle.org/current/userguide/multi_project_builds.html)
in Gradle. To register your new directory as a subproject add the following line to the root
[settings.gradle](https://github.com/followsclosely/connect-four/blob/master/settings.gradle) file.

```properties
include 'entries/my-ai'
```

After adding the entries/my-ai directory as a subproject you will have to
[refresh](https://www.jetbrains.com/help/idea/work-with-gradle-projects.html#gradle_refresh_project)
Gradle.

### Start Authoring Your AI

The directory you copied already has a class
named [YourCustomAI](https://github.com/followsclosely/connect-four/blob/master/entries/copy-me-to-get-started/src/main/java/YourCustomAI.java)
that implements the
[ArtificialIntelligence](https://github.com/followsclosely/connect-four/blob/master/core/src/main/java/io/github/followsclosley/connect/ArtificialIntelligence.java)
interface. The
[ArtificialIntelligence](https://github.com/followsclosely/connect-four/blob/master/core/src/main/java/io/github/followsclosley/connect/ArtificialIntelligence.java)
interface is the only class that you need to author to implement your own AI.

[ArtificialIntelligence.java](https://github.com/followsclosely/connect-four/blob/master/core/src/main/java/io/github/followsclosley/connect/ArtificialIntelligence.java)

```java
public abstract class ArtificialIntelligence {

    /**
     * This method is called by the Engine when it is "your" turn to play. 
     * It should return the column to drop the piece down.
     *
     * @param board The current state of the game.
     * @return The column (x) to drop the piece.
     */
    abstract public int yourTurn(Board board);
}
```

### Testing Your AI (Command Line)

You can test your AI using the following:
[ShellLauncher.java](https://github.com/followsclosely/connect-four/blob/master/entries/copy-me-to-get-started/src/main/java/ShellLauncher.java)

```java
import io.github.followsclosley.connect.Simulation;
import io.github.followsclosley.connect.impl.ai.Dummy;

public class io.github.jaron.connect.ShellLauncher{
public static void main(String[]args){
        new Simulation()
        .number(200000)
        .addArtificialIntelligence(new Dummy(1))
        .addArtificialIntelligence(new YourCustomAI(2))
        .run()
        .printSummary();
        }
        }
```

### Testing Your AI (Swing)

You can test your AI using a graphical interface:
[SwingLauncher.java](https://github.com/followsclosely/connect-four/blob/master/entries/copy-me-to-get-started/src/main/java/SwingLauncher.java)

```java
import io.github.followsclosley.connect.swing.SwingSupport;
import io.github.followsclosley.connect.impl.MutableBoard;

public class io.github.jaron.connect.SwingLauncher{
public static void main(String[]args){
        new SwingSupport()
        .setBoard(new MutableBoard())
        .setArtificialIntelligence(new YourCustomAI(SwingSupport.COMPUTER_COLOR))
        .run();
        }
        }
```

## Current AI implementations

### The win percentage:

| | Class Name                    |  #0    |  #1    |   #2   |   #3   |  #4   |  #5   |  #6   |
| ---: |:------------------------------|:------:|:------:|:------:|:------:|:-----:|:-----:|:-----:|
| #0 | Dummy                         | -      | %1.0   | %0.0   | %0.0   | %0.0  | %0.0  | %0.0  |
| #1 | JaronBot                      | %97.0  | -      | %12.0  | %23.0  | %0.0  | %1.0  | %0.0  |
| #2 | ScoreStrategy                 | %100.0 | %77.0  | -      | %0.0   | %43.0 | %0.0  | %0.0  |
| #3 | LaneAI                        | %99.0  | %57.0  | %0.0   | -      | %15.0 | %0.0  | %0.0  |
| #4 | MonteCarloAI                  | %100.0 | %93.0  | %57.0  | %66.0  | -     | %26.0 | %5.0  |
| #5 | MiniMaxWithAlphaBeta(depth=5) | %100.0 | %93.0  | %50.0  | %100.0 | %70.0 | -     | %28.0 |
| #6 | FableAI(depth=7)              | %100.0 | %100.0 | %100.0 | %50.0  | %75.0 | %65.0 | -     |

### The win or tie percentage:

| | Class Name                      |  #0    |  #1   |   #2   |   #3   |  #4   |  #5   |  #6   |
| ---: |:--------------------------------|:------:|:-----:|:------:|:------:|:-----:|:-----:|:-----:|
| #0 | Dummy *                         | -      | %1.0  | %0.0   | %0.0   | %0.0  | %0.0  | %0.0  |
| #1 | JaronBot *                      | %97.0  | -     | %27.0  | %37.0  | %1.0  | %1.0  | %1.0  |
| #2 | ScoreStrategy *                 | %100.0 | %90.0 | -      | %100.0 | %46.0 | %50.0 | %0.0  |
| #3 | LaneAI *                        | %99.0  | %69.0 | %100.0 | -      | %36.0 | %0.0  | %50.0 |
| #4 | MonteCarloAI *                  | %100.0 | %94.0 | %58.0  | %84.0  | -     | %37.0 | %17.0 |
| #5 | MiniMaxWithAlphaBeta(depth=5) * | %100.0 | %96.0 | %100.0 | %100.0 | %79.0 | -     | %37.0 |
| #6 | FableAI(depth=7) *              | %100.0 | %100.0 | %100.0 | %100.0 | %90.0 | %73.0 | -    |

### Performance (in millis)

| | Class Name                                                   | Name                          | Performance |
| ---: |:-------------------------------------------------------------|:------------------------------|:-----------:|
| 1 | io.github.followsclosley.connect.impl.ai.Dummy               | Dummy                         |      9      |
| 2 | io.github.jaron.connect.JaronBot                             | JaronBot                      |     133     |
| 3 | io.github.followsclosley.connect.ai.score.ScoreStrategy      | ScoreStrategy                 |     786     |
| 4 | io.github.lane.LaneAI                                        | LaneAI                        |   20,408    |
| 5 | io.github.ryanp102694.connect.MonteCarloAI                   | MonteCarloAI                  |   49,865    |
| 6 | io.github.followsclosley.connect.ai.mm.MiniMaxWithAlphaBeta  | MiniMaxWithAlphaBeta(depth=5) |   15,456    |
| 7 | io.github.fable.FableAI                                      | FableAI(depth=7)              |   54,537    |

```
io.github.followsclosley.connect.Competition.Dummy
             count = 6720
         mean rate = 46.50 calls/second
     1-minute rate = 77.67 calls/second
     5-minute rate = 379.23 calls/second
    15-minute rate = 503.04 calls/second
               min = 0.00 milliseconds
               max = 0.02 milliseconds
              mean = 0.00 milliseconds
            stddev = 0.00 milliseconds
            median = 0.00 milliseconds
              75% <= 0.00 milliseconds
              95% <= 0.00 milliseconds
              98% <= 0.00 milliseconds
              99% <= 0.00 milliseconds
            99.9% <= 0.01 milliseconds
io.github.followsclosley.connect.Competition.FableAI(depth=7)
             count = 16161
         mean rate = 111.86 calls/second
     1-minute rate = 132.95 calls/second
     5-minute rate = 61.00 calls/second
    15-minute rate = 40.48 calls/second
               min = 0.00 milliseconds
               max = 25.25 milliseconds
              mean = 3.37 milliseconds
            stddev = 4.17 milliseconds
            median = 2.25 milliseconds
              75% <= 5.08 milliseconds
              95% <= 14.29 milliseconds
              98% <= 15.80 milliseconds
              99% <= 17.41 milliseconds
            99.9% <= 24.32 milliseconds
io.github.followsclosley.connect.Competition.JaronBot
             count = 13020
         mean rate = 90.10 calls/second
     1-minute rate = 58.70 calls/second
     5-minute rate = 117.85 calls/second
    15-minute rate = 131.84 calls/second
               min = 0.00 milliseconds
               max = 0.66 milliseconds
              mean = 0.01 milliseconds
            stddev = 0.02 milliseconds
            median = 0.01 milliseconds
              75% <= 0.01 milliseconds
              95% <= 0.01 milliseconds
              98% <= 0.02 milliseconds
              99% <= 0.02 milliseconds
            99.9% <= 0.66 milliseconds
io.github.followsclosley.connect.Competition.LaneAI
             count = 15556
         mean rate = 107.65 calls/second
     1-minute rate = 78.19 calls/second
     5-minute rate = 106.37 calls/second
    15-minute rate = 108.25 calls/second
               min = 0.01 milliseconds
               max = 4.12 milliseconds
              mean = 1.31 milliseconds
            stddev = 0.74 milliseconds
            median = 1.48 milliseconds
              75% <= 1.99 milliseconds
              95% <= 2.23 milliseconds
              98% <= 2.34 milliseconds
              99% <= 2.38 milliseconds
            99.9% <= 3.10 milliseconds
io.github.followsclosley.connect.Competition.MiniMaxWithAlphaBeta(depth=5)
             count = 14548
         mean rate = 100.69 calls/second
     1-minute rate = 97.98 calls/second
     5-minute rate = 113.25 calls/second
    15-minute rate = 118.69 calls/second
               min = 0.00 milliseconds
               max = 4.49 milliseconds
              mean = 1.06 milliseconds
            stddev = 0.53 milliseconds
            median = 1.06 milliseconds
              75% <= 1.36 milliseconds
              95% <= 1.87 milliseconds
              98% <= 2.34 milliseconds
              99% <= 2.78 milliseconds
            99.9% <= 3.33 milliseconds
io.github.followsclosley.connect.Competition.MonteCarloAI
             count = 14743
         mean rate = 102.03 calls/second
     1-minute rate = 93.23 calls/second
     5-minute rate = 106.22 calls/second
    15-minute rate = 107.52 calls/second
               min = 0.00 milliseconds
               max = 12.64 milliseconds
              mean = 3.38 milliseconds
            stddev = 3.07 milliseconds
            median = 3.02 milliseconds
              75% <= 5.86 milliseconds
              95% <= 8.71 milliseconds
              98% <= 9.69 milliseconds
              99% <= 10.39 milliseconds
            99.9% <= 12.51 milliseconds
io.github.followsclosley.connect.Competition.ScoreStrategy
             count = 17843
         mean rate = 123.48 calls/second
     1-minute rate = 85.57 calls/second
     5-minute rate = 122.76 calls/second
    15-minute rate = 126.03 calls/second
               min = 0.00 milliseconds
               max = 0.86 milliseconds
              mean = 0.04 milliseconds
            stddev = 0.03 milliseconds
            median = 0.04 milliseconds
              75% <= 0.06 milliseconds
              95% <= 0.07 milliseconds
              98% <= 0.08 milliseconds
              99% <= 0.09 milliseconds
            99.9% <= 0.69 milliseconds


```
