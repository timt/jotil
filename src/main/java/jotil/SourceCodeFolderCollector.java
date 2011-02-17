package jotil;

import jotil.Folder;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;

class SourceCodeFolderCollector {
    public static List<Folder> collectSubFoldersFrom(Folder folder){
        return new SourceCodeFolderCollector().collectFromFolder(folder);
    }

    private List<Folder> collectFromFolder(Folder folder) {
        List<Folder> sourceFolders = new ArrayList<Folder>();
        for (Folder subFolder : folder.subFolders()) {
            sourceFolders.addAll(handleSourceFolders(subFolder));
            sourceFolders.addAll(handleNonSourceFolders(subFolder));
        }
        return sourceFolders;
    }

    private List<Folder> handleNonSourceFolders(Folder subFolder) {
        if(subFolder.isNotSourceFolder()){
            return collectFromFolder(subFolder);
        }
        return emptyList();
    }

    private List<Folder> handleSourceFolders(final Folder subFolder) {
        if(subFolder.isSourceFolder()){
            return new ArrayList<Folder>(){{add(subFolder);}};
        }
        return emptyList();
    }
}
