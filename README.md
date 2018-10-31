openmrs-module-msfcore
==========================

This module contains core functionality and customisations for the OpenMRS Médecins Sans Frontières(MSF) distribution such as;

> UI Customisations

> Primary & other Patient Identifiers

> Registration App

> Location (structure) metadata

> Audit Logging

> Html forms
- [x] Exit From NCD
- [x] Lab Results
- [x] NCD Baseline Consultation
- [x] NCD followup


Adding a custom widget to dashboard
===================================
- Add an entry in apps/msfcore_dashboard_widgets_app.json
- Make changes to following properties only (starting with your.); id, description, order, config, config.widget, extensions.appId

```json
  {
    "id": "your.app.id",
    "instanceOf": "coreapps.template.dashboardWidget",
    "description": "your.app.description",
    "order": your.order.number,
    "config": {
      "widget": "your-widget-name",
      "icon": "your.icon",
      "label": "your.label.for.header",
      "your.other.property.key":"your.other.property.value"
    },
    "extensions": [
      {
        "id": "org.openmrs.module.coreapps.mostRecentVitals.clinicianDashboardSecondColumn",
        "appId": "your.app.id",
        "extensionPointId": "patientDashboard.secondColumnFragments",
        "extensionParams": {
          "provider": "msfcore",
          "fragment": "dashboardwidgets/dashboardWidget"
        }
      }
    ]
  }
```

- Add an entry in /msfcore-omod/src/main/web/dashboardwidgets/index.js as below

```
import YourWidgetName from './your-widget-name';

```

- Add your newly added widget to module dependency array

```
export default angular.module("msfcore-openmrs-contrib-dashboardwidgets", [ YourWidgetName]).name;
```

- Add a folder to /msfcore-omod/src/main/web/dashboardwidgets/ with name your-widget-name
- Add your component, controller, template and any other required features. As an example check the recenttestresults widget
- Build app and test the widget