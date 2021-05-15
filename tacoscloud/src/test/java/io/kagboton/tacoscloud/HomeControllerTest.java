package io.kagboton.tacoscloud;

import io.kagboton.tacoscloud.repository.IngredientRepository;
import io.kagboton.tacoscloud.repository.OrderRepository;
import io.kagboton.tacoscloud.repository.TacoRepository;
import io.kagboton.tacoscloud.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(secure=false)
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;


    // Note: Most of these mocks are here to avoid autowiring issues. They aren't
    //       actually used for the home page test, so their behavior
    //       isn't important. They just need to exist so autowiring can take place.

    @MockBean
    private IngredientRepository ingredientRepository;

    @MockBean
    private TacoRepository designRepository;

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    public void testHomePage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(content().string(
                        containsString("Welcome to...")
                ));
    }
}
