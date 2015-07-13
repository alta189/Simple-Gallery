package com.alta189.simple.gallery.objects;

import com.alta189.simplesave.Field;
import com.alta189.simplesave.Id;
import com.alta189.simplesave.Table;
import com.google.gson.annotations.Expose;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

@Table("validation_rules")
public class ValidationRule {
	@Id
	@Expose
	private int id;
	@Field
	@Expose
	private String rule;
	@Field
	@Expose
	private ValidationType type;

	public int getId() {
		return id;
	}

	public String getRule() {
		return rule;
	}

	public Pattern getPattern() {
		return Pattern.compile(rule);
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public ValidationType getType() {
		return type;
	}

	public void setType(ValidationType type) {
		this.type = type;
	}

	public boolean check(String s) {
		if (StringUtils.isEmpty(s) || StringUtils.isEmpty(getRule())) {
			return false;
		}

		try {
			switch (getType()) {
				case STARTS_WITH:
					return s.startsWith(getRule());
				case ENDS_WITH:
					return s.endsWith(getRule());
				case REGEX:
					return getPattern().matcher(getRule()).matches();
			}
		} catch (Exception ignored) {
		}
		return false;
	}
}
