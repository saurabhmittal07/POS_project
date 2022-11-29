
function getOrderUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content");
	return baseUrl + "/api/order";
}

var count = 0;
var list = [];
//BUTTON ACTIONS
function checkInventory(event){
	//Set the values to update
	var $form = $("#order-form");
	var json = toJson($form);
	var url = getOrderUrl() + "/inventoryExist";
    console.log(json);

	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },
	   success: function(response) {
	        list[count] = json;
	        count++;
	   		displayList(json,response);
	   },
	   error: handleAjaxError
	});

	return false;
}

function createOrder(event){
    //list = JSON.parse(JSON.stringify(list));
    console.log(list);
    newarr=[]
    for(var i in list){
        newarr.push(JSON.parse(list[i]))
    }
    newarr = JSON.stringify(newarr);
    var url = getOrderUrl() ;
    $.ajax({
    	   url: url,
    	   type: 'POST',
    	   data: newarr,
    	   headers: {
           	'Content-Type': 'application/json'
           },
    	   success: function(response) {
                console.log("Order placed");
    	   },
    	   error: handleAjaxError
    	});
}

function updateProduct(event){
	$('#edit-product-modal').modal('toggle');
	//Get the ID
	var id = $("#product-edit-form input[name=id]").val();
	var url = getOrderUrl() + "/" + id;

	//Set the values to update
	var $form = $("#product-edit-form");
	var json = toJson($form);

	console.log(json);

	$.ajax({
	   url: url,
	   type: 'PUT',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },
	   success: function(response) {
	   		getList();
	   },
	   error: handleAjaxError
	});

	return false;
}


function deleteProduct(id){
	var url = getOrderUrl() + "/" + id;


	$.ajax({
	   url: url,
	   type: 'DELETE',
	   success: function(data) {
	   		getList();
	   },
	   error: handleAjaxError
	});
}

// FILE UPLOAD METHODS
var fileData = [];
var errorData = [];
var processCount = 0;


function processData(){
	var file = $('#productFile')[0].files[0];
	readFileData(file, readFileDataCallback);
}

function readFileDataCallback(results){
	fileData = results.data;
	uploadRows();
}

function uploadRows(){
	//Update progress
	updateUploadDialog();
	//If everything processed then return
	if(processCount==fileData.length){
	    getList();
		return;
	}

	//Process next row
	var row = fileData[processCount];
	processCount++;

	var json = JSON.stringify(row);
	var url = getOrderUrl();


    console.log(json);

	//Make ajax call
	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },
	   success: function(response) {
	   		uploadRows();
	   },
	   error: function(response){
	   		row.error=response.responseText
	   		errorData.push(row);
	   		uploadRows();
	   }
	});

}

function downloadErrors(){
	writeFileData(errorData);
}

//UI DISPLAY METHODS

function displayList(data, response){

	var $tbody = $('#order-table').find('tbody');

    const e = JSON.parse(data);

    var buttonHtml = '<button onclick="deleteProduct(' + e.id + ')">delete</button>'
    buttonHtml += ' <button onclick="displayEditProduct(' + e.id + ')">edit</button>'

    console.log(e["quantity"]);
    var row = '<tr>'
    + '<td>' + count + '</td>'
    + '<td>' + e["barcode"]+ '</td>'
    + '<td>' + e["quantity"] + '</td>'
    + '<td>' + response + '</td>'
    + '<td>' + e["quantity"]*response + '</td>'
    + '<td>' + buttonHtml + '</td>'
    + '</tr>';

    $tbody.append(row);

}

function displayEditProduct(id){
	var url = getOrderUrl() + "/" + id;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayProduct(data);
	   },
	   error: handleAjaxError
	});
}

function resetUploadDialog(){
	//Reset file name
	var $file = $('#productFile');
	$file.val('');
	$('#productFileName').html("Choose File");
	//Reset various counts
	processCount = 0;
	fileData = [];
	errorData = [];
	//Update counts
	updateUploadDialog();
}

function updateUploadDialog(){
	$('#rowCount').html("" + fileData.length);
	$('#processCount').html("" + processCount);
	$('#errorCount').html("" + errorData.length);
}

function updateFileName(){
	var $file = $('#productFile');
	var fileName = $file.val();
	$('#productFileName').html(fileName);
}

function displayUploadData(){
 	resetUploadDialog();
	$('#upload-product-modal').modal('toggle');
}

function displayProduct(data){
    console.log(data);
	$("#product-edit-form input[name=name]").val(data.name);
	$("#product-edit-form input[name=brandCategory]").val(data.brandCategory);
	$("#product-edit-form input[name=barcode]").val(data.barcode);
	$("#product-edit-form input[name=mrp]").val(data.mrp);
	$('#edit-product-modal').modal('toggle');
}


//INITIALIZATION CODE
function init(){
    $('#check-inventory').click(checkInventory);
    $('#create-order').click(createOrder);
    $('#update-Product').click(updateProduct);
    //$('#cross').click(getList);

    $('#process-data').click(processData);


}

$(document).ready(init);


