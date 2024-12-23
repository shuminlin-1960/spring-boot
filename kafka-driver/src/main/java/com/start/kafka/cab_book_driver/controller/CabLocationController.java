package com.start.kafka.cab_book_driver.controller;

import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.start.kafka.cab_book_driver.service.CabLocationService;

import java.util.Map;


@RestController()
//@RequestMapping("/location")
@RequestMapping("/")
public class CabLocationController {

    @Autowired()
    private CabLocationService cabLocationService;


    //Business logic will go
    @GetMapping("/location")
    public ResponseEntity updateLocation() throws InterruptedException {
        int range = 10;
        while (range >0){
            String message = Math.random() + " , " + Math.random();
            System.out.println(message);
            cabLocationService.updateLocation(message);
            Thread.sleep(1000);
            range--;
        }
        return new ResponseEntity<>(Map.of("message", "Location updated"), HttpStatus.OK);
    }

    @GetMapping (value = "/location/{state}/{town}")
    public ResponseEntity updateLocation(@PathVariable("state")  String state, @PathVariable("town")  String town) {
        int range = 10;
        while (range >0){
            System.out.println(town + ", " + state + ", " + Math.random() + " , " + Math.random());
            range--;
        }
        return new ResponseEntity<>(Map.of("message", "State: " + state +
                                                             ", township: " + town ),
                HttpStatus.OK);
    }


    @PostMapping (value = "/location/{state}/{township}")
    public ResponseEntity updateLocation(@PathVariable("state")  String state, @PathVariable("township")  String township, @RequestBody String details) {
        int range = 10;
        while (range >0){
            System.out.println(township + ", " + state + ", " + Math.random() + " , " + Math.random());
            range--;
        }
        return new ResponseEntity<>(Map.of("message", "State: " + state +
                                                              ", township: "  + township +
                                                              ", " + "Details: " +
                                                              ", " + details),
                HttpStatus.OK);
    }

    @PostMapping (value = "/location/{state}/{township}/{zip}")
    public ResponseEntity updateLocation(@PathVariable("zip")  String zip, @PathVariable("township")  String township,
                                         @RequestParam("foundBy") String foundBy,  @RequestParam("foundYear") String foundYear,
                                         @RequestBody String details) {
        int range = 10;
        while (range >0){
            System.out.println(township + ", int " + zip + ", " + Math.random() + " , " + Math.random());
            range--;
        }
        return new ResponseEntity<>(Map.of("message",
                "{Name: " +  township +
                    ", zip: " + zip +
                    ", Found by: " + foundBy +
                    " in "  + foundYear +
                    ", Details: "  + details+ "}"),
                HttpStatus.OK);
    }
}
