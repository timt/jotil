package jotil;

import java.io.File;

public class XmlFile extends jotil.File{

    public XmlFile(File underlyingFile) {
        super(underlyingFile);
    }

    @Override
    protected String extension() {
        return ".xml";
    }

    public static XmlFile anXmlFile(String filePath) {
            final String sourceFolder = "c:/dev/src/";
            return new XmlFile(new File(sourceFolder + filePath));
    }
}
