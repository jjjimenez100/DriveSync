package com.jjjimenez.drivesync.service;

import com.google.api.client.googleapis.media.MediaHttpDownloader;
import com.google.api.client.googleapis.media.MediaHttpDownloaderProgressListener;
import com.google.api.services.drive.Drive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

final class DriveDownloadServiceImpl implements DriveDownloadService {
    private static final Logger logger = LoggerFactory.getLogger(DriveDownloadService.class);

    private final Drive drive;
    private final MediaHttpDownloaderProgressListener downloaderProgressListener;

    public DriveDownloadServiceImpl(Drive drive, MediaHttpDownloaderProgressListener downloaderProgressListener) {
        this.drive = drive;
        this.downloaderProgressListener = downloaderProgressListener;
    }

    @Override
    public Object downloadFile(String fileId, OutputStream outputStream, boolean resumableDownload) throws IOException {
        Drive.Files.Get downloadRequest = drive.files().get(fileId);

        MediaHttpDownloader downloader = downloadRequest.getMediaHttpDownloader();
        downloader.setProgressListener(downloaderProgressListener);
        downloader.setDirectDownloadEnabled(resumableDownload);

        downloadRequest.executeMediaAndDownloadTo(outputStream);
        logger.info(downloadRequest.getLastStatusCode() + " " + downloadRequest.getLastStatusMessage());

        return downloadRequest.getJsonContent();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DriveDownloadServiceImpl that = (DriveDownloadServiceImpl) o;
        return drive.equals(that.drive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(drive);
    }

    @Override
    public String toString() {
        return "DriveDownloadServiceImpl{" +
                "drive=" + drive + '}';
    }
}
