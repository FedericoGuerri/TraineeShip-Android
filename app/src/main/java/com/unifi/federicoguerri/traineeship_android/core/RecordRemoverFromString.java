package com.unifi.federicoguerri.traineeship_android.core;


public class RecordRemoverFromString {

    public String remove(String data, int index) {
        if(data.startsWith(" ")){
            data=data.substring(1);
        }
        index=index*2;
        String[] records = data.split(" ");
        StringBuilder stringBuilder=new StringBuilder();
        for(int i=0;i<records.length;i++){
            if(i!=index && i!=index+1){
                stringBuilder.append(records[i]+" ");
            }
        }
        if(!stringBuilder.toString().endsWith(" ")){
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }
}
