package com.jjjimenez.drivesync.upload;

import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.googleapis.media.MediaHttpUploaderProgressListener;
import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

final class UploadServiceImpl implements UploadService {
    private static final Logger logger = LoggerFactory.getLogger(UploadServiceImpl.class);

    private final Drive drive;
    private final MediaHttpUploaderProgressListener uploaderProgressListener;

    UploadServiceImpl(Drive drive, MediaHttpUploaderProgressListener uploaderProgressListener) {
        this.drive = drive;
        this.uploaderProgressListener = uploaderProgressListener;
    }

    public File uploadFile(File file, FileContent fileContent, boolean resumableUpload) throws IOException {
        logger.info("Creating Drive file metadata");
        Drive.Files.Create create = drive.files().create(file, fileContent);

        MediaHttpUploader uploader = create.getMediaHttpUploader();
        uploader.setDirectUploadEnabled(resumableUpload);
        uploader.setProgressListener(uploaderProgressListener);

        return create.execute();
    }

    public File updateFile(File file, FileContent fileContent, String fileId) throws IOException {
        logger.info("Updating Drive file metadata");
        Drive.Files.Update update = drive.files().update(fileId, file, fileContent);

        return update.execute();
    }
}
