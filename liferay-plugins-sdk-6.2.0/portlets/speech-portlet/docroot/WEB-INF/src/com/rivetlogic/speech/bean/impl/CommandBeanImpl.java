package com.rivetlogic.speech.bean.impl;

import com.rivetlogic.speech.bean.CommandBean;

public class CommandBeanImpl implements CommandBean {
	protected String commandKey;
	protected String commandValue;
	public CommandBeanImpl() {
		
	}
	
	public CommandBeanImpl(String commandKey, String commandValue) {
		super();
		this.commandKey = commandKey;
		this.commandValue = commandValue;
	}

	/**
	 * @return the commandKey
	 */
	public String getCommandKey() {
		return commandKey;
	}
	/**
	 * @param commandKey the commandKey to set
	 */
	public void setCommandKey(String commandKey) {
		this.commandKey = commandKey;
	}
	/**
	 * @return the commandValue
	 */
	public String getCommandValue() {
		return commandValue;
	}
	/**
	 * @param commandValue the commandValue to set
	 */
	public void setCommandValue(String commandValue) {
		this.commandValue = commandValue;
	}
}
