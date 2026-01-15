/******************************************************************************
 * This piece of work is to enhance persona project functionality.            *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      PersonaREST.java                                                *
 * Created:   15/01/2026, 22:15                                               *
 * Modified:  15/01/2026, 22:15                                               *
 *                                                                            *
 * Copyright (c)  2026.  Aerosimo Ltd                                         *
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

import com.aerosimo.ominet.dao.impl.APIResponseDTO;
import com.aerosimo.ominet.dao.mapper.PersonaDAO;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Path("/prime")
public class PersonaREST {

    private static final Logger log = LogManager.getLogger(PersonaREST.class);

    /* -------------------- Common Response Helpers -------------------- */
    private Response missingUsername() {
        log.error("Missing username parameter");
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new APIResponseDTO("unsuccessful", "username is required"))
                .build();
    }

    private Response badRequest(String message) {
        log.error("Bad request parameter: " + message);
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new APIResponseDTO("unsuccessful", message))
                .build();
    }

    /* ======================= FLOW ======================= */
    @GET
    @Path("/flow/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMetrics(@PathParam("username") String username) {
        if (username == null || username.isEmpty()) return missingUsername();
        APIResponseDTO resp = PersonaDAO.getMetrics(username);
        return Response.ok(resp).build();
    }
}