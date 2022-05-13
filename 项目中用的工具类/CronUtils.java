package com.yhl.ros.common.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.cronutils.builder.CronBuilder;
import com.cronutils.model.Cron;
import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.model.field.expression.FieldExpression;
import com.cronutils.model.field.expression.FieldExpressionFactory;
import com.cronutils.model.field.expression.On;

/**
 * 
 * @ClassName: CronUtils
 * @Package: com.yhl.ros.common.utils
 * @Description: cron表达式工具类 
 * @Date: 2020-04-15 16:34
 */
public class CronUtils {

	private static CronBuilder cronBuilder;

	static {
		cronBuilder = CronBuilder.cron(CronDefinitionBuilder.instanceDefinitionFor(CronType.QUARTZ));
	}

	public static String buildAnalysisCronExpression(Integer hour, Integer minute,String week) {
		if(StringUtils.isNotEmpty(week)) {
			String[] split = week.split(",");
			if (split.length > 0) {
				List<FieldExpression> flist = new ArrayList<>();
				for (int i = 0; i < split.length; i++) {
					On on = FieldExpressionFactory.on(Integer.parseInt(split[i]));
					flist.add(on);
				}
				Cron cron = cronBuilder.withDoW(FieldExpressionFactory.and(flist)).withYear(FieldExpressionFactory.always())
						.withDoM(FieldExpressionFactory.questionMark()).withMonth(FieldExpressionFactory.always())
						.withHour(FieldExpressionFactory.on(hour)).withMinute(FieldExpressionFactory.on(minute))
						.withSecond(FieldExpressionFactory.on(0)).instance();
				return cron.asString();
			}
		}
		Cron cron = cronBuilder.withDoW(FieldExpression.questionMark()).withYear(FieldExpressionFactory.always())
				.withDoM(FieldExpressionFactory.always()).withMonth(FieldExpressionFactory.always())
				.withHour(FieldExpressionFactory.on(hour)).withMinute(FieldExpressionFactory.on(minute))
				.withSecond(FieldExpressionFactory.on(0)).instance();
		return cron.asString();
	}

	public static void main(String[] args) {
		System.out.println(buildAnalysisCronExpression(12, 30,""));
		System.out.println(buildAnalysisCronExpression(12, 30,"1,2"));
	}


}
