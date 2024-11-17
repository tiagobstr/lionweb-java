package io.lionweb.lioncore.java.language;

import io.lionweb.lioncore.java.LionWebVersion;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;

public class LionCoreBuiltins extends Language {
  private static final Map<LionWebVersion, LionCoreBuiltins> INSTANCES = new HashMap<>();

  /** This is private to prevent instantiation and enforce the Singleton pattern. */
  private LionCoreBuiltins(@Nonnull LionWebVersion lionWebVersion) {
    super(lionWebVersion, "LionCore_builtins");
    setID("LionCore-builtins");
    setKey("LionCore-builtins");
    setVersion(lionWebVersion.getValue());
    PrimitiveType string = new PrimitiveType(lionWebVersion, this, "String");
    new PrimitiveType(lionWebVersion, this, "Boolean");
    new PrimitiveType(lionWebVersion, this, "Integer");
    if (lionWebVersion.equals(LionWebVersion.v2023_1)) {
      new PrimitiveType(lionWebVersion, this, "JSON");
    }

    Concept node = new Concept(lionWebVersion, this, "Node").setID("LionCore-builtins-Node");
    node.setAbstract(true);

    Interface iNamed =
        new Interface(lionWebVersion, this, "INamed").setID("LionCore-builtins-INamed");
    iNamed.addFeature(
        Property.createRequired(lionWebVersion, "name", string)
            .setID("LionCore-builtins-INamed-name")
            .setKey("LionCore-builtins-INamed-name"));

    this.getElements()
        .forEach(
            e -> {
              if (e.getID() == null) {
                e.setID("LionCore-builtins-" + e.getName());
              }
              if (e.getKey() == null) {
                e.setKey("LionCore-builtins-" + e.getName());
              }
            });
  }

  public static LionCoreBuiltins getInstance() {
    return getInstance(LionWebVersion.currentVersion);
  }

  public static PrimitiveType getString() {
    return getInstance().getPrimitiveTypeByName("String");
  }

  public static PrimitiveType getInteger() {
    return getInstance().getPrimitiveTypeByName("Integer");
  }

  public static PrimitiveType getBoolean() {
    return getInstance().getPrimitiveTypeByName("Boolean");
  }

  public static PrimitiveType getJSON() {
    return getInstance().getPrimitiveTypeByName("JSON");
  }

  public static Interface getINamed() {
    return getInstance().getInterfaceByName("INamed");
  }

  public static Concept getNode() {
    return getInstance().getConceptByName("Node");
  }

  public static LionCoreBuiltins getInstance(@Nonnull LionWebVersion lionWebVersion) {
    if (!INSTANCES.containsKey(lionWebVersion)) {
      INSTANCES.put(lionWebVersion, new LionCoreBuiltins(lionWebVersion));
    }
    return INSTANCES.get(lionWebVersion);
  }

  public static PrimitiveType getString(@Nonnull LionWebVersion lionWebVersion) {
    return getInstance(lionWebVersion).getPrimitiveTypeByName("String");
  }

  public static PrimitiveType getInteger(@Nonnull LionWebVersion lionWebVersion) {
    return getInstance(lionWebVersion).getPrimitiveTypeByName("Integer");
  }

  public static PrimitiveType getBoolean(@Nonnull LionWebVersion lionWebVersion) {
    return getInstance(lionWebVersion).getPrimitiveTypeByName("Boolean");
  }

  public static PrimitiveType getJSON(@Nonnull LionWebVersion lionWebVersion) {
    if (!lionWebVersion.equals(LionWebVersion.v2023_1)) {
      throw new IllegalArgumentException("JSON was present only in v2023.1");
    }
    return getInstance(lionWebVersion).getPrimitiveTypeByName("JSON");
  }

  public static Interface getINamed(@Nonnull LionWebVersion lionWebVersion) {
    return getInstance(lionWebVersion).getInterfaceByName("INamed");
  }

  public static Concept getNode(@Nonnull LionWebVersion lionWebVersion) {
    return getInstance(lionWebVersion).getConceptByName("Node");
  }
}
