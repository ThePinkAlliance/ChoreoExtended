package com.ThePinkAlliance.ChoreoExtended;

import java.lang.reflect.Type;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

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

      EventCommandData[] temp_list = new EventCommandData[commands.size()];
      for (int i = 0; i < commands.size(); i++) {
        JsonObject command = commands.get(i).getAsJsonObject();
        JsonObject command_data = command.get("data").getAsJsonObject();
        String command_type = command.get("type").getAsString();
        String command_name = command_data.get("name").getAsString();

        temp_list[i] = new EventCommandData(command_type, command_name);
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
