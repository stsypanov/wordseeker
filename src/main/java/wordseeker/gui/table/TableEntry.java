
package wordseeker.gui.table;

/**
 * @author rad1kal
 */
public class TableEntry {
    private final String pathToFile;
    private final String foundInFile;

    public TableEntry(String pathToFile, String foundInFile) {
        this.pathToFile = pathToFile;
        this.foundInFile = foundInFile;
    }

    /**
     * @return the pathToFile
     */
    public String getPathToFile() {
        return pathToFile;
    }

    /**
     * @return the foundInFile
     */
    public String getFoundInFile() {
        return foundInFile;
    }

    @Override
    public String toString() {
        return pathToFile.concat(foundInFile);
    }
}
