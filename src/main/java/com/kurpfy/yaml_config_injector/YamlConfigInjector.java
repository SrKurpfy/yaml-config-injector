package com.kurpfy.yaml_config_injector;

import com.kurpfy.yaml_config_injector.adapter.FieldAdapter;
import com.kurpfy.yaml_config_injector.annotation.Inject;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;

public class YamlConfigInjector {

    private final Set<FieldAdapter<?>> adapters;

    public YamlConfigInjector() {
        this.adapters = new HashSet<>();

        addAdapter(String.class, MemorySection::getString);
        addAdapter(Integer.class, MemorySection::getInt);
        addAdapter(Long.class, MemorySection::getLong);
        addAdapter(Double.class, MemorySection::getDouble);
        addAdapter(Short.class, (configuration, s) -> Double.valueOf(configuration.getDouble(s)).shortValue());
        addAdapter(ItemStack.class, MemorySection::getItemStack);
    }

    public void addAdapter(FieldAdapter<?> toAdd) {
        adapters.add(toAdd);
    }

    public <T> void addAdapter(Class<T> clazz, BiFunction<FileConfiguration, String, T> function) {
        adapters.add(new FieldAdapter<T>() {
            @Override
            public Class<T> getAdaptClass() {
                return clazz;
            }

            @Override
            public T adapt(FileConfiguration configuration, String section) {
                return function.apply(configuration, section);
            }
        });
    }

    public void addAdapters(FieldAdapter<?>... toAdd) {
        adapters.addAll(Arrays.asList(toAdd));
    }

    public <T> FieldAdapter<T> getAdapter(Class<T> clazz) {
        for (FieldAdapter<?> adapter : adapters) {
            if(adapter.getAdaptClass().equals(clazz)) {
                return (FieldAdapter<T>) adapter;
            }
        }

        return null;
    }

    public void inject(FileConfiguration configuration, Object object) throws IllegalAccessException {
        for (Field field : object.getClass().getFields()) {
            final Inject annotation = field.getAnnotation(Inject.class);
            if(annotation == null) continue;

            final FieldAdapter<?> adapter = getAdapter(field.getType());
            final Object adapt = adapter.adapt(configuration, annotation.value());

            field.set(object, adapt);
        }
    }
}
