package org.openmrs.module.msfcore.aop;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.openmrs.PatientProgram;
import org.openmrs.api.context.Context;
import org.openmrs.module.msfcore.api.MSFCoreService;
import org.springframework.aop.MethodBeforeAdvice;

public class SavePatientProgramAdvice implements MethodBeforeAdvice {

    private boolean fromSaveHtmlFormAdvice() {
        for (StackTraceElement element : Arrays.asList(Thread.currentThread().getStackTrace())) {
            if (element.getClassName().endsWith("SaveEncounterAdvice")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        if (method.getName().equals("savePatientProgram") && !fromSaveHtmlFormAdvice()) {
            PatientProgram patientProgram = (PatientProgram) args[0];
            if (patientProgram != null && patientProgram.getStates().isEmpty()) {
                patientProgram = Context.getService(MSFCoreService.class).generatePatientProgram(true,
                                Context.getService(MSFCoreService.class).getMsfStages(), patientProgram, null);
                args[0] = patientProgram;
            }
        }
    }
}
