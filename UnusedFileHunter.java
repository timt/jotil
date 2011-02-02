
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//TODO Hunt for production methods that are only used by test classes
public class UnusedFileHunter {
    public static void main(String[] args) throws IOException {
        new UnusedFileHunter().doIt();
    }

    final List<XmlFile> xmlConfigFiles = new ArrayList<XmlFile>() {{
        add(anXmlFile("/src/webapp/WEB-INF/applicationContext.xml"));
        add(anXmlFile("/src/config/struts.xml"));
        add(anXmlFile("/src/webapp/WEB-INF/web.xml"));
        add(anXmlFile("/web/WEB-INF/web.xml"));
    }};
    private final List<JavaFile> javaFiles;


    private final List<XmlFile> hibernateFiles;

    public UnusedFileHunter() {
        final List<Folder> sourceFolders = collectSubFoldersFrom(new Folder("c:/dev/src/"));
        javaFiles = collectJavaFilesFrom(sourceFolders);
        hibernateFiles = collectHibernateFilesFrom(sourceFolders);
    }

    private void doIt() throws IOException {
        final List<JavaFile> unReferencedFiles = findCrossReferenceInFiles(javaFiles);
        for (JavaFile unReferencedFile : unReferencedFiles) {
            System.out.println("unReferencedFile.name() = " + unReferencedFile.name());
        }
        System.out.println("unReferencedFiles.size() = " + unReferencedFiles.size());
    }

    private List<JavaFile> findCrossReferenceInFiles(List<JavaFile> files) {
        List<JavaFile> unreferencedJavaFiles = new ArrayList<JavaFile>();
        for (JavaFile file : files) {
            if (not(referenced(file))) {
                unreferencedJavaFiles.add(file);
            }
        }
        return unreferencedJavaFiles;
    }

    private boolean referenced(JavaFile javaFile) {
        if (
                foundInFiles(javaFile, xmlConfigFiles)
                        || foundInFiles(javaFile, javaFiles)
                        || foundInFiles(javaFile, hibernateFiles)
                ) {
            return true;
        }
        return false;
    }


    private boolean foundInFiles(JavaFile javaFile, List<? extends File> files) {
        for (File file : files) {
            if (!javaFile.equals(file) && file.hasText(javaFile.className())) {
                return true;
            }
        }
        return false;
    }

    public boolean not(boolean bool) {
        return !bool;
    }

}
