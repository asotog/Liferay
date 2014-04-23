package com.rivetlogic.speech.portlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ValidatorException;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.service.PortletPreferencesServiceUtil;
import com.liferay.portal.service.persistence.PortletPreferencesUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.util.bridges.mvc.MVCPortlet;
import com.rivetlogic.speech.bean.CommandBean;
import com.rivetlogic.speech.bean.impl.CommandBeanImpl;

/**
 * Portlet implementation class SpeechPortlet
 */
public class SpeechPortlet extends MVCPortlet {

	private static final String KEY_WORD = "key_word";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.liferay.util.bridges.mvc.MVCPortlet#doView(javax.portlet.RenderRequest
	 * , javax.portlet.RenderResponse)
	 */
	@Override
	public void doView(RenderRequest request, RenderResponse response)
			throws IOException, PortletException {
		String key;
		List<CommandBean> commandBeans = new ArrayList<CommandBean>();
		
		PortletPreferences preference = request.getPreferences();
		
		Map<String, String[]> preferenceMap = preference.getMap();
		for (Map.Entry<String, String[]> entry : preferenceMap.entrySet()) {
			key = entry.getKey().trim();
			if (!key.isEmpty() && !key.equalsIgnoreCase(KEY_WORD)) {
				commandBeans.add(new CommandBeanImpl(entry.getKey(), entry.getValue()[0]));
			}
		}
		request.setAttribute("commandCount", commandBeans.size());
		request.setAttribute("commandList", commandBeans);
		super.doView(request, response);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.liferay.util.bridges.mvc.MVCPortlet#doEdit(javax.portlet.RenderRequest
	 * , javax.portlet.RenderResponse)
	 */
	@Override
	public void doEdit(RenderRequest request, RenderResponse response)
			throws IOException, PortletException {
		//PortletPreferences preference = request.getPreferences();
		//String keyWord = preference.getValue("key_word", StringPool.BLANK);
		//request.setAttribute("key_word", keyWord);
		super.doEdit(request, response);
	}

	/**
	 * Action to add new speech command and its value
	 * @param request
	 * @param response
	 * @throws PortalException
	 * @throws SystemException
	 */
	public void addCommandAction(ActionRequest request, ActionResponse response)
			throws PortalException, SystemException {
		PortletPreferences preference = request.getPreferences();
		try {
			preference.setValue("key_word", ParamUtil.getString(request, "key_word"));
			preference.setValue(ParamUtil.getString(request, "command_key"), ParamUtil.getString(request, "command_value"));
			preference.store();

			SessionMessages.add(request, "success-msg");
		} catch (ReadOnlyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ValidatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Return view page
	 * @param request
	 * @param response
	 * @throws PortalException
	 * @throws SystemException
	 * @throws PortletModeException
	 */
	public void backAction(ActionRequest request, ActionResponse response)
			throws PortalException, SystemException, PortletModeException {
		response.setPortletMode(PortletMode.VIEW);
	}
	
	public void deleteAction(ActionRequest request, ActionResponse response)
			throws PortalException, SystemException, PortletModeException {
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		long plid = themeDisplay.getLayout().getPlid();
		long ownerId = PortletKeys.PREFS_OWNER_ID_DEFAULT;
		int ownerType = PortletKeys.PREFS_OWNER_TYPE_LAYOUT;
		long companyId = themeDisplay.getCompanyId();
		String portletId = "speech_WAR_speechportlet";
		
		String commandKey = ParamUtil.get(request, "command-key", StringPool.BLANK);
		String commandValue = ParamUtil.get(request, "command-value", StringPool.BLANK);
		System.out.println("command-key:" + commandKey + " command-value: " + commandValue);
		
		PortletPreferences preference = request.getPreferences();
		Map<String, String[]> preferencesMap = new HashMap<String, String[]>(preference.getMap());
		preferencesMap.remove(commandKey);
		
		PortletPreferencesLocalServiceUtil.deletePortletPreferences(ownerId, ownerType, plid, portletId);
		
		preference = PortletPreferencesLocalServiceUtil.getPreferences(companyId, ownerId, ownerType, plid, portletId);
		try {
			for(Map.Entry<String, String[]> entry : preferencesMap.entrySet()) {
				preference.setValue(entry.getKey(), entry.getValue()[0]);
			}
			preference.store();
		} catch (ReadOnlyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ValidatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
