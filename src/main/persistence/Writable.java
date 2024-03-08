package persistence;

import org.json.JSONObject;

// adapted from edx
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
