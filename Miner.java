import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Miner extends Thread {
	// TODO should create a way for miners to communicate their existence to the others
	// TODO Nodes always consider the longest chain to be the correct one and will keep working on extending it
	Chain chain;// TODO Chain should not be centralized, each miner should have its own
	byte[] data;

	public Miner(Chain chain, byte[] data) {
		this.chain = chain;
		this.data = data;
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

	@Override
	public void run() {
		try {
			byte[] hdata = hash(this.data);//transforms the data to an hash so no matter its length, the stored length is fixed
			Block block=null;
			do{
				Block last = this.chain.getLast();
				byte[] lasth = last.getHash();//stores the hash of the last block of the chain

			
				block = new Block(hdata, lasth);//creates a new block with the hashed data and the hash of the last block to make it legitimate

			}while(!this.chain.addBlock(block));
			System.out.println("Successful inserting - "+new String(this.data));

		} catch (NoSuchAlgorithmException | IOException e) {
			e.printStackTrace();
		}
	}
}