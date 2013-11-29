package net.yura.social;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import net.yura.mobile.io.JSONUtil;
import net.yura.mobile.io.json.JSONWriter;

/**
 * http://www.tomanthony.co.uk/blog/google_plus_one_button_seo_count_api/
 */
public class GooglePlusOne {

    public static final String URL = "https://clients6.google.com/rpc?key=AIzaSyCKSbrvQasunBoV16zDH9R33D88CeLr9gQ";

    private static JSONUtil util = new JSONUtil() {
	protected void saveObject(JSONWriter serializer, Object object) throws IOException {
	    if (object instanceof Map && !(object instanceof Hashtable)) {
		super.saveObject(serializer, new Hashtable((Map) object));
	    }
	    else if (object instanceof List && !(object instanceof Vector)) {
		super.saveObject(serializer, new Vector((List) object));
	    }
	    else {
		super.saveObject(serializer, object);
	    }
	}
    };
    
    /**
     * [{"method":"pos.plusones.get","id":"p","params":{"nolog":true,"id":"http://www.test.com","source":"widget","userId":"@viewer","groupId":"@self"},"jsonrpc":"2.0","key":"p","apiVersion":"v1"}]
     */
    public static byte[] getRequest(String url) {
        try {
            Map<String, Object> params = new HashMap();
            params.put("nolog",true);
            params.put("id",url);
            params.put("source","widget");
            params.put("userId","@viewer");
            params.put("groupId","@self");

            Map<String, Object> request = new HashMap();
            request.put("method","pos.plusones.get");
            request.put("id","p");
            request.put("params", params);
            request.put("jsonrpc","2.0");
            request.put("key","p");
            request.put("apiVersion","v1");

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            util.save(out, new Object[] { request } );
            return out.toByteArray();
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * [{"result": { "kind": "pos#plusones", "id": "http://www.google.com/", "isSetByViewer": false, "metadata": {"type": "URL", "globalCounts": {"count": 3097.0} } } "id": "p"}]
     */
    public static int getCount(InputStream is) throws IOException {
	Object[] object = (Object[])util.load(is);
	Map<String, Object> responce = (Map)object[0];
	Map<String, Object> result = (Map)responce.get("result");
	Map<String, Object> metadata = (Map)result.get("metadata");
	Map<String, Object> globalCounts = (Map)metadata.get("globalCounts");
	double count = (Double)globalCounts.get("count");
	return (int)count;
    }
}
