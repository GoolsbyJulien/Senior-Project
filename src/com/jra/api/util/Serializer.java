package com.jra.api.util;

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

    public String[][] deserialize(String object) {
        String[] fields = object.split(",");

        for (int i = 0; i < fields.length; i++) {

        }

    }
}

