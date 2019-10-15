package com.jjjimenez.drivesync.upload;

import com.google.api.client.http.FileContent;
import com.google.api.services.drive.model.File;

import java.io.IOException;

interface UploadService {
    File uploadFile(File file, FileContent fileContent, boolean resumableUpload) throws IOException;
    File updateFile(File file, FileContent fileContent, String fileId) throws IOException;
}