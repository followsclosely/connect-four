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

public class io.github.jaron.connect.ShellLauncher {
    public static void main(String[] args) {
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

public class io.github.jaron.connect.SwingLauncher {
    public static void main(String[] args) {
        new SwingSupport()
                .setBoard(new MutableBoard())
                .setArtificialIntelligence(new YourCustomAI(SwingSupport.COMPUTER_COLOR))
                .run();
    }
}
```

## Current AI implementations

### The win percentage:

| | Class Name                                           |   #0   |  #1   |   #2    |  #3   |  #4   |  #5   | 
| ---: |:-----------------------------------------------------|:------:|:-----:|:-------:|:-----:|:-----:|:-----:| 
| #0 | ...followsclosley.connect.impl.ai.Dummy              |   -    | %1.0  |  %0.0   | %0.0  | %0.0  | %0.0  | 
| #1 | ...jaron.connect.JaronBot                            | %95.0  |   -   |  %11.0  | %31.0 | %5.0  | %1.0  | 
| #2 | ...followsclosley.connect.ai.score.ScoreStrategy     | %100.0 | %75.0 |    -    | %0.0  | %46.0 | %0.0  | 
| #3 | ...lane.LaneAI *                                     | %100.0 | %55.0 |  %0.0   |   -   | %13.0 | %0.0  | 
| #4 | ...ryanp102694.connect.MonteCarloAI                  | %100.0 | %95.0 | %52.9   | %68.0 |   -   | %11.0 | 
| #5 | ...followsclosley.connect.ai.mm.MiniMaxWithAlphaBeta | %100.0 | %97.0 | %100.0  | %81.0 | %70.0 |   -   | 

### The win or tie percentage:

| | Class Name                                           |   #0   |  #1   |     #2     |   #3   |  #4   |     #5     | 
| ---: |:-----------------------------------------------------|:------:|:-----:|:----------:|:------:|:-----:|:----------:| 
| #0 | ...followsclosley.connect.impl.ai.Dummy              |   -    | %1.0  |    %0.0    |  %0.0  | %0.0  |    %0.0    | 
| #1 | ...jaron.connect.JaronBot                            | %97.0  |   -   |   %23.0    | %46.0  | %8.0  |    %1.0    | 
| #2 | ...followsclosley.connect.ai.score.ScoreStrategy     | %100.0 | %92.0 |     -      | %100.0 | %49.0 |    %0.0    | 
| #3 | ...lane.LaneAI                                       | %100.0 | %69.0 |   %100.0   |   -    | %28.0 |    %9.0    | 
| #4 | ...ryanp102694.connect.MonteCarloAI                  | %100.0 | %95.0 | %52.9 | %84.0  |   -   | %27.0 | 
| #5 | ...followsclosley.connect.ai.mm.MiniMaxWithAlphaBeta | %100.0 | %99.0 |   %100.0   | %100.0 | %92.0 |     -      | 

### Performance (in millis)

| | Class Name | Performance |
| ---: | :--- | :---: |
| 1 |  io.github.followsclosley.connect.impl.ai.Dummy | 28
| 2 |  io.github.jaron.connect.JaronBot | 158
| 3 |  io.github.followsclosley.connect.ai.score.ScoreStrategy | 1,005
| 4 |  io.github.lane.LaneAI | 31,587
| 5 |  io.github.ryanp102694.connect.MonteCarloAI | 141,150
| 6 |  io.github.followsclosley.connect.ai.mm.MiniMaxWithAlphaBeta | 487,010