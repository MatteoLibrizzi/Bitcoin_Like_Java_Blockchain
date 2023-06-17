import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;

public class Block implements Serializable {

	public int[] timeStamp = new int[7];
	private byte[] hashPrevious;
	private int nonce;
	private byte[] merkleRoot;

	public Block(byte[] merkleRoot, byte[] hashPrevious) throws IOException,
			NoSuchAlgorithmException {// builder
		this.setTimeStamp();
		this.hashPrevious = hashPrevious;
		this.nonce = 0;
		this.merkleRoot = merkleRoot;

		this.changeNonceUntilBlockIsValid();
	}

	public Block(Block b) {// copy builder
		timeStamp = b.timeStamp;
		hashPrevious = b.hashPrevious;
		nonce = b.nonce;
		merkleRoot = b.merkleRoot;
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
				merkleRoot == b.merkleRoot;
	}

	public byte[] getPrevHash() {
		return this.hashPrevious;
	}

	private void changeNonceUntilBlockIsValid() throws NoSuchAlgorithmException, IOException {
		while (!isBlockValid(this, this.nonce)) {
			this.changeNonce();
		}
	}

	private void changeNonce() throws IOException, NoSuchAlgorithmException {
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

	public byte[] getBytes() throws IOException {// returns byte[] of the block considering all the attributes (caused a
													// lot of trouble)
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(bos);
		out.writeObject(this);// writes in the object output stream this
		out.flush();// transfers the date from the obj out stream to byte[] out stream, transforming
					// its type in the process
		return bos.toByteArray();
	}

	public void printTime() {// prints time the block was created (debug use)
		for (int i = 0; i < this.timeStamp.length; i++) {
			System.out.println(this.timeStamp[i]);
		}
	}

	public static byte[] findBlockHash(Block block) throws IOException, NoSuchAlgorithmException {
		byte[] b = block.getBytes();

		return Utils.hash(b);
	}

	public static byte[] byteGen() {// GENERATES random bytes
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[2];
		random.nextBytes(salt);
		return salt;
	}

	private static boolean isBlockValid(Block block, int difficulty) throws IOException {
		byte[] h = block.getPrevHash();
		byte valueToMatch = (byte) 0;

		for (int i = 0; i < difficulty; i++) {
			if(Byte.compare(h[0], valueToMatch) != 0) {
				return false;
			}
		}
		return true;
	}
}