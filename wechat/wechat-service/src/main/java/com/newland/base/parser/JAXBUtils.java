package com.newland.base.parser;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.commons.collections.CollectionUtils;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * jaxb解析工具类
 * @author Shiznn
 *
 */
public class JAXBUtils {
	
	static Logger logger = LoggerFactory.getLogger(JAXBUtils.class);
	/** 
     * JavaBean
     * 对象转xml报文，默认UTF-8 
     * @param obj 
     * @param writer 
     * @return  
	 * @throws Exception 
     */  
    public static String convertToXml(Object obj) throws Exception {  
        return convertToXml(obj, "UTF-8");  
    }  
  
    /** 
     * 对象转xml报文 
     * @param obj 
     * @param encoding  
     * @return  
     * @throws Exception 
     */  
    public static String convertToXml(Object obj, String encoding) throws Exception {  
        String result = null;  
        try {  
            JAXBContext context = JAXBContext.newInstance(obj.getClass());  
            Marshaller marshaller = context.createMarshaller();  
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);  
            marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);  
            /*marshaller.setProperty("com.sun.xml.internal.bind.marshaller.CharacterEscapeHandler",
                    new CharacterEscapeHandler() {
                @Override
                public void escape(char[] ch, int start,int length, boolean isAttVal,
                        Writer writer) throws IOException {
                    writer.write(ch, start, length);
                }
            });*/
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            XMLSerializer serializer = getXMLSerializer(output, obj);
            marshaller.marshal(obj, serializer.asContentHandler());  
            result = output.toString(encoding);
        } catch (Exception e) {  
        	logger.error("", e);
            throw e; 
        }  
  
        return result;  
    }  
    
    
  
    /** 
     * xml转对象
     * @param xml 
     * @param c 
     * @return 
     * @throws Exception 
     */  
    @SuppressWarnings("unchecked")  
    public static <T> T  converyToJavaBean(String xml, Class<T> c) throws Exception {  
        T t = null;  
        try {
        	Class<?> tmp = c;
        	List<Class<?>> list = new ArrayList<Class<?>>();
        	list.add(tmp);
        	while ((tmp = tmp.getSuperclass()).getName().equals(Object.class.getName()) == false){
        		list.add(tmp);
        	}
        	Class<?>[] classes = list.toArray(new Class<?>[list.size()]);
        	CollectionUtils.reverseArray(classes);
            JAXBContext context = JAXBContext.newInstance(classes);  
            Unmarshaller unmarshaller = context.createUnmarshaller();  
            t =  (T)unmarshaller.unmarshal(new StringReader(xml));  
        } catch (Exception e) {  
            logger.error("", e);
            throw e;
        }  
        return t;  
    } 
    private static XMLSerializer getXMLSerializer(OutputStream writer, Object obj) {
        // configure an OutputFormat to handle CDATA
        OutputFormat of = new OutputFormat();
        if (obj != null){ 
        	List<String> list = new ArrayList<String>();
        	Field[] fields = getFileds(obj);
        	if (fields != null && fields.length > 0){
        		for (Field field:fields){
        			XmlJavaTypeAdapter adapter = field.getAnnotation(XmlJavaTypeAdapter.class);
        			if (adapter == null) continue;
        			if (adapter.value().getName().equals(CDATAAdapter.class.getName()) ||
        					adapter.value().getName().equals(MsgTypeAdapter.class.getName()) ||
        					adapter.value().getName().equals(EventTypeAdapter.class.getName())){
        				XmlElement element = field.getAnnotation(XmlElement.class);
        				if (element != null){
        					list.add("^" + element.name());
        				}
        			}
        		}
        	}
	        of.setCDataElements(list.toArray(new String[list.size()]));   //
	        //of.setNonEscapingElements(list.toArray(new String[list.size()]));
        }

        // set any other options you'd like
        of.setPreserveSpace(true);
        of.setIndenting(true);
        
        XMLSerializer serializer = new XMLSerializer(of);
        serializer.setOutputByteStream(writer);
        
        return serializer;
    }

	private static Field[] getFileds(Object obj) {
		List<Field> list = new ArrayList<Field>();
		Class<?> clazz = obj.getClass();
		while (clazz != Object.class){
			Field[] fields = clazz.getDeclaredFields();
			if (fields != null){
				for (Field field: fields){
					list.add(field);
				}
			}
			clazz = clazz.getSuperclass();
		}
		return list.toArray(new Field[list.size()]);
	}
//	public static void main(String[] args) throws Exception {
//		Resp resp = new Resp();
//		resp.setMsgType(MsgType.image.name());
//		System.out.println(resp.getXMLRespContent());
//	}

}
