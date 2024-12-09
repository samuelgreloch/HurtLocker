import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;


// const matches = 'Some Text'.match(regex);
// const index = 'Some text' .search(regex);
// const reex = /milk/g

// sooo String input "




public class RawDataTest {


@Test
    public void testRecords(){
String input = "naMe:Milk;price:3.23;type:Food;expiration:1/25/2016";

    Map<String, String> expectedMap = new HashMap<>();
    expectedMap.put("naMe","Milk");
    expectedMap.put("price","3.23");
    expectedMap.put("type","food");
    expectedMap.put("experition","1/24/2016");

    Map<String, String> actualMap = JerkSonParser.parseRecord(input);

    assertEquals(expectedMap,actualMap);


}


}
