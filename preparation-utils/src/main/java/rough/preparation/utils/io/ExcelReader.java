package rough.preparation.utils.io;

import lombok.extern.apachecommons.CommonsLog;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 从 Excel 文件中读取内容。
 * 根据文件后缀名区分格式。
 * 暂时只支持一个 Sheet，并假设文件的第一行为标题。
 */
@CommonsLog
public abstract class ExcelReader {

    public abstract List<String> getTitle();

    public static ExcelReader createReader(File file) throws IOException, InvalidFormatException {
        if (file.getName().toLowerCase().endsWith(".xls")) {
            return new XlsReader(file);
        } else if (file.getName().toLowerCase().endsWith(".xlsx")) {
            return new XlsxReader(file);
        } else {
            String msg = file.getName() + " is not supported.";
            log.error(msg);
            throw new RuntimeException(msg);
        }
    }
}
