function commitChart(userKey) {
    var id;
    var graphData;
    var gData = [];
    $.getJSON("/dashboard/commit/class/list/" + userKey, function (json) {
        var data = [];
        for (var i = 0; i < json.length; i++) {
            data = [];
            data.push(i);
            data.push(json[i].value);
            gData.push(data);
        }
        graphData = [{
            data: gData,
            color: '#71c73e'
        }];

        // Lines
        $.plot($('#line_commit_chart'), graphData, {
            series: {
                points: {
                    show: true,
                    radius: 5
                },
                lines: {
                    show: true
                },
                shadowSize: 0
            },
            grid: {
                color: '#646464',
                borderColor: 'transparent',
                borderWidth: 20,
                hoverable: true
            },
            xaxis: {
                tickColor: 'transparent',
                tickDecimals: 2
            },
            yaxis: {
                tickSize: 10
            }
        });

        function showTooltip(x, y, contents) {
            $('<div id="tooltip">' + contents + '</div>').css({
                top: y - 16,
                left: x + 20
            }).appendTo('body').fadeIn();
        }

        var previousPoint = null;

        $('#line-chart, #graph-bars').bind('plothover', function (event, pos, item) {
            if (item) {
                if (previousPoint != item.dataIndex) {
                    previousPoint = item.dataIndex;
                    $('#tooltip').remove();
                    var x = item.datapoint[0],
                        y = item.datapoint[1];
                    showTooltip(item.pageX, item.pageY, y + ' visitors at ' + x + '.00h');
                }
            } else {
                $('#tooltip').remove();
                previousPoint = null;
            }
        });
    });
};


function diffChart(userKey) {
    var id;
    var graphData;
    var gData = [];
    $.getJSON("/dashboard/commit/diff/list/" + userKey, function (json) {
        var data = [];
        for (var i = 0; i < json.length; i++) {
            data = [];
            data.push(i);
            data.push(json[i].value);
            gData.push(data);
        }
        graphData = [{
            data: gData,
            color: '#71c73e'
        }];

        // Lines
        $.plot($('#line_diff_chart'), graphData, {
            series: {
                points: {
                    show: true,
                    radius: 5
                },
                lines: {
                    show: true
                },
                shadowSize: 0
            },
            grid: {
                color: '#646464',
                borderColor: 'transparent',
                borderWidth: 20,
                hoverable: true
            },
            xaxis: {
                tickColor: 'transparent',
                tickDecimals: 2
            },
            yaxis: {
                tickSize: 10
            }
        });

        function showTooltip(x, y, contents) {
            $('<div id="tooltip">' + contents + '</div>').css({
                top: y - 16,
                left: x + 20
            }).appendTo('body').fadeIn();
        }

        var previousPoint = null;

        $('#line-chart, #graph-bars').bind('plothover', function (event, pos, item) {
            if (item) {
                if (previousPoint != item.dataIndex) {
                    previousPoint = item.dataIndex;
                    $('#tooltip').remove();
                    var x = item.datapoint[0],
                        y = item.datapoint[1];
                    showTooltip(item.pageX, item.pageY, y + ' visitors at ' + x + '.00h');
                }
            } else {
                $('#tooltip').remove();
                previousPoint = null;
            }
        });
    });
};