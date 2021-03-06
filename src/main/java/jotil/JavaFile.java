package jotil;

import java.io.File;

public class JavaFile extends jotil.File {

    public JavaFile(File underlyingFile) {
        super(underlyingFile);
    }

    @Override
    protected String extension() {
        return ".java";
    }

    public String className() {
        return name().substring(0, name().indexOf(extension()));
    }
}
