package org.jaxws.stub2html.service;

import static org.jaxws.stub2html.util.MyClassUtils.isClassArrayOrCollection;
import static org.jaxws.util.lang.NameConversionUtils.camelToHyphen;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.xml.bind.annotation.XmlElement;

import org.jaxws.stub2html.model.JavaLanguageVariable;
import org.jaxws.stub2html.util.GenericsUtils;
import org.jaxws.util.lang.NameConversionUtils;

/**
 * 
 * @author chenjianjx
 * 
 */
public class JavaLanguageVariableFactory {

	/**
	 * create a variable from a field which is @XmlElement annotated
	 */
	public static JavaLanguageVariable createVariableFromField(Field field) {
		JavaLanguageVariable variable = new JavaLanguageVariable();

		XmlElement annotation = field.getAnnotation(XmlElement.class);
		if (annotation == null) {
			variable.setVariableName(getElementName(field));
			variable.setRequired(false);
		} else {
			variable.setVariableName(getVariableName(field, annotation));
			variable.setRequired(isVariableRequired(annotation));
		}
		variable.setType(GenericsUtils.getFieldGenericType(field));
		variable.setMultiOccurs(isClassArrayOrCollection((Class<?>) field.getType()));
		variable.setCardinality(processCardinality(variable.isRequired(), variable.isMultiOccurs()));
		return variable;
	}
	
	private static String processCardinality(boolean isRequired, boolean isMultiOccurs)
	{
		return (isRequired? "1..":"0..") + (isMultiOccurs? "n":"1"); 		
	}

	private static String getElementName(Field field) {
		return //camelToHyphen(
				(field.getName());
				
	}

	private static String getVariableName(Field field, XmlElement annotation) {
		String variableName = annotation.name();
		if ("##default".equals(variableName)) {
			variableName = getElementName(field);
		}
		return variableName;
	}

	/**
	 * create variables from parameters which are @WebParam annotated
	 */
	public static List<JavaLanguageVariable> createVariablesFromMethodParamaters(Method method) {
		List<JavaLanguageVariable> variables = new ArrayList<JavaLanguageVariable>();
		Annotation[][] annotationMatrix = method.getParameterAnnotations();
		for (int paramIndex = 0; paramIndex < annotationMatrix.length; paramIndex++) {
			Annotation[] annotationsForSingleParam = annotationMatrix[paramIndex];
			WebParam xmlAnnotation = getXmlAnnotation(annotationsForSingleParam);

			if (xmlAnnotation == null) {
				throw new IllegalArgumentException("One of the parameters of " + method.getName() + "  is not annotated with " + WebParam.class.getSimpleName());
			}

			JavaLanguageVariable variable = buildJavaVariableFromMethodParam(method, paramIndex, xmlAnnotation);
			variables.add(variable);
		}
		return variables;
	}

	/**
	 * create a variable from a method's return which is @WebResult annotated
	 */
	public static JavaLanguageVariable createVariableFromMethodReturn(Method method) {

		WebResult webResultAnnotation = method.getAnnotation(WebResult.class);
		if (webResultAnnotation == null) {
			throw new IllegalArgumentException(method.getName() + ": return result is not annotated with " + WebResult.class.getSimpleName());
		}
		JavaLanguageVariable variable = new JavaLanguageVariable();
		variable.setType(GenericsUtils.getMethodGenericReturnType(method));
		variable.setVariableName(webResultAnnotation.name());
		variable.setRequired(true);
		Class<?> resultClass = method.getReturnType();
		variable.setMultiOccurs(isClassArrayOrCollection(resultClass));
		variable.setCardinality(processCardinality(variable.isRequired(), variable.isMultiOccurs()));
		return variable;

	}

	private static JavaLanguageVariable buildJavaVariableFromMethodParam(Method method, int paramIndex, WebParam xmlAnnotation) {
		JavaLanguageVariable variable = new JavaLanguageVariable();
		variable.setType(GenericsUtils.getMethodGenericParameterTypes(method, paramIndex));
		variable.setVariableName(xmlAnnotation.name());
		variable.setRequired(true);
		Class<?> paramClass = method.getParameterTypes()[paramIndex];
		variable.setMultiOccurs(isClassArrayOrCollection(paramClass));
		variable.setCardinality(processCardinality(variable.isRequired(), variable.isMultiOccurs()));
		return variable;
	}

	private static WebParam getXmlAnnotation(Annotation[] annotationForSingleParam) {
		for (Annotation annotation : annotationForSingleParam) {
			if (annotation instanceof WebParam) {
				return (WebParam) annotation;
			}
		}
		return null;
	}

	static boolean isVariableRequired(XmlElement annotation) {
		return !annotation.nillable() && annotation.required();
	}

}
