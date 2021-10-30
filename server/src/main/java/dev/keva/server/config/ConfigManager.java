package dev.keva.server.config;

import dev.keva.server.config.util.ArgsParser;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;

@Slf4j
public final class ConfigManager {
    public static final String DEFAULT_FILE_PATH = Paths.get(".", "keva.properties").toString();

    public static KevaConfig loadConfig(String[] args) throws IOException {
        KevaConfig returnConf = KevaConfig.fromProperties(new Properties());
        val config = ArgsParser.parse(args);
        val overrider = KevaConfig.fromArgs(config);

        val configFilePath = config.getArgVal("f");
        if (configFilePath != null) {
            returnConf = loadConfigFromFile(configFilePath);
        }

        KevaConfig result = returnConf.merge(overrider);
        log.info(result.toString());
        return result;
    }

    public static KevaConfig loadConfigFromFile(String filePath) throws IOException {
        if (filePath.isEmpty()) {
            filePath = DEFAULT_FILE_PATH;
        }
        val props = new Properties();
        try (val file = new FileInputStream(filePath)) {
            props.load(file);
        }

        return KevaConfig.fromProperties(props);
    }
}
