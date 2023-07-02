package com.blockchain.app;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.LinkedList;
import java.util.Map;

public class Utils {
    public static byte[] generateRandomByteArray(int size) {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[size];
		random.nextBytes(salt);
		return salt;
	}

	static byte[] hashBlock(Block b) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(bos);
		out.writeObject(b);
		out.flush();

		byte[] bytes = bos.toByteArray();

		return Utils.hash(bytes);
	}

	static byte[] hashChain(Chain c) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(bos);
		out.writeObject(c);
		out.flush();

		return bos.toByteArray();
	}

    static byte[] hash(byte[] input) {
		byte[] output = new byte[0];
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
		
			output = md.digest(input);
			return output;
		} catch (NoSuchAlgorithmException e) {
			System.err.println("No such algorithm when hashing");
		}
		return output;
	}

	static String findMostFrequentChain (Map<String, LinkedList<Chain>> hashToChain) {
		int maxLength = 0;
		String currentLongest = null;

		for(String hash : hashToChain.keySet()) {
			LinkedList<Chain> chains = hashToChain.get(hash);

			if (maxLength < chains.size()) {
				maxLength = chains.size();
				currentLongest = hash;
			}
		}

		return currentLongest;
	}

	static void printByteArray (byte[] bytes) {
		for (byte b : bytes) {
			System.out.print(b + " ");
		}
		System.out.println();
	}
}
