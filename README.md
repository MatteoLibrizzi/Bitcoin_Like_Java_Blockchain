# JavaBlockchain

Implementation of a Blockchain to store arbitrary data in java.

Inspired by the bitcoin white paper.

Contains an ecosystem of interconnected nodes, each trying to create new blocks on the chain and interacting with each other.

To see it in action run:

```
mvn package
java -cp target/blockchain-1.0-SNAPSHOT.jar com.blockchain.app.App
```

feel free to specify in App.java the number of miners you want in the network and the difficulty of the problem they need to solve (thread lightly ;))


The miners communicate with each other to start working on the longest chain they find as soon as a miner in their network finds a block. The news of the new block spreads very quickly because each miner that receives the message will send it to it neighbors.
The numbers printed represent the nonces of the blocks in the chain. Each miner adds a new one on top of the existing ones.
