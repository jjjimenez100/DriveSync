package com.jjjimenez.drivesync.watcher;

import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.util.Map;

interface FileWatcherService {
    Map<Path, WatchKey> getWatchedFiles();
    WatchKey watchFile(Path path, WatchEvent.Kind... eventKinds);
    void startFileWatcherService(FileWatcherListener listener);
}