/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package regexapp;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import org.simpleflatmapper.csv.CsvParser;
import org.simpleflatmapper.util.ErrorHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.simpleflatmapper.lightningcsv.CloseableCsvReader;
import org.simpleflatmapper.lightningcsv.parser.CellConsumer;

/**
 *
 * @author Haris Tanwir
 */
public class RegexApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ParseException {

        String jsonArrayString = new RegexApp().getJSONData("C:\\Users\\Haris Tanwir\\Desktop\\csv2json\\ussd_notification_message_formats_13aug20.csv");
        String commandID = "SendP2p";
        String strussd = "Rs 1000 sent to Mariam Farrukh JazzCash A/C: 03079770309. Fee: Rs 000. Deduction: Rs 1000, Balance: Rs 19,97000. TID: 010381858348";
        strussd = strussd.replace("\n", "").replace("\r", "");

        JSONArray jsonArray = (JSONArray) new JSONParser().parse(jsonArrayString);
        HashMap<String, String> ussdMap = null;

        for (Object _jsonObj : jsonArray) {
            JSONObject jsonObj = (JSONObject) _jsonObj;
            if (commandID.equalsIgnoreCase((String) jsonObj.get("COMMAND_ID"))) {
                String strussd_format = (String) jsonObj.get("CONTENT");
                strussd_format = new RegexApp().filterUssdPattern(strussd_format);
                ussdMap = new RegexApp().matchUssdPattern(strussd, strussd_format);
                if (ussdMap != null) {
                    break;
                }
            }
        }
        if (ussdMap != null) {
            for (Map.Entry<String, String> entry : ussdMap.entrySet()) {
                System.out.println(entry.getKey() + " -> " + entry.getValue().toString());
            }
        }
    }

    public HashMap<String, String> matchUssdPattern(String strussd, String strussd_format) {

        HashMap<String, String> ussdMap = null;
        String delimiters = "\\{\\{([^}]+)\\}\\}";

        String[] tokensVal = strussd_format.split(delimiters);

        for (String token : tokensVal) {
            if (!token.isEmpty()) {
                strussd = strussd.replace(token, "|");
                strussd_format = strussd_format.replace(token, "|");
            }
        }

        String[] splitValue = strussd.split("[|]", -1);
        String[] splitKey = strussd_format.split("[|]", -1);

        System.out.println("Is pattern matched : " + (splitKey.length == splitValue.length));

        if ((splitKey.length == splitValue.length)) {
            ussdMap = new HashMap<String, String>();
            for (int i = 0; i < splitValue.length; i++) {
                ussdMap.put(splitKey[i].replaceAll("[{]", "").replaceAll("[}]", ""), splitValue[i]);
            }
        }

        return ussdMap;

    }

    public String getJSONData(String csvPath) throws IOException {
        CloseableCsvReader reader = CsvParser.reader(new File(csvPath));
        JsonFactory jsonFactory = new JsonFactory();
        Writer result = new StringWriter();
        PrintWriter printWriter = new PrintWriter(result);
        List<String> headers = new ArrayList<>();
        if (reader.parseRow(CellConsumer.of(headers::add))) {
            try (JsonGenerator jsonGenerator = jsonFactory.createGenerator(printWriter)) {
                jsonGenerator.writeStartArray();
                reader.parseAll(new CellConsumer() {
                    int index = 0;

                    public void newCell(char[] chars, int offset, int length) {
                        try {
                            if (index == 0) {
                                jsonGenerator.writeStartObject();
                            }
                            if (index < headers.size()) {
                                jsonGenerator.writeFieldName(headers.get(index));
                                jsonGenerator.writeString(chars, offset, length);
                            }
                            index++;
                        } catch (IOException e) {
                            ErrorHelper.rethrow(e);
                        }
                    }

                    public boolean endOfRow() {
                        try {
                            if (index != 0) {
                                jsonGenerator.writeEndObject();
                            }
                        } catch (IOException e) {
                            ErrorHelper.rethrow(e);
                        }
                        index = 0;
                        return true;
                    }

                    public void end() {
                        endOfRow();
                    }
                });
                jsonGenerator.writeEndArray();
            }
        }
        return result.toString();
    }

    private String filterUssdPattern(String _strussd_format) {
        String strussd_format = _strussd_format;
        strussd_format = strussd_format.replace("\n", "").replace("\r", "");
        while (strussd_format.indexOf("{{#formatDateTime}}") != -1) {
            String substr_tmp = strussd_format.substring(strussd_format.indexOf("{{#formatDateTime}}"), strussd_format.indexOf("{{/formatDateTime}}") + "{{/formatDateTime}}".length());
            System.out.println(substr_tmp);
            strussd_format = strussd_format.replace(substr_tmp, "{{.}}");
        }
        return strussd_format;
    }
}
