package com.andreidodu.oreshare.util;

import java.io.File;

public class FileUtil {
    public static boolean createDirectoriesRecursively(String path){
        return (new File(path)).mkdirs();
    }
}