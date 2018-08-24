<script type="text/javascript">
  jQuery(function() {
    // replace label 'Confirm' with 'Review'
    jQuery("#confirmation_label").html("${ui.message("msfcore.patient.confirm.label")}");

    // Move the cancel button to the header
    jQuery("#cancelSubmission").detach();
    jQuery("#content > h2").append('<input id="cancelSubmission" onclick="window.history.back()" type="button" value="${ui.message("coreapps.cancel")}" />')

    // replace label on 'Confirm' button with 'Submit' & remove the text 'Confirm submission?'
    jQuery("#confirmationQuestion").replaceWith(
      '<input id="submit" type="submit" class="submitButton confirm center" value="${ui.message("msfcore.patient.submit.label")}" />'
    );
    
    // add summary title
    jQuery("#dataCanvas").append('<h2>${ui.message("msfcore.patient.summary.title")}</h2>');
  });
</script>