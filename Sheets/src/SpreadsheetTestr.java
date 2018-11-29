

import java.io.File;

public class SpreadsheetTestr {
	public static void main(String[] args) {
		Sheet newSheet = new Sheet(new File("users.csv"));
		newSheet.prettyPrintSheet();
		//System.out.println(newSheet.getCell(2, 1).value);
	}
}
