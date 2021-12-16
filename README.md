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

