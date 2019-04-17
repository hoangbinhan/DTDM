function color(res, btn){
	 var b1 = document.getElementById("ubtn" + btn.value);
     var b2 = document.getElementById("dbtn" + btn.value);
	 switch(res) {
	    case "1":
	        b1.style.color = "white";
	        b1.style.backgroundColor = "#00ce02";
	        b2.style.color = "black";
	        b2.style.backgroundColor = "inherit";
	        break;
	    case "0":
	    	b1.style.color = "black";
	        b1.style.backgroundColor = "inherit";
	    	b2.style.color = "black";
	        b2.style.backgroundColor = "inherit";
	        break;
	    case "-1":
	    	b1.style.color = "black";
	        b1.style.backgroundColor = "inherit";
	        b2.style.color = "white";
	        b2.style.backgroundColor = "red";
	        break;
	  }
}

function upvote(btn){
    var xhttp = new XMLHttpRequest(); 
	xhttp.onreadystatechange = function() {
		    if(this.readyState == 4 && this.status == 200) {
		    	var res = this.responseText.split(",");
	        	document.getElementById("vote" + btn.value).innerHTML = res[0];
	        	color(res[1], btn);
		    }
    }
    xhttp.open("GET", "/upvote/" + btn.value, true);
    xhttp.send(); 
}

function downvote(btn){
    var xhttp = new XMLHttpRequest(); 
    xhttp.onreadystatechange = function() {
    	    if(this.readyState == 4 && this.status == 200) {
    	    	var res = this.responseText.split(",");
	        	document.getElementById("vote" + btn.value).innerHTML = res[0];
	        	color(res[1], btn);
    	    }
    }
    xhttp.open("GET", "/downvote/" + btn.value, true);
    xhttp.send(); 
}

