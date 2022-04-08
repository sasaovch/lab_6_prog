package lab.server.util;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import lab.common.data.SpaceMarine;


public class SpaceMarineSerializer implements JsonSerializer<SpaceMarine> {

	@Override
	public JsonElement serialize(SpaceMarine src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject result = new JsonObject();
        result.addProperty("id", src.getID());
		result.add("coordinates", context.serialize(src.getCoordinates()));
		result.add("creationDateTime", context.serialize(src.getCreationDateTime()));
        result.addProperty("health", src.getHealth());
        result.addProperty("heartCount", src.getHeartCount());
        result.addProperty("loyal", src.getLoyal());
        result.add("category", context.serialize(src.getCategory()));
        result.add("chapter", context.serialize(src.getChapter()));
		return result;
	}
}
