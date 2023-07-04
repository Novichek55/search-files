package example;

import java.nio.file.Path;

/*
Класс для хранения информации о файле (абсолютный путь и размер файла)
* */
public class FileInfo {
    private Path path;
    private double size;

    public FileInfo(Path path, double size) {
        this.path = path;
        this.size = size;
    }

    public Path getPath() {
        return path;
    }

    public double getSize() {
        return size;
    }



    @Override
    public String toString() {
        double Kb = 1024;
        double Mb = 1024 * Kb;
        double Gb = 1024 * Mb;

        if (size > Gb) {
            String result = String.format("%.1f", size / Gb);
            return path + "     " + result + "Gb";
        } else if (size > Mb) {
            String result = String.format("%.1f", size / Mb);
            return path + "      " + result + "Mb";
        } else if (size > Kb) {
            String result = String.format("%.1f", size / Kb);
            return path + "      " + result + "Kb";
        }
        return path + "      " + size + "Byte";
    }
}
