package com.jra.api.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Serializer {

    public static String serialize(int objectID, String[][] fields) {


        StringBuilder serializedObject = new StringBuilder();


        serializedObject.append(objectID);
        serializedObject.append("{\n");


        for (String[] field : fields) {

            String formattedField = field[0] + ":" + field[1] + ",\n";
            serializedObject.append(formattedField);
        }
        serializedObject.append("}");

        return serializedObject.toString();
    }

    public static String[][] deserialize(String name, String object) {


        System.out.println(name);
        String[] fields = extractTextBetweenCurlyBraces(object).replaceAll("\n", "").split(",");


        System.out.println("number of fields " + fields.length);
        String[][] deserializedArray = new String[fields.length][2];
        for (int i = 0; i < fields.length; i++) {

            String[] field = fields[i].split(":");

            //System.out.println(field.length);


            deserializedArray[i][0] = field[0];
            deserializedArray[i][1] = field[1];

        }
        return deserializedArray;
    }

    public static String extractTextBetweenCurlyBraces(String text) {
        Pattern pattern = Pattern.compile("\\{([^}]*)\\}");
        Matcher matcher = pattern.matcher(text);
        StringBuilder extractedText = new StringBuilder();
        while (matcher.find()) {
            extractedText.append(matcher.group(1)).append("\n");
        }

        return extractedText.toString();
    }
}

