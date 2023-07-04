package example;

import java.util.ArrayList;
import java.util.List;

/*
Класс для хранения информации о найденных и перемещенных фалах
* */
public class InformationMovedFiles {

private List<FileInfo> list;

    public InformationMovedFiles() {
        list=new ArrayList<>();
    }



    public List<FileInfo> getList() {
        return list;
    }
}
