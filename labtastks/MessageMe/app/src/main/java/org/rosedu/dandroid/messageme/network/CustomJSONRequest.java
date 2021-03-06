package org.rosedu.dandroid.messageme.network;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class CustomJSONRequest<T> extends Request<T> {

    private Map<String, String> params;
    private Response.Listener<T> responseListener;
    private Response.ErrorListener errorListener;
    private Class<T> classType;

    public CustomJSONRequest(String url, Map<String, String> params, Response.Listener<T> responseListener, Response.ErrorListener errorListener, Class<T> classType) {
        this(Method.GET, url, params, responseListener, errorListener, classType);
    }

    public CustomJSONRequest(int method, String url, Map<String, String> params, Response.Listener<T> responseListener, Response.ErrorListener errorListener, Class<T> classType) {
        super(method, url, errorListener);
        this.params = params;
        this.responseListener = responseListener;
        this.errorListener = errorListener;
        this.classType = classType;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse networkResponse) {
        try {
            String response = new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers));
            if (classType.equals(JSONObject.class)) {
                return Response.success((T)new JSONObject(response), HttpHeaderParser.parseCacheHeaders(networkResponse));
            }
            if (classType.equals(JSONArray.class)) {
                return Response.success((T)new JSONArray(response), HttpHeaderParser.parseCacheHeaders(networkResponse));
            }
            return null;
        } catch (UnsupportedEncodingException unsupportedEncodingException) {
            return Response.error(new ParseError(unsupportedEncodingException));
        } catch (JSONException jsonException) {
            return Response.error(new ParseError(jsonException));
        }
    }

    @Override
    protected void deliverResponse(T jsonObject) {
        responseListener.onResponse(jsonObject);
    }

    @Override
    public void deliverError(VolleyError volleyError) {
        errorListener.onErrorResponse(volleyError);
    }
}
