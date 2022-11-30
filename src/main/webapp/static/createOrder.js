
function getOrderUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content");
	return baseUrl + "/api/order";
}

var count = 0;
var list = [];
var totalPrice = 0;
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
                location.replace($("meta[name=baseUrl]").attr("content")+"/site/order");
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


//UI DISPLAY METHODS

function displayList(data, response){

	var $tbody = $('#order-table').find('tbody');


    const e = JSON.parse(data);

    var buttonHtml = '<button onclick="deleteProduct(' + e.id + ')">delete</button>'
    buttonHtml += ' <button onclick="displayEditProduct(' + count + ')">edit</button>'

    totalPrice += e["quantity"]*response;

     var second = document.getElementById("total-amount");
     second.innerHTML = "Total Amount : " + totalPrice  +" Ruppees";


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

    data = list[id-1];
	displayProduct(data);
}



function displayProduct(data){
    console.log(data);
	$("#order-edit-form input[name=barcode]").val(data.barcode);
	$("#order-edit-form input[name=quantity]").val(data.quantity);
	$("#inventory-edit-form input[name=id]").val(data.id);
	$('#edit-order-modal').modal('toggle');
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


