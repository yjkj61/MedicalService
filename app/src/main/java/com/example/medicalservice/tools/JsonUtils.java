package com.example.medicalservice.tools;

import com.google.gson.Gson;

import java.util.List;

public class JsonUtils {

     static Gson gson=new Gson();
     
     public static <T> T parse(String jsonStr, Class<T> clazz){

         return gson.fromJson(jsonStr, clazz);

     }

    public static <T> String stringify(List<T> list){

        return gson.toJson(list);

    }

    public static <T> String getJson(Class<T> clazz){

        return gson.toJson(clazz);

    }


    public static boolean isboolean(String str){
        int i=str.indexOf("true");
        int j=str.indexOf("false");
        if(i!=-1||j!=-1){//等于-1说明不存在
            return true;//存在
        }else{
            return false;//不存在
        }
    }

    /**
     * 半角转换为全角
     *
     * @param input
     * @return
     */
    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

}
