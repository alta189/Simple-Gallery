<!DOCTYPE html>
<html lang="en-gb" dir="ltr" class="uk-height-1-1">
    <head>
        <meta charset="utf-8">
        <title>${gallery_name}</title>
        <link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon">
        <link rel="apple-touch-icon-precomposed" href="img/apple-touch-icon.png">
        <link rel="stylesheet" href="css/uikit.almost-flat.min.css">
	    <link rel="stylesheet" href="css/components/notify.min.css">
        <link rel="stylesheet" href="css/sticky.css">
        <link rel="stylesheet" href="css/gallery.css">
        <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
        <script src="js/uikit.min.js"></script>
	    <script src="js/components/notify.min.js"></script>
	    <script src="js/messages.js"></script>
    </head>
    <body>
        <div id="sticky-anchor"></div>
        <div id="sticky"><a class="uk-icon-button uk-icon-navicon" data-uk-offcanvas="{target: '#side-nav'}"></a></div>
    
        <div class="uk-container uk-container-center uk-margin-top uk-margin-large-bottom">

            <!-- This is the off-canvas sidebar -->
            <div id="side-nav" class="uk-offcanvas">
                <div class="uk-offcanvas-bar">
                    <ul class="uk-nav uk-nav-offcanvas uk-nav-parent-icon" data-uk-nav>
                        <li class="uk-nav-divider"></li>
                        <li class="uk-active"><a href="#"><i class="uk-icon-home"></i> &nbsp;Home</a></li>
                        <div id="user-info-panel" class="hidden">
	                        <li class="uk-nav-divider"></li>
	                        <div class="uk-panel" style="margin: 10px">
		                        <h3 class="uk-panel-title" style="color: #FFFFFF; margin-bottom: 5px">User Info</h3>
		                        <hr style="color: #FFFFFF; margin-top: 0px; margin-bottom: 5px" size="10">
		                        <div class="wordwrap" style="color: #FFFFFF">
			                        <span id="user-name"></span><br />
			                        <span id="user-email"></span>
		                        </div>
	                        </div>
                        </div>
                        <li class="uk-nav-divider"></li>
                        <li id="nav-user-control"><a href="/signin"><i class="uk-icon-sign-in"></i> &nbsp;Sign In</a></li>
                        <li><a href="#"><i class="uk-icon-picture-o"></i> &nbsp;Create Album</a></li>
                        <li><a href="#"><i class="uk-icon-terminal"></i> &nbsp;Admin</a></li>
                        <li class="uk-nav-divider"></li>
                        <li><a href="#"><i class="uk-icon-support"></i> &nbsp;Contact Us</a></li>
                        <div class="pinned-bottom">
                            <div class="uk-panel" style="margin: 10px">
                                <h3 class="uk-panel-title" style="color: #FFFFFF; margin-bottom: 5px">About Simple Gallery</h3>
                                <hr style="color: #FFFFFF; margin-top: 0px; margin-bottom: 5px" size="10">
                                <div class="wordwrap" style="color: #FFFFFF">
                                    &copy; 2015 Stephen Williams <br />
                                    <a href="https://raw.githubusercontent.com/alta189/Simple-Gallery/master/LICENSE">Licensed under New BSD License</a><br />
                                    <a href="https://github.com/alta189/Simple-Gallery">Source Code</a> <br />
                                    <a href="https://github.com/alta189/Simple-Gallery/issues">Report an Issue</a>
                                </div>
                            </div>
                        </div>
                    </ul>
                </div>
            </div>

            <div class="uk-grid" data-uk-grid-margin>
                <div class="uk-width-1-1">
                    <h1 class="uk-heading-large" align="center">${gallery_name}</h1>
                    <p class="uk-text-large" align="center">${gallery_description}</p>
                </div>
            </div>

            <div class="uk-grid">
                <div class="uk-width-1-2">
                    <a class="uk-thumbnail" href="#">
                        <img src="img/placeholder_600x400.svg" alt="">
                        <div class="uk-thumbnail-caption">Album Title Here</div>
                    </a>
                </div>
                <div class="uk-width-1-2">
                    <a class="uk-thumbnail" href="#">
                        <img src="img/placeholder_600x400.svg" alt="">
                        <div class="uk-thumbnail-caption">Album Title Here</div>
                    </a>
                </div>
            </div>

            <div class="uk-grid">
                <div class="uk-width-1-2">
                    <a class="uk-thumbnail" href="#">
                        <img src="img/placeholder_600x400.svg" alt="">
                        <div class="uk-thumbnail-caption">Album Title Here</div>
                    </a>
                </div>
                <div class="uk-width-1-2">
                    <a class="uk-thumbnail" href="#">
                        <img src="img/placeholder_600x400.svg" alt="">
                        <div class="uk-thumbnail-caption">Album Title Here</div>
                    </a>
                </div>
            </div>

            <div class="uk-grid">
                <div class="uk-width-1-2">
                    <a class="uk-thumbnail" href="#">
                        <img src="img/placeholder_600x400.svg" alt="">
                        <div class="uk-thumbnail-caption">Album Title Here</div>
                    </a>
                </div>
                <div class="uk-width-1-2">
                    <a class="uk-thumbnail" href="#">
                        <img src="img/placeholder_600x400.svg" alt="">
                        <div class="uk-thumbnail-caption">Album Title Here</div>
                    </a>
                </div>
            </div>

            <div class="uk-grid">
                <div class="uk-width-1-2">
                    <a class="uk-thumbnail" href="#">
                        <img src="img/placeholder_600x400.svg" alt="">
                        <div class="uk-thumbnail-caption">Album Title Here</div>
                    </a>
                </div>
                <div class="uk-width-1-2">
                    <a class="uk-thumbnail" href="#">
                        <img src="img/placeholder_600x400.svg" alt="">
                        <div class="uk-thumbnail-caption">Album Title Here</div>
                    </a>
                </div>
            </div>

            <div class="uk-grid">
                <div class="uk-width-1-2">
                    <a class="uk-thumbnail" href="#">
                        <img src="img/placeholder_600x400.svg" alt="">
                        <div class="uk-thumbnail-caption">Album Title Here</div>
                    </a>
                </div>
                <div class="uk-width-1-2">
                    <a class="uk-thumbnail" href="#">
                        <img src="img/placeholder_600x400.svg" alt="">
                        <div class="uk-thumbnail-caption">Album Title Here</div>
                    </a>
                </div>
            </div>
        </div>
        <script src="js/sticky.js"></script>
	    <script src="js/side-nav.js"></script>
    </body>
</html>
