package com.jjjimenez.drivesync.watcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static java.nio.file.StandardWatchEventKinds.*;

final class FileWatcherServiceImpl implements FileWatcherService {
    private static final Logger logger = LoggerFactory.getLogger(FileWatcherServiceImpl.class);

    private final Map<Path, WatchKey> watchedFiles;
    private final WatchService fileSystemWatchService;

    private final CreateWatchStateListener createWatchStateListener;
    private final DeleteWatchStateListener deleteWatchStateListener;
    private final ModifyWatchStateListener modifyWatchStateListener;
    private final OverflowWatchStateListener overflowWatchStateListener;

    private FileWatcherServiceImpl(Builder builder) {
        this.watchedFiles = builder.watchedFiles;
        this.fileSystemWatchService = builder.fileSystemWatchService;
        this.createWatchStateListener = builder.createWatchStateListener;
        this.deleteWatchStateListener = builder.deleteWatchStateListener;
        this.modifyWatchStateListener = builder.modifyWatchStateListener;
        this.overflowWatchStateListener = builder.overflowWatchStateListener;
    }

    static class Builder {
        private Map<Path, WatchKey> watchedFiles;
        private WatchService fileSystemWatchService;

        private CreateWatchStateListener createWatchStateListener;
        private DeleteWatchStateListener deleteWatchStateListener;
        private ModifyWatchStateListener modifyWatchStateListener;
        private OverflowWatchStateListener overflowWatchStateListener;

        public Builder() throws IOException {
            this.watchedFiles = new HashMap<>();
            this.fileSystemWatchService = FileSystems.getDefault().newWatchService();
        }

        public Builder createWatchStateListener(CreateWatchStateListener createWatchStateListener) {
            this.createWatchStateListener = createWatchStateListener;
            return this;
        }

        public Builder deleteWatchStateListener(DeleteWatchStateListener deleteWatchStateListener) {
            this.deleteWatchStateListener = deleteWatchStateListener;
            return this;
        }

        public Builder modifyWatchStateListener(ModifyWatchStateListener modifyWatchStateListener) {
            this.modifyWatchStateListener = modifyWatchStateListener;
            return this;
        }

        public Builder overflowWatchStateListener(OverflowWatchStateListener overflowWatchStateListener) {
            this.overflowWatchStateListener = overflowWatchStateListener;
            return this;
        }

        public Builder watchedFiles(Map<Path, WatchKey> watchedFiles) {
            this.watchedFiles = watchedFiles;
            return this;
        }

        public Builder fileSystemWatchService(WatchService fileSystemWatchService) {
            this.fileSystemWatchService = fileSystemWatchService;
            return this;
        }

        public FileWatcherServiceImpl build() {
            return new FileWatcherServiceImpl(this);
        }
    }

    @Override
    public Map<Path, WatchKey> getWatchedFiles() {
        return Collections.unmodifiableMap(watchedFiles);
    }

    @Override
    public WatchKey watchFile(Path path, WatchEvent.Kind... eventKinds) throws IOException {
        WatchKey key = path.register(fileSystemWatchService, eventKinds);
        watchedFiles.put(path, key);

        return key;
    }

    @Override
    public void startFileWatcherService() {
        WatchKey watchedFile;

        logger.info("Polling for file state changes");
        while((watchedFile = fileSystemWatchService.poll()) != null) {
            for(WatchEvent<?> event: watchedFile.pollEvents()) {
                WatchEvent.Kind<?> kind = event.kind();
                if(kind == OVERFLOW && overflowWatchStateListener != null) {
                    overflowWatchStateListener.overflowStateChange();
                } else if(kind == ENTRY_CREATE && createWatchStateListener != null) {
                    createWatchStateListener.createStateChange();
                } else if(kind == ENTRY_DELETE && deleteWatchStateListener != null) {
                    deleteWatchStateListener.deleteStateChange();
                } else if(kind == ENTRY_MODIFY && modifyWatchStateListener != null) {
                    modifyWatchStateListener.modifyStateChange();
                } else {
                    logger.info("No provided state listeners.");
                }
            }
            watchedFile.reset();
        }
    }
}
