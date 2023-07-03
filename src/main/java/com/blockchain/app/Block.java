package com.blockchain.app;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;

public class Block implements Serializable {

	public int[] timeStamp = new int[7];
	private byte[] hashPrevious;
	private int nonce;
	private String data;

	public Block(String data, byte[] hashPrevious) {// builder
		this.setTimeStamp();
		this.hashPrevious = hashPrevious;
		this.nonce = 0;
		this.data = data;
	}

	public Block(Block b) {// copy builder
		timeStamp = b.timeStamp;
		hashPrevious = b.hashPrevious;
		nonce = b.nonce;
		data = b.data;
	}

	@Override
	public int hashCode() {
		return 0;
	}

	@Override
	public String toString() {
		return "  Block:\n    Data: " + this.data +"\n    Timestamp: " + Arrays.toString(timeStamp) + "\n    Nonce: " + Integer.toString(nonce) + "\n";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Block b = (Block) o;
		return timeStamp == b.timeStamp &&
				hashPrevious == b.hashPrevious &&
				nonce == b.nonce &&
				data == b.data;
	}

	public byte[] getPrevHash() {
		return this.hashPrevious;
	}

	public void changeNonce() {
		this.nonce++;
	}

	public int getNonce() {// returns nonce
		return this.nonce;
	}

	public void setTimeStamp() {// sets time stamp to the nanoseconds
		this.timeStamp[0] = LocalDateTime.now().getYear();
		this.timeStamp[1] = LocalDateTime.now().getMonthValue();
		this.timeStamp[2] = LocalDateTime.now().getDayOfMonth();
		this.timeStamp[3] = LocalDateTime.now().getHour();
		this.timeStamp[4] = LocalDateTime.now().getMinute();
		this.timeStamp[5] = LocalDateTime.now().getSecond();
		this.timeStamp[6] = LocalDateTime.now().getNano();
	}

	public String getGenerationTimeString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < this.timeStamp.length; i++) {
			sb.append(this.timeStamp[i]);
		}
		return sb.toString();
	}

	public void printGenerationTime() {
		for (int i = 0; i < this.timeStamp.length; i++) {
			System.out.println(this.timeStamp[i]);
		}
	}

	static boolean isBlockValid(Block block, int difficulty) throws IOException {
		byte[] h = Utils.hashBlock(block);
		byte valueToMatch = (byte) 0;

		for (int i = 0; i < difficulty; i++) {
			if(Byte.compare(h[i], valueToMatch) != 0) {
				return false;
			}
		}
		return true;
	}
}