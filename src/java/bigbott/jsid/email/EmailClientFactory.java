/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bigbott.jsid.email;

import bigbott.jsid.JsidFilter;

/**
 *
 * @author Owner
 */
public class EmailClientFactory {

    static private IMailClient client;

    public static IMailClient getMailClient() {
        return client;
    }

    public static void createInstance() {
        try {        
            Class mailClientClass = Class.forName(JsidFilter.getInitParameter("mailClientClass"));
            client = (IMailClient) mailClientClass.newInstance();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
