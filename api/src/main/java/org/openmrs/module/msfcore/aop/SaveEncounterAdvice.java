package org.openmrs.module.msfcore.aop;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.openmrs.Encounter;
import org.openmrs.Patient;
import org.openmrs.PatientProgram;
import org.openmrs.Program;
import org.openmrs.api.context.Context;
import org.openmrs.module.msfcore.MSFCoreConfig;
import org.openmrs.module.msfcore.api.MSFCoreService;
import org.springframework.aop.AfterReturningAdvice;

public class SaveEncounterAdvice implements AfterReturningAdvice {

    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        if (method.getName().equals("saveEncounter") && args[0] != null && returnValue instanceof Encounter
                        && requestFromFormEntrySession()) {
            Encounter encounter = (Encounter) returnValue;
            Patient patient = encounter.getPatient();
            Program ncdPrgram = Context.getProgramWorkflowService().getProgramByUuid(MSFCoreConfig.NCD_PROGRAM_UUID);
            PatientProgram patientProgram = null;

            for (PatientProgram pp : Context.getProgramWorkflowService().getPatientPrograms(patient, ncdPrgram, null, null, null, null,
                            false)) {
                if (pp.getPatient().equals(patient)) {
                    patientProgram = pp;
                }
            }
            if (patientProgram != null) {
                patientProgram = Context.getService(MSFCoreService.class).generatePatientProgram(false,
                                Context.getService(MSFCoreService.class).getMsfStages(), patientProgram, encounter);
                if (patientProgram != null) {
                    Context.getProgramWorkflowService().savePatientProgram(patientProgram);
                }
            }
        }
    }

    private boolean requestFromFormEntrySession() {
        for (StackTraceElement element : Arrays.asList(Thread.currentThread().getStackTrace())) {
            if (element.getClassName().endsWith("FormEntrySession")) {
                return true;
            }
        }
        return false;
    }

}
