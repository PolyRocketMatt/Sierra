package com.github.polyrocketmatt.sierra.engine.utils.manager;

import com.github.polyrocketmatt.sierra.engine.utils.io.YamlDocumentManager;
import dev.dejvokep.boostedyaml.YamlDocument;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ConfigurationManager {

    private static final ConfigurationManager instance = new ConfigurationManager();
    private final Map<UUID, YamlDocument> documents;

    private ConfigurationManager() {
        this.documents = new HashMap<>();
    }

    private UUID addDocument(YamlDocument document) {
        UUID uuid = UUID.randomUUID();
        documents.put(uuid, document);

        return uuid;
    }

    private UUID addDocument(File folder, String name) {
        UUID uuid = UUID.randomUUID();
        documents.put(uuid, YamlDocumentManager.get(folder, name));

        return uuid;
    }

    private YamlDocument getDocument(UUID uuid) {
        return documents.get(uuid);
    }

    private void saveDocument(UUID uuid) {
        YamlDocumentManager.save(documents.get(uuid));
    }

    private void save(YamlDocument document) {
        YamlDocumentManager.save(document);
    }

    private void saveAllDocuments() {
        documents.forEach((uuid, document) -> save(document));
    }

    public static UUID add(YamlDocument document) {
        return instance.addDocument(document);
    }

    public static UUID add(File folder, String name) {
        return instance.addDocument(folder, name);
    }

    public static YamlDocument get(UUID uuid) {
        return instance.getDocument(uuid);
    }

    public static void save(UUID uuid) {
        instance.saveDocument(uuid);
    }

    public static void saveAll() {
        instance.saveAllDocuments();
    }

}
