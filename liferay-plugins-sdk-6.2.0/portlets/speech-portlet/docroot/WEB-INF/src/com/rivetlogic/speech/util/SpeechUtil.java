package com.rivetlogic.speech.util;

import java.util.ArrayList;
import java.util.List;

import com.liferay.portal.kernel.util.ListUtil;
import com.rivetlogic.speech.bean.CommandBean;

public class SpeechUtil {
	public static List<CommandBean> subList(List<CommandBean> list, Integer start, Integer end){
		if(list != null) {
			return ListUtil.subList(list, start, end);
		} else {
			return new ArrayList<CommandBean>();
		}
	}
}
