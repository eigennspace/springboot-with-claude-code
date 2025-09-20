package com.example.demo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * TestControllerTest adalah kelas unit test untuk TestController.
 *
 * <p>Test ini menguji endpoint-endpoint yang disediakan oleh TestController
 * menggunakan MockMvc untuk simulasi permintaan HTTP.
 *
 * @author Harist Islami
 * @version 1.0
 * @since 2025-09-20
 */
@WebMvcTest(TestController.class)
class TestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Test untuk endpoint GET default ("/").
     *
     * <p>Menguji bahwa endpoint mengembalikan status 200 OK
     * dan pesan "Hello World" yang sesuai.
     *
     * @throws Exception jika terjadi kesalahan saat melakukan permintaan
     */
    @Test
    void testIndex_ShouldReturnHelloWorld() throws Exception {
        mockMvc.perform(get("/"))
               .andExpect(status().isOk())
               .andExpect(content().string("Hello World"));
    }

    /**
     * Test untuk endpoint GET dengan path kosong.
     *
     * <p>Menguji bahwa endpoint dengan path kosong juga mengembalikan
     * status 200 OK dan pesan "Hello World".
     *
     * @throws Exception jika terjadi kesalahan saat melakukan permintaan
     */
    @Test
    void testIndex_WithEmptyPath_ShouldReturnHelloWorld() throws Exception {
        mockMvc.perform(get(""))
               .andExpect(status().isOk())
               .andExpect(content().string("Hello World"));
    }
}