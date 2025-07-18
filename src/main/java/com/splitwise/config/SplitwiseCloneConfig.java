package com.splitwise.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;


public class SplitwiseCloneConfig extends Configuration {

    @Valid
    @NotNull
    private DataSourceFactory database = new DataSourceFactory();

    @JsonProperty("database") //Matches the key in config.yaml
    public DataSourceFactory getDataSourceFactory(){
        return database;
    }

    @JsonProperty("database")
    public void setDataSourceFactory(DataSourceFactory database){
        this.database = database;
    }

}
