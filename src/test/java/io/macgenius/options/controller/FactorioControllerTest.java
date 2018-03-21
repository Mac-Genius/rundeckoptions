package io.macgenius.options.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.macgenius.options.Config;
import io.macgenius.options.RundeckOptions;
import io.macgenius.options.model.Pair;
import io.macgenius.options.model.PairList;
import lombok.RequiredArgsConstructor;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RundeckOptions.class, Config.class})
@ActiveProfiles("test")
public class FactorioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @Test
    public void getVersions() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders.get("/factorio/versions"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
        System.out.println(response);
        PairList versions = gson.fromJson(response, PairList.class);
        Assert.assertNotNull(versions);
        for (Pair pair : versions) {
            Assert.assertNotNull(pair.getName());
            Assert.assertNotNull(pair.getValue());
        }
    }
}