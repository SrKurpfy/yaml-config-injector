package com.kurpfy.yaml_config_injector.adapter;

import org.bukkit.configuration.file.FileConfiguration;

public interface FieldAdapter<T> {

    Class<T> getAdaptClass();

    T adapt(FileConfiguration configuration, String section);
}
