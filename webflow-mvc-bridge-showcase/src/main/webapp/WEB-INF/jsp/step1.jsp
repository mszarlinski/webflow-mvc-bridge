<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Step 1</title>
</head>
<body>
<h1>Select account</h1>

<form action="${flowExecutionUrl}" method="post">
    <table border="0">
        <tr>
            <td>User name:</td>
            <td><input type="text" name="userName" value="${account.userName}" /> (try Alice or Bob)</td>
        </tr>
        <tr>
            <td></td>
            <td><input type="submit" name="_eventId_next" value="Select best friend" /></td>
        </tr>
    </table>
</form>

</body>
</html>