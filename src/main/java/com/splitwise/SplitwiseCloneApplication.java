package com.splitwise;

import com.splitwise.config.SplitwiseCloneConfig;
import com.splitwise.dao.UserDAO;
import com.splitwise.model.User;
import com.splitwise.resource.UserResource;
import com.splitwise.service.UserService;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class SplitwiseCloneApplication extends Application<SplitwiseCloneConfig> {

    // Hibernate Bundle
    private final HibernateBundle<SplitwiseCloneConfig> hibernateBundle =
        new HibernateBundle<SplitwiseCloneConfig>(User.class) {
            @Override
            public DataSourceFactory getDataSourceFactory(SplitwiseCloneConfig configuration) {
                return configuration.getDataSourceFactory();
            }
        };

    @Override
    public void initialize(Bootstrap<SplitwiseCloneConfig> bootstrap) {
        bootstrap.addBundle(hibernateBundle);
    }

    public static void main(String[] args) throws Exception {
        new SplitwiseCloneApplication().run(args);
    }

    @Override
    public void run(SplitwiseCloneConfig splitwiseCloneConfig, Environment environment)
        throws Exception {
        System.out.println("Splitwise Clone App has started!");
        environment.jersey().register(new UserResource(new UserService(new UserDAO(
            hibernateBundle.getSessionFactory())))); //registering makes the endpoint available
    }
}