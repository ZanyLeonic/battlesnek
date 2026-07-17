package uk.pilk.snek;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SnekController {

    @GetMapping("/")
    SnekInfo SnakeInfo() {
        return new SnekInfo(1, "Pilk", "#888888", "default", "default");
    }
}
