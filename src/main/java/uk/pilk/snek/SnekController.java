package uk.pilk.snek;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import uk.pilk.snek.models.GameStatusRequest;
import uk.pilk.snek.models.MoveOutput;
import uk.pilk.snek.models.SnekInfo;

@RestController
public class SnekController {

    @GetMapping("/")
    SnekInfo SnakeInfo() {
        return new SnekInfo(1, "Pilk", "#888888", "default", "default");
    }

    @PostMapping("/start")
    void SnekStart(@RequestParam GameStatusRequest startInfo) {

    }

    @PostMapping("/move")
    MoveOutput SnekMove(@RequestParam GameStatusRequest moveInfo) {
        return new MoveOutput("right", "yeet");
    }

    @PostMapping("/end")
    void SnekGameOver(@RequestParam GameStatusRequest gameOver) {

    }
}
