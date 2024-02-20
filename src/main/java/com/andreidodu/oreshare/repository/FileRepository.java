package com.andreidodu.oreshare.repository;

import java.io.IOException;
import java.io.InputStream;

public interface FileRepository {
    void writeDocumentToFile(String filename, InputStream inputStream) throws IOException;

    byte[] retrieveFileContent(String filename) throws IOException;
}
