package fr.lernejo.navy_battle.Utils;

import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Validation {
    public boolean validateSch(String req, String sch){
        JSONTokener tokener = new JSONTokener(sch);
        JSONObject object = new JSONObject(tokener);
        JSONTokener jsonTokener = new JSONTokener(req);
        JSONObject object1 = new JSONObject(jsonTokener);
        Schema schema = SchemaLoader.load(object);
        try {
            schema.validate(object1);
            return true;
        } catch (ValidationException e) {

            return false;
        }
    }
}
