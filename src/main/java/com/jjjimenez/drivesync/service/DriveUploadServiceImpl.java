package com.jjjimenez.drivesync.service;

import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.googleapis.media.MediaHttpUploaderProgressListener;
import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;

final class DriveUploadServiceImpl implements DriveUploadService {
    private static final Logger logger = LoggerFactory.getLogger(DriveUploadServiceImpl.class);

    private final Drive drive;
    private final MediaHttpUploaderProgressListener uploaderProgressListener;

    DriveUploadServiceImpl(Drive drive, MediaHttpUploaderProgressListener uploaderProgressListener) {
        this.drive = drive;
        this.uploaderProgressListener = uploaderProgressListener;
    }

    @Override
    public Object uploadFile(File file, FileContent fileContent, boolean resumableUpload) throws IOException {
        Drive.Files.Create uploadRequest = drive.files().create(file, fileContent);
        MediaHttpUploader uploader = uploadRequest.getMediaHttpUploader();

        uploader.setDirectUploadEnabled(resumableUpload);
        uploader.setProgressListener(uploaderProgressListener);

        uploadRequest.execute();
        logger.info(uploadRequest.getLastStatusCode() + " " + uploadRequest.getLastStatusMessage());

        return uploadRequest.getJsonContent();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DriveUploadServiceImpl that = (DriveUploadServiceImpl) o;
        return drive.equals(that.drive) &&
                uploaderProgressListener.equals(that.uploaderProgressListener);
    }

    @Override
    public int hashCode() {
        return Objects.hash(drive, uploaderProgressListener);
    }

    @Override
    public String toString() {
        return "DriveUploadServiceImpl{" +
                "drive=" + drive +
                ", uploaderProgressListener=" + uploaderProgressListener +
                '}';
    }
}
