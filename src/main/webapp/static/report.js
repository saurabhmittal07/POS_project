
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
	   		displayInventoryReport(data);
	   		inventoryReportList = data;
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
	   		displayBrandReport(data);
	   		brandReportList = data;
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


function displayInventoryReport(data){
    var $tbody = $('#inventory-report-table').find('tbody');
    $tbody.empty();

    for(var i in data){
        var e = data[i];
        var row = '<tr>'
            + '<td>' + e.serialNo+ '</td>'
            + '<td>' + e.brand + '</td>'
            + '<td>' + e.category + '</td>'
            + '<td>' + e.quantity + '</td>'
            + '</tr>';
        $tbody.append(row);
    }
    $('#view-inventory-report-modal').modal('toggle');
}

function displayBrandReport(data){
    var $tbody = $('#brand-report-table').find('tbody');
    $tbody.empty();

    for(var i in data){
        var e = data[i];
        var row = '<tr>'
            + '<td>' + e.id+ '</td>'
            + '<td>' + e.brand + '</td>'
            + '<td>' + e.category + '</td>'
            + '</tr>';
        $tbody.append(row);
    }
    $('#view-brand-report-modal').modal('toggle');
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
//INITIALIZATION CODE
function init(){
    $('#inventory-report').click(inventoryReport);
    $('#brand-report').click(brandReport);
    $('#download-inventory-report').click(downloadInventoryReport);
    $('#download-brand-report').click(downloadBrandReport);
    $('#download-report').click(getReport);

}

$(document).ready(init);

