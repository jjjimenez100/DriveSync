package com.jjjimenez.drivesync.service;

import com.google.api.services.drive.model.Change;

import java.util.Map;

public interface DriveSyncService {
    boolean syncFiles();
    Map<Change, Boolean> getSyncedChanges();
}
