<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%
    request.setCharacterEncoding("UTF-8");
%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>JICTOR - Software Health Kit</title>

    <link href="/resources/css/main/layout.css" rel="stylesheet" type="text/css" media="all">
    <link href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css" rel="stylesheet" type="text/css"
          media="screen">
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" rel="stylesheet" type="text/css"
          media="screen"/>

    <link href="/resources/css/bootstrap/bootstrap.min.css" rel="stylesheet" type="text/css" media="screen">
    <link href="/resources/css/bootstrap/board.css" rel="stylesheet" type="text/css" media="screen">
    <link href="/resources/css/tree/tree.css" rel="stylesheet" type="text/css" media="screen"/>
    <link href="/resources/css/sunburst/sunburst.css" rel="stylesheet" type="text/css" media="screen"/>
    <link href="/resources/css/graph/treeGraph.css" rel="stylesheet" type="text/css" media="screen"/>
    <link href="/resources/css/graph/bubbleGraph.css" rel="stylesheet" type="text/css" media="screen"/>
    <link href="/resources/css/search/style.css" rel="stylesheet" type="text/css" media="screen"/>
    <link href="/resources/fonts/bariol/bariol.css" rel="stylesheet" type="text/css" media="screen"/>

    <link href="/resources/css/timeline/horizontal.css" rel="stylesheet" type="text/css" media="screen"/>

    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js" type="text/javascript"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js" type="text/javascript"></script>

    <style>
        input[type='radio']{
            display: inline-block;
            margin-bottom : 10px;
        }

        .box-title{
            font-weight: bold;
        }

        tr[role='row']{
            height: 37px;
        }

        .searchComponents {
            margin: 0;
            height: 34px;
        }

        #btn_searchoption:hover, #btn_searchoption:focus {
            background-color: white;
            border: 0;
        }

        input[readonly] {
            background-color: white !important;
            cursor: text !important;
        }

        #btn_action, #btn_action:hover {
            width: 100px;
            background-color: white;
            font-weight: bold;
            text-transform: none;
            cursor:inherit;
        }

        @media (min-width: 768px) {
            #btn_action, #btn_action:hover {
                width: 170px;
                font-weight: bold;
                text-transform: none;
                cursor:inherit;
            }
        }

        @media (min-width: 1036px) {
            .site-wrapper-inner {
                width: 800px;
                margin: auto;
            }
        }

        .upload {
            padding: 0;
            margin: 0;
            color: black;
            height: 100%;
            border: 0;
        }

        #btn_upload {
            display: inline-block;
            position: absolute;
            margin: 0 0 0 -68px;
            z-index: 2000;
            border:1px solid #3c8dbc;
            background-color: #3c8dbc;
            color: white;
            height: 36px;
            min-width: 70px;
            font-family: inherit;
            font-weight: bold;
            float: right;
        }

        #btn_upload:hover {
            background-color: dimgray;
            border:1px solid dimgray;
            color: white;
        }

        input.input_upload {
            left: 0;
            position: absolute;
            min-width: 100%;
            min-height: 100%;
            padding: 5px;
            font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
            margin-left: 1px;
            border: 0;
            box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);
        }

        #input_main {
            filter: alpha(opacity=0);
            opacity: 0;
            cursor: pointer;
            display: block;
            z-index: 20;
        }

        #btn_dropdown {
            border-left: 1px solid white;
            border-right: 1px solid white;
            z-index: 1000;
            min-width: 30px;
        }

        #btn_sidebar_display {
            background-color: #3c8dbc;
            opacity: 0.3;
        }

        #btn_sidebar_display:hover {
            /*background-color: dimgray;*/
            opacity: 1;
        }

    </style>

</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top" style="z-index:1002;">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                    aria-expanded="false" aria-controls="navbar" id="btn_nav_toggle">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/main">J I C T O R</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#" id="a_upload">Upload</a></li>
                <li><a href="#" id="a_dashboard">Dashboard</a></li>
                <li><a href="#" id="a_sunburst">Sunburst</a></li>
                <li><a href="#" id="a_graph">Graph</a></li>
                <li><a href="#">Help</a></li>
            </ul>
            <form class="navbar-form navbar-right">
                <div class="input-group">
                    <div class="input-group-btn">
                        <div class="btn btn-default btn-file searchComponents"
                                style="cursor:inherit; padding:6px; text-transform: none;" id="btn_searchoption">Commit ID
                        </div>
                    </div>
                    <!-- /btn-group -->
                    <div id="div_search" class="form-control" style="margin:0; padding:0;">
                        <div id="bp-ac" class="bp-ac" style="width:100%; height:100%;"></div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</nav>

<div id="btn_sidebar_display" class="btn" value="hide" style="position:absolute; top:50px; margin:20px; z-index:1001; min-width: 30px;">
    <i class="fa fa-chevron-right"></i>
</div>

<div id="sidebar" class="sideVisual main" style="display:none; padding-top:80px;">
    <div class="box">
        <div class="box-header with-border">
            <h3 class="box-title">Commit Information</h3>
        </div>
        <div class="box-body">
            <table id="commitInfo" class="table table-bordered">
                <tbody>
                <tr role="row">
                    <td style="width: 5px"><b>CommitNum</b></td>
                    <td id="commitNum"></td>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>

    <div class="box">
        <div class="box-header with-border">
            <h3 class="box-title">Class Information</h3>
        </div>
        <div class="box-body">
            <div id="example2_wrapper" class="table-responsive">
                <table id="classInfo" class="table table-bordered" style="margin:0;">
                    <tbody>
                    <tr role="row">
                        <td style="width: 5px"><b>NOC</b></td>
                        <td id="classNum"></td>
                        </td>
                    </tr>
                    <tr role="row">
                        <td colspan="2"><b>Name</b></td>
                    </tr>
                    <tr role="row">
                        <td id="sunburstPackageClassName" colspan="2"></td>
                    </tr>
                    <tr role="row">
                        <td><b>Count</b></td>
                        <td id="sunburstCount"></td>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <div class="box">
        <div class="box-header with-border">
            <h3 class="box-title">Sunburst Mode</h3>
        </div>
        <div class="box-body">
            <form id="sunburstMode" action="">
                <input type="radio" name="option" value="count" checked>&nbsp;Class Count<br>
                <input type="radio" name="option" value="lineCount"/>&nbsp;Line Count<br>
                <input type="radio" name="option" value="classUseCount"/>&nbsp;Class Use Count<br>
                <input type="radio" name="option" value="usedClassCount"/>&nbsp;Used Class Count
            </form>
        </div>
    </div>

    <div class="box">
        <div class="box-header with-border">
            <h3 class="box-title">Package</h3>
        </div>
        <div class="box-body">
            <ul id="dirTree"></ul>
        </div>
    </div>
</div>
<div class="content-page">
    <div id="dashboard" class="content">
        <div class="<%--row top-summary--%>container-fluid" id="div_dashboard">
            <div class="row">
                <div class="<%--col-lg-8--%>col-12 col-sm-12 col-lg-12">
                    <div class="portlet portlet-dark-blue">
                        <div class="portlet-heading">
                            <div class="portlet-title">
                                <%--<i class="fa fa-long-arrow-right"></i>--%>
                                <h4>The number of classes by commitId</h4>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                        <div class="panel-collapse collpase in">
                            <div class="portlet-body">
                                <div id="line_commit_chart" style="width:100%;height:300px;">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="<%--col-lg-8--%>col-12 col-sm-12 col-lg-12">
                    <div class="portlet portlet-purple">
                        <div class="portlet-heading">
                            <div class="portlet-title">
                                <%--<i class="fa fa-long-arrow-right"></i>--%>
                                <h4>The number of changed files by commitId</h4>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                        <div class="panel-collapse collpase in">
                            <div class="portlet-body">
                                <div id="line_diff_chart" style="width:100%;height:300px;">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="visualization" class="visual main">
    <div id="sunburst" class="inVisual">
        <div id="sunburstSequence"></div>
        <%--<div id="sunburstVisual" style="width:100%; height:95%; padding-top:5%"></div>--%>
        <%--GIT--%>
        <div id="sunburstVisual"></div>
        <div class="wrap">
            <div class="scrollbar">
                <div class="handle" style="transform: translateZ(0px) translateX(608px); width: 190px;">
                    <div class="mousearea"></div>
                </div>
            </div>
            <div class="frame" id="cycleitems" style="overflow: hidden;">
                <ul class="clearfix" id="commitIdListUL"
                    style="transform: translateZ(0px) translateX(-3648px); width: 6840px;">
                </ul>
            </div>
            <div class="controls center">
                <%--<button class="btn prev"><i class=""></i> prev</button>--%>
                <%--<div class="btn-group">--%>
                    <%--<button class="btn pause"><i class=""></i> pause</button>--%>
                    <%--<button class="btn resume"><i class=""></i> resume</button>--%>
                <%--</div>--%>
                <%--<button class="btn next">next <i class=""></i></button>--%>
                <div class="btn prev icon"><i class="fa fa-step-backward"></i></div>
                <div class="btn-group">
                    <div class="btn pause icon" style="margin-left:0;"><i class="fa fa-pause"></i></div>
                    <div class="btn resume icon" style="margin-right: 0;"><i class="fa fa-play"></i></div>
                </div>
                <div class="btn next icon"><i class="fa fa-step-forward"></i></div>
            </div>
        </div>
        <%--GIT--%>
    </div>
    <div id="tree" class="inVisual" style="display: none;"></div>
    <div id="bubble" class="inVisual" style="display: none;"></div>
    <div id="mainScreen" class="inVisual" style="display: none;">
        <div class="site-wrapper" id="div_site_wrapper" style="display: table-cell; vertical-align: middle;">
            <div class="site-wrapper-inner">
                <h3 style="font-size: 50px; margin-bottom:20px;">Upload Your Java Project</h3>

                <div class="input-group" style="width:100%; border: 1px solid #ccc;">
                    <div class="input-group-btn upload">
                        <button type="button" class="btn btn-default btn-file upload"
                                id="btn_action">File
                        </button>
                        <button type="button" id="btn_dropdown" class="btn btn-default dropdown- upload"
                                data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"
                                style="border-left: 1px solid #ccc; border-right: 1px solid #ccc;">
                            <span class="caret"></span>
                            <span class="sr-only">Toggle Dropdown</span>
                        </button>
                        <ul class="dropdown-menu">
                            <li><a href="#" id="a_file">File</a></li>
                            <li><a href="#" id="a_folder">Folder</a></li>
                            <li role="separator" class="divider"></li>
                            <li><a href="#" id="a_private_git">Private Git Repository</a></li>
                            <li><a href="#" id="a_public_git">Public Git Repository</a></li>
                        </ul>
                    </div>
                    <div class="form-control" style="padding:0; border:0;">
                        <form:form id="uploadForm" method="post" action="fileUpload" modelAttribute="filesUploadCommand"
                                   enctype="multipart/form-data" class="form-control" style="padding:0px; border:0px;">
                            <input type="file" class="input_upload" name="files" accept=".java,.zip" id="input_main"
                                   multiple required/>
                            <input type="text" class="form-control input_upload" name="gitPath" id="input_text"
                                   placeholder="Click and Input .java or .zip Files" style="z-index:10;"
                                   aria-label="..." readonly/>

                            <p class="lead">
                                <a href="#" id="btn_upload" class="btn btn-default">Upload</a>
                            </p>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js" type="text/javascript"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js" type="text/javascript"></script>
<script src="/resources/js/jquery/jquery.tree.js" type="text/javascript"></script>
<script src="/resources/js/d3/d3.v3.min.js" type="text/javascript"></script>
<script src="/resources/js/bootstrap/bootstrap.min.js" type="text/javascript"></script>

<%--GIT--%>
<script src="/resources/js/tree/gitPackage.js" type="text/javascript"></script>
<script src="/resources/js/graph/gitTree.js" type="text/javascript"></script>
<script src="/resources/js/graph/gitBubble.js" type="text/javascript"></script>
<script src="/resources/js/sunburst/sunburstGit.js" type="text/javascript"></script>
<script src="/resources/js/timeline/timelineSly.js" type="text/javascript"></script>
<script src="/resources/js/timeline/plugin.js" type="text/javascript"></script>
<script src="/resources/js/timeline/horizontal.js" type="text/javascript"></script>
<%--GIT--%>
<script src="/resources/js/search/gitSearch.js" type="text/javascript"></script>

<script src="/resources/js/flot/jquery-migrate-1.2.1.min.js" type="text/javascript"></script>
<script src="/resources/js/flot/jquery-ui-1.10.3.custom.js" type="text/javascript"></script>
<script src="/resources/js/flot/jquery.sparkline.min.js" type="text/javascript"></script>
<script src="/resources/js/flot/jquery.flot.min.js" type="text/javascript"></script>
<script src="/resources/js/flot/jquery.flot.resize.min.js" type="text/javascript"></script>
<script src="/resources/js/flot/jquery.flot.time.min.js" type="text/javascript"></script>
<script src="/resources/js/flot/custom.min.js" type="text/javascript"></script>
<script src="/resources/js/flot/core.min.js" type="text/javascript"></script>
<script src="/resources/js/chart/chart.js" type="text/javascript"></script>

<script type="text/javascript">
    $(document).ready(function () {
        var userURLKey = document.URL.substr(document.URL.lastIndexOf("/")+1)
        if (navigator.userAgent.search("Chrome") < 0) {
            $("#a_folder").remove();
        }

        $(document).on('change', '#input_main', function () {
            var input = $(this),
                    numFiles = input.get(0).files ? input.get(0).files.length : 1,
                    label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
            input.trigger('fileselect', [numFiles, label]);
        });

        function changeActionText(text) {
            $("#btn_action").text(text);
        }

        function hideSidebar() {
            $("#sidebar").hide();
            $("#btn_sidebar_display").prop("value","hide");
            $("#btn_sidebar_display i").removeClass("fa-chevron-left")
                    .addClass("fa-chevron-right");
            $("#trail").css("padding-left", "80px");
            resizeBtnSidebarDisplay();
            visualizationDivResize();
        }

        function showSidebar() {
            $("#sidebar").show();
            $("#btn_sidebar_display").prop("value","show");
            $("#btn_sidebar_display i").removeClass("fa-chevron-right")
                    .addClass("fa-chevron-left");
            $("#trail").css("padding-left", "0");
            resizeBtnSidebarDisplay();
            visualizationDivResize();
        }

        function visualizationDivResize() {
            var div = document.getElementById('visualization');
            var div_search = document.getElementById('div_search');
            var div_side = document.getElementById('sidebar');
            var div_dashboard = document.getElementById('dashboard');
            var sideLength;

            if(div_side.style.display == 'none'){
                sideLength = 0;
            }else{
                sideLength = div_side.offsetWidth;
            }

            div.style.marginLeft = sideLength + 'px';
            div.style.width = window.innerWidth - sideLength + 'px';
            div_search.style.width = window.innerWidth * 0.2 + 'px';
            div_dashboard.style.marginLeft = 0 + 'px';
            $("#div_site_wrapper").css("height", $("#mainScreen").css("height"))
                    .css("width", $("#mainScreen").css("width"));

            $("#div_dashboard").css("width", $("#mainScreen").css("width"));
        }

        function resizeBtnSidebarDisplay(){
            if($("#sidebar").css("display") != "none") {
                if ($("#sidebar").get(0).scrollHeight > $("#sidebar").get(0).clientHeight) {
                    $("#btn_sidebar_display").css("min-width", "220px")
                } else {
                    $("#btn_sidebar_display").css("min-width", "240px")
                }
            }else{
                $("#btn_sidebar_display").css("min-width", "30px")
            }
        }

        function closeNavDropbox(){
            if($("#btn_nav_toggle").attr("aria-expanded") == 'true'){
                $("#btn_nav_toggle").click();
            }
        }

        $("#sidebar").bind("DOMSubtreeModified", function(){
            resizeBtnSidebarDisplay();
        });

        $("#btn_sidebar_display").click(function(){
            if($("#sidebar").css("display") != 'none'){
                hideSidebar();
            }else{
                showSidebar();
            }
        });

        function resetInputMain() {
            var input = $("#input_text");
            input.wrap('<form>').closest('form').get(0).reset();
            input.unwrap();
            input.text("");
        }

        $("#a_file").click(function () {
            changeActionText($("#a_file").text());
            resetInputMain();
            $("#input_main")
                    .prop("webkitdirectory", false)
                    .prop("directory", false)
                    .css("z-index", 20)
                    .prop("accept", ".java,.zip");
            $("#input_text").css("z-index", 10)
                    .prop("placeholder", "Click and Input .java or .zip Files");
            $("#uploadForm").prop("action", "fileUpload")
                    .prop("modelAttribute", "filesUploadCommand");
        });

        $("#a_folder").click(function () {
            changeActionText($("#a_folder").text());
            resetInputMain();
            $("#input_main")
                    .prop("webkitdirectory", true)
                    .prop("directory", true)
                    .css("z-index", 20)
                    .prop("accept", false);
            $("#input_text").css("z-index", 10)
                    .prop("placeholder", "Click and Input a Java Project Folder");
            $("#uploadForm").prop("action", "fileUpload")
                    .prop("modelAttribute", "filesUploadCommand");
        });

        $("#a_private_git").click(function () {
            changeActionText($("#a_private_git").text());
            $("#input_text").prop("readonly", false)
                    .prop("placeholder", "Input .git Folder Parent Absolute Path - ex) C:\\Jictor\\ProjectFolder")
                    .css("z-index", 20)
                    .click(function () {
                        $(this).val("")
                    });
            $("#input_main").css("z-index", 10);
            $("#uploadForm").prop("action", "privateGit")
                    .prop("modelAttribute", "gitCommand");
        });

        $("#a_public_git").click(function () {
            changeActionText($("#a_public_git").text());
            $("#input_text").prop("readonly", false)
                    .css("z-index", 20)
                    .prop("placeholder", "Input Git Repository URL - ex) https://Jittore@bitbucket.org/Jictor/start.git")
                    .click(function () {
                        $(this).val("")
                    });
            $("#input_main").css("z-index", 10);
            $("#uploadForm").prop("action", "publicGit")
                    .prop("modelAttribute", "gitCommand");
        });

        $("#li_home").click(function () {
            $("#li_credit").removeClass("active");
            $(this).addClass("active");
        });

        $("#li_credit").click(function () {
            $("#li_home").removeClass("active");
            $(this).addClass("active");
        });

        $("#btn_upload").click(function () {
            var inputFiles = document.getElementById("input_main").files;
            if ((inputFiles != null && inputFiles.length > 0) || $("#input_text").val() != "") {
                $("#uploadForm").submit();
            }
        });

        $("#input_main").on('fileselect', function (event, numFiles, label) {

            var input = $("#input_text"),
                    log = numFiles > 1 ? numFiles + ' files selected' : label;

            if (input.length) {
                input.val(log);
            } else {
                if (log) alert(log);
            }
        });

        visualizationDivResize();

        $(window).resize(function () {
            visualizationDivResize();
        });

        $.getJSON("/commitIdList/" + userURLKey, function (json) {
            if (json.length == 0) {
                $('#sunburst').hide();
                $('#mainScreen').show();
                $('#statusResult').hide();
                $('#sunburstModeChange').hide();

                $("#btn_sunburst").click(function () {
                    $('#statusResult').hide();
                    $('#sunburstModeChange').hide();
                });
                return;
            }

            $("#commitNum").append('<b>'+ json.length + '</b>');
            for (var i in json) {
                $('#commitIdListUL').append('<li class="" data-index=' + json[i] + '>' + json[i].substring(0, 7) + '</li>');
            }

            $('#dashboard').hide();
            $('#mainScreen').hide();
            $('#statusResult').show();
            $('#sunburstModeChange').show();
            $("#btn_graph").click(function () {
                $('#statusResult').show();
                $('#sunburstModeChange').hide();
            });

            commitChart(userURLKey);
            diffChart(userURLKey);
            searchStart(userURLKey);
            drawSunburst(json[0]);
            packageJsonDrawTree("#dirTree", json[0]);
            horizontal("#cycleitems");
            $('#sunbusrt').show();
        });

        $("#commitIdListUL").click(function (e) {
            var commitId = e.target.dataset['index'];
            changeJsonData(commitId);
            packageJsonDrawTree("#dirTree", commitId);
        });

        $("#a_dashboard").click(function () {
            $('#dashboard').show();
            $('#sunburst').hide();
            $('#mainScreen').hide();
            $('#tree').hide();
            $('#bubble').hide();
            $('#btn_sidebar_display').hide();
            $('#sidebar').hide();
            closeNavDropbox();
            visualizationDivResize();
        });

        $("#a_sunburst").click(function () {
            $('#btn_sidebar_display').show();
            if($('#btn_sidebar_display').prop("value") == 'show'){
                showSidebar();
            }else{
                hideSidebar();
            }
            $('#sunburst').show();
            $('#sunburstModeChange').show();
            $('#mainScreen').hide();
            $('#tree').hide();
            $('#dashboard').hide();
            $('#bubble').hide();
            closeNavDropbox();
            visualizationDivResize();
        });
        $("#a_graph").click(function () {
            $('#btn_sidebar_display').show();
            if($('#btn_sidebar_display').prop("value") == 'show'){
                showSidebar();
            }else{
                hideSidebar();
            }
            $('#sunburst').hide();
            $('#mainScreen').hide();
            $('#dashboard').hide();
            var selectedVal = $("input[type='radio'][name='option']:checked").val();
            if (selectedVal == 'count' || selectedVal == 'lineCount') {
                $('#tree').show();
                $('#bubble').hide();
            } else {
                $('#tree').hide();
                $('#bubble').show();
            }
            closeNavDropbox();
            visualizationDivResize();
        });

        $("#ul_searchoption li").click(function () {
            $("#btn_searchoption").text($(this).text());
        });

        $("#a_jictor").click(function () {
            window.location = "main";
        });

        $("#a_upload").click(function () {
            $('#dashboard').hide();
            $('#sidebar').hide();
            $('#visualization').show();
            $('#mainScreen').show();
            $('#tree').hide();
            $('#bubble').hide();
            $('#sunburst').hide();
            $('#statusResult').hide();
            $('#sunburstModeChange').hide();
            $('#btn_sidebar_display').hide();
            closeNavDropbox();
            visualizationDivResize();
        });
    });
</script>
<script>
    (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
                (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
            m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
    })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

    ga('create', 'UA-66360804-1', 'auto');
    ga('send', 'pageview');
</script>
</body>
</html>