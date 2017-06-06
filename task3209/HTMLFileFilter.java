package com.javarush.task.task32.task3209;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * Created by Мои документы on 06.06.2017.
 */
public class HTMLFileFilter  extends FileFilter{
    @Override
    public boolean accept(File f) {
        String file = f.getName().toLowerCase();
        return f.isDirectory() || file.endsWith(".html") || file.endsWith(".htm");
    }

    @Override
    public String getDescription() {
        return "HTML и HTM файлы";
    }
}
