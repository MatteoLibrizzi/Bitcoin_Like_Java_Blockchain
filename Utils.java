import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Utils {
    public static byte[] generateRandomByteArray(int size) {// GENERATES SALT
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[size];
		random.nextBytes(salt);
		return salt;
	}

    public static String byteArrayToHex(byte[] input) {
		StringBuilder sb = new StringBuilder();

		for (byte b : input) {
			sb.append(Integer.toHexString(0xFF & b));
		}

		return sb.toString();
	}

    static byte[] hash(byte[] input) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");

		byte[] output = md.digest(input);

		return output;
	}
}
