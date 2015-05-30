/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */

package org.openmrs.module.myanmardq.page.controller.dataqual;

import org.openmrs.Program;
import org.openmrs.module.appframework.domain.AppDescriptor;
import org.openmrs.module.myanmarcore.program.ProgramDescriptor;
import org.openmrs.module.myanmarcore.program.ProgramManager;
import org.openmrs.module.myanmarcore.report.ReportDescriptor;
import org.openmrs.module.myanmarcore.report.ReportManager;
import org.openmrs.module.myanmardq.DqConstants;
import org.openmrs.module.myanmarui.KenyaUiUtils;
import org.openmrs.module.myanmarui.annotation.AppPage;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.page.PageModel;
import org.openmrs.ui.framework.page.PageRequest;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Data quality home page
 */
@AppPage(DqConstants.APP_DATAQUALITY)
public class DataQualityHomePageController {

	public void controller(PageModel model,
						   PageRequest pageRequest,
						   UiUtils ui,
						   @SpringBean ReportManager reportManager,
						   @SpringBean ProgramManager programManager,
						   @SpringBean KenyaUiUtils kenyaUi) {

		AppDescriptor currentApp = kenyaUi.getCurrentApp(pageRequest);

		List<ReportDescriptor> commonReports = reportManager.getCommonReports(currentApp);

		Map<String, SimpleObject[]> programReports = new LinkedHashMap<String, SimpleObject[]>();

		for (ProgramDescriptor programDescriptor : programManager.getAllProgramDescriptors()) {
			Program program = programDescriptor.getTarget();
			List<ReportDescriptor> reports = reportManager.getProgramReports(currentApp, program);

			if (reports.size() > 0) {
				programReports.put(program.getName(), ui.simplifyCollection(reports));
			}
		}

		model.addAttribute("commonReports", ui.simplifyCollection(commonReports));
		model.addAttribute("programReports", programReports);
	}
}