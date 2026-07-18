package uk.pilk.snek;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.exc.MismatchedInputException;
import uk.pilk.snek.models.*;

import java.util.ArrayList;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class SnekController {

    int gameCount = 0;
    ArrayList<ArrayList<Board>> history = new ArrayList<>();
    Direction lastMove = null;

    @GetMapping("/")
    SnekInfo SnakeInfo() {
        return new SnekInfo("1", "Pilk", "#888888", "default", "default");
    }

    @PostMapping("/start")
    void SnekStart(@RequestBody GameStatusRequest startInfo) {
        history.add(new ArrayList<>());
//        log.warn("SnekStart");
    }

    @PostMapping("/move")
    MoveOutput SnekMove(@RequestBody GameStatusRequest moveInfo) {
        Board board = moveInfo.getBoard();
        board.createTiled();

        Snek snek = moveInfo.getYou();
        board.populateDesires(snek);

        Direction move = snek.findNext(board);
        history.get(gameCount).add(moveInfo.getBoard());
        if(move == null) {
            return new MoveOutput(lastMove, "Last move...");
        }
        lastMove = move;
        return new MoveOutput(move, move.toString());
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

    @GetMapping("/gameHistoryPage/{gameId}/{boardId}")
    ModelAndView gameHistory(@PathVariable int gameId,
                             @PathVariable int boardId,
                             Model model){
        model.addAttribute("board", history.get(gameId).get(boardId));
        return new ModelAndView("gameBoard");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public String handleHttpMessageNotReadableException(HttpMessageNotReadableException ex)
    {
        MismatchedInputException jme = (MismatchedInputException) ex.getCause();
        log.error(ex.getMessage() + " -> " + jme.getPath().stream()
                .map(JacksonException.Reference::getDescription)
                .collect(Collectors.joining("; ")));
        return jme.getLocalizedMessage();
    }
}
