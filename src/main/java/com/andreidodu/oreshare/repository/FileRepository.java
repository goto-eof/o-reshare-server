package com.andreidodu.oreshare.repository;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public interface FileRepository {
    void writeDocumentToFile(String filename, InputStream inputStream) throws IOException;

    File retrieveFile(String filename);
}
