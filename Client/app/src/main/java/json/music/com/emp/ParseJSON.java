package json.music.com.emp;

/**
 * Created by Aurobind on 06-03-2017.
 */
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Belal on 9/22/2015.
 */
public class ParseJSON {
    public static String[] ids;
    public static String[] names;
    public static String[] path;
    public static String[] mood;

    public static final String JSON_ARRAY = "result";
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_ADDRESS = "path";
    public static final String KEY_MOOD = "mood";

    private JSONArray users = null;

    private String json;

    public ParseJSON(String json){
        this.json = json;
        Log.i("TAG","hello");
    }

    protected void parseJSON(){
        JSONObject jsonObject=null;
        try {
            jsonObject = new JSONObject(json);
            users = jsonObject.getJSONArray(JSON_ARRAY);

            ids = new String[users.length()];
            names = new String[users.length()];
            path = new String[users.length()];
            mood = new String[users.length()];

            for(int i=0;i<users.length();i++){
                JSONObject jo = users.getJSONObject(i);
                ids[i] = jo.getString(KEY_ID);
                names[i] = jo.getString(KEY_NAME);
                path[i] = jo.getString(KEY_ADDRESS);
                mood[i] = jo.getString(KEY_MOOD);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}