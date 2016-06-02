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

package org.openmrs.module.kenyadq.fragment.controller.patient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Encounter;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.PersonAttribute;
import org.openmrs.PersonName;
import org.openmrs.api.EncounterService;
import org.openmrs.api.IdentifierNotUniqueException;
import org.openmrs.api.context.Context;
import org.openmrs.module.kenyacore.form.FormManager;
import org.openmrs.module.kenyadq.DqConstants;
import org.openmrs.module.kenyadq.api.KenyaDqService;
import org.openmrs.module.kenyaui.KenyaUiUtils;
import org.openmrs.module.kenyaui.annotation.AppAction;
import org.openmrs.module.kenyaui.form.ValidatingCommandObject;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.annotation.BindParams;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.annotation.MethodParam;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.fragment.FragmentActionRequest;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.ui.framework.fragment.action.FailureResult;
import org.openmrs.ui.framework.resource.ResourceFactory;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;
/**
 * Merge patients form fragment
 */
public class ImportPatientsListFragmentController {

	protected static final Log log = LogFactory.getLog(ImportPatientsListFragmentController.class);

	public void controller() {
	}

	private List<String> importStatusMsgList = new ArrayList<String>();

	public List<String> getImportStatusMsgList() {
		return importStatusMsgList;
	}

	public SimpleObject submit(
			HttpSession session, FragmentActionRequest actionRequest, 
			HttpServletRequest request)
			throws Exception {
		EncounterService encService = Context
				.getService(EncounterService.class);

		Date curDate = new Date();
		importStatusMsgList = new ArrayList<String>();
		String value =  request.getParameter("fileName");
		System.out.println("File name : " + value);
/*		String fileType = fileName.substring(fileName.indexOf('.') + 1,
				fileName.length());

		if (!fileType.equalsIgnoreCase("xls")) {
			message = "The file you are trying to import is not an excel file";

			return SimpleObject.create("success", true);
		}

		// WritableSheet sheet0 = outputReportWorkbook.getSheet( sheetNo );
		Workbook excelImportFile = Workbook.getWorkbook(file);
		int sheetNo = 0;
		Sheet sheet0 = excelImportFile.getSheet(sheetNo);
		Integer rowStart = Integer.parseInt(sheet0.getCell(6, 0).getContents());
		Integer rowEnd = Integer.parseInt(sheet0.getCell(6, 1).getContents());
		System.out.println("User  Creation Start Time is : " + new Date());
		System.out.println("Row Start : " + rowStart + " ,Row End : " + rowEnd);

		int orgunitcount = 0;
		for (int i = rowStart; i <= rowEnd; i++) {
			Integer orgUnitId = Integer.parseInt(sheet0.getCell(0, i)
					.getContents());
			// String orgUnitname = sheet0.getCell( 1, i ).getContents();
			// String orgUnitCode = sheet0.getCell( 2, i ).getContents();
			String userName = sheet0.getCell(1, i).getContents();
			String passWord = sheet0.getCell(2, i).getContents();
			Integer userRoleId = Integer.parseInt(sheet0.getCell(3, i)
					.getContents());

			importStatusMsgList.add("User Name -- " + userName + " Created"); 
		}

		excelImportFile.close();

		System.out.println("**********************************************");
		System.out.println("MULTIPLE USER CREATION IS FINISHED");
		System.out.println("Total No of User Created : -- " + orgunitcount);
		System.out.println("**********************************************");
		System.out.println("User  Creation End Time is : " + new Date());
*/
		// message += "<font color=red><strong>" + orgunitcount +
		// " : User Created .<br></font></strong>";

		
		return SimpleObject.create("success", true);
	}
}