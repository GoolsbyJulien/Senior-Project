package com.jra.api.util;

public class Serializer {

    public static String serialize(int objectID, String[][] fields) {


        StringBuilder serializedObject = new StringBuilder();


        serializedObject.append(objectID);
        serializedObject.append("{\n");


        for (int x = 0; x < fields.length; x++) {

            String formattedField = fields[x][0] + ":" + fields[x][1] + ",\n";
            serializedObject.append(formattedField);
        }
        serializedObject.append("}");

        return serializedObject.toString();
    }
}

