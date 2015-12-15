import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

// shout out to king shushu
public class JsonUtil {

    /**
     * string转换成map, 分隔符是spacer
     */
    public static Map<String, String> toMap(String input, String spacer) {
        Map<String, String> map = new HashMap<>();

        String[] nameValuePairs = input.split(spacer);
        for (String nameValuePair : nameValuePairs) {
            String[] nameValue = nameValuePair.split("=");
            try {
                map.put(URLDecoder.decode(nameValue[0], "UTF-8"), nameValue.length > 1 ? URLDecoder.decode(
                        nameValue[1], "UTF-8") : "");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("This method requires UTF-8 encoding support", e);
            }
        }
        return map;
    }

    /**
     * JsonElement对象转map对象
     */
    public static Map<String, String> toMap(JsonElement jsonElement){
        Type type = new TypeToken<Map<String, ?>>(){}.getType();
        return new Gson().fromJson(jsonElement, type);
    }

    public static JsonElement tojson(String form){
        return toElement(tojson(toMap(form, "&")));
    }

    public static String tojson(Map map){
        Gson gson = new GsonBuilder().create();
        return gson.toJson(map);
    }

    /**
     * json字符串转JsonElement对象
     */
    public static JsonElement toElement(String json){
        try {
            return new JsonParser().parse(json);
        }catch (JsonSyntaxException e){
            return null;
        }
    }

    /**
     * 字符串转JsonPrimitive
     */
    public static JsonPrimitive toJsonPrimitive(String string) {
        return new JsonPrimitive(string);
    }

    /**
     * newData更新到target对象中
     */
    public static JsonElement update(JsonObject target, JsonObject newData){
        for (Map.Entry<String, JsonElement> map : newData.entrySet()) {
            target.add(map.getKey(), map.getValue());
        }
        return target;
    }
}