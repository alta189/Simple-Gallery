<!DOCTYPE html>
<html lang="en-gb" dir="ltr" class="uk-height-1-1">
<head>
	<meta charset="utf-8">
	<title>${gallery_name} | Sign In</title>
	<link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon">
	<link rel="apple-touch-icon-precomposed" href="images/apple-touch-icon.png">
	<link rel="stylesheet" href="css/uikit.almost-flat.min.css">
	<link rel="stylesheet" href="css/components/notify.min.css">
	<link rel="stylesheet" href="css/sticky.css">
	<link rel="stylesheet" href="css/gallery.css">
	<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
	<script src="js/uikit.min.js"></script>
	<script src="js/components/notify.min.js"></script>
	<script src="js/messages.js"></script>
</head>
<body class="uk-height-1-1">
	<div id="sticky-anchor"></div>
	<div id="sticky"><a class="uk-icon-button uk-icon-navicon" data-uk-offcanvas="{target: '#side-nav'}"></a></div>
	<!-- This is the off-canvas sidebar -->
	<div id="side-nav" class="uk-offcanvas">
		<div class="uk-offcanvas-bar">
			<ul class="uk-nav uk-nav-offcanvas uk-nav-parent-icon" data-uk-nav>
				<li class="uk-nav-divider"></li>
				<li><a href="/"><i class="uk-icon-home"></i> &nbsp;Home</a></li>
				<div id="user-info-panel" class="hidden">
					<li class="uk-nav-divider"></li>
					<div class="uk-panel" style="margin: 10px">
						<h3 class="uk-panel-title" style="color: #FFFFFF; margin-bottom: 5px">Account Info</h3>
						<hr style="color: #FFFFFF; margin-top: 0px; margin-bottom: 5px" size="10">
						<div class="wordwrap" style="color: #FFFFFF">
							<span id="user-name"></span><br />
							<span id="user-email"></span>
						</div>
					</div>
				</div>
				<li class="uk-nav-divider"></li>
				<li class="uk-active"><a href="#"><i class="uk-icon-sign-in"></i> &nbsp;Sign In</a></li>
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

	<div class="uk-vertical-align uk-text-center uk-height-1-1">
		<div class="uk-vertical-align-middle">
			<h1>Access Denied</h1>
			<iframe src="//giphy.com/embed/vSSdLSLbGIXio?html5=true" width="480" height="453" frameBorder="0" webkitAllowFullScreen mozallowfullscreen allowFullScreen></iframe>
			<h1><a href="/">You are NOT supposed to be here!</a></h1>
		</div>
	</div>
	<script src="js/side-nav.js"></script>
</body>
</html>
