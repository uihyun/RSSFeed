import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager {
	private FileWriter fw;
	private BufferedWriter bw;
	private static FileManager instance;
	
	public static FileManager getInsatnce() {
		if (instance == null)
			instance = new FileManager();
		return instance;
	}

	private FileManager() {}

	public void openWriter() {
		try {
			fw = new FileWriter("feed.txt");
			bw = new BufferedWriter(fw);
		} catch (IOException e) {
			System.out.println("[ERROR] " + e.getMessage());
			throw new RuntimeException(e);
		}
	}

	public void writeFeedToFile(String text) {
		try {
			bw.write(text);
		} catch (IOException e) {
			System.out.println("[ERROR] " + e.getMessage());
			throw new RuntimeException(e);
		}
	}

	public void closeWriter() {
		try {
			if (bw != null)
				bw.close();
			if (fw != null)
				fw.close();
		} catch (IOException e) {
			System.out.println("[ERROR] " + e.getMessage());
			throw new RuntimeException(e);
		}
	}
}
