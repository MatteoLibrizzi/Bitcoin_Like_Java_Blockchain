package com.blockchain.app;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args ) throws InterruptedException, IOException, NoSuchAlgorithmException
    {

        int difficulty = 3;
        int numberOfMiners = 10;
		Chain originalChain = new Chain(difficulty);

        LinkedList<Miner> miners = new LinkedList<>();

        for (int i = 0; i < numberOfMiners; i++) {
            Miner m = new Miner(new Chain(originalChain), Utils.generateRandomByteArray(2));
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
