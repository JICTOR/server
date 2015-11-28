//TODO : bubble, tree 갔다오면 문제 있음.
//TODO : compile error 발생 시 색깔 변화
var width = 720,
    height = 540,
    radius = Math.min(width, height) / 2;

var b = {
    w: 150, h: 30, s: 3, t: 10
};

var sunburst_x = d3.scale.linear()
    .range([0, 2 * Math.PI]);

var sunburst_y = d3.scale.sqrt()
    .range([0, radius]);

var color = d3.scale.category20c();

function zoom() {
    svg.attr("transform", "translate(" + d3.event.translate + ")scale(" + d3.event.scale + ")");
}

// define the zoomListener which calls the zoom function on the "zoom" event constrained within the scaleExtents
var zoomListener = d3.behavior.zoom().scaleExtent([0.1, 3]).on("zoom", zoom);

var svg = d3.select("#sunburstVisual").append("svg")
    .attr("width", "100%")
    .attr("height", height)
    .call(zoomListener)
    .append("g")
    .attr("id", "sunburstContainer")
    .attr("transform", "translate(" + width / 2 + "," + (height / 2) + ")");

var partition = d3.layout.partition()
    .sort(null)
    .value(function (d) {
        return 1;
    });

var arc = d3.svg.arc()
    .startAngle(function (d) {
        return Math.max(0, Math.min(2 * Math.PI, sunburst_x(d.x)));
    })
    .endAngle(function (d) {
        return Math.max(0, Math.min(2 * Math.PI, sunburst_x(d.x + d.dx)));
    })
    .innerRadius(function (d) {
        return Math.max(0, sunburst_y(d.y));
    })
    .outerRadius(function (d) {
        return Math.max(0, sunburst_y(d.y + d.dy));
    });

var node;
var totalSize = 0;
var path;
var commitId;

function initSunburst(){
    sunburst_x = d3.scale.linear()
        .range([0, 2 * Math.PI]);

    sunburst_y = d3.scale.sqrt()
        .range([0, radius]);
}
function drawSunburst(commitId) {
    this.commitId = commitId;
    d3.json("/sunburst/git/" + commitId, function (error, root) {
        if (root === undefined || root.children.length === 0) {
            return;
        }
        node = root;
        path = svg.datum(root).selectAll("path")
            .data(partition.nodes)
            .enter().append("path")
            .attr("d", arc)
            .style("fill", function (d) {
                return color((d.children ? d : d.parent).name);
            })
            .on("click", click)
            .on("mouseover", mouseover)
            .each(stash);
        totalSize = path.node().__data__.value;
        initializeBreadcrumbTrail();
    });
};

d3.select("#sunburstContainer").on("mouseleave", mouseleave);

d3.select("#sunburstMode").on("change", function change() {
    var value = optionValue();

    path
        .data(partition.value(value).nodes)
        .transition()
        .duration(1000)
        .attrTween("d", arcTweenData);

    totalSize = path.node().__data__.value;
});

function click(d) {
    if(d.children == null){
        var value = d3.select('input[name="option"]:checked').node().value;
        switch(value){
            case "classUseCount":
                $('#sunburst').hide();
                $('#bubble').show();
                $('#tree').hide();
                $('#statusResult').show();
                $('#sunburstModeChange').hide();
                drawClassUseBubble(commitId, d.classInfoKey);
                break;
            case "usedClassCount":
                $('#sunburst').hide();
                $('#bubble').show();
                $('#tree').hide();
                $('#statusResult').show();
                $('#sunburstModeChange').hide();
                drawUsedClassBubble(commitId, d.classInfoKey);
                break;
            default :
                window.location.replace("#");
                $('#sunburst').hide();
                $('#bubble').hide();
                $('#tree').show();
                $('#sunburstModeChange').hide();
                drawTree(d.classInfoKey);
                break;
        }
    }else{
        node = d;
        path.transition()
            .duration(1000)
            .attrTween("d", arcTweenZoom(d));
    }
}

d3.select(self.frameElement).style("height", height + "px");

function stash(d) {
    d.x0 = d.x;
    d.dx0 = d.dx;
}

function arcTweenData(a, i) {
    var oi = d3.interpolate({x: a.x0, dx: a.dx0}, a);

    function tween(t) {
        var b = oi(t);
        a.x0 = b.x;
        a.dx0 = b.dx;
        return arc(b);
    }

    if (i == 0) {
        var xd = d3.interpolate(sunburst_x.domain(), [node.x, node.x + node.dx]);
        return function (t) {
            sunburst_x.domain(xd(t));
            return tween(t);
        };
    } else {
        return tween;
    }
}

function arcTweenZoom(d) {
    var xd = d3.interpolate(sunburst_x.domain(), [d.x, d.x + d.dx]),
        yd = d3.interpolate(sunburst_y.domain(), [d.y, 1]),
        yr = d3.interpolate(sunburst_y.range(), [d.y ? 20 : 0, radius]);
    return function (d, i) {
        return i
            ? function (t) {
            return arc(d);
        }
            : function (t) {
            sunburst_x.domain(xd(t));
            sunburst_y.domain(yd(t)).range(yr(t));
            return arc(d);
        };
    };
}

function mouseover(d) {

    d3.select("#sunburstPackageClassName").text(d.name);
    d3.select("#sunburstCount").text(d.value);

    var percentage = (100 * d.value / totalSize).toPrecision(3);
    var percentageString = percentage + "%";
    if (percentage < 0.1) {
        percentageString = "< 0.1%";
    }

    var sequenceArray = getAncestors(d);
    updateBreadcrumbs(sequenceArray, percentageString);

    d3.selectAll("path")
        .style("opacity", 0.3);

    svg.selectAll("path")
        .filter(function (node) {
            return (sequenceArray.indexOf(node) >= 0);
        })
        .style("opacity", 1);
}

function mouseleave(d) {
    d3.selectAll("path").on("mouseover", null);

    d3.selectAll("path")
        .transition()
        .duration(1000)
        .style("opacity", 1)
        .each("end", function () {
            d3.select(this).on("mouseover", mouseover);
        });
}

function getAncestors(node) {
    var path = [];
    var current = node;
    while (current.parent) {
        path.unshift(current);
        current = current.parent;
    }
    return path;
}

function updateBreadcrumbs(nodeArray, percentageString) {
    var g = d3.select("#trail")
        .selectAll("g")
        .data(nodeArray, function (d) {
            return d.name + d.depth;
        });

    var entering = g.enter().append("svg:g");

    var polygonLengthCumulative = 0;

    entering.append("svg:polygon")
        .attr("points", function breadcrumbPoints(d, i) {
            var points = [];
            points.push("0,0");
            points.push((d.name.length * 7 + 20) + ",0");
            points.push((d.name.length * 7 + 20) + b.t + "," + (b.h / 2));
            points.push((d.name.length * 7 + 20) + "," + b.h);
            points.push("0," + b.h);
            if (i > 0) { // Leftmost breadcrumb; don't include 6th vertex.
                points.push(b.t + "," + (b.h / 2));
            }
            return points.join(" ");
        })
        .style("fill", function (d) {
            return color((d.children ? d : d.parent).name);
        });

    entering.append("svg:text")
        .attr("x", function (d) {
            return (d.name.length * 4 + 10)
        })
        .attr("y", b.h / 2)
        .attr("dy", "0.35em")
        .attr("text-anchor", "middle")
        .text(function (d) {
            return d.name;
        });

    g.attr("transform", function (d, i) {
        var beforeLength = polygonLengthCumulative;
        polygonLengthCumulative += (d.name.length * 7 + 20) + b.s;
        return "translate(" + beforeLength + ", 0)";
    });

    g.exit().remove();

    d3.select("#trail").select("#endlabel")
        .attr("x", polygonLengthCumulative + 40)
        .attr("y", b.h / 2)
        .attr("dy", "0.35em")
        .attr("text-anchor", "middle")
        .text(percentageString);

    d3.select("#trail")
        .style("visibility", "");

}

function initializeBreadcrumbTrail() {
    var trail = d3.select("#sunburstSequence").append("svg:svg")
        .attr("width", width)
        .attr("height", 30)
        .attr("style","float:left; margin-bottom:10px; padding-left:80px;")
        .attr("id", "trail");
    trail.append("svg:text")
        .attr("id", "endlabel")
        .style("fill", "#000");
}

function changeJsonData(commitId) {
    this.commitId = commitId;
    d3.json("/sunburst/git/" + commitId, function (error, root) {
        if (root === undefined || root.children.length === 0) {
            return;
        }

        var value = optionValue();
        var beforeNodeSize = svg.selectAll("path")[0].length;
        var rootSize = svg.datum(root).selectAll("path").data(partition.nodes)[0].length;

        if (rootSize > beforeNodeSize) {
            path = svg.datum(root).selectAll("path")
                .data(partition.nodes)
                .enter().append("g");
            path.append("path")
                .attr("d", arc)
                .style("fill", function (d) {
                    return color((d.children ? d : d.parent).name);
                })
                .on("click", click)
                .on("mouseover", mouseover)
                .each(stash);
        } else if (rootSize < beforeNodeSize) {
            svg.datum(root).selectAll("path").data(partition.nodes).exit().remove();
        }

        path = svg.datum(root).selectAll("path")
            .data(partition.nodes)
            .attr("d", arc)
            .style("fill", function (d) {
                return color((d.children ? d : d.parent).name);
            })
            .on("click", click)
            .on("mouseover", mouseover)
            .each(stash);

        node = root;

        path
            .data(partition.value(value).nodes)
            .transition()
            .duration(1000)
            .attrTween("d", arcTweenData);

        totalSize = path.node().__data__.value;
    });
}

function optionValue(){
    var value;
    var checked = d3.select('input[name="option"]:checked').node().value;

    if (checked === "lineCount") {
        value = function (d) {
            return d.lineCount;
        };
    } else if (checked === "classUseCount") {
        value = function (d) {
            return d.classUseCount;
        }
    } else if (checked === "usedClassCount") {
        value = function (d) {
            return d.usedClassCount;
        }
    } else {
        value = function () {
            return 1;
        }
    }
    return value;
}