package com.kurpfy.yaml_config_injector.adapter;

import org.bukkit.configuration.ConfigurationSection;

public interface FieldAdapter<T> {

    Class<T> getAdaptClass();

    T adapt(ConfigurationSection configurationSection, String section);
}
