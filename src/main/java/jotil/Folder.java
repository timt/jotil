package jotil;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import org.hamcrest.MatcherAssert;

import java.io.File;
import java.util.List;

import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.transform;
import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;

public class Folder {
    private final File underlyingFolder;

    public Folder(File underlyingFolder) {
        MatcherAssert.assertThat(underlyingFolder.isDirectory(), is(true));
        this.underlyingFolder = underlyingFolder;
    }

    public Folder(String underlyingFolder) {
        this(new File(underlyingFolder));
    }


    public File[] listFilesAndFolders() {
        return underlyingFolder.listFiles();
    }

    public List<Folder> subFolders() {
       return transform(collectFolders(), asFolders());
    }

    private List<File> collectFolders() {
        return newArrayList(filter(asList(underlyingFolder.listFiles()), onlyFolders()));
    }

    private Function<File, Folder> asFolders() {
        return new Function<File, Folder>() {
            public Folder apply(File file) {
                return new Folder(file);
            }
        };
    }

    private Predicate<File> onlyFolders() {
        return new Predicate<File>() {
            public boolean apply(File file) {
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
