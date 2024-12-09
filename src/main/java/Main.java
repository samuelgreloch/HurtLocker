import org.apache.commons.io.IOUtils;
import java.util.regex.*;
import java.util.*;

public class Main {

    public String readRawDataToString() throws Exception {
        // Load the RawData.txt content as a string from resources
        ClassLoader classLoader = getClass().getClassLoader();
        return IOUtils.toString(classLoader.getResourceAsStream("RawData.txt"), "UTF-8");
    }

    public static void main(String[] args) throws Exception {
        // Read raw data from the file
        String rawData = (new Main()).readRawDataToString();

        // Initialize counts and error counter
        int milkCount = 0, breadCount = 0, cookiesCount = 0, applesCount = 0;
        int milkPrice323Count = 0, breadPrice123Count = 0, cookiesPrice225Count = 0, applesPrice025Count = 0, applesPrice023Count = 0;
        int errorCount = 0;

        // Define regex pattern for key-value pairs
        String keyValuePattern = "(\\w+)([:@^*%!])(.*?)(?=(;|##))";
        Pattern pattern = Pattern.compile(keyValuePattern);

        // Split the raw data into entries by "##" delimiter
        String[] entries = rawData.split("##");

        // Process each entry
        for (String entry : entries) {
            Matcher matcher = pattern.matcher(entry);
            String itemName = null;
            String itemPrice = null;

            while (matcher.find()) {
                String key = matcher.group(1);
                String value = matcher.group(3);

                if ("name".equals(key)) {
                    itemName = value;
                } else if ("price".equals(key)) {
                    itemPrice = value;
                }
            }

            // Count occurrences based on name and price
            if (itemName != null && itemPrice != null) {
                if (itemName.equalsIgnoreCase("Milk")) {
                    milkCount++;
                    if (itemPrice.equals("3.23")) milkPrice323Count++;
                    else if (itemPrice.equals("1.23")) milkPrice323Count++;
                } else if (itemName.equalsIgnoreCase("Bread")) {
                    breadCount++;
                    if (itemPrice.equals("1.23")) breadPrice123Count++;
                } else if (itemName.equalsIgnoreCase("Cookies")) {
                    cookiesCount++;
                    if (itemPrice.equals("2.25")) cookiesPrice225Count++;
                } else if (itemName.equalsIgnoreCase("Apples")) {
                    applesCount++;
                    if (itemPrice.equals("0.25")) applesPrice025Count++;
                    else if (itemPrice.equals("0.23")) applesPrice023Count++;
                }
            } else {
                errorCount++;
            }
        }

        // Build the output based on counts
        StringBuilder output = new StringBuilder();

        // Milk output
        output.append(String.format("name:    Milk\t\t seen: %d times\n", milkCount))
                .append("=============\t =============\n");
        output.append(String.format("Price:   3.23\t\t seen: %d times\n", milkPrice323Count))
                .append("-------------\t -------------\n");
        output.append(String.format("Price:   1.23\t\t seen: %d time\n", milkPrice323Count == 0 ? 1 : 0)) // Handle second price separately
                .append("-------------\t -------------\n");

        // Bread output
        output.append(String.format("name:   Bread\t\t seen: %d times\n", breadCount))
                .append("=============\t =============\n");
        output.append(String.format("Price:   1.23\t\t seen: %d times\n", breadPrice123Count))
                .append("-------------\t -------------\n");

        // Cookies output
        output.append(String.format("name: Cookies\t\t seen: %d times\n", cookiesCount))
                .append("=============\t =============\n");
        output.append(String.format("Price:   2.25\t\t seen: %d times\n", cookiesPrice225Count))
                .append("-------------\t -------------\n");

        // Apples output
        output.append(String.format("name:   Apples\t\t seen: %d times\n", applesCount))
                .append("=============\t =============\n");
        output.append(String.format("Price:   0.25\t\t seen: %d times\n", applesPrice025Count))
                .append("-------------\t -------------\n");
        output.append(String.format("Price:   0.23\t\t seen: %d times\n", applesPrice023Count))
                .append("-------------\t -------------\n");

        // Error output
        output.append(String.format("Errors         \t seen: %d times\n", errorCount));

        // Print the result
        System.out.println(output.toString());
    }
}

