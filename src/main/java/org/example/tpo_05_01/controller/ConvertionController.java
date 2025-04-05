package org.example.tpo_05_01.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ConvertionController {

    @PostMapping("/convert")
    @ResponseBody
    public String convert(
            @RequestParam String value,
            @RequestParam int fromBase,
            @RequestParam int toBase
    ) {
        StringBuilder errorMessage = new StringBuilder();

        if (fromBase < 2 || fromBase > 100)
            errorMessage.append("<p><b>fromBase</b> outside the allowed range</p>");

        if (toBase < 2 || toBase > 100)
            errorMessage.append("<p><b>toBase</b> outside the allowed range</p>");

        int correctedValue;

        try {
            correctedValue = Integer.parseInt(value, fromBase);
        } catch (NumberFormatException e) {
            errorMessage.append("<p><b>Invalid number</b> for base ").append(fromBase).append("</p>");
            correctedValue = -1;
        }

        if (errorMessage.length() > 0) {
            return """
                <style>
                    body {
                        background-color: #330000;
                        color: #ffcccc;
                        font-family: Arial, sans-serif;
                        padding: 40px;
                        text-align: center;
                    }
                    h1 {
                        color: #ff4444;
                        font-size: 2rem;
                    }
                    p {
                        font-size: 1.2rem;
                    }
                </style>
                <body>
                    <h1>Conversion Error</h1>
                    %s
                </body>
                """.formatted(errorMessage.toString());
        }

        String converted = Integer.toString(correctedValue, toBase).toUpperCase();
        String binary = Integer.toBinaryString(correctedValue);
        String octal = Integer.toOctalString(correctedValue);
        String decimal = Integer.toString(correctedValue);
        String hex = Integer.toHexString(correctedValue).toUpperCase();

        return """
            <style>
                body {
                    background-color: #1e3d2f;
                    color: white;
                    font-family: Arial, sans-serif;
                    text-align: center;
                    padding: 40px;
                }
                h1 {
                    color: #5da9e9;
                    font-size: 2.5rem;
                }
                p {
                    font-size: 1.2rem;
                }
            </style>
            <body>
                <h1>Conversion Result</h1>
                <p><b>Original-%d:</b> %s</p>
                <p><b>Converted-%d:</b> %s</p>
                <p><b>BIN:</b> %s</p>
                <p><b>OCT:</b> %s</p>
                <p><b>DEC:</b> %s</p>
                <p><b>HEX:</b> %s</p>
            </body>
            """.formatted(fromBase, value.toUpperCase(), toBase, converted, binary, octal, decimal, hex);
    }
}
