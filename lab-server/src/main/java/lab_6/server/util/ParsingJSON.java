package lab_6.server.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

import com.google.gson.*;

import lab_6.common.data.Chapter;
import lab_6.common.data.Coordinates;
import lab_6.common.data.SpaceMarine;
import lab_6.common.data.SpaceMarineCollection;


public class ParsingJSON {
  
    public boolean serialize(SpaceMarineCollection collection, File file) throws IOException {
        Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(SpaceMarineCollection.class, new SpaceMarineCollectionSerializer())
            .registerTypeAdapter(SpaceMarine.class, new SpaceMarineSerializer())
            .create();
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(gson.toJson(collection));
        writer.flush();
        writer.close();
        return true;
    }

    public SpaceMarineCollection deSerialize(String strData) throws JsonSyntaxException {
        Gson g = new GsonBuilder()
            .registerTypeAdapter(SpaceMarineCollection.class, new SpaceMarineCollectionDeserializer())
            .registerTypeAdapter(SpaceMarine.class, new SpaceMarineDeserializer())
            .registerTypeAdapter(Coordinates.class, new CoordinatesDeserializer())
            .registerTypeAdapter(Chapter.class, new ChapterDeserializer())
            .create();;
        if ("".equals(strData)) {
            return new SpaceMarineCollection();
        }
        SpaceMarineCollection deserCollection = g.fromJson(strData, SpaceMarineCollection.class);
        if (Objects.equals(deserCollection, null)) {
            return null;
        }
        return deserCollection;
    }
}