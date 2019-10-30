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


@Component
public class TextFileLoaderHelper {

	@Autowired
	private SearchRepository searchRepository;

	  public  void read(File file) throws IOException {
	    Scanner scanner = new Scanner(file);
	    Stack<Directory> directoryStack = new Stack<>();
	    Directory root = new Directory(scanner.nextLine(), null);
	    directoryStack.add(root);
	    while (scanner.hasNextLine()) {
	      processLine(scanner.nextLine(), directoryStack);
	    }

	    printTree(root, " ");
	  }

	  private static void processLine(String line, Stack<Directory> directoryStack) {
	    int nLeadingSpaces = getNumberOfLeadingSpaces(line);
	    if (nLeadingSpaces == -1) return;
	    int depth = nLeadingSpaces;
	    while (directoryStack.size() > depth) {
	      directoryStack.pop(); 
	    }
	    String dirName = line.substring(nLeadingSpaces);
	    Directory directory = new Directory(dirName, directoryStack.peek().getName());
	    directoryStack.peek().getChildren().add(directory);
	    directoryStack.push(directory);
	  }

	  private static int getNumberOfLeadingSpaces(String line) {
		   String[] lengthStrings= line.split("\t");
	    return lengthStrings.length-1;
	  }
	  
	  private  void printTree(Directory node, String appender) {
		  
		   System.out.println(appender + node.getName());
		   searchRepository.createFileSystem((appender + node.getName()).replaceAll("\\s+", ""));
		   node.getChildren().forEach(each ->  printTree(each, appender + each.parent + "."));
		 }
	  
	  public static class Directory {
	    private List<Directory> children = new ArrayList<>();
	    private String parent = null;

	    private final String name;

	    public Directory(String name, String parent) {
	      this.name = name;
	      this.parent = parent;
	    }

	    public String getName() {
	      return name;
	    }

	    public List<Directory> getChildren() {
	      return children;
	    }
	    
	  }
}
