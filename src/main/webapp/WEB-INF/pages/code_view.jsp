<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!doctype html>
<html lang="en">
<meta charset="utf-8">
<title>Java File Code Viewer</title>
<script src="/resources/js/jquery/jquery.min.js" type="text/javascript"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>

<style>

    html {
        overflow: hidden;
    }

    body {
        overflow: hidden;
    }

    pre {
        position: absolute;
        width: 98%;
        border-radius: 3px;
        border: 1px solid #C3CCD0;
        background: #FFF;
        overflow-y: auto;
        overflow-x: hidden;
    }

    code {
        display: block;
        padding: 12px 24px;
        font-weight: 300;
        font-family: Menlo, monospace;
        font-size: 0.8em;
        overflow:auto;
    }

    code.has-numbering {
        margin-left: 31px;
    }

    pre{
        background-color : #EEE;
    }

    .pre-numbering {
        position: absolute;
        top: 0;
        left: 0;
        width: 30px;
        padding: 0.5em 2px 0.5em 0;
        margin:0;
        border-right: 1px solid #C3CCD0;
        border-radius: 3px 0 0 3px;
        background-color: #EEE;
        text-align: right;
        font-family: Menlo, monospace;
        font-size: 0.8em;
        color: #AAA;
    }
</style>
<link id="themeLink" rel="stylesheet" href="/resources/css/highlight/default.css">
<script src="/resources/js/highlight/highlight.pack.js"></script>
<script type="text/javascript">

//    var codeViewer = window.open("code_view", id);
//    codeViewer.codeURLIndex = index;
//    codeViewer.javaFileName = fileName;

    $(document).ready(function () {
        var codeURLIndex = document.URL.substr(document.URL.lastIndexOf("/")+1);
        var userKeyURLIndex = (document.URL.substr(0, document.URL.lastIndexOf("/"))).substr(document.URL.substr(0, document.URL.lastIndexOf("/")).lastIndexOf("/") + 1);
        var sourceCodeURL = "/code/classInfo/" + userKeyURLIndex + "/" + codeURLIndex;
        var fqn = window.name.substr(window.name.lastIndexOf("/")+1) + ".java";
        var theme = document.getElementById("themeLink");

        $("#themeDiv").append(fqn)
                .attr("style","font-size : 20px; font-weight:bold;");

        function resizePre() {
            var pre = document.getElementsByTagName('pre');
            pre[0].style.height = window.innerHeight - 67 + 'px';
        }

        $(window).resize(resizePre);

        $.ajax({
            url: sourceCodeURL,
            dataType: "html"
        }).done(function (data) {
            document.body.innerHTML += "<pre><code>" + data + "</code></pre>";
            $('pre code').each(function (i, block) {
                var lines = $(this).text().split(/\r\n|\r|\n/).length - 1;
                var $numbering = $('<ul/>').addClass('pre-numbering');
                $(this)
                        .addClass('has-numbering')
                        .parent()
                        .append($numbering);
                for(i=1;i<=lines;i++){
                    $numbering.append($('<li/>').text(i));
                }
                hljs.highlightBlock(block);
                if(localStorage["code theme"] != null){
                    theme.href = "/resources/css/highlight/" + localStorage["code theme"];
                }

                $(localStorage["code theme"]).change(function(){
                    theme.href = "/resources/css/highlight/" + localStorage["code theme"];
                });

                $("#themeSelect").change(function() {
                    var t = $("#themeSelect option:selected").val();
                    theme.href = "/resources/css/highlight/" + t;
                    localStorage["code theme"] = t;
                });
            });
            resizePre();
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
</head>
<body>
    <div id="themeDiv">
        <select id="themeSelect" style="float:right;">
            <option value="default.css">Default</option>
            <option value="github.css">Github</option>
            <option value="dark.css">Dark</option>
            <option value="androidstudio.css">Android Studio</option>
            <option value="docco.css">Docco</option>
            <option value="googlecode.css">Google Code</option>
            <option value="hybrid.css">Hybrid</option>
            <option value="solarized_light.css">Solarized light</option>
            <option value="monokai.css">Monokai</option>
            <option value="rainbow.css">Rainbow</option>
            <option value="xcode.css">Xcode</option>
            <option value="zenburn.css">Zenburn</option>
            <option value="vs.css">vs</option>
            <option value="arta.css">Arta</option>
            <option value="ascetic.css">Ascetic</option>
            <option value="obsidian.css">Obsidian</option>
            <option value="pojoaque.css">Pojoaque</option>
            <option value="railscasts.css">Railscasts</option>
            <option value="sunburst.css">Sunburst</option>
        </select>
    </div>
</body>
</html>