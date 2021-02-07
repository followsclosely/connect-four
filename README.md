# Connect Four AI Challenge

## Getting Started

###Clone the connect-four Repository
To get started you will need to first download the [code](https://github.com/followsclosely/connect-four). 
If you are using IntelliJ IDEA detailed instructions can be found on 
[IntelliJ website](https://www.jetbrains.com/help/idea/manage-projects-hosted-on-github.html).

[ArtificialIntelligence.java](https://github.com/followsclosely/connect-four/blob/master/core/src/main/java/net/wilson/games/connect/ArtificialIntelligence.java)
```java
public abstract class ArtificialIntelligence {

   ...

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

## Current Implementations

| Module | Class Name  | Win % |
| ---: | :--- | :---: |
| core | [net.wilson.games.connect.impl.ai.Dummy](https://github.com/followsclosely/connect-four/blob/master/core/src/main/java/net/wilson/games/connect/impl/ai/Dummy.java) | N/A |
| entries/followsclosely | [net.wilson.games.connect.ai.ScoreStrategy](https://github.com/followsclosely/connect-four/blob/master/entries/followsclosely/src/main/java/net/wilson/games/connect/ai/ScoreStrategy.java) | 99.92% |
|  |  |   |
