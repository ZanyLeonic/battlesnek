package uk.pilk.snek;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import uk.pilk.snek.models.Board;
import uk.pilk.snek.models.GameStatusRequest;
import uk.pilk.snek.models.MoveOutput;
import uk.pilk.snek.models.SnekInfo;

import java.util.ArrayList;

@RestController
public class SnekController {

    int gameCount = 0;
    ArrayList<ArrayList<Board>> history = new ArrayList<>();

    @GetMapping("/")
    SnekInfo SnakeInfo() {
        return new SnekInfo("1", "Pilk", "#888888", "default", "default");
    }

    @PostMapping("/start")
    void SnekStart(@RequestParam GameStatusRequest startInfo) {
        history.add(new ArrayList<>());
    }

    @PostMapping("/move")
    MoveOutput SnekMove(@RequestParam GameStatusRequest moveInfo) {
        String move = moveInfo.getYou().findNext(moveInfo.getBoard());
        history.get(gameCount).add(moveInfo.getBoard());
        return new MoveOutput(move, "yeet");
    }

    @PostMapping("/end")
    void SnekGameOver(@RequestParam GameStatusRequest gameOver) {
        gameCount++;
    }

    @GetMapping("/gameHistory")
    ArrayList<ArrayList<Board>> getHistory() {
        return history;
    }
}
