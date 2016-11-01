package jmp.rest.service;

import jmp.nosql.MongoEditHandler;
import org.bson.Document;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Map;

/**
 * Created by Nona_Sarokina on 10/31/2016.
 */
@ApplicationPath("/")
@Path("documents")
public class MongoCLIRestService {

    private MongoEditHandler mongoEditHandler = new MongoEditHandler();

    @Context
    UriInfo uriInfo;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDocumentByNameValuePair(@MatrixParam("name") String name, @MatrixParam("value") String value) {
        if (name == null || value == null) {
            throw new NotFoundException("Document with such name and value - " + name + " " + value + "wasn't found");
        }
        return Response.ok(mongoEditHandler.find(name, value)).build();

    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createDocument(Map<String, Object> values) {
        Document document = mongoEditHandler.createDocument(values);
        URI uri = uriInfo.getAbsolutePathBuilder().path(document.get("_id").toString()).build();
        return Response.created(uri).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteDocument(@PathParam("id") String id) {
        Document deletedDocument = mongoEditHandler.delete(id);
        if (deletedDocument == null) {
            throw new NotFoundException("Document that you want to delete doesn't exist");
        }
        return Response.noContent().build();
    }

}
