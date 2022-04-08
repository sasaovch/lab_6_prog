package lab_6.server.util;

import java.lang.reflect.Type;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import lab_6.common.data.SpaceMarine;
import lab_6.common.data.SpaceMarineCollection;



/**
 * Type adapter of SpaceMarineCollection for json serialization.
 */
public class SpaceMarineCollectionSerializer implements JsonSerializer<SpaceMarineCollection> {

	@Override
	public JsonElement serialize(SpaceMarineCollection src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject result = new JsonObject();
		for(SpaceMarine spMar : src.getCollection()) {
			result.add(spMar.getName(), context.serialize(spMar));
		}
		return result;
	}
}