package com.jjjimenez.drivesync.service;

import com.google.api.services.drive.Drive;

import java.util.Objects;

public final class DriveService {
    private final Drive drive;
    private final DriveUploadService driveUploadService;
    private final DriveUpdateService driveUpdateService;
    private final DriveDeleteService driveDeleteService;
    private final DriveDownloadService driveDownloadService;

    public DriveService(Drive drive) {
        this.drive = drive;
        this.driveUploadService = new DriveUploadServiceImpl(drive, new UploadServiceProgressListener());
        this.driveUpdateService = new DriveUpdateServiceImpl(drive);
        this.driveDeleteService = new DriveDeleteServiceImpl(drive);
        this.driveDownloadService = new DriveDownloadServiceImpl(drive, new DownloadServiceProgressListener());
    }

    public Drive getDrive() {
        return drive;
    }

    public DriveUploadService getDriveUploadService() {
        return driveUploadService;
    }

    public DriveUpdateService getDriveUpdateService() {
        return driveUpdateService;
    }

    public DriveDeleteService getDriveDeleteService() {
        return driveDeleteService;
    }

    public DriveDownloadService getDriveDownloadService() {
        return driveDownloadService;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DriveService that = (DriveService) o;
        return drive.equals(that.drive) &&
                driveUploadService.equals(that.driveUploadService) &&
                driveUpdateService.equals(that.driveUpdateService) &&
                driveDeleteService.equals(that.driveDeleteService) &&
                driveDownloadService.equals(that.driveDownloadService);
    }

    @Override
    public int hashCode() {
        return Objects.hash(drive, driveUploadService, driveUpdateService, driveDeleteService, driveDownloadService);
    }

    @Override
    public String toString() {
        return "DriveService{" +
                "drive=" + drive +
                ", driveUploadService=" + driveUploadService +
                ", driveUpdateService=" + driveUpdateService +
                ", driveDeleteService=" + driveDeleteService +
                ", driveDownloadService=" + driveDownloadService +
                '}';
    }
}
