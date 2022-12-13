
function getReportUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content");
	return baseUrl + "/api/report";
}
var inventoryReportList = [];
var brandReportList = [];
var reportList = [];
function inventoryReport(){

	var url = getReportUrl() + "/" + "inventory";
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		inventoryReportList = data;
	   		downloadInventoryReport();
	   },
	   error: handleAjaxError
	});
}

function brandReport(){

	var url = getReportUrl() + "/" + "brand";
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		brandReportList = data;
	   		downloadBrandReport();
	   },
	   error: handleAjaxError
	});
}

function getReport(event){
    var $form = $("#filter-form");
	var json = toJson($form);
	var url = getReportUrl();


	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },
	   success: function(response) {
	   		reportList = response;
	   		downloadReport();
	   },
	   error: handleAjaxError
	});

	return false;
}

function downloadReport(){
    writeFileData(reportList);
}

function downloadInventoryReport(){
    writeFileData(inventoryReportList);
}

function downloadBrandReport(){
    writeFileData(brandReportList);
}
function myFunction() {
  var x = document.getElementById("filters");
  if (x.style.display === "none") {
    x.style.display = "block";
  } else {
    x.style.display = "none";
  }
}
//INITIALIZATION CODE
function init(){
    $('#inventory-report').click(inventoryReport);
    $('#brand-report').click(brandReport);
    $('#download-inventory-report').click(downloadInventoryReport);
    $('#download-brand-report').click(downloadBrandReport);
    $('#download-report').click(getReport);
    $('#revenue-report').click(myFunction);

}

$(document).ready(init);

