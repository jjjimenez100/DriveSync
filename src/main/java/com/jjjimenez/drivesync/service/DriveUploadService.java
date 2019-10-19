package com.jjjimenez.drivesync.service;

import com.google.api.client.http.FileContent;
import com.google.api.services.drive.model.File;

import java.io.IOException;

public interface DriveUploadService {
    Object uploadFile(File file, FileContent fileContent, boolean resumableUpload) throws IOException;
}