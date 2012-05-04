function execute(method, url, callback) {
	var request = undefined;

	load();

	try {
		request = new XMLHttpRequest(); // Firefox, Opera 8.0+, Safari e IE 7
	} catch (e) {
		try {
			request = new ActiveXObject('Msxml2.XMLHTTP'); // IE
		} catch (e) {
			try {
				request = new ActiveXObject('Microsoft.XMLHTTP'); // IE Antigo
			} catch (e) {
				alert('Seu browser não suporta esta funcionalidade!');
				return false;
			}
		}
	}

	request.onreadystatechange = function() {
		if (request.readyState == 4 && request.status == 200) {
			load(false);
			callback.call(this, request.responseText);
		}
	};

	request.open(method, url, true);
	request.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded; charset=utf-8');
	request.send(null);
};

function get(url, callback) {
	return execute('get', url, callback);
};

function load(isStart) {
	var fade = document.getElementById('loading-fade');

	if (isStart === undefined || isStart) {
		fade.style.display = 'block';
	} else {
		fade.style.display = 'none';
	}
};

function post(url, callback) {
	return execute('post', url, callback);
};

function save() {
	var comment = document.getElementById('comment').value;

	post('http://localhost:8080/blacklist-server/check?comment=' + comment, function(json) {
		var entity = eval('(' + json + ')');

		post('http://localhost:8080/blacklist-client/comment?action=save&censured=' + entity.censured + '&original=' + entity.original, function(response) {
			loadComments();
		});
	});
};

function loadComments() {
	post('http://localhost:8080/blacklist-client/comment?action=list', function(json) {
		var entityList	= eval('(' + json + ')').entityList,
			build		= '';

		for (var i = 0; i < entityList.length; i++) {
			build += 
			'<tr>' +
				'<td class="description">' +
					'<a href="javascript:void(0);" class="mini-link-comment">#' + (i + 1) + '</a> Frodo Baggins' + 
				'</td>' +
				'<td class="date">21/01/12 14:00:42</td>' +
				'<td width="16" class="balloon"><img src="${pageContext.request.contextPath}/img/balloon.png" width="16" height="16"/></td>' +
			'</tr>' +
			'<tr>' +
				'<td colspan="3" class="text">' + 
					'<img src="${pageContext.request.contextPath}/img/avatar.png" alt="Show user" title="Show user" width="60" height="60" />' +
					'<div class="content">' + entityList[i].censured + '</div>' +
				'</td>' +
			'</tr>' +
			'<tr>' +
				'<td>&nbsp;</td>' +
			'</tr>';
		}

		document.getElementById('comments').innerHTML = build;
	});
};

function rnToBr(text) {
	return text.replace(/\r\n/g, '<br/><br/>');
}

function home() {
	document.location.href = 'http://mockr.me/blacklist-client';
}