package com.blackcloud.desktop.service.zip;

import java.io.IOException;
import java.nio.file.Path;

public interface IZipService {
    String zip(Path srcPath) throws IOException;
    void unzip(Path srcPath) throws IOException;
}
