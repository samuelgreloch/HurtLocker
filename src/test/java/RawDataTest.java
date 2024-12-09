import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;


// const matches = 'Some Text'.match(regex);
// const index = 'Some text' .search(regex);
// const reex = /milk/g

// sooo String input "




public class RawDataTest {

    private static String RawData;

    @Before
    public void setup() throws Exception {
        RawData = (new Main()).readRawDataToString();
    }

@Test
    public void testRecords() {
    String input = RawData;

    JerkSonParser parser = new JerkSonParser();
    parser.readRawData(input);
    parser.parseData();

    List<JerkSonParser> parsedData = parser.getParsedData();


    assertNotNull(parsedData);
    assertTrue(parsedData.size()>0);

    assertEquals("Name:Milk",parsedData.get(0).toString());
    assertEquals("Price:3.23",parsedData.get(1).toString());






}

    @Test

            public void testInvalidRecord(){

    String input = "naMe:Milk;price:3.23;type:Food;expiration:1/25/2016";

        Map<String, String> expectedMap = new HashMap<>();
        expectedMap.put("naMe","Milk");
        expectedMap.put("expiration","1/25/2016");

        Map<String, String> actualMap = JerkSonParser.parseRecord(input);

        assertEquals(expectedMap, actualMap);

    }

    @Test
    public void testException(){

    String input = "naMe:Milk;price:3.23;type:Food;expiration:1/25/2016";

    int exception = JerkSonParser.getExectionCount(input);

    assertEquals(1,exception);

    }


    @Test
    public void testEmpty(){

    String emptyInput = "";

        Map<String, String> result = JerkSonParser.parseRecord(emptyInput);

        assertTrue(result.isEmpty());


    }







}
