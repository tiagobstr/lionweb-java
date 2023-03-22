package org.lionweb.lioncore.java.serialization;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.Test;
import org.lionweb.lioncore.java.metamodel.Concept;
import org.lionweb.lioncore.java.metamodel.Property;
import org.lionweb.lioncore.java.model.Node;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.lionweb.lioncore.java.serialization.SerializedJsonComparisonUtils.assertEquivalentLionWebJson;

public class SerializationOfPrimitiveValuesTest extends SerializationTest {

    @Test
    public void serializeBoolean() {
        MyNodeWithProperties node = new MyNodeWithProperties("n1");
        node.setP1(true);

        JsonObject expected = JsonParser.parseString("{\n" +
                "  \"serializationFormatVersion\": \"1\",\n" +
                "  \"metamodels\": [],\n" +
                "  \"nodes\": [\n" +
                "    {\n" +
                "      \"id\": \"n1\",\n" +
                "      \"concept\": {\n" +
                "        \"metamodel\": \"mymetamodel\",\n" +
                "        \"version\": \"1\",\n" +
                "        \"key\": \"concept-MyNodeWithProperties\"\n" +
                "      },\n" +
                "      \"properties\": [\n" +
                "        {\n" +
                "          \"property\": {\n" +
                "            \"metamodel\": \"mymetamodel\",\n" +
                "            \"version\": \"1\",\n" +
                "            \"key\": \"p1\"\n" +
                "          },\n" +
                "          \"value\": \"true\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"property\": {\n" +
                "            \"metamodel\": \"mymetamodel\",\n" +
                "            \"version\": \"1\",\n" +
                "            \"key\": \"p2\"\n" +
                "          },\n" +
                "          \"value\": null\n" +
                "        },\n" +
                "        {\n" +
                "          \"property\": {\n" +
                "            \"metamodel\": \"mymetamodel\",\n" +
                "            \"version\": \"1\",\n" +
                "            \"key\": \"p3\"\n" +
                "          },\n" +
                "          \"value\": null\n" +
                "        },\n" +
                "        {\n" +
                "          \"property\": {\n" +
                "            \"metamodel\": \"mymetamodel\",\n" +
                "            \"version\": \"1\",\n" +
                "            \"key\": \"p4\"\n" +
                "          },\n" +
                "          \"value\": null\n" +
                "        }\n" +
                "      ],\n" +
                "      \"children\": [],\n" +
                "      \"references\": [],\n" +
                "      \"parent\": null\n" +
                "    }\n" +
                "  ]\n" +
                "}").getAsJsonObject();
        JsonSerialization jsonSerialization = JsonSerialization.getStandardSerialization();
        JsonObject serialized = jsonSerialization.serializeNodesToJson(node).getAsJsonObject();
        assertEquivalentLionWebJson(expected, serialized);
    }

    @Test
    public void unserializeBoolean() {
        MyNodeWithProperties node = new MyNodeWithProperties("n1");
        node.setP1(true);

        JsonObject serialized = JsonParser.parseString("{\n" +
                "  \"serializationFormatVersion\": \"1\",\n" +
                "  \"metamodels\": [],\n" +
                "  \"nodes\": [\n" +
                "    {\n" +
                "      \"id\": \"n1\",\n" +
                "      \"concept\": {\n" +
                "        \"metamodel\": \"mymetamodel\",\n" +
                "        \"version\": \"1\",\n" +
                "        \"key\": \"concept-MyNodeWithProperties\"\n" +
                "      },\n" +
                "      \"properties\": [\n" +
                "        {\n" +
                "          \"property\": {\n" +
                "            \"metamodel\": \"mymetamodel\",\n" +
                "            \"version\": \"1\",\n" +
                "            \"key\": \"p1\"\n" +
                "          },\n" +
                "          \"value\": \"true\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"property\": {\n" +
                "            \"metamodel\": \"mymetamodel\",\n" +
                "            \"version\": \"1\",\n" +
                "            \"key\": \"p2\"\n" +
                "          },\n" +
                "          \"value\": null\n" +
                "        },\n" +
                "        {\n" +
                "          \"property\": {\n" +
                "            \"metamodel\": \"mymetamodel\",\n" +
                "            \"version\": \"1\",\n" +
                "            \"key\": \"p3\"\n" +
                "          },\n" +
                "          \"value\": null\n" +
                "        },\n" +
                "        {\n" +
                "          \"property\": {\n" +
                "            \"metamodel\": \"mymetamodel\",\n" +
                "            \"version\": \"1\",\n" +
                "            \"key\": \"p4\"\n" +
                "          },\n" +
                "          \"value\": null\n" +
                "        }\n" +
                "      ],\n" +
                "      \"children\": [],\n" +
                "      \"references\": [],\n" +
                "      \"parent\": null\n" +
                "    }\n" +
                "  ]\n" +
                "}").getAsJsonObject();
        JsonSerialization jsonSerialization = JsonSerialization.getStandardSerialization();
        jsonSerialization.getConceptResolver().registerMetamodel(MyNodeWithProperties.METAMODEL);
        jsonSerialization.getNodeInstantiator().registerCustomUnserializer(MyNodeWithProperties.CONCEPT.getID(), (concept, serializedNode) -> new MyNodeWithProperties(serializedNode.getID()));
        List<Node> unserialized = jsonSerialization.unserializeToNode(serialized);
        assertEquals(Arrays.asList(node), unserialized);
    }

    @Test
    public void serializeString() {
        MyNodeWithProperties node = new MyNodeWithProperties("n1");
        node.setP3("qwerty");

        JsonObject expected = JsonParser.parseString("{\n" +
                "  \"serializationFormatVersion\": \"1\",\n" +
                "  \"metamodels\": [],\n" +
                "  \"nodes\": [\n" +
                "    {\n" +
                "      \"id\": \"n1\",\n" +
                "      \"concept\": {\n" +
                "        \"metamodel\": \"mymetamodel\",\n" +
                "        \"version\": \"1\",\n" +
                "        \"key\": \"concept-MyNodeWithProperties\"\n" +
                "      },\n" +
                "      \"properties\": [\n" +
                "        {\n" +
                "          \"property\": {\n" +
                "            \"metamodel\": \"mymetamodel\",\n" +
                "            \"version\": \"1\",\n" +
                "            \"key\": \"p1\"\n" +
                "          },\n" +
                "          \"value\": null\n" +
                "        },\n" +
                "        {\n" +
                "          \"property\": {\n" +
                "            \"metamodel\": \"mymetamodel\",\n" +
                "            \"version\": \"1\",\n" +
                "            \"key\": \"p2\"\n" +
                "          },\n" +
                "          \"value\": null\n" +
                "        },\n" +
                "        {\n" +
                "          \"property\": {\n" +
                "            \"metamodel\": \"mymetamodel\",\n" +
                "            \"version\": \"1\",\n" +
                "            \"key\": \"p3\"\n" +
                "          },\n" +
                "          \"value\": \"qwerty\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"property\": {\n" +
                "            \"metamodel\": \"mymetamodel\",\n" +
                "            \"version\": \"1\",\n" +
                "            \"key\": \"p4\"\n" +
                "          },\n" +
                "          \"value\": null\n" +
                "        }\n" +
                "      ],\n" +
                "      \"children\": [],\n" +
                "      \"references\": [],\n" +
                "      \"parent\": null\n" +
                "    }\n" +
                "  ]\n" +
                "}").getAsJsonObject();
        JsonSerialization jsonSerialization = JsonSerialization.getStandardSerialization();
        JsonObject serialized = jsonSerialization.serializeNodesToJson(node).getAsJsonObject();
        assertEquivalentLionWebJson(expected, serialized);
    }

    @Test
    public void unserializeString() {
        MyNodeWithProperties node = new MyNodeWithProperties("n1");
        node.setP3("qwerty");

        JsonObject serialized = JsonParser.parseString("{\n" +
                "  \"serializationFormatVersion\": \"1\",\n" +
                "  \"metamodels\": [],\n" +
                "  \"nodes\": [\n" +
                "    {\n" +
                "      \"id\": \"n1\",\n" +
                "      \"concept\": {\n" +
                "        \"metamodel\": \"mymetamodel\",\n" +
                "        \"version\": \"1\",\n" +
                "        \"key\": \"concept-MyNodeWithProperties\"\n" +
                "      },\n" +
                "      \"properties\": [\n" +
                "        {\n" +
                "          \"property\": {\n" +
                "            \"metamodel\": \"mymetamodel\",\n" +
                "            \"version\": \"1\",\n" +
                "            \"key\": \"p1\"\n" +
                "          },\n" +
                "          \"value\": null\n" +
                "        },\n" +
                "        {\n" +
                "          \"property\": {\n" +
                "            \"metamodel\": \"mymetamodel\",\n" +
                "            \"version\": \"1\",\n" +
                "            \"key\": \"p2\"\n" +
                "          },\n" +
                "          \"value\": null\n" +
                "        },\n" +
                "        {\n" +
                "          \"property\": {\n" +
                "            \"metamodel\": \"mymetamodel\",\n" +
                "            \"version\": \"1\",\n" +
                "            \"key\": \"p3\"\n" +
                "          },\n" +
                "          \"value\": \"qwerty\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"property\": {\n" +
                "            \"metamodel\": \"mymetamodel\",\n" +
                "            \"version\": \"1\",\n" +
                "            \"key\": \"p4\"\n" +
                "          },\n" +
                "          \"value\": null\n" +
                "        }\n" +
                "      ],\n" +
                "      \"children\": [],\n" +
                "      \"references\": [],\n" +
                "      \"parent\": null\n" +
                "    }\n" +
                "  ]\n" +
                "}").getAsJsonObject();
        JsonSerialization jsonSerialization = JsonSerialization.getStandardSerialization();
        jsonSerialization.getConceptResolver().registerMetamodel(MyNodeWithProperties.METAMODEL);
        jsonSerialization.getNodeInstantiator().registerCustomUnserializer(MyNodeWithProperties.CONCEPT.getID(), (concept, serializedNode) -> new MyNodeWithProperties(serializedNode.getID()));
        List<Node> unserialized = jsonSerialization.unserializeToNode(serialized);
        assertEquals(Arrays.asList(node), unserialized);
    }

    @Test
    public void serializeInteger() {
        MyNodeWithProperties node = new MyNodeWithProperties("n1");
        node.setP2(2904);

        JsonObject expected = JsonParser.parseString("{\n" +
                "  \"serializationFormatVersion\": \"1\",\n" +
                "  \"metamodels\": [],\n" +
                "  \"nodes\": [\n" +
                "    {\n" +
                "      \"id\": \"n1\",\n" +
                "      \"concept\": {\n" +
                "        \"metamodel\": \"mymetamodel\",\n" +
                "        \"version\": \"1\",\n" +
                "        \"key\": \"concept-MyNodeWithProperties\"\n" +
                "      },\n" +
                "      \"properties\": [\n" +
                "        {\n" +
                "          \"property\": {\n" +
                "            \"metamodel\": \"mymetamodel\",\n" +
                "            \"version\": \"1\",\n" +
                "            \"key\": \"p1\"\n" +
                "          },\n" +
                "          \"value\": null\n" +
                "        },\n" +
                "        {\n" +
                "          \"property\": {\n" +
                "            \"metamodel\": \"mymetamodel\",\n" +
                "            \"version\": \"1\",\n" +
                "            \"key\": \"p2\"\n" +
                "          },\n" +
                "          \"value\": \"2904\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"property\": {\n" +
                "            \"metamodel\": \"mymetamodel\",\n" +
                "            \"version\": \"1\",\n" +
                "            \"key\": \"p3\"\n" +
                "          },\n" +
                "          \"value\": null\n" +
                "        },\n" +
                "        {\n" +
                "          \"property\": {\n" +
                "            \"metamodel\": \"mymetamodel\",\n" +
                "            \"version\": \"1\",\n" +
                "            \"key\": \"p4\"\n" +
                "          },\n" +
                "          \"value\": null\n" +
                "        }\n" +
                "      ],\n" +
                "      \"children\": [],\n" +
                "      \"references\": [],\n" +
                "      \"parent\": null\n" +
                "    }\n" +
                "  ]\n" +
                "}").getAsJsonObject();
        JsonSerialization jsonSerialization = JsonSerialization.getStandardSerialization();
        JsonObject serialized = jsonSerialization.serializeNodesToJson(node).getAsJsonObject();
        assertEquivalentLionWebJson(expected, serialized);
    }

    @Test
    public void unserializeInteger() {
        MyNodeWithProperties node = new MyNodeWithProperties("n1");
        node.setP2(2904);

        JsonObject serialized = JsonParser.parseString("{\n" +
                "  \"serializationFormatVersion\": \"1\",\n" +
                "  \"metamodels\": [],\n" +
                "  \"nodes\": [\n" +
                "    {\n" +
                "      \"id\": \"n1\",\n" +
                "      \"concept\": {\n" +
                "        \"metamodel\": \"mymetamodel\",\n" +
                "        \"version\": \"1\",\n" +
                "        \"key\": \"concept-MyNodeWithProperties\"\n" +
                "      },\n" +
                "      \"properties\": [\n" +
                "        {\n" +
                "          \"property\": {\n" +
                "            \"metamodel\": \"mymetamodel\",\n" +
                "            \"version\": \"1\",\n" +
                "            \"key\": \"p1\"\n" +
                "          },\n" +
                "          \"value\": null\n" +
                "        },\n" +
                "        {\n" +
                "          \"property\": {\n" +
                "            \"metamodel\": \"mymetamodel\",\n" +
                "            \"version\": \"1\",\n" +
                "            \"key\": \"p2\"\n" +
                "          },\n" +
                "          \"value\": \"2904\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"property\": {\n" +
                "            \"metamodel\": \"mymetamodel\",\n" +
                "            \"version\": \"1\",\n" +
                "            \"key\": \"p3\"\n" +
                "          },\n" +
                "          \"value\": null\n" +
                "        },\n" +
                "        {\n" +
                "          \"property\": {\n" +
                "            \"metamodel\": \"mymetamodel\",\n" +
                "            \"version\": \"1\",\n" +
                "            \"key\": \"p4\"\n" +
                "          },\n" +
                "          \"value\": null\n" +
                "        }\n" +
                "      ],\n" +
                "      \"children\": [],\n" +
                "      \"references\": [],\n" +
                "      \"parent\": null\n" +
                "    }\n" +
                "  ]\n" +
                "}").getAsJsonObject();
        JsonSerialization jsonSerialization = JsonSerialization.getStandardSerialization();
        jsonSerialization.getConceptResolver().registerMetamodel(MyNodeWithProperties.METAMODEL);
        jsonSerialization.getNodeInstantiator().registerCustomUnserializer(MyNodeWithProperties.CONCEPT.getID(), (concept, serializedNode) -> new MyNodeWithProperties(serializedNode.getID()));
        List<Node> unserialized = jsonSerialization.unserializeToNode(serialized);
        assertEquals(Arrays.asList(node), unserialized);
    }

    @Test
    public void serializeJSON() {
        MyNodeWithProperties node = new MyNodeWithProperties("n1");
        JsonArray ja = new JsonArray();
        ja.add(1);
        ja.add("foo");
        node.setP4(ja);

        JsonObject expected = JsonParser.parseString("{\n" +
                "  \"serializationFormatVersion\": \"1\",\n" +
                "  \"metamodels\": [],\n" +
                "  \"nodes\": [\n" +
                "    {\n" +
                "      \"id\": \"n1\",\n" +
                "      \"concept\": {\n" +
                "        \"metamodel\": \"mymetamodel\",\n" +
                "        \"version\": \"1\",\n" +
                "        \"key\": \"concept-MyNodeWithProperties\"\n" +
                "      },\n" +
                "      \"properties\": [\n" +
                "        {\n" +
                "          \"property\": {\n" +
                "            \"metamodel\": \"mymetamodel\",\n" +
                "            \"version\": \"1\",\n" +
                "            \"key\": \"p1\"\n" +
                "          },\n" +
                "          \"value\": null\n" +
                "        },\n" +
                "        {\n" +
                "          \"property\": {\n" +
                "            \"metamodel\": \"mymetamodel\",\n" +
                "            \"version\": \"1\",\n" +
                "            \"key\": \"p2\"\n" +
                "          },\n" +
                "          \"value\": null\n" +
                "        },\n" +
                "        {\n" +
                "          \"property\": {\n" +
                "            \"metamodel\": \"mymetamodel\",\n" +
                "            \"version\": \"1\",\n" +
                "            \"key\": \"p3\"\n" +
                "          },\n" +
                "          \"value\": null\n" +
                "        },\n" +
                "        {\n" +
                "          \"property\": {\n" +
                "            \"metamodel\": \"mymetamodel\",\n" +
                "            \"version\": \"1\",\n" +
                "            \"key\": \"p4\"\n" +
                "          },\n" +
                "          \"value\": \"[1,\\\"foo\\\"]\"\n" +
                "        }\n" +
                "      ],\n" +
                "      \"children\": [],\n" +
                "      \"references\": [],\n" +
                "      \"parent\": null\n" +
                "    }\n" +
                "  ]\n" +
                "}").getAsJsonObject();
        JsonSerialization jsonSerialization = JsonSerialization.getStandardSerialization();
        JsonObject serialized = jsonSerialization.serializeNodesToJson(node).getAsJsonObject();
        assertEquivalentLionWebJson(expected, serialized);
    }

    @Test
    public void unserializeJSON() {
        MyNodeWithProperties node = new MyNodeWithProperties("n1");
        JsonArray ja = new JsonArray();
        ja.add(1);
        ja.add("foo");
        node.setP4(ja);

        JsonObject serialized = JsonParser.parseString("{\n" +
                "  \"serializationFormatVersion\": \"1\",\n" +
                "  \"metamodels\": [],\n" +
                "  \"nodes\": [\n" +
                "    {\n" +
                "      \"id\": \"n1\",\n" +
                "      \"concept\": {\n" +
                "        \"metamodel\": \"mymetamodel\",\n" +
                "        \"version\": \"1\",\n" +
                "        \"key\": \"concept-MyNodeWithProperties\"\n" +
                "      },\n" +
                "      \"properties\": [\n" +
                "        {\n" +
                "          \"property\": {\n" +
                "            \"metamodel\": \"mymetamodel\",\n" +
                "            \"version\": \"1\",\n" +
                "            \"key\": \"p1\"\n" +
                "          },\n" +
                "          \"value\": null\n" +
                "        },\n" +
                "        {\n" +
                "          \"property\": {\n" +
                "            \"metamodel\": \"mymetamodel\",\n" +
                "            \"version\": \"1\",\n" +
                "            \"key\": \"p2\"\n" +
                "          },\n" +
                "          \"value\": null\n" +
                "        },\n" +
                "        {\n" +
                "          \"property\": {\n" +
                "            \"metamodel\": \"mymetamodel\",\n" +
                "            \"version\": \"1\",\n" +
                "            \"key\": \"p3\"\n" +
                "          },\n" +
                "          \"value\": null\n" +
                "        },\n" +
                "        {\n" +
                "          \"property\": {\n" +
                "            \"metamodel\": \"mymetamodel\",\n" +
                "            \"version\": \"1\",\n" +
                "            \"key\": \"p4\"\n" +
                "          },\n" +
                "          \"value\": \"[1,\\\"foo\\\"]\"\n" +
                "        }\n" +
                "      ],\n" +
                "      \"children\": [],\n" +
                "      \"references\": [],\n" +
                "      \"parent\": null\n" +
                "    }\n" +
                "  ]\n" +
                "}").getAsJsonObject();
        JsonSerialization jsonSerialization = JsonSerialization.getStandardSerialization();
        jsonSerialization.getConceptResolver().registerMetamodel(MyNodeWithProperties.METAMODEL);
        jsonSerialization.getNodeInstantiator().registerCustomUnserializer(MyNodeWithProperties.CONCEPT.getID(), (concept, serializedNode) -> new MyNodeWithProperties(serializedNode.getID()));
        List<Node> unserialized = jsonSerialization.unserializeToNode(serialized);
        assertEquals(Arrays.asList(node), unserialized);
    }

}
