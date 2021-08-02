package cdp.bonial.qa;

import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedBuilderParametersImpl;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.jupiter.api.BeforeAll;

import java.io.*;

public class BaseTest {

    protected static PropertiesConfiguration config = null;

    @BeforeAll
    public static void setup() throws ConfigurationException {
        final File configFile = new File("./src/test/resources/stage.properties");
        final FileBasedConfigurationBuilder<PropertiesConfiguration> builder =
                new FileBasedConfigurationBuilder<>(
                        PropertiesConfiguration.class)
                        .configure(new FileBasedBuilderParametersImpl()
                                .setFile(configFile));

        builder.getConfiguration();
        config = builder.getConfiguration();
    }

    public static void prepareXLSX(String filename, String groupName) {
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        Workbook workbook = null;
        try {
            inputStream = new FileInputStream(filename);
            workbook = WorkbookFactory.create(inputStream);

            Sheet sheet = workbook.getSheetAt(0);
            Cell cell2Update = sheet.getRow(1).getCell(20);
            cell2Update.setCellValue(groupName);
            inputStream.close();

            outputStream = new FileOutputStream(filename);
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
