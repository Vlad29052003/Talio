package server.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import commons.Board;
import commons.messages.BoardUpdateMessage;
import commons.messages.UpdateMessage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class WebsocketControllerTest {
    @Value("${local.server.port}")
    private int port;
    private String URL;

    private static final String SUBSCRIBE_BOARDUPDATE_ENDPOINT = "/topic/boards/";

    @Autowired
    private MockMvc mockMvc;

    private List<BoardUpdateMessage> receivedUpdates;
    private CountDownLatch done = new CountDownLatch(1);
    private WebSocketStompClient stompClient; 
    private StompSession stompSession;

    @BeforeEach
    public void setup() throws ExecutionException, InterruptedException, TimeoutException {
        receivedUpdates = new ArrayList<>();
        URL = "ws://localhost:" + port + "/ws";

        stompClient = new WebSocketStompClient(new StandardWebSocketClient());
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        stompSession = stompClient.connect(URL, new StompSessionHandlerAdapter() {
        }).get(3, SECONDS);
    }

    @AfterEach
    public void cleanup(){
        stompSession.disconnect();
        stompClient.stop();
    }

    @Test @DisplayName("Board creation websocket broadcast test")
    public void testCreateBroadcast() throws Exception {
        stompSession.subscribe(SUBSCRIBE_BOARDUPDATE_ENDPOINT, new BoardUpdateFrameHandler());
        Board test = postCreateDummyBoard();

        done.await(1, SECONDS);

        assertEquals(1, receivedUpdates.size());
        BoardUpdateMessage update = receivedUpdates.get(0);
        assertNotNull(update);
        assertNotNull(update.toString());
        assertEquals(UpdateMessage.Operation.CREATED, update.getOperation());

        Board board = (Board) update.getObject();
        assertNotNull(board);
        assertEquals(test, board);
    }

    @Test @DisplayName("Board deletion websocket broadcast test")
    public void testDeleteBroadcast() throws Exception {
        done = new CountDownLatch(2);

        stompSession.subscribe(SUBSCRIBE_BOARDUPDATE_ENDPOINT, new BoardUpdateFrameHandler());
        Board test = postCreateDummyBoard();

        // Wait between sending request to keep request order
        Thread.sleep(100);

        postDeleteBoard(test);

        done.await(1, SECONDS);
        assertEquals(2, receivedUpdates.size());

        BoardUpdateMessage update = receivedUpdates.get(1);
        assertNotNull(update);
        assertEquals(UpdateMessage.Operation.DELETED, update.getOperation());

        Board board = (Board) update.getObject();
        assertNull(board);
    }

    @Test @DisplayName("Board edit websocket broadcast test")
    public void testUpdateBroadcast() throws Exception {
        done = new CountDownLatch(2);

        stompSession.subscribe(SUBSCRIBE_BOARDUPDATE_ENDPOINT, new BoardUpdateFrameHandler());
        Board test = postCreateDummyBoard();

        String newname = "This is a different name";
        test.name = newname;

        // Wait between sending request to keep request order
        Thread.sleep(100);

        postUpdateBoard(test);

        done.await(1, SECONDS);

        assertEquals(2, receivedUpdates.size());

        BoardUpdateMessage update = receivedUpdates.get(1);
        assertNotNull(update);
        assertEquals(UpdateMessage.Operation.UPDATED, update.getOperation());

        Board board = (Board) update.getObject();
        assertNotNull(board);

        assertEquals(newname, board.name);
    }

    private Board postCreateDummyBoard() throws Exception {
        Board test = new Board("Board1", "Black", "password", false);
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(test);
        MvcResult result =  mockMvc.perform(MockMvcRequestBuilders
                .post("/api/boards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
            .andExpect(status().isOk())
            .andReturn();
        return mapper.readValue(result.getResponse().getContentAsString(), Board.class);
    }

    private void postUpdateBoard(Board board) throws Exception{
        String content = new ObjectMapper().writeValueAsString(board);
        MvcResult result =  mockMvc.perform(MockMvcRequestBuilders
                .put("/api/boards/" + board.id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
            .andExpect(status().isOk())
            .andReturn();
    }

    private void postDeleteBoard(Board board) throws Exception{
        String content = new ObjectMapper().writeValueAsString(board);
        MvcResult result =  mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/boards/" + board.id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
            .andExpect(status().isOk())
            .andReturn();
    }

    private class BoardUpdateFrameHandler implements StompFrameHandler {
        @Override
        public Type getPayloadType(StompHeaders stompHeaders) {
            return BoardUpdateMessage.class;
        }

        @Override
        public void handleFrame(StompHeaders stompHeaders, Object o) {
            BoardUpdateMessage a = (BoardUpdateMessage) o;
            receivedUpdates.add(a);
            done.countDown();
        }
    }
}
