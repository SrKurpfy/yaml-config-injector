package com.kurpfy.yaml_config_injector.examples;

import com.kurpfy.yaml_config_injector.annotation.Inject;

public enum ExampleEnum {

    DIAMOND, GOLD, IRON;

    @Inject("price")
    private int price;

    public int getPrice() {
        return price;
    }
}
