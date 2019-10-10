package com.jjjimenez.drivesync;

import java.io.IOException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.nio.file.StandardWatchEventKinds.*;

final class DirectoryWatcher {
    private final Map<Path, WatchKey> watchedPaths;
    private final WatchService fileSystemWatchService;

    DirectoryWatcher() throws IOException {
        watchedPaths = new HashMap<>();
        fileSystemWatchService = FileSystems.getDefault().newWatchService();
    }

    WatchKey registerPath(Path path, WatchEvent.Kind... eventKinds) throws IOException {
        WatchKey key = path.register(fileSystemWatchService, eventKinds);
        watchedPaths.put(path, key);

        return key;
    }

    void registerAllPaths(List<Path> paths) throws IOException {
        for(Path path: paths) {
            registerPath(path, OVERFLOW, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
        }
    }

    void startDirectoryWatcher() { }

    public Map<Path, WatchKey> getWatchedPaths() {
        return watchedPaths;
    }

    public WatchService getFileSystemWatchService() {
        return fileSystemWatchService;
    }
}
