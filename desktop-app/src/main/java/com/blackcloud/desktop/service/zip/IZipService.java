package com.blackcloud.desktop.service.zip;

import java.io.IOException;
import java.nio.file.Path;

public interface IZipService {
    void zip(Path srcPath, Path dstPath) throws IOException;
    void unzip(Path srcPath, Path dstPath) throws IOException;
}
