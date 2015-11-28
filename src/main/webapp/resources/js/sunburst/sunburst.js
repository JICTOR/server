function drawSunburst(drawSunburstId, sequenceId, modeId, packageClassNameId, countId, userKey) {
    var width = $(drawSunburstId).width(),
        height = $(drawSunburstId).height() - 35,
        radius = Math.min(width, height) / 2;

    // Breadcrumb dimensions: width, height, spacing, width of tip/tail.
    var b = {
        w: 150, h: 30, s: 3, t: 10
    };

    var x = d3.scale.linear()
        .range([0, 2 * Math.PI]);

    var y = d3.scale.sqrt()
        .range([0, radius]);

    var color = d3.scale.category20c();

    function zoom() {
        svg.attr("transform", "translate(" + d3.event.translate + ")scale(" + d3.event.scale + ")");
    }

    // define the zoomListener which calls the zoom function on the "zoom" event constrained within the scaleExtents
    var zoomListener = d3.behavior.zoom().scaleExtent([0.1, 3]).on("zoom", zoom);

    var svg = d3.select(drawSunburstId).append("svg")
        .attr("width", "100%")
        .attr("height", "100%")
        .call(zoomListener)
        .append("g")
        .attr("id", "sunburstContainer")
        .attr("transform", "translate(" + width / 2 + "," + (height / 2) + ")");

    function initZoom(){
        zoomListener.translate([width/2, height/2]);
    }

    initZoom();

    var partition = d3.layout.partition()
        .sort(null)
        .value(function(d) { return 1; });

    var arc = d3.svg.arc()
        .startAngle(function(d) { return Math.max(0, Math.min(2 * Math.PI, x(d.x))); })
        .endAngle(function(d) { return Math.max(0, Math.min(2 * Math.PI, x(d.x + d.dx))); })
        .innerRadius(function(d) { return Math.max(0, y(d.y)); })
        .outerRadius(function(d) { return Math.max(0, y(d.y + d.dy)); });

    // Keep track of the node that is currently being displayed as the root.
    var node;
    var totalSize = 0;

    d3.json("/sunburst/classInfo/" + userKey, function(error, root) {
        node = root;
        var path = svg.datum(root).selectAll("path")
            .data(partition.nodes)
            .enter().append("path")
            .attr("d", arc)
            .style("fill", function(d) { return color((d.children ? d : d.parent).name); })
            .on("click", click)
            .on("mouseover", mouseover)
            .each(stash);

        totalSize = path.node().__data__.value;

        // Add the mouseleave handler to the bounding circle.
        d3.select("#sunburstContainer").on("mouseleave", mouseleave);

        // Basic setup of page elements.
        initializeBreadcrumbTrail();

        d3.select(modeId).on("change", function change() {
            var checked = d3.select('input[name="option"]:checked').node().value;
            var value;

            if(checked === "lineCount"){
                value = function(d) { return d.lineCount; };
            }else if(checked === "classUseCount"){
                value = function(d) { return d.classUseCount; }
            }else if(checked === "usedClassCount"){
                value = function(d) { return d.usedClassCount; }
            }else{
                value = function() { return 1; }
            }

            path
                .data(partition.value(value).nodes)
                .transition()
                .duration(1000)
                .attrTween("d", arcTweenData);

            totalSize = path.node().__data__.value;
        });

        $('#sunburst').hide();

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
                        drawClassUseBubble(userKey, d.classInfoKey);
                        break;
                    case "usedClassCount":
                        $('#sunburst').hide();
                        $('#bubble').show();
                        $('#tree').hide();
                        $('#statusResult').show();
                        $('#sunburstModeChange').hide();
                        drawUsedClassBubble(userKey, d.classInfoKey);
                        break;
                    default :
                        window.location.replace("#");
                        $('#sunburst').hide();
                        $('#bubble').hide();
                        $('#tree').show();
                        $('#sunburstModeChange').hide();
                        drawTree(userKey, d.classInfoKey);
                        break;
                }
            }else{
                node = d;
                path.transition()
                    .duration(1000)
                    .attrTween("d", arcTweenZoom(d));
            }
        }
        //animateSunburst();
    });

    d3.select(self.frameElement).style("height", height + "px");

    // Setup for switching data: stash the old values for transition.
    function stash(d) {
        d.x0 = d.x;
        d.dx0 = d.dx;
    }

    // When switching data: interpolate the arcs in data space.
    function arcTweenData(a, i) {
        var oi = d3.interpolate({x: a.x0, dx: a.dx0}, a);
        function tween(t) {
            var b = oi(t);
            a.x0 = b.x;
            a.dx0 = b.dx;
            return arc(b);
        }
        if (i == 0) {
            // If we are on the first arc, adjust the x domain to match the root node
            // at the current zoom level. (We only need to do this once.)
            var xd = d3.interpolate(x.domain(), [node.x, node.x + node.dx]);
            return function(t) {
                x.domain(xd(t));
                return tween(t);
            };
        } else {
            return tween;
        }
    }

    // When zooming: interpolate the scales.
    function arcTweenZoom(d) {
        var xd = d3.interpolate(x.domain(), [d.x, d.x + d.dx]),
            yd = d3.interpolate(y.domain(), [d.y, 1]),
            yr = d3.interpolate(y.range(), [d.y ? 20 : 0, radius]);
        return function(d, i) {
            return i
                ? function(t) { return arc(d); }
                : function(t) { x.domain(xd(t)); y.domain(yd(t)).range(yr(t)); return arc(d); };
        };
    }

    // Fade all but the current sequence, and show it in the breadcrumb trail.
    function mouseover(d) {

        d3.select(packageClassNameId).text(d.name);
        d3.select(countId).text(d.value);

        var percentage = (100 * d.value / totalSize).toPrecision(3);
        var percentageString = percentage + "%";
        if (percentage < 0.1) {
            percentageString = "< 0.1%";
        }

        //d3.select(percentageId)
        //    .text(percentageString);

        //d3.select(explanationId)
        //    .style("visibility", "");

        var sequenceArray = getAncestors(d);
        updateBreadcrumbs(sequenceArray, percentageString);

        // Fade all the segments.
        d3.selectAll("path")
            .style("opacity", 0.3);

         //Then highlight only those that are an ancestor of the current segment.
        svg.selectAll("path")
            .filter(function(node) {
                return (sequenceArray.indexOf(node) >= 0);
            })
            .style("opacity", 1);
    }

    // Restore everything to full opacity when moving off the visualization.
    function mouseleave(d) {

        //// Hide the breadcrumb trail
        //d3.select("#trail")
        //    .style("visibility", "hidden");

        // Deactivate all segments during transition.
        d3.selectAll("path").on("mouseover", null);

        // Transition each segment to full opacity and then reactivate it.
        d3.selectAll("path")
            .transition()
            .duration(1000)
            .style("opacity", 1)
            .each("end", function() {
                d3.select(this).on("mouseover", mouseover);
            });

        //d3.select(explanationId)
        //    .style("visibility", "hidden");
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

    // Update the breadcrumb trail to show the current sequence and percentage.
    function updateBreadcrumbs(nodeArray, percentageString) {

        // Data join; key function combines name and depth (= position in sequence).
        var g = d3.select("#trail")
            .selectAll("g")
            .data(nodeArray, function(d) { return d.name + d.depth; });

        // Add breadcrumb and label for entering nodes.
        var entering = g.enter().append("svg:g");

        var polygonLengthCumulative = 0;

        entering.append("svg:polygon")
            //.attr("points", breadcrumbPoints)
            .attr("points", function breadcrumbPoints(d, i) {
                var points = [];
                points.push("0,0");
                points.push((d.name.length*7+20) + ",0");
                points.push((d.name.length*7+20) + b.t + "," + (b.h / 2));
                points.push((d.name.length*7+20) + "," + b.h);
                points.push("0," + b.h);
                if (i > 0) { // Leftmost breadcrumb; don't include 6th vertex.
                    points.push(b.t + "," + (b.h / 2));
                }
                return points.join(" ");
            })
            .style("fill", function(d) { return color((d.children ? d : d.parent).name); });

        entering.append("svg:text")
            //.attr("x", (b.w + b.t) / 2)
            .attr("x", function(d){return (d.name.length*4+10)})
            .attr("y", b.h / 2)
            .attr("dy", "0.35em")
            .attr("text-anchor", "middle")
            .text(function(d) { return d.name; });

        // Set position for entering and updating nodes.
        g.attr("transform", function(d, i) {
            var beforeLength = polygonLengthCumulative;
            polygonLengthCumulative += (d.name.length*7+20) + b.s;
            //return "translate(" + i * (b.w + b.s) + ", 0)";
            return "translate(" + beforeLength + ", 0)";
        });

        // Remove exiting nodes.
        g.exit().remove();

        // Now move and update the percentage at the end.
        d3.select("#trail").select("#endlabel")
            //.attr("x", (nodeArray.length + 0.5) * (b.w + b.s))
            .attr("x",polygonLengthCumulative + 40)
            .attr("y", b.h / 2)
            .attr("dy", "0.35em")
            .attr("text-anchor", "middle")
            .text(percentageString);

        // Make the breadcrumb trail visible, if it's hidden.
        d3.select("#trail")
            .style("visibility", "");

    }

    function initializeBreadcrumbTrail() {
        // Add the svg area.
        var trail = d3.select(sequenceId).append("svg:svg")
            //.attr("width", width*2)
            .attr("width", width)
            .attr("height", 30)
            .attr("style","float:left; position:relative;")
            .attr("id", "trail");
        // Add the label at the end, for the percentage.
        trail.append("svg:text")
            .attr("id", "endlabel")
            .style("fill", "#000");
    }

    //function breadcrumbPoints(d, i) {
    //    var points = [];
    //    points.push("0,0");
    //    //points.push(b.w + ",0");
    //    //points.push(b.w + b.t + "," + (b.h / 2));
    //    //points.push(b.w + "," + b.h);
    //    points.push(d.length*20 + ",0");
    //    points.push(d.length*20 + b.t + "," + (b.h / 2));
    //    points.push(d.length*20 + "," + b.h);
    //    points.push("0," + b.h);
    //    if (i > 0) { // Leftmost breadcrumb; don't include 6th vertex.
    //        points.push(b.t + "," + (b.h / 2));
    //    }
    //    return points.join(" ");
    //}
};