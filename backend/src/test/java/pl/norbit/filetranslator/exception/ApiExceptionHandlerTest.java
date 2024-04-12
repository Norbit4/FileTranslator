package pl.norbit.filetranslator.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

@SpringBootTest
class ApiExceptionHandlerTest {

    @Autowired
    private ApiExceptionHandler underTest;

    @Test
    @DisplayName("Should handle FileException")
    void should_handle_FileException() {
        //given
        FileException mockException = mock(FileException.class);

        //when
        ResponseEntity<ApiException> response = underTest.handleFileException(mockException);

        //then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Should handle FileReadException")
    void should_handle_FileReadException() {
        //given
        FileReadException mockException = mock(FileReadException.class);

        //when
        ResponseEntity<ApiException> response = underTest.handleFileReadException(mockException);

        //then
        assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Should handle FileWriteException")
    void should_handle_FileWriteException() {
        //given
        FileWriteException mockException = mock(FileWriteException.class);

        //when
        ResponseEntity<ApiException> response = underTest.handleFileWriteException(mockException);

        //then
        assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Should handle JsonException")
    void should_handle_JsonException() {
        //given
        JsonException mockException = mock(JsonException.class);

        //when
        ResponseEntity<ApiException> response = underTest.handleJsonException(mockException);

        //then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Should handle RuntimeException")
    void should_handle_RuntimeException() {
        //given
        RuntimeException mockException = mock(RuntimeException.class);

        //when
        ResponseEntity<ApiException> response = underTest.handleOther(mockException);

        //then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}