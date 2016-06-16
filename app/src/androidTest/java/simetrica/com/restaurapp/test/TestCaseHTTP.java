package simetrica.com.restaurapp.test;

import junit.framework.TestCase;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Created by rytscc on 15/06/2016.
 */
public class TestCaseHTTP extends TestCase {

    public void testHttpGet() {
        HttpGet httpget = new HttpGet("http://opentable.herokuapp.com/api/restaurants");
        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response = null;

        try {
            response = httpclient.execute(httpget);
        } catch(Exception e) {
            fail("Excepcion");
        }

        assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode() );
    }
}


