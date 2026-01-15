/******************************************************************************
 * This piece of work is to enhance persona project functionality.            *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      PersonaDAO.java                                                 *
 * Created:   15/01/2026, 21:01                                               *
 * Modified:  15/01/2026, 21:01                                               *
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

package com.aerosimo.ominet.dao.mapper;

import com.aerosimo.ominet.core.config.Connect;
import com.aerosimo.ominet.core.model.Spectre;
import com.aerosimo.ominet.dao.impl.APIResponseDTO;
import oracle.jdbc.OracleTypes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class PersonaDAO {

    private static final Logger log = LogManager.getLogger(PersonaDAO.class.getName());

    public static APIResponseDTO getMetrics(String username) {
        log.info("Preparing to retrieve user profile completion");
        String response;
        String status;
        String sql = "{call identification_pkg.metrics(?,?)}";
        try (Connection con = Connect.dbase();
             CallableStatement stmt = con.prepareCall(sql)) {
            stmt.setString(1, username);
            stmt.registerOutParameter(2, OracleTypes.VARCHAR);
            stmt.execute();
            response = stmt.getString(2);
            if (response.equalsIgnoreCase("unsuccessful")) {
                status = "unsuccessful";
                return new APIResponseDTO(status,response);
            } else {
                status = "success";
                return new APIResponseDTO(status,response);
            }
        } catch (SQLException err) {
            log.error("Error in identification_pkg (GET METRICS)", err);
            try {
                Spectre.recordError("TE-20001", "Error in identification_pkg (GET METRICS): " + err.getMessage(), PersonaDAO.class.getName());
                response = "internal server error";
                status = "error";
                return new APIResponseDTO(status,response);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}