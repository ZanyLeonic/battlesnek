package uk.pilk.snek;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.file.Files;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SampleMoveTest {

    @Autowired
    MockMvc mockMvc;

    @Value("classpath:SampleFirstMove1.json")
    Resource resourceFile;

    @Test
    public void test() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/start")
                .content(resourceFile.getContentAsByteArray())
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.post("/move")
                .content(resourceFile.getContentAsByteArray())
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.get("/gameHistoryPage/0/0"))
                .andDo(print());
    }
}
