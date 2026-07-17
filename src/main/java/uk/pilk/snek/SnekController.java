package uk.pilk.snek;

import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.server.ResponseStatusException;
import tools.jackson.databind.exc.MismatchedInputException;
import uk.pilk.snek.models.Board;
import uk.pilk.snek.models.GameStatusRequest;
import uk.pilk.snek.models.MoveOutput;
import uk.pilk.snek.models.SnekInfo;

import java.util.ArrayList;
import java.util.stream.Collectors;

@RestController
@Slf4j
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
        return new MoveOutput(move, move);
    }

    @PostMapping("/end")
    void SnekGameOver(@RequestBody GameStatusRequest gameOver) {
        gameCount++;
    }

    @GetMapping("/gameHistory/{id}")
    ArrayList<Board> getHistory(@PathVariable int id) {
        if(id >= history.size()) {
            return null;
        }
        return history.get(id);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public String handleHttpMessageNotReadableException(HttpMessageNotReadableException ex)
    {
        MismatchedInputException jme = (MismatchedInputException) ex.getCause();
        log.error(ex.getMessage() + " -> " + jme.getPath().stream()
                .map(err -> err.getPropertyName())
                .collect(Collectors.joining("; ")));
        return jme.getLocalizedMessage();
    }
}
