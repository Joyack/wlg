package com.wlg.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public final class ExcelUtil {

	private ExcelUtil() {
	}

	/**
	 * @param excel_name
	 *            生成的Excel文件路径+名称
	 * @param headList
	 *            Excel文件Head标题集合
	 *
	 * @param dataList
	 *            Excel文件数据内容部分
	 * @throws Exception
	 */
	public static void createExcel(String excel_name, String[] headList,
			String[] fieldList, List<Map<String, Object>> dataList)
			throws Exception {
		// 创建新的Excel 工作簿
		HSSFWorkbook workbook = new HSSFWorkbook();

		// 在Excel工作簿中建一工作表，其名为缺省值
		// 如要新建一名为"效益指标"的工作表，其语句为：
		// HSSFSheet sheet = workbook.createSheet("效益指标");
		HSSFSheet sheet = workbook.createSheet();
		// 在索引0的位置创建行（最顶端的行）
		HSSFRow row = sheet.createRow(0);
		// ===============================================================
		for (int i = 0; i < headList.length; i++) {

			// 在索引0的位置创建单元格（左上端）
			HSSFCell cell = row.createCell(i);
			// 定义单元格为字符串类型
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			// 在单元格中输入一些内容
			cell.setCellValue(headList[i]);
		}
		// ===============================================================

		for (int n = 0; n < dataList.size(); n++) {
			// 在索引1的位置创建行（最顶端的行）
			HSSFRow row_value = sheet.createRow(n + 1);
			Map<String, Object> dataMap = dataList.get(n);
			// ===============================================================
			for (int i = 0; i < fieldList.length; i++) {

				// 在索引0的位置创建单元格（左上端）
				HSSFCell cell = row_value.createCell(i);
				// 定义单元格为字符串类型
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				// 在单元格中输入一些内容
				cell.setCellValue(objToString(dataMap.get(fieldList[i])));
			}
			// ===============================================================
		}

		// 新建一输出文件流
		FileOutputStream fOut = new FileOutputStream(excel_name);
		// 把相应的Excel 工作簿存盘
		workbook.write(fOut);
		fOut.flush();
		// 操作结束，关闭文件
		fOut.close();
		//org.jeecgframework.core.util.LogUtil.info("[" + excel_name + "]" + "文件生成...");
	}

	/**
	 * @param excel_name
	 *            生成的Excel文件路径+名称
	 * @param headList
	 *            Excel文件Head标题集合
	 * @param
	 * @param dataList
	 *            Excel文件数据内容部分
	 * @throws Exception
	 */
	public static void createExcel(String excel_name, List<String> headList,
			List<String> fieldList, List<Map<String, Object>> dataList)
			throws Exception {
		// 创建新的Excel 工作簿
		HSSFWorkbook workbook = new HSSFWorkbook();

		// 在Excel工作簿中建一工作表，其名为缺省值
		// 如要新建一名为"效益指标"的工作表，其语句为：
		// HSSFSheet sheet = workbook.createSheet("效益指标");
		HSSFSheet sheet = workbook.createSheet();
		// 在索引0的位置创建行（最顶端的行）
		HSSFRow row = sheet.createRow(0);
		// ===============================================================
		for (int i = 0; i < headList.size(); i++) {

			// 在索引0的位置创建单元格（左上端）
			HSSFCell cell = row.createCell(i);
			// 定义单元格为字符串类型
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			// 在单元格中输入一些内容
			cell.setCellValue(headList.get(i));
		}
		// ===============================================================

		for (int n = 0; n < dataList.size(); n++) {
			// 在索引1的位置创建行（最顶端的行）
			HSSFRow row_value = sheet.createRow(n + 1);
			Map<String, Object> dataMap = dataList.get(n);
			// ===============================================================
			for (int i = 0; i < fieldList.size(); i++) {

				// 在索引0的位置创建单元格（左上端）
				HSSFCell cell = row_value.createCell(i);
				// 定义单元格为字符串类型
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				// 在单元格中输入一些内容
				cell.setCellValue(objToString(dataMap.get(fieldList.get(i))));
			}
			// ===============================================================
		}

		// 新建一输出文件流
		FileOutputStream fOut = new FileOutputStream(excel_name);
		// 把相应的Excel 工作簿存盘
		workbook.write(fOut);
		fOut.flush();
		// 操作结束，关闭文件
		fOut.close();
		//org.jeecgframework.core.util.LogUtil.info("[" + excel_name + "]" + "文件生成...");
	}

	/**
	 * @param
	 * @param headList
	 *            Excel文件Head标题集合
	 *            Excel文件Field标题集合
	 * @param dataList
	 *            Excel文件数据内容部分
	 * @throws HSSFWorkbook
	 */
	public static HSSFWorkbook createExcel(List<String> headList,
			List<String> fieldList, List<Map<String, Object>> dataList)
			throws Exception {
		// 创建新的Excel 工作簿
		HSSFWorkbook workbook = new HSSFWorkbook();

		// 在Excel工作簿中建一工作表，其名为缺省值
		// 如要新建一名为"效益指标"的工作表，其语句为：
		// HSSFSheet sheet = workbook.createSheet("效益指标");
		HSSFSheet sheet = workbook.createSheet();
		// 在索引0的位置创建行（最顶端的行）
		HSSFRow row = sheet.createRow(0);
		// ===============================================================
		for (int i = 0; i < headList.size(); i++) {

			// 在索引0的位置创建单元格（左上端）
			HSSFCell cell = row.createCell(i);
			// 定义单元格为字符串类型
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			// 在单元格中输入一些内容
			cell.setCellValue(headList.get(i));
		}
		// ===============================================================
		if(dataList != null && dataList.size() > 0){
			for (int n = 0; n < dataList.size(); n++) {
				// 在索引1的位置创建行（最顶端的行）
				HSSFRow row_value = sheet.createRow(n + 1);
				Map<String, Object> dataMap = dataList.get(n);
				// ===============================================================
				for (int i = 0; i < fieldList.size(); i++) {
	
					// 在索引0的位置创建单元格（左上端）
					HSSFCell cell = row_value.createCell(i);
					// 定义单元格为字符串类型
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					// 在单元格中输入一些内容
					cell.setCellValue(objToString(dataMap.get(fieldList.get(i))));
				}
				// ===============================================================
			}
		}
		return workbook;
	}

	private static String objToString(Object obj) {
		if (obj == null) {
			return "";
		} else {
			if (obj instanceof String) {
				return (String) obj;
			} else if (obj instanceof Date) {
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");		
				return sdf.format(obj);
				
			} else {
				return obj.toString();
			}
		}
	}

	/**
	 * 
	 * @param excel_name
	 *            生成的Excel文件路径+名称
	 * @param headList
	 *            Excel文件Head标题部分
	 * @param valueList
	 *            Excel文件数据内容部分
	 * @throws Exception
	 */
	public static void bulidExcel(String excel_name, String[] headList,
			List<String[]> valueList) throws Exception {
		// 创建新的Excel 工作簿
		HSSFWorkbook workbook = new HSSFWorkbook();

		// 在Excel工作簿中建一工作表，其名为缺省值
		// 如要新建一名为"效益指标"的工作表，其语句为：
		// HSSFSheet sheet = workbook.createSheet("效益指标");
		HSSFSheet sheet = workbook.createSheet();
		// 在索引0的位置创建行（最顶端的行）
		HSSFRow row = sheet.createRow(0);
		// ===============================================================
		for (int i = 0; i < headList.length; i++) {

			// 在索引0的位置创建单元格（左上端）
			HSSFCell cell = row.createCell(i);
			// 定义单元格为字符串类型
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			// 在单元格中输入一些内容
			cell.setCellValue(headList[i]);
		}
		// ===============================================================

		for (int n = 0; n < valueList.size(); n++) {
			// 在索引1的位置创建行（最顶端的行）
			HSSFRow row_value = sheet.createRow(n + 1);
			String[] valueArray = valueList.get(n);
			// ===============================================================
			for (int i = 0; i < valueArray.length; i++) {

				// 在索引0的位置创建单元格（左上端）
				HSSFCell cell = row_value.createCell(i);
				// 定义单元格为字符串类型
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				// 在单元格中输入一些内容
				cell.setCellValue(valueArray[i]);
			}
			// ===============================================================
		}

		// 新建一输出文件流
		FileOutputStream fOut = new FileOutputStream(excel_name);
		// 把相应的Excel 工作簿存盘
		workbook.write(fOut);
		fOut.flush();
		// 操作结束，关闭文件
		fOut.close();
		//org.jeecgframework.core.util.LogUtil.info("[" + excel_name + "]" + "文件生成...");
	}

	/**
	 * 读取 Excel文件内容
	 * 
	 * @param excel_name
	 * @return
	 * @throws Exception
	 */
	public static List<String[]> readExcel(String excel_name) throws Exception {
		// 结果集
		List<String[]> list = new ArrayList<String[]>();

		HSSFWorkbook hssfworkbook = new HSSFWorkbook(new FileInputStream(
				excel_name));

		// 遍历该表格中所有的工作表，i表示工作表的数量 getNumberOfSheets表示工作表的总数
		HSSFSheet hssfsheet = hssfworkbook.getSheetAt(0);

		// 遍历该行所有的行,j表示行数 getPhysicalNumberOfRows行的总数
		for (int j = 0; j < hssfsheet.getPhysicalNumberOfRows(); j++) {
			HSSFRow hssfrow = hssfsheet.getRow(j);
			if(hssfrow!=null){
			int col = hssfrow.getPhysicalNumberOfCells();
			// 单行数据
			String[] arrayString = new String[col];
			for (int i = 0; i < col; i++) {
				HSSFCell cell = hssfrow.getCell(i);
				if (cell == null) {
					arrayString[i] = "";
				} else if (cell.getCellType() == 0) {
					// arrayString[i] = new Double(cell.getNumericCellValue()).toString();
					if (HSSFCell.CELL_TYPE_NUMERIC == cell.getCellType()) { 
						  if (HSSFDateUtil.isCellDateFormatted(cell)) {    
						    Date d = cell.getDateCellValue();    
//						    DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");    
						     DateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						    arrayString[i] = formater.format(d);   
						   } else {    
						       arrayString[i] = new BigDecimal(cell.getNumericCellValue()).longValue()+"";    
						}
					}
				} else {// 如果EXCEL表格中的数据类型为字符串型
					arrayString[i] = cell.getStringCellValue().trim();
				}
			}
			list.add(arrayString);
		}
		}
		return list;
	}

	/**
	 * 读取 Excel文件内容
	 * 
	 * @param excel_name
	 * @return
	 * @throws Exception
	 */
	public static List<List<Object>> readExcelByList(String excel_name)
			throws Exception {
		// 结果集
		List<List<Object>> list = new ArrayList<List<Object>>();

		HSSFWorkbook hssfworkbook = new HSSFWorkbook(new FileInputStream(
				excel_name));

		// 遍历该表格中所有的工作表，i表示工作表的数量 getNumberOfSheets表示工作表的总数
		HSSFSheet hssfsheet = hssfworkbook.getSheetAt(0);

		// 遍历该行所有的行,j表示行数 getPhysicalNumberOfRows行的总数
		for (int j = 0; j < hssfsheet.getPhysicalNumberOfRows(); j++) {
			HSSFRow hssfrow = hssfsheet.getRow(j);
			if (hssfrow != null) {
				int col = hssfrow.getPhysicalNumberOfCells();
				// 单行数据
				List<Object> arrayString = new ArrayList<Object>();
				for (int i = 0; i < col; i++) {
					HSSFCell cell = hssfrow.getCell(i);
					if (cell == null) {
						arrayString.add("");
					} else if (cell.getCellType() == 0) {
						arrayString.add(new Double(cell.getNumericCellValue())
								.toString());
					} else {// 如果EXCEL表格中的数据类型为字符串型
						arrayString.add(cell.getStringCellValue().trim());
					}
				}
				list.add(arrayString);
			}
		}
		return list;
	}

	/**
	 * 读取 Excel文件内容
	 * 
	 * @return
	 * @throws Exception
	 */
	public static List<List<Object>> readExcelByInputStream(
			InputStream inputstream) throws Exception {
		// 结果集
		List<List<Object>> list = new ArrayList<List<Object>>();

		HSSFWorkbook hssfworkbook = new HSSFWorkbook(inputstream);

		// 遍历该表格中所有的工作表，i表示工作表的数量 getNumberOfSheets表示工作表的总数
		HSSFSheet hssfsheet = hssfworkbook.getSheetAt(0);

		// 遍历该行所有的行,j表示行数 getPhysicalNumberOfRows行的总数

		// //org.jeecgframework.core.util.LogUtil.info("excel行数： "+hssfsheet.getPhysicalNumberOfRows());
		for (int j = 0; j < hssfsheet.getPhysicalNumberOfRows(); j++) {
			HSSFRow hssfrow = hssfsheet.getRow(j);
			if (hssfrow != null) {
				int col = hssfrow.getPhysicalNumberOfCells();
				// 单行数据
				List<Object> arrayString = new ArrayList<Object>();
				for (int i = 0; i < col; i++) {
					HSSFCell cell = hssfrow.getCell(i);
					if (cell == null) {
						arrayString.add("");
					} else if (cell.getCellType() == 0) {
						arrayString.add(new Double(cell.getNumericCellValue())
								.toString());
					} else {// 如果EXCEL表格中的数据类型为字符串型
						arrayString.add(cell.getStringCellValue().trim());
					}
				}
				list.add(arrayString);
			}
		}
		return list;
	}

	/**
	 * 导入 excel
	 * 
	 * @param file : Excel文件
	 * @param pojoClass : 对应的导入对象 (每行记录)
	 * @return
	 */
	@Deprecated
	public static Collection importExcel(File file, Class pojoClass) {
		try {
			// 将传入的File构造为FileInputStream;
			FileInputStream in = new FileInputStream(file);
			return importExcelByIs(in, pojoClass);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * 导入 excel
	 * 
	 * @param inputstream : 文件输入流
	 * @param pojoClass : 对应的导入对象 (每行记录)
	 * @return
	 */
	@Deprecated
	public static Collection importExcelByIs(InputStream inputstream, Class pojoClass) {
		Collection dist = new ArrayList<Object>();
		try {
			// 得到目标目标类的所有的字段列表
			Field filed[] = pojoClass.getDeclaredFields();
			// 将所有标有Annotation的字段，也就是允许导入数据的字段,放入到一个map中
			Map<String, Method> fieldSetMap = new HashMap<String, Method>();
			Map<String, Method> fieldSetConvertMap = new HashMap<String, Method>();
			// 循环读取所有字段
			for (int i = 0; i < filed.length; i++) {
				Field f = filed[i];
				// 得到单个字段上的Annotation
				Excel excel = f.getAnnotation(Excel.class);
				// 如果标识了Annotationd的话
				if (excel != null) {
					// 构造设置了Annotation的字段的Setter方法
					String fieldname = f.getName();
					String setMethodName = "set" + fieldname.substring(0, 1).toUpperCase() + fieldname.substring(1);
					// 构造调用的method，
					Method setMethod = pojoClass.getMethod(setMethodName, new Class[] { f.getType() });
					// 将这个method以Annotaion的名字为key来存入。
					// 对于重名将导致 覆盖 失败，对于此处的限制需要
					fieldSetMap.put(excel.exportName(), setMethod);
					if (excel.importConvertSign() == 1) {
						//----------------------------------------------------------------

						// 用get/setXxxxConvert方法名的话， 由于直接使用了数据库绑定的Entity对象，注入会有冲突
						StringBuffer setConvertMethodName = new StringBuffer("convertSet");
						setConvertMethodName.append(fieldname.substring(0, 1).toUpperCase());
						setConvertMethodName.append(fieldname.substring(1));

						//----------------------------------------------------------------
						Method getConvertMethod = pojoClass.getMethod(setConvertMethodName.toString(), new Class[] { String.class });
						fieldSetConvertMap.put(excel.exportName(), getConvertMethod);
					}
				}
			}
			// 将传入的File构造为FileInputStream;
			// // 得到工作表
			HSSFWorkbook book = new HSSFWorkbook(inputstream);
			// // 得到第一页
			HSSFSheet sheet = book.getSheetAt(0);
			// // 得到第一面的所有行
			Iterator<Row> row = sheet.rowIterator();
			// 得到第一行，也就是标题行
			Row title = row.next();
			// 得到第一行的所有列
			Iterator<Cell> cellTitle = title.cellIterator();
			// 将标题的文字内容放入到一个map中。
			Map titlemap = new HashMap();
			// 从标题第一列开始
			int i = 0;
			// 循环标题所有的列
			while (cellTitle.hasNext()) {
				Cell cell = cellTitle.next();
				String value = cell.getStringCellValue();
				titlemap.put(i, value);
				i = i + 1;
			}
			// 用来格式化日期的DateFormat
			// SimpleDateFormat sf;
			while (row.hasNext()) {
				// 标题下的第一行
				Row rown = row.next();
				// 行的所有列
				Iterator<Cell> cellbody = rown.cellIterator();
				// 得到传入类的实例
				Object tObject = pojoClass.newInstance();
				int k = 0;
				// 遍历一行的列
				while (cellbody.hasNext()) {
					Cell cell = cellbody.next();
					// 这里得到此列的对应的标题
					String titleString = (String) titlemap.get(k);
					// 如果这一列的标题和类中的某一列的Annotation相同，那么则调用此类的的set方法，进行设值
					if (fieldSetMap.containsKey(titleString)) {
						Method setMethod = (Method) fieldSetMap.get(titleString);
						// 得到setter方法的参数
						Type[] ts = setMethod.getGenericParameterTypes();
						// 只要一个参数
						String xclass = ts[0].toString();
						// 判断参数类型
						if (Cell.CELL_TYPE_STRING == cell.getCellType()
								&& fieldSetConvertMap.containsKey(titleString)) {
							// 目前只支持从String转换
							fieldSetConvertMap.get(titleString).invoke(tObject, cell.getStringCellValue());
						} else {
							if (xclass.equals("class java.lang.String")) {
								// 先设置Cell的类型，然后就可以把纯数字作为String类型读进来了：
								cell.setCellType(Cell.CELL_TYPE_STRING);
								setMethod.invoke(tObject, cell.getStringCellValue());
							} else if (xclass.equals("class java.util.Date")) {

								Date cellDate = null;
								if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
									// 日期格式
									cellDate = cell.getDateCellValue();
								} else {	// 全认为是  Cell.CELL_TYPE_STRING: 如果不是 yyyy-mm-dd hh:mm:ss 的格式就不对(wait to do:有局限性)
									cellDate = stringToDate(cell.getStringCellValue());
								}
			                    setMethod.invoke(tObject,cellDate);

								//// --------------------------------------------------------------------------------------------
								//String cellValue = cell.getStringCellValue();
								//Date theDate = stringToDate(cellValue);
								//setMethod.invoke(tObject, theDate);

								//// --------------------------------------------------------------------------------------------
							} else if (xclass.equals("class java.lang.Boolean")) {
								boolean valBool;
								if (Cell.CELL_TYPE_BOOLEAN == cell.getCellType()) {
									valBool = cell.getBooleanCellValue();
								} else {// 全认为是  Cell.CELL_TYPE_STRING
									valBool = cell.getStringCellValue().equalsIgnoreCase("true") 
											|| (!cell.getStringCellValue().equals("0"));
								}
								setMethod.invoke(tObject, valBool);
							} else if (xclass.equals("class java.lang.Integer")) {
								Integer valInt;
								if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
									valInt = (new Double(cell.getNumericCellValue())).intValue();
								} else {// 全认为是  Cell.CELL_TYPE_STRING
									valInt = new Integer(cell.getStringCellValue());
								}
								setMethod.invoke(tObject, valInt);
							} else if (xclass.equals("class java.lang.Long")) {
								Long valLong;
								if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
									valLong = (new Double(cell.getNumericCellValue())).longValue();
								} else {// 全认为是  Cell.CELL_TYPE_STRING
									valLong = new Long(cell.getStringCellValue());
								}
								setMethod.invoke(tObject, valLong);
							} else if (xclass.equals("class java.math.BigDecimal")) {
								BigDecimal valDecimal;
								if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
									valDecimal = new BigDecimal(cell.getNumericCellValue());
								} else {// 全认为是  Cell.CELL_TYPE_STRING
									valDecimal = new BigDecimal(cell.getStringCellValue());
								}
								setMethod.invoke(tObject, valDecimal);
								//// ----------------------------------------------------------------

								//setMethod.invoke(tObject, new BigDecimal(cell.getStringCellValue()));

								//// ----------------------------------------------------------------

							}
						}
					}
					// 下一列
					k = k + 1;
				}
				dist.add(tObject);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return dist;
	}
	
	
	/**
	 * 自定义
	 * 导入 excel
	 * 
	 * @param inputstream : 文件输入流
	 * @param  : 对应的导入对象 (每行记录)
	 * @return listmap
	 */
	
	public static List<Map<String,Object>> importExcelByIs(InputStream inputstream) {
		
		try {
			// 得到工作表
		    HSSFWorkbook workbook = new HSSFWorkbook(inputstream);

			List<Map<String,Object>> listmap = new ArrayList<Map<String,Object>>();
			
			for (int numSheets = 0; numSheets < workbook.getNumberOfSheets(); numSheets++) {
				if (workbook.getSheetAt(numSheets)!=null) {
					Sheet aSheet = workbook.getSheetAt(numSheets); // 获得一个sheet
										
					ArrayList<String> header = new ArrayList<String>();
					//TODO 因为格式问题，所以要从第二行标题栏开始
					for (int rowNumOfSheet = 2; rowNumOfSheet <=aSheet.getLastRowNum(); rowNumOfSheet++) {
						// exl开始行
						if ( aSheet.getRow(rowNumOfSheet)!=null /*&& rowNumOfSheet > 0*/) {
							
							Row aRow = aSheet.getRow(rowNumOfSheet);
							Map<String,Object> map = new HashMap<String,Object>();
							for (int cellNumOfRow = 0; cellNumOfRow < aRow.getLastCellNum(); cellNumOfRow++) {
								//exl开始列
								if (null != aRow.getCell(cellNumOfRow)) {
									
									Cell aCell = aRow.getCell(cellNumOfRow);

									//TODO 因为格式问题，所以要从第二行标题栏开始
									if(rowNumOfSheet==2)
									{
										header.add(aCell.toString().trim());
									}else{
										
										switch (aCell.getCellType()) {
										    case Cell.CELL_TYPE_STRING:
										    	map.put(""+header.get(cellNumOfRow), aCell.getStringCellValue().trim());
										    	break;
										    case Cell.CELL_TYPE_NUMERIC:
										        if (DateUtil.isCellDateFormatted(aCell)) {
										        	map.put(""+header.get(cellNumOfRow), aCell.getDateCellValue());
										        } else {
										        	map.put(""+header.get(cellNumOfRow), aCell.getNumericCellValue());
										        }
										        break;
										    case Cell.CELL_TYPE_BOOLEAN:
										    	map.put(""+header.get(cellNumOfRow), aCell.getBooleanCellValue());
										    	break;
										    case Cell.CELL_TYPE_FORMULA:
										    	FormulaEvaluator formula = new HSSFFormulaEvaluator(workbook);
										    	map.put(""+header.get(cellNumOfRow), formula.evaluate(aCell).getNumberValue());
										    	break;
										    default:
										    	map.put(""+header.get(cellNumOfRow), "");
									    }	
									}
								}else{
									map.put(""+header.get(cellNumOfRow),"");
								}
							}


							if(rowNumOfSheet!=0&&rowNumOfSheet!=2){
//								map.put("行号", rowNumOfSheet);//从0开始
								listmap.add(map);
							}
						}
					}
				}
			}
			return listmap;
		} catch (Exception e) {
			System.out.println("ReadExcelError" + e);
		}
		
		return null;
	}
	
	
	/**
	 * 回写Excel
	 * @param listMap
	 * @param file
	 * 
	 */
	public void updateToExcel(List<Map<String, Object>> listMap, String file) throws FileNotFoundException, IOException{
		
		// 得到工作表
	    HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(new File(file)));
		
	    try {
			Sheet firstSheet = workbook.getSheetAt(0);
			int lastColumnNo = firstSheet.getRow(0).getLastCellNum();
			CellStyle cellstyle = workbook.createCellStyle();
			cellstyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			//cellstyle.setFillForegroundColor(HSSFColor.DARK_RED.index);
			cellstyle.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);
			// cellstyle.setFillBackgroundColor(HSSFColor.DARK_RED.index);
			if (listMap != null && listMap.size() > 0) {
				Cell errorMsgHeadCell = firstSheet.getRow(0).createCell(lastColumnNo + 3);
				errorMsgHeadCell.setCellValue("错误消息提示");
				errorMsgHeadCell.setCellStyle(cellstyle);
				Cell IsImportHeadCell = firstSheet.getRow(0).createCell(lastColumnNo + 2);
				IsImportHeadCell.setCellValue("是否导入成功");
				IsImportHeadCell.setCellStyle(cellstyle);
			}
			for (int i = 0; i < listMap.size(); i++) {
				Map<String,Object> map = listMap.get(i);
				if (map != null) {
					String errorMsg = (String)map.get("错误消息提示");
					Row row = firstSheet.getRow((Integer)map.get("行号"));
					Cell errorMsgCell = row.getCell(lastColumnNo + 3);
					if (null == errorMsgCell) {
						errorMsgCell = row.createCell(lastColumnNo + 3);
					}
					errorMsgCell.setCellValue(errorMsg);
					errorMsgCell.setCellStyle(cellstyle);
					//
					String isImport = (String)map.get("是否导入成功");
					Cell IsImportCell = row.getCell(lastColumnNo + 2);
					if (null == IsImportCell) {
						IsImportCell = row.createCell(lastColumnNo + 2);
					}
					IsImportCell.setCellValue(isImport);
					IsImportCell.setCellStyle(cellstyle);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			new BusinessException("回写Excel数据出错");
		} finally {
			FileOutputStream out = null;
			try {
				out = new FileOutputStream(file);
				workbook.write(out);
			} catch (IOException e) {
				e.printStackTrace();
				new BusinessException("回写Excel数据出错,请确认是否已打开该EXCEL");
			} finally {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
					new BusinessException("关闭Excel数据流出错");
				}
			}
		}
		
		System.out.println(123);
		
	}

	// --------------------------------------------------------------------------------------------

	/**
	 * 字符串转换为Date类型数据（限定格式      YYYY-MM-DD hh:mm:ss）或（YYYY/MM/DD hh:mm:ss）
	 * 
	 * @param cellValue : 字符串类型的日期数据
	 * @return
	 */
	public static Date stringToDate(String cellValue) {
		if (cellValue.length() > 19)
			cellValue = cellValue.substring(0, 19);
		Calendar calendar = Calendar.getInstance();
		String[] dateStr = cellValue.split(" +");
		String[] dateInfo = dateStr[0].split("-");
		if (dateInfo.length != 3) {
			dateInfo = dateStr[0].split("/");	// 让  yyyy/mm/dd 的格式也支持
		}
		if (dateInfo.length == 3) {
			int year = Integer.parseInt(dateInfo[0]);
			int month = Integer.parseInt(dateInfo[1])-1;	// 0~11
			int day = Integer.parseInt(dateInfo[2]);
			calendar.set(year, month, day);
		} else {
			return null;	// 格式不正确
		}
		if (dateStr.length > 1) {// 有时间（限定格式     hh:mm:ss）
			String[] timeStr = dateStr[1].split(":");
			if (timeStr.length == 3) {
				int hour = Integer.parseInt(timeStr[0]);
				int minute = Integer.parseInt(timeStr[1]);
				int second = Integer.parseInt(timeStr[2]);
				calendar.set(Calendar.HOUR_OF_DAY, hour);
				calendar.set(Calendar.MINUTE, minute);
				calendar.set(Calendar.SECOND, second);
			} else {
				return null;	// 格式不正确
			}
		}
		return calendar.getTime();
	}

	// --------------------------------------------------------------------------------------------
	public static ExcelUtil makeExcelUtil() {
	       return (new ExcelUtil());
	}

}
