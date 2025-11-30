/******************************************************************************
 * This piece of work is to enhance personahub project functionality.         *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      AddressRequestDTO.java                                          *
 * Created:   29/11/2025, 19:53                                               *
 * Modified:  29/11/2025, 19:53                                               *
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

package com.aerosimo.ominet.dao.impl;

public class AddressRequestDTO {

    private String username;
    private String firstline;
    private String secondline;
    private String thirdline;
    private String city;
    private String postcode;
    private String country;

    public AddressRequestDTO() {
    }

    public AddressRequestDTO(String username, String firstline, String secondline, String thirdline, String city, String postcode, String country) {
        this.username = username;
        this.firstline = firstline;
        this.secondline = secondline;
        this.thirdline = thirdline;
        this.city = city;
        this.postcode = postcode;
        this.country = country;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstline() {
        return firstline;
    }

    public void setFirstline(String firstline) {
        this.firstline = firstline;
    }

    public String getSecondline() {
        return secondline;
    }

    public void setSecondline(String secondline) {
        this.secondline = secondline;
    }

    public String getThirdline() {
        return thirdline;
    }

    public void setThirdline(String thirdline) {
        this.thirdline = thirdline;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "AddressRequestDTO{" +
                "username='" + username + '\'' +
                ", firstline='" + firstline + '\'' +
                ", secondline='" + secondline + '\'' +
                ", thirdline='" + thirdline + '\'' +
                ", city='" + city + '\'' +
                ", postcode='" + postcode + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}