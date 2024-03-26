package Locks;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Lock {

    private boolean isLocked;

    public Lock() {
        this.isLocked = false;
    }


    public boolean getLock(String lockType) {
        try {
            Scanner fileScanner = new Scanner(new File("src/Locks/" + getFile(lockType) + ".txt"));

            if (fileScanner.nextLine().equals("locked")) {
                this.isLocked = true;
            } else {
                this.isLocked = false;
            }

            fileScanner.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return this.isLocked;
    }

    public void setLock(String lockType, String lock) {
        try {
            FileWriter writer = new FileWriter("src/Locks/" + getFile(lockType) + ".txt");
            writer.write(lock);

            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getFile(String lockType) {
        String file = "";

        if (lockType.equals("write")) {
            file = "WriteLock";
        } else {
            file = "ReadLock";
        }

        return file;
    }
}
