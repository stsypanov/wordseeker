
package wordseeker.logics;

import java.io.File;
import java.util.List;

/**
 * @author stsypanov
 */
public interface Parser {

    //override this method to enable word combination search
    public List<String> findExpression(File doc);

    //override this method to enable regexp search
    public List<String> findRegexp(File doc);


}
