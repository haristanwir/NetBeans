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

        String jsonArrayString = new RegexApp().getJSONData("C:\\ACE\\csv2json\\ussd_notification_message_formats_test.csv");
        String _jsonArrayString = new RegexApp().getJSONData("C:\\ACE\\csv2json\\ussd_notification_mappings.csv");
        String commandID = "VIEWBILL";
        String strussd = "TID 010710979849. 00180566 - kuickpay bill of Rs 12,000.00 of 00008181329954 due by 2023-12-31 paid on 11:27:15 AM. Collect:Rs 12,000.00, Bal:Rs 845,003.73";
        //String commandID = "balance";
        //String strussd = "Rs 10,947.00 is your available balance on JazzCash Mobile A/C: 7376362, as of 28/09/2020 05:27:13 PM.";
        //String commandID = "SENDOTC";
        //String strussd = "TID 010710873700. Rs 100.00 sent to JazzCash A/C # 3246284 , 03079770700 on 30/09/2020 00:36:29 AM. Collect:Rs 100.00, Fee:Rs 0.00, Bal:Rs 247,645.67";
        strussd = strussd.replace("\n", "").replace("\r", "");

        JSONArray jsonArray = (JSONArray) new JSONParser().parse(jsonArrayString);
        JSONArray _jsonArray = (JSONArray) new JSONParser().parse(_jsonArrayString);
        HashMap<String, String> ussdMap = null;
        
        //ussdMap = new RegexApp().matchUssdPattern(strussd, "TID {{Transaction.BasicInfo.TransLogId}}. {{Transaction.Credit.IdentityName}} bill of {{Transaction.FeeInfo.ActualAmount}} of {{Transaction.PayBillInfo.BillReferenceNumber}} due by {{Transaction.BillInfo.DueDate}} paid on {{Transaction.BasicInfo.TransInitiateTime}}. Collect:{{Transaction.Deduction}}, Bal:{{Transaction.Debit.IdentityAccountBalance}}");
        
        //System.out.println(new RegexApp().jazzrules("{{#Transaction.LimitRemind.LimitRemind}}{{#switch}} case [{{LimitRuleId}}] == [5] : [LimitRuleName: {{LimitRuleName}} RemainingValue: {{RemainingValue}}];default : [];{{/switch}}{{/Transaction.LimitRemind.LimitRemind}}"));
        for (Object _jsonObj : jsonArray) {
            JSONObject jsonObj = (JSONObject) _jsonObj;
            if (commandID.equalsIgnoreCase((String) jsonObj.get("COMMAND_ID"))) 
            {
                String strussd_format = (String) jsonObj.get("CONTENT");
                strussd_format = strussd_format.replace("\n", "").replace("\r", "");

                //System.out.println((strussd_format));
                //System.out.println(new RegexApp().jazzrules(strussd_format));
                //System.out.println("_____________________________");
                try {
                    strussd_format = new RegexApp().jazzrules(strussd_format);
                    ussdMap = new RegexApp().matchUssdPattern(strussd, strussd_format);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
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
        int idx = 0;
        for (String token : tokensVal) {
            if (!token.isEmpty() && !token.equals(".") && !token.equals(",")) {
                int n_idx = strussd_format.indexOf(token, idx);
                strussd_format = strussd_format.substring(0, n_idx) + "|" + strussd_format.substring(n_idx + token.length(), strussd_format.length());
                idx = n_idx;
            }
        }
        idx = 0;
        for (String token : tokensVal) {
            if (!token.isEmpty() && !token.equals(".") && !token.equals(",")) {
                int n_idx = strussd.indexOf(token, idx);
                strussd = strussd.substring(0, n_idx) + "|" + strussd.substring(n_idx + token.length(), strussd.length());
                idx = n_idx;
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

    private String jazzrules(String strussd_format) {
        //strussd_format = "Pay bill against PSID: {{Transaction.PayBillInfo.BillReferenceNumber}} due by {{#formatDateTime}}{{Transaction.BillInfo.DueDate}},yyyy-MM-dd{{/formatDateTime}}? Bill Amount {{Transaction.BillInfo.DueAmount}} with Fee Rs 0. Enter MPIN to confirm.";

        strussd_format = strussd_format.replaceAll("\\{\\{.\\}\\}", "");
        if (strussd_format.contains("{{#")) {
            int idx = 0;
            while (idx < strussd_format.length()) {
                if (strussd_format.indexOf("{{#", idx) == -1) {
                    break;
                }
                int tagstartidx = strussd_format.indexOf("{{#", idx) + 3;
                int tagendidx = strussd_format.indexOf("}}", tagstartidx);
                String blockname = strussd_format.substring(tagstartidx, tagendidx);
                if (strussd_format.indexOf("{{#" + blockname + "}}", idx) == -1) {
                    break;
                }
                int blockstartidx = strussd_format.indexOf("{{#" + blockname + "}}", idx);
                int blockendidx = strussd_format.indexOf("{{/" + blockname + "}}", blockstartidx) + new String("{{/" + blockname + "}}").length();
                String blockdata = strussd_format.substring(blockstartidx, blockendidx);
                String blockinnerdata = strussd_format.substring(blockstartidx + new String("{{#" + blockname + "}}").length(), blockendidx - new String("{{/" + blockname + "}}").length());
                if (blockdata.startsWith("{{#switch")) {
                    strussd_format = strussd_format.replace(blockdata, "{{" + blockname + "}}");
                    idx = blockendidx - (blockdata.length()) + new String("{{" + blockname + "}}").length();
                } else {
                    if (!blockinnerdata.contains("{{#")) {
                        if (blockinnerdata.contains("{{")) {
                            String[] innertags = blockinnerdata.split("[}}]", -1);
                            String innertagvalues = "";
                            for (String tag : innertags) {
                                if (tag.contains("{{")) {
                                    innertagvalues += tag + "}}";
                                } else {
                                    //innertagvalues += tag;
                                }
                            }
                            strussd_format = strussd_format.replace(blockdata, innertagvalues);
                            idx = blockendidx - (blockdata.length()) + new String(innertagvalues).length();
                        } else {
                            strussd_format = strussd_format.replace(blockdata, "{{" + blockname + "}}");
                            idx = blockendidx - (blockdata.length()) + new String("{{" + blockname + "}}").length();
                        }
                    } else {
                        //if (blockinnerdata.startsWith("{{#switch")) {
                        //    strussd_format = strussd_format.replace(blockdata, "{{switch}}");
                        //} else {
                            strussd_format = strussd_format.replace(blockdata, "{{" + blockname + "}}");
                            idx = blockendidx - (blockdata.length()) + new String("{{" + blockname + "}}").length();
                        //}
                    }
                }
                // System.out.println(strussd_format);
            }
        }
        if (strussd_format.contains("{{^")) {
            int idx = 0;
            while (idx < strussd_format.length()) {
                if (strussd_format.indexOf("{{^", idx) == -1) {
                    break;
                }
                int tagstartidx = strussd_format.indexOf("{{^", idx) + 3;
                int tagendidx = strussd_format.indexOf("}}", tagstartidx);
                String blockname = strussd_format.substring(tagstartidx, tagendidx);
                if (strussd_format.indexOf("{{^" + blockname + "}}", idx) == -1) {
                    break;
                }
                int blockstartidx = strussd_format.indexOf("{{^" + blockname + "}}", idx);
                int blockendidx = strussd_format.indexOf("{{/" + blockname + "}}", blockstartidx) + new String("{{/" + blockname + "}}").length();
                String blockdata = strussd_format.substring(blockstartidx, blockendidx);
                strussd_format = strussd_format.replace(blockdata, "");
                //System.out.println(strussd_format);
            }
        }
        return strussd_format;
    }

}
