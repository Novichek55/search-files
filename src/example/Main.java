package example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

public class Main {
    static Path targetPath = Path.of("C:\\Users\\Aleksandr\\Desktop\\temp");

    public static void main(String[] args) {

        File directory = null;
        int sizeFile = 0;

        if (args.length < 2) {
            System.out.println("Specify the directory to search for files and the file size");
        } else {
            directory = new File(args[0]);
            try {
                sizeFile = Integer.parseInt(args[1]) * 1024;
            } catch (NumberFormatException e) {
                System.out.println("Invalid file size format specified");
                e.printStackTrace();
            }
        }

        List<File> allDirectory = null;
        if (directory.isDirectory()) {
        /*
        Поиск всех папок в каталоге указанном пользователем
        * */
            FindAllDirectory findAllDirectory = new FindAllDirectory(directory);
            allDirectory = findAllDirectory.getDirectoryList();

        /*
        Объект для хранения информации о всех найденных файлах
        * */
            InformationMovedFiles infoMovedFiles = new InformationMovedFiles();

            ForkJoinPool fjp = new ForkJoinPool();
            Task task = new Task(sizeFile, allDirectory, infoMovedFiles, 0, allDirectory.size());
            fjp.invoke(task);

        /*
        Сортировка списка с информацией о найденных файлах
        * */
            List<FileInfo> list = infoMovedFiles.getList();
            if (list.size() != 0) {
                Collections.sort(list, (a, b) -> {
                    return ((int) b.getSize() - (int) a.getSize());
                });
                for (FileInfo f : list)
                    System.out.println(f);
            } else {
                System.out.println("No files of the specified size were found");
            }


        } else if (directory.isFile()) {
            if (directory.length() > sizeFile) {
                try {
                    System.out.println(new FileInfo(directory.toPath(), directory.length()));
                    Files.move(directory.toPath(), targetPath.resolve(directory.getName()));
                    System.out.println();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else
                System.out.println("No files of the specified size were found");
        } else
            System.out.println("No files of the specified size were found");
    }
}
