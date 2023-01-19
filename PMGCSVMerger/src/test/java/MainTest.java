import org.junit.Test;

import java.nio.file.FileSystems;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    @Test
    public void invalidPathFileDoesNotExist() {
        assertTrue(Main.invalidPath(FileSystems.getDefault().getPath("C:\\Users\\hello.csv")));
    }
    @Test
    public void invalidPathFileNotCSV() {
        assertTrue(Main.invalidPath(FileSystems.getDefault().getPath("C:\\Users\\perry\\Downloads\\Diploma.pdf")));
    }
    @Test
    public void invalidPathValid() {
        assertFalse(Main.invalidPath(FileSystems.getDefault().getPath("C:\\Users\\perry\\Downloads\\clothing.csv")));
    }

    @Test
    public void isValidFilename() {
        assertTrue(Main.isValidFilename("games.csv"));
    }
    @Test
    public void isNotValidFilename() {
        assertFalse(Main.isValidFilename("%thisIsNotAGoodName&&[{"));
    }

    @Test
    public void isValidDirectory() {
        assertTrue(Main.isValidDirectory(FileSystems.getDefault().getPath("C:\\Users\\perry\\Downloads")));
    }
    @Test
    public void isNotValidDirectory() {
        assertFalse(Main.isValidDirectory(FileSystems.getDefault().getPath("C:\\Users\\perry\\Downloas")));
    }
}