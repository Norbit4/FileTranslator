package pl.norbit.filetranslator.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pl.norbit.filetranslator.model.FileContent;
import pl.norbit.filetranslator.model.FileLine;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class DeepLServiceTest {

    private DeepLService onTest;

    @BeforeEach
    void setUp() {
        onTest = new DeepLService();
    }

    @Test
    void itShouldTranslate() {
        //given
        FileContent fileContent = new FileContent();

        FileLine line1 = new FileLine("key1", "cat");
        FileLine line2 = new FileLine("key2", "car");

        fileContent.addLine(line1);
        fileContent.addLine(line2);

        FileContent expectedFileContent = new FileContent();
        FileLine expectedLine1 = new FileLine("key1", "cat");
        FileLine expectedLine2 = new FileLine("key2", "car");

        expectedLine1.setTranslate("kot");
        expectedLine2.setTranslate("samochód");

        expectedFileContent.addLine(expectedLine1);
        expectedFileContent.addLine(expectedLine2);

        //when
        onTest.translate(fileContent);

        //then
        assertEquals(expectedFileContent, fileContent);
    }
}
