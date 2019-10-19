package com.jjjimenez.drivesync.service;

import com.google.api.services.drive.Drive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;

final class DriveDeleteServiceImpl implements DriveDeleteService {
    private static final Logger logger = LoggerFactory.getLogger(DriveUploadServiceImpl.class);

    private final Drive drive;

    DriveDeleteServiceImpl(Drive drive) {
        this.drive = drive;
    }

    @Override
    public Object deleteFile(String fileId) throws IOException {
        Drive.Files.Delete deleteRequest = drive.files().delete(fileId);
        logger.info(deleteRequest.getLastStatusCode() + " " + deleteRequest.getLastStatusMessage());

        return deleteRequest.getJsonContent();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DriveDeleteServiceImpl that = (DriveDeleteServiceImpl) o;
        return drive.equals(that.drive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(drive);
    }

    @Override
    public String toString() {
        return "DriveDeleteServiceImpl{" +
                "drive=" + drive +
                '}';
    }
}
