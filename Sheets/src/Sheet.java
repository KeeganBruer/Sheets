

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Sheet {
	private ArrayList<ArrayList<Cell>> sheet = new ArrayList<ArrayList<Cell>>();
	private File sheetFile;
	public Sheet(File file) {
		this.sheetFile = file;
		this.importSpreadSheet(this.sheetFile);
	}
	
	public Cell getCell(int row, int col) {
		try {
			return sheet.get(col).get(row);
		} catch (Exception e) {
			for (int c = sheet.size(); c < col; c++) {
				sheet.add(new ArrayList<Cell>());
			}
			for (int r = sheet.get(col).size(); r < row; r++) {
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void printSheet() {
		System.out.println(this.sheet.size() + " " + this.sheet.get(0).size());
		for (ArrayList<Cell> list : this.sheet) {
			for (Cell c : list) {
				System.out.print("\"" + c.value + "\"");
			}
			System.out.println();
		}
	}
}
