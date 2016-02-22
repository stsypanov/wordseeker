package wordseeker.gui.table

/**
 * @author rad1kal
 */
class TableEntry(
        /**
         * @return the pathToFile
         */
        val pathToFile: String,
        /**
         * @return the foundInFile
         */
        val foundInFile: String) {

    override fun toString(): String {
        return pathToFile + foundInFile
    }
}
