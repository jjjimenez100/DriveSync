package com.jjjimenez.drivesync.watcher;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.util.Map;

interface FileWatcherService {
    Map<Path, WatchKey> getWatchedFiles();
    WatchKey watchFile(Path path, WatchEvent.Kind... eventKinds) throws IOException;
    void startFileWatcherService();
}