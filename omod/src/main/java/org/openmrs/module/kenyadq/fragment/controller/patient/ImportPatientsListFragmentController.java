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
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Location;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.PatientIdentifierType;
import org.openmrs.PersonAddress;
import org.openmrs.PersonAttribute;
import org.openmrs.PersonName;
import org.openmrs.api.EncounterService;
import org.openmrs.api.IdentifierNotUniqueException;
import org.openmrs.api.context.Context;
//import org.openmrs.module.idgen.service.IdentifierSourceService;
import org.openmrs.module.kenyacore.form.FormManager;
import org.openmrs.module.kenyadq.DqConstants;
import org.openmrs.module.kenyadq.api.KenyaDqService;
//import org.openmrs.module.kenyaemr.Dictionary;
//import org.openmrs.module.kenyaemr.api.KenyaEmrService;
//import org.openmrs.module.kenyaemr.metadata.CommonMetadata;
//import org.openmrs.module.kenyaemr.wrapper.PatientWrapper;
import org.openmrs.module.kenyaui.KenyaUiUtils;
import org.openmrs.module.kenyaui.annotation.AppAction;
import org.openmrs.module.kenyaui.form.ValidatingCommandObject;
//import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.annotation.BindParams;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.annotation.MethodParam;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.fragment.FragmentActionRequest;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.ui.framework.fragment.action.FailureResult;
import org.openmrs.ui.framework.page.PageModel;
import org.openmrs.ui.framework.resource.ResourceFactory;
import org.openmrs.util.OpenmrsConstants;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpSession;
/**
 * Merge patients form fragment
 */
public class ImportPatientsListFragmentController {

	protected static final Log log = LogFactory.getLog(ImportPatientsListFragmentController.class);

	public void controller(
			@RequestParam(required = false, value = "returnUrl") String returnUrl,
			PageModel model) {
		model.addAttribute("returnUrl", returnUrl);
	}

	public SimpleObject submit(HttpServletRequest request) throws Exception {

		Enumeration params = request.getParameterNames();
		while (params.hasMoreElements()) {
			String paramName = (String) params.nextElement();
			// System.out.println("Parameter Name - " + paramName + ", Value - "
			// + request.getParameter(paramName));
		}

		FileInputStream inputStream = new FileInputStream(new File(
				"C:/Users/Sagar/Downloads/Legacy_data.xlsm"));
/*
		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet firstSheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = firstSheet.iterator();
		int rowCount = 0;

		while (iterator.hasNext()) {
			Row nextRow = iterator.next();
			Iterator<Cell> cellIterator = nextRow.cellIterator();
			if (rowCount > 0) {
				ArrayList<String> legacyData = new ArrayList<String>();

				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_STRING:
						legacyData.add(cell.getColumnIndex(),
								cell.getStringCellValue());
						break;
					case Cell.CELL_TYPE_NUMERIC:
						if (HSSFDateUtil.isCellDateFormatted(cell)) {
							legacyData.add(cell.getColumnIndex(),
									String.valueOf(cell.getDateCellValue()));
						} else {

							legacyData.add(cell.getColumnIndex(),
									NumberToTextConverter.toText(cell
											.getNumericCellValue()));
						}
						break;
					default:
						legacyData.add(cell.getColumnIndex(), "");
						break;
					}
				}
				int i = 0;
				for (String s : legacyData) {
					System.out.println(s + "-" + i);
					i++;
				}

				if (!legacyData.get(0).equals("")) {
					Patient toSave = new Patient(); // Creating a new patient
													// and
					// person
					PersonName personName = new PersonName();
					PersonAddress personAddress = new PersonAddress();
					Location location;

					SimpleDateFormat formatter = new SimpleDateFormat(
							"E MMM dd HH:mm:ss Z yyyy");
					Date date = new Date();
					try {
						date = (Date) formatter.parse(legacyData.get(2));

					} catch (ParseException e) {
						e.printStackTrace();
					}

					toSave.setGender(legacyData.get(3));
					toSave.setBirthdate(date);
					toSave.setBirthdateEstimated(false);
					toSave.setDead(false);
					//
					// toSave.setDeathDate(deathDate);
					// toSave.setCauseOfDeath(dead ? Dictionary
					// .getConcept(CAUSE_OF_DEATH_PLACEHOLDER) : null);
					 
					if (legacyData.get(1) != "") {
						personName.setGivenName(legacyData.get(1));
						personName.setFamilyName("(NULL)");
						toSave.addName(personName);
					}

					// toSave.
					personAddress.setAddress1(legacyData.get(9));
					toSave.addAddress(personAddress);

					PatientWrapper wrapper = new PatientWrapper(toSave);

					wrapper.getPerson().setTelephoneContact(legacyData.get(8));

					location = Context.getService(KenyaEmrService.class)
							.getDefaultLocation();

					if (!legacyData.get(4).equals("")) {
						wrapper.setPreArtRegistrationNumber(legacyData.get(4),
								location);
					}

					if (!legacyData.get(5).equals("")) {
						wrapper.setArtRegistrationNumber(legacyData.get(5),
								location);
					}

					if (!legacyData.get(6).equals("")) {
						wrapper.setNapArtRegistrationNumber(legacyData.get(5),
								location);
					}

					// Algorithm to generate system generated patient Identifier
					Calendar now = Calendar.getInstance();
					String shortName = Context
							.getAdministrationService()
							.getGlobalProperty(
									OpenmrsConstants.GLOBAL_PROPERTY_PATIENT_IDENTIFIER_PREFIX);

					String noCheck = shortName
							+ String.valueOf(now.get(Calendar.YEAR)).substring(
									2, 4)
							+ String.valueOf(now.get(Calendar.MONTH) + 1)
							+ String.valueOf(now.get(Calendar.DATE))

							+ String.valueOf(now.get(Calendar.HOUR))
							+ String.valueOf(now.get(Calendar.MINUTE))
							+ String.valueOf(now.get(Calendar.SECOND))
							+ String.valueOf(new Random()
									.nextInt(9999 - 999 + 1));

					wrapper.setSystemPatientId(noCheck + "-"
							+ generateCheckdigit(noCheck), location);

					wrapper.setNextOfKinName(legacyData.get(11));
					wrapper.setNextOfKinContact(legacyData.get(12));

					if (!legacyData.get(13).equals("")) {
						wrapper.setPreviousHivTestStatus("Yes");
						wrapper.setPreviousHivTestPlace(legacyData.get(14));

						Date capturedTestDate = new Date();
						try {
							capturedTestDate = formatter.parse(legacyData
									.get(13));

						} catch (ParseException e) {
							e.printStackTrace();
						}

						DateFormat testDate = new SimpleDateFormat(
								"dd-MMMM-yyyy");
						wrapper.setPreviousHivTestDate(testDate
								.format(capturedTestDate));
					} else {
						wrapper.setPreviousHivTestStatus("No");
					}

					wrapper.setPreviousClinicName(legacyData.get(16));

					// Make sure everyone gets an OpenMRS ID
					PatientIdentifierType openmrsIdType = MetadataUtils
							.existing(
									PatientIdentifierType.class,
									CommonMetadata._PatientIdentifierType.OPENMRS_ID);
					PatientIdentifier openmrsId = toSave
							.getPatientIdentifier(openmrsIdType);

					if (openmrsId == null) {
						String generated = Context.getService(
								IdentifierSourceService.class)
								.generateIdentifier(openmrsIdType,
										"Registration");
						openmrsId = new PatientIdentifier(generated,
								openmrsIdType, location);
						toSave.addIdentifier(openmrsId);

						if (!toSave.getPatientIdentifier().isPreferred()) {
							openmrsId.setPreferred(true);
						}
					}

					Patient ret = Context.getPatientService().savePatient(
							toSave);
					System.out
							.println("###########################################################---"
									+ ret.getPatientId());
					// Explicitly save all identifier objects including voided
					for (PatientIdentifier identifier : toSave.getIdentifiers()) {
						Context.getPatientService().savePatientIdentifier(
								identifier);
					}

					// Save remaining fields as obs
					List<Obs> obsToSave = new ArrayList<Obs>();
					List<Obs> obsToVoid = new ArrayList<Obs>();

					// With value text and Date
					if (!legacyData.get(15).equals("")) {

						Date dateTransfer = null;
						if(!legacyData.get(17).equals("")){
							try {
								dateTransfer = formatter.parse(legacyData.get(17));

							} catch (ParseException e) {
								e.printStackTrace();
							}
						}

						Concept enrollementConcept = Context
								.getConceptService().getConcept(
										Integer.parseInt(legacyData.get(15)));
						handleOncePerPatientObs(
								ret,
								obsToSave,
								obsToVoid,
								Dictionary
										.getConcept(Dictionary.METHOD_OF_ENROLLMENT),
								enrollementConcept, "", dateTransfer);
					}

					Concept ingoConcept = Context.getConceptService()
							.getConcept(Integer.parseInt(legacyData.get(7)));
					if (!legacyData.get(6).equals("")) {
						handleOncePerPatientObs(ret, obsToSave, obsToVoid,
								Dictionary.getConcept(Dictionary.INGO_NAME),
								ingoConcept, "", null);
					}
					
					for (Obs o : obsToVoid) {
						Context.getObsService().voidObs(o, "KenyaEMR edit patient");
					}

					for (Obs o : obsToSave) {
						Context.getObsService().saveObs(o, "KenyaEMR edit patient");
					}


				}
			}
			rowCount++;
		}

		inputStream.close();*/
		return SimpleObject.create("success", true);

	}

	private static int generateCheckdigit(String input) {
		int factor = 2;
		int sum = 0;
		int n = 10;
		int length = input.length();

		if (!input.matches("[\\w]+"))
			throw new RuntimeException("Invalid character in patient id: "
					+ input);
		// Work from right to left
		for (int i = length - 1; i >= 0; i--) {
			int codePoint = input.charAt(i) - 48;
			// slight openmrs peculiarity to Luhn's algorithm
			int accum = factor * codePoint - (factor - 1)
					* (int) (codePoint / 5) * 9;

			// Alternate the "factor"
			factor = (factor == 2) ? 1 : 2;

			sum += accum;
		}

		int remainder = sum % n;
		return (n - remainder) % n;
	}

	protected void handleOncePerPatientObs(Patient patient,
			List<Obs> obsToSave, List<Obs> obsToVoid, Concept question,
			Concept newValue, String textValue, Date textDate) {
		if (!newValue.equals("")) {
			Obs o = new Obs();
			o.setPerson(patient);
			o.setConcept(question);
			o.setObsDatetime(new Date());
//			o.setLocation(Context.getService(KenyaEmrService.class).getDefaultLocation());

			o.setValueCoded(newValue);

			if (textValue != null && !textValue.equals("")) {
				o.setValueText(textValue);
			}
			if (textDate != null && !textDate.equals("")) {
				o.setValueDate(textDate);
			}
			obsToSave.add(o);
		}
	}
}