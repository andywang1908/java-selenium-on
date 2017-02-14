<!DOCTYPE html>
<html>
<head>
<style>
table {width:1000px;word-break:break-all;}
tr.error {
	background-color:red;
}
tr.success {
	background-color:#DBEEDD;
}
</style>
</head>
<body>
<table align="center">
	<tr>
		<td>case id</td>
		<td>${caseResult.id}</td>
	</tr>
	<tr>
		<td>case desc</td>
		<td>${caseResult.desc}</td>
	</tr>
	<tr class="success">
		<td>success number</td>
		<td>${successCount}</td>
	</tr>
	<tr class="error">
		<td>error number</td>
		<td>${errorCount}</td>
	</tr>
</table>

<br/>

<table align="center">
	<tr>
		<td style="width:10%">data id</td>
		<td style="width:10%">data desc</td>
		<td style="width:10%">data result</td>
		<td style="width:70%">data summary</td>
  	</tr>
  	<#list loopResultList as loopResult>
	<tr class="${loopResult.result}">
		<td>${loopResult.id}</td>
		<td>${loopResult.desc}</td>
		<td>${loopResult.result}</td>
		<td>
		${loopResult.summary}
		</td>
	</tr>
	</#list>
</table>
</body>
</html>