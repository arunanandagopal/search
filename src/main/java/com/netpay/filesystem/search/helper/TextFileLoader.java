package com.netpay.filesystem.search.helper;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netpay.filesystem.search.repository.SearchRepository;

/**
 * This Class will be loaded after every Spring Initialization and will load the
 * directory from the provided Text File
 * 
 * @author aruna
 *
 */
@Component
public class TextFileLoader {

	@Autowired
	private SearchRepository searchRepository;

	public void loadFile(File file) throws IOException {
		Scanner scanner = new Scanner(file);
		Stack<DirectoryStructure> directoryStack = new Stack<>();
		DirectoryStructure directoryStructure = new DirectoryStructure(scanner.nextLine(), null);
		directoryStack.add(directoryStructure);
		while (scanner.hasNextLine()) {
			processLine(scanner.nextLine(), directoryStack);
		}
		scanner.close();
		saveDirectory(directoryStructure, " ");
	}

	private static void processLine(String line, Stack<DirectoryStructure> directoryStack) {
		int nLeadingTabs = getNumberOfLeadingTabs(line);
		if (nLeadingTabs == -1)
			return;
		int depth = nLeadingTabs;
		while (directoryStack.size() > depth) {
			directoryStack.pop();
		}
		String dirName = line.substring(nLeadingTabs);
		DirectoryStructure directory = new DirectoryStructure(dirName, directoryStack.peek().getName());
		directoryStack.peek().getChildren().add(directory);
		directoryStack.push(directory);
	}

	private static int getNumberOfLeadingTabs(String line) {
		String[] lengthStrings = line.split("\t");
		return lengthStrings.length - 1;
	}

	private void saveDirectory(DirectoryStructure node, String appender) {
		searchRepository.createFileSystem((appender + node.getName()).replaceAll("\\s+", ""));
		node.getChildren().forEach(each -> saveDirectory(each, appender + each.parent + "."));
	}

	public static class DirectoryStructure {
		private List<DirectoryStructure> children = new ArrayList<>();
		private String parent = null;

		private final String name;

		public DirectoryStructure(String name, String parent) {
			this.name = name;
			this.parent = parent;
		}

		public String getName() {
			return name;
		}

		public List<DirectoryStructure> getChildren() {
			return children;
		}

	}
}
