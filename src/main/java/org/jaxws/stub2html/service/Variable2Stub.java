package org.jaxws.stub2html.service;

import static org.jaxws.stub2html.service.JavaLanguageVariableFactory.createVariableFromField;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

import org.jaxws.stub2html.model.JavaLanguageVariable;
import org.jaxws.stub2html.model.Stub;
import org.jaxws.stub2html.model.StubTypeTree;

/**
 * Convert java language variables to stubs
 * 
 * @author chenjianjx
 * 
 */
public class Variable2Stub {

	public static Stub convertToStub(JavaLanguageVariable variable, Stub parentStub,StubTypeTreeRepository typeTreeRepository) {

		Stub stub = new Stub();
		stub.setParentStubRelation(parentStub);
		stub.setStubName(variable.getVariableName());
		stub.setRequired(variable.isRequired());
		stub.setHeader(variable.isHeader());
		stub.setMultiOccurs(variable.isMultiOccurs());
		stub.setType(variable.getType());

		if (variable.getType().isAnnotationPresent(XmlType.class) && !variable.getType().isEnum() && !stub.isSameTypeWithSomeAncestor()) {
			convertFieldsToChildStubs(stub, variable.getType(), typeTreeRepository);
		}
		
		stub.setCardinality(processCardinality(variable.isRequired(), variable.isMultiOccurs()));
		return stub;
	}	

	private static String processCardinality(boolean isRequired, boolean isMultiOccurs)
	{
		return (isRequired? "1..":"0..") + (isMultiOccurs? "n":"1"); 		
	}
	
	private static void convertFieldsToChildStubs(Stub parentStub, Class<?> stub, StubTypeTreeRepository typeTreeRepository) {
		
		List<Field> fields = getFieldsIncludingAncestorTypes(stub);
		
		for (Field childField : fields) {
			@SuppressWarnings("unused")
			Stub child = convertToStub(createVariableFromField(childField), parentStub, typeTreeRepository);
		}

		LinkedList<FieldsOfSubType> fieldsOfSubTypes = getFieldsOfSubTypes(stub, typeTreeRepository);
		for (FieldsOfSubType fieldsOfSubType : fieldsOfSubTypes) {
			for (Field field : fieldsOfSubType.fields) {
				Stub childStub = convertToStub(createVariableFromField(field), parentStub, typeTreeRepository);			
				childStub.setSubTypeOfParentStub(fieldsOfSubType.subType);
			}
		}

	}

	private static List<Field> getFieldsIncludingAncestorTypes(Class<?> type) {
		List<Field> allFields = new ArrayList<Field>();
		while (true) {
			Field[] fieldsArray = type.getDeclaredFields();
			List<Field> fields = new ArrayList<Field>();
			if (fieldsArray != null) {
				for (Field f : fieldsArray) {
					fields.add(f);
				}
			}
			allFields.addAll(0, fields);

			type = type.getSuperclass();
			if (type == null) {
				break;
			}
			if (!type.isAnnotationPresent(XmlType.class)) {
				break;
			}

		}
		return allFields;
	}

	private static LinkedList<FieldsOfSubType> getFieldsOfSubTypes(Class<?> thisType, StubTypeTreeRepository typeTreeRepository) {
		LinkedList<FieldsOfSubType> fieldsOfAllSubTypes = new LinkedList<FieldsOfSubType>();

		if (!thisType.isAnnotationPresent(XmlSeeAlso.class)) {
			return fieldsOfAllSubTypes;
		}

		List<Class<?>> subTypes = getSubTypes(thisType);

		registerToStubTypeTree(thisType, typeTreeRepository, subTypes);

		for (Class<?> subType : subTypes) {

			FieldsOfSubType fieldsOfSubType = new FieldsOfSubType(subType);
			fieldsOfSubType.addAll(Arrays.asList(subType.getDeclaredFields()));
			LinkedList<FieldsOfSubType> fieldsOfGrandSonType = getFieldsOfSubTypes(subType, typeTreeRepository);
			fieldsOfAllSubTypes.add(fieldsOfSubType);
			fieldsOfAllSubTypes.addAll(fieldsOfGrandSonType);
		}

		return fieldsOfAllSubTypes;
	}

	private static void registerToStubTypeTree(Class<?> thisType, StubTypeTreeRepository typeTreeRepository, List<Class<?>> subTypes) {
		StubTypeTree thisTypeTree = typeTreeRepository.getStubTypeTree(thisType);
		for (Class<?> subType : subTypes) {			
			StubTypeTree subTypeTree = typeTreeRepository.getStubTypeTree(subType);
			subTypeTree.setParent(thisTypeTree);
		}
	}

	private static List<Class<?>> getSubTypes(Class<?> thisType) {
		List<Class<?>> subTypes = new ArrayList<Class<?>>();
		for (Class<?> subType : thisType.getAnnotation(XmlSeeAlso.class).value()) {
			if (thisType.isAssignableFrom(subType)) {
				subTypes.add(subType);
			}
		}
		return subTypes;
	}

	private static final class FieldsOfSubType {
		private Class<?> subType;
		private List<Field> fields = new ArrayList<Field>();

		private FieldsOfSubType(Class<?> subType) {
			super();
			this.subType = subType;
		}

		public void addAll(List<Field> fields) {
			this.fields.addAll(fields);

		}
	}
}
