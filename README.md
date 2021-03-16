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
[ArtificialIntelligence](https://github.com/followsclosely/connect-four/blob/master/core/src/main/java/net/wilson/games/connect/ArtificialIntelligence)
interface. The
[ArtificialIntelligence](https://github.com/followsclosely/connect-four/blob/master/core/src/main/java/net/wilson/games/connect/ArtificialIntelligence)
interface is the only class that you need to author to implement your own AI.

[ArtificialIntelligence.java](https://github.com/followsclosely/connect-four/blob/master/core/src/main/java/net/wilson/games/connect/ArtificialIntelligence.java)

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
[io.github.jaron.connect.ShellLauncher.java](https://github.com/followsclosely/connect-four/blob/master/entries/copy-me-to-get-started/src/main/java/io.github.jaron.connect.ShellLauncher.java)

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
[io.github.jaron.connect.SwingLauncher.java](https://github.com/followsclosely/connect-four/blob/master/entries/copy-me-to-get-started/src/main/java/io.github.jaron.connect.SwingLauncher.java)

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

## Current Implementations

The win percentage of current AI implementations:

| | Class Name |  #0 |  #1 |  #2 |  #3 |  #4 | 
| ---: | :--- |  :---: |  :---: |  :---: |  :---: |  :---: | 
| #0 | io.github.followsclosley.connect.impl.ai.Dummy |  -  |  %0.0  |  %2.0  |  %0.0  |  %25.0  | 
| #1 | io.github.jaron.connect.JaronBot |  %95.0  |  -  |  %6.0  |  %24.0  |  %96.0  | 
| #2 | io.github.followsclosley.connect.ai.ScoreStrategy |  %100.0  |  %87.0  |  -  |  %0.0  |  %100.0  | 
| #3 | io.github.lane.LaneAI |  %99.0  |  %63.0  |  %0.0  |  -  |  %100.0  | 
| #4 | io.github.ryanp102694.connect.MonteCarloAI |  %76.0  |  %2.0  |  %0.0  |  %0.0  |  -  | 