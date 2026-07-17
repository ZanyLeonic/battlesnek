package uk.pilk.snek;

import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.*;

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
    void SnekStart(@RequestBody GameStatusRequest startInfo) {
        history.add(new ArrayList<>());
    }

    @PostMapping("/move")
    MoveOutput SnekMove(@RequestBody GameStatusRequest moveInfo) {
        String move = moveInfo.getYou().findNext(moveInfo.getBoard());
        history.get(gameCount).add(moveInfo.getBoard());
        return new MoveOutput(move, "yeet");
    }

    @PostMapping("/end")
    void SnekGameOver(@RequestBody GameStatusRequest gameOver) {
        gameCount++;
    }

    @GetMapping("/gameHistory/{id}")
    ArrayList<Board> getHistory(@PathVariable int id) {
        if(id > history.size()) {
            return null;
        }
        return history.get(id);
    }
}
