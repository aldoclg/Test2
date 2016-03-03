/**
 * 
 */

function zeraProgress() {
	var pfClient = document.getElementById("progress");
	document.getElementById("progress").setAttribute('style','visibility:hidden');
	
	pfClient.setValue(0);
}

function start() {	
	document.getElementById("progress").setAttribute('style','visibility:visible');		
}

window.onbeforeunload = function closeListenerJs() {	
	closeListener();
}