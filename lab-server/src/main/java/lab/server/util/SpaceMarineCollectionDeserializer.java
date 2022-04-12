package lab.server.util;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import lab.common.data.SpaceMarine;
import lab.common.exception.IncorrectData;


public class SpaceMarineCollectionDeserializer implements JsonDeserializer<SpaceMarineCollection> {

	@Override
	public SpaceMarineCollection deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		SpaceMarineCollection result = new SpaceMarineCollection(new HashSet<>());
		JsonObject jsonObject = json.getAsJsonObject();
		List<Long> listID = new ArrayList<>();
		for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
			SpaceMarine spaceMarine = context.deserialize(entry.getValue(), SpaceMarine.class);
			if (Objects.equals(spaceMarine, null)) {
				return null;
			} else if (listID.contains(spaceMarine.getID())) {
				return null;
			}
			try {
				spaceMarine.setName(entry.getKey());
			} catch (IncorrectData e) {
				return null;
			}
			result.addElement(spaceMarine);
			listID.add(spaceMarine.getID());
		}
		return result;
	}
}
