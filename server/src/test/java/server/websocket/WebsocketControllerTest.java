package server.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import commons.Board;
import commons.messages.BoardUpdateMessage;
import commons.messages.UpdateMessage;
import org.junit.jupiter.api.BeforeEach;
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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

    private CompletableFuture<Board> completableFuture;
    private WebSocketStompClient stompClient; 
    private StompSession stompSession;

    @BeforeEach
    public void setup() throws ExecutionException, InterruptedException, TimeoutException {
        completableFuture = new CompletableFuture<>();
        URL = "ws://localhost:" + port + "/ws";

        stompClient = new WebSocketStompClient(new StandardWebSocketClient());
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        stompSession = stompClient.connect(URL, new StompSessionHandlerAdapter() {
        }).get(1, SECONDS);
    }

    @Test
    public void testUpdateBroadcast() throws Exception {
        stompSession.subscribe(SUBSCRIBE_BOARDUPDATE_ENDPOINT, new BoardUpdateFrameHandler());
        postDummyBoard();
        Board board = completableFuture.get(10, SECONDS);
        assertNotNull(board);
    }

    private void postDummyBoard() throws Exception {
        Board test = new Board("Board1", "Black", "password", false);
        String content = new ObjectMapper().writeValueAsString(test);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/boards")
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
            UpdateMessage a = (UpdateMessage) o;
            completableFuture.complete((Board) a.getObject());
        }
    }
}
