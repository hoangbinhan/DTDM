window.onload = function poll(){
	document.querySelector("main").onclick = () => {
		var x = document.querySelector("nav div");
	    if (x.className != "topnav") {
	        x.className = "topnav";
	    }
	}
	xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function(){
		if(this.readyState == 4 && this.status == 200){
			var r = this.responseText;
			var main = document.querySelector("main");
			if(r.length > 5){
				var q = document.createElement("div");
				q.innerHTML = r;
				main.prepend(q);
			}
			else{
				var news = document.getElementsByClassName("news");
				for(i = 0; i < news.length; i++){
					if(news[i].id === "n" + r)
						news[i].remove();
				}
			}				
			poll();
		}
	}
	xhr.open("GET", "/pollnews", true);
	xhr.timeout = 30000;
	xhr.ontimeout = function(){
		poll();
	}
	xhr.send();
}

var cont = document.querySelectorAll(".content");
for(var i = 0; i < cont.length; i++){
	var col = 'hsl(' + 360 * Math.random() + ',' + '100%,' + '50%)';
	cont[i].style.borderLeft = "2px solid " + col;
}

function remove(btn){
    var xhttp = new XMLHttpRequest();
    var val = btn.value;   
    xhttp.open("GET", "/remove/" + val, true);
    xhttp.send(); 		
}

