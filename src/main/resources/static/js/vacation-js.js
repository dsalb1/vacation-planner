$(document).ready(function() {
    var cookieId = $.cookie('id');
    var cookieHex = $.cookie('hex');
    if(cookieId != null && cookieHex != null) {
    	$('#login').hide();
    } else {
    	$('#logout').hide();
    }
});