package com.ashteric.demo.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.UnknownHostException;
import java.util.Map;

@Controller
public class RestController {

    // The `GetMapping` annotation indicates that this method should be called
    // when handling GET requests to the "/simple-request" endpoint
    @GetMapping("/simple-request")
    public String simpleRequest() {
        // In this case, we return the plain text response "ok"
        return "ok";
    }

    @GetMapping("/echo")
    // We can pass the name of the url param we want as an argument to the RequestParam annotation.
    // The value will be stored in the annotated variable
    public String echo(@RequestParam(name = "text") String echoText) {
        // The response will be "Echo: " followed by the param that was passed in
        return "Echo: " + echoText;
    }

    // We can add a variable to the path by wrapping its name in curly braces
    @GetMapping("/echo/{text}")
    // The PathVariable annotation assigns the text form the actual request to the `echoText`
    // argument
    public String echoPath(@PathVariable(name = "text") String echoText) {
        return "Echo in path: " + echoText;
    }

//    @GetMapping("/large-cappuccino")
//    public Coffee largeCappuccino() {
//        Coffee largeCappuccino = new Coffee();
//        largeCappuccino.setMilk(true);
//        largeCappuccino.setSize("L");
//        return largeCappuccino;
//    }

    // We can use @PostMapping to denote that this handled a POST request
    // to the /coffee endpoint
    @PostMapping("/coffee")
    // We need to specify the @RequestBody annotation along with the `Coffee`
    // class as the argument. The decoded JSON body is then stored in the `coffee` argument
    // variable
//    public String createCoffee(@RequestBody Coffee coffee) {
//        return "Created coffee: " + coffee.toString();
//    }

    @GetMapping("/echo/headers")
    public String echoHeaders(@RequestHeader Map<String, String> headers) {
        StringBuilder sb = new StringBuilder("Headers: \n");
        for (Map.Entry<String, String> header : headers.entrySet()) {
            sb.append(header.getKey());
            sb.append(":");
            sb.append(header.getValue());
            sb.append("\n");
        }
        return sb.toString();
    }

    @GetMapping("/custom-header")
    public String setCustomHeader(HttpServletResponse response) {
        response.setHeader("X-Custom-Header", "Some-Custom-Value");
        return "ok";
    }

    @GetMapping("/sample-error")
    public String sampleError(HttpServletResponse response) {
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return "error";
    }


    @CrossOrigin("*")
    @GetMapping("/get-my-ip")
    public String getIP(HttpServletRequest request) throws UnknownHostException {
        String remoteAddr = "";
        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }
        System.out.println(request.getRemoteHost());
        return remoteAddr;
    }
}
