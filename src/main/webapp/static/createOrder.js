
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

    // toLowerCase
    json = JSON.parse(json);
    json["barcode"] = json["barcode"].trim().toLowerCase();

    // check already present inventory

    var curCount = 0;
    for(var i =0;i<list.length;i++){
        a = JSON.parse(list[i]);

        if(a["barcode"] === json["barcode"]){
            curCount = +curCount + +a["quantity"];
        }
    }

    var totalReq = +curCount+ +json["quantity"];

    var url = $("meta[name=baseUrl]").attr("content") + "/api/inventory/inventoryExist" + "/"+ json["barcode"] + "/" + totalReq;
    var url1 = $("meta[name=baseUrl]").attr("content")+ "/api/product/" +json["barcode"] + "/mrp";
    json = JSON.stringify(json);

    var baseUrl = $("meta[name=baseUrl]").attr("content");

    console.log(url);
    $.ajax({
    	   url: url,
    	   type: 'GET',
    	   success: function() {
    	       console.log("URL1 : " + url1);

    	       $.ajax({
                   	   url: url1,
                   	   type: 'GET',
                   	   success: function(data) {
                   	   		appendList(json,data);
                   	   },
                   	   error: handleAjaxError
                   	});
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
            displayList();
            return false;
        }
    }

    list[count] = json1;
    count++;

    localStorage.setItem('mylist', JSON.stringify(list));
    displayList();
}

function createOrder(event){

    if(list.length == 0){
        alert("There are no items in cart");
        return false;
    }
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
    	        alert("Order placed!!. You can view details on Order History page");
                location.replace($("meta[name=baseUrl]").attr("content")+"/site/order");
    	   },
    	   error: handleAjaxError
    	});
}

function updateOrder(event){
	$('#edit-order-modal').modal('toggle');

	var $form = $("#order-edit-form");
    var json = toJson($form);
	//var url = getOrderUrl() + "/updateInventory";

    json1 = JSON.parse(json);

     var url = $("meta[name=baseUrl]").attr("content") + "/api/inventory/inventoryExist" + "/"+ json1["barcode"] + "/" +  json1["quantity"];
	$.ajax({
	   url: url,
	   type: 'GET',
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

function deleteProduct(index){
    list.splice(index, 1);
    displayList();
}


//UI DISPLAY METHODS
function displayList(){
    var $tbody = $('#order-table').find('tbody');
    $tbody.empty();
    totalPrice = 0;


    mylist    = localStorage.getItem("mylist");

    var serial = 1

    for(var i in list){

        const e = JSON.parse(list[i]);
        j = i;

          var buttonHtml = ' <button onclick="displayEditProduct(' + j + ')">Edit</button>' ;
           buttonHtml += ' <button onclick="deleteProduct(' + j + ')">Delete</button>';

        j++;
         var row = '<tr>'
            + '<td>' + serial + '</td>'
            + '<td>' + e["barcode"]+ '</td>'
            + '<td>' + e["quantity"] + '</td>'
            + '<td>' + e["price"] + ' Rs</td>'
            + '<td>' + e["quantity"]*e["price"] + ' Rs</td>'
            + '<td>' + buttonHtml + '</td>'
            + '</tr>';

        $tbody.append(row);
        serial = +serial + 1;
        totalPrice += e["quantity"]*e["price"];

    }
     var second = document.getElementById("total-amount");
     second.innerHTML = "Total Amount : " + totalPrice  +" Rs";
}


function displayEditProduct(id){

    data = list[id];
	displayProduct(data,id);
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
    localStorage.setItem("mylist" ,"");
    $('#check-inventory').click(checkInventory);
    $('#create-order').click(createOrder);
    $('#update-order-item').click(updateOrder);
}

$(document).ready(init);
$(document).ready(displayList);

