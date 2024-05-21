package com.ThePinkAlliance.ChoreoExtended;

import java.lang.reflect.Type;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

public class Deserializer implements JsonDeserializer<EventCommandData[]> {

    @Override
    public EventCommandData[] deserialize(JsonElement json, Type type, JsonDeserializationContext context)
            throws JsonParseException {
        EventCommandData[] list;
        JsonObject object = json.getAsJsonObject();
        JsonObject event_data = object.get("data").getAsJsonObject();
        boolean using_chained_commands = event_data.asMap().containsKey("commands");
        String event_type = object.get("type").getAsString();

        if (using_chained_commands) {
            JsonArray commands = event_data.get("commands").getAsJsonArray();

            ArrayList<EventCommandData> populated_list = new ArrayList<>();
            for (var c : commands) {
                JsonObject command = c.getAsJsonObject();
                JsonObject command_data = command.get("data").getAsJsonObject();
                String command_type = command.get("type").getAsString();
                String command_name = command_data.get("name").getAsString();

                populated_list.add(new EventCommandData(command_type, command_name));
            }

            EventCommandData[] temp_list = new EventCommandData[populated_list.size()];

            for (int i = 0; i < populated_list.size(); i++) {
                EventCommandData element = populated_list.get(i);

                temp_list[i] = element;
            }

            list = temp_list;
        } else {
            String event_name = event_data.getAsJsonObject().get("name").getAsString();
            EventCommandData event = new EventCommandData(event_type, event_name);

            list = new EventCommandData[] {
                    event
            };
        }

        return list;
    }

}
