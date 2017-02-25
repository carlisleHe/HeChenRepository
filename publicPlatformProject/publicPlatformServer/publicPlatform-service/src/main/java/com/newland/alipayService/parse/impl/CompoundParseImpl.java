package com.newland.alipayService.parse.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.jxpath.JXPathContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.intensoft.checker.Checker;
import com.intensoft.coresyst.CIBTxnAdapterPara;
import com.intensoft.exception.AppRTException;
import com.intensoft.file.exception.FileParserException;
import com.intensoft.file.text.SepCombinedDataField;
import com.intensoft.file.text.annotation.DataColumn;
import com.intensoft.file.text.annotation.FileEntity;
import com.intensoft.formater.Decorator;
import com.intensoft.formater.Delimiter;
import com.intensoft.formater.FixedLength;
import com.intensoft.formater.Formatter;
import com.intensoft.formater.NullDecorator;
import com.intensoft.util.StringUtils;
import com.newland.alipayService.parse.CompoundParsePattern;
import com.newland.alipayService.parse.ICompoundParse;
import com.newland.alipayService.parse.ParseFileType;
/**
 * 
 * @describer 文件多对象解析实现
 * @author xzz
 * @date 2013-6-4
 */
@Service("iCompoundParse")
public class CompoundParseImpl implements ICompoundParse{
	private static final Logger logger = LoggerFactory.getLogger(CompoundParseImpl.class);
	/**
	 * 获取系统允许的临时文件目录
	 */
	@Resource(name = "tuxedoCIBTxnAdapterPara")
	private CIBTxnAdapterPara adapterPara;

	@Override
	public Collection<? > convertFileToModel(File file,CompoundParsePattern pattern) {
		InputStream in  = null;
		try{
			in = new FileInputStream(file);
			if(ParseFileType.PLAIN==pattern.fileType()){
				return convertFromPlain(in, pattern);
			}
			else{
				return convertFromExcel(in, pattern);
			}
		}
		catch(FileParserException e){
			throw e;
		}
		catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new FileParserException(e.getMessage());
		}
		
	}

	@Override
	public File convertModelToFile(Collection<? > collection,CompoundParsePattern pattern,String fileName) {
		String tempPath = "d:/";
		File file = new File(tempPath+"/"+fileName);
		try {
			OutputStream out = new FileOutputStream(file);
			if(ParseFileType.PLAIN==pattern.fileType()){
				convertToPlain(collection, out, pattern);
			}
			else
				convertToExcel(collection, out, pattern);
			return file;
		}
		catch(FileParserException e){
			throw e;
		}
		catch (FileNotFoundException e) {
			logger.error(String.format("文件:[%s]路径不存在!", tempPath));
			throw new FileParserException(e.getMessage());
		}
	}
	/**
	 * 
	 * @describer model配置信息
	 * @author xzz
	 * @date 2013-6-5
	 */
	class ConfigInfo{
		/**
		 * 文件格式 说明
		 */
		private FileEntity fileEntity;
		/**
		 * model name
		 */
		private String className;
		/**
		 * 
		 */
		private Map<String,DataColumn> map;
		public FileEntity getFileEntity() {
			return fileEntity;
		}
		public void setFileEntity(FileEntity fileEntity) {
			this.fileEntity = fileEntity;
		}
		public Map<String, DataColumn> getMap() {
			return map;
		}
		public void setMap(Map<String, DataColumn> map) {
			this.map = map;
		}
		public String getClassName() {
			return className;
		}
		public void setClassName(String className) {
			this.className = className;
		}
	}
	/**
	 * 解析配置提取映射的model name
	 * @param pattern
	 * @return
	 */
	private Map<String,ConfigInfo> parseModelNames(CompoundParsePattern pattern){
		String[] modelNames = pattern.mappedModelNames();
		if(modelNames.length==0)
			throw new FileParserException("文件解析模板配置错误!");
		Map<String,ConfigInfo> map = new HashMap<String,ConfigInfo>(modelNames.length);
		for(String modelName: modelNames){
			String key =null;
			String value =null;
			String[] kv = modelName.split(":");
			if(kv.length!=2){
				throw new FileParserException("文件解析模板配置错误!");
			}
			key = kv[0];
			value = kv[1];
			if(StringUtils.isBlank(value)){
				throw new FileParserException("文件解析模板配置错误!");
			}
			try {
				Class<?> clazz =  Class.forName(value);
				FileEntity fileEntity = clazz.getAnnotation(FileEntity.class);
				Map<String,DataColumn> dataColumn = populate(clazz);
				ConfigInfo c = new ConfigInfo();
				c.setFileEntity(fileEntity);
				c.setMap(dataColumn);
				c.setClassName(value);
				map.put(key, c);
				
			} catch (ClassNotFoundException e) {
				throw new FileParserException(String.format("文件解析模板model class:[%s]不存在!",value));
			}
		}
		return map;
	}
	/**
	 * 获取model对应的属性配置
	 * <p>自动检测当前类field并向上追加父类的field一直至最外层object</p>
	 * @param clazz
	 * @return model的datacolumn
	 */
	private Map<String,DataColumn> populate(Class<?> clazz) {
        Map<String,DataColumn> dataColumnMap = new HashMap<String,DataColumn>();
        Class<?> cnow = clazz;
        while(!Object.class.equals(cnow)){
        	Field[] fields = cnow.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                DataColumn dataColumn = (DataColumn) field.getAnnotation(DataColumn.class);
                if (dataColumn == null) {
                    continue;
                } else {
                    dataColumnMap.put(field.getName(), dataColumn);
                }
            }
            //向上遍历父类
            cnow=cnow.getSuperclass();
        }
        return dataColumnMap;
    }
	/**
	 * 查询行对应的区分值
	 * @param line 文件里的行
	 * @param delimiter 区分符
	 * @param position 区分值位置
	 * @return 区分值 
	 * null 未发现区分值
	 */
	private String findDelimiterValue(String line,char delimiter,int position){
		//1、构建正则表达式用于查询指定位置的数据
		//eg ([^\\|]*)\\|
		StringBuffer regex = new StringBuffer("([^\\");
		regex.append(delimiter).append("]*)\\").append(delimiter);
		Pattern pattern = Pattern.compile(regex.toString());
		//为了能正常解析到最后一个字段,line后面如果不是以区分符结束，则自动加上区分符，则方法只用于查询区分值，不影响正常的结果解析
		Matcher matcher = pattern.matcher(line);
		//2、根据指定位置进行查找
		int index = 0;
		//获取区分值所在的group
		for(;matcher.find()&&index<=position;){
			return matcher.group(1);
		}
		//查询不到返回null
		return null;
	}
	/**
	 * 解析标准文本文件，返回对应的对象集合
	 * @param in 文件流
	 * @param pattern 解析模板
	 * @return model集合
	 */
	@SuppressWarnings("deprecation")
	private Collection<? > convertFromPlain(InputStream in,CompoundParsePattern pattern) {
		//提取模板信息
		
		//解析映射的model name
		Map<String,ConfigInfo> modelNames = parseModelNames(pattern);
		//编码
		String charset = pattern.charset();
		//区分值位置
		int delimiterPosition = pattern.delimiterPosition();
		//符号
		char delimiter = pattern.delimiterChar();
		//定长报文标识
		boolean isFixLength = pattern.isFixLength();
		//定长报文区分值长度
		int delimiterValueLenth = pattern.delimiterValueLength();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(in,charset));
		} catch (IOException e) {
			throw new FileParserException("IOException when reading file!", e);
		}

		if (reader == null) {
			throw new FileParserException("unknown exception for reader is null!May read failed?");
		}
		
		//TODO 考虑到兼容原先的配置的问题，这里对原先的代码进行了原样迁移,后续可以考虑重构add by xuzz at 2013-06-05
		int lineCount = 0;
		int fIndex = 0;
		try {
			String line = null;
			List<Object> list = new ArrayList<Object>();
			Delimiter delimiterFormatter = new Delimiter();
			FixedLength fixLengthFormatter = new FixedLength(0, pattern.textAlign(), ' ');
			while ((line = reader.readLine()) != null) {
				// modified by zhangzhaoxing 2010-02-01
//				if ("".equals(line.trim())) {
//					continue;
//				}
				//正则，用于判断是否跳过本行的解析
				if(StringUtils.isNotBlank(pattern.skipRegex())&&line.matches(pattern.skipRegex())){
					continue;
				}
				//1、查询区分值
				String delimiterValue = null;
				//多对象模板
				if(delimiterPosition!=-1){
					//定长报文
					if(isFixLength){
						if(line.length()<delimiterPosition+delimiterValueLenth){
							throw new FileParserException(String.format("定长报文,行号:[%s],位置:[%s-%s],区分值获取失败!", lineCount+1,delimiterPosition,delimiterPosition+delimiterValueLenth));
						}
						delimiterValue = line.substring(delimiterPosition,delimiterPosition+delimiterValueLenth);	
					}
					//分隔符报文
					else{
						delimiterValue = findDelimiterValue(line, delimiter, delimiterPosition);
					}
				}
				//单对象模板
				else{
					delimiterValue="";
				}
				//2、根据区分值查找对应的model
				String[] fmted;
				ConfigInfo cf = modelNames.get(delimiterValue);
				if(cf==null||cf.getClassName()==null||cf.getFileEntity()==null||cf.getMap()==null){
					throw new FileParserException(String.format("行号:[%s],区分值:[%s],对应的model不存在!", lineCount+1,delimiterValue)); 
				}
				Object lineObj = null;
				try{
					lineObj = Class.forName(cf.getClassName()).newInstance();
				}
				catch(ClassNotFoundException e){
					throw new FileParserException(String.format("行号:[%s],区分值:[%s],model:[%s],不存在!", lineCount+1,delimiterValue,cf.getClassName())); 
				}
				//3、解析对应的行内容
				String value = line;
				JXPathContext context = JXPathContext.newContext(lineObj);
				FileEntity fileEntity = cf.getFileEntity();
				String[] fields = fileEntity.fields();
				boolean checkIllegalStr = fileEntity.checkIllegalStr();
				boolean setValue = false;
				Map<String, DataColumn> dataColumns = cf.getMap();
				logger.debug("Parse line " + lineCount);
			
				for (fIndex = 0; fIndex < fields.length; fIndex++) {
					// 获取格式化器
					String field = (String) fields[fIndex];
					DataColumn dataColumn = (DataColumn) dataColumns.get(field);
					Formatter formatter = (Formatter) dataColumn.formatter().newInstance();
					if (!"".equals(dataColumn.pattern())) {
						formatter.setPattern(dataColumn.pattern());
					}
					if (!"".equals(fileEntity.delimiter())) {
						delimiterFormatter.setDecorated(formatter);
						delimiterFormatter.setDelimiterChar(new Character(fileEntity.delimiter().charAt(0)).charValue());
						formatter = delimiterFormatter;
					} else if (dataColumn.fixLength() > 0) {
						fixLengthFormatter.setLength(dataColumn.fixLength());
						fixLengthFormatter.setDecorated(formatter);
						formatter = fixLengthFormatter;
					}
					if (!dataColumn.decorator().getName().equals(NullDecorator.class.getName())) {
						// 针对SepCombinedDataField中无法处理设置delimiter的bug
						// 该方法仅解决现有bug，不考虑现有存在的问题。
						// 若重构，需要考虑该处行为
						Decorator d = (Decorator) dataColumn.decorator().newInstance();
						if (d instanceof SepCombinedDataField) {
							((SepCombinedDataField) d).setDelimiterChar(fileEntity.delimiter().charAt(0));
						}
						fmted = d.extract(value);

					} else {
						fmted = formatter.extract(value);
					}

					if (fmted != null) {
						if (fmted.length > 0) {
							if (dataColumn.name() != null) {
								logger.debug("Element " + dataColumn.name()
										+ " Parse [" + fmted[0] + "] to path "
										+ field);
								// TODO
								// 基于原先接口中关于装饰器Decorator行为已经变更，且企业网银已经使用，拒绝修改，无法回归原由理念。
								// 详细参考DataColumn中的说明。
								// 使得对该对象是否为空的判定，只能依靠确认解析开对象以后的字符串是否以delimiter开头进行确认。
								Object obj = null;
								if (formatter instanceof Delimiter) {
									 delimiter = fileEntity.delimiter().charAt(0);
									if (fmted[0] != null
											&& !fmted[0].trim().equals("")) { // 如果为空，或者为空串时，都认为为空。
										if (fmted[0].charAt(0) != delimiter) { // 如果第一个字符是分割符，也认为为空。
											// modified by zhengsm 20120401
											// 增加非法字符判断如<>/\
											if (checkIllegalStr) {
												Checker checker = (Checker) dataColumn.checker().newInstance();
												String prepareForCheckStr = fmted[0];
												if (fmted[0].charAt(fmted[0].length() - 1) == delimiter)
													prepareForCheckStr = fmted[0].substring(0,fmted[0].length() - 1);
												checker.check(prepareForCheckStr);
											}
											// end
											obj = formatter.unformat(fmted[0]);
										}
									}
								} else if (formatter instanceof FixedLength) {
									if (fmted[0] != null
											&& !fmted[0].trim().equals("")) {// 如果为空，或者为空串时，都认为为空。
										// modified by zhengsm 20120401
										// 增加非法字符判断如<>/\
										if (checkIllegalStr) {
											Checker checker = (Checker) dataColumn.checker().newInstance();
											checker.check(fmted[0]);
										}
										// end
										obj = formatter.unformat(fmted[0]);
									}
								} else {
									throw new FileParserException("unexpected extract formatter!only support Delimiter and FixedLength!");
								}
								logger.debug("result=[" + obj + "]");
								context.setValue(field, obj);
								setValue = true;
							} else {
								logger.debug("skip this field");
							}
						} else {
							break;
						}
					} else {
						break;
					}
					if (fmted.length > 1) {
						value = fmted[1];
					} else {
						break;
					}
				}
				if (setValue) {
					list.add(lineObj);
				}
				lineCount++;
			}
			return list;

		} catch (Exception e) {
			// 为了异常国际化，需要将具体异常码抛出
			if (e instanceof AppRTException) {
				AppRTException ate = (AppRTException) e;
				throw new AppRTException(ate.getCode(), new Object[] {Integer.valueOf(lineCount + 1),Integer.valueOf(fIndex + 1) }, e.getMessage(), e);
			}
			throw new FileParserException("文件解释异常，错误可能在第" + (lineCount + 1)+ "行,第" + (fIndex + 1) + "字段！", e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception ee) {
				}
			}
		}
	}
	private Collection<? extends Object> convertFromExcel(InputStream in,CompoundParsePattern pattern){
		throw new UnsupportedOperationException("目前不支持excel多对象解析");
	}
	/**
	 * 结果数据转成文本文件
	 * @param object
	 * @param outputStream
	 * @param pattern
	 */
	private void convertToPlain(Collection<?>  object,OutputStream outputStream,CompoundParsePattern pattern){
        StringBuffer sb = new StringBuffer();
        Delimiter delimiterFormatter = new Delimiter();
        FixedLength fixLengthFormatter = new FixedLength(0, pattern.textAlign(), ' ');
        int lineCount = 0;
        int fIndex = 0;
        try {
        	//TODO 考虑到兼容原先的配置的问题，这里对原先的代码进行了原样迁移,后续可以考虑重构add by xuzz at 2013-06-05
            Iterator<?> it = object.iterator();
            String fieldValue;
            String charset =  pattern.charset();;
            //追加头
            if(StringUtils.isNotBlank(pattern.addHead()))
            	sb.append(pattern.addHead()).append("\n");
            while(it.hasNext()) {
                // 创建目标对象
                Object lineObj = it.next();
                FileEntity fileEntity = (FileEntity) lineObj.getClass().getAnnotation(FileEntity.class);
                if(fileEntity == null){
       			 	throw new FileParserException("unable to get @FileEntity,did the entity has this annotation declared?");
                }
               
                JXPathContext context = JXPathContext.newContext(lineObj);       
                String[] fields = fileEntity.fields();
                Map<String,DataColumn> dataColumns = populate(lineObj.getClass());
                logger.debug("Parse line " + lineCount);
                for ( fIndex=0; fIndex < fields.length; fIndex++ ) {
                    String field = (String) fields[fIndex];
                    DataColumn dataColumn = (DataColumn) dataColumns.get(field);
                    Formatter formatter = (Formatter) dataColumn.formatter().newInstance();
                    if( !"".equals(dataColumn.pattern())) {
                        formatter.setPattern(dataColumn.pattern());
                    }
                    if ( !"".equals(fileEntity.delimiter()) ) {
                        delimiterFormatter.setDecorated(formatter);
                        delimiterFormatter.setDelimiterChar(new Character(fileEntity.delimiter().charAt(0)).charValue());
                        formatter = delimiterFormatter;
                    } else if (dataColumn.fixLength() > 0) {
                        fixLengthFormatter.setLength(dataColumn.fixLength());
                        fixLengthFormatter.setDecorated(formatter);
                        formatter = fixLengthFormatter;
                    }
                    Object fieldObj = null;
                    if (field != null) fieldObj = context.getValue(field);
//                    if (fieldObj == null && df.getDefaultValue() != null) {
//                        // 使用默认值
//                        fieldObj = df.getDefaultValue();
//                    }
                    fieldValue = formatter.format(fieldObj);
                    if (fieldValue != null) {
                        sb.append(fieldValue);
                    } else {
                        throw new FileParserException("字段值不得为null, 错误可能在第"+(lineCount+1)+"行,第"+(fIndex+1)+"字段！");
                    }
                }
                lineCount++;
                sb.append("\n");
            }
            //追加尾
            if(StringUtils.isNotBlank(pattern.addTail()))
            	sb.append(pattern.addTail()).append("\n");
            outputStream.write(sb.toString().getBytes(charset));
            outputStream.close();

        } catch (Exception e) {    	
            throw new FileParserException("格式化异常，错误可能在第"+(lineCount+1)+"行,第"+(fIndex+1)+"字段！", e);
        } finally {

        }
    
	}
	private void convertToExcel(Collection<?>  object,OutputStream outputStream,CompoundParsePattern pattern){
		throw new UnsupportedOperationException("目前不支持excel多对象解析");
	}
}