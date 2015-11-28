<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--todo 내용 채우기, href 연결하기, button 이미지 변경하기--%>
<!DOCTYPE html>
<!--
Template Name: Arrival
Author: <a href="http://www.os-templates.com/">OS Templates</a>
Author URI: http://www.os-templates.com/
Licence: Free to use under our free template licence terms
Licence URI: http://www.os-templates.com/template-terms
-->
<html>
<head>
    <title>JICTOR - Software Health Kit</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link href="/resources/css/main/layout.css" rel="stylesheet" type="text/css" media="all">
    <!-- Bootstrap core CSS -->
    <link href="/resources/css/bootstrap/bootstrap.min.css" rel="stylesheet" type="text/css">

    <!-- Custom styles for this template -->
    <link href="/resources/css/bootstrap/cover.css" rel="stylesheet" type="text/css">

    <style>
        input[readonly] {
            background-color: white !important;
            cursor: text !important;
        }
        #btn_action, #div_email_text{
            width:100px;
            background-color: white;
            font-weight:bold;
            text-transform: none;
            cursor:inherit;
        }
        .masthead, .mastfoot{
            position: absolute;
            color:#333;
            width: 100%;
        }
        .masthead{
            top: 0;
        }
        .mastfoot{
            bottom: 0;
        }
        div.cover{
            margin-top: 30px;
        }
        #text_upload{
            font-size: 30px;
        }
        /*#div_email{*/
            /*/!*top:100px;*!/*/
            /*float:none;*/
        /*}*/
        /*@media(min-width:600px){*/
            /*#div_email{*/
                /*/!*top: 20px;*!/*/
                /*float:right;*/
            /*}*/
        /*}*/
        @media(min-width:992px){
            #btn_action, #div_email_text{
                width:170px;
                background-color: white;
                text-transform: none;
                cursor:inherit;
            }
            .masthead, .mastfoot{
                position: absolute;
                width: 900px;
                color: black;
            }
            div.cover{
                margin-top: 100px;
            }
            #text_upload{
                font-size: 40px;
                margin-bottom: 10px;
            }
        }
        .btn-upload{
            border:0;
            height:34px;
        }
        #a_upload{
            position: absolute;
            margin-left: -78px;
            z-index: 1000;
            background-color: dodgerblue;
            color: white;
            border-color: dodgerblue;
        }
        #a_upload:hover{
            background-color: darkblue;
            border-color: darkblue;
        }
        #input_text{
            width: 100%;
            height: 100%;
            border: 0;
            font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
            color:black;
        }
        #input_main {
            position: absolute;
            top: 0;
            right: 0;
            min-width: 100%;
            min-height: 100%;
            filter: alpha(opacity=0);
            opacity: 0;
            cursor: pointer;
            display: block;
            z-index: 20
        }
        /*article.one_half{*/
        /*margin-bottom : 50px;*/
        /*}*/
        .articleHeight{
            float: left;
            vertical-align: middle;
        }
        img{
            max-height: 300px;
            margin: auto;
        }
        figure{
            text-align: center;
        }
        article.minBoard{
            min-height: 145px;
        }
        .en-text{
            display: none;
        }
        .ko-text{
            display: none;
        }
    </style>
</head>
<body>
<!-- ################################################################################################ -->
<!-- ################################################################################################ -->
<!-- ################################################################################################ -->
<!-- Top Background Image Wrapper -->
<div class="bgded" style="width:100%; height:100%; min-height:500px;">
    <div class="banner-image" style="position: absolute; z-index: 0; background: none; min-height:400px; height:inherit; width:100%; opacity:0.5;">
        <div class="backstretch" style="left: 0px; top: 0px; overflow: hidden; margin: 0px; padding: 0px; min-height:100%; height:auto; width: 100%; z-index: -999998; position: absolute;">
            <%--todo 이미지 바꾸기--%>
                <img src="/resources/images/main/main.jpg" style="position: absolute; margin: 0px; padding: 0px; border: none; width: inherit; height:100%; max-height: none; max-width: none; z-index: -999999; left: 0; top: 0px;">
        </div>
    </div>
    <div class="site-wrapper" style="background: none; position:absolute; min-height:400px;" >
        <div class="site-wrapper-inner">
            <div class="cover-container">
                <div class="masthead clearfix">
                    <div class="inner" style="padding:10px;">
                        <div class="masthead-brand" style="float:left;">
                            <img src="/resources/images/main/Jictor_left.png" style="height:45px; width: auto;"/>
                        </div>
                        <nav>
                            <ul class="nav masthead-nav" style="position : absolute; top:30px; right:30px; z-index:999;">
                                <li class="active" id="li_korean"><a href="#">Korean</a></li>
                                <li class="" id="li_english"><a href="#" >English</a></li>
                                <%--&lt;%&ndash;<li><a href="#">Git Repository</a></li>&ndash;%&gt;--%>
                            </ul>
                        </nav>
                    </div>
                </div>

                <div class="inner cover">
                    <form:form id="uploadForm" method="post" action="fileUpload" modelAttribute="filesUploadCommand"
                               enctype="multipart/form-data" style="padding:0px; border:0px;">
                    <h2 id="text_upload" style="margin-bottom: 30px">Start Now With Your Java Project</h2>
                    <div id="div_email" class="input-group" style="z-index:999; width:100%; min-width:275px; margin-bottom:10px;">
                        <div class="input-group-btn">
                            <div id="div_email_text" class="form-control" style="border:0; border-right:1px solid #ccc; border-radius:4px; color:rgb(51, 51, 51);"><b>E - mail</b></div>
                        </div>
                        <div class="form-control" style="padding:0; border:0; width:100%;">
                            <input type="text" name="userMail" id="input_email" required placeholder="Input your e-mail address" style="border:0; height:34px; width:100%; border-radius:4px; min-width:185px;" class="form-control" aria-label="..."/>
                        </div>
                    </div>
                    <div class="input-group">   <%-- style="border: 1px solid #ccc;"--%>
                        <div class="input-group-btn">
                            <div class="btn btn-default btn-file btn-upload" id="btn_action">File</div>
                            <button type="button" class="btn btn-default dropdown-toggle btn-upload" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" style="z-index:1000;  border-left: 1px solid #ccc; border-right: 1px solid #ccc;">
                                <span class="caret"></span>
                                <span class="sr-only">Toggle Dropdown</span>
                            </button>
                            <ul class="dropdown-menu" style="text-shadow: 0 0 0 white;">
                                <li><a href="#" id="a_file">File</a></li>
                                <li><a href="#" id="a_folder">Folder</a></li>
                                <li role="separator" class="divider"></li>
                                <li><a href="#" id="a_private_git" style="pointer-events: none; color: #D8D8D8;">Private Git Repository</a></li>
                                <li><a href="#" id="a_public_git">Public Git Repository</a></li>
                            </ul>
                        </div>
                        <div class="form-control" style="padding:0; border:0;">
                                <input type="file" name="files" accept=".java,.zip" id="input_main" multiple required/>
                                <input type="text" name="gitPath" id="input_text" placeholder="Click and Input .java or .zip Files" class="form-control" style="z-index:10;" aria-label="..." readonly/>
                                <p class="lead">
                                    <a href="#" id="a_upload"  class="btn btn-default">Upload</a>
                                </p>
                        </div>
                    </div>
                    </form:form>
                </div>
                <div class="mastfoot">
                    <div class="inner">
                        <p>Powered by <b>Jictor</b> engine.</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- End Top Background Image Wrapper -->
<!-- ################################################################################################ -->
<!-- ################################################################################################ -->
<!-- ################################################################################################ -->
<div class="wrapper row3" style="background-color: rgb(224,224,224) ;" >
    <main class="container clear">
        <!-- main body -->
        <!-- ################################################################################################ -->
        <article class="one_third first minBoard">
            <h2>JICTOR</h2><h3>- Software Health Kit</h3>
            <p class="btmspace-30 en-text">JICTOR is the combination of words Java and Pictor (Latin word for artist).
                JICTOR analyzes the Java Source Code and provides visualizations of dependencies between classes.
                JICTOR allows productive management of the complexity of the software project.
            </p>
            <p class="btmspace-30 ko-text">JICTOR는 Java와 화가의 라틴어인 Pictor를 합성한 단어로 Java Source Code를 분석하여 Class 사이의
                Dependency를 시각적으로 보여줌으로써 software 프로젝트의 복잡도를 효율적으로 관리하도록 도와주는 툴입니다.
            </p>
            <%--<p class="nospace"><a class="btn" href="#">Eget fermentum</a></p>--%>
        </article>
        <div class="two_third">
            <div class="group services">
                <article class="one_half first btmspace-30 minBoard"><div class="icon"><i class="fa fa-file-text-o"></i></div>
                    <h4 class="heading">Class Dependency Analysis</h4>
                    <p class="en-text">JICTOR analyzes and extracts dependencies between classes of the Java Source Code.</p>
                    <p class="ko-text">Java Source Code에서 Class 간의 Dependency를 분석, 추출합니다.</p>
                    <%--<a class="viewit" href="#"><i class="fa fa-angle-right"></i></a>--%>
                </article>
                <article class="one_half btmspace-30 minBoard"><div class="icon"><i class="fa fa-picture-o"></i></div>
                    <h4 class="heading">Dependency Visualizer</h4>
                    <p class="en-text">JICTOR visualizes the dependencies of the analyzed classes in three types: Sunburst, Tree and Bubble.</p>
                    <p class="ko-text">분석한 Class 간의 Dependency를 Sunburst, Tree, Bubble의 세 가지 형태로 시각화합니다.</p>
                    <%--<a class="viewit" href="#"><i class="fa fa-angle-right"></i></a>--%>
                </article>
                <article class="one_half first minBoard"><div class="icon"><i class="fa fa-bar-chart-o"></i></div>
                    <h4 class="heading">Software Statistics</h4>
                    <p class="en-text">JICTOR displays a statistical report of the current condition of the source code in the Dashboard.</p>
                    <p class="ko-text">Dashboard를 통해서 software의 현재 상태를 통계적 수치로 보여줍니다.</p>
                    <%--<a class="viewit" href="#"><i class="fa fa-angle-right"></i></a>--%>
                </article>
                <article class="one_half minBoard"><div class="icon"><i class="fa fa-git"></i></div>
                    <h4 class="heading">Git Repository Mining</h4>
                    <p class="en-text">JICTOR tracks changes of the source code between each commit by analyzing the Git Repository.</p>
                    <p class="ko-text">Git Repository를 분석하여 각 commit에 따른 Software 변화를 추적합니다.</p>
                    <%--<a class="viewit" href="#"><i class="fa fa-angle-right"></i></a>--%>
                </article>
            </div>
        </div>
        <!-- ################################################################################################ -->
        <!-- / main body -->
        <div class="clear"></div>
    </main>
</div>
<!-- ################################################################################################ -->
<!-- ################################################################################################ -->
<!-- ################################################################################################ -->
<%--<div class="wrapper row1 bgded" style="background-image:url('/resources/images/main/backgrounds/01.png');">--%>
<%--<div class="overlay dark">--%>
<%--<div class="container clear">--%>
<%--<!-- ################################################################################################ -->--%>
<%--<ul class="pr-charts nospace group center">--%>
<%--<li class="pr-chart-ctrl" data-animate="false">--%>
<%--<div class="pr-chart" data-percent="25"><i></i></div>--%>
<%--<p>Lectus euismod sagittis</p>--%>
<%--</li>--%>
<%--<li class="pr-chart-ctrl" data-animate="false">--%>
<%--<div class="pr-chart" data-percent="50"><i></i></div>--%>
<%--<p>Vitae consequat lorem</p>--%>
<%--</li>--%>
<%--<li class="pr-chart-ctrl" data-animate="false">--%>
<%--<div class="pr-chart" data-percent="75"><i></i></div>--%>
<%--<p>Cras pulvinar rutrum</p>--%>
<%--</li>--%>
<%--<li class="pr-chart-ctrl" data-animate="false">--%>
<%--<div class="pr-chart" data-percent="100"><i></i></div>--%>
<%--<p>Cursus ligula imperdiet</p>--%>
<%--</li>--%>
<%--</ul>--%>
<%--<!-- ################################################################################################ -->--%>
<%--</div>--%>
<%--</div>--%>
<%--</div>--%>
<!-- ################################################################################################ -->
<!-- ################################################################################################ -->
<!-- ################################################################################################ -->
<div class="wrapper row3" >
    <section class="container clear" style="padding-bottom: 10px;">
        <!-- ################################################################################################ -->
        <h2>Configuration</h2>
        <p class="btmspace-50 en-text">JICTOR is composed of four different upload methods
            (File, Folder, Private Git Repository, and Public Git Repository) and three different visualization types
            (Sunburst, Tree and Bubble). JICTOR also provides Auto Complete Search and Code View functionalities for better user experience.</p>
        <p class="btmspace-50 ko-text">JICTOR는 기본적으로 4가지 Java Project Upload 방식과 Class 사이의 Dependency 결과를
            시각적으로 보여주는 Sunburst, Tree, Bubble의 3가지 시각화로 구성되어 있습니다.
            그 외에 사용자 편의를 위한 Auto Complete Search 기능과 Source Code를 보여주는 Code Viewer 기능이 있습니다.</p>
        <div class="group">
            <%--todo 이미지 크기는 600X385 이상 이어야한다.--%>
            <article class="btmspace-50 articleHeight">
                <div class="one_half first bg-grey inspace-30">
                    <h2>Dashboard</h2>
                    <p class="ko-text">Dashboard에서는 software의 복잡도를 차트나 순위를 통해서 통계적으로 보여줍니다.
                        File, Folder Upload 시, 클래스 간의 사용정도의 순위를 보여주며 Git Repository Upload 시, commit 별 변화 추이를 보여줍니다.</p>
                    <p class="en-text">Statistical Analysis of the complexity of the software is shown in the dashboard as a chart or as an independent ranking system.
                        Ranking of number of use of classes is shown for file or folder uploads, and differences in trend by each commit is shown for git repository uploads.  </p>
                </div>
                <figure class="one_half" style="float: right; vertical-align: middle;"><img src="/resources/images/main/dashboard.PNG" alt=""></figure>
            </article>
            <article class="btmspace-50 articleHeight">
                <figure class="one_half first" style="vertical-align: middle;"><img src="/resources/images/main/upload.PNG" alt=""></figure>
                <div class="one_half bg-grey inspace-30">
                    <h2>Project Upload</h2>
                    <p class="en-text">Upload Zip files or Java files via 'File Upload',
                        upload folders with multiple source code files via 'Folder Upload',
                        upload an offline git repository via 'Private Git Repository',
                        or upload an online git repository via 'Public Git Repository'.
                        (Folder Upload only offered in web browser Chrome).</p>
                    <p class="ko-text">Project Upload는 Source Code나 Git Repository를 Upload 합니다.
                        zip파일이나 Java파일을 업로드 하는 File Upload, Source Code가 들어있는 폴더를 업로드하는 Folder Upload,
                        Private Git Repository Upload, Public Git Repository 주소를 입력하는 Public Git Repository Upload,
                        총 4가지로 구성되어 있습니다. 단, Folder Upload는 Chrome에서만 가능합니다.</p>
                </div>
            </article>
            <article class="btmspace-50 articleHeight">
                <div class="one_half first bg-grey inspace-30">
                    <h2>Visualization - Sunburst</h2>
                    <p class="en-text">Sunburst uses differently sized tiles to optimally present number of classes, number of lines in a class,
                        number of other class references or number of references from other classes.
                        Select desired type in the options.</p>
                    <p class="ko-text">Sunburst는 서로 다른 크기를 가진 여러 개의 영역을 한 눈에 비교할 수 있는 시각화 방법입니다.
                        클래스 개수, 클래스의 라인 개수, 다른 클래스를 사용한 개수, 다른 클래스에 사용된 개수, 총 4가지 크기 옵션이 있습니다.</p>
                </div>
                <figure class="one_half" style="vertical-align: middle;"><img src="/resources/images/main/sunburst.PNG" alt=""></figure>
            </article>
            <article class="btmspace-50 articleHeight">
                <figure class="one_half first" style="height:auto;"><img src="/resources/images/main/tree.PNG" alt=""></figure>
                <div class="one_half bg-grey inspace-30">
                    <h2>Visualization - Tree</h2>
                    <p class="en-text">Tree presents a detailed report of a single class.
                        It contains information such as declaration of fields, declaration of methods, and other six different dependency relations.
                        Clicking the Root Node presents the Source Code.</p>
                    <p class="ko-text">Tree는 클래스 하나의 정보를 자세히 보여주는 시각화 방법입니다.
                       Class에 선언 된 Field, Method 그리고 6가지 Dependency Relation으로 구성됩니다.
                       Root Node를 클릭하면 Source Code를 볼 수 있습니다.</p>
                </div>
            </article>
            <article class="btmspace-50 articleHeight">
                <div class="one_half first bg-grey inspace-30">
                    <h2>Visualization - Bubble</h2>
                    <p class="en-text">Bubble presents the relationship between different classes through a graph.
                        Bubble consist of two different types: Out Bubble and In Bubble.
                        In Bubble shows the classes that reference a certain class, and Out Bubble shows the classes a certain class uses.</p>
                    <p class="ko-text">Bubble은 클래스 간의 연관 관계를 Graph로 나타내는 시각화 방법입니다.
                        Bubble은 해당 클래스가 사용한 클래스들을 나타내는 In Bubble과 해당 클래스를 사용한 클래스들을 나타내는 Out Bubble,
                        2종류가 있습니다. 화살표의 시작은 사용된 클래스, 화살표의 끝은 사용한 클래스를 나타냅니다.
                        Center Node를 Double Click하면 Source Code를 볼 수 있습니다.</p>
                </div>
                <figure class="one_half" style="vertical-align: middle;"><img src="/resources/images/main/bubble.PNG" alt=""></figure>
            </article>
            <article class="btmspace-50 articleHeight">
                <figure class="one_half first" style="vertical-align: middle;"><img src="/resources/images/main/search.PNG" alt=""></figure>
                <div class="one_half bg-grey inspace-30">
                    <h2>Auto Complete Search</h2>
                    <p class="en-text">'Auto Complete Search' 	searches for the desired Tree, In Bubble, Out Bubble or Source Code with the given set of characters.
                        All search results are fully qualified names, and a simple click on the desired item will redirect the page. </p>
                    <p class="ko-text">Auto Complete Search는 원하는 Tree나 In Bubble, Out Bubble, Source Code를 찾아서 출력하는 자동 완성 검색 기능입니다.
                        검색 결과는 Fully Qualified Name이며, 리스트에서 해당 아이템을 클릭하면 연결할 수 있습니다.</p>
                </div>
            </article>
        </div>
        <!-- ################################################################################################ -->
        <div class="clear"></div>
    </section>
</div>
<!-- ################################################################################################ -->
<!-- ################################################################################################ -->
<!-- ################################################################################################ -->
<!-- Bottom Background Image Wrapper -->
<div>
    <div class="overlay light">
        <!-- ################################################################################################ -->
        <!-- ################################################################################################ -->
        <!-- ################################################################################################ -->
        <div class="wrapper row4" style="background-color: rgb(224,224,224) ;">
            <footer id="footer" class="clear" style="padding-left:20px; padding-right:20px;">
                <!-- ################################################################################################ -->
                <div class="one_third first">
                    <h6 class="title">Contacts</h6>
                    <address class="btmspace-15">
                        Dongguk University<br>
                        Pildong-ro 1 street<br>
                        Seoul, Korea<br>
                        100-715
                    </address>
                    <ul class="nospace">
                        <%--<li class="btmspace-10"><i class="fa fa-phone"></i> +00 (123) 456 7890</li>--%>
                        <li><i class="fa fa-envelope-o"></i>  jictorgummybear@gmail.com</li>
                    </ul>
                </div>
                <div class="one_third">
                    <h6 class="title">From The Blog</h6>
                    <article>
                        <h2 class="nospace font-x1"><a href="#">Jictor launches today!</a></h2>
                        <time class="smallfont" datetime="2045-04-06">Friday, 14<sup>th</sup> Aug 2015</time>
                        <p>Finally, Jictor.com launches today! We hope you enjoy our cutting-edge source code visualization technology.</p>
                    </article>
                </div>
                <div class="one_third">
                    <h6 class="title">Company Information</h6>
                    <ul class="nospace linklist">
                        <li><a href="#">About Us</a></li>
                        <li><a href="#">Jobs</a></li>
                        <li><a href="#">Privacy</a></li>
                        <li><a href="#">Status</a></li>
                    </ul>
                </div>
                <%--<div class="one_quarter">--%>
                    <%--<h6 class="title">Proin et magna eget</h6>--%>
                    <%--<p>Curabitur a sapien tincidunt, ullamcorper mauris sit amet, ornare augue.</p>--%>
                    <%--<p>Sed eget ultricies sem. Proin quis lacus egestas, adipiscing nunc ornare, gravida diam.</p>--%>
                <%--</div>--%>
                <!-- ################################################################################################ -->
            </footer>
        </div>
        <!-- ################################################################################################ -->
        <!-- ################################################################################################ -->
        <!-- ################################################################################################ -->
        <div class="wrapper row5" style="background-color: rgb(224,224,224) ;">
            <div id="copyright" class="clear">
                <!-- ################################################################################################ -->
                <p class="fl_left">Copyright &copy; 2015 - All Rights Reserved - <a href="http://www.jictor.com">www.Jictor.com</a></p>
                <%--<p class="fl_right">Template by <a target="_blank" href="http://www.os-templates.com/" title="Free Website Templates">OS Templates</a></p>--%>
                <!-- ################################################################################################ -->
            </div>
        </div>
        <!-- ################################################################################################ -->
        <!-- ################################################################################################ -->
        <!-- ################################################################################################ -->
    </div>
</div>
<!-- End Bottom Background Image Wrapper -->
<!-- ################################################################################################ -->
<!-- ################################################################################################ -->
<!-- ################################################################################################ -->
<a id="backtotop" href="#"><i class="fa fa-chevron-up"></i></a>
<!-- JAVASCRIPTS -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js" type="text/javascript"></script>
<script src="/resources/js/jquery/jquery.backtotop.js"></script>
<script src="/resources/js/bootstrap/bootstrap.min.js" type="text/javascript"></script>

<script type="text/javascript">



    $(document).ready(function () {

        var userLang = navigator.language || navigator.userLanguage;

        if(userLang != "ko" && userLang != "ko-KR"){
            $(".en-text").show();
            $('#li_korean').removeClass("active");
            $('#li_english').addClass("active");
        }else{
            $(".ko-text").show();
            $('#li_english').removeClass("active");
            $('#li_korean').addClass("active");
        }

        $('#li_korean').click(function(){
            $(".en-text").hide();
            $(".ko-text").show();
            $('#li_english').removeClass("active");
            $(this).addClass("active");
        });

        $('#li_english').click(function(){
            $(".ko-text").hide();
            $(".en-text").show();
            $('#li_korean').removeClass("active");
            $(this).addClass("active");
        })

        if(navigator.userAgent.search("Chrome") < 0){
            $("#a_folder").remove();
        }

        $(document).on('change', '#input_main', function() {
            var input = $(this),
                    numFiles = input.get(0).files ? input.get(0).files.length : 1,
                    label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
            input.trigger('fileselect', [numFiles, label]);
        });

        function changeActionText(text){
            $("#btn_action").text(text);
        }

        function resetInputMain(){
            var input = $("#input_text");
            input.wrap('<form>').closest('form').get(0).reset();
            input.unwrap();
            input.text("");
        }

        $("#a_file").click(function(){
            changeActionText($("#a_file").text());
            resetInputMain();
            $("#input_main")
                    .prop("webkitdirectory", false)
                    .prop("directory", false)
                    .css("z-index",20)
                    .prop("accept",".java,.zip")
                    .show();
            $("#input_text").css("z-index",10)
                    .prop("placeholder", "Click and Input .java or .zip Files");
            $("#uploadForm").prop("action","fileUpload")
                    .prop("modelAttribute","filesUploadCommand");
        });

        $("#a_folder").click(function(){
            changeActionText($("#a_folder").text());
            resetInputMain();
            $("#input_main")
                    .prop("webkitdirectory", true)
                    .prop("directory", true)
                    .css("z-index",20)
                    .prop("accept",false)
                    .show();
            $("#input_text").css("z-index",10)
                    .prop("placeholder", "Click and Input a Java Project Folder");
            $("#uploadForm").prop("action","fileUpload")
                    .prop("modelAttribute","filesUploadCommand");
        });

        $("#a_private_git").click(function(){
            changeActionText($("#a_private_git").text());
            $("#input_text").prop("readonly", false)
                    .prop("placeholder", "Input .git Folder Parent Absolute Path - ex) C:\\Jictor\\ProjectFolder")
                    .css("z-index", 20)
                    .click(function(){$(this).val("")});
            $("#input_main").css("z-index",10)
                    .hide();
            $("#uploadForm").prop("action","privateGit")
                    .prop("modelAttribute","gitCommand");
        });

        $("#a_public_git").click(function(){
            changeActionText($("#a_public_git").text());
            $("#input_text").prop("readonly", false)
                    .css("z-index",20)
                    .prop("placeholder", "Input Git Repository URL - ex) https://Jittore@bitbucket.org/Jictor/start.git")
                    .click(function(){$(this).val("")});
            $("#input_main").css("z-index",10)
                    .hide();
            $("#uploadForm").prop("action","publicGit")
                    .prop("modelAttribute","gitCommand");
        });

        $("#li_home").click(function(){
            $("#li_credit").removeClass("active");
            $(this).addClass("active");
        });

        $("#li_credit").click(function(){
            $("#li_home").removeClass("active");
            $(this).addClass("active");
        });

        $("#a_upload").click(function(){
            if(detectmob() === true){
                alert("You can not upload from mobile")
                return;
            }
            var inputFiles = document.getElementById("input_main").files;
            if($("#input_email").val() != "" && ((inputFiles != null && inputFiles.length > 0) || $("#input_text").val() != "")){
                $("#uploadForm").submit();
                return;
            }
            alert("Please, Write E-Mail and Upload.");
        });

        $("#input_main").on('fileselect', function(event, numFiles, label) {

            var input = $("#input_text"),
                    log = numFiles > 1 ? numFiles + ' files selected' : label;

            if( input.length ) {
                input.val(log);
            } else {
                if( log ) alert(log);
            }
        });

        function detectmob() {
            if( navigator.userAgent.match(/Android/i)
                    || navigator.userAgent.match(/webOS/i)
                    || navigator.userAgent.match(/iPhone/i)
                    || navigator.userAgent.match(/iPad/i)
                    || navigator.userAgent.match(/iPod/i)
                    || navigator.userAgent.match(/BlackBerry/i)
                    || navigator.userAgent.match(/Windows Phone/i)
            ){
                return true;
            }
            else {
                return false;
            }
        }
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