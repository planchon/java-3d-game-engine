package Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileUtil {
	public String path;
	public String content = "";
	
	public FileUtil(String path) {
		try {
			File file = new File(path);
			Scanner scanner;
			scanner = new Scanner(file);

			while (scanner.hasNextLine()) {
				String data = scanner.nextLine();
				this.content += data + "\n";
			}
			
			scanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("[file] error with file " + path + ", error : ");
			e.printStackTrace();
		}
	}
	
	public String get_content() {
		return this.content;
	}
}
