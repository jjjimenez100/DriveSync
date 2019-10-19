package com.jjjimenez.drivesync.service;

import java.io.IOException;
import java.io.OutputStream;

public interface DriveDownloadService {
    Object downloadFile(String fileId, OutputStream outputStream, boolean resumableDownload) throws IOException;
}
