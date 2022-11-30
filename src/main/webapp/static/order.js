function getProductUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content");
	return baseUrl + "/api/order";
}

function getList(){

    console.log("Getting  List");
	var url = getProductUrl();

	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	        console.log(data);
	   		displayList(data);
	   },
	   error: handleAjaxError
	});
}

function displayList(data){

	var $tbody = $('#order-table').find('tbody');

    console.log(data);

	$tbody.empty();
	for(var i in data){
		var e = data[i];
		var buttonHtml = '<button onclick="getOrderDetails(' + e.id + ')">View Details</button>';

		var row = '<tr>'
		+ '<td>' + e.id + '</td>'
		+ '<td>' + e.date + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';

        $tbody.append(row);
	}
}

function getOrderDetails(id){
	var url = getProductUrl() + "/" + id;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {

	   		displayOrderDetails(data);
	   },
	   error: handleAjaxError
	});
}

function displayOrderDetails(data){
	var $tbody = $('#order-detail-table').find('tbody');

        console.log(data);
         var totalPrice = 0;
    	$tbody.empty();
    	for(var i in data){
    		var e = data[i];
    		var sno = ++i;
    		var row = '<tr>'
    		+ '<td>' + sno+ '</td>'
    		+ '<td>' + e.productId + '</td>'
    		+ '<td>' + e.quantity + '</td>'
    		+ '<td>' + e.price + '</td>'
    		+ '<td>' + e.price*e.quantity + '</td>'
    		+ '</tr>';
            totalPrice += e.price*e.quantity;
            $tbody.append(row);
    	}

        var second = document.getElementById("total-amount");
        second.innerHTML = "Total Amount : " + totalPrice  +" Ruppees";
	$('#view-orderDetails-modal').modal('toggle');
}

function init(){
    $('#add-Product').click(addProduct);
    $('#update-Product').click(updateProduct);
    $('#refresh-data').click(getList);
    $('#cross').click(getList);
    $('#upload-data').click(displayUploadData);
    $('#process-data').click(processData);
    $('#download-errors').click(downloadErrors);
    $('#productFile').on('change', updateFileName);

}
$(document).ready(init);
$(document).ready(getList);