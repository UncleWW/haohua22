package com.hh.improve.common.format;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.hh.improve.common.util.DateUtils;

import java.io.IOException;
import java.util.Date;

/**
 * 将date类型数据转换成yyyy-MM-dd 格式字符串
 */

public class DateToString extends JsonSerializer<Date> {

	@Override
	public void serialize(Date date, JsonGenerator generator, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		generator.writeString(DateUtils.formatDate(date, DateUtils.DEFAULT_DATE));
	}

}
