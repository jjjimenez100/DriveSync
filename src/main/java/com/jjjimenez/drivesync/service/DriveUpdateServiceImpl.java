package com.jjjimenez.drivesync.service;

import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;

final class DriveUpdateServiceImpl implements DriveUpdateService {
    private static final Logger logger = LoggerFactory.getLogger(DriveUploadServiceImpl.class);

    private final Drive drive;

    DriveUpdateServiceImpl(Drive drive) {
        this.drive = drive;
    }

    @Override
    public Object updateFile(File file, FileContent fileContent, String fileId) throws IOException {
        Drive.Files.Update updateRequest = drive.files().update(fileId, file, fileContent);

        updateRequest.execute();
        logger.info(updateRequest.getLastStatusCode() + " " + updateRequest.getLastStatusMessage());

        return updateRequest.getJsonContent();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DriveUpdateServiceImpl that = (DriveUpdateServiceImpl) o;
        return drive.equals(that.drive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(drive);
    }

    @Override
    public String toString() {
        return "DriveUpdateServiceImpl{" +
                "drive=" + drive +
                '}';
    }
}
