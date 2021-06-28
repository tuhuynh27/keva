package com.jinyframework.keva.server.command;

import com.jinyframework.keva.server.ServiceInstance;
import com.jinyframework.keva.server.storage.StorageService;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Expire implements CommandHandler {
    private final Timer timer = new Timer();
    private final StorageService storageService = ServiceInstance.getStorageService();


    @Override
    public Object handle(CommandContext ctx, List<String> args) {
        try {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    storageService.remove(args.get(0));
                }
            }, Long.parseLong(args.get(1)));
            return CommandConstant.SUCCESS_CODE;
        } catch (Exception ignore) {
            return CommandConstant.FAIL_CODE;
        }
    }
}
