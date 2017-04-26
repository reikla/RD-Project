package at.ac.fh.salzburg.smartmeter.ldap;

/**
 * Created by wiela on 25.04.2017.
 */

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "Willkommen auf der Webseite!";
    }
}