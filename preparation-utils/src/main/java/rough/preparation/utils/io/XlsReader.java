package rough.preparation.utils.io;

import java.io.File;
import java.util.List;

public class XlsReader extends ExcelReader {

    public XlsReader(File file) {
        super();
    }

    @Override
    public List<String> getTitle() {
        return null;
    }
}
