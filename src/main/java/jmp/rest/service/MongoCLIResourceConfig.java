package jmp.rest.service;

import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

/**
 * Created by Nona_Sarokina on 11/1/2016.
 */
@ApplicationPath("/")
public class MongoCLIResourceConfig extends ResourceConfig {

    public MongoCLIResourceConfig() {
        packages("jmp.rest");
    }
}
