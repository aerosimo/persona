<!--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  ~ This piece of work is to enhance personahub project functionality.        ~
  ~                                                                           ~
  ~ Author:    eomisore                                                       ~
  ~ File:      index.html                                                     ~
  ~ Created:   30/11/2025, 00:25                                              ~
  ~ Modified:  30/11/2025, 00:25                                              ~
  ~                                                                           ~
  ~ Copyright (c)  2025.  Aerosimo Ltd                                        ~
  ~                                                                           ~
  ~ Permission is hereby granted, free of charge, to any person obtaining a   ~
  ~ copy of this software and associated documentation files (the "Software"),~
  ~ to deal in the Software without restriction, including without limitation ~
  ~ the rights to use, copy, modify, merge, publish, distribute, sublicense,  ~
  ~ and/or sell copies of the Software, and to permit persons to whom the     ~
  ~ Software is furnished to do so, subject to the following conditions:      ~
  ~                                                                           ~
  ~ The above copyright notice and this permission notice shall be included   ~
  ~ in all copies or substantial portions of the Software.                    ~
  ~                                                                           ~
  ~ THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,           ~
  ~ EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES           ~
  ~ OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND                  ~
  ~ NONINFINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT                ~
  ~ HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,              ~
  ~ WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING              ~
  ~ FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE                ~
  ~ OR OTHER DEALINGS IN THE SOFTWARE.                                        ~
  ~                                                                           ~
  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~-->

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta content="Elijah Omisore" name="author">
    <meta content="Aerosimo 1.0.0" name="generator">
    <meta content="Aerosimo" name="application-name">
    <meta content="IE=edge" http-equiv="X-UA-Compatible">
    <meta content="Aerosimo IT Consultancy" name="description">
    <meta content="Aerosimo" name="apple-mobile-web-app-title">
    <meta content="Oracle, Java, Tomcat, Maven, Jenkins, Bitbucket, Github" name="keywords">
    <meta content="width=device-width, initial-scale=1, user-scalable=no" name="viewport" />
    <!-- Title -->
    <title>PersonaHub | Aerosimo Ltd</title>
    <!-- Favicon-->
    <link href="assets/img/favicon.ico" rel="shortcut icon"/>
    <link href="assets/img/favicon.ico" rel="icon" type="image/x-icon">
    <link href="assets/img/favicon-32x32.png" rel="icon" sizes="32x32" type="image/png">
    <link href="assets/img/favicon-16x16.png" rel="icon" sizes="16x16" type="image/png">
    <link href="assets/img/apple-touch-icon.png" rel="apple-touch-icon" sizes="180x180">
    <link href="assets/img/android-chrome-192x192.png" rel="android-chrome" sizes="192x192">
    <!-- Google Fonts for clean typography -->
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">
    <style>
        /* Reset and base */
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Roboto', sans-serif;
            background-color: #1e1e2f; /* dark background */
            color: #f0f0f0;
            height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            flex-direction: column;
        }

        .container {
            text-align: center;
        }

        .logo {
            width: 180px;
            height: auto;
            margin-bottom: 40px;
            filter: drop-shadow(0 0 10px #4d3b7a);
        }

        h1 {
            font-size: 2.5rem;
            margin-bottom: 10px;
            color: #ffffff;
            text-shadow: 0 0 5px #4d3b7a;
        }

        p {
            font-size: 1.1rem;
            color: #b0b0b0;
        }

        /* Optional dark-themed button */
        .btn {
            display: inline-block;
            margin-top: 20px;
            padding: 12px 30px;
            background-color: #4d3b7a;
            color: #fff;
            font-weight: 500;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            transition: background-color 0.3s ease, transform 0.2s ease;
            text-decoration: none;
        }

        .btn:hover {
            background-color: #6c52a1;
            transform: translateY(-2px);
        }

        /* Subtle animated gradient background */
        body::before {
            content: "";
            position: fixed;
            top: 0; left: 0; right: 0; bottom: 0;
            background: linear-gradient(120deg, #2c2c3e, #1e1e2f, #2c2c3e);
            z-index: -1;
            animation: gradientShift 15s ease infinite;
        }

        @keyframes gradientShift {
            0% { background-position: 0% 50%; }
            50% { background-position: 100% 50%; }
            100% { background-position: 0% 50%; }
        }
    </style>
</head>
<body>
<div class="container">
    <img src="https://thumbs4.imagebam.com/3e/10/82/MED2HDH_t.png" alt="PersonaHub Logo" class="logo">
    <h1>Welcome to PersonaHub</h1>
    <p>Securely manage profiles, contacts, and avatars in one central hub.</p>
    <a href="#" class="btn">Get Started</a>
</div>
</body>
</html>