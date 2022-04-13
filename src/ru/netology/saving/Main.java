package ru.netology.saving;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {

    static final String absoluteNameSaving = "K:/Games/savegames/";
    static final String nameZip = "saving.zip";

    public static void main(String[] args) {
        openZip(absoluteNameSaving + nameZip, absoluteNameSaving);
        for (File file : new File(absoluteNameSaving).listFiles()) {
            String fileName = file.getAbsolutePath();
            if (fileName.substring(fileName.lastIndexOf(".") + 1).equals("data")) {
                GameProgress obj = openProgress(fileName);
            }
        }
    }

    public static void openZip(String absolutePath, String dirName) {
        try (ZipInputStream zip = new ZipInputStream(new FileInputStream(absolutePath))) {
            ZipEntry entry;
            String name;
            while ((entry = zip.getNextEntry()) != null) {
                name = entry.getName();
                FileOutputStream fout = new FileOutputStream(dirName + name);
                for (int c = zip.read(); c != -1; c = zip.read()) {
                    fout.write(c);
                }
                fout.flush();
                zip.closeEntry();
                fout.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static GameProgress openProgress(String savingPath) {
        GameProgress gp = null;
        try (FileInputStream fis = new FileInputStream(savingPath);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            gp = (GameProgress) ois.readObject();
            System.out.println(gp.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return gp;
    }
}