package com.jjjimenez.drivesync.service;

import java.io.IOException;

public interface DriveDeleteService {
    Object deleteFile(String fileId) throws IOException;
}
