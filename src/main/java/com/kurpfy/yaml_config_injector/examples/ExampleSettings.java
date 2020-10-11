package com.kurpfy.yaml_config_injector.examples;

import com.kurpfy.yaml_config_injector.annotation.Inject;

public class ExampleSettings {

    @Inject("lack_perm_message")
    private String lackPermMessage;

    @Inject("price")
    private Integer price;

    public String getLackPermMessage() {
        return lackPermMessage;
    }

    public Integer getPrice() {
        return price;
    }
}
