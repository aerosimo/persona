/******************************************************************************
 * This piece of work is to enhance personahub project functionality.         *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      PersonaHubFilter.java                                           *
 * Created:   30/11/2025, 00:10                                               *
 * Modified:  30/11/2025, 00:10                                               *
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
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.Priorities;
import jakarta.annotation.Priority;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class PersonaHubFilter implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext ctx) {

        // Extract Authorization Header
        String authHeader = ctx.getHeaderString("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            abort(ctx, "Missing or invalid Authorization header");
            return;
        }

        String token = authHeader.substring("Bearer ".length()).trim();

        // Validate via AuthCore
        boolean valid = AuthCore.validateToken(token);

        if (!valid) {
            abort(ctx, "Invalid or expired token");
        }
    }

    private void abort(ContainerRequestContext ctx, String message) {
        ctx.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                .entity("{\"status\":\"unauthorized\",\"message\":\"" + message + "\"}")
                .build());
    }
}