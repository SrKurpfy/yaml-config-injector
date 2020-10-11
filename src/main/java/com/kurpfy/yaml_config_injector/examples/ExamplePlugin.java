package com.kurpfy.yaml_config_injector.examples;

import com.kurpfy.yaml_config_injector.YamlConfigInjector;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ExamplePlugin extends JavaPlugin {

    private ExampleSettings settings;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        this.settings = new ExampleSettings();

        final FileConfiguration config = getConfig();
        final YamlConfigInjector injector = new YamlConfigInjector();

        try {
            injector.inject(config.getConfigurationSection("settings"), settings);

            for (ExampleEnum example : ExampleEnum.values()) {
                injector.inject(config.getConfigurationSection("ores." + example.name()), example);
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public ExampleSettings getSettings() {
        return settings;
    }
}
