package com.volkodav4ik.api;

import com.volkodav4ik.dao.PictureDao;
import com.volkodav4ik.model.Picture;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/picture")
public class PictureApi {

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPicture(String json) {
        Picture picture = PictureDao.pictureFromJson(json);
        Picture savedPicture = PictureDao.addPicture(picture);
        if (savedPicture != null && !savedPicture.getBase64().equals("") && !savedPicture.getPictureFileName().equals("")) {
            String result = "Picture added to database with ID: " + savedPicture.getId();
            return Response.status(Response.Status.OK).entity(result).build();
        } else {
            String result = "Failed to add picture to database";
            return Response.status(Response.Status.BAD_REQUEST).entity(result).build();
        }
    }

    @GET
    @Path("/get/all")
    public Response getAllId() {
        List list = PictureDao.getIdList();
        String result = list.toString();
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @GET
    @Path("/get/id")
    public Response getBase64ById(int id) {
        String base64 = PictureDao.getBase64ById(id);
        if (base64 != null) {
            return Response.status(Response.Status.OK).entity(base64).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Wrong ID").build();
        }
    }

}
