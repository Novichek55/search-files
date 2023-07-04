package example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.RecursiveAction;

public class Task extends RecursiveAction {
    private Path target = Path.of("C:\\Users\\Aleksandr\\Desktop\\temp");
    private int sizeFile;
    private List<File> allDirectory;
    private int start;
    private int end;
    private InformationMovedFiles informationMovedFiles;
    private final int threshold = 1;

    public Task(int sizeFile, List<File> allDirectory, InformationMovedFiles informationMovedFiles, int start, int end) {
        this.sizeFile = sizeFile;
        this.allDirectory = allDirectory;
        this.informationMovedFiles = informationMovedFiles;
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute() {
        if ((end - start) == threshold) {
            for (int i = start; i < end; i++) {
                File[] files = allDirectory.get(start).listFiles();
                for (File file : files) {
                    if (file.isFile() && file.length() > sizeFile) {
                        try {
                            System.out.println(Thread.currentThread().getName() + " Перемещаем " + file);
                            informationMovedFiles.getList().add(new FileInfo(file.toPath(), file.length()));
                            Files.move(file.toPath(), target.resolve(file.getName()));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }

        } else {
            int middle = (start + end) / 2;
            invokeAll(new Task(sizeFile, allDirectory, informationMovedFiles, start, middle)
                    , new Task(sizeFile, allDirectory, informationMovedFiles, middle, end));
        }
    }
}
