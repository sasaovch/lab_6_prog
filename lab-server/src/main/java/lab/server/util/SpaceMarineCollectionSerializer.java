package lab.server.util;

import java.lang.reflect.Type;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import lab.common.data.SpaceMarine;
import lab.common.data.SpaceMarineCollection;



public class SpaceMarineCollectionSerializer implements JsonSerializer<SpaceMarineCollection> {

	@Override
	public JsonElement serialize(SpaceMarineCollection src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject result = new JsonObject();
		for (SpaceMarine spMar : src.getCollection()) {
			result.add(spMar.getName(), context.serialize(spMar));
		}
		return result;
	}
}
