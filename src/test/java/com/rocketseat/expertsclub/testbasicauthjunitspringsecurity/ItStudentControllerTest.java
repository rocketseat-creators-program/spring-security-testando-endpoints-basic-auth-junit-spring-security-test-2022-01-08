package com.rocketseat.expertsclub.testbasicauthjunitspringsecurity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rocketseat.expertsclub.testbasicauthjunitspringsecurity.model.Student;
import com.rocketseat.expertsclub.testbasicauthjunitspringsecurity.repository.StudentRepository;
import com.rocketseat.expertsclub.testbasicauthjunitspringsecurity.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class ItStudentControllerTest {

    @Autowired
    WebApplicationContext webApplicationContext;


    MockMvc mockMvc;

    @MockBean
    StudentRepository studentRepository;


    @MockBean
    StudentService studentService;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }


    public static String convertToJson(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void shouldCreateANewStudentUsingUserExpertsClub() throws Exception {
        mockMvc.perform(post("/student/")
                        .with(httpBasic("expertsclub", "senha"))

                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertToJson(new Student(null, "kamila", "java", 4)))
                        .characterEncoding("utf-8"))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldCreateANewStudentUsingUserEC() throws Exception {
        mockMvc.perform(post("/student/")
                        .with(httpBasic("ec", "senha"))

                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertToJson(new Student(null, "kamila", "java", 4)))
                        .characterEncoding("utf-8"))
                .andExpect(status().isCreated());
    }


    @Test
    void shouldReturn401WhenCreateANewStudentUsingIncorrectUser() throws Exception {
        mockMvc.perform(post("/student/")
                        .with(httpBasic("incorrect user", "senha"))

                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertToJson(new Student(null, "kamila", "java", 4)))
                        .characterEncoding("utf-8"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldReturn401WhenCreateANewStudentUsingIncorrectPassword() throws Exception {
        mockMvc.perform(post("/student/")
                        .with(httpBasic("ec", "senha errada"))

                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertToJson(new Student(null, "kamila", "java", 4)))
                        .characterEncoding("utf-8"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldReturn401WhenCreateANewStudentNotUsingAuthentication() throws Exception {
        mockMvc.perform(post("/student/")

                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertToJson(new Student(null, "kamila", "java", 4)))
                        .characterEncoding("utf-8"))
                .andExpect(status().isUnauthorized());
    }


    @WithMockUser("usuariomockado")
    @Test
    void shouldCreateANewStudentUsingMockUser() throws Exception {
        mockMvc.perform(post("/student/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertToJson(new Student(null, "kamila", "java", 4)))
                        .characterEncoding("utf-8"))
                .andExpect(status().isCreated());
    }


    @Test
    void shouldReturnAllStudentsWithoutAuthentication() throws Exception {
        mockMvc.perform(get("/student/find"))
                .andExpect(status().isOk());
    }


    @Test
    void shouldFindAnStudentByIdUsingUserExpertsClub() throws Exception {
        mockMvc.perform(get("/student/{id}", 1).with(httpBasic("expertsclub", "senha")))
                .andExpect(status().isOk());
    }

    @Test
    void shouldFindAnStudentByIdUsingUserEC() throws Exception {
        mockMvc.perform(get("/student/{id}", 1).with(httpBasic("ec", "senha")))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturn401WhenFindStudentByIdUsingIncorrectUser() throws Exception {
        mockMvc.perform(get("/student/{id}", 1).with(httpBasic("usuario errado", "senha")))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldReturn401WhenFindStudentByIdUsingIncorrectPassword() throws Exception {
        mockMvc.perform(get("/student/{id}", 1).with(httpBasic("ec", "senha incorreta")))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser("usuariomockado")
    void shouldFindStudentByIdUsingMockUser() throws Exception {
        mockMvc.perform(get("/student/{id}", 1))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturn401WhenFindStudentByIdNotUsingAuthentication() throws Exception {
        mockMvc.perform(get("/student/{id}", 1))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldUpdateANewStudentUsingUserExpertsClub() throws Exception {
        mockMvc.perform(put("/student/{id}", 3)
                        .with(httpBasic("expertsclub", "senha"))

                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertToJson(new Student(null, "kamila atualizada", "java", 4)))
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk());
    }


    @Test
    void shouldUpdateAStudentUsingUserEC() throws Exception {
        mockMvc.perform(put("/student/{id}", 3)
                        .with(httpBasic("ec", "senha"))

                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertToJson(new Student(null, "kamila atualizada", "java", 4)))
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturn401WhenUpdateAStudentUsingIncorrectUser() throws Exception {
        mockMvc.perform(put("/student/{id}", 3)
                        .with(httpBasic("incorrect user", "senha"))

                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertToJson(new Student(null, "kamila", "java", 4)))
                        .characterEncoding("utf-8"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldReturn401WhenUpdateNewStudentUsingIncorrectPassword() throws Exception {
        mockMvc.perform(put("/student/{id}", 3)
                        .with(httpBasic("ec", "senha errada"))

                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertToJson(new Student(null, "kamila", "java", 4)))
                        .characterEncoding("utf-8"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldReturn401WhenUpdateStudentNotUsingAuthentication() throws Exception {
        mockMvc.perform(put("/student/{id}", 3)

                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertToJson(new Student(null, "kamila", "java", 5)))
                        .characterEncoding("utf-8"))
                .andExpect(status().isUnauthorized());
    }


    @WithMockUser("usuariomock")
    @Test
    void shouldUpdateAStudentUsingMockUser() throws Exception {
        mockMvc.perform(put("/student/{id}", "3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertToJson(new Student(null, "kamila atualizacao", "java", 5)))
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteAStudentByIdUsingUserExpertsClub() throws Exception {
        mockMvc.perform(delete("/student/{id}", 1).with(httpBasic("expertsclub", "senha")))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldDeleteAStudentByIdUsingUserEC() throws Exception {
        mockMvc.perform(delete("/student/{id}", 1).with(httpBasic("ec", "senha")))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturn401WhenDeleteStudentByIdUsingIncorrectUser() throws Exception {
        mockMvc.perform(delete("/student/{id}", 1).with(httpBasic("usuario errado", "senha")))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldReturn401WhenDeleteStudentByIdUsingIncorrectPassword() throws Exception {
        mockMvc.perform(delete("/student/{id}", 1).with(httpBasic("ec", "senha incorreta")))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser("usuariomock")
    void shouldDeleteStudentByIdUsingMockUser() throws Exception {
        mockMvc.perform(delete("/student/{id}", 8))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturn401WhenDeleteStudentByIdNotUsingAuthentication() throws Exception {
        mockMvc.perform(delete("/student/{id}", 1))
                .andExpect(status().isUnauthorized());
    }


}
