package com.blockchain.app;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.Random;

public class App
{
    public static void main( String[] args ) throws InterruptedException, IOException, NoSuchAlgorithmException
    {
        Random rand = new Random();
        int difficulty = 3;
        int numberOfMiners = 10;
		Chain originalChain = new Chain(difficulty);

        LinkedList<Miner> miners = new LinkedList<>();

        for (int i = 0; i < numberOfMiners; i++) {
            Integer randomInt = rand.nextInt();
            Miner m = new Miner(new Chain(originalChain), "RandomData_" + randomInt.toString());
            miners.add(m);
        }

        for (Miner m : miners) {
            m.addListeners(miners);
        }

        for (Miner m : miners) {
            m.start();
        }
    }
}
