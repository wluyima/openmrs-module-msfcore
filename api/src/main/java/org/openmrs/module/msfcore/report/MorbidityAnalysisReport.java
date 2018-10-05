package org.openmrs.module.msfcore.report;

import java.util.ArrayList;
import java.util.List;

import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.ReportingConstants;
import org.openmrs.module.reporting.dataset.definition.SqlDataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.report.ReportDesign;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.openmrs.module.reporting.report.manager.ReportManagerUtil;
import org.openmrs.util.OpenmrsConstants;
import org.springframework.stereotype.Component;

@Component
public class MorbidityAnalysisReport extends BaseMSFReportManager {

    @Override
    public String getName() {
        return "Morbidity Analysis";
    }

    @Override
    public String getUuid() {
        return "3572e84a-c8ae-11e8-a8d5-f2801f1b9fd1";
    }

    @Override
    public String getDescription() {
        return "Count of patients diagnoses disaggregated by gender";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    @Override
    public List<Parameter> getParameters() {
        List<Parameter> parameterArrayList = new ArrayList<Parameter>();
        parameterArrayList.add(ReportingConstants.START_DATE_PARAMETER);
        parameterArrayList.add(ReportingConstants.END_DATE_PARAMETER);
        return parameterArrayList;
    }

    @Override
    public ReportDefinition constructReportDefinition() {
        ReportDefinition reportDef = new ReportDefinition();
        reportDef.setUuid(getUuid());
        reportDef.setName(getName());
        reportDef.setDescription(getDescription());
        reportDef.setParameters(getParameters());

        // Using sql is the easiest way here
        SqlDataSetDefinition sqlDataDef = new SqlDataSetDefinition();
        sqlDataDef.setUuid("3572eafc-c8ae-11e8-a8d5-f2801f1b9fd1");
        sqlDataDef.setName(getName());
        sqlDataDef.addParameters(getParameters());
        sqlDataDef.setSqlQuery(getDiagnosisByGenderSQLQuery());
        reportDef.addDataSetDefinition("diagnosesByGender", Mapped.mapStraightThrough(sqlDataDef));
        return reportDef;
    }

    @Override
    public List<ReportDesign> constructReportDesigns(ReportDefinition reportDefinition) {
        List<ReportDesign> l = new ArrayList<ReportDesign>();
        l.add(ReportManagerUtil.createExcelDesign("3572ec50-c8ae-11e8-a8d5-f2801f1b9fd1", reportDefinition));
        return l;
    }

    private String getDiagnosisByGenderSQLQuery() {
        String diagnosesListConceptId = Context.getAdministrationService().getGlobalProperty(OpenmrsConstants.GLOBAL_PROPERTY_PROBLEM_LIST);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT max(cn.name) as Diagnosis, ");
        stringBuilder.append("COUNT(CASE WHEN gender = 'M' THEN p.person_id END) AS Male, ");
        stringBuilder.append("COUNT(CASE WHEN gender = 'F' THEN p.person_id END) AS Female, ");
        stringBuilder.append("COUNT(*) as Total FROM obs o ");
        stringBuilder.append("INNER JOIN concept_name cn ON o.value_coded = cn.concept_id ");
        stringBuilder.append("INNER JOIN person p ON o.person_id = p.person_id ");
        stringBuilder.append("WHERE o.concept_id = " + diagnosesListConceptId + " ");
        stringBuilder.append("AND cn.locale='en' ");
        stringBuilder.append("AND cn.locale_preferred = '1' ");
        stringBuilder.append("AND o.obs_datetime >= :startDate ");
        stringBuilder.append("AND o.obs_datetime <= :endDate ");
        stringBuilder.append("group by o.value_coded, cn.name ");
        stringBuilder.append("order by count(*) desc;");

        return stringBuilder.toString();
    }

}
