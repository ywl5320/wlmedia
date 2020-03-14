package com.ywl5320.wlmedia.sample.util;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * Created by wanli on 2018/6/12
 */
public class GsonFormatUtil {

    public static Object format(Object data, Type type)
    {
        try {
            Gson gson = new Gson();
            return gson.fromJson(gson.toJson(data), type);
        }catch (Exception e)
        {
        }
        return null;
    }

    public static Object format(Gson gson, Object data, Type type)
    {
        if(gson != null)
        {
            try {
                return gson.fromJson(gson.toJson(data), type);
            }catch (Exception e)
            {
            }
        }
        return null;
    }

    public static String toJson(Object data, Type type)
    {
        try
        {
            Gson gson = new Gson();
            return gson.toJson(data, type);
        }
        catch (Exception e)
        {
        }
        return null;

    }

    public static Object formatJson(String json, Type type)
    {
        try
        {
            Gson gson = new Gson();
            return gson.fromJson(json, type);
        }
        catch(Exception e)
        {
        }
        return null;
    }

}
