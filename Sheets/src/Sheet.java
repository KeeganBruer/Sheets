

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Sheet {
	private ArrayList<ArrayList<Cell>> sheet = new ArrayList<ArrayList<Cell>>();
	private File sheetFile;
	public Sheet(File file) {
		this.sheetFile = file;
		if (!this.sheetFile.exists()) {
			try {
				this.sheetFile.createNewFile();
			} catch (IOException e) {
				System.out.println("ERROR: Spreadsheet file does not exist and could not be created.");
			}
		}
		this.importSpreadSheet(this.sheetFile);
	}
	
	public Cell getCell(int row, int col) {
		try {
			return sheet.get(col).get(row);
		} catch (Exception e) {
			for (int c = sheet.size()-1; c < col; c++) {
				sheet.add(new ArrayList<Cell>());
			}
			for (int r = sheet.get(col).size()-1; r < row; r++) {
				Cell cell = new Cell("");
				cell.setPosition(row, col);
				sheet.get(col).add(cell);
			}
			return sheet.get(col).get(row);
		}
	}
	
	public void setItem(int row, int col, String value) {
		try {
			this.sheet.get(col).add(row, new Cell(row, col, value));
		} catch (Exception e) {
			for (int c = sheet.size(); c <= col; c++) {
				sheet.add(new ArrayList<Cell>());
			}
			for (int r = sheet.get(col).size(); r < row; r++) {
				Cell cell = new Cell("");
				cell.setPosition(row, col);
				sheet.get(col).add(cell);
			}
			this.sheet.get(col).add(row, new Cell(row, col, value));
		}
	}
	
	private void importSpreadSheet(File file) {
		try {
			Scanner scan = new Scanner(file);
			int currentRowIndex = 0;
			while(scan.hasNextLine()) {
				//currentRowIndex++;
				String[] line = scan.nextLine().split("");
				String entry = "";
				int currentColumnIndex = 0;
				for (int i = 0; i < line.length; i++) {
					if (line[i].equals("\"")) {
						i += 1;
						while (!line[i].equals("\"")) {
							entry += line[i];
							i += 1;
						}
					} else if (line[i].equals(",")) {
						this.setItem(currentColumnIndex,currentRowIndex, entry.trim());
						entry = "";
						currentColumnIndex += 1;
					} else {
						entry += line[i];
					}
				}
				this.setItem(currentColumnIndex,currentRowIndex, entry.trim());
				entry = "";
				currentColumnIndex += 1;
				currentRowIndex += 1;
			}
			int currentLongest = this.getLongestRowSize();
			for (int r = 0; r < this.sheet.size(); r++) {
				for (int i = this.sheet.get(r).size(); i < currentLongest; i++) {
					Cell cell = new Cell(" ");
					cell.setPosition(r, i);
					this.sheet.get(r).add(cell);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void printSheet() {
		for (ArrayList<Cell> list : this.sheet) {
			for (Cell c : list) {
				System.out.print("\"" + c.value + "\"");
			}
			System.out.println();
		}
	}
	
	public void prettyPrintSheet() {
		int longestLength = 0;
		for (ArrayList<Cell> list : this.sheet) {
			for (Cell c : list) {
				if (c.value.split("").length > longestLength)
					longestLength = c.value.split("").length;
			}
		}
		int counter = 0;
		for (int i = 0; i < this.getLongestRowSize(); i++) {
			String[] array = new String[longestLength];
			int count = array.length-1;
			for (int x = (counter+"").split("").length-1; x >= 0; x--) {
				array[count] = (counter+"").split("")[x];
				count--;
			}
			String printString = "";
			for (String s : array) {
				if (s == null) {
					printString += " ";
				} else {
					printString += s;
				}
			}
			System.out.print("| " + printString + " |");
			counter++;
		}
		System.out.println();
		for (int i = 0; i < this.getLongestRowSize(); i++) {
			for (int j = 0; j < longestLength+4; j++) {
				System.out.print("-");
			}
		}
		System.out.println();
		for (ArrayList<Cell> list : this.sheet) {
			for (Cell c : list) {
				String[] array = new String[longestLength];
				int count = array.length-1;
				for (int i = c.value.split("").length-1; i >= 0; i--) {
					array[count] = c.value.split("")[i];
					count--;
				}
				String printString = "";
				for (String s : array) {
					if (s == null) {
						printString += " ";
					} else {
						printString += s;
					}
				}
				System.out.print("| " + printString + " |");
			}
			System.out.println();
			for (int i = 0; i < list.size(); i++) {
				for (int j = 0; j < longestLength+4; j++) {
					System.out.print("-");
				}
			}
			System.out.println();
		}
	}

	private int getLongestRowSize() {
		int longest = 0;
		for (ArrayList<Cell> list : this.sheet) {
			if (list.size() > longest)
				longest = list.size();
		}
		return longest;
	}
}
