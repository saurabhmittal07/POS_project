
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

    // toLowerCase
    json = JSON.parse(json);
    json["barcode"] = json["barcode"].trim().toLowerCase();
    json = JSON.stringify(json);

	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },
	   success: function(response) {
            appendList(json,response);

	   },
	   error: handleAjaxError
	});

	return false;
}
function appendList(json,data){

    json1 = JSON.parse(json);

    json1["price"] = JSON.stringify(data);
    json1 = JSON.stringify(json1);

    //Check if barcode already added
    json2 = JSON.parse(json1);
    for(let i =0;i<list.length;i++){
        json3 = JSON.parse(list[i]);
        if(json2["barcode"] == json3["barcode"]){
            json3["quantity"]  = +json3["quantity"] + +json2["quantity"];
            list[i] = JSON.stringify(json3);
            localStorage.setItem("mylist" , list);
            displayList();
            return false;
        }
    }

    list[count] = json1;
    count++;

    localStorage.setItem("mylist" , list);
    displayList();
}

function createOrder(event){


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

function updateOrder(event){
	$('#edit-order-modal').modal('toggle');

	var $form = $("#order-edit-form");
    var json = toJson($form);
	var url = getOrderUrl() + "/updateInventory";


	$.ajax({
	   url: url,
	   type: 'PUT',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },
	   success: function(response) {
	        updateList(json);

	   },
	   error: handleAjaxError
	});

	return false;
}
function updateList(data){
    index = JSON.parse(data).id;
    newRow = JSON.parse(list[index]);
    newRow.quantity = JSON.parse(data).quantity;
    list[index] = JSON.stringify(newRow);
    displayList();
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



//UI DISPLAY METHODS
function displayList(){
    var $tbody = $('#order-table').find('tbody');
    $tbody.empty();
    totalPrice = 0;


    mylist    = localStorage.getItem("mylist");

    for(var i in list){

        const e = JSON.parse(list[i]);
        j = i;
        j++;
          var buttonHtml = '<button onclick="deleteProduct(' + j+ ')">delete</button>';
          buttonHtml += ' <button onclick="displayEditProduct(' + j + ')">Edit</button>';


         var row = '<tr>'
            + '<td>' + count + '</td>'
            + '<td>' + e["barcode"]+ '</td>'
            + '<td>' + e["quantity"] + '</td>'
            + '<td>' + e["price"] + '</td>'
            + '<td>' + e["quantity"]*e["price"] + '</td>'
            + '<td>' + buttonHtml + '</td>'
            + '</tr>';

        $tbody.append(row);

        totalPrice += e["quantity"]*e["price"];
         var second = document.getElementById("total-amount");
        second.innerHTML = "Total Amount : " + totalPrice  +" Ruppees";
    }
}


function displayEditProduct(id){

    data = list[id-1];
	displayProduct(data,id-1);
}



function displayProduct(data,id){
	$("#order-edit-form input[name=barcode]").val(JSON.parse(data).barcode);
	$("#order-edit-form input[name=quantity]").val(JSON.parse(data).quantity);
	$("#order-edit-form input[name=id]").val(id);
	$("#order-edit-form input[name=preQuantity]").val(JSON.parse(data).quantity);
	$('#edit-order-modal').modal('toggle');
}

//INITIALIZATION CODE
function init(){
    $('#check-inventory').click(checkInventory);
    $('#create-order').click(createOrder);
    $('#update-order-item').click(updateOrder);

}

$(document).ready(init);
$(document).ready(displayList);

