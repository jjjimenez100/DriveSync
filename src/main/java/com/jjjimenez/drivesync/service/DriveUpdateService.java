package com.jjjimenez.drivesync.service;

import com.google.api.client.http.FileContent;
import com.google.api.services.drive.model.File;

import java.io.IOException;

public interface DriveUpdateService {
    Object updateFile(File file, FileContent fileContent, String fileId) throws IOException;
}
