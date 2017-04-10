package com.sxk.bootstrap;

import org.elasticsearch.plugins.Plugin;

public class EsPlugin extends Plugin {
    private String name;
    private String description;

    public String name() {
        return this.name;
    }

    public String description() {
        return this.description;
    }

    public EsPlugin() {
        super();
    }

}
