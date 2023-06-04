package com.ifx.account.util;

import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileTest {


    public static void main(String[] args) {
        String path = "D:\\project\\myproject\\IFxIM\\1.txt";

        String data = "userName";

        BufferedWriter writer = null;
        try {
            File file = new File(path);
            boolean writeAvailable = file.canWrite();
            if (!writeAvailable){
                throw new RuntimeException("File is not Writeable , please checkout current file permission!");
            }
            writer = new BufferedWriter(new FileWriter(file));
            writer.write(data);
            System.out.println("Data has been written to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    System.out.println("An error occurred while closing the writer: " + e.getMessage());
                }
            }
        }
    }
    @Test
    public void writeFile(){

        String path = " D:\\project\\myproject\\IFxIM\\1.txt";

        String data = "userName";
        BufferedWriter writer = null;
        try {
            File file = new File(path);
            writer = new BufferedWriter(new FileWriter(file));
            writer.write(data);
            System.out.println("Data has been written to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    System.out.println("An error occurred while closing the writer: " + e.getMessage());
                }
            }
        }
    }
}
