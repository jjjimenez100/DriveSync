package com.jjjimenez.drivesync.watcher;

interface FileWatcherListener<T> {
    T fileStateChanged(FileWatcherService watcherService);
}
