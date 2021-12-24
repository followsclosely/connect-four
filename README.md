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

| | Class Name                    |  #0   |  #1   |  #2   |   #3   |  #4   |  #5   |
| ---: |:------------------------------|:-----:|:-----:|:-----:|:------:|:-----:|:-----:|
| #0 | Dummy                         | -     | %2.3  | %0.1  |  %0.4  | %0.5  | %0.0  |
| #1 | JaronBot                      | %96.9 |  -    | %8.4  | %26.6  | %5.4  | %2.6  |
| #2 | ScoreStrategy                 | %99.8 | %76.7 |  -    |  %0.0  | %48.9 | %0.0  |
| #3 | LaneAI                        | %99.2 | %61.0 | %0.0  |  -     | %12.8 | %0.0  |
| #4 | MonteCarloAI                  | %99.7 | %94.4 | %52.2 | %66.9  |  -    | %22.7 |
| #5 | MiniMaxWithAlphaBeta(depth=5) | %99.8 | %95.3 | %59.7 | %100.0 | %64.7 |  -    |

### The win or tie percentage:

| | Class Name                    |  #0   |  #1   |   #2   |   #3   |  #4   |  #5   |
| ---: |:------------------------------|:-----:|:-----:|:------:|:------:|:-----:|:-----:|
| #0 | Dummy                         | -     | %2.4  |  %0.1  |  %0.4  | %0.5  | %0.0  |
| #1 | JaronBot                      | %97.1 | -     | %23.0  | %41.6  | %5.7  | %3.8  |
| #2 | ScoreStrategy                 | %99.8 | %88.8 |  -     | %100.0 | %49.6 | %39.0 |
| #3 | LaneAI                        | %99.2 | %76.0 | %100.0 |  -     | %31.4 | %0.0  |
| #4 | MonteCarloAI                  | %99.7 | %95.3 | %53.5  | %84.89 | -     | %36.7 |
| #5 | MiniMaxWithAlphaBeta(depth=5) | %99.8 | %96.7 | %100.0 | %100.0 | %79.5 |  -    |

### Performance (in millis)

| | Class Name                                                  | Name                          | Performance |
| ---: |:------------------------------------------------------------|:------------------------------|:-----------:|
| 1 | io.github.followsclosley.connect.impl.ai.Dummy              | Dummy                         |     92      |
| 2 | io.github.jaron.connect.JaronBot                            | JaronBot                      |    1,051    |
| 3 | io.github.followsclosley.connect.ai.score.ScoreStrategy     | ScoreStrategy                 |    6,930    |
| 4 | io.github.lane.LaneAI                                       | LaneAI                        |   181,100   |
| 5 | io.github.ryanp102694.connect.MonteCarloAI                  | MonteCarloAI                  |   993,048   |
| 6 | io.github.followsclosley.connect.ai.mm.MiniMaxWithAlphaBeta | MiniMaxWithAlphaBeta(depth=5) |   263,890   |

```
io.github.followsclosley.connect.Competition.Dummy
count = 57195
mean rate = 27.80 calls/second
1-minute rate = 0.03 calls/second
5-minute rate = 7.28 calls/second
15-minute rate = 288.12 calls/second
min = 0.00 milliseconds
max = 0.11 milliseconds
mean = 0.00 milliseconds
stddev = 0.01 milliseconds
median = 0.00 milliseconds
75% <= 0.00 milliseconds
95% <= 0.00 milliseconds
98% <= 0.01 milliseconds
99% <= 0.02 milliseconds
99.9% <= 0.10 milliseconds
io.github.followsclosley.connect.Competition.JaronBot
count = 110507
mean rate = 53.70 calls/second
1-minute rate = 0.16 calls/second
5-minute rate = 11.95 calls/second
15-minute rate = 174.40 calls/second
min = 0.00 milliseconds
max = 1.60 milliseconds
mean = 0.02 milliseconds
stddev = 0.07 milliseconds
median = 0.01 milliseconds
75% <= 0.01 milliseconds
95% <= 0.03 milliseconds
98% <= 0.04 milliseconds
99% <= 0.06 milliseconds
99.9% <= 1.60 milliseconds
io.github.followsclosley.connect.Competition.LaneAI
count = 125562
mean rate = 61.02 calls/second
1-minute rate = 0.87 calls/second
5-minute rate = 15.11 calls/second
15-minute rate = 45.04 calls/second
min = 1.37 milliseconds
max = 23.83 milliseconds
mean = 2.08 milliseconds
stddev = 0.98 milliseconds
median = 1.90 milliseconds
75% <= 2.27 milliseconds
95% <= 2.95 milliseconds
98% <= 3.89 milliseconds
99% <= 4.49 milliseconds
99.9% <= 23.83 milliseconds
io.github.followsclosley.connect.Competition.MiniMaxWithAlphaBeta(depth=5)
count = 115300
mean rate = 56.04 calls/second
1-minute rate = 55.16 calls/second
5-minute rate = 76.60 calls/second
15-minute rate = 60.10 calls/second
min = 0.00 milliseconds
max = 463.70 milliseconds
mean = 5.28 milliseconds
stddev = 16.64 milliseconds
median = 3.04 milliseconds
75% <= 4.76 milliseconds
95% <= 13.45 milliseconds
98% <= 28.30 milliseconds
99% <= 50.72 milliseconds
99.9% <= 287.18 milliseconds
io.github.followsclosley.connect.Competition.MonteCarloAI
count = 121152
mean rate = 58.88 calls/second
1-minute rate = 52.79 calls/second
5-minute rate = 40.89 calls/second
15-minute rate = 44.90 calls/second
min = 0.00 milliseconds
max = 150.66 milliseconds
mean = 10.91 milliseconds
stddev = 12.47 milliseconds
median = 9.23 milliseconds
75% <= 17.45 milliseconds
95% <= 27.94 milliseconds
98% <= 38.51 milliseconds
99% <= 56.57 milliseconds
99.9% <= 150.66 milliseconds
io.github.followsclosley.connect.Competition.ScoreStrategy
count = 140828
mean rate = 68.44 calls/second
1-minute rate = 0.59 calls/second
5-minute rate = 19.24 calls/second
15-minute rate = 168.22 calls/second
min = 0.00 milliseconds
max = 1.81 milliseconds
mean = 0.06 milliseconds
stddev = 0.10 milliseconds
median = 0.06 milliseconds
75% <= 0.07 milliseconds
95% <= 0.10 milliseconds
98% <= 0.13 milliseconds
99% <= 0.19 milliseconds
99.9% <= 1.47 milliseconds
```