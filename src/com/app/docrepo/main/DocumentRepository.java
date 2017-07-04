/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.docrepo.main;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author Anish Panthi
 */
public class DocumentRepository {

    private static int rootLength;

    public DocumentRepository(int rootLength) {
        DocumentRepository.rootLength = rootLength;
    }

    /**
     * This method recursively list all the files and directories and then
     * prints in the specified format.
     *
     * @param dir The current directory or file
     * @param tabs Number of tabs to be applied before printing
     */
    public void listAllFilesAndDirectories(File dir, int tabs) {
        if (dir != null) {
            if (dir.isDirectory()) {
                for (int i = 0; i < tabs; i++) {
                    System.out.print("  ");
                }
                System.out.println("- Project: " + dir.getName() + " - URL: " + dir.getAbsolutePath().substring(rootLength));
                List<String> sortedList = null;
                String s[] = dir.list();
                //sortedList = sort(s);
                sortedList = sortExtension(s);
                for (String item : sortedList) {
                    tabs++;
                    File f = new File(dir + "/" + item);
                    if (f.isDirectory()) {
                        listAllFilesAndDirectories(f, tabs);
                        tabs--;
                    } else {
                        File file = new File(dir + "/" + item);
                        displayFile(file, tabs);
                        tabs--;
                    }
                }
            } else {
                System.out.println("Need directory/folder name.");
            }
        } else {
            System.out.println(dir + " is not a directory");
        }
    }

    /**
     * Displays a document file in the specified format
     *
     * @param file Document File name
     * @param tabs Number of tabs to be applied before printing
     */
    public void displayFile(File file, int tabs) {
        String s = file.getName();
        String[] split = s.split("\\.");
        for (int i = 0; i < tabs; i++) {
            System.out.print("  ");
        }
        System.out.println("- Document: " + file.getName() + " - Extension: ." + split[split.length - 1] + " - URL: " + file.getAbsolutePath().substring(rootLength));
    }

    /**
     * Sorts the document files on the basis of its extension.
     *
     * @param s Array of Strings
     * @return List of Strings
     */
    public List<String> sortExtension(String[] s) {
        List<String> orgList = new CopyOnWriteArrayList<>(Arrays.asList(s));
        Set<String> uniqueExtension = new TreeSet<>();

        for (String item : s) {
            if (item.contains(".")) {
                String[] split = item.split("\\.");
                String temp = "." + split[split.length - 1];
                uniqueExtension.add(temp);
            }
        }

        List<String> finalListOfAllFiles = new LinkedList<>();
        uniqueExtension.stream().forEach((s1) -> {
            for (int i = 0; i < orgList.size(); i++) {
                if (orgList.get(i).contains(s1)) {
                    finalListOfAllFiles.add(orgList.get(i));
                    orgList.remove(orgList.get(i));
                    i--;
                }
            }
        });
        
        orgList.stream().filter((s1) -> (!finalListOfAllFiles.contains(s1))).forEach((s1) -> {
            finalListOfAllFiles.add(s1);
        });

//        for (String s1 : orgList) {
//            if (!finalListOfAllFiles.contains(s1)) {
//                finalListOfAllFiles.add(s1);
//            }
//        }
        return finalListOfAllFiles;
    }

//      public List<String> sort(String[] s) {
//        List<String> copyOnWriteList = new CopyOnWriteArrayList<>(Arrays.asList(s));
//        Collections.sort(copyOnWriteList, (String o1, String o2) -> o1.compareTo(o2));
//        return copyOnWriteList;
//    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        File rootDirectory = new File("Main Project");

        //This variable is used to track the root directory only
        int directoryFileLength = rootDirectory.getAbsolutePath().length() - rootDirectory.getName().length();

        DocumentRepository dla = new DocumentRepository(directoryFileLength);
        dla.listAllFilesAndDirectories(rootDirectory, 0);
    }

}
