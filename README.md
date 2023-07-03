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

The miners communicate with each other to start working on the longest chain on their network. The news of the new block spreads very quickly because each miner that receives the message will send it to its neighbors.

Its important to note that each miner maintains its own copy of the chain, so that when a new block is added, the network is forced to spread the message.

The data printed represents the version of the chain (the blocks it contains) as each miner adds its new block.
