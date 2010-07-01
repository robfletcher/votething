$(document).ready(function() {
	$("a.addOption").click(addOption);
});

$(document).ready(function() {
	$("a.removeOption").click(removeOption);
});

function addOption(e) {
	var index = $("ol#options li").size();
	var newOption = $("ol#options li:last").clone();
	newOption.children("input").attr("id", "options_"+index).attr("name", "options["+index+"]").attr("value", "");
	newOption.children("a.removeOption").click(removeOption);
	$("ol#options").append(newOption);
}

function removeOption(e) {
	$(e.target).parent("li").remove();
}
