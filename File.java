
import org.apache.commons.io.FileUtils;

import java.io.IOException;

public abstract class File {
    private final java.io.File underlyingFile;
    public final String asString;

    public File(java.io.File underlyingFile) {
        this.underlyingFile = underlyingFile;
        try {
            asString = FileUtils.readFileToString(underlyingFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract String extension();

    public String name() {
        return underlyingFile.getName();
    }

    public String filePath() {
        return underlyingFile.getAbsolutePath();
    }

    public boolean hasText(String textToFind) {
        return asString.contains(textToFind);
    }
}
