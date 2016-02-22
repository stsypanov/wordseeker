package wordseeker.logics

import java.io.File

/**
 * @author stsypanov
 */
interface Parser {

    //override this method to enable word combination search
    fun findExpression(doc: File): List<String>

    //override this method to enable regexp search
    fun findRegexp(doc: File): List<String>?


}
