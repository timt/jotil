
import jedi.functional.Filter;
import jedi.functional.Functor;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static jedi.functional.FunctionalPrimitives.collect;
import static jedi.functional.FunctionalPrimitives.select;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class Folder {
    private final File underlyingFolder;

    public Folder(File underlyingFolder) {
        assertThat(underlyingFolder.isDirectory(), is(true));
        this.underlyingFolder = underlyingFolder;
    }

    public Folder(String underlyingFolder) {
        this(new File(underlyingFolder));
    }


    public File[] listFilesAndFolders() {
        return underlyingFolder.listFiles();
    }

    public List<Folder> subFolders() {
       return collect(select(Arrays.asList(underlyingFolder.listFiles()), onlyFolders()), asFolders());
    }

    private Functor<File, Folder> asFolders() {
        return new Functor<File, Folder>() {
            public Folder execute(File file) {
                return new Folder(file);
            }
        };
    }

    private Filter<File> onlyFolders() {
        return new Filter<File>() {
            public Boolean execute(File file) {
                return file.isDirectory();
            }
        };
    }

    public boolean isSourceFolder() {
        return name().endsWith("\\src\\java");
    }

    public boolean isNotSourceFolder() {
        return !isSourceFolder();
    }

    public String name() {
        return underlyingFolder.getAbsolutePath();
    }
}
