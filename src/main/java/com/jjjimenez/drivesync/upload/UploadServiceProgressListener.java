package com.jjjimenez.drivesync.upload;

import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.googleapis.media.MediaHttpUploaderProgressListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

class UploadServiceProgressListener implements MediaHttpUploaderProgressListener {
    private static final Logger logger = LoggerFactory.getLogger(UploadServiceProgressListener.class);

    @Override
    public void progressChanged(MediaHttpUploader mediaHttpUploader) throws IOException {
        switch(mediaHttpUploader.getUploadState()) {
            case NOT_STARTED:
                logger.info("File upload not started.");
                break;
            case MEDIA_COMPLETE:
                logger.info("File upload complete.");
                break;
            case INITIATION_STARTED:
                logger.info("File upload started initiation.");
                break;
            case INITIATION_COMPLETE:
                logger.info("File upload completed initiation.");
                break;
        }
    }
}
