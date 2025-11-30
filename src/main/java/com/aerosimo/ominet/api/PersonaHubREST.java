/******************************************************************************
 * This piece of work is to enhance personahub project functionality.         *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      PersonaHubREST.java                                                *
 * Created:   29/11/2025, 23:57                                               *
 * Modified:  30/11/2025, 00:02                                               *
 *                                                                            *
 * Copyright (c)  2025.  Aerosimo Ltd                                         *
 *                                                                            *
 * Permission is hereby granted, free of charge, to any person obtaining a    *
 * copy of this software and associated documentation files (the "Software"), *
 * to deal in the Software without restriction, including without limitation  *
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,   *
 * and/or sell copies of the Software, and to permit persons to whom the      *
 * Software is furnished to do so, subject to the following conditions:       *
 *                                                                            *
 * The above copyright notice and this permission notice shall be included    *
 * in all copies or substantial portions of the Software.                     *
 *                                                                            *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,            *
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES            *
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND                   *
 * NONINFINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT                 *
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,               *
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING               *
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE                 *
 * OR OTHER DEALINGS IN THE SOFTWARE.                                         *
 *                                                                            *
 ******************************************************************************/

package com.aerosimo.ominet.api;

import com.aerosimo.ominet.security.AuthCore;
import com.aerosimo.ominet.dao.impl.*;
import com.aerosimo.ominet.dao.mapper.PersonaDAO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.media.multipart.FormDataParam;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;

@Path("/profile")
public class PersonaHubREST {

    private static final Logger log = LogManager.getLogger(PersonaHubREST.class);

    /* =====================================================================================
       Common Validation Helpers
    ===================================================================================== */

    private boolean tokenInvalid(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return true;
        }
        String token = authHeader.substring("Bearer ".length()).trim();
        return !AuthCore.validateToken(token);
    }

    private Response invalidTokenResponse() {
        return Response.status(Response.Status.UNAUTHORIZED)
                .entity(new APIResponseDTO("unsuccessful", "invalid or expired token"))
                .build();
    }

    private Response missingUsername() {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new APIResponseDTO("unsuccessful", "username is required"))
                .build();
    }

    private Response okOrBad(APIResponseDTO resp) {
        return "success".equalsIgnoreCase(resp.getStatus())
                ? Response.ok(resp).build()
                : Response.status(Response.Status.BAD_REQUEST).entity(resp).build();
    }

    /* =====================================================================================
       Avatar Upload (Multipart)
    ===================================================================================== */
    @POST
    @Path("/avatar/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadAvatar(
            @HeaderParam("Authorization") String authHeader,
            @FormDataParam("username") String username,
            @FormDataParam("file") InputStream fileInputStream) {
        if (tokenInvalid(authHeader)) return invalidTokenResponse();
        if (username == null || fileInputStream == null) return missingUsername();
        APIResponseDTO resp = PersonaDAO.saveImage(username, fileInputStream);
        return okOrBad(resp);
    }

    /* =====================================================================================
       Avatar Upload (Base64)
    ===================================================================================== */
    @POST
    @Path("/avatar/transfer")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadAvatarJson(
            @HeaderParam("Authorization") String authHeader,
            ImageRequestDTO req) {
        if (tokenInvalid(authHeader)) return invalidTokenResponse();
        if (req == null || req.getUsername() == null || req.getAvatar() == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new APIResponseDTO("unsuccessful", "missing required fields"))
                    .build();
        }
        try {
            String base64 = req.getAvatar().contains(",")
                    ? req.getAvatar().split(",", 2)[1]
                    : req.getAvatar();
            byte[] bytes = Base64.getDecoder().decode(base64);
            APIResponseDTO resp = PersonaDAO.saveImage(req.getUsername(), new ByteArrayInputStream(bytes));
            return okOrBad(resp);
        } catch (Exception e) {
            log.error("Invalid base64 format", e);
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new APIResponseDTO("unsuccessful", "invalid base64 image format"))
                    .build();
        }
    }

    /* =====================================================================================
       ADDRESS
    ===================================================================================== */

    @POST
    @Path("/address")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveAddress(
            @HeaderParam("Authorization") String authHeader,
            AddressRequestDTO req) {
        if (tokenInvalid(authHeader)) return invalidTokenResponse();
        if (req == null || req.getUsername() == null) return missingUsername();
        APIResponseDTO resp = PersonaDAO.saveAddress(
                req.getUsername(),
                req.getFirstline(),
                req.getSecondline(),
                req.getThirdline(),
                req.getCity(),
                req.getPostcode(),
                req.getCountry()
        );
        return okOrBad(resp);
    }

    @GET
    @Path("/address/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAddress(
            @HeaderParam("Authorization") String authHeader,
            @PathParam("username") String username) {
        if (tokenInvalid(authHeader)) return invalidTokenResponse();
        if (username == null || username.isEmpty()) return missingUsername();
        AddressResponseDTO resp = PersonaDAO.getAddress(username);
        if (resp == null || resp.getUsername() == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new APIResponseDTO("unsuccessful", "no address found"))
                    .build();
        }
        return Response.ok(resp).build();
    }

    /* =====================================================================================
       CONTACT (Bulk Save)
    ===================================================================================== */

    @POST
    @Path("/contact")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveContact(
            @HeaderParam("Authorization") String authHeader,
            List<ContactRequestDTO> reqList) {
        if (tokenInvalid(authHeader)) return invalidTokenResponse();
        if (reqList == null || reqList.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new APIResponseDTO("unsuccessful", "No contact records supplied"))
                    .build();
        }
        for (ContactRequestDTO req : reqList) {
            if (req.getUsername() == null || req.getChannel() == null || req.getAddress() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new APIResponseDTO("unsuccessful",
                                "Missing required fields in one or more records"))
                        .build();
            }
        }
        APIResponseDTO resp = PersonaDAO.saveContacts(reqList);
        return okOrBad(resp);
    }

    @GET
    @Path("/contact/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getContact(
            @HeaderParam("Authorization") String authHeader,
            @PathParam("username") String username) {
        if (tokenInvalid(authHeader)) return invalidTokenResponse();
        if (username == null || username.isEmpty()) return missingUsername();
        List<ContactResponseDTO> list = PersonaDAO.getContact(username);
        if (list == null || list.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new APIResponseDTO("unsuccessful", "no contact records found"))
                    .build();
        }
        return Response.ok(list).build();
    }

    /* =====================================================================================
       PERSON
    ===================================================================================== */

    @POST
    @Path("/person")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response savePerson(
            @HeaderParam("Authorization") String authHeader,
            PersonRequestDTO req) {
        if (tokenInvalid(authHeader)) return invalidTokenResponse();
        if (req == null || req.getUsername() == null) return missingUsername();
        APIResponseDTO resp = PersonaDAO.savePerson(
                req.getUsername(),
                req.getTitle(),
                req.getFirstName(),
                req.getMiddleName(),
                req.getLastName(),
                req.getGender(),
                req.getBirthday()
        );
        return okOrBad(resp);
    }

    @GET
    @Path("/person/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPerson(
            @HeaderParam("Authorization") String authHeader,
            @PathParam("username") String username) {
        if (tokenInvalid(authHeader)) return invalidTokenResponse();
        if (username == null || username.isEmpty()) return missingUsername();
        PersonResponseDTO resp = PersonaDAO.getPerson(username);
        if (resp == null || resp.getUsername() == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new APIResponseDTO("unsuccessful", "no person record found"))
                    .build();
        }
        return Response.ok(resp).build();
    }

    /* =====================================================================================
       AVATAR GET + DELETE
    ===================================================================================== */

    @GET
    @Path("/avatar/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAvatar(
            @HeaderParam("Authorization") String authHeader,
            @PathParam("username") String username) {
        if (tokenInvalid(authHeader)) return invalidTokenResponse();
        if (username == null || username.isEmpty()) return missingUsername();
        ImageResponseDTO image = PersonaDAO.getImage(username);
        if (image == null || image.getAvatar() == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new APIResponseDTO("unsuccessful", "no avatar found"))
                    .build();
        }
        return Response.ok(image).build();
    }

    @DELETE
    @Path("/avatar/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteAvatar(
            @HeaderParam("Authorization") String authHeader,
            @PathParam("username") String username) {
        if (tokenInvalid(authHeader)) return invalidTokenResponse();
        if (username == null || username.isEmpty()) return missingUsername();
        APIResponseDTO resp = PersonaDAO.removeImage(username);
        return okOrBad(resp);
    }

    /* =====================================================================================
       REMOVE PERSON & ADDRESS
    ===================================================================================== */

    @DELETE
    @Path("/address")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeAddress(
            @HeaderParam("Authorization") String authHeader,
            APIRequestDTO req) {
        if (tokenInvalid(authHeader)) return invalidTokenResponse();
        if (req == null || req.getUsername() == null) return missingUsername();
        return okOrBad(PersonaDAO.removeAddress(req.getUsername()));
    }

    @DELETE
    @Path("/person")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response removePerson(
            @HeaderParam("Authorization") String authHeader,
            APIRequestDTO req) {
        if (tokenInvalid(authHeader)) return invalidTokenResponse();
        if (req == null || req.getUsername() == null) return missingUsername();
        return okOrBad(PersonaDAO.removePerson(req.getUsername()));
    }
}