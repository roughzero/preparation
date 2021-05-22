package rough.preparation.utils.io;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XlsxReader extends ExcelReader {
    private static final int TITLE_ROW = 0;
    private final XSSFSheet sheet;
    private final List<String> title;

    public XlsxReader(File file) throws IOException, InvalidFormatException {
        super();
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        this.sheet = workbook.getSheetAt(0);
        // read Title
        XSSFRow row = sheet.getRow(TITLE_ROW);
        title = new ArrayList<>();
        for (int i = row.getFirstCellNum(); i <= row.getLastCellNum(); i++) {
            title.add(row.getCell(i).getStringCellValue());
        }
    }

    @Override
    public List<String> getTitle() {
        return title;
    }

    public List<String> readLineAsString(int rowNumber) {
        List<String> result = new ArrayList<>();
        XSSFRow row = sheet.getRow(rowNumber);
        for (int i = row.getFirstCellNum(); i <= row.getLastCellNum(); i++) {
            result.add(row.getCell(i).getStringCellValue());
        }
        return result;
    }
}
