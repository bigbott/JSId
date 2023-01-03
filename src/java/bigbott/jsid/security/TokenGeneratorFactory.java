/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bigbott.jsid.security;

import bigbott.jsid.JsidFilter;

/**
 *
 * @author Owner
 */
public class TokenGeneratorFactory {

    static private ITokenGenerator tokenGenerator;

    public static ITokenGenerator getTokenGenerator() {
        return tokenGenerator;
    }

    public static void createInstance() {
        try {
            Class tokenGeneratorClass = Class.forName(JsidFilter.getInitParameter("tokenGeneratorClass"));
            tokenGenerator = (ITokenGenerator) tokenGeneratorClass.newInstance();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
