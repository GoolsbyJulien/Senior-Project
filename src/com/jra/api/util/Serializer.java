package com.jra.api.util;

import com.jra.api.core.MapObject;
import com.jra.app.MapObjects.SelectableObject;

import java.awt.*;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Serializer {

    public static String serialize(String objectID, String[][] fields) {


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

    public static MapObject deserialize(String objectID, String object) {


        String[] fields = extractTextBetweenCurlyBraces(object).replaceAll("\n", "").split(",");
        System.out.println(objectID + " " + Arrays.toString(fields));

        String[][] deserializedArray = new String[fields.length][2];
        for (int i = 0; i < fields.length; i++) {

            String[] field = fields[i].split(":");

            //System.out.println(field.length);


            deserializedArray[i][0] = field[0];
            deserializedArray[i][1] = field[1];

        }


        try {
            if (objectID.equals("SO")) {
                SelectableObject temp = new SelectableObject(new Vector(Integer.parseInt(deserializedArray[0][1]), Integer.parseInt(deserializedArray[1][1])));

                temp.setColor(new Color(Integer.parseInt(deserializedArray[2][1])));
                temp.setLabel(deserializedArray[3][1]);
                temp.changeSize(Integer.parseInt(deserializedArray[4][1]));
                temp.setDescription(deserializedArray[5][1]);

                return temp;
            }

        } catch (Exception e) {

            System.err.println("Corrupted Object  " + objectID);
        }

        return null;
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
