package io.lionweb.lioncore.java.serialization;

import io.lionweb.lioncore.java.language.LionCoreBuiltins;
import io.lionweb.lioncore.java.self.LionCore;
import io.lionweb.lioncore.java.serialization.extras.ExtraFlatBuffersSerialization;
import io.lionweb.lioncore.java.serialization.extras.ExtraProtoBufSerialization;

public class SerializationProvider {

  /** This has specific support for LionCore or LionCoreBuiltins. */
  public static JsonSerialization getStandardJsonSerialization() {
    JsonSerialization serialization = new JsonSerialization();
    standardInitialization(serialization);
    return serialization;
  }

  /** This has no specific support for LionCore or LionCoreBuiltins. */
  public static JsonSerialization getBasicJsonSerialization() {
    return new JsonSerialization();
  }

  /** This has specific support for LionCore or LionCoreBuiltins. */
  public static ProtoBufSerialization getStandardProtoBufSerialization() {
    ProtoBufSerialization serialization = new ProtoBufSerialization();
    standardInitialization(serialization);
    return serialization;
  }

  /** This has no specific support for LionCore or LionCoreBuiltins. */
  public static ProtoBufSerialization getBasicProtoBufSerialization() {
    return new ProtoBufSerialization();
  }

  /** This has specific support for LionCore or LionCoreBuiltins. */
  public static FlatBuffersSerialization getStandardFlatBuffersSerialization() {
    FlatBuffersSerialization serialization = new FlatBuffersSerialization();
    standardInitialization(serialization);
    return serialization;
  }

  public static ExtraFlatBuffersSerialization getExtraStandardFlatBuffersSerialization() {
    ExtraFlatBuffersSerialization serialization = new ExtraFlatBuffersSerialization();
    standardInitialization(serialization);
    return serialization;
  }

  public static ExtraProtoBufSerialization getExtraStandardProtoBufSerialization() {
    ExtraProtoBufSerialization serialization = new ExtraProtoBufSerialization();
    standardInitialization(serialization);
    return serialization;
  }

  /** This has no specific support for LionCore or LionCoreBuiltins. */
  public static FlatBuffersSerialization getBasicFlatBuffersSerialization() {
    return new FlatBuffersSerialization();
  }

  private static void standardInitialization(AbstractSerialization serialization) {
    serialization.classifierResolver.registerLanguage(LionCore.getInstance());
    serialization.instantiator.registerLionCoreCustomDeserializers();
    serialization.primitiveValuesSerialization
        .registerLionBuiltinsPrimitiveSerializersAndDeserializers();
    serialization.instanceResolver.addAll(LionCore.getInstance().thisAndAllDescendants());
    serialization.instanceResolver.addAll(LionCoreBuiltins.getInstance().thisAndAllDescendants());
  }
}
