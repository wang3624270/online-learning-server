package org.octopus.reportdog.handler;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.octopus.reportdog.constants.ReportdogConstants;
import org.octopus.reportdog.form.DefaultDataForm;
import org.octopus.reportdog.module.PageConfig;
import org.octopus.reportdog.module.SourceDataConfig;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserDefinedHandler implements SourceHandlerI {

	private JdbcTemplate jdbcTemplate;

	public void process(RmiRequest request, RmiResponse response) {
		// TODO Auto-generated method stub
		DefaultDataForm form = (DefaultDataForm) request
				.get(ReportdogConstants.SOURCE_FORM_KEY);
		SourceDataConfig sourceDataConfig = (SourceDataConfig) request
				.get(ReportdogConstants.SOURCE_DATA_CONFIG_KEY);
		PageConfig pageConfig = (PageConfig) request
				.get(ReportdogConstants.PANEL_PAGE_MODEL_CONFIG_KEY);

		DefaultDataForm midForm = new DefaultDataForm();

		String currentPage = pageConfig.getProperty("currentPage");
		if (currentPage != null) {
			midForm
					.set("currentPage", (Integer.parseInt(currentPage) + 1)
							+ "");
		}
		Date now = Calendar.getInstance().getTime();
		String now_str = new SimpleDateFormat("yyyy/MM/dd").format(now);
		midForm.set("makeDate", now_str);

		form.set(sourceDataConfig.getName(), midForm);

	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}
