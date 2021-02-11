# Connect Four AI Challenge

## Getting Started

### Clone the connect-four Repository
To get started you will need to first download the [code](https://github.com/followsclosely/connect-four). 
If you are using IntelliJ IDEA detailed instructions can be found on 
[IntelliJ website](https://www.jetbrains.com/help/idea/manage-projects-hosted-on-github.html).

### Create a Subproject
To get started building your own AI you will need to copy the "entries/copy-me-to-get-started" directory.
For the sake of this Getting Started guide we will assume that you copied the directory to entries/my-ai.

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
The directory you copied already has a class named [YourCustomAI](https://github.com/followsclosely/connect-four/blob/master/entries/copy-me-to-get-started/src/main/java/YourCustomAI.java) 
that implements the
[ArtificialIntelligence](https://github.com/followsclosely/connect-four/blob/master/core/src/main/java/net/wilson/games/connect/ArtificialIntelligence) interface. The
[ArtificialIntelligence](https://github.com/followsclosely/connect-four/blob/master/core/src/main/java/net/wilson/games/connect/ArtificialIntelligence) interface is the only class that you need to author to implement your own AI.


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
[ShellLauncher.java](https://github.com/followsclosely/connect-four/blob/master/entries/copy-me-to-get-started/src/main/java/ShellLauncher.java)
```java
import io.github.followsclosley.connect.Simulation;
import io.github.followsclosley.connect.impl.ai.Dummy;

public class ShellLauncher {
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

public class SwingLauncher {
    public static void main(String[] args) {
        new SwingSupport()
                .setBoard(new MutableBoard())
                .setArtificialIntelligence(new YourCustomAI(SwingSupport.COMPUTER_COLOR))
                .run();
    }
}
```

## Current Implementations

| Module | Class Name  | Win % |
| ---: | :--- | :---: |
| core | [io.github.followsclosley.connect.impl.ai.Dummy](https://github.com/followsclosely/connect-four/blob/master/core/src/main/java/io/github/followsclosley/connect/impl/ai/Dummy.java) | N/A |
| entries/followsclosely | [io.github.followsclosley.connect.ai.ScoreStrategy](https://github.com/followsclosely/connect-four/blob/master/entries/followsclosely/src/main/java/io/github/followsclosley/connect/ai/ScoreStrategy.java) | 99.92% |
|  |  |   |
