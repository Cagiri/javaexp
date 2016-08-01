package javaexp.com.fileExp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileOperator {

	final static String fileReadPath = "";
	final static String fileWritePath = "";

	public static void main(String[] args) {
		String fileName = "";
		String clob = readFile(fileReadPath, fileName);
		writeFile(fileWritePath, fileName,clob);

	}

	public static String readFile(String filePath, String fileName) {
		String clob = "";
		Path p = Paths.get(fileReadPath + fileName);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(p.toFile()));
			String thisLine = null;
			System.out.println("FILE NAME : " + p.toFile().getName());
			while ((thisLine = br.readLine()) != null) {
				clob = clob.concat(thisLine);
				System.out.println(thisLine);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return clob;
	}

	public static void writeFile(String filePath, String fileName,String clob) {
		Path p = Paths.get(fileReadPath + fileName);
		BufferedWriter bw = null;
		BufferedReader br = null;
		try {
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Paths.get(fileWritePath + fileName).toFile()),"UTF-8"));
			String thisLine = null;
			br = new BufferedReader(new FileReader(p.toFile()));
			while ((thisLine = br.readLine()) != null) {
				bw.write(thisLine);
				bw.newLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
