import jdk.internal.util.SystemProps;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

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

    private Main data;

    @BeforeEach
    public void setUp(){
        data = new Main();
    }

@Test
    public void testRecords() {
    String RawData = "naMe:Milk;price:3.23;type:Food;expiration:1/25/2016##naME:BreaD;price:1.23;type:Food;expiration:1/02/2016##";

    String parsedData = data.parseRawData(RawData);

    String expectedOutput = "Name: Milk\\nPrice: 3.23\\nType: Food\\nExpiration: 1/25/2016\\n\\n" + "Name: Bread\\nPrice: 1.23\\nType: Food\\nExpiration: 1/02/2016\\n\\n";

    assertEquals(expectedOutput, parsedData,"Parsed data matches output");
}

    @Test

            public void testInvalidRecord(){


        String RawData = "naMe:Milk;price:3.23;type:;expiration:1/25/2016##naME:BreaD;price:1.23;type:Food;expiration:##";

        String parsedData = data.parseRawData(RawData);

        String expectedOutput = "Name: Milk\\nPrice: 3.23\\nType: N/A\\nExpiration: 1/25/2016\\n\\n\" +\n" +
                "                \"Name: Bread\\nPrice: 1.23\\nType: Food\\nExpiration: N/A\\n\\n";

        assertEquals(expectedOutput, parsedData, "Parsed data handles missing values");




    }

    @Test
    public void testException(){


        String RawData = "naMe:;price:;type:;expiration:1/25/2016##";

        data.parseRawData(RawData);

        List<String> exceptions = Main.getExceptions();

        assertEquals(3,exceptions.size(),"There are 3 exceptions");
        assertTrue("Exception for Name should be logged", exceptions.contains("Missing value for key: Name"));
        assertTrue("Exception of missing Price", exceptions.contains("Missing value for key: Price"));
        assertTrue("Exception of missing Type", exceptions.contains("Missing value for key: Type"));

    }


    @Test
    public void testEmpty(){

  String 

    }







}
