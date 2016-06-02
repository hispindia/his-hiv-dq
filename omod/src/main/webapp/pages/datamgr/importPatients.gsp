<%
	ui.decorateWith("kenyaemr", "standardPage", [ layout: "sidebar" ])

	def menuItems = [
			[ label: "Import Legacy Data", iconProvider: "kenyadq", icon: "buttons/patient_merge.png", href: ui.pageLink("kenyadq", "datamgr/importPatients", [ returnUrl: ui.thisUrl() ]) ],
			[ label: "Back to home", iconProvider: "kenyaui", icon: "buttons/back.png", label: "Back to home", href: ui.pageLink("kenyadq", "datamgr/dataManagerHome") ]
	]
%>
<div class="ke-page-sidebar">
	${ ui.includeFragment("kenyaui", "widget/panelMenu", [ heading: "Tasks", items: menuItems ]) }
</div>


<div class="ke-page-content">
		${ ui.includeFragment("kenyadq", "patient/importPatientsList") }
</div>