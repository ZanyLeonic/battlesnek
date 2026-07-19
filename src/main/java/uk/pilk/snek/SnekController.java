package uk.pilk.snek;

import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.exc.MismatchedInputException;
import uk.pilk.snek.models.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class SnekController {

    int gameCount = 0;
    ArrayList<ArrayList<Board>> history = new ArrayList<>();
    Direction lastMove = null;

    HashMap<String, List<SseEmitter>> listeners = new HashMap<>();
    ExecutorService virtualService = Executors.newVirtualThreadPerTaskExecutor();
    HashSet<String> gameIds = new HashSet<>();

    @GetMapping("/")
    SnekInfo SnakeInfo() {
        return new SnekInfo("1", "Pilk", "#888888", "default", "default");
    }

    @PostMapping("/start")
    void SnekStart(@RequestBody GameStatusRequest startInfo) {
        history.add(new ArrayList<>());
        gameIds.add(startInfo.getGame().getId());
        listeners.put(startInfo.getGame().getId(), new ArrayList<>());
//        log.warn("SnekStart");
    }

    private void notifyListeners(String gameId, Board board, int turn){
        for (SseEmitter emitter : listeners.get(gameId)){
            virtualService.submit(() -> {
                try {
                    emitter.send(SseEmitter.event()
                            .data(board)
                            .id(board + "-" + turn)
                            .name("move"));
                } catch (IOException e) {
                    log.error("Failed to send live event", e);
                }
            });
        }
    }

    private void closeListeners(String gameId){
        for (SseEmitter emitter : listeners.get(gameId)){
            virtualService.submit(() -> {
                try {
                    emitter.send(SseEmitter.event()
                            .data("COMPLETE")
                            .name("COMPLETE"));
                    emitter.complete();
                } catch (IOException ex){
                    log.error("Failed to terminate SSE", ex);
                }
            });
        }
    }

    @PostMapping("/move")
    MoveOutput SnekMove(@RequestBody GameStatusRequest moveInfo) {
        Board board = moveInfo.getBoard();
        board.createTiled();

        Snek snek = moveInfo.getYou();
        board.populateDesires(snek);

        Direction move = snek.findNext(board);
        history.get(gameCount).add(moveInfo.getBoard());
        notifyListeners(moveInfo.getGame().getId(), board, moveInfo.getTurn());
        if(move == null) {
            return new MoveOutput(lastMove, "Last move...");
        }
        lastMove = move;
        return new MoveOutput(move, move.toString());
    }

    @PostMapping("/end")
    void SnekGameOver(@RequestBody GameStatusRequest gameOver) {
        gameCount++;
        this.gameIds.remove(gameOver.getGame().getId());
        this.listeners.remove(gameOver.getGame().getId());
        closeListeners(gameOver.getGame().getId());
    }

    @GetMapping("/gameHistory/{id}")
    ArrayList<Board> getHistory(@PathVariable int id) {
        if(id >= history.size()) {
            return null;
        }
        return history.get(id);
    }

    @GetMapping("/live/{gameId}")
    SseEmitter getLiveGame(@PathVariable("gameId") String gameId){
        if (!gameIds.contains(gameId)){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Game not found");
        }
        Thread.ofVirtual().name("Live game " + gameId);
        SseEmitter emitter = new SseEmitter();
        this.listeners.get(gameId).add(emitter);
        return emitter;
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
