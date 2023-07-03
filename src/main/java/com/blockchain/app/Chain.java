package com.blockchain.app;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class Chain {
	private Block firstBlock;
	private MappedLinkedList<Block> blockList;
	public Integer difficulty;

	public Chain(int difficulty) throws NoSuchAlgorithmException, IOException {
		this.blockList = new MappedLinkedList<Block>();
		this.difficulty = difficulty;
		addFirstBlock();
	}

	public Chain(Chain c) {
		this.firstBlock = c.firstBlock;
		this.difficulty = c.difficulty;
		this.blockList = new MappedLinkedList<Block>(c.blockList);
	}

	@Override
	public String toString() {
		Block block;
		StringBuilder sb = new StringBuilder();

		sb.append("Chain:\n");

		for (int i = 0; i < this.blockList.size(); i++) {
			block = this.blockList.get(i);

			sb.append(block.toString());
		}
		sb.append("\n");
		return sb.toString();
	}

	public Block getLastBlock() {
		return this.blockList.getLast();
	}

	private void addFirstBlock() throws IOException,
			NoSuchAlgorithmException {
		byte[] previousHashPlaceholder = new byte[2];
		String firstBlockDummyData = "First Block dummy data";

		this.firstBlock = new Block(firstBlockDummyData, previousHashPlaceholder);
		while(!Block.isBlockValid(firstBlock, this.difficulty)) {
			this.firstBlock.changeNonce();
		}
		this.blockList.insert(firstBlock);
	}

	public boolean addBlock(Block block) throws IOException, NoSuchAlgorithmException, NullPointerException {
		try {
			if (Block.isBlockValid(block, this.difficulty) && Arrays.equals(Utils.hashBlock(this.getLastBlock()), block.getPrevHash())) {
				this.blockList.insert(block);
				return true;
			}
		} catch (NoSuchElementException e) {
		}
		return false;
	}

	public Block getFirstBlock() {
		return this.firstBlock;
	}

	public static String toHex(byte[] input) {
		StringBuilder sb = new StringBuilder();

		for (byte b : input) {
			sb.append(Integer.toHexString(0xFF & b));
		}

		return sb.toString();
	}

	public String toStringHash() throws IOException {
		int chainSize = this.blockList.size();
		StringBuilder sb = new StringBuilder();
		Block b = null;
		for (int i = 1; i < chainSize; i++) {
			b = this.blockList.get(i);
			String s = toHex(Utils.hashBlock(b)) + "\n";
			sb.append(s);
		}
		return sb.toString();
	}
}