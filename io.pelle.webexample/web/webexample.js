var baseUrl = "api";
var userTable;

$(document).ready(function(){

	userTable = $('#userTable').dataTable({
		
		"bProcessing": true,
        "sAjaxSource": baseUrl + "/user/all",
		"sDom": "<'row'<'span6'l><'span6'f>r>t<'row'<'span6'i><'span6'p>>",
		"sWrapper": "dataTables_wrapper form-inline",
		"bPaginate": false,
		"bInfo": false,
		"bFilter" : false,
        "sAjaxDataProp": "",
		"aoColumns": [
            { "mData": "name" },
            { "mData": "mail" },
			{ 
				"mData": "id",
				"bSearchable": false,
				"bSortable": false,
				"bUseRendered": false, 
				"fnRender": function (oObj)                              
				{
					// oObj.aData.id
					return "<a onclick='deleteUser(" + oObj.aData.id + ");'  href='#'>Delete</a>";
				}
			}
		]
	});
	
	$( "#userForm" ).submit(function( event ) {
		
		var actionUrl;
		
		if ($('#userId').val() == 0)
		{
			actionUrl = baseUrl + "/user/add"; 
		}
		else
		{
			actionUrl = baseUrl + "/user/update"; 
		}

		$.ajax({
			type: "POST",
			url: actionUrl,
			data: { 
				"userName": $('#userName').val(),
				"userMail": $('#userMail').val(),
				"userId": $('#userId').val()
			},
			success: function(user)
			{
				$('#userName').val(user.name),
				$('#userMail').val(user.mail),
				$('#userId').val(user.id)
				userTable.fnReloadAjax();
			}
		});

		event.preventDefault();
	});

	$("#userNew").click(function(){
		emptyForm();
	});
		
	$("#userTable tbody tr").live("click", function(event){

		var user = userTable.fnGetData(this);

		$('#userName').val(user.name);
		$('#userMail').val(user.mail);
		$('#userId').val(user.id);

		if ( $(this).hasClass('row_selected') )
		{
			$(this).removeClass('row_selected');
		}
		else 
		{
			userTable.$('tr.row_selected').removeClass('row_selected');
			$(this).addClass('row_selected');
		}

	});
});

function deleteUser(id)
{
	$.ajax({
		type: "GET",
		url: baseUrl + "/user/delete/" + id,
		success: function(data)
		{
			emptyForm();
			userTable.fnReloadAjax();
		}
	});
}

function emptyForm()
{
	$('#userName').val("");
	$('#userMail').val("");
	$('#userId').val(0);
}

$.fn.dataTableExt.oApi.fnReloadAjax = function ( oSettings, sNewSource, fnCallback, bStandingRedraw )
{
    // DataTables 1.10 compatibility - if 1.10 then versionCheck exists.
    // 1.10s API has ajax reloading built in, so we use those abilities
    // directly.
    if ( $.fn.dataTable.versionCheck ) {
        var api = new $.fn.dataTable.Api( oSettings );
 
        if ( sNewSource ) {
            api.ajax.url( sNewSource ).load( fnCallback, !bStandingRedraw );
        }
        else {
            api.ajax.reload( fnCallback, !bStandingRedraw );
        }
        return;
    }
 
    if ( sNewSource !== undefined && sNewSource !== null ) {
        oSettings.sAjaxSource = sNewSource;
    }
 
    // Server-side processing should just call fnDraw
    if ( oSettings.oFeatures.bServerSide ) {
        this.fnDraw();
        return;
    }
 
    this.oApi._fnProcessingDisplay( oSettings, true );
    var that = this;
    var iStart = oSettings._iDisplayStart;
    var aData = [];
 
    this.oApi._fnServerParams( oSettings, aData );
 
    oSettings.fnServerData.call( oSettings.oInstance, oSettings.sAjaxSource, aData, function(json) {
        /* Clear the old information from the table */
        that.oApi._fnClearTable( oSettings );
 
        /* Got the data - add it to the table */
        var aData =  (oSettings.sAjaxDataProp !== "") ?
            that.oApi._fnGetObjectDataFn( oSettings.sAjaxDataProp )( json ) : json;
 
        for ( var i=0 ; i<aData.length ; i++ )
        {
            that.oApi._fnAddData( oSettings, aData[i] );
        }
         
        oSettings.aiDisplay = oSettings.aiDisplayMaster.slice();
 
        that.fnDraw();
 
        if ( bStandingRedraw === true )
        {
            oSettings._iDisplayStart = iStart;
            that.oApi._fnCalculateEnd( oSettings );
            that.fnDraw( false );
        }
 
        that.oApi._fnProcessingDisplay( oSettings, false );
 
        /* Callback user function - for event handlers etc */
        if ( typeof fnCallback == 'function' && fnCallback !== null )
        {
            fnCallback( oSettings );
        }
    }, oSettings );
};
