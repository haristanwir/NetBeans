/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function getFlowState(servletUrl) {
    $.ajax({
        url: servletUrl,
        data: {
            "function": "getFlowState"
        },
        success: function (data) {
            data = eval('(' + $.trim(data) + ')');
            if (data.status === 1) {
                $("#status-label-" + servletUrl).html("Running");
                $("#start-button-" + servletUrl).prop('disabled', 'disabled');
                $("#stop-button-" + servletUrl).prop('disabled', false);
            } else {
                $("#status-label-" + servletUrl).html("Stopped");
                $("#start-button-" + servletUrl).prop('disabled', false);
                $("#stop-button-" + servletUrl).prop('disabled', 'disabled');
            }
            setTimeout(function () {
                getFlowState(servletUrl);
            }, 5000);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            if (textStatus == "timeout") {
                console.log("getFlowState -> Request timeout, Server -> " + servletUrl + ", Detail -> " + errorThrown);
            } else {
                console.log("getFlowState -> server not responding, Server -> " + servletUrl + ", Detail -> " + errorThrown);
            }
            $("#status-label-" + servletUrl).html("Error");
            $("#start-button-" + servletUrl).prop('disabled', false);
            $("#stop-button-" + servletUrl).prop('disabled', 'disabled');
            setTimeout(function () {
                getFlowState(servletUrl);
            }, 5000);
        }
    });
}

function startFlow(servletUrl) {
    $("#start-button-" + servletUrl).prop('disabled', 'disabled');
    $.ajax({
        url: servletUrl,
        data: {
            "function": "startFlow"
        },
        success: function (data) {
            data = eval('(' + $.trim(data) + ')');

        },
        error: function (jqXHR, textStatus, errorThrown) {
            if (textStatus == "timeout") {
                console.log("startFlow -> Request timeout, Server -> " + servletUrl + ", Detail -> " + errorThrown);
            } else {
                console.log("startFlow -> server not responding, Server -> " + servletUrl + ", Detail -> " + errorThrown);
            }

        }
    });
}

function stopFlow(servletUrl) {
    $("#stop-button-" + servletUrl).prop('disabled', 'disabled');
    $.ajax({
        url: servletUrl,
        data: {
            "function": "stopFlow"
        },
        success: function (data) {
            data = eval('(' + $.trim(data) + ')');
        },
        error: function (jqXHR, textStatus, errorThrown) {
            if (textStatus == "timeout") {
                console.log("stopFlow -> Request timeout, Server -> " + servletUrl + ", Detail -> " + errorThrown);
            } else {
                console.log("stopFlow -> server not responding, Server -> " + servletUrl + ", Detail -> " + errorThrown);
            }

        }
    });
}

function reloadFlow(servletUrl) {
    $("#reload-button-" + servletUrl).prop('disabled', 'disabled');
    $.ajax({
        url: servletUrl,
        data: {
            "function": "reloadFlow"
        },
        success: function (data) {
            data = eval('(' + $.trim(data) + ')');
            $("#reload-button-" + servletUrl).prop('disabled', false);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            if (textStatus == "timeout") {
                console.log("reloadFlow -> Request timeout, Server -> " + servletUrl + ", Detail -> " + errorThrown);
            } else {
                console.log("reloadFlow -> server not responding, Server -> " + servletUrl + ", Detail -> " + errorThrown);
            }
            $("#reload-button-" + servletUrl).prop('disabled', false);
        }
    });
}