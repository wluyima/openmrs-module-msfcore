jQuery(document).ready(function() {

    // Patient Search widget handling
    if (jQuery('#patient-search-form').length == 1) {
        // add a header section just above the form
        // Hide the register patient link
        jQuery('#register-patient-link').hide();
        // remove the page heading
        jQuery('#patient-search-form').prevAll('h2').remove();

        // change the text on the register patient link
        jQuery('#patient-search-register-patient').html('+ ADD NEW PATIENT');
        var content = '<div id="page-header"><h2>Search for Patient Record</h2> ' +
            '               <a class="button" id="find-patient-back" href="#" onclick="window.history.go(-1); return false;">< Back</a> '
                            + jQuery('#patient-search-register-patient')[0].outerHTML + '</div>'

        // add the patient header contents
        jQuery('#patient-search-form').before(content);
    }
});

function disableFn(group){
    jq(group).addClass('hidden');
    group.children("#disabled").fadeTo(250,0.33);
    group.children("#disabled").find(":checkbox").attr("checked",false); //uncheck
    group.children("#disabled").find("input[type$='text']").val("");
    group.children("#disabled").find("input").attr("disabled",true);  //disable
}
function enableFn(group){
    jq(group).removeClass('hidden');
    group.children("#disabled").fadeTo(250,1);
    group.children("#disabled").find("input").attr("disabled",false);
}