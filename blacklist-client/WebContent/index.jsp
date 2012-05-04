<!DOCTYPE html>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<link type="image/x-icon" rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico"/>

		<meta name="description" content="R7 Blacklist" />
		<meta name="author" content="Washington Botelho" />
		<meta name="keywords" content="r7, comentario, censura, blacklist" />

		<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/stylesheet.css"/>
		<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/js/javascript.js"></script>

		<title>R7 | Blacklist</title>
	</head>
	<body>
		<div id="loading-fade"></div>

		<div id="top">
			<div id="logo" onclick="home();"></div>
			<div id="logotipo" onclick="home();"><h1>Blacklist</h1></div>

			<div id="menu">
				<ul>
					<li><a href="http://github.com/wbotelhos/blacklist" title="Ver este projeto no Github" target="_blank">Github</a></li>
				</ul>
			</div>
		</div>

		<div id="content">
			<div id="comments-wrapper">
				<fieldset>
					<form method="get" action="${pageContext.request.contextPath}/validate">
						<textarea id="comment" name="comment" cols="60" rows="9">A minha àrm@ é para atirar no bumbum dos à toas do BBB!</textarea>
	
						<div id="info">
							<div id="result"></div>
							<span id="loading"><img src="${pageContext.request.contextPath}/img/loading.gif" width="10" height="10"/> aguarde...</span>
						</div>
	
						<input type="button" value="Comentar" onclick="save();" />
					</form>
				</fieldset>
	
		
				<table id="comments"></table>
			</div>
		</div>

		<div id="footer">
			<div id="copy">
				&copy; 2011 <a href="http://r7.com" target="_blank">r7.com</a>
			</div>
		</div>

		<script type="text/javascript">
			var	comment = document.getElementById('comment');

			function save() {
				var domain	= document.location.href.replace('http://', '').replace('https://', '').split('/')[0],
					result	= document.getElementById('result'),
					loading	= document.getElementById('loading');

				if (comment.value.replace(/^\s+|\s+$/g, '') == '') {
					showMessage('Ops! Você precisa escrever o seu comentário.', false);
					return false;
				}

				result.style.display = 'none';
				loading.style.display = 'block';

				get('http://' + domain + '/blacklist-server/check.json?comment=' + comment.value, function(json) {

					var entity = eval('(' + json + ')');

					if (entity.success == false) {
						showMessage(entity.message, entity.success);

						load(false);
						loading.style.display = 'none';

						return false;
					}

					post('${pageContext.request.contextPath}/comment.json?action=save&censured=' + entity.censured + '&original=' + entity.original + '&valid=' + entity.valid, function(json) {
						load(false);
						loading.style.display = 'none';

						var response = eval('(' + json + ')');

						showMessage(response.message, response.success);

						if (response.success) {
							loadComments();
						}

						comment.value = '';
						comment.focus();
					});
				});
			};

			function showMessage(message, isSuccess) {
				var result = document.getElementById('result');

				result.style.color = (isSuccess) ? '#7A0' : '#F55';
				result.innerHTML = message;

				result.style.display = 'block';

				setTimeout(function() {
					result.style.display = 'none';
				}, 5000);
			};

			function loadComments() {
				get('${pageContext.request.contextPath}/comment.json?action=list', function(json) {
					var response = eval('(' + json + ')');

					if (response.success == false) {
						showMessage(response.message, response.success);
						return false;
					}

					var entityList	= response.entityList,
						build		= '',
						size		= entityList.length;

					for (var i = 0; i < size; i++) {
						build += 
						'<tr id="comment-' + (size - i) + '" class="header">' +
							'<td class="description">' +
								'<a href="javascript:void(0);" class="mini-link-comment">#' + (size - i) + '</a> Frodo Baggins' + 
							'</td>' +
							'<td class="date">' + entityList[i].createdAt + '</td>' +
							'<td width="16" class="balloon"><img src="${pageContext.request.contextPath}/img/balloon.png" width="16" height="16"/></td>' +
						'</tr>' +
						'<tr>' +
							'<td colspan="3" class="text">' + 
								'<img src="${pageContext.request.contextPath}/img/avatar.png" alt="Show user" title="Show user" width="60" height="60" />' +
								'<div class="content">' + rnToBr(entityList[i].censured) + '</div>' +
							'</td>' +
						'</tr>' +
						'<tr>' +
							'<td>&nbsp;</td>' +
						'</tr>';
					}

					document.getElementById('comments').innerHTML = build;
				});
			};

			comment.focus();

			loadComments();
		</script>
	</body>
</html>
