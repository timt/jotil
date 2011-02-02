
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;

public class FileCollector<T> {
    private final String extension;
    private final NewFileCreator<T> newFileCreator;

    public static List<JavaFile> collectJavaFilesFrom(List<Folder> folders) {
        return new FileCollector<JavaFile>(".java", javaFileCreator()).collectFromFolders(folders);
    }

    public static List<XmlFile> collectHibernateFilesFrom(List<Folder> folders) {
        return new FileCollector<XmlFile>(".hbm.xml", hibernateFileCreator()).collectFromFolders(folders);
    }

    public FileCollector(String extension, NewFileCreator<T> newFileCreator) {
        this.extension = extension;
        this.newFileCreator = newFileCreator;
    }


    private List<T> collectFromFolders(List<Folder> folders) {
        List<T> filesFound = new ArrayList<T>();
        for (Folder folder : folders) {
            filesFound.addAll(collectFromFolder(folder));
        }
        return filesFound;
    }


    private List<T> collectFromFolder(Folder folder) {
        List<T> filesFound = new ArrayList<T>();
        File[] files = folder.listFilesAndFolders();
        for (File fileOrFolder : files) {
            filesFound.addAll(handleFolders(fileOrFolder));
            filesFound.addAll(handleFiles(fileOrFolder));
        }
        return filesFound;
    }

    private List<T> handleFiles(final File fileOrFolder) {
        if (isFileWithExtension(fileOrFolder)) {
            return new ArrayList<T>() {{
                add(newFileCreator.createFileFrom(fileOrFolder));
            }};
        }
        return emptyList();
    }

    private boolean isFileWithExtension(File fileOrFolder) {
        return fileOrFolder.isFile() && fileOrFolder.getName().endsWith(extension);
    }

    private List<T> handleFolders(File fileOrFolder) {
        if (fileOrFolder.isDirectory()) {
            return collectFromFolder(new Folder(fileOrFolder));
        }
        return emptyList();
    }

    private static NewFileCreator<JavaFile> javaFileCreator() {
        return new NewFileCreator<JavaFile>(){
            public JavaFile createFileFrom(File file) {
                return new JavaFile(file);
            }
        };
    }

    private static NewFileCreator<XmlFile> hibernateFileCreator() {
        return new NewFileCreator<XmlFile>() {
            public XmlFile createFileFrom(File file) {
                return new XmlFile(file);
            }
        };
    }

    private static interface NewFileCreator<T> {
        T createFileFrom(File file);
    }
}
