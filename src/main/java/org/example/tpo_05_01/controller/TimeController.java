package org.example.tpo_05_01.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.*;
import java.time.format.DateTimeFormatter;

@Controller
public class TimeController {

    @GetMapping("/current-time")
    @ResponseBody
    public String currentTime(
            @RequestParam(required = false, defaultValue = "Europe/Warsaw") String timezone,
            @RequestParam(required = false, defaultValue = "HH:mm:ss.SSSS YYYY/MM/dd") String format
    ) {

        ZonedDateTime zdt;
        String message = "";
        String formattedTime;

        try {
            ZoneId zone = ZoneId.of(timezone);
            zdt = ZonedDateTime.now(zone);
        } catch (DateTimeException e) {
            zdt = ZonedDateTime.now();
            message += " Invalid time zone provided. Defaulting to system time zone.<br>";
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            formattedTime = zdt.format(formatter);
        } catch (Exception e) {
            formattedTime = zdt.format(DateTimeFormatter.ofPattern("HH:mm:ss.SSSS yyyy/MM/dd"));
            message += " Invalid format type provided. Defaulting to system format type.";
        }

        return """
                <style>
                    body {
                        background-color: #1a1c2c;
                        color: #e0e0e0;
                        font-family: 'Verdana', sans-serif;
                        padding: 40px;
                        text-align: center;
                    }
                    h1 {
                        font-size: 2.5rem;
                        margin-bottom: 10px;
                        color: #5da9e9;
                    }
                    h2 {
                        font-size: 1.2rem;
                        color: #8fbbe0;
                        font-weight: normal;
                    }
                </style>
                <body>
                    <h1>Current Time:\s""" + formattedTime + "</h1>" +
                (!message.isEmpty() ? "<h2>" + message + "</h2>" : "") + """
                </body>
                """;
    }

    @GetMapping("/current-year")
    @ResponseBody
    public String currentYear() {
        int year = LocalDate.now().getYear();
        return """
                <style>
                    body {
                        background-color: #2a0000;
                        color: #f8f8f8;
                        font-family: 'Verdana', sans-serif;
                        padding: 40px;
                        text-align: center;
                    }
                    h1 {
                        font-size: 2.5rem;
                        color: #e74c3c;
                    }
                </style>
                <body>
                    <h1>Current Year: %d</h1>
                </body>
                """.formatted(year);
    }
}

