package com.example.houer_challenge.service;

import com.example.houer_challenge.dto.SchoolDeleteDTO;
import com.example.houer_challenge.model.School;
import com.example.houer_challenge.model.UserInfo;
import com.example.houer_challenge.repository.SchoolRepository;
import com.example.houer_challenge.repository.UserInfoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SchoolServiceTest {

    @Mock
    private SchoolRepository schoolRepository;

    @InjectMocks
    private SchoolService schoolService;


    private Map<String, String> buildValidRow() {
        Map<String, String> row = new HashMap<>();
        row.put("NOMEDEP", "Dep1");
        row.put("DE", "Dept");
        row.put("MUN", "Mun");
        row.put("DISTR", "Distr");
        row.put("CODESC", "123");
        row.put("NOMESC", "School1");
        row.put("TIPOESC", "1");
        row.put("TIPOESC_DESC", "PUB");
        row.put("CODSIT", "2");
        row.put("CODESC1", "321");
        row.put("SALAS_AULA", "10");
        return row;
    }

    @Test
    public void createEmptySchoolTest() {
        //Arrange
        School school = new School();
        school.setId(12L);

        when(schoolRepository.save(any(School.class))).thenReturn(school);

        //Act
        Long createdId = schoolService.createSchool();

        //Assert
        assertEquals(school.getId(), createdId);
    }

    @Test
    void shouldSaveSchoolsWhenDataIsValid() {
        //Arrange
        List<Map<String, String>> csvData = new ArrayList<>();
        csvData.add(buildValidRow());

        schoolService.saveAllCsvSchools(csvData);

        verify(schoolRepository, times(1)).saveAll(anyList());
    }

    @Test
    void testParseCsvNormal() throws Exception {
        // Arrange
        String csvContent = "CODESC;NOMESC;MUN\n" +
                "1;Escola A;Cidade X\n" +
                "2;Escola B;Cidade Y";

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "normal.csv",
                "text/csv",
                new ByteArrayInputStream(csvContent.getBytes())
        );

        // Act
        List<Map<String, String>> parsed = SchoolService.parseCsv(file);

        // Assert
        assertEquals(2, parsed.size());

        Map<String, String> firstRow = parsed.get(0);
        assertEquals("1", firstRow.get("CODESC"));
        assertEquals("Escola A", firstRow.get("NOMESC"));
        assertEquals("Cidade X", firstRow.get("MUN"));

        Map<String, String> secondRow = parsed.get(1);
        assertEquals("2", secondRow.get("CODESC"));
        assertEquals("Escola B", secondRow.get("NOMESC"));
        assertEquals("Cidade Y", secondRow.get("MUN"));
    }

    @Test
    void testParseCsvWithDuplicateHeaders() throws Exception {
        // Arrange
        String csvContent = "CODESC;NOMESC;CODESC;MUN\n" +
                "1;Escola A;100;Cidade X\n" +
                "2;Escola B;200;Cidade Y";

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test.csv",
                "text/csv",
                new ByteArrayInputStream(csvContent.getBytes())
        );

        //Act
        List<Map<String, String>> parsed = SchoolService.parseCsv(file);
        Map<String, String> firstRow = parsed.get(0);

        // Assert
        assertTrue(firstRow.containsKey("CODESC"));
        assertTrue(firstRow.containsKey("CODESC1"));
        assertEquals("1", firstRow.get("CODESC"));
        assertEquals("100", firstRow.get("CODESC1"));

        Map<String, String> secondRow = parsed.get(1);
        assertEquals("2", secondRow.get("CODESC"));
        assertEquals("200", secondRow.get("CODESC1"));
    }

    @Test
    void shouldThrowExceptionWhenIdsNull() {
        //Arrange
        SchoolDeleteDTO dto = new SchoolDeleteDTO(null);


        //Act and Assert
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> schoolService.deleteSchool(dto)
        );

        assertEquals("No ID sent for delete !", ex.getMessage());
        verifyNoInteractions(schoolRepository);
    }

    @Test
    void shouldDeleteWhenIdsProvided() {
        //Arrange
        List<Long> ids = List.of(1L, 2L, 3L);
        SchoolDeleteDTO dto = new SchoolDeleteDTO(ids);

        //Act
        schoolService.deleteSchool(dto);

        //Assert
        verify(schoolRepository, times(1)).deleteByIdIn(ids);
    }

}