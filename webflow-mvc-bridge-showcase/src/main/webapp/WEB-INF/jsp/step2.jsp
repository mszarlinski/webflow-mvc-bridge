<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Step 2</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.16/jquery-ui.min.js"></script>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/ui-lightness/jquery-ui-1.8.16.custom.css" />
</head>
<body>
<h1>Select one friend to be your best friend</h1>

<form action="${flowExecutionUrl}" method="post">
    <table border="0">
        <tr>
            <td>Select a best friend:</td>
            <td><input id="friendsAutocomplete" type="text" name="bestFriend" /> (try typing M, J, B or R)</td>
        </tr>
        <tr>
            <td></td>
            <td><input type="submit" name="_eventId_select" value="Make best friend" /></td>
        </tr>
    </table>
</form>

<a href="${flowExecutionUrl}&_eventId=back">&lt; Back</a>

<script type="text/javascript">
    $("#friendsAutocomplete").autocomplete({ source: 'listMyFiends?execution=${flowExecutionKey}' });
</script>

</body>
</html>