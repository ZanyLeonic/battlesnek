package uk.pilk.snek;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import uk.pilk.snek.models.GameOver;
import uk.pilk.snek.models.MoveOutput;
import uk.pilk.snek.models.SnekInfo;
import uk.pilk.snek.models.ingest.MoveSnek;
import uk.pilk.snek.models.ingest.StartSnek;

@RestController
public class SnekController {

    @GetMapping("/")
    SnekInfo SnakeInfo() {
        return new SnekInfo(1, "Pilk", "#888888", "default", "default");
    }

    @PostMapping("/start")
    void SnekStart(@RequestParam StartSnek startInfo) {

    }

    @PostMapping("/move")
    MoveOutput SnekMove(@RequestParam MoveSnek moveInfo) {
        return new MoveOutput("right", "yeet");
    }

    @PostMapping("/end")
    void SnekGameOver(@RequestParam GameOver gameOver) {

    }
}
