package com.jinyframework.keva.server.command;

import com.jinyframework.keva.server.ServiceInstance;
import com.jinyframework.keva.server.storage.StorageService;

import java.util.List;

public class Del implements CommandHandler {
    private final StorageService storageService = ServiceInstance.getStorageService();

    @Override
    public Object handle(List<String> args) {
        try {
            storageService.remove(args.get(0));
            return 1;
        } catch (Exception ignore) {
            return 0;
        }
    }
}
