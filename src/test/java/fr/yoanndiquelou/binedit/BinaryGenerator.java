package fr.yoanndiquelou.binedit;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class BinaryGenerator {

	public static void main(String[] args) throws IOException {
		FileOutputStream fos = new FileOutputStream(new File("test.bin"));

		byte[] array = new byte[256];
		for (int i = 0; i < 256; i++) {
			array[i] = (byte) i;
		}
		int it = (1024 * 1024 * 3)/255;
		while (it-- > 0) {
			fos.write(array);
		}
		fos.close();
	}

}
