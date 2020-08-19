<%-- 
    Document   : index
    Created on : 01-Jun-2019, 22:42:18
    Author     : Haris Tanwir
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Web Console</title>
        <style type="text/css">
            table {
                border: 1px solid black;
                text-align: left;
                width: 500px;
            }
            table td {
                border-top: 1px solid black;
            }
        </style>
        <script type="text/javascript" src="scripts/jquery-3.4.1.min.js" ></script>
        <script type="text/javascript" src="scripts/ajax.js" ></script>
        <script type="text/javascript">
            <%
                String[] servlets = new String[]{"EmployeeInputFlow", "EmployeeTimerFlow"};
                for (String servlet : servlets) {
            %>

            $(function () {
                setTimeout(function () {
                    getFlowState("<%= servlet%>");
                }, 1000);
            });
            
            $(function () {
                $("#start-button-<%= servlet%>").click(function () {
                    startFlow("<%= servlet%>");
                });
                $("#stop-button-<%= servlet%>").click(function () {
                    stopFlow("<%= servlet%>");
                });
                $("#reload-button-<%= servlet%>").click(function () {
                    reloadFlow("<%= servlet%>");
                });
            });

            <%
                }
            %>
        </script>

    </head>
    <body>
        <table>
            <tr><th>FlowName</th><th>Status</th><th>Action</th></tr>
                    <%
                        for (String servlet : servlets) {
                    %>
            <tr>
                <td><%= servlet%></td>
                <td><span id="status-label-<%= servlet%>" ></span></td>
                <td>
                    <input id="start-button-<%= servlet%>" type="button" value="Start" disabled="disabled"/>
                    <input id="stop-button-<%= servlet%>" type="button" value="Stop" disabled="disabled"/>
                    <input id="reload-button-<%= servlet%>" type="button" value="Reload"/>
                </td>
            </tr>
            <%
                }
            %>
        </table>
    </body>
</html>
