package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.PoemDTO;
import entities.Poem;
import facades.PoemFacade;
import fetchers.PoemFetcher;
import utils.EMF_Creator;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;


@Path("poem")
public class PoemResource {


    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public static final PoemFacade poemFacade = PoemFacade.getPoemFacade(EMF);
            

    @GET
    @RolesAllowed({"admin", "user"})
    @Produces({MediaType.APPLICATION_JSON})
    public String getPoem() throws IOException {
        PoemDTO newPoem = PoemFetcher.fetchPoem(GSON);
        return GSON.toJson(newPoem);
    }

    @POST
    @RolesAllowed({"admin", "user"})
    @Path("{username}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String addPoem(String poem, @PathParam("username") String username){


        PoemDTO poemDTO = GSON.fromJson(poem, PoemDTO.class);
        poemFacade.addPoem(username, poemDTO);

        return GSON.toJson(poemDTO);
    }
    @GET
    @RolesAllowed({"admin", "user"})
    @Path("{username}")
    @Produces({MediaType.APPLICATION_JSON})
    public String getPoemsByUser(@PathParam("username") String username){

        List<PoemDTO> poemDTOList = poemFacade.getPoemsByUsername(username);

        return GSON.toJson(poemDTOList);
    }

    @DELETE
    @RolesAllowed({"admin", "user"})
    @Path("{username}")
    @Produces({MediaType.APPLICATION_JSON})
    public String deletePoem(@PathParam("username") String username, String poem){

        PoemDTO poemDTO = GSON.fromJson(poem, PoemDTO.class);

         poemFacade.deletePoem(poemDTO,username);

        return GSON.toJson(poemDTO);
    }

    @GET
    @RolesAllowed({"admin", "user"})
    @Path("all")
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllPoems(){

        List<PoemDTO> poemDTOList = poemFacade.getAllPoems();
        return GSON.toJson(poemDTOList);
    }

}
