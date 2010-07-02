$(document).ready(function() {
	$("a.addOption").click(addOption);
});

$(document).ready(function() {
	$("a.removeOption").click(removeOption);
});

function addOption(e) {
	var index = $("fieldset#options ol li").size();
	var newOption = $("fieldset#options ol li:last").clone();
	newOption.children("input").attr("id", "options_"+index).attr("value", "");
	newOption.children("a.removeOption").click(removeOption);
	$("fieldset#options ol").append(newOption);
	return false;
}

function removeOption(e) {
	$(e.target).parent("li").remove();
	return false;
}
