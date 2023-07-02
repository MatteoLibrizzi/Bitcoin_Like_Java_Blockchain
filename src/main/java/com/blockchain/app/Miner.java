package com.blockchain.app;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Miner extends Thread implements BlockAdditionListener{
	// TODO should create a way for miners to communicate their existence to the others
	private Chain chain;
	private byte[] data;
	private LinkedList<Miner> connectedMiners;
	private boolean calculatingBlock;

	public Miner(Chain chain, byte[] data) {
		this.chain = chain;
		this.connectedMiners = new LinkedList<>();
		this.data = data;
		
		this.calculatingBlock = false;
	}

	public void addListeners (LinkedList<Miner> connecteMiners) {
		this.connectedMiners.addAll(connecteMiners);
	}

	public static byte[] hash(byte[] input) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");

		byte[] output = md.digest(input);

		return output;
	}

	public static String toHex(byte[] input) {
        StringBuilder sb = new StringBuilder();
        
        for (byte b : input) {
            sb.append(Integer.toHexString(0xFF & b));
        }
        
        return sb.toString();
	}

	public Chain getChain() {
		return this.chain;
	}

	private Chain getBestChain() throws IOException {
		Map<String, LinkedList<Chain>> hashToChain = new HashMap<>();

		for(Miner miner : connectedMiners) {
			Chain chainFromMiner = miner.getChain();

			LinkedList<Chain> chainsWithHash = null;

			if(hashToChain.containsKey(chainFromMiner.toStringHash())) {
				chainsWithHash = hashToChain.get(chainFromMiner.toStringHash());
			}
			if(chainsWithHash == null) {
				chainsWithHash = new LinkedList<>();

				hashToChain.put(chainFromMiner.toStringHash(), chainsWithHash);
			}
			chainsWithHash.add(chainFromMiner);
		}

		String longestChainHash = Utils.findMostFrequentChain(hashToChain);

		return hashToChain.get(longestChainHash).getLast();
	}

	private void notifyOtherMiners(Chain c) {
		for (BlockAdditionListener listener : this.connectedMiners) {
			listener.onBlockAdded(c);
		}
	}

	@Override
	public void run() {
		try {
			while (true) {
				this.chain = this.getBestChain();

				byte[] hdata = hash(this.data);
				Block lastBlock = this.chain.getLastBlock();
				byte[] lasth = Utils.hashBlock(lastBlock);
				Block newBlock = new Block(hdata, lasth);
				this.calculatingBlock = true;

				while (this.calculatingBlock) {
					if (Block.isBlockValid(newBlock, this.chain.difficulty)) {
						this.calculatingBlock = false;
					} else {
						newBlock.changeNonce();
					}
				}

				this.notifyOtherMiners(this.chain);
				if (Block.isBlockValid(newBlock, this.chain.difficulty)) {
					boolean insertedBlockSuccessfully = this.chain.addBlock(newBlock);
					if (insertedBlockSuccessfully) {
						break;
					}
				}
			}

			System.out.println(this.chain);

		} catch (NoSuchAlgorithmException | IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onBlockAdded(Chain c) {
		this.calculatingBlock = false;
	}
}