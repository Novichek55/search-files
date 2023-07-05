package example;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FindAllDirectory {
    private File dir;
    private List<File> directoryList;

    public FindAllDirectory(File dir) {
        this.dir = dir;
        directoryList = new ArrayList<>();
        if (dir.isDirectory()) {
            directoryList.add(dir);
            findAllDirectory(dir);
        }
    }

    public List<File> getDirectoryList() {
        return directoryList;
    }

    public void findAllDirectory(File dir) {
        File[] files = dir.listFiles((file) -> {
            return file.isDirectory();
        });
        for (File file : files) {
            directoryList.add(file);
            findAllDirectory(file);
        }
    }
}


