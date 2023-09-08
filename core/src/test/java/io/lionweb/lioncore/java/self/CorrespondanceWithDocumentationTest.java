package io.lionweb.lioncore.java.self;

import static org.junit.Assert.assertEquals;

import io.lionweb.lioncore.java.language.Language;
import io.lionweb.lioncore.java.language.LionCoreBuiltins;
import io.lionweb.lioncore.java.model.Node;
import io.lionweb.lioncore.java.serialization.JsonSerialization;
import io.lionweb.lioncore.java.utils.ModelComparator;
import java.io.*;
import java.net.URL;
import java.util.List;
import org.junit.Test;

public class CorrespondanceWithDocumentationTest {

  private static final String ORGANIZATION_COMMIT_CONSIDERED =
      "a110e28bd8345a6b1445b9d7fdab5de3c258b23c";

  @Test
  public void lioncoreIsTheSameAsInTheOrganizationRepo() throws IOException {
    JsonSerialization jsonSer = JsonSerialization.getStandardSerialization();

    URL url =
        new URL(
            "https://raw.githubusercontent.com/LIonWeb-org/organization/"
                + ORGANIZATION_COMMIT_CONSIDERED
                + "/metametamodel/lioncore.json");
    List<Node> nodes = jsonSer.unserializeToNodes(url);

    Language unserializedLioncore = (Language) nodes.get(0);
    ModelComparator.ComparisonResult comparison =
        new ModelComparator().compare(unserializedLioncore, LionCore.getInstance());
    System.out.println("Differences " + comparison.getDifferences().size());
    for (String difference : comparison.getDifferences()) {
      System.out.println(" - " + difference);
    }
    assertEquals(comparison.toString(), true, comparison.areEquivalent());
  }

  @Test
  public void builtInIsTheSameAsInTheOrganizationRepo() throws IOException {
    JsonSerialization jsonSer = JsonSerialization.getStandardSerialization();

    URL url =
        new URL(
            "https://raw.githubusercontent.com/LIonWeb-org/organization/"
                + ORGANIZATION_COMMIT_CONSIDERED
                + "/metametamodel/builtins.json");
    List<Node> nodes = jsonSer.unserializeToNodes(url);

    Language unserializedBuiltins = (Language) nodes.get(0);
    ModelComparator.ComparisonResult comparison =
        new ModelComparator().compare(unserializedBuiltins, LionCoreBuiltins.getInstance());
    System.out.println("Differences " + comparison.getDifferences().size());
    for (String difference : comparison.getDifferences()) {
      System.out.println(" - " + difference);
    }
    assertEquals(comparison.toString(), true, comparison.areEquivalent());
  }
}
