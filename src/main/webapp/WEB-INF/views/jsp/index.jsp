<%--
  Created by IntelliJ IDEA.
  User: alekseysamoylov
  Date: 8/8/17
  Time: 9:02 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html data-ng-app="widget">
<head>
    <title>Title</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <script src="js/jquery.min.js"></script>
    <script src="js/angular.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <jsp:include page="/WEB-INF/app/includes/appJs.jsp"/>
</head>
<body>
<div class="container">
    <h1>Welcome to ${message}</h1>
</div>

<div ng-controller="LogListController">
    <div class="container">
        <form ng-submit="setInterval()">
            <div class="form-group">
                <label for="interval">Set the interval value in seconds</label>
                <input class="form-control" type="number" id="interval" ng-model="newInterval">
            </div>
            <button class="btn btn-success" type="submit">Save changes</button>
        </form>
    </div>

    <div class="container">
        <table class="table" ng-repeat="log in logList">
            <tr>
                <td>
                    <pre>{{ log }}</pre>
                </td>
            </tr>
        </table>
    </div>
</div>

</body>
</html>
