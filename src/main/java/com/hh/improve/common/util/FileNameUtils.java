/**
 * 
 */
package com.hh.improve.common.util;

import com.hh.improve.common.constants.WebConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Date;

/**
 * @author 011589
 *
 */
public class FileNameUtils {
	
	private static Logger LOGGER = LoggerFactory.getLogger(FileNameUtils.class);
	
	public static String getInterfaceListFileName (String path, String originalName) {
		String suffix = originalName.substring(originalName.lastIndexOf("."));
		String filename = path + File.separator + WebConstants.INTERFACE_LIST_EXCEL_FILE_NAME + new Date().getTime() + suffix;
		
		LOGGER.info("FileNameUtils.getFileName path: [" + originalName + " => " + filename + "]");
		return filename;
	}
	
	public static String getExportExcelFileName() {
		StringBuffer fileName = new StringBuffer();
		fileName.append(WebConstants.EXCEL_EXPORT_PREFIX);
		fileName.append("-").append(new Date().getTime());
		fileName.append(WebConstants.EXCEL_EXPORT_SUFFIX);
		return fileName.toString();
	}
	public static String getExportMonitorExcelFileName() {
		StringBuffer fileName = new StringBuffer();
		fileName.append(WebConstants.EXCEL_EXPORT_MONITOR_PREFIX);
		fileName.append("-").append(new Date().getTime());
		fileName.append(WebConstants.EXCEL_EXPORT_SUFFIX);
		return fileName.toString();
	}
	public static String getExportMonitorDetailExcelFileName() {
		StringBuffer fileName = new StringBuffer();
		fileName.append(WebConstants.EXCEL_EXPORT_MONITORDETAIL_PREFIX);
		fileName.append("-").append(new Date().getTime());
		fileName.append(WebConstants.EXCEL_EXPORT_SUFFIX);
		return fileName.toString();
	}
}
