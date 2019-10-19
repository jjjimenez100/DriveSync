package com.jjjimenez.drivesync.service;

import com.google.api.client.googleapis.media.MediaHttpDownloader;
import com.google.api.client.googleapis.media.MediaHttpDownloaderProgressListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

final class DownloadServiceProgressListener implements MediaHttpDownloaderProgressListener {
    private static final Logger logger = LoggerFactory.getLogger(DownloadServiceProgressListener.class);

    @Override
    public void progressChanged(MediaHttpDownloader mediaHttpDownloader) throws IOException {
        switch(mediaHttpDownloader.getDownloadState()) {
            case NOT_STARTED:
                logger.info("File download not started.");
                break;
            case MEDIA_COMPLETE:
                logger.info("File download complete.");
                break;
        }
    }
}
