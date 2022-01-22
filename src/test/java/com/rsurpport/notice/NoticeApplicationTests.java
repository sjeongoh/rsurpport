package com.rsurpport.notice;
;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
class NoticeApplicationTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    void contextLoads() throws Exception  {

        String url = "/api/v1/board/notices";

        MultiValueMap<String, String> requestParam = new LinkedMultiValueMap<>();

        mockMvc
                .perform(
                        MockMvcRequestBuilders.get(url)
                                .params(requestParam))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());


    }

}
