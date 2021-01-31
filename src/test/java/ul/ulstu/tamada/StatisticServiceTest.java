package ul.ulstu.tamada;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ul.ulstu.tamada.rest.dto.statistic.OrdersStatisticResponse;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class StatisticServiceTest extends DatabaseIntegrationTest {

    private final String url = "/statistics/v1";

    private final WebApplicationContext wac;
    private MockMvc mvc;

    public StatisticServiceTest(WebApplicationContext wac) {
        this.wac = wac;
        this.mvc = MockMvcBuilders
                .webAppContextSetup(this.wac)
                .dispatchOptions(true)
                .build();
    }

    @Test
    public void statisticsEventsMethodOk() throws Exception {
        var result = mvc.perform(MockMvcRequestBuilders.get(url + "/events")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                ).andReturn();

        var responseJson = result.getResponse().getContentAsString();
        var response = new ObjectMapper().readValue(responseJson, OrdersStatisticResponse.class);

        Assertions.assertEquals(0, response.getTotal());
    }
}
