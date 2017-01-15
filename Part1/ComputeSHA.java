import java.io.*;
import java.security.*;

public class ComputeSHA {
	public static String Hash(String inFile) throws Exception {
		InputStream file = new FileInputStream(inFile);
		byte[] bytes = new byte[file.available()];

		// Create a MessageDigest object for hashing
		MessageDigest md = MessageDigest.getInstance("SHA-1");

        file.read(bytes);
        md.update(bytes);

        file.close();
		byte[] hash = md.digest();

		StringBuilder sb = new StringBuilder();
		for(byte b : hash){
			sb.append(Integer.toString( ( b & 0xff ) + 0x100, 16).substring(1));
		}
		return sb.toString();
	}


	public static void main(String[] args) {
		try {
			if(args.length == 1) {
				System.out.println(Hash(args[0]));
			} else {
				System.out.println("invalid number of files.");
			}
		} catch (Exception error) {
			error.printStackTrace();
		}
	}
}
