package example;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

public class Main {
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
        /*
        Поиск всех папок в каталоге
        * */
        FindAllDirectory findAllDirectory = new FindAllDirectory(directory);
        List<File> allDirectory = findAllDirectory.getDirectoryList();
        allDirectory.add(directory);

        /*
        Объект для хранения результатов найденных файлов
        * */
        InformationMovedFiles infoMovedFiles = new InformationMovedFiles();

        ForkJoinPool fjp = new ForkJoinPool();
        Task task = new Task(sizeFile, allDirectory, infoMovedFiles, 0, allDirectory.size());
        fjp.invoke(task);

        /*
        Сортировка списка с информацией о найденных файлах
        * */
        List<FileInfo> list = infoMovedFiles.getList();
        Collections.sort(list, (a, b) -> {
            return ((int) a.getSize() - (int) b.getSize()) * (-1);
        });
        for (FileInfo f : list)
            System.out.println(f);

    }
}
