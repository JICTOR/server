function drawClassUseBubble(userKey, classInfoKey) {

    $("#bubble").html("");

    d3.json("/bubble/use/classInfo/" + userKey + "/" + classInfoKey, function (error, link) {

            var nodeset = {};
            var links = link;

            if(!(link.length > 0)){
                $("#bubble").html("<h3> This Class Does Not Use Another Classes </h3>");
            }

            // Compute the distinct nodes from the links.
            /*links.forEach(function (link) {
                link.source = nodeset[link.source] || (nodeset[link.source] = {name: link.source});
                link.target = nodeset[link.target] || (nodeset[link.target] = {name: link.target});
            });*/

            // Compute the distinct nodes from the links.
            links.forEach(function (link) {
                link.source = nodeset[link.source] || (nodeset[link.source] = {name: link.source});
                link.target = nodeset[link.target] || (nodeset[link.target] = {name: link.target});
            });

            links.forEach(function (d) {
                d.straight = 1;
                links.forEach(function(d1) {
                    if((d.source == d1.target) && (d1.source == d.target))
                        d.straight = 0;
                });
            });

            nodes = d3.values(nodeset);

            /*nodes.forEach(function(d) {
                d.started = 0;
                links.forEach(function(d1) {
                    if(d == d1.source)
                        d.started++;
                });
            });*/

            nodes.forEach(function(d) {
                d.started = 0;
                links.forEach(function(d1) {
                    if(d == d1.target)
                        d.started++;
                });
            });

            var width = $('#visualization').width(),
                 height = $('#visualization').height();

            var color = d3.scale.category20();

            var force = d3.layout.force()
                .nodes(d3.values(nodes))
                .links(links)
                .size([width, height])
                .linkDistance(240)
                .charge(-1000)
                .on("tick", tick)
                .start();

            var svg = d3.select("#bubble").append("svg")
                .attr("width", width)
                .attr("height", height)

            // Per-type markers, as they don't inherit styles.
            svg.append("defs").selectAll("marker")
                //.data(["suit", "licensing", "resolved"])
                .data(["end"])
                .enter().append("marker")
                .attr("id", function (d) {
                    return d;
                })
                .attr("viewBox", "0 -5 10 10")
                .attr("refX", 15)
                .attr("refY", -1.5)
                .attr("markerWidth", 6)
                .attr("markerHeight", 6)
                .attr("orient", "auto")
                .append("path")
                .attr("d", "M0,-5L10,0L0,5");

            var path = svg.append("g").selectAll("path")
                .data(force.links())
                .enter().append("path")
                /*.attr("class", function (d) {
                    return "link " + d.type;
                })
                .attr("marker-end", function (d) {
                    return "url(#" + d.type + ")";
                });*/
                .attr("class", function (d) {
                    return "link" + d.type;
                })
                .attr("class", "link")
                .attr("marker-end", "url(#end)");

            var circle = svg.append("g").selectAll("circle")
                .data(force.nodes())
                .enter().append("circle")
                .on('dblclick', dblclick)
                //.attr("r", 10)
                .attr("r", function(d)  {   return 8 + d.started;   })
                .style("fill", function(d, i) {    return color(i);  })
                .call(force.drag);

            /*if(links.target === path[0]) {
                circle.attr("r", 20)
            }
            console.log(links.target);
            console.log(path);*/

            var text = svg.append("g").selectAll("text")
                .data(force.nodes())
                .enter().append("text")
                .attr("x", function(d) {    return 15 + d.started;   } )
                .attr("y", ".31em")
                //.attr("text-anchor", "middle")
                .text(function (d) {
                    return d.name;
                });

            //var container = svg.append("g");

            // Use elliptical arc path segments to doubly-encode directionality.
            function tick() {
                path.attr("d", linkArc);
                circle.attr("transform", transform);
                text.attr("transform", transform);
            }

            function linkArc(d) {
                var dx = d.target.x - d.source.x,
                    dy = d.target.y - d.source.y,
                    //dr = Math.sqrt(dx * dx + dy * dy);
                    dr = (d.straight == 0)?Math.sqrt(dx * dx + dy * dy):0;

                return "M" + d.source.x + "," + d.source.y + "A" + dr + "," + dr + " 0 0,1 " + d.target.x + "," + d.target.y;
            }

            function transform(d) {
                return "translate(" + d.x + "," + d.y + ")";
            }
        }
    )
    function dblclick(d){
        if(d.index == 1) {
            var code = window.open("/code_view/" + userKey + "/" +classInfoKey, "code_view/" + userKey + "/" + d.name);
            code.fqn = d.name;
        }
    }
}

function drawUsedClassBubble(userKey, classInfoKey) {

    $("#bubble").html("");

    d3.json("/bubble/used/classInfo/" + userKey + "/" + classInfoKey, function (error, link) {

            var nodeset = {};
            var links = link;

            if(!(link.length > 0)){
                $("#bubble").html("<h3> This Class Is Not Used Another Classes </h3>");
            }

            // Compute the distinct nodes from the links.
            /*links.forEach(function (link) {
             link.source = nodeset[link.source] || (nodeset[link.source] = {name: link.source});
             link.target = nodeset[link.target] || (nodeset[link.target] = {name: link.target});
             });*/

            // Compute the distinct nodes from the links.
            links.forEach(function (link) {
                link.source = nodeset[link.source] || (nodeset[link.source] = {name: link.source});
                link.target = nodeset[link.target] || (nodeset[link.target] = {name: link.target});
            });

            links.forEach(function (d) {
                d.straight = 1;
                links.forEach(function(d1) {
                    if((d.source == d1.target) && (d1.source == d.target))
                        d.straight = 0;
                });
            });

            nodes = d3.values(nodeset);

            /*nodes.forEach(function(d) {
             d.started = 0;
             links.forEach(function(d1) {
             if(d == d1.source)
             d.started++;
             });
             });*/

            nodes.forEach(function(d) {
                d.started = 0;
                links.forEach(function(d1) {
                    if(d == d1.source)
                        d.started++;
                });
            });

            var width = $('#visualization').width(),
                height = $('#visualization').height();

            var color = d3.scale.category20();

            var force = d3.layout.force()
                .nodes(d3.values(nodes))
                .links(links)
                .size([width, height])
                .linkDistance(240)
                .charge(-1000)
                .on("tick", tick)
                .start();

            var svg = d3.select("#bubble").append("svg")
                .attr("width", width)
                .attr("height", height)

            // Per-type markers, as they don't inherit styles.
            svg.append("defs").selectAll("marker")
                //.data(["suit", "licensing", "resolved"])
                .data(["end"])
                .enter().append("marker")
                .attr("id", function (d) {
                    return d;
                })
                .attr("viewBox", "0 -5 10 10")
                .attr("refX", 15)
                .attr("refY", -1.5)
                .attr("markerWidth", 6)
                .attr("markerHeight", 6)
                .attr("orient", "auto")
                .append("path")
                .attr("d", "M0,-5L10,0L0,5");

            var path = svg.append("g").selectAll("path")
                .data(force.links())
                .enter().append("path")
                /*.attr("class", function (d) {
                 return "link " + d.type;
                 })
                 .attr("marker-end", function (d) {
                 return "url(#" + d.type + ")";
                 });*/
                .attr("class", function (d) {
                    return "link" + d.type;
                })
                .attr("class", "link")
                .attr("marker-end", "url(#end)");

            var circle = svg.append("g").selectAll("circle")
                .data(force.nodes())
                .enter().append("circle")
                .on('dblclick', dblclick)
                //.attr("r", 10)
                .attr("r", function(d)  {   return 8 + d.started;   })
                .style("fill", function(d, i) {    return color(i);  })
                .call(force.drag);

            /*if(links.target === path[0]) {
             circle.attr("r", 20)
             }
             console.log(links.target);
             console.log(path);*/

            var text = svg.append("g").selectAll("text")
                .data(force.nodes())
                .enter().append("text")
                .attr("x", function(d) {    return 15 + d.started;   } )
                .attr("y", ".31em")
                //.attr("text-anchor", "middle")
                .text(function (d) {
                    return d.name;
                });

            //var container = svg.append("g");

            // Use elliptical arc path segments to doubly-encode directionality.
            function tick() {
                path.attr("d", linkArc);
                circle.attr("transform", transform);
                text.attr("transform", transform);
            }

            function linkArc(d) {
                var dx = d.target.x - d.source.x,
                    dy = d.target.y - d.source.y,
                //dr = Math.sqrt(dx * dx + dy * dy);
                    dr = (d.straight == 0)?Math.sqrt(dx * dx + dy * dy):0;

                return "M" + d.source.x + "," + d.source.y + "A" + dr + "," + dr + " 0 0,1 " + d.target.x + "," + d.target.y;
            }

            function transform(d) {
                return "translate(" + d.x + "," + d.y + ")";
            }
        }
    )
    function dblclick(d){
        if(d.index == 0) {
            var code = window.open("/code_view/" + userKey + "/" + classInfoKey, "code_view/" + userKey + "/" + d.name);
            code.fqn = d.name;
        }
    }
}