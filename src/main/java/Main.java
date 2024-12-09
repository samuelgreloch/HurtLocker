import org.apache.commons.io.IOUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.*;
import java.util.regex.*;

public class Main {
    public static void main(String[] args) throws Exception {
        String rawData = (new Main()).readRawDataToString();
        String output = parseJerkSON(rawData);
        System.out.println(output);
    }

    public String readRawDataToString() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        return new String(Objects.requireNonNull(classLoader.getResourceAsStream("RawData.txt"))
                .readAllBytes());
    }

    public static String parseJerkSON(String rawData) {
        // Regex for splitting entries and extracting key-value pairs
        String entryDelimiter = "##";
        String keyValuePattern = "(\\w+)([:@^*%!])(.*?)(?=(;|##))";

        // Storage for processed data
        Map<String, Map<String, Integer>> data = new LinkedHashMap<>();
        int errorCount = 0;

        // Split by entry delimiter
        String[] entries = rawData.split(entryDelimiter);
        Pattern pattern = Pattern.compile(keyValuePattern);

        for (String entry : entries) {
            Map<String, String> entryData = new HashMap<>();
            Matcher matcher = pattern.matcher(entry);

            while (matcher.find()) {
                String key = normalizeKey(matcher.group(1));
                String value = matcher.group(3).trim();

                if (key.isEmpty() || value.isEmpty()) {
                    errorCount++;
                } else {
                    entryData.put(key, value);
                }
            }

            // Process name and price
            if (entryData.containsKey("name")) {
                String itemName = normalizeName(entryData.get("name"));
                String itemPrice = entryData.getOrDefault("price", "");

                if (!itemName.isEmpty() && !itemPrice.isEmpty()) {
                    data.computeIfAbsent(itemName, k -> new LinkedHashMap<>())
                            .merge(itemPrice, 1, Integer::sum);
                }
            }
        }

        return formatOutput(data, errorCount);
    }

    private static String normalizeKey(String key) {
        if (key == null) return "";
        return key.toLowerCase().trim();
    }

    private static String normalizeName(String name) {
        if (name == null) return "";
        name = name.toLowerCase().replaceAll("[^a-z]", ""); // Remove non-alphabetic chars
        if (name.contains("milk")) return "Milk";
        if (name.contains("bread")) return "Bread";
        if (name.contains("cookies") || name.contains("cokies")) return "Cookies";
        if (name.contains("apples")) return "Apples";
        return "";
    }

    private static String formatOutput(Map<String, Map<String, Integer>> data, int errorCount) {
        StringBuilder output = new StringBuilder();

        for (Map.Entry<String, Map<String, Integer>> entry : data.entrySet()) {
            String name = entry.getKey();
            Map<String, Integer> prices = entry.getValue();

            output.append(String.format("name:    %-10s\t seen: %d times\n", name, prices.values().stream().mapToInt(Integer::intValue).sum()))
                    .append("=============\t =============\n");

            for (Map.Entry<String, Integer> priceEntry : prices.entrySet()) {
                output.append(String.format("Price:   %-10s\t seen: %d times\n", priceEntry.getKey(), priceEntry.getValue()))
                        .append("-------------\t -------------\n");
            }

            output.append("\n");
        }

        output.append(String.format("Errors         \t seen: %d times\n", errorCount));
        return output.toString();
    }
}

