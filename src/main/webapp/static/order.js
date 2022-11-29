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
		var buttonHtml = '<button onclick="deleteProduct(' + e.id + ')">delete</button>'
		buttonHtml += ' <button onclick="displayEditProduct(' + e.id + ')">edit</button>'



		var row = '<tr>'
		+ '<td>' + e.id + '</td>'
		+ '<td>' + e.date + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';

        $tbody.append(row);
        
	}
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