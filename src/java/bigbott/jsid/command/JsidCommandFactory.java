/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bigbott.jsid.command;

import bigbott.jsid.user.IUserManager;
import bigbott.jsid.JsidFilter;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Owner
 */
public class JsidCommandFactory {
    
    private static final String COMMAND_PACKAGE = "bigbott.jsid.command.";
    
    static Map<String, JsidCommand> commandMap = new HashMap<>();
    
    public static JsidCommand getCommand (String commandName){
        if (commandMap.containsKey(commandName)){
            return commandMap.get(commandName);
        }
        String commandNameCapitalized = commandName.substring(0, 1).toUpperCase() + commandName.substring(1);
        String commandClassString = COMMAND_PACKAGE + commandNameCapitalized;
        try {
            Class commandClass = Class.forName(commandClassString);
            JsidCommand command = (JsidCommand) commandClass.newInstance();
            commandMap.put(commandName, command);
            return command;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
}
