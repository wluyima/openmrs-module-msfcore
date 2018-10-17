/*
 * Print html from entire page ignoring some elements
 */
function printPageWithIgnore(elements) {
	jQuery(elements).hide();
	window.print();
	jQuery(elements).show();
}