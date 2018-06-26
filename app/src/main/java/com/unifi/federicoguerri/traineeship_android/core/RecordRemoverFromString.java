package com.unifi.federicoguerri.traineeship_android.core;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

class RecordRemoverFromString {

    public String remove(String data, int index) {
        if(!data.startsWith(" ")){
            data=" "+data;
        }

        data = removePriceAndMiniaturePath(data, index);

        if(data.startsWith(" ")){
            data=data.substring(1);
        }
        if(!data.endsWith(" ")){
            data=data+" ";
        }

        return data;
    }

    private String removePriceAndMiniaturePath(String data, int index) {
        Pattern whitespace = Pattern.compile("\\s");
        Matcher matcher = whitespace.matcher(data);
        int foundSpaces=0;
        while (matcher.find()){
            if(foundSpaces==index*2){
                int firstSpaceIndex=matcher.end();
                matcher.find(matcher.end());
                matcher.find(matcher.end());
                data=data.substring(0,firstSpaceIndex)+data.substring(matcher.end(),data.length());
                break;
            }
            foundSpaces++;
        }
        return data;
    }
}
