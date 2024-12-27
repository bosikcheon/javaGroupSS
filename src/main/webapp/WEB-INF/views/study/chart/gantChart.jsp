<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs5.jsp" />
  <script src="https://www.gstatic.com/charts/loader.js"></script>
  <script>
    google.charts.load("current", { packages: ["gantt"] });
    google.charts.setOnLoadCallback(drawChart);

    function toMilliseconds(minutes) {
      return minutes * 60 * 1000;
    }

    function drawChart() {
      var otherData = new google.visualization.DataTable();
      otherData.addColumn("string", "Task ID");
      otherData.addColumn("string", "Task Name");
      otherData.addColumn("string", "Resource");
      otherData.addColumn("date", "Start");
      otherData.addColumn("date", "End");
      otherData.addColumn("number", "Duration");
      otherData.addColumn("number", "Percent Complete");
      otherData.addColumn("string", "Dependencies");

      otherData.addRows([
        [
          "toTrain",
          "Walk to train stop",
          "walk",
          null,
          null,
          toMilliseconds(5),
          100,
          null,
        ],
        [
          "music",
          "Listen to music",
          "music",
          null,
          null,
          toMilliseconds(70),
          100,
          null,
        ],
        [
          "wait",
          "Wait for train",
          "wait",
          null,
          null,
          toMilliseconds(10),
          100,
          "toTrain",
        ],
        [
          "train",
          "Train ride",
          "train",
          null,
          null,
          toMilliseconds(45),
          75,
          "wait",
        ],
        [
          "toWork",
          "Walk to work",
          "walk",
          null,
          null,
          toMilliseconds(10),
          0,
          "train",
        ],
        [
          "work",
          "Sit down at desk",
          null,
          null,
          null,
          toMilliseconds(2),
          0,
          "toWork",
        ],
      ]);

      var options = {
        height: 275,
        gantt: {
          defaultStartDate: new Date(2015, 3, 28),
        },
      };

      var chart = new google.visualization.Gantt(
        document.getElementById("chart_div")
      );

      chart.draw(otherData, options);
    }
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
  <div id="chart_div"></div>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>