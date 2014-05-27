<%-- 
    Document   : index
    Created on : 11-dic-2013, 16.06.07
    Author     : Nertil & Elion
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="css/style.css" rel="stylesheet" type="text/css">
		<title>SGSUsersService</title>
	</head>
	<body>
		<h1>Descrizione web service!</h1>
		<ol/>
		<div>
			<li>
				<h2>getAccess(String appName, String Password);</h2>
				<p>
				<ul>
					<li>
						serve per permettere di accedere ai servizi solo applicazioni (i vari siti web) che siano accreditate;<h4></li>

					<li>restituisce una key che deve essere usata dalle applicazioni in tutte le successive chiamate.</li>
				</ul></p>
			</li>
		</div>	
		<div>
			<li>
				<h2>checkUser(String key, string userName; String userPassword)</h3>
					<ul>
						<li>per poter usare il servizio, l’applicazione deve prima essersi accreditata e usare le key che il servizio le ha fornito;</li>
						<li>una applicazione manda name e password di un utente (che l’applicazione avrà preso dal form che l’utente filla quando vuole loggarsi);</li>
						<li>SGSUsersService deve prendere la password, criptarla (con MD5) e controllare che l’utente esista.</li>
						<li>risponde true se trova l’utente oppure false se non lo trova;</li>
						<li>per questo devi usare la stessa tabella degli utenti del sito SGS (che diventa il punto centrale per la gestione utenti). Il sito ha una tabella utenti stile Joomla. Quindi SGSUsersService dovrà girare anche sulla stessa macchina del sito SGS.</li>
					</ul>
			</li>

		</div>
		<div>
			<li>
				<h2>getUserInfo(String key, String username)</h3>
					<ul>
						<li>per poter usare il servizio, l’applicazione deve prima essersi accreditata e usare le key che il servizio le ha fornito;</li>
						<li>una applicazione può chiedere di avere di un utente più informazioni che gli vengono restituite tramite una stringa XML. Per il momento facciamo una stringa semplice con le seguenti informazioni:
							<br>&lt;SGSuser>
							<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;User>
							<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;id>id&lt;/id>
							<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;name>name&lt;/name>
							<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;username>username&lt;/username>
							<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;email>email&lt;/email>
							<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;password>password&lt;/password>
							<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;usertype>user_type&lt;/usertype>
							<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;block>block&lt;/block>
							<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;sendEmail>sendEmail&lt;/sendEmail>
							<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;gid>gid&lt;/gid>
							<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;registerDate>registerDate&lt;/registerDate>
							<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;lastvisitDate>lastvisitDate&lt;/lastvisitDate>
							<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;activation>activation&lt;/activation>
							<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;params>params&lt;/params>
							<br>&lt;/SGSuser>
							<br>&lt;/SGSuser>
						</li>
					</ul>
			</li>

		</div>
		<div>
			<li>
				<h2>getUpdatedUsers(String key, String lastUpdateTime)</h3>
					<ul>
						<li>per poter usare il servizio, l’applicazione deve prima essersi accreditata e usare le key che il servizio le ha fornito;</li>
						<li>una applicazione può chiedere di avere la lista di tutti quei utenti che hanno aggiornato la loro form di profilo utente
							<br>&lt;SGSuser>
							<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;User>
							<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;id>id&lt;/id>
							<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;name>name&lt;/name>
							<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;username>username&lt;/username>
							<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;email>email&lt;/email>
							<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;password>password&lt;/password>
							<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;usertype>user_type&lt;/usertype>
							<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;block>block&lt;/block>
							<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;sendEmail>sendEmail&lt;/sendEmail>
							<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;gid>gid&lt;/gid>
							<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;registerDate>registerDate&lt;/registerDate>
							<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;lastvisitDate>lastvisitDate&lt;/lastvisitDate>
							<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;activation>activation&lt;/activation>
							<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;params>params&lt;/params>
							<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;/User>

							<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;.
							<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;.
							<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;.
							<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;User>
							<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;.
							<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;.
							<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;.
							<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;/User>
							<br>&lt;/SGSuser>
							<br>
							<br>
						</li>
					</ul>
			</li>

		</div>





	</ol>
</body>
</html>
