package io.lionweb.lioncore.java.emf.support;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

import org.eclipse.emf.ecore.*;
import org.eclipse.emf.ecore.impl.DynamicEObjectImpl;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * We have issues using the JSON resource factory part of EMF-Cloud, so we provide our own
 * implementation of the logic to load Resources stored as JSON.
 */
public class EMFJsonLoader {
  public List<EObject> load(InputStream inputStream, Resource resource) {
    JsonElement json = JsonParser.parseReader(new InputStreamReader(inputStream));
    if (json.isJsonObject()) {
      List<EObject> nodes =
          Collections.singletonList(readEObject(json.getAsJsonObject(), resource, null));
      resource.getContents().addAll(nodes);
      return nodes;
    } else if (json.isJsonArray()) {
      List<EObject> nodes = new ArrayList<>();
      json.getAsJsonArray()
          .forEach(
              jsonElement -> nodes.add(readEObject(jsonElement.getAsJsonObject(), resource, null)));
      resource.getContents().addAll(nodes);
      return nodes;
    } else {
      throw new UnsupportedOperationException();
    }
  }

  private EObject resolveReference(String ref, Resource resource) {
    String[] parts = ref.split("#//");
    if (parts.length == 2) {
      EPackage ePackage = resource.getResourceSet().getPackageRegistry().getEPackage(parts[0]);
      if (ePackage == null) {
        throw new IllegalStateException("Unable to resolve package " + parts[0]);
      } else {
        if (parts[1].contains("/")) {
          throw new UnsupportedOperationException();
        } else {
          Optional<EClassifier> resolved = ePackage.getEClassifiers().stream().filter(e -> e.getName().equals(parts[1])).findFirst();
          if (resolved.isPresent()) {
            return resolved.get();
          } else {
            throw new IllegalStateException("Cannot find " + parts[1] + " in package " + ePackage);
          }
        }
      }

    } else {
      throw new UnsupportedOperationException();
    }
  }

  private EObject readEObject(JsonObject jsonObject, Resource resource, EClass expectedEClass) {
    Objects.requireNonNull(jsonObject);
    String eClassURI = null;
    if (jsonObject.has("eClass")) {
      eClassURI = jsonObject.get("eClass").getAsString();
    }
    EClass eClass;
    if (eClassURI == null) {
      if (expectedEClass == null) {
        throw new IllegalArgumentException();
      }
      eClass = expectedEClass;
    } else {
      String[] parts = eClassURI.split("#//");
      String packageURI = parts[0];
      String eClassName = parts[1];
      EPackage ePackage = resource.getResourceSet().getPackageRegistry().getEPackage(packageURI);
      if (ePackage == null) {
        throw new UnsupportedOperationException();
      }
      EClassifier eClassifier = ePackage.getEClassifier(eClassName);
      if (eClassifier == null) {
        throw new UnsupportedOperationException();
      }
      if (!(eClassifier instanceof EClass)) {
        throw new IllegalStateException();
      }
      eClass = (EClass) eClassifier;
    }
    EObject eObject = eClass.getEPackage().getEFactoryInstance().create(eClass);
    eClass
        .getEAllStructuralFeatures()
        .forEach(
            eStructuralFeature -> {
              JsonElement jsonFeatureValue = jsonObject.get(eStructuralFeature.getName());
              if (jsonFeatureValue != null) {
                if (eStructuralFeature instanceof EAttribute) {
                  EAttribute eAttribute = (EAttribute) eStructuralFeature;
                  if (eAttribute.getEAttributeType().equals(EcorePackage.eINSTANCE.getEInt())) {
                    if (eStructuralFeature.isMany()) {
                      throw new UnsupportedOperationException();
                    } else {
                      eObject.eSet(
                          eStructuralFeature, Integer.parseInt(jsonFeatureValue.getAsString()));
                    }
                  } else if (eAttribute
                      .getEAttributeType()
                      .equals(EcorePackage.eINSTANCE.getEString())) {
                    if (eStructuralFeature.isMany()) {
                      throw new UnsupportedOperationException();
                    } else {
                      eObject.eSet(eStructuralFeature, jsonFeatureValue.getAsString());
                    }
                  } else {
                    throw new UnsupportedOperationException();
                  }
                } else if (eStructuralFeature instanceof EReference) {
                  EReference eReference = (EReference) eStructuralFeature;
                  if (eReference.isContainment()) {
                    if (eStructuralFeature.isMany()) {
                      List<EObject> children = new ArrayList<>();
                      jsonFeatureValue
                          .getAsJsonArray()
                          .forEach(
                              e ->
                                  children.add(
                                      readEObject(
                                          e.getAsJsonObject(),
                                          resource,
                                          ((EReference) eStructuralFeature).getEReferenceType())));
                      eObject.eSet(eStructuralFeature, children);
                    } else {
                      EObject child =
                          readEObject(
                              jsonFeatureValue.getAsJsonObject(),
                              resource,
                              ((EReference) eStructuralFeature).getEReferenceType());
                      eObject.eSet(eStructuralFeature, child);
                    }
                  } else {
                    if (eStructuralFeature.isMany()) {
                      throw new UnsupportedOperationException();
                    } else {
                      if (jsonFeatureValue.isJsonObject()) {
                        String ref = jsonFeatureValue.getAsJsonObject().get("$ref").getAsString();
                        EObject referred = resolveReference(ref, resource);
                        eObject.eSet(eStructuralFeature, referred);
                      } else {
                        throw new UnsupportedOperationException(
                                "Non-containment EReferences are not yet supported");
                      }
                    }
                  }
                } else {
                  throw new IllegalStateException();
                }
              }
            });
    return eObject;
  }
}
