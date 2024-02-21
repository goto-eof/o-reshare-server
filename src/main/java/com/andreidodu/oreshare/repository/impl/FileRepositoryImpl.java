package com.andreidodu.oreshare.repository.impl;

import com.andreidodu.oreshare.exception.ApplicationException;
import com.andreidodu.oreshare.repository.FileRepository;
import com.andreidodu.oreshare.util.FileUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

@Repository
public class FileRepositoryImpl implements FileRepository {

    public static final int BUFFER_SIZE = 1024;
    @Value("${o-reshare.files.path}")
    private String documentsPath;

    @Override
    public void writeDocumentToFile(String filename, InputStream inputStream) throws IOException {
        createDirectoriesRecursivelyIfNecessary();
        Path path = calculateFileFullPathWithFilename(filename);
        File file = path.toFile();
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, bytesRead);
        }
        IOUtils.closeQuietly(inputStream);
        IOUtils.closeQuietly(bos);
    }

    @Override
    public File retrieveFile(String filename) {
        return new File(documentsPath + '/' + filename);
    }

    private Path calculateFileFullPathWithFilename(String filename) {
        return Paths.get(documentsPath, filename);
    }

    private void createDirectoriesRecursivelyIfNecessary() {
        if (!(new File(documentsPath).exists()) && !createDirectoriesRecursively()) {
            throw new ApplicationException("Unable to create directories recursively for files: " + documentsPath);
        }
    }

    private boolean createDirectoriesRecursively() {
        return FileUtil.createDirectoriesRecursively(documentsPath);
    }
}
